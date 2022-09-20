package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.ThkSecIssDetailMapper;
import com.deloitte.crm.domain.ThkSecIssDetail;
import com.deloitte.crm.service.IThkSecIssDetailService;

/**
 * 证券发行-股票发行-首次发行明细Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class ThkSecIssDetailServiceImpl implements IThkSecIssDetailService 
{
    @Autowired
    private ThkSecIssDetailMapper thkSecIssDetailMapper;

    /**
     * 查询证券发行-股票发行-首次发行明细
     * 
     * @param id 证券发行-股票发行-首次发行明细主键
     * @return 证券发行-股票发行-首次发行明细
     */
    @Override
    public ThkSecIssDetail selectThkSecIssDetailById(Long id)
    {
        return thkSecIssDetailMapper.selectThkSecIssDetailById(id);
    }

    /**
     * 查询证券发行-股票发行-首次发行明细列表
     * 
     * @param thkSecIssDetail 证券发行-股票发行-首次发行明细
     * @return 证券发行-股票发行-首次发行明细
     */
    @Override
    public List<ThkSecIssDetail> selectThkSecIssDetailList(ThkSecIssDetail thkSecIssDetail)
    {
        return thkSecIssDetailMapper.selectThkSecIssDetailList(thkSecIssDetail);
    }

    /**
     * 新增证券发行-股票发行-首次发行明细
     * 
     * @param thkSecIssDetail 证券发行-股票发行-首次发行明细
     * @return 结果
     */
    @Override
    public int insertThkSecIssDetail(ThkSecIssDetail thkSecIssDetail)
    {
        return thkSecIssDetailMapper.insertThkSecIssDetail(thkSecIssDetail);
    }

    /**
     * 修改证券发行-股票发行-首次发行明细
     * 
     * @param thkSecIssDetail 证券发行-股票发行-首次发行明细
     * @return 结果
     */
    @Override
    public int updateThkSecIssDetail(ThkSecIssDetail thkSecIssDetail)
    {
        return thkSecIssDetailMapper.updateThkSecIssDetail(thkSecIssDetail);
    }

    /**
     * 批量删除证券发行-股票发行-首次发行明细
     * 
     * @param ids 需要删除的证券发行-股票发行-首次发行明细主键
     * @return 结果
     */
    @Override
    public int deleteThkSecIssDetailByIds(Long[] ids)
    {
        return thkSecIssDetailMapper.deleteThkSecIssDetailByIds(ids);
    }

    /**
     * 删除证券发行-股票发行-首次发行明细信息
     * 
     * @param id 证券发行-股票发行-首次发行明细主键
     * @return 结果
     */
    @Override
    public int deleteThkSecIssDetailById(Long id)
    {
        return thkSecIssDetailMapper.deleteThkSecIssDetailById(id);
    }
}
