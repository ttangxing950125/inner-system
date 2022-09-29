package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.service.ICrmMasTaskService;
import com.deloitte.crm.service.IModelMasterService;
import com.deloitte.crm.vo.CrmMasTaskVo;
import com.deloitte.crm.vo.ModelMasterInfoVo;
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

    private final IModelMasterService iModelMasterService;

    /**
     * 角色2今日运维模块
     * @author 正杰
     * @date 2022/9/27
     * @param date 请传入参数 yyyy-MM-dd
     * @return R<List<CrmMasTask>> 当月任务
     */
    @ApiOperation(value="查询当日任务 by正杰")
    @ApiImplicitParam(name="date",value="请传入参数 yyyy-MM-dd",paramType = "query",dataType = "String")
    @PostMapping("/getDayTaskInfo")
    @Log(title = "【 查询当日任务情况 】", businessType = BusinessType.OTHER)
    public R<List<CrmMasTaskVo>> getDayTaskInfo(String date){
        //单表查询 角色2当日任务完成情况
        return iCrmMasTaskService.getTaskInfo(Common.DAY,date);
    }

    /**
     * 角色2今日运维模块
     * @author 正杰
     * @date 2022/9/27
     * @param date 请传入参数 yyyy-MM
     * @return R<List<CrmMasTask>> 当日任务
     */
    @ApiOperation(value="查询当月任务 by正杰")
    @ApiImplicitParam(name="date",value="请传入参数 yyyy-MM",paramType = "query",dataType = "String")
    @PostMapping("/getMouthTaskInfo")
    @Log(title = "【 查询当月任务情况 】", businessType = BusinessType.OTHER)
    public R<List<CrmMasTaskVo>> getMouthTaskInfo(String date){
        //单表查询 角色2当月任务完成情况
        return iCrmMasTaskService.getTaskInfo(Common.MOUTH,date);
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

    /**
     * 敞口划分 选中单行开始工作 传入id后返回窗口 by正杰
     * @param id
     * @return
     */
    @ApiOperation(value="敞口划分，选中单行开始工作 传入id后返回窗口 by正杰")
    @ApiImplicitParam(name="id",value="传入 id",paramType = "query",dataType = "Integer")
    @Log(title = "确认该任务已完成,修改数据库任务状态", businessType = BusinessType.OTHER)
    @PostMapping("/getTable")
    public R<ModelMasterInfoVo> getTable(Integer id){
        return iModelMasterService.getTable(id);
    }


}
