package com.deloitte.crm.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.constants.StockCnStatus;
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
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/27
 */
@Component
public class CnCheckDeclareStrategy implements WindTaskStrategy {

    @Resource
    private CnCheckDeclareService cnCheckDeclareService;

    @Resource
    private StockCnInfoService stockCnInfoService;

    @Resource
    private EntityStockCnRelService entityStockCnRelService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    /**
     * 处理文件中的每一行
     * @param item
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doThkStockImport(CnCheckDeclare item, Date timeNow, CrmWindTask windTask) {
        try {
            //设置属性
            item.setTaskId(windTask.getId());

            //查询a股是否存在
            String code = item.getCode();
            StockCnInfo stockCnInfo = stockCnInfoService.findByCode(code);

            //没有就创建一个
            if (stockCnInfo==null){
                stockCnInfo = new StockCnInfo();
                stockCnInfo.setStockCode(code);
            }


            //这条CnCoachBack是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            String entityName = item.getEntityName();
            CnCheckDeclare last = cnCheckDeclareService.findLastByEntityName(entityName);

            if (last==null){
                //查询不到之前的数据，代表是新增的
                changeType = DataChangeType.INSERT.getId();
                //当股票首次出现在  IPO审核申报表 中时，
                // 记为“IPO审核申报中(XXXX)”，其中XXXX为【审核状态】中的字段内容
                stockCnInfo.setStockStatus(StockCnStatus.CHECK_DECLARE.getId());
                stockCnInfo.setStatusDesc(StockCnStatus.CHECK_DECLARE.getName()+"("+item.getAuditStatus()+")");

            }else if (!Objects.equals(last, item)){
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }

            if (StrUtil.isNotBlank(code)){
                //保存a股信息
                stockCnInfoService.saveOrUpdateNew(stockCnInfo);

                //更新a股属性
                entityAttrValueService.updateStockCnAttr(code, item);
            }


            item.setChangeType(changeType);

            cnCheckDeclareService.save(item);

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
        return Objects.equals(WindTaskEnum.CN_CHECK_DECLARE.getId(), windDictId);
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
        ExcelUtil<CnCheckDeclare> util = new ExcelUtil<CnCheckDeclare>(CnCheckDeclare.class);
        List<CnCheckDeclare> cnCoachBacks = util.importExcel(file.getInputStream(), true);

        return cnCheckDeclareService.doTask(windTask, cnCoachBacks);
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


        arr.add("企业名称");
        arr.add("代码");


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
        Wrapper<CnCheckDeclare> wrapper = Wrappers.<CnCheckDeclare>lambdaQuery()
                .eq(CnCheckDeclare::getTaskId, taskId)
                .in(CnCheckDeclare::getChangeType, changeStatusArr);


        return cnCheckDeclareService.list(wrapper).stream().map(item->{
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("企业名称", item.getEntityName());
            dataMap.put("代码", item.getCode());


            return dataMap;
        }).collect(Collectors.toList());
    }


}
