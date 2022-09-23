package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.service.IEntityNameHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityNameHisServiceImpl implements IEntityNameHisService 
{
    @Resource
    private EntityNameHisMapper entityNameHisMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityNameHis selectEntityNameHisById(Long id)
    {
        return entityNameHisMapper.selectEntityNameHisById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityNameHis> selectEntityNameHisList(EntityNameHis entityNameHis)
    {
        return entityNameHisMapper.selectEntityNameHisList(entityNameHis);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityNameHis(EntityNameHis entityNameHis)
    {
        return entityNameHisMapper.insertEntityNameHis(entityNameHis);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityNameHis(EntityNameHis entityNameHis)
    {
        return entityNameHisMapper.updateEntityNameHis(entityNameHis);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityNameHisByIds(Long[] ids)
    {
        return entityNameHisMapper.deleteEntityNameHisByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityNameHisById(Long id)
    {
        return entityNameHisMapper.deleteEntityNameHisById(id);
    }

    @Override
    public List<EntityNameHis> getNameListByDqCoded(String dqCode) {
        QueryWrapper<EntityNameHis>queryWrapper=new QueryWrapper<>();
        return entityNameHisMapper.selectList(
                queryWrapper.lambda()
                        .eq(EntityNameHis::getDqCode,dqCode));
    }
}
