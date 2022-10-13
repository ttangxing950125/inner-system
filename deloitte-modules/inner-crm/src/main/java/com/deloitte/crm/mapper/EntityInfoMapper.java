package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface EntityInfoMapper  extends BaseMapper<EntityInfo>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public EntityInfo selectEntityInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertEntityInfo(EntityInfo entityInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateEntityInfo(EntityInfo entityInfo);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteEntityInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntityInfoByIds(Long[] ids);

    /**
     * 根据名称查询主体，不会查询失效主体
     * @param entityName
     * @return
     */
    List<EntityInfo> findByName(String entityName);

    List<EntityInfo> selectGovInfoListByTypeAndParam(@Param("type")Integer type,
                                                     @Param("param")String param,
                                                     @Param("pageNum")Integer pageNum,
                                                     @Param("pageSize")Integer pageSize);

    List<EntityInfo> getEntityByBondType(@Param("raiseType") Integer raiseType,
                                         @Param("abs")Integer abs,
                                         @Param("coll")Integer coll);

    Integer getEntityCountByBondType(@Param("raiseType") Integer raiseType,
                                     @Param("abs")Integer abs,
                                     @Param("coll")Integer coll);

    List<EntityInfo> getEntityByBondTypeByPage(@Param("raiseType") Integer raiseType,
                                               @Param("abs")Integer abs,
                                               @Param("coll")Integer coll,
                                               @Param("pageNum")Integer pageNum,
                                               @Param("pageSize")Integer pageSize);

    /**
     * 通过 credit_code 的前缀 查询主体
     * @param prefix
     * @return
     */
    EntityInfo findLastOneByPrefixCredit(@Param("prefix") String prefix);

    /**
     * 通过 id 字段名 修改数据
     * @param id
     * @param filedName
     * @param value
     */
    void editByBondInfoManager(@Param("id") Integer id, @Param("filedName") String filedName, @Param("value") String value);
}
