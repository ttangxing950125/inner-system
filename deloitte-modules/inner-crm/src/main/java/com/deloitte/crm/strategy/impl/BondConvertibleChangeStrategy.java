package com.deloitte.crm.strategy.impl;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.domain.BondConvertibleInfo;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.BondConvertibleChangeInfoService;
import com.deloitte.crm.service.BondConvertibleInfoService;
import com.deloitte.crm.service.StockCnInfoService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import com.deloitte.crm.utils.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/13/16:50
 * @Description:
 */
@Slf4j
@Component
public class BondConvertibleChangeStrategy implements WindTaskStrategy {
    @Resource
    private StockCnInfoService stockCnInfoService;
    @Resource
    private EntityStockCnRelMapper entityStockCnRelMapper;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;
    @Resource
    private EntityAttrMapper entityAttrMapper;
    @Resource
    private BondInfoMapper bondInfoMapper;
    @Resource
    private EntityBondRelMapper entityBondRelMapper;

    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.BOND_CONVERTIBLE_CHANGE.getId(), windDictId);
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<BondConvertibleChangeInfo> util = new ExcelUtil<BondConvertibleChangeInfo>(BondConvertibleChangeInfo.class);
        List<BondConvertibleChangeInfo> bondConvertibleChangeInfo = util.importExcel(windTaskContext.getFileStream(), true);
        return ApplicationContextHolder.get().getBean(BondConvertibleChangeInfoService.class).doTask(windTask, bondConvertibleChangeInfo);
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

    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(BondConvertibleChangeInfo item, Date timeNow, CrmWindTask windTask) {
        item.setTaskId(windTask.getId());
        StockCnInfo stockCnInfo = stockCnInfoService.findByCode(item.getCode());
        return null;
    }
}
