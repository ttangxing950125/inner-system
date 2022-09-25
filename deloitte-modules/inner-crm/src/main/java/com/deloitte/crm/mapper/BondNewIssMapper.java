package com.deloitte.crm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.BondNewIss;

/**
 * 新债发行-新发行债券-20220801-20220914Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface BondNewIssMapper extends BaseMapper<BondNewIss>
{
    /**
     * 查询新债发行-新发行债券-20220801-20220914
     * 
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 新债发行-新发行债券-20220801-20220914
     */
    public BondNewIss selectBondNewIssById(Long id);

    /**
     * 查询新债发行-新发行债券-20220801-20220914列表
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 新债发行-新发行债券-20220801-20220914集合
     */
    public List<BondNewIss> selectBondNewIssList(BondNewIss bondNewIss);

    /**
     * 新增新债发行-新发行债券-20220801-20220914
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    public int insertBondNewIss(BondNewIss bondNewIss);

    /**
     * 修改新债发行-新发行债券-20220801-20220914
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    public int updateBondNewIss(BondNewIss bondNewIss);

    /**
     * 删除新债发行-新发行债券-20220801-20220914
     * 
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 结果
     */
    public int deleteBondNewIssById(Long id);

    /**
     * 批量删除新债发行-新发行债券-20220801-20220914
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBondNewIssByIds(Long[] ids);


    BondNewIss findByShortName(String shortName);
}
