package com.deloitte.crm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.EntityAttrValue;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityAttrValueService extends IService<EntityAttrValue>
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
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityAttrValueByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityAttrValueById(Long id);

    /**
     * 新增或修改
     * @return
     */
    int insertUpdateCondition(EntityAttrValue attrValue);

    /**
     * 更新entityAttrValue表中债券的相关信息
     * 反射获取obj里的属性，key 为 Excel 注解 的name 属性, value 为实体类的值
     * @return
     */
    int updateBondAttr(String bondCode, Object obj);
}
