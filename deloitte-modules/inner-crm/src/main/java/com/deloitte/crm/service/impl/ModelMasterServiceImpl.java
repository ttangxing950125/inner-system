package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.ModelMasterMapper;
import com.deloitte.crm.domain.ModelMaster;
import com.deloitte.crm.service.IModelMasterService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class ModelMasterServiceImpl implements IModelMasterService 
{
    @Autowired
    private ModelMasterMapper modelMasterMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ModelMaster selectModelMasterById(Long id)
    {
        return modelMasterMapper.selectModelMasterById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ModelMaster> selectModelMasterList(ModelMaster modelMaster)
    {
        return modelMasterMapper.selectModelMasterList(modelMaster);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertModelMaster(ModelMaster modelMaster)
    {
        return modelMasterMapper.insertModelMaster(modelMaster);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateModelMaster(ModelMaster modelMaster)
    {
        return modelMasterMapper.updateModelMaster(modelMaster);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterByIds(Long[] ids)
    {
        return modelMasterMapper.deleteModelMasterByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterById(Long id)
    {
        return modelMasterMapper.deleteModelMasterById(id);
    }
}
