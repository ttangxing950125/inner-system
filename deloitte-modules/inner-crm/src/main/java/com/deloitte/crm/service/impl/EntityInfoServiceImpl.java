package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.IEntityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityInfoServiceImpl implements IEntityInfoService 
{
    @Autowired
    private EntityInfoMapper entityInfoMapper;

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
    public Page<EntityInfo> getInfoList(@RequestBody EntityInfo entityInfo, Integer pageNum, Integer pageSize) {
        Page<EntityInfo> pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper<>(entityInfo);
        return entityInfoMapper.selectPage(pageInfo, queryWrapper);
    }

    @Override
    public Integer updateInfoList(List<EntityInfo> list) {
        list.stream().forEach(o->entityInfoMapper.updateById(o));
        return list.size();
    }
}
