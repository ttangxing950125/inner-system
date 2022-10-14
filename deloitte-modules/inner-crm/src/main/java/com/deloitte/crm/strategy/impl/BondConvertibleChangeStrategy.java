package com.deloitte.crm.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
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
    @Resource
    private BondConvertibleChangeInfoMapper bondConvertibleChangeInfoMapper;

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
        Integer taskId = windTask.getId();
        Wrapper<BondConvertibleChangeInfo> wrapper = Wrappers.<BondConvertibleChangeInfo>lambdaQuery()
                .eq(BondConvertibleChangeInfo::getTaskId, taskId)
                .in(BondConvertibleChangeInfo::getChangeType, 1,2);

        return bondConvertibleChangeInfoMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("公司代码", item.getCode());
            dataMap.put("公司名称", item.getName());

            return dataMap;
        }).collect(Collectors.toList());
    }

    /***
     * 逻辑餐参看 {@link com.deloitte.crm.strategy.impl.BondConvertibleStrategy}
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(BondConvertibleChangeInfo item, Date timeNow, CrmWindTask windTask) {
        item.setTaskId(windTask.getId());
        StockCnInfo stockCnInfo = stockCnInfoService.findByCode(item.getCode());
        if (stockCnInfo != null) {
            Long attrId = 0L;
            EntityStockCnRel entityStockCnRel = entityStockCnRelMapper.selectOne(new LambdaQueryWrapper<EntityStockCnRel>().eq(EntityStockCnRel::getStockDqCode, stockCnInfo.getStockDqCode()).eq(EntityStockCnRel::getStatus, Boolean.TRUE));
            if (entityStockCnRel != null) {
                List<EntityAttr> entityAttrsAll = entityAttrMapper.selectList(new LambdaQueryWrapper<EntityAttr>().eq(EntityAttr::getAttrType, 3));
                EntityAttr filterEntityAttr = entityAttrMapper.selectList(new LambdaQueryWrapper<EntityAttr>().eq(EntityAttr::getAttrType, 3)).stream().filter(e -> ObjectUtil.isNotEmpty(e.getName()) && e.getName().equals("是否有可转债")).findFirst().get();
                if (filterEntityAttr != null) {
                    attrId = filterEntityAttr.getId();
                } else {
                    EntityAttr entityAttr = new EntityAttr();
                    EntityAttr entityAttrDB = entityAttrsAll.stream().findFirst().get();
                    entityAttr.setAttrCateId(entityAttrDB.getAttrCateId());
                    entityAttr.setAttrCateName(entityAttrDB.getAttrCateName());
                    entityAttr.setName("是否有可转债");
                    attrId = Long.valueOf(entityAttrMapper.insertEntityAttr(entityAttr));
                }
                EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, entityStockCnRel.getEntityCode()).eq(EntityInfo::getStatus, 1));
                if (entityInfo != null) {
                    EntityAttrValue entityAttrValue = entityAttrValueMapper.selectOne(new LambdaQueryWrapper<EntityAttrValue>().eq(EntityAttrValue::getAttrId, attrId).eq(EntityAttrValue::getEntityCode, entityInfo.getEntityCode()));
                    Long finalAttrId = attrId;//Y N =>1 0
                    EntityAttrValue judgeEntityAttrValue = Optional.ofNullable(entityAttrValue).map(e -> e.setValue("1")).orElseGet(() -> EntityAttrValue.builder().value("1").attrId(finalAttrId).entityCode(entityInfo.getEntityCode()).build());
                    if (judgeEntityAttrValue.getId() != null) {
                        entityAttrValueMapper.updateById(judgeEntityAttrValue);
                    } else {
                        entityAttrValueMapper.insertEntityAttrValue(judgeEntityAttrValue);
                    }
                    if (StrUtil.isNotBlank(item.getBondThatName())) {
                        BondInfo bondInfo = bondInfoMapper.selectOne(new LambdaQueryWrapper<BondInfo>().eq(BondInfo::getBondShortName, item.getBondThatName()));
                        if (bondInfo != null) {
                            EntityBondRel judgeEntityBondRel = Optional.ofNullable(entityBondRelMapper
                                    .selectOne(new LambdaQueryWrapper<EntityBondRel>()
                                            .eq(EntityBondRel::getBdCode, bondInfo.getBondCode())
                                            .eq(EntityBondRel::getEntityCode, entityInfo.getEntityCode())))
                                    .map(e -> e.setConver(Boolean.TRUE)).orElseGet(() -> EntityBondRel.builder()
                                            .bdCode(bondInfo.getBondCode())
                                            .conver(Boolean.TRUE)
                                            .entityCode(entityInfo.getEntityCode())
                                            .build());
                            if (judgeEntityBondRel.getId() != null) {
                                entityBondRelMapper.updateEntityBondRel(judgeEntityBondRel);
                            } else {
                                entityBondRelMapper.insertEntityBondRel(judgeEntityBondRel);
                            }
                        }
                    }
                }
            }
            //这条CnDelistInfo 是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            final BondConvertibleChangeInfo bondConvertibleChangeInfo = bondConvertibleChangeInfoMapper.selectOne(new LambdaQueryWrapper<BondConvertibleChangeInfo>().eq(BondConvertibleChangeInfo::getCode, item.getCode()));
            if (bondConvertibleChangeInfo == null) {
                changeType = DataChangeType.INSERT.getId();
            }
            if (!Objects.equals(bondConvertibleChangeInfo, item)) {
                changeType = DataChangeType.UPDATE.getId();
            } else {
                changeType = DataChangeType.INSERT.getId();
            }
            item.setChangeType(changeType);
        }
        bondConvertibleChangeInfoMapper.insert(item);
        return new AsyncResult(new Object());
    }
}
