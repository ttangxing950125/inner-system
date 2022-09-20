package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.EntityGovRelMapper;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.service.IEntityGovRelService;

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
}
