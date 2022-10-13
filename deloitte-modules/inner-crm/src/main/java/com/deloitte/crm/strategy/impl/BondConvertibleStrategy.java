package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.BondConvertibleInfoService;
import com.deloitte.crm.service.IEntityBondRelService;
import com.deloitte.crm.service.StockCnInfoService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import com.deloitte.crm.utils.ApplicationContextHolder;
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
 * @Date: 2022/10/13/14:11
 * @Description: 可转债发行预案
 */
@Component
public class BondConvertibleStrategy implements WindTaskStrategy {
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
        return Objects.equals(WindTaskEnum.BOND_CONVERTIBLE.getId(), windDictId);
    }

    /**
     * 1.通过attrType=3 企业主体属性 2 - 政府主体属性 3-债券 -4港股 5-a股
     * {@link EntityAttr#attrType } 过滤掉 <是否有可转债/> 如果没有的话则添加数据 到EntityAttr
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(BondConvertibleInfo item, Date timeNow, CrmWindTask windTask) {
        //设置属性
        item.setTaskId(windTask.getId());
        StockCnInfo stockCnInfo = this.stockCnInfoService.findByCode(item.getCode());
        if (stockCnInfo != null) {
            Long attrId = 0L;
            LambdaQueryWrapper<EntityStockCnRel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            EntityStockCnRel entityStockCnRel = entityStockCnRelMapper.selectOne(lambdaQueryWrapper.eq(EntityStockCnRel::getStockDqCode, stockCnInfo.getStockDqCode()).eq(EntityStockCnRel::getStatus, Boolean.TRUE));//TODO  只查询 未删除的 关系状态 1是未删除 0 是已删除
            if (entityStockCnRel != null) {
                List<EntityAttr> entityAttrsLists = entityAttrMapper.selectList(new LambdaQueryWrapper<EntityAttr>().eq(EntityAttr::getAttrType, 3));
                List<EntityAttr> fiterEntityAttrsLists = entityAttrsLists.stream().filter(e -> ObjectUtil.isNotEmpty(e.getName()) && e.getName().equals("是否有可转债")).collect(Collectors.toList());
                if (CollUtil.isEmpty(fiterEntityAttrsLists)) {
                    EntityAttr entityAttr = new EntityAttr();
                    EntityAttr entityAttrDB = entityAttrsLists.stream().findFirst().get();
                    entityAttr.setAttrCateId(entityAttrDB.getAttrCateId());
                    entityAttr.setAttrCateName(entityAttrDB.getAttrCateName());
                    entityAttr.setName("是否有可转债");
                    attrId = Long.valueOf(entityAttrMapper.insertEntityAttr(entityAttr));
                } else {
                    attrId = fiterEntityAttrsLists.get(0).getId();
                }
                LambdaQueryWrapper<EntityInfo> lambdaQueryWrapperEntityInfo = new LambdaQueryWrapper<>();
                EntityInfo entityInfo = entityInfoMapper.selectOne(lambdaQueryWrapperEntityInfo.eq(EntityInfo::getEntityCode, entityStockCnRel.getEntityCode()).eq(EntityInfo::getStatus, 1));//TODO -失效 1-生效
                if (entityInfo != null) {
                    EntityAttrValue entityAttrValue = entityAttrValueMapper.selectOne(new LambdaQueryWrapper<EntityAttrValue>().eq(EntityAttrValue::getAttrId, attrId).eq(EntityAttrValue::getEntityCode, entityInfo.getEntityCode()));
                    if (entityAttrValue != null) {
                        entityAttrValue.setValue("1"); // Y N =>1 0
                    } else {
                        EntityAttrValue insertEntityAttrValue = new EntityAttrValue();
                        insertEntityAttrValue.setValue("1");
                        insertEntityAttrValue.setEntityCode(entityInfo.getEntityCode());
                        insertEntityAttrValue.setAttrId(attrId);
                        entityAttrValueMapper.insertEntityAttrValue(insertEntityAttrValue);
                    }

                    /***
                     * 根据 转债简称 查询 bond_info 如果这一列有数据
                     * 添加entity_bond_info 的关联关系 更新 conver 为ture
                     * {@link com.deloitte.crm.domain.EntityBondRel#conver}
                     */
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
        }
        return new AsyncResult(new Object());

    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<BondConvertibleInfo> util = new ExcelUtil<BondConvertibleInfo>(BondConvertibleInfo.class);
        List<BondConvertibleInfo> bondConvertibleInfo = util.importExcel(windTaskContext.getFileStream(), true);
        return ApplicationContextHolder.get().getBean(BondConvertibleInfoService.class).doTask(windTask,bondConvertibleInfo);
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
