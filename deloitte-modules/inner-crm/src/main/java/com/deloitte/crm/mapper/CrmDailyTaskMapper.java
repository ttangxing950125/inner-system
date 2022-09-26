package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmWindTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日生产角色任务mapper接口
 * @author PenTang
 * @date 2022/09/22 15:36
 */
public interface CrmDailyTaskMapper extends BaseMapper<CrmDailyTask>
{

    /**
     *根据月份查询当前运维任务状态
     *
     * @param startDate
     * @param endDate
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 13:56
     */
    List<CrmDailyTask> selectCrmDailyTaskListByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);


}
