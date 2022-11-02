package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.domain.StockCnUndoStInfo;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.mapper.StockCnUndoStInfoMapper;
import com.deloitte.crm.service.StockCnUndoStInfoService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import com.deloitte.crm.utils.ApplicationContextHolder;
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
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/14/18:08
 * @Description:
 */
@Slf4j
@Component
public class CnUndoStInfoStrategy implements WindTaskStrategy {
    @Resource
    private StockCnUndoStInfoMapper undoStInfoMapper;
    @Resource
    private StockCnInfoMapper stockCnInfoMapper;

    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.CN_ST_UNDO.getId(), windDictId);
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<StockCnUndoStInfo> util = new ExcelUtil<StockCnUndoStInfo>(StockCnUndoStInfo.class);
        List<StockCnUndoStInfo> list = util.importExcel(windTaskContext.getFileStream(), true);
        Collections.reverse(list);
        return ApplicationContextHolder.get().getBean(StockCnUndoStInfoService.class).doTask(windTask, list);
    }

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

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Wrapper<StockCnUndoStInfo> wrapper = Wrappers.<StockCnUndoStInfo>lambdaQuery()
                .eq(StockCnUndoStInfo::getTaskId, taskId)
                .in(StockCnUndoStInfo::getChangeType, 1, 2);

        return undoStInfoMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("代码", item.getCode());
            dataMap.put("证券简称", item.getName());

            return dataMap;
        }).collect(Collectors.toList());
    }

    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(StockCnUndoStInfo undoStInfo, Date timeNow, CrmWindTask windTask) {
        log.info(">>>>>>>>开始到导入【实施ST摘帽】【当前时间】=>:{},【股票代码】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), undoStInfo.getCode(), windTask.getId());
        undoStInfo.setTaskId(windTask.getId());
        StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, undoStInfo.getCode()));
        if (stockCnInfo != null) {
            stockCnInfo.setStUndoImplemtnet(1);
            stockCnInfo.setStockShortName(Optional.ofNullable(undoStInfo.getUndoBackName()).orElse(""));
            stockCnInfoMapper.updateById(stockCnInfo);
            log.info(">>>>>>>>开始到导入【实施ST摘帽】【当前时间】=>:{},【股票代码】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), undoStInfo.getCode(), windTask.getId());
        } else {
            log.warn(">>>>>>>>开始到导入【实施ST摘帽】根据【股票代码】=>:{}>查询不到A股信息 【任务ID】={}任务结束！！！！！", undoStInfo.getCode(), windTask.getId());
        }
        List<StockCnUndoStInfo> undoStInfoResultLists = undoStInfoMapper.selectList(new LambdaQueryWrapper<StockCnUndoStInfo>()
                .eq(StockCnUndoStInfo::getCode, undoStInfo.getCode())
                .orderBy(true, false, StockCnUndoStInfo::getId)
                .last("LIMIT 1"));
        Integer changeType = null;
        if (CollUtil.isEmpty(undoStInfoResultLists)) {
            changeType = DataChangeType.INSERT.getId();
        } else {
            final StockCnUndoStInfo stockCnUndoStInfo = undoStInfoResultLists.stream().findFirst().get();
            if (!Objects.equals(stockCnUndoStInfo, undoStInfo)) {
                changeType = DataChangeType.UPDATE.getId();
            }
        }
        undoStInfo.setChangeType(changeType);
        undoStInfoMapper.insert(undoStInfo);
        return new AsyncResult<>(new Object());
    }
}
