package com.deloitte.crm.service;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.GovLevel;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IGovLevelService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public GovLevel selectGovLevelById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param govLevel 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<GovLevel> selectGovLevelList(GovLevel govLevel);

    /**
     * 新增【请填写功能名称】
     * 
     * @param govLevel 【请填写功能名称】
     * @return 结果
     */
    public int insertGovLevel(GovLevel govLevel);

    /**
     * 修改【请填写功能名称】
     * 
     * @param govLevel 【请填写功能名称】
     * @return 结果
     */
    public int updateGovLevel(GovLevel govLevel);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteGovLevelByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteGovLevelById(Long id);

    /**
     * 获取顶级
     * @return
     */
    R<List<GovLevel>> getGovLevelBig();

    /**
     * 获取子集
     * @param id
     * @return
     */
    R<List<GovLevel>> getGovLevelSmall(Integer id);

    R getGovLevel();
}
