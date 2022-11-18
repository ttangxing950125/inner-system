package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CnApprdWaitIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.CnApprdWaitIssMapper;
import com.deloitte.crm.domain.CnApprdWaitIss;
import com.deloitte.crm.service.CnApprdWaitIssService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.CnApprdWaitIssStrategy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
@Service("cnApprdWaitIssService")
public class CnApprdWaitIssServiceImpl extends ServiceImpl<CnApprdWaitIssMapper, CnApprdWaitIss> implements CnApprdWaitIssService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Lazy
    @Resource
    private CnApprdWaitIssStrategy cnApprdWaitIssStrategy;

    /**
     * 查询IPO-审核通过尚未发行(CnApprdWaitIss)表最后一条记录
     *
     * @param entityName
     * @return
     */
    @Override
    public CnApprdWaitIss findLastByEntityName(String entityName) {
        return baseMapper.findLastByEntityName(entityName);
    }

    /**
     * IPO-审核通过尚未发行(CnApprdWaitIss)执行导入任务
     *
     * @param windTask
     * @param list
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<CnApprdWaitIss> list) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();

        for (CnApprdWaitIss item : list) {
            String code = item.getCode();
            if (StrUtil.isEmpty(item.getEntityName()) || code.contains("数据来源：Wind")){
                continue;
            }

            //执行每一行
            Future<Object> future = cnApprdWaitIssStrategy.doThkStockImport(item, timeNow, windTask);
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
}
