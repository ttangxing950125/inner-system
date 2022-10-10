package com.deloitte.crm.strategy;

import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;
import java.util.Map;

/**
 *
 * 处理wind任务
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
public interface WindTaskStrategy {

    /**
     * 是否支持当前wind任务
     * @return
     */
    boolean support(Integer windDictId);

    /**
     * 开始执行任务
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    Object doTask(WindTaskContext windTaskContext) throws Exception;

    /**
     * 获得任务详情页，上传的数据的表头
     * @return
     */
    List<String> getDetailHeader(CrmWindTask windTask);

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     * @param windTask
     * @return
     */
    List<Map<String, Object>> getDetail(CrmWindTask windTask);

    /**
     * 查询详情数据
     * @param windDictId 分类id
     * @param rowId 分类所属表的id
     * @return
     */
//    Map<String, Object> findRowDetail(Integer windDictId, Integer rowId);

}
