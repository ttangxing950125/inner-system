package com.deloitte.crm.service;

import com.deloitte.crm.domain.EntityNameHis;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityNameHisService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityNameHis selectEntityNameHisById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityNameHis> selectEntityNameHisList(EntityNameHis entityNameHis);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityNameHis(EntityNameHis entityNameHis);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityNameHis(EntityNameHis entityNameHis);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityNameHisByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityNameHisById(Long id);
    /**
     * 根据德勤code查询曾用名列表
     *
     * @param dqCode
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 23:50
     */
    List<EntityNameHis> getNameListByDqCoded(String dqCode);
}
