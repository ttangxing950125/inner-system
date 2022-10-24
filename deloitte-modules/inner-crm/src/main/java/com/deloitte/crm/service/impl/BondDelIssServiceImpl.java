package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.BondDelIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.mapper.BondDelIssMapper;
import com.deloitte.crm.service.BondDelIssService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.BondDelIssStrategy;
import com.deloitte.crm.utils.ApplicationContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 新债发行-推迟或取消发行债券(BondDelIss)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 12:11:32
 */
@Service
public class BondDelIssServiceImpl extends ServiceImpl<BondDelIssMapper, BondDelIss> implements BondDelIssService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    /**
     * 完成当前wind文件的任务
     *
     * @param windTask
     * @param delIsses
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<BondDelIss> delIsses) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();

        //入历史记录库
        for (BondDelIss delIss : delIsses) {
            if (StrUtil.isBlank(delIss.getBondShortName())) {
                continue;
            }
            //多线程保存债券信息，更新attrvalue表
            Future<BondInfoDto> future = ApplicationContextHolder.get().getBean(BondDelIssStrategy.class).doBondImport(delIss, timeNow, windTask);

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
     * 根据债券简称查询数据
     *
     * @param shortName
     * @return
     */
    @Override
    public List<BondDelIss> findByBondName(String shortName) {
        LambdaUpdateWrapper<BondDelIss> wrapper = Wrappers.<BondDelIss>lambdaUpdate().eq(BondDelIss::getBondShortName, shortName)
                .orderBy(true, false, BondDelIss::getId).last("LIMIT 1");
        return this.list(wrapper);
    }
}
