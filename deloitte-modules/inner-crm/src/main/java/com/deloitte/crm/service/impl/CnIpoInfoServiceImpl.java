package com.deloitte.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CnIecSmpcCheckResult;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.CnIpoInfoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CnIpoInfoMapper;
import com.deloitte.crm.domain.CnIpoInfo;
import com.deloitte.crm.service.ICnIpoInfoService;

import javax.annotation.Resource;

/**
 * IPO-新股发行资料-20210914-20221014Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CnIpoInfoServiceImpl extends ServiceImpl<CnIpoInfoMapper, CnIpoInfo> implements ICnIpoInfoService
{
    @Autowired
    private CnIpoInfoMapper cnIpoInfoMapper;

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Lazy
    @Resource
    private CnIpoInfoStrategy cnIpoInfoStrategy;

    /**
     * 导入完成IPO-新股发行资料 的任务
     * @param windTask
     * @param list
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<CnIpoInfo> list) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();


        for (CnIpoInfo item : list) {
            String code = item.getCode();
            if (code.contains("数据来源：Wind")){
                continue;
            }

            //执行每一行
            Future<Object> future = cnIpoInfoStrategy.doThkStockImport(item, timeNow, windTask);
            futureList.add(future);
        }


        while (true) {
            boolean isAllDone = true;
            for (Future future : futureList) {
                if (null == future || !future.isDone()) {
                    isAllDone = false;
                }
            }
            if (isAllDone) {
                break;
            }
        }


        //修改原任务状态
        windTask.setComplete(1);
        windTask.setHandleUser(SecurityUtils.getUserId().intValue());
        crmWindTaskService.updateById(windTask);

        //如果今天的windTask全部为已完成，修改crm_daily_task的状态
        crmWindTaskService.checkAllComplete(timeNow);

        return true;
    }

    /**
     * 查询IPO-新股发行资料-20210914-20221014
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return IPO-新股发行资料-20210914-20221014
     */
    @Override
    public CnIpoInfo selectCnIpoInfoById(Long id)
    {
        return cnIpoInfoMapper.selectCnIpoInfoById(id);
    }

    /**
     * 查询IPO-新股发行资料-20210914-20221014列表
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return IPO-新股发行资料-20210914-20221014
     */
    @Override
    public List<CnIpoInfo> selectCnIpoInfoList(CnIpoInfo cnIpoInfo)
    {
        return cnIpoInfoMapper.selectCnIpoInfoList(cnIpoInfo);
    }

    /**
     * 新增IPO-新股发行资料-20210914-20221014
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return 结果
     */
    @Override
    public int insertCnIpoInfo(CnIpoInfo cnIpoInfo)
    {
        return cnIpoInfoMapper.insertCnIpoInfo(cnIpoInfo);
    }

    /**
     * 修改IPO-新股发行资料-20210914-20221014
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return 结果
     */
    @Override
    public int updateCnIpoInfo(CnIpoInfo cnIpoInfo)
    {
        return cnIpoInfoMapper.updateCnIpoInfo(cnIpoInfo);
    }

    /**
     * 批量删除IPO-新股发行资料-20210914-20221014
     * 
     * @param ids 需要删除的IPO-新股发行资料-20210914-20221014主键
     * @return 结果
     */
    @Override
    public int deleteCnIpoInfoByIds(Long[] ids)
    {
        return cnIpoInfoMapper.deleteCnIpoInfoByIds(ids);
    }

    /**
     * 删除IPO-新股发行资料-20210914-20221014信息
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return 结果
     */
    @Override
    public int deleteCnIpoInfoById(Long id)
    {
        return cnIpoInfoMapper.deleteCnIpoInfoById(id);
    }

    /**
     * 根据code查询最后一个  IPO-新股发行资料
     * @param code
     * @return
     */
    @Override
    public CnIpoInfo findLastByCode(String code) {

        return cnIpoInfoMapper.findLastByCode(code);
    }

}
