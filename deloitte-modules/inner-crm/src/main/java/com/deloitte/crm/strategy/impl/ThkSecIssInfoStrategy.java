package com.deloitte.crm.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.service.EntityStockThkRelService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.service.StockThkInfoService;
import com.deloitte.crm.service.ThkSecIssInfoService;
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
 * @date 2022/9/25
 */
@Component
public class ThkSecIssInfoStrategy implements WindTaskStrategy {

    @Resource
    private ThkSecIssInfoService thkSecIssInfoService;

    @Resource
    private StockThkInfoService stockThkInfoService;

    @Resource
    private EntityStockThkRelService entityStockThkRelService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }

    /**
     * 导入ThkSecIssInfo表的数据
     * @param secIssInfo
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doThkStockImport(ThkSecIssInfo secIssInfo, Date timeNow, CrmWindTask windTask) {
        try {
            //设置属性
            secIssInfo.setTaskId(windTask.getId());

            //查询证券代码是否存在
            String secIssInfoCode = secIssInfo.getCode();

            StockThkInfo stockThkInfo = stockThkInfoService.findByCode(secIssInfoCode);
            //没有就创建一个
            if (stockThkInfo==null){
                stockThkInfo = new StockThkInfo();
                stockThkInfo.setStockCode(secIssInfoCode);
                stockThkInfo.setStockStatus(1);
                stockThkInfo.setStatusDesc("聆讯中("+secIssInfo.getStatus()+")");
            }

            stockThkInfo.setStockName(secIssInfo.getName());



            //主体名
            String entityName = secIssInfo.getEntityCnName();

            //这条ThkSecIssInfo是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            //查询这条数据有没有
            ThkSecIssInfo lastThkSecIssInfo = thkSecIssInfoService.findLastByEntityName(entityName);
            if (lastThkSecIssInfo==null){
                changeType = DataChangeType.INSERT.getId();
            }else if (!Objects.equals(lastThkSecIssInfo, secIssInfo)){
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }

            secIssInfo.setChangeType(changeType);

            //新增港股
            stockThkInfo = stockThkInfoService.saveOrUpdateNew(stockThkInfo);


            //保存thkSecIssInfo
            thkSecIssInfoService.save(secIssInfo);

            //如果是新增数据，并且有主体名，就要自动绑定关联关系了
            if (Objects.equals(DataChangeType.INSERT.getId(), changeType) && StrUtil.isNotBlank(entityName)){
                entityStockThkRelService.bindRelOrCreateTask(stockThkInfo, entityName, windTask, secIssInfo);
            }

            //更新港股属性
            entityAttrValueService.updateStockThkAttr(stockThkInfo.getStockDqCode(), secIssInfo);


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
        return Objects.equals(windDictId, WindTaskEnum.THK_SEC_ISS_INFO.getId());
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
        ExcelUtil<ThkSecIssInfo> util = new ExcelUtil<ThkSecIssInfo>(ThkSecIssInfo.class);
        List<ThkSecIssInfo> thkSecIssInfos = util.importExcel(windTaskContext.getFileStream(), true);;

        return thkSecIssInfoService.doTask(windTask, thkSecIssInfos);
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
        arr.add("证券代码");
        arr.add("证券简称");
        arr.add("公司中文名称");


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
        Wrapper<ThkSecIssInfo> wrapper = Wrappers.<ThkSecIssInfo>lambdaQuery()
                .eq(ThkSecIssInfo::getTaskId, taskId)
                .in(ThkSecIssInfo::getChangeType, 1, 2);


        return thkSecIssInfoService.list(wrapper).stream().map(item->{
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("证券代码", item.getCode());
            dataMap.put("证券简称", item.getName());
            dataMap.put("公司中文名称", item.getEntityCnName());


            return dataMap;
        }).collect(Collectors.toList());
    }

}
