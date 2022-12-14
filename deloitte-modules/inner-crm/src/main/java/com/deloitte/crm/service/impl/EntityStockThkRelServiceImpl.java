package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.EntityBaseBusiInfoMapper;
import com.deloitte.crm.mapper.EntityStockThkRelMapper;
import com.deloitte.crm.service.EntityStockThkRelService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.utils.ApplicationContextHolder;
import com.deloitte.crm.utils.AttrValueUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (EntityStockThkRel)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:18
 */
@Service("entityStockThkRelService")
public class EntityStockThkRelServiceImpl extends ServiceImpl<EntityStockThkRelMapper, EntityStockThkRel> implements EntityStockThkRelService {

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    private ICrmEntityTaskService entityTaskService;

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private EntityStockThkRelMapper stockThkRelMapper;
    /**
     * 绑定港股和主体的关联关系
     *
     * @param stockThkInfo
     * @param entityName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindRelOrCreateTask(StockThkInfo stockThkInfo, String entityName,
                                       CrmWindTask windTask, ThkSecIssInfo secIssInfo) {

        entityName = entityName.trim().replace("（","(").replace("）",")");

        //根据名称查询主体
        List<EntityInfo> entityInfos = entityInfoService.findByNameOrOldName(entityName);

        //之前数据库中没有该主体
        if (CollUtil.isEmpty(entityInfos)) {
            //创建任务
            CrmEntityTask entityTask = new CrmEntityTask();

            entityTask.setTaskCategory(windTask.getTaskCategory());
            entityTask.setDataSource(2);
            entityTask.setDataCode(stockThkInfo.getStockDqCode());

            entityTask.setSourceType(2);
            entityTask.setSourceId(secIssInfo.getId());
            entityTask.setTaskDate(windTask.getTaskDate());
            String showData = "公司中文名称:" + entityName;
            showData += ", 证券代码:" + secIssInfo.getCode() + ", 证券简称:" + secIssInfo.getName();

            entityTask.setDataShow(showData);

            //infoMap
            HashMap<String, Object> infoMap = new HashMap<>();
            infoMap.put("证券代码", stockThkInfo.getStockCode());
            infoMap.put("证券简称", stockThkInfo.getStockName());
            infoMap.put("公司中文名称", entityName);


            try {
                Map<String, Object> data = AttrValueUtils.parseObj(secIssInfo, Excel.class, "name");
                entityTask.setWindMaster(secIssInfo.getWindIndustry());
                entityTask.setInfos(objectMapper.writeValueAsString(infoMap));
                entityTask.setDetails(objectMapper.writeValueAsString(data));


                entityTask.setEntityName(entityName);

                entityTaskService.createTask(entityTask);
            } catch (Exception e) {
                log.error("创建Task出现异常>>>>>:{}", e);
            }

        } else {
            //是否绑定过关联关系
            for (EntityInfo info : entityInfos) {
                String entityCode = info.getEntityCode();
                String stockDqCode = stockThkInfo.getStockDqCode();
                //查询关联关系
                EntityBaseBusiInfo entityBaseBusiInfo = finEntityBaseBusiInfoByEntityCode(entityCode);
                if (entityBaseBusiInfo != null) {
                    entityBaseBusiInfo.setEstablishDate(StringUtils.isEmpty(secIssInfo.getCreateDate()) ? null : DateUtil.parseDate(formatDateByString(secIssInfo.getCreateDate())));
                    entityBaseBusiInfo.setRegAddr(secIssInfo.getRegisterAddress());
                    entityBaseBusiInfo.setEntityStaffCount(Optional.ofNullable(secIssInfo.getEntityEmpCount() + "").orElse(null));
                    entityBaseBusiInfo.setBusRange(Optional.ofNullable(secIssInfo.getEntityScope()).orElse(null));
                    ApplicationContextHolder.get().getBean(EntityBaseBusiInfoMapper.class).updateById(entityBaseBusiInfo);
                } else {
                    EntityBaseBusiInfo info1 = new EntityBaseBusiInfo();
                    info1.setEstablishDate(StringUtils.isEmpty(secIssInfo.getCreateDate()) ? null : DateUtil.parseDate(formatDateByString(secIssInfo.getCreateDate())));
                    info1.setRegAddr(secIssInfo.getRegisterAddress());
                    info1.setEntityStaffCount(Optional.ofNullable(secIssInfo.getEntityEmpCount() + "").orElse(null));
                    info1.setBusRange(Optional.ofNullable(secIssInfo.getEntityScope()).orElse(null));
                    info1.setEntityCode(entityCode);
                    ApplicationContextHolder.get().getBean(EntityBaseBusiInfoMapper.class).insert(info1);
                }
                EntityStockThkRel dbRel = this.findByEntityStockDeCode(entityCode, stockDqCode);
                if (dbRel != null) {
                    continue;
                }
                //新增关联关系
                EntityStockThkRel stockThkRel = new EntityStockThkRel();
                stockThkRel.setEntityCode(entityCode);
                stockThkRel.setStockDqCode(stockDqCode);
                this.save(stockThkRel);
            }
        }
        return true;
    }

    public EntityBaseBusiInfo finEntityBaseBusiInfoByEntityCode(String entityCode) {
        return ApplicationContextHolder.get().getBean(EntityBaseBusiInfoMapper.class).selectOne(new LambdaQueryWrapper<EntityBaseBusiInfo>().eq(EntityBaseBusiInfo::getEntityCode, entityCode));
    }

    /**
     * 查询是否存在主体和港股的关联关系
     *
     * @param entityCode
     * @param stockDqCode
     * @return
     */
    @Override
    public EntityStockThkRel findByEntityStockDeCode(String entityCode, String stockDqCode) {
        return baseMapper.findByEntityStockDeCode(entityCode, stockDqCode);
    }

    /**
     * 查询港股所属主体，只查询关联关系启用状态的
     *
     * @param stockDqCode
     * @return
     */
    @Override
    public List<EntityInfo> findEntityByStockDqCode(String stockDqCode) {
        ArrayList<EntityInfo> infos = new ArrayList<>();

        //查询关联关系表
        Wrapper<EntityStockThkRel> wrapper = Wrappers.<EntityStockThkRel>lambdaQuery()
                .eq(EntityStockThkRel::getStockDqCode, stockDqCode)
                .eq(EntityStockThkRel::getStatus, 1);

        List<EntityStockThkRel> thkRels = this.list(wrapper);

        if (CollUtil.isEmpty(thkRels)) {
            return infos;
        }

        //取出code
        List<String> entityCodes = thkRels.stream().map(EntityStockThkRel::getEntityCode).collect(Collectors.toList());

        //查询主体
        return entityInfoService.findListByEntityCodes(entityCodes);
    }

    @Override
    public List<EntityStockThkRel> selectEntityStockThkRelListByBondCodes(List<String> stockThkCodes) {
        return stockThkRelMapper.selectList(new QueryWrapper<EntityStockThkRel>().lambda().in(EntityStockThkRel::getStockDqCode,stockThkCodes));
    }

    private String formatDateByString(String value) {
        if (value.length() == 8) {
            return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
        } else if (value.length() == 6) {
            return value.substring(0, 4) + "-" + value.substring(4, 6);
        } else {
            return value;
        }
    }
}
