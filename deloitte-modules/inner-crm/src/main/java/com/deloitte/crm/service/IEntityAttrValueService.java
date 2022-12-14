package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.vo.EntityByIondVo;
import com.deloitte.crm.vo.EntityStockInfoVo;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityAttrValueService extends IService<EntityAttrValue>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityAttrValue selectEntityAttrValueById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityAttrValue> selectEntityAttrValueList(EntityAttrValue entityAttrValue);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityAttrValue(EntityAttrValue entityAttrValue);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityAttrValue(EntityAttrValue entityAttrValue);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteEntityAttrValueByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityAttrValueById(Long id);

    /**
     * 新增或修改
     * @return
     */
    int insertUpdateCondition(EntityAttrValue attrValue);

    /**
     * 更新entityAttrValue表中债券的相关信息
     * 反射获取obj里的属性，key 为 Excel 注解 的name 属性, value 为实体类的值
     * @return
     */
    int updateBondAttr(String bondCode, Object obj);

    /**
     * 更新entityAttrValue表中债券的相关信息
     * @param entityCode entityAttrValue的entityCode
     * @param obj 反射获取属性的对象
     * @Param anno         anno 中的 annoFiled的值作为entityAttr的name
     * @Param annoFiled
     * @Param entityAttr - attrType
     * @return
     */
    int updateAttrValue(String entityCode,
                        Object obj,
                        Integer attrType,
                        Class<? extends Annotation> anno,
                        String annoFiled);

    /**
     * 更新entityAttrValue表中港股的相关信息
     * @param stockDqCode 港股code
     * @param secIssInfo ThkSecIssInfo 对象
     * @return
     */
    int updateStockThkAttr(String stockDqCode, Object secIssInfo);

    /**
     * 更新entityAttrValue表中a股的相关信息
     * @param code a股德勤code
     * @param item a股相关表任意对象
     * @return
     */
    int updateStockCnAttr(String code, Object item);

    R createBondEntity(EntityByIondVo entityByIondVo);

    R createStockEntity (EntityStockInfoVo entityStockInfoVo);

    R createStockEntityG(EntityStockInfoVo entityStockInfoVo);

    /**
     *
     *   ****************
     *   *    通用方法   *
     *   ****************
     *
     * 查询 attr&attr_value 的泛用查询
     * @param entityCode
     * @param attrId
     * @return
     */
    Map<String,AttrValueMapDto> findAttrValue(String entityCode,Integer attrId);

    /**
     *
     *   ****************
     *   *    通用方法   *
     *   ****************
     *
     * @param entityCode
     * @param attrValueMapDto
     * @return
     */
    Boolean saveAttrValue(String entityCode,AttrValueMapDto attrValueMapDto);

    /**
     * 批量导入附表数据至 entity_attr_value by 正杰
     *
     * @param entityCode
     * @param attrId
     * @param value
     */
    void runBatchToAttrValue(String entityCode,Integer attrId,String value);

}
