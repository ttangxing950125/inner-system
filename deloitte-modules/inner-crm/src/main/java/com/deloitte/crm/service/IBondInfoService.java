package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.BondInfo;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-23
 */
public interface IBondInfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public BondInfo selectBondInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param bondInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<BondInfo> selectBondInfoList(BondInfo bondInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param bondInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertBondInfo(BondInfo bondInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param bondInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateBondInfo(BondInfo bondInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteBondInfoByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteBondInfoById(Long id);

    /**
     * 根据债券简称查询
     * @param shortName
     * @return
     */
    BondInfo findByShortName(String shortName);

    /**
     * 根据id保存或新增
     * @param bondInfo
     * @return
     */
    BondInfo saveOrUpdate(BondInfo bondInfo);

}
