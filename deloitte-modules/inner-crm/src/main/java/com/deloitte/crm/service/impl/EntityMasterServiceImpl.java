package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.EntityMasterMapper;
import com.deloitte.crm.domain.EntityMaster;
import com.deloitte.crm.service.IEntityMasterService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityMasterServiceImpl implements IEntityMasterService 
{
    @Autowired
    private EntityMasterMapper entityMasterMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityMaster selectEntityMasterById(Long id)
    {
        return entityMasterMapper.selectEntityMasterById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityMaster 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityMaster> selectEntityMasterList(EntityMaster entityMaster)
    {
        return entityMasterMapper.selectEntityMasterList(entityMaster);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityMaster(EntityMaster entityMaster)
    {
        return entityMasterMapper.insertEntityMaster(entityMaster);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityMaster(EntityMaster entityMaster)
    {
        return entityMasterMapper.updateEntityMaster(entityMaster);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityMasterByIds(Long[] ids)
    {
        return entityMasterMapper.deleteEntityMasterByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityMasterById(Long id)
    {
        return entityMasterMapper.deleteEntityMasterById(id);
    }
}
