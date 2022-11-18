package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CnIpoFail;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.CnIpoFailMapper;
import com.deloitte.crm.domain.CnIpoFail;
import com.deloitte.crm.service.CnIpoFailService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.CnIpoFailStrategy;
import com.deloitte.crm.strategy.impl.CnIpoFailStrategy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * IPO-发行失败(CnIpoFail)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:49
 */
@Service("cnIpoFailService")
public class CnIpoFailServiceImpl extends ServiceImpl<CnIpoFailMapper, CnIpoFail> implements CnIpoFailService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Lazy
    @Resource
    private CnIpoFailStrategy cnIpoFailStrategy;
    
    /**
     * 根据code查询最新的一条 CnIpoFail
     *
     * @param code
     * @return
     */
    @Override
    public CnIpoFail findLastByCode(String code) {
        return baseMapper.findLastByCode(code);
    }

    /**
     * 导入IPO-发行暂缓(CnIpoFail) 的任务
     *
     * @param windTask
     * @param list
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<CnIpoFail> list) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();


        for (CnIpoFail item : list) {
            String entityName = item.getEntityName();
            if (entityName.contains("数据来源：Wind")){
                continue;
            }

            //执行每一行
            Future<Object> future = cnIpoFailStrategy.doThkStockImport(item, timeNow, windTask);
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
