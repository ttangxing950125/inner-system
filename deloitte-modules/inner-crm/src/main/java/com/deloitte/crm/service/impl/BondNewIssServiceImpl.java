package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.BondNewIssMapper;
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.service.IBondNewIssService;

/**
 * 新债发行-新发行债券-20220801-20220914Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class BondNewIssServiceImpl implements IBondNewIssService 
{
    @Autowired
    private BondNewIssMapper bondNewIssMapper;

    /**
     * 查询新债发行-新发行债券-20220801-20220914
     * 
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 新债发行-新发行债券-20220801-20220914
     */
    @Override
    public BondNewIss selectBondNewIssById(Long id)
    {
        return bondNewIssMapper.selectBondNewIssById(id);
    }

    /**
     * 查询新债发行-新发行债券-20220801-20220914列表
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 新债发行-新发行债券-20220801-20220914
     */
    @Override
    public List<BondNewIss> selectBondNewIssList(BondNewIss bondNewIss)
    {
        return bondNewIssMapper.selectBondNewIssList(bondNewIss);
    }

    /**
     * 新增新债发行-新发行债券-20220801-20220914
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    @Override
    public int insertBondNewIss(BondNewIss bondNewIss)
    {
        return bondNewIssMapper.insertBondNewIss(bondNewIss);
    }

    /**
     * 修改新债发行-新发行债券-20220801-20220914
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    @Override
    public int updateBondNewIss(BondNewIss bondNewIss)
    {
        return bondNewIssMapper.updateBondNewIss(bondNewIss);
    }

    /**
     * 批量删除新债发行-新发行债券-20220801-20220914
     * 
     * @param ids 需要删除的新债发行-新发行债券-20220801-20220914主键
     * @return 结果
     */
    @Override
    public int deleteBondNewIssByIds(Long[] ids)
    {
        return bondNewIssMapper.deleteBondNewIssByIds(ids);
    }

    /**
     * 删除新债发行-新发行债券-20220801-20220914信息
     * 
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 结果
     */
    @Override
    public int deleteBondNewIssById(Long id)
    {
        return bondNewIssMapper.deleteBondNewIssById(id);
    }
}
