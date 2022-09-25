package com.deloitte.crm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.mapper.EntityAttrMapper;
import com.deloitte.crm.service.IEntityAttrService;
import com.deloitte.crm.utils.AttrValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.service.IEntityAttrValueService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityAttrValueServiceImpl extends ServiceImpl<EntityAttrValueMapper, EntityAttrValue> implements IEntityAttrValueService
{
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
    public EntityAttrValue selectEntityAttrValueById(Long id)
    {
        return entityAttrValueMapper.selectEntityAttrValueById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityAttrValue> selectEntityAttrValueList(EntityAttrValue entityAttrValue)
    {
        return entityAttrValueMapper.selectEntityAttrValueList(entityAttrValue);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityAttrValue(EntityAttrValue entityAttrValue)
    {
        return entityAttrValueMapper.insertEntityAttrValue(entityAttrValue);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityAttrValue(EntityAttrValue entityAttrValue)
    {
        return entityAttrValueMapper.updateEntityAttrValue(entityAttrValue);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrValueByIds(Long[] ids)
    {
        return entityAttrValueMapper.deleteEntityAttrValueByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrValueById(Long id)
    {
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
        //更新成功的条数
        int updateCount = 0;

        Map<String, Object> data = null;
        try {
            data = AttrValueUtils.parseObj(obj, Excel.class, "name");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }


//        ArrayList<EntityAttrValue> saveList = new ArrayList<>();
//        ArrayList<EntityAttrValue> updateList = new ArrayList<>();

        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            Object value = data.get(key);
            if (StrUtil.isBlankCast(value)){
                continue;
            }
            EntityAttr attr = entityAttrService.findByNameType(key, 3);
            if (attr==null){
                continue;
            }
            Long id = attr.getId();

            EntityAttrValue attrValue = new EntityAttrValue();
            attrValue.setEntityCode(bondCode);
            attrValue.setValue(value.toString());
            attrValue.setAttrId(id);

            /*EntityAttrValue dbAttrValue = entityAttrValueMapper.findByAttrCode(attrValue);
            if (dbAttrValue==null){
                //没有这条记录，新增
                saveList.add(attrValue);
            }else {
                //修改
                attrValue.setId(dbAttrValue.getId());
                updateList.add(attrValue);
            }*/

            int upCount = this.insertUpdateCondition(attrValue);
            updateCount += upCount;
        }


        return updateCount;
    }

    /**
     * 新增或修改
     * @return
     */
    @Override
    public int insertUpdateCondition(EntityAttrValue attrValue) {
        String value = attrValue.getValue();

        EntityAttrValue dbAttrValue = entityAttrValueMapper.findByAttrCode(attrValue);

        if (dbAttrValue==null){
            return entityAttrValueMapper.insert(attrValue);
        }

        dbAttrValue.setValue(value);

        return entityAttrValueMapper.updateById(dbAttrValue);
    }
}
