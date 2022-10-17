package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ImplementStInfo;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.mapper.ImplementStInfoMapper;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.service.BondConvertibleChangeInfoService;
import com.deloitte.crm.service.ImplementStInfoService;
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
public class ImplementStInfoStrategy implements WindTaskStrategy {
    @Resource
    private StockCnInfoMapper stockCnInfoMapper;
    @Resource
    private ImplementStInfoMapper implementStInfoMapper;


    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(ImplementStInfo implementStInfo, Date timeNow, CrmWindTask windTask) {
        log.info(">>>>>>>>开始到导入【实施ST带帽】【当前时间】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), windTask.getId());

        StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, implementStInfo.getCode()));
        if (stockCnInfo != null) {
            stockCnInfo.setStUndoImplemtnet(1);
            stockCnInfo.setStockShortName(Optional.ofNullable(implementStInfo.getImplementBackName()).orElse(""));
            stockCnInfoMapper.updateById(stockCnInfo);
            log.info(">>>>>>>>开始到导入【实施ST带帽】【当前时间】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), windTask.getId());
        } else {
            log.warn(">>>>>>>>开始到导入【实施ST带帽】根据<股票代码=>:{}>查询不到A股信息 【任务ID】={}任务结束！！！！！", implementStInfo.getCode(), windTask.getId());
        }
        ImplementStInfo implementStInfoResult = implementStInfoMapper.selectOne(new LambdaQueryWrapper<ImplementStInfo>().eq(ImplementStInfo::getCode, implementStInfo.getCode()));
        Integer changeType = null;
        if (implementStInfoResult == null) {
            changeType = DataChangeType.INSERT.getId();
        } else if (!Objects.equals(implementStInfoResult, implementStInfo)) {
            changeType = DataChangeType.UPDATE.getId();
        }
        implementStInfo.setChangeType(changeType);
        implementStInfo.setTaskId(windTask.getId());
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
        ExcelUtil<ImplementStInfo> util = new ExcelUtil<ImplementStInfo>(ImplementStInfo.class);
        List<ImplementStInfo> implementStInfo = util.importExcel(windTaskContext.getFileStream(), true);
        return ApplicationContextHolder.get().getBean(ImplementStInfoService.class).doTask(windTask, implementStInfo);
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
        arr.add("公司名称");
        return arr;
    }

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Wrapper<ImplementStInfo> wrapper = Wrappers.<ImplementStInfo>lambdaQuery()
                .eq(ImplementStInfo::getTaskId, taskId)
                .in(ImplementStInfo::getChangeType, 1, 2);

        return implementStInfoMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("公司代码", item.getCode());
            dataMap.put("公司名称", item.getName());

            return dataMap;
        }).collect(Collectors.toList());
    }

}
