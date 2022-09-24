package com.deloitte.crm.mapper;

import java.util.List;
import com.deloitte.crm.domain.EntityBondRel;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityBondRelMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityBondRelById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityBondRelByIds(Long[] ids);

    /**
     * 根据债券code和企业code查询是否存在
     * @param entityCode
     * @param bondCode
     * @return
     */
    EntityBondRel findByEntityBondCode(@Param("entityCode") String entityCode,
                                       @Param("bondCode") String bondCode);
}
