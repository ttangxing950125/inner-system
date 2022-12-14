package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.math.MathUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.constants.StockThkStatus;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/26
 */
@Component
public class ThkSecIssDetailStrategy implements WindTaskStrategy {
    @Resource
    private CrmTypeInfoService crmTypeInfoService;

    @Resource
    private IThkSecIssDetailService thkSecIssDetailService;

    @Resource
    private StockThkInfoService stockThkInfoService;

    @Resource
    private EntityStockThkRelService entityStockThkRelService;

    @Resource
    private ICrmMasTaskService crmMasTaskService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    private BondsListingLogService bondsListingLogService;

    /**
     * 处理文件中的每一行
     *
     * @param thkSecIssInfos
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doThkStockImport(ThkSecIssDetail thkSecIssInfos, Date timeNow, CrmWindTask windTask) {
        //设置属性
        thkSecIssInfos.setTaskId(windTask.getId());

        //查询港股是否存在
        String code = thkSecIssInfos.getCode();
        StockThkInfo stockThkInfo = stockThkInfoService.findByCode(code);

        //没有就创建一个
        if (stockThkInfo == null) {
            stockThkInfo = new StockThkInfo();
        }
        //股票代码
        stockThkInfo.setStockCode(code);
        //港股简称
        stockThkInfo.setStockName(thkSecIssInfos.getName());
        //上市版
        stockThkInfo.setListsector(thkSecIssInfos.getIpoBoard());
        //上市日期
        stockThkInfo.setListDate(thkSecIssInfos.getIpoDate() == null ? null : cn.hutool.core.date.DateUtil.formatDate(thkSecIssInfos.getIpoDate()));
        //公司介绍
        stockThkInfo.setEntityIntro(Optional.ofNullable(thkSecIssInfos.getEntityDes()).orElse(""));

        /***
         *部分代码逻辑沿用BondDelIssStrategy
         * {@link com.deloitte.crm.strategy.impl.BondDelIssStrategy#doBondImport(BondDelIss, Date, CrmWindTask)}
         */
        //推迟或取消发行债券 均为二级发行
        CrmTypeInfo crmTypeInfo = crmTypeInfoService.getBaseMapper().selectOne(new LambdaQueryWrapper<CrmTypeInfo>().eq(CrmTypeInfo::getName, thkSecIssInfos.getBelWind()).eq(CrmTypeInfo::getIsDeleted, Boolean.FALSE).eq(CrmTypeInfo::getType, 1));
        if (crmTypeInfo != null) {
            if (StringUtils.isNotEmpty(crmTypeInfo.getParentCode())) {
                Set<CrmTypeInfo> hashSetResult = crmTypeInfoService.findCodeByParent(crmTypeInfo, Integer.valueOf(crmTypeInfo.getType()));
                if (CollectionUtil.isEmpty(hashSetResult)) {
                    thkSecIssInfos.setBelWind(crmTypeInfo.getName());
                } else {
                    String WindIndustryApend = hashSetResult.stream().sorted(Comparator.comparing(CrmTypeInfo::getLevel)).map(CrmTypeInfo::getName).collect(Collectors.joining("--"));
                    thkSecIssInfos.setBelWind(WindIndustryApend + "--" + crmTypeInfo.getName());
                }
            } else {
                thkSecIssInfos.setBelWind(crmTypeInfo.getName());
            }
        }

        //这条ThkSecIssDetail是新增还是修改 1-新增 2-修改
        Integer changeType = null;

        //查询这条数据有没有
        ThkSecIssDetail lastThkSecIssInfo = thkSecIssDetailService.findLastByCode(code);

        if (lastThkSecIssInfo == null) {

            changeType = DataChangeType.INSERT.getId();

            //当股票首次出现在  首次发行明细 中时，记为“发行中”
            stockThkInfo.setStockStatus(StockThkStatus.ISSUE.getId());
            stockThkInfo.setStatusDesc(StockThkStatus.ISSUE.getName());
        } else if (!Objects.equals(lastThkSecIssInfo, thkSecIssInfos)) {
            //如果他们两个不相同，代表有属性修改了
            changeType = DataChangeType.UPDATE.getId();
        }

        thkSecIssInfos.setChangeType(changeType);


        Date ipoDate = thkSecIssInfos.getIpoDate();

        boolean ipoNow = DateUtil.compare(ipoDate, timeNow) == 0;

        //当股票状态已经是“发行中”时，当【上市日期】 = 今天 时，状态改为“成功上市”
        if (Objects.equals(StockThkStatus.ISSUE.getId(), stockThkInfo.getStockStatus()) && DateUtil.compare(ipoDate, timeNow) == 0) {
            stockThkInfo.setStockStatus(StockThkStatus.LIST.getId());
            stockThkInfo.setStatusDesc(StockThkStatus.LIST.getName());

            //查询当前港股绑定的主体
            List<EntityInfo> entityInfos = entityStockThkRelService.findEntityByStockDqCode(stockThkInfo.getStockDqCode());

            entityInfos.forEach(entity -> {
                entity.setList(1);
            });


            //上市记录
            String names = entityInfos.stream().map(EntityInfo::getEntityName).collect(Collectors.joining());
            //创建log
            BondsListingLog log = new BondsListingLog();
            log.setCode(thkSecIssInfos.getCode());
            log.setName(thkSecIssInfos.getName());
            log.setIpoDate(DateUtil.parseDate(thkSecIssInfos.getIpoDate()));
            log.setPublisher(names);
            log.setRecordTime(timeNow);
            log.setSourceType(2);


            bondsListingLogService.save(log);

            entityInfoService.updateBatchById(entityInfos);

            //敞口划分任务
            crmMasTaskService.createTasks(entityInfos, windTask.getTaskCategory(), timeNow);

        }

        //新增港股
        stockThkInfo = stockThkInfoService.saveOrUpdateNew(stockThkInfo);

        if (changeType == null) {
            //保存attr
            //更新港股属性
            entityAttrValueService.updateStockThkAttr(stockThkInfo.getStockDqCode(), thkSecIssInfos);
        }

        //保存thkSecIssInfo
        thkSecIssDetailService.save(thkSecIssInfos);


        return new AsyncResult(new Object());
    }

    /**
     * 是否支持当前wind任务
     *
     * @param windDictId
     * @return
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(windDictId, WindTaskEnum.THK_SEC_ISS_DETAIL.getId());
    }

    /**
     * 开始执行任务
     *
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
//        读取文件
        ExcelUtil<ThkSecIssDetail> util = new ExcelUtil<ThkSecIssDetail>(ThkSecIssDetail.class);
        List<ThkSecIssDetail> list = util.importExcel(windTaskContext.getFileStream(), true);
        Collections.reverse(list);
        return thkSecIssDetailService.doTask(windTask, list);
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @param windTask
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        ArrayList<String> arr = new ArrayList<>();

        arr.add("导入日期");
        arr.add("变化状态");

        arr.add("证券代码");
        arr.add("证券简称");
        arr.add("上市日期");


        return arr;
    }

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     *
     * @param windTask
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();

        Wrapper<ThkSecIssDetail> wrapper = Wrappers.<ThkSecIssDetail>lambdaQuery()
                .eq(ThkSecIssDetail::getTaskId, taskId)
                .in(ThkSecIssDetail::getChangeType, 1, 2);


        return thkSecIssDetailService.list(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("证券代码", item.getCode());
            dataMap.put("证券简称", item.getName());
            dataMap.put("上市日期", item.getIpoDate());


            return dataMap;
        }).collect(Collectors.toList());
    }


}
