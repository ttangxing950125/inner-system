package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * IPO-发审委上市委审核结果
 */
@Slf4j
@Component
public class CnIecSmpcCheckResultStrategy implements WindTaskStrategy {

    @Resource
    private CnIecSmpcCheckResultService cnIecSmpcCheckResultService;

    @Resource
    private StockCnInfoService stockCnInfoService;

    @Resource
    private EntityStockCnRelService entityStockCnRelService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private IEntityInfoService entityInfoService;

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
    public Future<Object> doThkStockImport(CnIecSmpcCheckResult item, Date timeNow, CrmWindTask windTask) {
        try {
            //设置属性
            item.setTaskId(windTask.getId());

            //查询a股是否存在
            String code = item.getTempCode();
//            StockCnInfo stockCnInfo = stockCnInfoService.findByCode(code);
            StockCnInfo stockCnInfo = stockCnInfoService.getBaseMapper().selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, code)
                    .eq(StockCnInfo::getIsDeleted, Boolean.FALSE));

            //没有就创建一个
            if (stockCnInfo == null) {
                stockCnInfo = new StockCnInfo();
                stockCnInfo.setStockCode(code);
            }
            //这条CnCoachBack是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            String entityName = item.getEntityName();
            CnIecSmpcCheckResult last = cnIecSmpcCheckResultService.findLastByEntityName(entityName);
            if (last == null) {
                //查询不到之前的数据，代表是新增的
                changeType = DataChangeType.INSERT.getId();
                //当股票首次出现在  IPO审核申报表 中时，
                // 记为“IPO审核申报中(XXXX)”，其中XXXX为【审核状态】中的字段内容
                if (stockCnInfo.getStockStatus() == null) {
                    log.info("==> IPO-发审委上市委审核结果 修改股票状态为 《IPO法审上市委审核中》3 ！！！");
                    stockCnInfo.setStockStatus(StockCnStatus.IEC_SMPC_CHECK.getCode());
                    stockCnInfo.setStatusDesc(StockCnStatus.IEC_SMPC_CHECK.getMessage() + "(" + item.getCheckResult() + ")");
                } else if (stockCnInfo.getStockStatus() != null && stockCnInfo.getStockStatus() == StockCnStatus.CHECK_DECLARE.getCode()) {
                    log.info("==> IPO-发审委上市委审核结果 原【股票代码】={} A股状态为:{} 修改A股状态为:《IPO法审上市委审核中》3！！！", stockCnInfo.getStockCode(), stockCnInfo.getStockStatus());
                    stockCnInfo.setStockStatus(StockCnStatus.IEC_SMPC_CHECK.getCode());
                    stockCnInfo.setStatusDesc(StockCnStatus.IEC_SMPC_CHECK.getMessage() + "(" + item.getCheckResult() + ")");
                } else {
                    log.warn("==> IPO-发审委上市委审核结果 跳过修改A股状态逻辑目前【股票代码】:{},A股状态为:{}", code, stockCnInfo.getStockStatus());
                }
            } else if (!Objects.equals(last, item)) {
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }
            if (StrUtil.isNotBlank(code)) {
                //保存a股信息
                stockCnInfoService.saveOrUpdateNew(stockCnInfo);

                if (changeType != null) {
                    //更新a股属性
                    entityAttrValueService.updateStockCnAttr(code, item);
                }
                if (changeType != null ) {
                    //查询状态status=1
                    List<EntityInfo> entityInfos = entityInfoService.findByName(entityName);
                    if (CollUtil.isNotEmpty(entityInfos)) {
                       /* List<EntityInfo> mapEntityInfos = entityInfos.stream().map(e -> e.setWindMaster(windIndustry)).collect(Collectors.toList());
                        entityInfoService.updateBatchById(mapEntityInfos);*/
                        for (EntityInfo info : entityInfos) {
                            String entityCode = info.getEntityCode();
                            String stockDqCode = stockCnInfo.getStockDqCode();
                            //查询关联关系
                            EntityStockCnRel dbRel = entityStockCnRelService.getBaseMapper().selectOne(new LambdaQueryWrapper<EntityStockCnRel>().eq(EntityStockCnRel::getEntityCode, entityCode).eq(EntityStockCnRel::getStockDqCode, stockDqCode).eq(EntityStockCnRel::getStatus, Boolean.FALSE));
                            if (dbRel != null) {
                                continue;
                            }
                            //新增关联关系
                            EntityStockCnRel cnRel = new EntityStockCnRel();
                            cnRel.setEntityCode(entityCode);
                            cnRel.setStockDqCode(stockDqCode);
                            cnRel.setStatus(Boolean.TRUE);
                            entityStockCnRelService.getBaseMapper().insert(cnRel);
                        }
                    }
                }
            }
            item.setChangeType(changeType);
            cnIecSmpcCheckResultService.save(item);

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
        return Objects.equals(WindTaskEnum.CN_IEC_SMPC_CHECK_RESULT.getId(), windDictId);
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
        ExcelUtil<CnIecSmpcCheckResult> util = new ExcelUtil<CnIecSmpcCheckResult>(CnIecSmpcCheckResult.class);
        List<CnIecSmpcCheckResult> list = util.importExcel(windTaskContext.getFileStream(), true);
        ;
        Collections.reverse(list);
        return cnIecSmpcCheckResultService.doTask(windTask, list);
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


        arr.add("临时代码");
        arr.add("公司名称");


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
        Wrapper<CnIecSmpcCheckResult> wrapper = Wrappers.<CnIecSmpcCheckResult>lambdaQuery()
                .eq(CnIecSmpcCheckResult::getTaskId, taskId)
                .in(CnIecSmpcCheckResult::getChangeType, changeStatusArr);


        return cnIecSmpcCheckResultService.list(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("公司名称", item.getEntityName());
            dataMap.put("临时代码", item.getTempCode());


            return dataMap;
        }).collect(Collectors.toList());
    }


}
