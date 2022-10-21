package com.deloitte.crm.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.constants.StockCnStatus;
import com.deloitte.crm.constants.StockThkStatus;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.service.CnCoachBackService;
import com.deloitte.crm.service.EntityStockCnRelService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.service.StockCnInfoService;
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
 * @date 2022/9/27 IPO-辅导备案
 */
@Slf4j
@Component
public class CnCoachBackStrategy implements WindTaskStrategy {

    @Resource
    private CnCoachBackService cnCoachBackService;

    @Resource
    private StockCnInfoService stockCnInfoService;

    @Resource
    private EntityStockCnRelService entityStockCnRelService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    /**
     * 处理文件中的每一行
     * IPO-辅导备案
     *
     * @param cnCoachBack
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doThkStockImport(CnCoachBack cnCoachBack, Date timeNow, CrmWindTask windTask) {
        try {
            //设置属性
            cnCoachBack.setTaskId(windTask.getId());

            //查询a股是否存在
            String code = cnCoachBack.getCode();
            StockCnInfo stockCnInfo = stockCnInfoService.findByCode(code);

            //没有就创建一个
            if (stockCnInfo == null) {
                stockCnInfo = new StockCnInfo();
            }

            stockCnInfo.setStockCode(code);


            //这条CnCoachBack是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            String entityName = cnCoachBack.getEntityName();
            CnCoachBack lastCoachBack = cnCoachBackService.findLastByEntityName(entityName);

            if (lastCoachBack == null) {
                //查询不到之前的数据，代表是新增的
                changeType = DataChangeType.INSERT.getId();
                //当股票首次出现在  IPO辅导备案表 中时，记为“IPO辅导备案”
                Integer stockStatus = stockCnInfo.getStockStatus();
                if (stockStatus == null) {
                    log.info("==> IPO-辅导备案 修改A股状态为 《IPO-辅导备案》1 ！！");
                    stockCnInfo.setStockStatus(StockCnStatus.COACH_BACK.getCode());
                    stockCnInfo.setStatusDesc(StockCnStatus.COACH_BACK.getMessage());
                } else {
                    log.warn("==> IPO-辅导备案 跳过修改A股状态逻辑目前【股票代码】:{},A股状态为:{}", code, stockCnInfo.getStockStatus());
                }
            } else if (!Objects.equals(lastCoachBack, cnCoachBack)) {
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }
            if (StrUtil.isNotBlank(code)) {
                //保存a股信息
                stockCnInfoService.saveOrUpdateNew(stockCnInfo);
                if (changeType != null) {
                    //更新a股属性
                    entityAttrValueService.updateStockCnAttr(code, cnCoachBack);
                }
            }
            //有债券信息，给债券和主体绑定关联关系
            if (StrUtil.isNotBlank(code) && Objects.equals(changeType, DataChangeType.INSERT.getId())) {
                //绑定主体关系
                entityStockCnRelService.bindRelOrCreateTask(stockCnInfo, entityName, windTask, cnCoachBack);
            }

            cnCoachBack.setChangeType(changeType);

            cnCoachBackService.save(cnCoachBack);

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
        return Objects.equals(WindTaskEnum.CN_COACH_BACK.getId(), windDictId);
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
        ExcelUtil<CnCoachBack> util = new ExcelUtil<CnCoachBack>(CnCoachBack.class);
        List<CnCoachBack> cnCoachBacks = util.importExcel(windTaskContext.getFileStream(), true);
        ;

        return cnCoachBackService.doTask(windTask, cnCoachBacks);
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @param windTask
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {


        return Arrays.asList(
                "导入日期",
                "变化状态",

                "企业名称",
                "代码",
                "最新公告日",
                "审核状态"
        );
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
        Wrapper<CnCoachBack> wrapper = Wrappers.<CnCoachBack>lambdaQuery()
                .eq(CnCoachBack::getTaskId, taskId)
                .in(CnCoachBack::getChangeType, 1, 2);


        return cnCoachBackService.list(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("企业名称", item.getEntityName());
            dataMap.put("代码", item.getCode());
            dataMap.put("最新公告日", item.getAnnoDate());
            dataMap.put("审核状态", item.getAuditStatus());


            return dataMap;
        }).collect(Collectors.toList());
    }


}
