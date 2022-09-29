package com.deloitte.crm.service;

import java.util.List;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.vo.BondEntityInfoVo;

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

    R checkEntityBondFullName(String fullName);

    R checkEntityBondTradCode(String tradCode);

    R checkEntityBondShortName(String shortName);

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

    /**
     *  查询选择的债券 查询债券的具体信息 by正杰
     * @param bondCode
     * @return
     * @author 正杰
     * @date 2022/9/28
     */
    R<BondEntityInfoVo> findAllDetail(String entityCode, String bondCode);

    /**
     * 修改具体信息 by正杰
     * @param bondInfoEditVo
     * @author 正杰
     * @date 2022/9/28
     */
    R<BondEntityInfoVo> editAllDetail(BondEntityInfoVo bondInfoEditVo);
}
