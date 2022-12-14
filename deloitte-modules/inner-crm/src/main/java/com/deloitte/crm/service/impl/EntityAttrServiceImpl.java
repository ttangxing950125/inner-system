package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntitySupplyBack;
import com.deloitte.crm.mapper.EntityAttrMapper;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.IEntityAttrService;
import com.deloitte.crm.vo.NameValueVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@Slf4j
public class EntityAttrServiceImpl extends ServiceImpl<EntityAttrMapper, EntityAttr> implements IEntityAttrService {
    @Autowired
    private EntityAttrMapper entityAttrMapper;

    @Autowired
    private EntityAttrValueMapper valueMapper;

    @Resource
    private RedisService redisService;

    @Autowired
    private EntityInfoMapper entityInfoMapper;

    /**
     * 缓存全部数据
     *
     * @return
     */
    @Override
    public boolean cacheAll() {
        List<EntityAttr> list = this.list();
        redisService.deleteObject(CacheName.ENTITY_ATTR);

        Map<String, EntityAttr> attrMap = list.stream()
                .collect(Collectors.toMap(item -> item.getName() + "::" + item.getAttrType()
                        , Function.identity()));

        redisService.redisTemplate.opsForHash().putAll(CacheName.ENTITY_ATTR, attrMap);

        return true;
    }

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityAttr selectEntityAttrById(Long id) {
        return entityAttrMapper.selectEntityAttrById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityAttr 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityAttr> selectEntityAttrList(EntityAttr entityAttr) {
        return entityAttrMapper.selectEntityAttrList(entityAttr);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param entityAttr 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityAttr(EntityAttr entityAttr) {
        return entityAttrMapper.insertEntityAttr(entityAttr);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityAttr 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityAttr(EntityAttr entityAttr) {
        return entityAttrMapper.updateEntityAttr(entityAttr);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrByIds(Long[] ids) {
        return entityAttrMapper.deleteEntityAttrByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrById(Long id) {
        return entityAttrMapper.deleteEntityAttrById(id);
    }

    @Override
    public R getAllByGroup(Integer type) {
        String name= Common.ENTITY;
        if (!ObjectUtils.isEmpty(type)&&2==type){
            name= Common.GOV;
        }
        log.info("  >>>> 获取 "+name+" 主体父子级指标清单  <<<<  ");
        QueryWrapper<EntityAttr> query = new QueryWrapper<>();
        List<EntityAttr> entityAttrs = entityAttrMapper.selectList((query.lambda().eq(EntityAttr::getAttrType, type)));

        Map<String, List<EntityAttr>> listMap = entityAttrs.stream().collect(Collectors.groupingBy(EntityAttr::getAttrCateName));
        List<NameValueVo> result = new ArrayList<>();
        for (String key : listMap.keySet()) {
            NameValueVo nameValueVo = new NameValueVo();
            nameValueVo.setName(key);
            nameValueVo.setValue(listMap.get(key));
            result.add(nameValueVo);
        }
        return R.ok(result);
    }

    /**
     * 根据名称和属性分类查询
     *
     * @param name
     * @param attrType
     * @return
     */
    @Override
    public EntityAttr findByNameType(String name, int attrType) {
        EntityAttr entityAttr = redisService.getCacheMapValue(CacheName.ENTITY_ATTR, name + "::" + attrType);
        if (entityAttr == null) {
            entityAttr = entityAttrMapper.findByNameType(name, attrType);
            redisService.setCacheMapValue(CacheName.ENTITY_ATTR, name + "::" + attrType, entityAttr);
        }

        return entityAttr;
    }

    @Override
    public List<EntityAttr> getAttrByDqCode(String dqCode) {
        log.info("  >>>> 根据主体dqCode查询详细信息,dqCode=[{}]  <<<<  ",dqCode);
        QueryWrapper<EntityAttrValue> valueQuery = new QueryWrapper<>();
        List<EntityAttrValue> attrValues = valueMapper.selectList(valueQuery.lambda().eq(EntityAttrValue::getEntityCode, dqCode));
        if (CollectionUtils.isEmpty(attrValues)) {
            return null;
        }
        List<EntityAttr> result = new ArrayList<>();
        attrValues.stream().forEach(o -> result.add(entityAttrMapper.selectById(o.getAttrId())));

        //TODO 需要分组返回
        return result;
    }

    /**
     * 获取属性信息
     *
     * @param entityCode
     * @return List<EntityAttrValue>
     * @author 冉浩岑
     * @date 2022/9/28 9:38
     */
    public List<EntityAttrValue> getAttrValue(String entityCode) {
        QueryWrapper<EntityAttrValue> valueQuery = new QueryWrapper<>();
        return valueMapper.selectList(valueQuery.lambda().eq(EntityAttrValue::getEntityCode, entityCode));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R getTaskByEntityCode(String entityCode, Integer roleId) {
        log.info("  >>>> 根据任务id查询补充信息,entityCode=[{}],roleId=[{}] <<<<  ",entityCode,roleId);
        //封装企业返回信息
        EntitySupplyBack entitySupplyBack = new EntitySupplyBack();

        //获取企业基础信息
        QueryWrapper<EntityInfo> entityuInfoQuery = new QueryWrapper<>();
        EntityInfo entityInfo = entityInfoMapper.selectOne(entityuInfoQuery.lambda().eq(EntityInfo::getEntityCode, entityCode).last(" limit 1"));
        entitySupplyBack.setEntityInfo(entityInfo);

        //获取属性信息
        List<EntityAttr> entityAttrs = entityAttrMapper.selectList(new QueryWrapper<>());
        //获取属性值信息
        List<EntityAttrValue> attrByDqCode = getAttrValue(entityCode);

        entitySupplyBack.setRoleId(roleId)
                .setEntityAttrList(entityAttrs)
                .setAttrValueList(attrByDqCode);

        return R.ok(entitySupplyBack);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<EntityAttr>> getAttrByOrganName(String organName) {
        log.info("  >>>> 根据机构类型查询需要补充录入的信息,organName=[{}] <<<<  ",organName);
        QueryWrapper<EntityAttr> query = new QueryWrapper<>();
        List<EntityAttr> entityAttrs = entityAttrMapper.selectList(query.lambda().eq(EntityAttr::getAttrCateName, organName));
        Map<String, List<EntityAttr>> collectMap=new HashMap<>();
        if (CollectionUtils.isEmpty(entityAttrs)) {
            return collectMap;
        }
        collectMap = entityAttrs.stream().collect(Collectors.groupingBy(EntityAttr::getName));

        return collectMap;
    }
}