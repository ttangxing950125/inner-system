package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoDetails;
import com.deloitte.crm.domain.dto.EntityInfoResult;
import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;
import com.deloitte.crm.dto.ExportEntityCheckDto;
import com.deloitte.crm.vo.EntityInfoVo;
import com.deloitte.crm.vo.TargetEntityBondsVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityInfoService extends IService<EntityInfo> {
    /**
     * 统计企业主体信息
     *
     * @return List<EntityInfoDto>
     * @author penTang
     * @date 2022/9/22 22:40
     */
    EntityInfoDto getEntityInfo();

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
     * @param entityDto
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    public R insertEntityInfo(EntityDto entityDto);

    /**
     * 修改【请填写功能名称】
     *
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityInfo(EntityInfo entityInfo);
    public int updateOrInsertEntityInfoByEntityCode(EntityInfo entityInfo);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityInfoByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityInfoById(Long id);

    R getInfoList(Integer type, String param,Integer pageNum,Integer pageSize);

    Integer updateInfoList(List<EntityInfo> list);

    List<EntityInfo> checkEntity(EntityInfo entityInfo);

    Object getListEntityByPage(EntityAttrByDto entityAttrDto);

    R<EntityInfoVo> validEntity(String creditCode, String entityName);
    /**
     * 根据统一社会信用代码 查询主体信息
     * @param creditCode
     * @return
     */
    EntityInfo getEntityInfoByCreditCode(String creditCode);


    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     *
     * @param creditCode    统一社会信用代码
     * @param entityNewName 主体新名称
     * @param remarks       备注
     * @return 修改返回信息
     * @author 正杰
     * @date 2022/9/22
     */
    R editEntityNameHis(String creditCode, String entityNewName, String remarks);

    /**
     * 根据名称查询主体
     *
     * @param entityName
     * @return
     */
    List<EntityInfo> findByName(String entityName);

    R addOldName(EntityInfo entityInfo);

    R updateOldName(String dqCode, String oldName, String newOldName, String status);

    R getInfoDetailByEntityCode(String entityCode);

    List<EntityInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto);

    /**
     * 导出企业主体excel表格
     *
     * @param entityAttrDto
     * @return void
     * @author penTang
     * @date 2022/9/26 18:24
     */
    void ExportEntityInFor(EntityAttrByDto entityAttrDto);


    /**
     * 根据多个主体code查询
     *
     * @param entityCodes
     * @return
     */
    List<EntityInfo> findListByEntityCodes(List<String> entityCodes);

    /**
     * 查询债卷信息 模糊匹配
     *
     * @param name    entity_name || bond_short_name
     * @param keyword 请传入常量 ENTITY || BOND
     * @return R<List < TargetEntityBondsVo>>
     * @author 正杰
     * @date 2022/9/25
     */
    R <Page<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword, Integer pageNum, Integer pageSize);

    /**
     * 查询债券或是主体下相关的主体或是债券信息 by正杰
     * @param id
     * @param keyword
     * @return
     * @author 正杰
     * @date 2022/9/25
     */
    R<List<TargetEntityBondsVo>> findRelationEntityOrBond(Integer id, String keyword);


    Map<String, Object> getOverview();

    Map<String, Object> getOverviewByGroup();

    Map<String, Object> getOverviewByAll();

    /**
     * 校验统一社会信用代码是否存在 by正杰
     * @author 正杰
     * @date 2022/9/28
     * @param creditCode
     * @return
     */
    R<EntityInfoVo> checkCreditCode(String creditCode);

    /**
     * 校验主体名称是否存在
     * @author 正杰
     * @date 2022/9/28
     * @param entityName
     * @return R
     */
    R<EntityInfoVo> checkEntityName(String entityName);
    /**
     * 覆盖情况快速查询
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return R
     * @author 冉浩岑
     * @date 2022/10/8 15:53
     */
    R getQuickOfCoverage(String param, Integer pageNum,Integer pageSize);


    /**
     *   ****************
     *   *    通用方法   *
     *   ****************
     *
     * 拿到后缀数字
     * @param suffixLength 后缀长度
     * @param target 字符
     */
    Integer getSuffixNumber(Integer suffixLength,String target);

    /**
     *   ****************
     *   *    通用方法   *
     *   ****************
     *
     * 拼接 0
     * @param prefixLength 前缀长度
     * @param target 字符
     * @return
     */
    String appendPrefix(Integer prefixLength,Integer target);

    /**
     *批量查询并导出excel结果
     *
     * @param file
     * @return R
     * @author penTang
     * @date 2022/10/9 16:12
     */
    List<ExportEntityCheckDto> checkBatchEntity(MultipartFile file,String uuid);
    R getIng(String uuid);
    R getExcelWriter(List<ExportEntityCheckDto> entityByBatchLis);

    /**
     *   ****************
     *   *    通用方法   *
     *   ****************
     *
     * 拼接 0
     * @param prefixWord 前缀 拼接的字符
     * @param prefixLength 前缀长度
     * @param target 目标字符
     */
    String appendPrefixDiy(String prefixWord,Integer prefixLength,Integer target);

    void addEntityeMsg(EntityInfo entityInfo);

    void updateInfoDetail(EntityInfoDetails entityInfoDetails);

    /**
     * 根据 id 字段名 修改
     * @param id
     * @param filedName
     * @param value
     */
}
