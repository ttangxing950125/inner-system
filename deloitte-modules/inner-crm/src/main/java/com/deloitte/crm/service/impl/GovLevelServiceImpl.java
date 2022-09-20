package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.GovLevelMapper;
import com.deloitte.crm.domain.GovLevel;
import com.deloitte.crm.service.IGovLevelService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class GovLevelServiceImpl implements IGovLevelService 
{
    @Autowired
    private GovLevelMapper govLevelMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public GovLevel selectGovLevelById(Long id)
    {
        return govLevelMapper.selectGovLevelById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param govLevel 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<GovLevel> selectGovLevelList(GovLevel govLevel)
    {
        return govLevelMapper.selectGovLevelList(govLevel);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param govLevel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertGovLevel(GovLevel govLevel)
    {
        return govLevelMapper.insertGovLevel(govLevel);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param govLevel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateGovLevel(GovLevel govLevel)
    {
        return govLevelMapper.updateGovLevel(govLevel);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovLevelByIds(Long[] ids)
    {
        return govLevelMapper.deleteGovLevelByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovLevelById(Long id)
    {
        return govLevelMapper.deleteGovLevelById(id);
    }
}
