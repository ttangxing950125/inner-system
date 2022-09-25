package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityAttrMapper extends BaseMapper<EntityAttr>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityAttrById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityAttrByIds(Long[] ids);

    /**
     * 根据名称和属性分类查询
     * @param name
     * @param attrType
     * @return
     */
    EntityAttr findByNameType(@Param("name") String name,
                              @Param("attrType") int attrType);
}
