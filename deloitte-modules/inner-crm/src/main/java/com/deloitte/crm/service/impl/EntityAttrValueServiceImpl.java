package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.service.IEntityAttrService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.utils.AttrValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityAttrValueServiceImpl extends ServiceImpl<EntityAttrValueMapper, EntityAttrValue> implements IEntityAttrValueService {
    @Autowired
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private IEntityAttrService entityAttrService;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityAttrValue selectEntityAttrValueById(Long id) {
        return entityAttrValueMapper.selectEntityAttrValueById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityAttrValue 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityAttrValue> selectEntityAttrValueList(EntityAttrValue entityAttrValue) {
        return entityAttrValueMapper.selectEntityAttrValueList(entityAttrValue);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityAttrValue(EntityAttrValue entityAttrValue) {
        return entityAttrValueMapper.insertEntityAttrValue(entityAttrValue);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityAttrValue(EntityAttrValue entityAttrValue) {
        return entityAttrValueMapper.updateEntityAttrValue(entityAttrValue);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrValueByIds(Long[] ids) {
        return entityAttrValueMapper.deleteEntityAttrValueByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrValueById(Long id) {
        return entityAttrValueMapper.deleteEntityAttrValueById(id);
    }


    /**
     * 更新entityAttrValue表中债券的相关信息
     * 反射获取obj里的属性，key 为 Excel 注解 的name 属性, value 为实体类的值
     *
     * @param bondCode
     * @param obj
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBondAttr(String bondCode, Object obj) {
        return this.updateAttrValue(bondCode, obj, 3, Excel.class, "name");
    }

    /**
     * 更新entityAttrValue表中债券的相关信息
     *
     * @param entityCode entityAttrValue的entityCode
     * @param obj        反射获取属性的对象
     * @param attrType
     * @param anno
     * @param annoFiled
     * @return
     * @Param anno         anno 中的 annoFiled的值作为entityAttr的name
     * @Param annoFiled
     * @Param entityAttr - attrType
     */
    @Override
    public int updateAttrValue(String entityCode, Object obj, Integer attrType, Class<? extends Annotation> anno, String annoFiled) {
        //更新成功的条数
        int updateCount = 0;

        Map<String, Object> data = new HashMap<>();
        try {
            data = AttrValueUtils.parseObj(obj, anno, annoFiled);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }


        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            Object value = data.get(key);
            if (StrUtil.isBlankCast(value)) {
                continue;
            }
            EntityAttr attr = entityAttrService.findByNameType(key, attrType);
            if (attr == null) {
                continue;
            }
            Long id = attr.getId();

            EntityAttrValue attrValue = new EntityAttrValue();
            attrValue.setEntityCode(entityCode);
            attrValue.setValue(value.toString());
            attrValue.setAttrId(id);
            int upCount = this.insertUpdateCondition(attrValue);
            updateCount += upCount;
        }


        return updateCount;
    }

    /**
     * 更新entityAttrValue表中港股的相关信息
     *
     * @param stockDqCode 港股code
     * @param secIssInfo  ThkSecIssInfo 对象
     * @return
     */
    @Override
    public int updateStockThkAttr(String stockDqCode, Object secIssInfo) {
        return this.updateAttrValue(stockDqCode, secIssInfo, 4, Excel.class, "name");
    }

    /**
     * 更新entityAttrValue表中a股的相关信息
     *
     * @param code a股德勤code
     * @param item a股相关表任意对象
     * @return
     */
    @Override
    public int updateStockCnAttr(String code, Object item) {
        return this.updateAttrValue(code, item, 5, Excel.class, "name");
    }

    @Override
    public Integer addEntityAttrValues(List<EntityAttrValue> valueList) {
        valueList.stream().forEach(o -> {
            QueryWrapper<EntityAttrValue> query = new QueryWrapper<>();
            String attrId = o.getAttrId().toString();
            if (attrId.equals(Common.WHETHER_ATTR_NAME_SW_ATTR_CATE_ID.toString())
                    || attrId.equals(Common.WWHETHER_ATTR_NAME_WIND_ATTR_CATE_ID.toString())) {
                entityAttrValueMapper.update(o, query.lambda().eq(EntityAttrValue::getEntityCode, o.getEntityCode()));
            } else {
                entityAttrValueMapper.insert(o);
            }
        });
        return valueList.size();
    }

    /**
     * 新增或修改
     *
     * @return
     */
    @Override
    public int insertUpdateCondition(EntityAttrValue attrValue) {
        String value = attrValue.getValue();

        EntityAttrValue dbAttrValue = entityAttrValueMapper.findByAttrCode(attrValue);

        if (dbAttrValue == null) {
            return entityAttrValueMapper.insert(attrValue);
        }

        dbAttrValue.setValue(value);

        return entityAttrValueMapper.updateById(dbAttrValue);
    }
}
