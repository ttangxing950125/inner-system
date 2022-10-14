package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityGovRelDto;
import com.deloitte.crm.mapper.EntityGovRelMapper;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import com.deloitte.crm.service.IEntityGovRelService;
import com.deloitte.crm.service.IEntityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityGovRelServiceImpl implements IEntityGovRelService 
{
    @Autowired
    private EntityGovRelMapper entityGovRelMapper;
    @Autowired
    private ICrmSupplyTaskService crmSupplyTaskService;
    @Autowired
    private IEntityInfoService entityInfoService;
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityGovRel selectEntityGovRelById(Long id)
    {
        return entityGovRelMapper.selectEntityGovRelById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityGovRel 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityGovRel> selectEntityGovRelList(EntityGovRel entityGovRel)
    {
        return entityGovRelMapper.selectEntityGovRelList(entityGovRel);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityGovRel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityGovRel(EntityGovRel entityGovRel)
    {
        return entityGovRelMapper.insertEntityGovRel(entityGovRel);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityGovRel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityGovRel(EntityGovRel entityGovRel)
    {
        return entityGovRelMapper.updateEntityGovRel(entityGovRel);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityGovRelByIds(Long[] ids)
    {
        return entityGovRelMapper.deleteEntityGovRelByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityGovRelById(Long id)
    {
        return entityGovRelMapper.deleteEntityGovRelById(id);
    }

    @Override
    public Long getEntityGovCount(String dqCode) {
        QueryWrapper<EntityGovRel>govRelQuery=new QueryWrapper<>();
        return entityGovRelMapper.selectCount(govRelQuery.lambda().eq(EntityGovRel::getDqGovCode,dqCode));
    }

    /**
     * 城投机构根据entityCode补充录入副表信息
     *
     * @param entityGovRelDto
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:56
     */
    @Override
    @Transactional
    public void addGovEntitySubtableMsg(EntityGovRelDto entityGovRelDto) {
        crmSupplyTaskService.completeTaskById(entityGovRelDto.getId());
        EntityGovRel entityGovRel = entityGovRelDto.getEntityGovRel();
        QueryWrapper<EntityGovRel> govQuery = new QueryWrapper<>();
        int update = entityGovRelMapper.update(entityGovRel, govQuery.lambda().eq(EntityGovRel::getEntityCode, entityGovRel.getEntityCode()));
        if (update<1){
            entityGovRelMapper.insert(entityGovRel);
        }
        EntityInfo entityInfo = entityGovRelDto.getEntityInfo();
        entityInfoService.updateOrInsertEntityInfoByEntityCode(entityInfo);
    }

}
