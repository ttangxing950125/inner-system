package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.EntityStockCnRelMapper;
import com.deloitte.crm.service.EntityStockCnRelService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (EntityStockCnRel)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:36
 */
@Service("entityStockCnRelService")
public class EntityStockCnRelServiceImpl extends ServiceImpl<EntityStockCnRelMapper, EntityStockCnRel> implements EntityStockCnRelService {

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    private ICrmEntityTaskService entityTaskService;

    /**
     * 绑定主体和a股的关联关系，如果没有这个企业，就创建新增企业的任务
     * @param stockCnInfo
     * @param entityName
     * @param windTask
     * @param cnCoachBack
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindRelOrCreateTask(StockCnInfo stockCnInfo, String entityName, CrmWindTask windTask, CnCoachBack cnCoachBack) {
        //根据名称查询主体
        List<EntityInfo> entityInfos = entityInfoService.findByName(entityName);

        //之前数据库中没有该主体
        if (CollUtil.isEmpty(entityInfos)){
            //创建任务
            CrmEntityTask entityTask = new CrmEntityTask();

            entityTask.setTaskCategory(windTask.getTaskCategory());
            entityTask.setDataSource(3);
            entityTask.setDataCode(stockCnInfo.getStockDqCode());

            entityTask.setSourceType(3);
            entityTask.setSourceId(stockCnInfo.getId());
            entityTask.setTaskDate(windTask.getTaskDate());
            String showData = "公司中文名称:"+entityName;
            showData += ", 代码:"+cnCoachBack.getCode();

            entityTask.setDataShow(showData);

            entityTaskService.createTask(entityTask);

            return true;
        }


        for (EntityInfo info : entityInfos) {
            String entityCode = info.getEntityCode();
            String stockDqCode = stockCnInfo.getStockDqCode();

            //查询关联关系
            EntityStockCnRel dbRel = baseMapper.findByEntityStockDeCode(entityCode, stockDqCode);

            if (dbRel!=null){
                continue;
            }

            //新增关联
            //新增关联关系
            EntityStockCnRel cnRel = new EntityStockCnRel();
            cnRel.setEntityCode(entityCode);
            cnRel.setStockDqCode(stockDqCode);
            this.save(cnRel);
        }


        return true;
    }
}
