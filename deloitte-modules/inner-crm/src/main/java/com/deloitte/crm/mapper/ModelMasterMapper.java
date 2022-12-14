package com.deloitte.crm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.ModelMaster;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ModelMasterMapper extends BaseMapper<ModelMaster>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ModelMaster selectModelMasterById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ModelMaster> selectModelMasterList(ModelMaster modelMaster);

    /**
     * 新增【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    public int insertModelMaster(ModelMaster modelMaster);

    /**
     * 修改【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    public int updateModelMaster(ModelMaster modelMaster);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteModelMasterById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteModelMasterByIds(Long[] ids);
}
