package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.EntityBondRel;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityBondRelService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityBondRel selectEntityBondRelById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityBondRel 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityBondRel> selectEntityBondRelList(EntityBondRel entityBondRel);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityBondRel 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityBondRel(EntityBondRel entityBondRel);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityBondRel 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityBondRel(EntityBondRel entityBondRel);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityBondRelByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityBondRelById(Long id);
}
