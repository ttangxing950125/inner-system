package com.deloitte.crm.mapper;

import java.util.List;
import com.deloitte.crm.domain.EntityAttrValue;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityAttrValueMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityAttrValue selectEntityAttrValueById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityAttrValue> selectEntityAttrValueList(EntityAttrValue entityAttrValue);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityAttrValue(EntityAttrValue entityAttrValue);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityAttrValue(EntityAttrValue entityAttrValue);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityAttrValueById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityAttrValueByIds(Long[] ids);
}
