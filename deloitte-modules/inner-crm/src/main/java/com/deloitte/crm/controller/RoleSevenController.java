package com.deloitte.crm.controller;

import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 正杰
 * @description: 该类为 角色7的总控制层
 * 2022.9.22 =>  主体基本信息填写
 *
 * 如果角色7确定这条信息没有问题，需要新增主体，则触发弹窗需要角色7输入/检查该主体全称、
 * 填入该主体统一社会信用代码，保存提交后，触发判断该主体是否需要被新增的判断，即于库中
 * 先判断统一社会信用代码是否已存在，然后判断企业名称是否已存在。
 *
 * @date 2022/9/22
 */
@RestController
@RequestMapping("/roleSeven")
@AllArgsConstructor
@Api("角色7控制层")
public class RoleSevenController {

    private final ICrmEntityTaskService iCrmEntityTaskService;

    private final IEntityInfoService iEntityInfoService;

    /**
     * 角色7今日运维模块
     * @author 正杰
     * @date 2022/9/22
     * @param timeUnit 请传入时间单位常量 MOUTH || DAY
     * @param date 请传入具体日期: yyyy-mm-dd
     * @return 当月或者当日的任务情况
     */
    @ApiOperation(value="查询指定日期或当月任务情况")
    @ApiImplicitParam(name="timeUnit,date",value="请传入时间单位常量 MOUTH || DAY,请传入具体日期: yyyy-mm-dd",required = true,paramType = "query",dataType = "String,Date")
    @PostMapping("/getTaskInfo")
    @Log(title = "【查询指定日期或当月任务情况】", businessType = BusinessType.OTHER)
    public AjaxResult getTaskInfo(String timeUnit,Date date){
        //TODO 单表查询 角色7当月||当日任务完成情况
        return iCrmEntityTaskService.getTaskInfo(timeUnit,date);
    }

    /**
     * 确认该任务的主体是新增或是忽略
     * @author 正杰
     * @date 2022/9/22
     * @param id 传入 id
     * @param state 传入 状态 1是忽略 2是新增
     * @return 操作成功与否
     */
    @ApiOperation(value="确认该任务的主体是新增或是忽略")
    @ApiImplicitParam(name="id,state",value="传入 id,传入 状态 1是忽略 2是新增",required = true,paramType = "query",dataType = "Integer,Integer")
    @Log(title = "【确认该任务的主体是新增或是忽略】", businessType = BusinessType.UPDATE)
    @PostMapping("/changeState")
    public AjaxResult changeState(Integer id,Integer state){
        //TODO 单表修改 角色7完成任务，选择是否为忽略或者新增
        return iCrmEntityTaskService.changeState(id,state);
    }

    /**
     * 传入社会信用代码于企业名称
     *  => 存在该社会信用代码 返回 比较信息为 false
     *     ==> 前端跳转调用人工对比信息，并确认
     *        ==> 如果判断为库内错误 调用 /entityInfo/edit
     *
     *  => 不存在社会信用代码 但存在相同企业名称 返回 比较信息 false
     *     ==> 前端跳转调用人工对比信息，并确认
     *        ==> 如果判断为 库中企业名称需要修改或需要修改企业曾用名  调用 /roleSeven/editEntityNameHis
     *
     *  => 不存在社会信用代码 也不存在相同企业名称 返回 比较信息 true
     *     ==> 确认新增主体 生成企业主体德勤代码、统一社会信用代码相关字段
     *
     * @author 正杰
     * @date 2022/9/22
     * @param creditCode 传入 企业统一社会信用代码
     * @param entityName 传入 企业名称
     * @return 比较信息结果
     */
    @ApiOperation(value="校验该主体是否存在，并做其他判断")
    @ApiImplicitParam(name="creditCode,entityName",value="传入 企业统一社会信用代码,传入 企业名称",required = true,paramType = "query",dataType = "String,String")
    @Log(title = "【校验该主体是否存在，并做其他判断】", businessType = BusinessType.OTHER)
    @PostMapping("/validEntity")
    public AjaxResult validEntity(String creditCode,String entityName){
        //TODO 校验数据库是否存在该主体
        return iEntityInfoService.validEntity(creditCode,entityName);
    }

    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     * @author 正杰
     * @date 2022/9/22
     * @param creditCode 统一社会信用代码
     * @param entityNewName 主体新名称
     * @param remarks 备注
     * @return 修改返回信息
     */
    @ApiOperation(value="修改主体信息中的主体名称 & 汇总曾用名")
    @ApiImplicitParam(name="creditCode,entityNewName,remarks",value="统一社会信用代码,主体新名称,备注",required = true,paramType = "query",dataType = "String,String,String")
    @Log(title = "【修改主体信息中的主体名称 & 汇总曾用名】", businessType = BusinessType.UPDATE)
    @PostMapping("/editEntityNameHis")
    public AjaxResult editEntityNameHis(String creditCode,String entityNewName,String remarks){
        //TODO 修改主体名称  =>  修改主体曾用名
        return iEntityInfoService.editEntityNameHis(creditCode,entityNewName,remarks);
    }

}
