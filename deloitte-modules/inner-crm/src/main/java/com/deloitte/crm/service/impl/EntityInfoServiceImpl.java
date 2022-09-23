package com.deloitte.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.dto.EntityAttrDto;
import com.deloitte.crm.domain.dto.EntityInfoDto;
import com.deloitte.crm.mapper.EntityAttrMapper;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.service.IEntityInfoService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@Aspect
public class EntityInfoServiceImpl implements IEntityInfoService 
{
    @Autowired
    private EntityInfoMapper entityInfoMapper;

    @Autowired
    private EntityNameHisMapper nameHisMapper;

    @Autowired
    private EntityAttrMapper entityAttrMapper;

    @Autowired
    private EntityAttrValueMapper entityAttrValueMapper;
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityInfo selectEntityInfoById(Long id)
    {
        return entityInfoMapper.selectEntityInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo)
    {
        return entityInfoMapper.selectEntityInfoList(entityInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityInfo(EntityInfo entityInfo)
    {
        return entityInfoMapper.insertEntityInfo(entityInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityInfo(EntityInfo entityInfo)
    {
        return entityInfoMapper.updateEntityInfo(entityInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoByIds(Long[] ids)
    {
        return entityInfoMapper.deleteEntityInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoById(Long id)
    {
        return entityInfoMapper.deleteEntityInfoById(id);
    }

    @Override
    public AjaxResult getInfoList(EntityInfoDto entityInfoDto) {
        entityInfoDto.setEntityInfo();
        EntityInfo entityInfo = entityInfoDto.getEntityInfo();
        Integer pageNum = entityInfoDto.getPageNum();
        Integer pageSize = entityInfoDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)){
            return AjaxResult.error("请输入页码");
        }
        if (ObjectUtils.isEmpty(pageSize)){
            pageSize=EntityInfoUtil.DEFAULT_PAGE_SIZE;
        }
        Page<EntityInfo> pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper<>(entityInfo);

        Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //创建结果集
        Page<Map<String, Object>> pageResult=new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());
        //封装结果集
        List<Map<String,Object>> records = new ArrayList<>();
        entityInfoPage.getRecords().stream().forEach(o-> {
            records.add(getResultMap(o));
        });
        pageResult.setRecords(records);
        return AjaxResult.success(pageResult);
    }

    @Override
    public Integer updateInfoList(List<EntityInfo> list) {
        list.stream().forEach(o->entityInfoMapper.updateById(o));
        return list.size();
    }

    @Override
    public List<EntityInfo> checkList(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper(entityInfo);
        return entityInfoMapper.selectList(queryWrapper);
    }

    @Override
    public AjaxResult getOneAllInfo(String entityCode) {
        try {
            QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper();
            //企业主体基本信息
            EntityInfo entityInfo = entityInfoMapper.selectOne(queryWrapper.lambda().eq(EntityInfo::getEntityCode, entityCode));
            //企业主体全量信息
            QueryWrapper<EntityAttrValue>valueQuery=new QueryWrapper();
            Long attrId = entityAttrValueMapper.selectOne(valueQuery.lambda().eq(EntityAttrValue::getEntityCode, entityCode)).getAttrId();
            EntityAttr entityAttr = entityAttrMapper.selectById(attrId);

            //封装结果集
            Map<String,Object>resultMap=new HashMap<>();
            resultMap.put("entityInfo", entityInfo);
            resultMap.put("entityAttr",entityAttr );
            return AjaxResult.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @Override
    public AjaxResult getListEntityByPage(EntityAttrDto entityAttrDto) {
        return null;
    }

    /**
     * EntityInfo 对象转 map,并查询 曾用名条数
     *
     * @param entityInfo
     * @return Map<String,Object>
     * @author 冉浩岑
     * @date 2022/9/22 23:45
     */
    public Map<String, Object> getResultMap(EntityInfo entityInfo) {
        Map<String, Object> resultMap = new HashMap();
        if (null != entityInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(entityInfo), new TypeReference<Map<String, String>>() {
            });
            try {
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                Long count = nameHisMapper.selectCount(wrapper.lambda().eq(EntityNameHis::getDqCode, entityInfo.getEntityCode()));
                resultMap.put(EntityInfoUtil.NAME_USED_NUM, count);
            }catch (Exception e){
                return resultMap;
            }
        }
        return resultMap;
    }
}
class EntityInfoUtil{
    /***
     * 默认页面条数
     */
    public static final Integer DEFAULT_PAGE_SIZE=9;
    /***
     * 曾用名
     */
    public static final String NAME_USED_NUM="nameUsedNum";
    /***
     * 曾用名分隔符
     */
    public static final String NAME_USED_SIGN=",";
    /***
     * 曾用名备注分隔符
     */
    public static final String NAME_USED_REMARK_SIGN=";";
}