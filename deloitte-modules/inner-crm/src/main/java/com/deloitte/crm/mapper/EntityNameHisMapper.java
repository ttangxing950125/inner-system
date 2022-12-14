package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityNameHis;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityNameHisMapper extends BaseMapper<EntityNameHis>
{


    List<EntityNameHis> findByOldName(String oldName);

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityNameHis selectEntityNameHisById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityNameHis> selectEntityNameHisList(EntityNameHis entityNameHis);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityNameHis(EntityNameHis entityNameHis);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityNameHis(EntityNameHis entityNameHis);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityNameHisById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityNameHisByIds(Long[] ids);

    Page<Map<String, Object>> getGovHisNameList(Page<Map<String, Object>>page, @Param("param") String param);

    Page<Map<String, Object>> getEntityHisNameList(Page<Map<String, Object>>page, @Param("param") String param);
}
