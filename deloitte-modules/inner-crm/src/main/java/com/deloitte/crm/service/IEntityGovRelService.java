package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.EntityGovRel;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityGovRelService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityGovRel selectEntityGovRelById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityGovRel 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityGovRel> selectEntityGovRelList(EntityGovRel entityGovRel);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityGovRel 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityGovRel(EntityGovRel entityGovRel);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityGovRel 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityGovRel(EntityGovRel entityGovRel);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityGovRelByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityGovRelById(Long id);
}
