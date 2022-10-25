package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.StockCnImplementStInfoMapper;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.service.StockCnImplementStInfoService;
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
 * @Date: 2022/10/14/18:07
 * @Description:
 */
@Slf4j
@Component
public class StockCnImplementStInfoStrategy implements WindTaskStrategy {
    @Resource
    private StockCnInfoMapper stockCnInfoMapper;
    @Resource
    private StockCnImplementStInfoMapper implementStInfoMapper;


    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(StockCnImplementStInfo implementStInfo, Date timeNow, CrmWindTask windTask) {
        log.info(">>>>>>>>开始到导入【实施ST带帽】【当前时间】=>:{},【股票代码】=>:{}【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), implementStInfo.getCode(), windTask.getId());
        implementStInfo.setTaskId(windTask.getId());
        StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, implementStInfo.getCode()));
        if (stockCnInfo != null) {
            stockCnInfo.setStUndoImplemtnet(1);
            stockCnInfo.setStockShortName(Optional.ofNullable(implementStInfo.getImplementBackName()).orElse(""));
            stockCnInfoMapper.updateById(stockCnInfo);
            log.info(">>>>>>>>开始到导入【实施ST带帽】【当前时间】=>:{},【股票代码】=>:{}【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), implementStInfo.getCode(), windTask.getId());
        } else {
            log.warn(">>>>>>>>开始到导入【实施ST带帽】根据【股票代码】=>:{}>查询不到A股信息 【任务ID】={}任务结束！！！！！", implementStInfo.getCode(), windTask.getId());
        }

        List<StockCnImplementStInfo> implementStInfoResult = implementStInfoMapper.selectList(new LambdaQueryWrapper<StockCnImplementStInfo>()
                .eq(StockCnImplementStInfo::getCode, implementStInfo.getCode())
                .orderBy(true, false, StockCnImplementStInfo::getId)
                .last("LIMIT 1"));

        Integer changeType = null;
        if (CollUtil.isEmpty(implementStInfoResult)) {
            changeType = DataChangeType.INSERT.getId();
        } else {
            final StockCnImplementStInfo last = implementStInfoResult.stream().findFirst().get();
            last.setNumber(null);
            implementStInfo.setNumber(null);
            if (!ObjectUtil.equals(last, implementStInfo)) {
                changeType = DataChangeType.UPDATE.getId();
            }
        }
        implementStInfo.setChangeType(changeType);
        implementStInfoMapper.insert(implementStInfo);
        return new AsyncResult<>(new Object());

    }

    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.CN_ST_IMPLEMENT.getId(), windDictId);
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<StockCnImplementStInfo> util = new ExcelUtil<StockCnImplementStInfo>(StockCnImplementStInfo.class);
        List<StockCnImplementStInfo> list = util.importExcel(windTaskContext.getFileStream(), true);
        Collections.reverse(list);
        return ApplicationContextHolder.get().getBean(StockCnImplementStInfoService.class).doTask(windTask, list);
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
        Wrapper<StockCnImplementStInfo> wrapper = Wrappers.<StockCnImplementStInfo>lambdaQuery()
                .eq(StockCnImplementStInfo::getTaskId, taskId)
                .in(StockCnImplementStInfo::getChangeType, 1, 2);

        return implementStInfoMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("代码", item.getCode());
            dataMap.put("证券简称", item.getName());

            return dataMap;
        }).collect(Collectors.toList());
    }

}
