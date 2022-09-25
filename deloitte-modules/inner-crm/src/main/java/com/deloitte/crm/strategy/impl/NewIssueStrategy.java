package com.deloitte.crm.strategy.impl;

import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
public class NewIssueStrategy implements WindTaskStrategy {
    /**
     * 是否支持当前wind任务
     *
     * @param windDictId
     * @return
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(windDictId, WindTaskEnum.NEW_ISSUE.getId());
    }

    /**
     * 开始执行任务
     *
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    @Override
    public Object doTask(WindTaskContext windTaskContext) {
        return null;
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        return null;
    }

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     *
     * @param windTask
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        return null;
    }
}
