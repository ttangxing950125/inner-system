package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.GovInfoDto;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IGovInfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public GovInfo selectGovInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param govInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<GovInfo> selectGovInfoList(GovInfo govInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertGovInfo(GovInfo govInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateGovInfo(GovInfo govInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteGovInfoByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteGovInfoById(Long id);

    Page<GovInfo> getInfoList(GovInfo govInfo, Integer pageNum, Integer pageSize);

    Integer updateInfoList(List<GovInfo> list);

    AjaxResult getNewInfo(GovInfo govInfo);

    AjaxResult getInfoList(GovInfoDto govInfo);

    AjaxResult updateOldName(GovInfo govInfo);

    AjaxResult checkList(GovInfo govInfo);
}
