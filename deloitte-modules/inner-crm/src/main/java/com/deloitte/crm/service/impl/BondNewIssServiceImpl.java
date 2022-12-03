package com.deloitte.crm.service.impl;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.impl.BondNewIssueStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.BondNewIssMapper;
import com.deloitte.crm.domain.BondNewIss;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 新债发行-新发行债券-20220801-20220914Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class BondNewIssServiceImpl extends ServiceImpl<BondNewIssMapper, BondNewIss> implements IBondNewIssService {
    @Resource
    private BondNewIssMapper bondNewIssMapper;

    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Resource
    private IEntityBondRelService bondRelService;

    @Resource
    private ICrmMasTaskService crmMasTaskService;

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    @Lazy
    private BondNewIssueStrategy bondNewIssueStrategy;

    private List<String> excludeBondTypeSecond = Arrays.asList("国债", "地方政府债");


    /**
     * 导入债券任务
     *
     * @param windTask
     * @param isses
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<BondNewIss> isses) throws Exception {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);


        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();

        //入历史记录库
        for (BondNewIss newIss : isses) {
            if (StrUtil.isBlank(newIss.getBondName())) {
                continue;
            }
            //Wind债券类型(二级) 国债和地方政府债不考虑
            if (excludeBondTypeSecond.contains(newIss.getWindBondTypeSecond())) {
                newIss.setTaskId(windTask.getId());
                newIss.setImportTime(new Date());
                bondNewIssMapper.insert(newIss);
                continue;
            }


            //多线程保存债券信息，更新attrvalue表
            Future<BondInfoDto> future = bondNewIssueStrategy.doBondImport(newIss, timeNow, windTask);

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
     * 根据taskId查询
     *
     * @param taskId
     * @return
     */
    @Override
    public List<BondNewIss> findByTaskIdChangeType(Integer taskId, Integer... changeType) {
        LambdaUpdateWrapper<BondNewIss> wrapper = Wrappers.<BondNewIss>lambdaUpdate()
                .eq(BondNewIss::getTaskId, taskId)
                .in(BondNewIss::getChangeType, changeType);


        return list(wrapper);
    }


    /**
     * 查询新债发行-新发行债券-20220801-20220914
     *
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 新债发行-新发行债券-20220801-20220914
     */
    @Override
    public BondNewIss selectBondNewIssById(Long id) {
        return bondNewIssMapper.selectBondNewIssById(id);
    }

    /**
     * 查询新债发行-新发行债券-20220801-20220914列表
     *
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 新债发行-新发行债券-20220801-20220914
     */
    @Override
    public List<BondNewIss> selectBondNewIssList(BondNewIss bondNewIss) {
        return bondNewIssMapper.selectBondNewIssList(bondNewIss);
    }

    /**
     * 新增新债发行-新发行债券-20220801-20220914
     *
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    @Override
    public int insertBondNewIss(BondNewIss bondNewIss) {
        return bondNewIssMapper.insertBondNewIss(bondNewIss);
    }

    /**
     * 修改新债发行-新发行债券-20220801-20220914
     *
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    @Override
    public int updateBondNewIss(BondNewIss bondNewIss) {
        return bondNewIssMapper.updateBondNewIss(bondNewIss);
    }

    /**
     * 批量删除新债发行-新发行债券-20220801-20220914
     *
     * @param ids 需要删除的新债发行-新发行债券-20220801-20220914主键
     * @return 结果
     */
    @Override
    public int deleteBondNewIssByIds(Long[] ids) {
        return bondNewIssMapper.deleteBondNewIssByIds(ids);
    }

    /**
     * 删除新债发行-新发行债券-20220801-20220914信息
     *
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 结果
     */
    @Override
    public int deleteBondNewIssById(Long id) {
        return bondNewIssMapper.deleteBondNewIssById(id);
    }

}
