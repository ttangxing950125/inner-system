package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskDate", value = "当天日期", example = "2022-09-29", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query", example = "9", dataType = "Integer")
    })

    @PostMapping("/getRoleSupplyTask")
    public R getRoleSupplyTask(String taskDate, Integer pageNum, Integer pageSize) {
        return iCrmSupplyTaskService.getRoleSupplyTask(taskDate, pageNum, pageSize);
    }
}
