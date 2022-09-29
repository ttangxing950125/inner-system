package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.vo.CheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private EntityInfoManager entityInfoManager;

    private final ICrmWindTaskService crmWindTaskService;

    private final ICrmDailyTaskService crmDailyTaskService;

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

    @ApiOperation(value="校验字段 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword",value="传入 需要校验的信息",paramType = "query",dataType = "String",example = "ENTITY_CODE"),
            @ApiImplicitParam(name="target",value="传入 目标字段",paramType = "query",dataType = "String")
    })
    @Log(title = "【校验字段】", businessType = BusinessType.OTHER)
    @PostMapping("/checkData")
    public R<CheckVo> checkData(String keyword,String target){
        return R.ok(entityInfoManager.matchByKeyword(keyword,target));
    }



}
