package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoDetails;
import com.deloitte.crm.domain.dto.EntityInfoResult;
import com.deloitte.crm.domain.dto.EntityListView;
import com.deloitte.crm.dto.*;
import com.deloitte.crm.vo.EntityInfoVo;
import com.deloitte.crm.vo.EntitySupplyMsgBack;
import com.deloitte.crm.vo.TargetEntityBondsVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    public long updateEntityInfoByEntityCodeWithOutId(EntityInfo entityInfo);

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

    /**
     * 根据统一社会信用代码 查询主体信息
     * @param creditCode
     * @return
     */
    EntityInfo getEntityInfoByCreditCode(String creditCode);


    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     * @author 正杰
     * @date 2022/9/22
     * @param entityCode 德勤code
     * @param entityNewName 主体新名称
     * @return 修改返回信息
     */
    R editEntityNameHis(String entityCode, String entityNewName);

    /**
     * 根据名称查询主体
     *
     * @param entityName
     * @return
     */
    List<EntityInfo> findByName(String entityName);

    R addOldName(EntityInfo entityInfo);

    R updateOldName(String dqCode, String oldName, String newOldName, String status,String remarks);

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


    R getExcelWriter(List<ExportEntityCheckDto> entityByBatchLis,ImportDto importDto);

    List<ExportEntityCheckDto> checkBatchEntity(MultipartFile file, ImportDto importDto);

    R getIng(String uuid);


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

    R addEntityeMsg(EntitySupplyMsgBack entitySupplyMsgBack);

    void updateInfoDetail(EntityInfoDetails entityInfoDetails);

    EntityListView getListView(Integer type);



    /**
     *根据名称查询主体的相关信息(产品客户划分情况)
     *
     * @param name
     * @return List<EntityInfo>
     * @author penTang
     * @date 2022/10/17 9:53
     */
    List<EntityInfo> selectEntityInfoListByName(String name);


    EntityInfoCodeDto selectEntityDto(String code);
    /**
     * 角色3，4，5补充录入查询和回显
     *
     * @param id
     * @return R
     * @author 冉浩岑
     * @date 2022/10/24 11:13
     */
    R getEntityBackSupply(Integer id);

    /**
     * 校验 统一社会信用代码，主体名称
     * @author 正杰
     * @param creditCode
     * @param entityName
     * @return
     */
    R<EntityInfoVo> validateCodeAndName(String creditCode, String entityName);

    /**
     * 修改库中主体的统一社会信用代码 by正杰
     * @param entityCode
     * @param creditCode
     * @return
     */
    R editeCreditCode(String entityCode, String creditCode);

    /**
     * 新增主体 并绑定关联债券||股票信息
     * @param entityInfoInsertDTO
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    R insertEntityInfoByDaily(EntityInfoInsertDTO entityInfoInsertDTO);

    void exportEntity(HttpServletResponse response);

    void exportEntityByType(Integer type, HttpServletResponse response);
}
