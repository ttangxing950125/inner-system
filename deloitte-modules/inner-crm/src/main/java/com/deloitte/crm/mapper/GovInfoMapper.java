package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.GovAttrByDto;
import com.deloitte.crm.dto.GovInfoBynameDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface GovInfoMapper extends BaseMapper<GovInfo>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteGovInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGovInfoByIds(Long[] ids);

    List<GovInfo> selectGovInfoListByTypeAndParam(@Param("type") Integer type,
                                                  @Param("param") String param,
                                                  @Param("pageNum") Integer pageNum,
                                                  @Param("pageSize") Integer pageSize);
    Integer selectGovInfoCountByTypeAndParam(@Param("type") Integer type,
                                             @Param("param") String param);
    List<GovInfo> selectCountByGroup(@Param("param") String param);

    List<GovInfo> getGovByAttrValue(GovAttrByDto govAttrByDto);

    List<GovInfo> getGovByAttrValueByPage(GovAttrByDto govAttrDto);

    Integer getGovCountByAttrValue(GovAttrByDto entityAttrDto);

    List<GovInfoBynameDto> getGovByname(Page<GovInfoBynameDto> page, @Param("govName") String govName);


}
