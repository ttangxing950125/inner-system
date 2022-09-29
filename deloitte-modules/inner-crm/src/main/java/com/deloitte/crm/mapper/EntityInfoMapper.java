package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityInfo;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityInfoMapper  extends BaseMapper<EntityInfo>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityInfoByIds(Long[] ids);

    /**
     * 根据名称查询主体，不会查询失效主体
     * @param entityName
     * @return
     */
    List<EntityInfo> findByName(String entityName);

    List<EntityInfo> selectGovInfoListByTypeAndParam(EntityInfo entityInfo);
}
