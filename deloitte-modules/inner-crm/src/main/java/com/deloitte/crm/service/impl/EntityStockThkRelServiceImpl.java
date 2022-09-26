package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.EntityStockThkRelMapper;
import com.deloitte.crm.service.EntityStockThkRelService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 绑定港股和主体的关联关系
     * @param stockThkInfo
     * @param entityName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindRelOrCreateTask(StockThkInfo stockThkInfo, String entityName,
                                       CrmWindTask windTask, ThkSecIssInfo secIssInfo) {
        //根据名称查询主体
        List<EntityInfo> entityInfos = entityInfoService.findByName(entityName);

        //之前数据库中没有该主体
        if (CollUtil.isEmpty(entityInfos)){
            //创建任务
            CrmEntityTask entityTask = new CrmEntityTask();

            entityTask.setTaskCategory(windTask.getTaskCategory());
            entityTask.setDataSource(2);
            entityTask.setDataCode(stockThkInfo.getStockDqCode());

            entityTask.setSourceType(2);
            entityTask.setSourceId(secIssInfo.getId());
            entityTask.setTaskDate(windTask.getTaskDate());
            String showData = "公司中文名称:"+entityName;
            showData += ", 证券代码:"+secIssInfo.getCode()+", 债券简称:"+secIssInfo.getName();

            entityTask.setDataShow(showData);

            entityTaskService.createTask(entityTask);

            return true;
        }

        //是否绑定过关联关系
        //是否绑定过关联关系
        for (EntityInfo info : entityInfos) {
            String entityCode = info.getEntityCode();
            String stockDqCode = stockThkInfo.getStockDqCode();

            //查询关联关系
            EntityStockThkRel dbRel = this.findByEntityStockDeCode(entityCode, stockDqCode);
            if (dbRel!=null){
                continue;
            }

            //新增关联关系
            EntityStockThkRel stockThkRel = new EntityStockThkRel();
            stockThkRel.setEntityCode(entityCode);
            stockThkRel.setStockDqCode(stockDqCode);
            this.save(stockThkRel);

        }

        return true;
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
}
