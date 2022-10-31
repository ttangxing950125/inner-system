package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.EntityBondRelMapper;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityBondRelService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.utils.AttrValueUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityBondRelServiceImpl implements IEntityBondRelService
{
    @Resource
    private EntityBondRelMapper entityBondRelMapper;

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    private ICrmEntityTaskService entityTaskService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private EntityCaptureSpeedService entityCaptureSpeedService;

    /**
     * 绑定指定主体和债券的关系
     * 如果没有这个主体，就生成今天的任务
     * @param issorName 发行人全称
     * @param bondInfo
     * @param newIss
     * @return
     */
    @Override
    public boolean bindRelOrCreateTask(String issorName, BondInfo bondInfo, BondNewIss newIss, CrmWindTask windTask) {
        //没有发行人名称不处理
        if (StrUtil.isBlank(issorName)){
            return false;
        }

        //查询主体
        List<EntityInfo> entityInfos = entityInfoService.findByName(issorName);
        //没有主体就创建任务
        if (CollUtil.isEmpty(entityInfos)){
            CrmEntityTask entityTask = new CrmEntityTask();
            entityTask.setTaskCategory(windTask.getTaskCategory());
            entityTask.setDataSource(1);
            entityTask.setDataCode(bondInfo.getBondCode());
            entityTask.setSourceType(1);
            entityTask.setSourceId(newIss.getId());
            entityTask.setTaskDate(windTask.getTaskDate());
            String showData = "发行人全称:"+issorName;
            showData += ", 交易代码:"+newIss.getTradeCode()+", 债券简称:"+newIss.getBondShortName();

            entityTask.setDataShow(showData);

            //infoMap
            HashMap<String, Object> infoMap = new LinkedHashMap<>();
            infoMap.put("发行人全称", issorName);
            infoMap.put("债券简称", bondInfo.getBondShortName());
            infoMap.put("债券全称", bondInfo.getBondName());
            infoMap.put("交易代码", bondInfo.getOriCode());


            try {
                Map<String, Object> data = AttrValueUtils.parseObj(newIss, Excel.class, "name");

                entityTask.setInfos(objectMapper.writeValueAsString(infoMap));
                entityTask.setDetails(objectMapper.writeValueAsString(data));

                entityTask.setEntityName(issorName);

                entityTaskService.createTask(entityTask);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //是否绑定过关联关系
        for (EntityInfo info : entityInfos) {
            String entityCode = info.getEntityCode();
            String bondCode = bondInfo.getBondCode();

            //查询关联关系
            EntityBondRel dbRel = entityBondRelMapper.findByEntityBondCode(entityCode, bondCode);
            if (dbRel!=null){
                continue;
            }

            //新增关联关系
            EntityBondRel saveRel = new EntityBondRel();
            saveRel.setBdCode(bondCode);
            saveRel.setEntityCode(entityCode);
            entityBondRelMapper.insertEntityBondRel(saveRel);

        }


        return true;
    }

    /**
     * 查询【请填写功能名称】
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityBondRel selectEntityBondRelById(Long id)
    {
        return entityBondRelMapper.selectEntityBondRelById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityBondRel 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityBondRel> selectEntityBondRelList(EntityBondRel entityBondRel)
    {
        return entityBondRelMapper.selectEntityBondRelList(entityBondRel);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param entityBondRel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityBondRel(EntityBondRel entityBondRel)
    {
        return entityBondRelMapper.insertEntityBondRel(entityBondRel);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityBondRel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityBondRel(EntityBondRel entityBondRel)
    {
        return entityBondRelMapper.updateEntityBondRel(entityBondRel);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityBondRelByIds(Long[] ids)
    {
        return entityBondRelMapper.deleteEntityBondRelByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityBondRelById(Long id)
    {
        return entityBondRelMapper.deleteEntityBondRelById(id);
    }

}
