package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityAttrCate;

import java.util.List;

/**
 * 企业属性分类Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityAttrCateMapper extends BaseMapper<EntityAttrCate>
{
    /**
     * 查询企业属性分类
     * 
     * @param id 企业属性分类主键
     * @return 企业属性分类
     */
    public EntityAttrCate selectEntityAttrCateById(Long id);

    /**
     * 查询企业属性分类列表
     * 
     * @param entityAttrCate 企业属性分类
     * @return 企业属性分类集合
     */
    public List<EntityAttrCate> selectEntityAttrCateList(EntityAttrCate entityAttrCate);

    /**
     * 新增企业属性分类
     * 
     * @param entityAttrCate 企业属性分类
     * @return 结果
     */
    public int insertEntityAttrCate(EntityAttrCate entityAttrCate);

    /**
     * 修改企业属性分类
     * 
     * @param entityAttrCate 企业属性分类
     * @return 结果
     */
    public int updateEntityAttrCate(EntityAttrCate entityAttrCate);

    /**
     * 删除企业属性分类
     * 
     * @param id 企业属性分类主键
     * @return 结果
     */
    public int deleteEntityAttrCateById(Long id);

    /**
     * 批量删除企业属性分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityAttrCateByIds(Long[] ids);
}
