package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 添加方法描述
 *
 * @author 冉浩岑
 * @date 2022/9/27 18:47
 */
@RestController
@RequestMapping("/roletff")
@AllArgsConstructor
@Api(tags = "角色345控制层")
public class RoleTFFController {

    private final ICrmSupplyTaskService iCrmSupplyTaskService;

    /**
     * 获取登录用户角色的信息补充任务
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/27 18:52
     */
    @ApiOperation(value = "获取登录用户角色的信息补充任务")
    @ApiImplicitParam(name = "taskDate", value = "当天日期 2022-09-29", paramType = "query", dataType = "Integer")
    @PostMapping("/getRoleSupplyTask")
    public R getRoleSupplyTask(String taskDate) {
        return iCrmSupplyTaskService.getRoleSupplyTask(taskDate);
    }

    /**
     * 完成任务
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/27 18:52
     */
    @ApiOperation(value = "完成任务")
    @ApiImplicitParam(name = "id", value = "任务id", paramType = "query", dataType = "Long")
    @PostMapping("/completeRoleSupplyTask")
    public R completeRoleSupplyTask(Long id) {
        return R.ok(iCrmSupplyTaskService.completeRoleSupplyTask(id));
    }
}
