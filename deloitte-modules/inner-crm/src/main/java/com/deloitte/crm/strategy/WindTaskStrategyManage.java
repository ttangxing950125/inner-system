package com.deloitte.crm.strategy;

import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.crm.domain.CrmWindTask;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
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
    public Object doTask(WindTaskContext windTaskContext) {
        return null;
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @return
     */
    public List<String> getDetailHeader(CrmWindTask windTask) {
        return getSupportItem(windTask.getTaskDictId()).getDetailHeader(windTask);
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
        return getSupportItem(windTask.getTaskDictId()).getDetail(windTask);
    }


    public WindTaskStrategy getSupportItem(Integer taskDictId){
        WindTaskStrategy strategy = windTaskStrategies.stream().filter(item -> item.support(taskDictId)).findFirst().orElse(null);
        if (strategy==null){
            throw new GlobalException("暂不支持该任务");
        }

        return strategy;
    }

}
