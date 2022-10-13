package com.deloitte.crm.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.dto.EntityAttrDetailDto;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-23
 */
public interface BondInfoMapper extends BaseMapper<BondInfo>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteBondInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBondInfoByIds(Long[] ids);


    BondInfo findByShortName(@Param("shortName") String shortName ,@Param("isDeleted") Boolean isDeleted );

    /**
     * 单表查询并封装进对象
     * @param bondCode
     * @return
     */
    EntityAttrDetailDto findByQualName(String bondCode);

    /**
     * 根据债券code查询
     * @param dqBondCode
     * @return
     */
    BondInfo findByDqCode(String dqBondCode);

    /**
     * 通过 id 字段名 修改数据
     * @param id
     * @param filedName
     * @param value
     */
    void editByBondInfoManager(@Param("id") Integer id, @Param("filedName") String filedName, @Param("value") String value);

    void editByBondInfoManagerDate(@Param("id") Integer id, @Param("filedName") String filedName, @Param("date") Date date);
}
