package com.deloitte.crm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityInfoDto;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityInfoService extends IService<EntityInfo>
{
    EntityInfoDto getEntityInfo();

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
}
