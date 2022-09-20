package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.EntityAttrMapper;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.service.IEntityAttrService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityAttrServiceImpl implements IEntityAttrService 
{
    @Autowired
    private EntityAttrMapper entityAttrMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityAttr selectEntityAttrById(Long id)
    {
        return entityAttrMapper.selectEntityAttrById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityAttr 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityAttr> selectEntityAttrList(EntityAttr entityAttr)
    {
        return entityAttrMapper.selectEntityAttrList(entityAttr);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityAttr 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityAttr(EntityAttr entityAttr)
    {
        return entityAttrMapper.insertEntityAttr(entityAttr);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityAttr 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityAttr(EntityAttr entityAttr)
    {
        return entityAttrMapper.updateEntityAttr(entityAttr);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrByIds(Long[] ids)
    {
        return entityAttrMapper.deleteEntityAttrByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrById(Long id)
    {
        return entityAttrMapper.deleteEntityAttrById(id);
    }
}
