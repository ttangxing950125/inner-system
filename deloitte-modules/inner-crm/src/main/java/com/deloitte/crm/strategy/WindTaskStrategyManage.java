package com.deloitte.crm.strategy;

import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.crm.domain.CrmWindTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
@Slf4j
@Component
public class WindTaskStrategyManage implements ApplicationContextAware {

    /**
     * 能够处理wind任务的对象
     */
    private Collection<WindTaskStrategy> windTaskStrategies;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, WindTaskStrategy> beans = applicationContext.getBeansOfType(WindTaskStrategy.class);
        windTaskStrategies = beans.values();
    }

    /**
     * 开始执行任务
     *
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        CrmWindTask windTask = windTaskContext.getWindTask();
        WindTaskStrategy supportItem = getSupportItem(windTask.getTaskDictId());
        if (supportItem == null) {
            log.warn("上下文获取WindTaskStrategy为空缺少taskDictId={}, 任务结束!!!!!", windTask.getTaskDictId());
            return null;
        }
        return supportItem.doTask(windTaskContext);
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @return
     */
    public List<String> getDetailHeader(CrmWindTask windTask) {
        WindTaskStrategy supportItem = getSupportItem(windTask.getTaskDictId());
        if (supportItem == null) {
            return new ArrayList<>();
        }

        return supportItem.getDetailHeader(windTask);
    }

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     *
     * @param windTask
     * @return
     */
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        WindTaskStrategy supportItem = getSupportItem(windTask.getTaskDictId());
        if (supportItem == null) {
            return new ArrayList<>();
        }

        return supportItem.getDetail(windTask);
    }


    public WindTaskStrategy getSupportItem(Integer taskDictId) {
        return windTaskStrategies.stream().filter(item -> item.support(taskDictId)).findFirst().orElse(null);
    }

}
