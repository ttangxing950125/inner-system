package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.RoleMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 正杰
 * @description: RoleMainController
 * @date 2022/9/29
 */
@RestController
@RequestMapping("/roleMain")
@Api(tags = "角色控制总层")
@AllArgsConstructor
public class RoleMainController {

    private final EntityInfoManager entityInfoManager;
    private final ICrmDailyTaskService crmDailyTaskService;

    private final RoleMainService roleMainService;


    /**
     * 指定日期查询各角色当月任务完成情况
     *
     * @return R
     * @author penTang
     * @editeBy 正杰
     * @date 2022/9/21 18:06
     * @editeDate 2022/9/29
     *
     */
    @GetMapping("/queryDailyTask")
    @ApiOperation(value = "指定日期查询各角色当月任务完成情况",response = CrmWindTask.class)
    @ApiImplicitParam(name ="taskDate",value = " taskDate 任务日期 format => yyyy-MM-dd",paramType = "query",example = "2022-09-23",dataType = "String")
    public R<List<CrmDailyTask>> queryDailyTask(String taskDate) {
        return R.ok(crmDailyTaskService.selectCrmDailyTaskListByDate(taskDate));
    }

    @GetMapping("/queryDailyTaskByDay")
    @ApiOperation(value ="指定日期 查询当日任务",response = CrmWindTask.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name ="taskDate",value = "yyyy-MM-dd", paramType = "query",example = "2022-09-28",dataType = "String"),
        @ApiImplicitParam(name ="pageNum",value = "当前页",example = "1",paramType = "query",dataType = "Integer"),
        @ApiImplicitParam(name ="pageSize",value = "分页页数",example = "5",paramType = "query",dataType = "Integer")
    })
    public R queryDailyTaskByDay(String taskDate, Integer pageNum, Integer pageSize){
        return roleMainService.queryDailyTaskByDay(taskDate,pageNum,pageSize);
    }

    @ApiOperation(value="校验字段 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword",value="传入 需要校验的信息",paramType = "query",dataType = "String",example = "ENTITY_CODE"),
            @ApiImplicitParam(name="target",value="传入 目标字段",paramType = "query",dataType = "String")
    })
    @Log(title = "【校验字段】", businessType = BusinessType.OTHER)
    @PostMapping("/checkData")
    public R checkData(@RequestParam("keyword")@NotNull(message = "关键字不能为空") String keyword,@RequestParam("target")@NotNull(message = "目标字段不能为空") String target){
        return entityInfoManager.matchByKeyword(keyword,target);
    }
    @PostMapping("/sendEmail")
    public R sendEmail(String date){
        return R.ok(crmDailyTaskService.sendEmail(date));
    }
}
