package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.service.ICrmMasTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 正杰
 * @description: RoleTwoController
 * @date 2022/9/26
 */
@RestController
@RequestMapping("/roleTwo")
@AllArgsConstructor
@Api(tags = "角色2控制层")
public class RoleTwoController {

    private final ICrmMasTaskService iCrmMasTaskService;

    /**
     * 角色2今日运维模块
     * @author 正杰
     * @date 2022/9/27
     * @param timeUnit 请传入时间单位常量 MOUTH || DAY
     * @param date 请传入具体日期: yyyy/mm/dd
     * @return R<List<CrmMasTask>> 当月或者当日的任务情况
     */
    @ApiOperation(value="查询指定日期或当月任务情况,返回 List<CrmMasTask> by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="timeUnit",value="请传入时间单位常量 MOUTH || DAY",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="date",value="请传入具体日期: yyyy/mm/dd",paramType = "query",dataType = "Date")})
    @PostMapping("/getTaskInfo")
    @Log(title = "【查询指定日期或当月任务情况】", businessType = BusinessType.OTHER)
    public R<List<CrmMasTask>> getTaskInfo(String timeUnit, Date date){
        //单表查询 角色2当月||当日任务完成情况
        return iCrmMasTaskService.getTaskInfo(timeUnit,date);
    }

    /**
     * 确认该任务已完成,修改数据库任务状态
     * @author 正杰
     * @date 2022/9/27
     * @param id 传入 id
     * @return 操作成功与否
     */
    @ApiOperation(value="确认该任务已完成 by正杰")
    @ApiImplicitParam(name="id",value="传入 id",paramType = "query",dataType = "Integer")
    @Log(title = "确认该任务已完成,修改数据库任务状态", businessType = BusinessType.UPDATE)
    @PostMapping("/changeState")
    public R changeState(Integer id){
        //单表修改 角色2完成任务,确认该任务已完成,修改数据库任务状态
        //TODO 修改 关联大表 crm_daily_task 的 task_status
        return iCrmMasTaskService.changeState(id);
    }

}
