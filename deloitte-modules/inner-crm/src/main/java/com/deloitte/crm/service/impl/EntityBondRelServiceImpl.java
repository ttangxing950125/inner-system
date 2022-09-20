package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.EntityBondRelMapper;
import com.deloitte.crm.domain.EntityBondRel;
import com.deloitte.crm.service.IEntityBondRelService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityBondRelServiceImpl implements IEntityBondRelService 
{
    @Autowired
    private EntityBondRelMapper entityBondRelMapper;

    /**
     * 查询【请填写功能名称】
     * 
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
