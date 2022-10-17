package com.deloitte.crm.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ImplementStInfo;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.domain.UndoStInfo;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.mapper.UndoStInfoMapper;
import com.deloitte.crm.service.ImplementStInfoService;
import com.deloitte.crm.service.UndoStInfoService;
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
public class UndoStInfoStrategy implements WindTaskStrategy {
    @Resource
    private UndoStInfoMapper undoStInfoMapper;
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
        ExcelUtil<UndoStInfo> util = new ExcelUtil<UndoStInfo>(UndoStInfo.class);
        List<UndoStInfo> undoStInfoListndoStInfo = util.importExcel(windTaskContext.getFileStream(), true);
        return ApplicationContextHolder.get().getBean(UndoStInfoService.class).doTask(windTask, undoStInfoListndoStInfo);
    }

    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {ArrayList<String> arr = new ArrayList<>();
        //证券代码
        //证券简称
        //公司中文名称
        arr.add("导入日期");
        arr.add("变化状态");
        arr.add("代码");
        arr.add("公司名称");
        return arr;
    }

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Wrapper<UndoStInfo> wrapper = Wrappers.<UndoStInfo>lambdaQuery()
                .eq(UndoStInfo::getTaskId, taskId)
                .in(UndoStInfo::getChangeType, 1, 2);

        return undoStInfoMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("公司代码", item.getCode());
            dataMap.put("公司名称", item.getName());

            return dataMap;
        }).collect(Collectors.toList());
    }
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(UndoStInfo undoStInfo, Date timeNow, CrmWindTask windTask) {
        log.info(">>>>>>>>开始到导入【实施ST摘帽】【当前时间】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), windTask.getId());
        StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, undoStInfo.getCode()));
        if (stockCnInfo != null) {
            stockCnInfo.setStUndoImplemtnet(1);
            stockCnInfo.setStockShortName(Optional.ofNullable(undoStInfo.getUndoBackName()).orElse(""));
            stockCnInfoMapper.updateById(stockCnInfo);
            log.info(">>>>>>>>开始到导入【实施ST摘帽】【当前时间】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), windTask.getId());
        } else {
            log.warn(">>>>>>>>开始到导入【实施ST摘帽】根据<股票代码=>:{}>查询不到A股信息 【任务ID】={}任务结束！！！！！", undoStInfo.getCode(), windTask.getId());
        }
        UndoStInfo undoStInfoResult = undoStInfoMapper.selectOne(new LambdaQueryWrapper< UndoStInfo>().eq(UndoStInfo::getCode, undoStInfo.getCode()));
        Integer changeType = null;
        if (undoStInfoResult == null) {
            changeType = DataChangeType.INSERT.getId();
        } else if (!Objects.equals(undoStInfoResult, undoStInfo)) {
            changeType = DataChangeType.UPDATE.getId();
        }
        undoStInfo.setChangeType(changeType);
        undoStInfo.setTaskId(windTask.getId());
        undoStInfoMapper.insert(undoStInfo);
        return new AsyncResult<>(new Object());
    }
}
