package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.CnDelistInfoMapper;
import com.deloitte.crm.mapper.EntityStockCnRelMapper;
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
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/11/18:40
 * @Description: A股 退市
 */
@Component
public class CnDelistInfoStrategy implements WindTaskStrategy {
    @Resource
    private CnDelistInfoService cnDelistInfoService;
    @Resource
    private StockCnInfoService stockCnInfoService;
    @Resource
    private CnDelistInfoMapper cnDelistInfoMapper;


    @Resource
    private EntityStockCnRelService entityStockCnRelService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private EntityStockCnRelMapper entityStockCnRelMapper;

    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.CN_DELISTINFO.getId(), windDictId);
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<CnDelistInfo> util = new ExcelUtil<CnDelistInfo>(CnDelistInfo.class);
        List<CnDelistInfo> cnCoachBacks = util.importExcel(windTaskContext.getFileStream(), true);
        Collections.reverse(cnCoachBacks);
        return cnDelistInfoService.doTask(windTask, cnCoachBacks);
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
        Wrapper<CnDelistInfo> wrapper = Wrappers.<CnDelistInfo>lambdaQuery()
                .eq(CnDelistInfo::getTaskId, taskId)
                .in(CnDelistInfo::getChangeType, 1, 2);

        return cnDelistInfoMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("证券代码", item.getCode());
            dataMap.put("证券简称", item.getName());

            return dataMap;
        }).collect(Collectors.toList());
    }

    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<CnDelistInfo> doBondImport(CnDelistInfo item, Date timeNow, CrmWindTask windTask) {
        //设置属性
        item.setTaskId(windTask.getId());
        //查询a股是否存在
        String code = item.getCode();
        StockCnInfo stockCnInfo = stockCnInfoService.findByCode(code);
        if (stockCnInfo != null) {
//            stockCnInfo.setIsDeleted(Boolean.TRUE);//TODO 1-删除 0-未删除 默认都是未删除
            stockCnInfo.setDelistingDate(DateUtil.formatDate(item.getDelistDate()));
            stockCnInfo = stockCnInfoService.saveOrUpdateNew(stockCnInfo);
            final LambdaQueryWrapper<EntityStockCnRel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            EntityStockCnRel entityStockCnRel = entityStockCnRelMapper.selectOne(lambdaQueryWrapper.eq(EntityStockCnRel::getStockDqCode, stockCnInfo.getStockDqCode()));
            if (entityStockCnRel != null) {
                entityStockCnRel.setStatus(Boolean.FALSE);
                entityStockCnRelMapper.updateById(entityStockCnRel);
            }
        }
        //这条CnDelistInfo 是新增还是修改 1-新增 2-修改
        Integer changeType = null;
        String excelReadCode = item.getCode();
        final LambdaQueryWrapper<CnDelistInfo> cnDelistInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        final List<CnDelistInfo> cnDelistInfos = cnDelistInfoMapper.selectList(cnDelistInfoLambdaQueryWrapper.eq(CnDelistInfo::getCode, excelReadCode).orderBy(true, false, CnDelistInfo::getId).last("LIMIT 1"));
        if (CollUtil.isEmpty(cnDelistInfos)) {
            changeType = DataChangeType.INSERT.getId();
        } else {
            CnDelistInfo last = cnDelistInfos.stream().findFirst().get();
            if (!Objects.equals(last, item)) {
                changeType = DataChangeType.UPDATE.getId();
            }
        }
        item.setChangeType(changeType);

        if (changeType!=null){
            //更新a股属性
            entityAttrValueService.updateStockCnAttr(stockCnInfo.getStockDqCode(), item);
        }
        //添加
        cnDelistInfoMapper.insert(item);

        return new AsyncResult(new Object());
    }
}
