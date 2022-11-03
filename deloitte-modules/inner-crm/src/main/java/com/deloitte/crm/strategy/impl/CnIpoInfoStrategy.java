package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.BondStatus;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.constants.StockCnStatus;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/27
 * IPO-新股发行资料
 */
@Slf4j
@Component
public class CnIpoInfoStrategy implements WindTaskStrategy {

    @Resource
    private ICnIpoInfoService cnIpoInfoService;

    @Resource
    private StockCnInfoService stockCnInfoService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private ICrmMasTaskService crmMasTaskService;

    @Resource
    private EntityStockCnRelService entityStockCnRelService;

    @Resource
    private IEntityInfoService entityInfoService;


    @Resource
    private BondsListingLogService bondsListingLogService;

    /**
     * 处理文件中的每一行
     *
     * @param item
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doThkStockImport(CnIpoInfo item, Date timeNow, CrmWindTask windTask) {
        try {
            //设置属性
            item.setTaskId(windTask.getId());

            //查询a股是否存在
            String code = item.getCode();
            StockCnInfo stockCnInfo = stockCnInfoService.findByCode(code);

            //没有就创建一个
            if (stockCnInfo == null) {
                stockCnInfo = new StockCnInfo();
            }

            stockCnInfo.setStockCode(code);
            stockCnInfo.setStockShortName(item.getStockName());

            //这条CnCoachBack是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            CnIpoInfo last = cnIpoInfoService.findLastByCode(code);

            if (last == null) {
                //查询不到之前的数据，代表是新增的
                changeType = DataChangeType.INSERT.getId();
                //当股票首次出现在  新股发行 中时，记为“发行中”
                if (stockCnInfo.getStockStatus() == null) {
                    stockCnInfo.setStockStatus(StockCnStatus.ISSUE.getCode());
                    stockCnInfo.setStatusDesc(StockCnStatus.ISSUE.getMessage());
                } else if (stockCnInfo.getStockStatus() != null && stockCnInfo.getStockStatus() == StockCnStatus.IEC_SMPC_CHECK.getCode()) {
                    stockCnInfo.setStockStatus(StockCnStatus.ISSUE.getCode());
                    stockCnInfo.setStatusDesc(StockCnStatus.ISSUE.getMessage());
                } else {
                    log.warn("==> 新股发行 跳过修改A股状态逻辑目前【股票代码】:{},A股状态为:{}", code, stockCnInfo.getStockStatus());
                }

            } else if (!Objects.equals(last, item)) {
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }

            if (last != null && !StrUtil.equals(last.getIpoDate(), item.getIpoDate())) {
                //*后续如果该股票信息再次更新有出现新的【上市日期】时，状态变回为“发行中”，
                // 并当【上市日期】 = 今天 时， 状态改为“成功上市”
                stockCnInfo.setStockStatus(StockCnStatus.ISSUE.getCode());
                stockCnInfo.setStatusDesc(StockCnStatus.ISSUE.getMessage());
            }


            //上市日期是否是今天
            boolean ipoNow = DateUtil.format(timeNow, "yyyy-MM-dd").equals(item.getIpoDate());

            //当股票状态已经是“发行中”时，且【上市日期】 = 今天 时，状态改为“成功上市”
            if (Objects.equals(stockCnInfo.getStockStatus(), StockCnStatus.ISSUE.getCode()) && DateUtil.format(timeNow, "yyyy-MM-dd").equals(item.getIpoDate())) {
                stockCnInfo.setStockStatus(StockCnStatus.IPO_INFO.getCode());
                stockCnInfo.setStatusDesc(StockCnStatus.IPO_INFO.getMessage());
            }

            String windIndustry = item.getWindIndustry();

            List<EntityInfo> entityInfos = entityStockCnRelService.findByStockCode(stockCnInfo.getStockDqCode());
            if (!CollectionUtil.isEmpty(entityInfos)) {
                for (EntityInfo entityInfo : entityInfos) {
                    entityInfo.setWindMaster(windIndustry);
                    //审计机构
                    entityInfo.setEntityAuditinstitNew(item.getAuditInst());
                }

            }
            //如果是成功上市，发送给敞口划分人
            if (Objects.equals(stockCnInfo.getStockStatus(), StockCnStatus.IPO_INFO.getCode())) {
                //查询和当前a股绑定关联关系的主体
                entityInfos.forEach(entity -> {
                    entity.setList(1);
                });
                if (ipoNow) {
                    //新敞口划分任务
                    crmMasTaskService.createTasks(entityInfos, windTask.getTaskCategory(), windTask.getTaskDate());

                    //上市记录
                    String names = entityInfos.stream().map(EntityInfo::getEntityName).collect(Collectors.joining());
                    //创建log
                    BondsListingLog log = new BondsListingLog();
                    log.setCode(item.getCode());
                    log.setName(item.getStockName());
                    log.setIpoDate(DateUtil.parseDate(item.getIpoDate()));
                    log.setPublisher(names);
                    log.setRecordTime(timeNow);
                    log.setSourceType(3);


                    bondsListingLogService.save(log);
                }
            }
            entityInfoService.updateBatchById(entityInfos);
            if (StrUtil.isNotBlank(code)) {
                //保存a股信息
                stockCnInfo = stockCnInfoService.saveOrUpdateNew(stockCnInfo);
                //更新a股属性
                entityAttrValueService.updateStockCnAttr(stockCnInfo.getStockDqCode(), item);
            }
            item.setChangeType(changeType);

            cnIpoInfoService.save(item);

            return new AsyncResult(new Object());
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<>(e);
        }
    }

    /**
     * 是否支持当前wind任务
     *
     * @param windDictId
     * @return
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.CN_IPO_INFO.getId(), windDictId);
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
        ExcelUtil<CnIpoInfo> util = new ExcelUtil<CnIpoInfo>(CnIpoInfo.class);
        List<CnIpoInfo> list = util.importExcel(null, file.getInputStream(), 1, true);
        Collections.reverse(list);
        return cnIpoInfoService.doTask(windTask, list);
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
        //证券代码
        //证券简称
        //公司中文名称
        arr.add("导入日期");
        arr.add("变化状态");


        arr.add("代码");
        arr.add("证券简称");
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
        List<Integer> changeStatusArr = Arrays.stream(DataChangeType.values()).map(DataChangeType::getId).collect(Collectors.toList());

        Integer taskId = windTask.getId();
        Wrapper<CnIpoInfo> wrapper = Wrappers.<CnIpoInfo>lambdaQuery()
                .eq(CnIpoInfo::getTaskId, taskId)
                .in(CnIpoInfo::getChangeType, changeStatusArr);


        return cnIpoInfoService.list(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());
            dataMap.put("代码", item.getCode());
            dataMap.put("证券简称", item.getStockName());


            return dataMap;
        }).collect(Collectors.toList());
    }


}
