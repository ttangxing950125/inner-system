package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.EntityAttr;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityAttrService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityAttr selectEntityAttrById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityAttr 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityAttr> selectEntityAttrList(EntityAttr entityAttr);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityAttr 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityAttr(EntityAttr entityAttr);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityAttr 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityAttr(EntityAttr entityAttr);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityAttrByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityAttrById(Long id);
}
