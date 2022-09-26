package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoByDto;
import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityInfoService extends IService<EntityInfo>
{
    /**
     *
     *统计企业主体信息
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
     * 新增【请填写功能名称】
     * 
     * @param entityDto 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityInfo(EntityDto entityDto);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityInfo(EntityInfo entityInfo);

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
    /**
     *添加方法描述
     *
     * @param  entityInfo
     * @return int
     * @author 冉浩岑
     * @date 2022/9/22 14:14
    */
    R getInfoList(EntityInfoByDto entityInfo);

    Integer updateInfoList(List<EntityInfo> list);

    List<EntityInfo> checkEntity(EntityInfo entityInfo);

    Object getListEntityByPage(EntityAttrByDto entityAttrDto);

    /**
     * 传入社会信用代码于企业名称
     *  => 存在该社会信用代码 返回 比较信息为 false
     *     ==> 前端跳转调用人工对比信息，并确认
     *
     *  => 不存在社会信用代码 但存在相同企业名称 返回 比较信息 false
     *     ==> 前端跳转调用人工对比信息，并确认
     *
     *  => 不存在社会信用代码 也不存在相同企业名称 返回 比较信息 true
     *     ==> 确认新增主体 生成企业主体德勤代码、统一社会信用代码相关字段
     *
     * @author 正杰
     * @date 2022/9/22
     * @param creditCode 传入 企业统一社会信用代码
     * @param entityName 传入 企业名称
     * @return 比较信息结果
     */
    AjaxResult validEntity(String creditCode, String entityName);

    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     * @author 正杰
     * @date 2022/9/22
     * @param creditCode 统一社会信用代码
     * @param entityNewName 主体新名称
     * @param remarks 备注
     * @return 修改返回信息
     */
    AjaxResult editEntityNameHis(String creditCode, String entityNewName,String remarks);

    /**
     * 根据名称查询主体
     * @param entityName
     * @return
     */
    List<EntityInfo> findByName(String entityName);

    R addOldName(EntityInfo entityInfo);

    R updateOldName(String dqCode, String oldName, String newOldName, String status);

    R getInfoDetail(EntityInfo entityInfo);
}
