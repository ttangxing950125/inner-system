package com.deloitte.crm.mapper;

import java.util.List;
import com.deloitte.crm.domain.EntityGovRel;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityGovRelMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityGovRelById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityGovRelByIds(Long[] ids);
}
