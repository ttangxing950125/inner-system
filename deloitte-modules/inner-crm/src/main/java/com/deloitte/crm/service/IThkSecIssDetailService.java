package com.deloitte.crm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ThkSecIssDetail;

/**
 * 证券发行-股票发行-首次发行明细Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IThkSecIssDetailService extends IService<ThkSecIssDetail>
{
    /**
     * 查询证券发行-股票发行-首次发行明细
     * 
     * @param id 证券发行-股票发行-首次发行明细主键
     * @return 证券发行-股票发行-首次发行明细
     */
    public ThkSecIssDetail selectThkSecIssDetailById(Long id);

    /**
     * 查询证券发行-股票发行-首次发行明细列表
     * 
     * @param thkSecIssDetail 证券发行-股票发行-首次发行明细
     * @return 证券发行-股票发行-首次发行明细集合
     */
    public List<ThkSecIssDetail> selectThkSecIssDetailList(ThkSecIssDetail thkSecIssDetail);

    /**
     * 新增证券发行-股票发行-首次发行明细
     * 
     * @param thkSecIssDetail 证券发行-股票发行-首次发行明细
     * @return 结果
     */
    public int insertThkSecIssDetail(ThkSecIssDetail thkSecIssDetail);

    /**
     * 修改证券发行-股票发行-首次发行明细
     * 
     * @param thkSecIssDetail 证券发行-股票发行-首次发行明细
     * @return 结果
     */
    public int updateThkSecIssDetail(ThkSecIssDetail thkSecIssDetail);

    /**
     * 批量删除证券发行-股票发行-首次发行明细
     * 
     * @param ids 需要删除的证券发行-股票发行-首次发行明细主键集合
     * @return 结果
     */
    public int deleteThkSecIssDetailByIds(Long[] ids);

    /**
     * 删除证券发行-股票发行-首次发行明细信息
     * 
     * @param id 证券发行-股票发行-首次发行明细主键
     * @return 结果
     */
    public int deleteThkSecIssDetailById(Long id);

    /**
     * 开始执行wind任务  删除证券发行-股票发行-首次发行明细信息
     * @param windTask
     * @param thkSecIssInfos
     * @return
     */
    Object doTask(CrmWindTask windTask, List<ThkSecIssDetail> thkSecIssInfos);

    /**
     * 根据code查询表中最后一条记录
     * @param code
     * @return
     */
    ThkSecIssDetail findLastByCode(String code);
}
