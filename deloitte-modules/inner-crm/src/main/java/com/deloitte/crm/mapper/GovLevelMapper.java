package com.deloitte.crm.mapper;

import java.util.List;
import com.deloitte.crm.domain.GovLevel;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface GovLevelMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteGovLevelById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGovLevelByIds(Long[] ids);
}
