package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CnCoachBack;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.CnCheckDeclareMapper;
import com.deloitte.crm.domain.CnCheckDeclare;
import com.deloitte.crm.service.CnCheckDeclareService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.CnCheckDeclareStrategy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * IPO-审核申报(CnCheckDeclare)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:47
 */
@Service("cnCheckDeclareService")
public class CnCheckDeclareServiceImpl extends ServiceImpl<CnCheckDeclareMapper, CnCheckDeclare> implements CnCheckDeclareService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Lazy
    @Resource
    private CnCheckDeclareStrategy cnCheckDeclareStrategy;

    /**
     * IPO-审核申报(CnCheckDeclare)执行导入任务
     * @param windTask
     * @param list
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<CnCheckDeclare> list) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();


        for (CnCheckDeclare item : list) {
            String code = item.getCode();
            if (StrUtil.isEmpty(item.getEntityName()) || code.contains("数据来源：Wind")){
                continue;
            }

            //执行每一行
            Future<Object> future = cnCheckDeclareStrategy.doThkStockImport(item, timeNow, windTask);
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
     * 根据企业名查询 IPO-审核申报(CnCheckDeclare) 最后一条记录
     * @param entityName
     * @return
     */
    @Override
    public CnCheckDeclare findLastByEntityName(String entityName) {
        return baseMapper.findLastByEntityName(entityName);
    }
}
