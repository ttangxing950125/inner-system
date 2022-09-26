package com.deloitte.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.ThkSecIssDetailMapper;
import com.deloitte.crm.domain.ThkSecIssDetail;
import com.deloitte.crm.service.IThkSecIssDetailService;

import javax.annotation.Resource;

/**
 * 证券发行-股票发行-首次发行明细Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class ThkSecIssDetailServiceImpl extends ServiceImpl<ThkSecIssDetailMapper, ThkSecIssDetail> implements IThkSecIssDetailService
{
    @Autowired
    private ThkSecIssDetailMapper thkSecIssDetailMapper;

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    /**
     * 开始执行wind任务  删除证券发行-股票发行-首次发行明细信息
     * @param windTask
     * @param thkSecIssInfos
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<ThkSecIssDetail> thkSecIssInfos) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();


        this.saveBatch(thkSecIssInfos);
        return true;
    }

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
