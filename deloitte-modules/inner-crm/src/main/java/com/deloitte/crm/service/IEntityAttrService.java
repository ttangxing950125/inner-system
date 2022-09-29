package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityAttr;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityAttrService extends IService<EntityAttr>
{

    /**
     * 缓存全部数据
     * @return
     */
    public boolean cacheAll();

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

    R getAllByGroup(Integer type);

    /**
     * 根据名称和属性分类查询
     * @param name
     * @param attrType
     * @return
     */
    EntityAttr findByNameType(String name, int attrType);

    List<EntityAttr> getAttrByDqCode(String dqCode);

    R getTaskByEntityCode(String entityCode, Integer roleId);

    List<EntityAttr> getAttrByOrganName(String organName);
}
