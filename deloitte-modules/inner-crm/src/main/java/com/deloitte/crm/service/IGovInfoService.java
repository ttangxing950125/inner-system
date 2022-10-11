package com.deloitte.crm.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.GovAttrByDto;
import com.deloitte.crm.domain.dto.GovAttrByDtoBack;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.vo.EntityOrGovByAttrVo;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IGovInfoService  extends IService<GovInfo>
{

    /**
     * 统计政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     *
     */
    GovInfoDto getGovInfo();

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

    R updateInfoList(List<GovInfo> list);

    R getInfoDetail(GovInfo govInfo);

    R getInfoList(Integer type,String param);

    R addOldName(GovInfo govInfo);

    R checkGov(GovInfo govInfo);

    Object getListEntityByPage(GovAttrByDto govAttrDto);

    void ExportEntityGov(GovAttrByDto govAttrByDto);

    R updateOldName(String dqCode, String oldName, String newOldName, String status);


    Map<String, Object> getOverview();

    Map<String, Object> getOverviewByGroup();

    List<GovInfo> getGovLevel(String preGovCode);


    /**
     * 获取上级地方政府行政编码 by正杰
     * @param govCode
     * @return
     */
    R<String> getPreGovName(String govCode);

    R  getGovEntityResult(EntityOrGovByAttrVo entityOrGovByAttrVo);

    GovAttrByDtoBack getGovRange();
}
