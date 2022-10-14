package com.deloitte.crm.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ImplementStInfo;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.service.BondConvertibleChangeInfoService;
import com.deloitte.crm.service.ImplementStInfoService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.utils.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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


    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(ImplementStInfo implementStInfo, Date timeNow, CrmWindTask windTask) {
        log.info(">>>>>>>>开始到导入【实施ST带帽】【当前时间】=>:{},【任务ID】=>:{}", DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, timeNow), windTask.getId());
        implementStInfo.setTaskId(windTask.getId());
        StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, implementStInfo.getCode()));
        if (stockCnInfo != null) {

        }

        return null;
    }

    @Override
    public boolean support(Integer windDictId) {
        return false;
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
        return null;
    }

}
