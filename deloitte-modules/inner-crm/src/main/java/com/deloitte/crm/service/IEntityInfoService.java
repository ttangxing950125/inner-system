package com.deloitte.crm.service;

import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityAttrDto;
import com.deloitte.crm.domain.dto.EntityInfoDto;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityInfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityInfo selectEntityInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityInfo(EntityInfo entityInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityInfo(EntityInfo entityInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityInfoByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityInfoById(Long id);
    /**
     *添加方法描述
     *
     * @param  entityInfo
     * @return int
     * @author 冉浩岑
     * @date 2022/9/22 14:14
    */
    AjaxResult getInfoList(EntityInfoDto entityInfo);

    Integer updateInfoList(List<EntityInfo> list);

    List<EntityInfo> checkList(EntityInfo entityInfo);

    AjaxResult getOneAllInfo(String entityCode);

    AjaxResult getListEntityByPage(EntityAttrDto entityAttrDto);
}
