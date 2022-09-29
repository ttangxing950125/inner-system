package com.deloitte.crm.controller;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.CrmEntityTaskVo;
import com.deloitte.crm.vo.EntityInfoVo;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
@Api(tags = "角色7控制层")
public class RoleSevenController {

    private final ICrmEntityTaskService iCrmEntityTaskService;

    private final IEntityInfoService iEntityInfoService;

    /**
     * 角色7今日运维模块
     * @author 正杰
     * @param date 请传入参数 yyyy-mm
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当月任务情况
     */
    @ApiOperation(value="查询当月任务 by正杰")
    @ApiImplicitParam(name="date",value="请传入参数 yyyy-mm",paramType = "query",dataType = "String")
    @PostMapping("/getMouthTaskInfo")
    @Log(title = "【 查询当月任务情况 】", businessType = BusinessType.OTHER)
    public R<List<CrmEntityTaskVo>> getMouthTaskInfo(String date){
        return iCrmEntityTaskService.getTaskInfo(Common.MOUTH,date);
    }

    /**
     * 角色7今日运维模块
     * @author 正杰
     * @param date 请传入参数 yyyy-mm-dd
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当日任务情况
     */
    @ApiOperation(value="查询当日任务 by正杰")
    @ApiImplicitParam(name="date",value="请传入参数 yyyy-mm-dd",paramType = "query",dataType = "String")
    @PostMapping("/getDayTaskInfo")
    @Log(title = "【 查询当日任务情况 】", businessType = BusinessType.OTHER)
    public R<List<CrmEntityTaskVo>> getDayTaskInfo(String date){
        return iCrmEntityTaskService.getTaskInfo(Common.DAY,date);
    }

    /**
     * 确认该任务的主体是新增或是忽略
     * @author 正杰
     * @date 2022/9/22
     * @param id 传入 id
     * @param state 传入 状态 1是忽略 2是新增
     * @return 操作成功与否
     */
    @ApiOperation(value="确认该任务的主体是新增或是忽略 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="传入 id",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name="state",value="传入 状态 1是忽略 2是新增",paramType = "query",dataType = "Integer")})
    @Log(title = "【确认该任务的主体是新增或是忽略】", businessType = BusinessType.UPDATE)
    @PostMapping("/changeState")
    public R changeState(Integer id,Integer state){
        //单表修改 角色7完成任务，选择是否为忽略或者新增
        //TODO 修改 关联大表 crm_daily_task 的 task_status
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
    @ApiOperation(value="校验该主体是否存在，并做其他判断 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="creditCode",value="传入 企业统一社会信用代码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="entityName",value="传入 企业名称",paramType = "query",dataType = "String")
    })
    @Log(title = "【校验该主体是否存在，并做其他判断】", businessType = BusinessType.OTHER)
    @PostMapping("/validEntity")
    public R<EntityInfoVo> validEntity(String creditCode, String entityName,Boolean enableCreditCode){
        Assert.isTrue(StringUtils.hasText(creditCode), BadInfo.PARAM_EMPTY.getInfo());
        Assert.isTrue(StringUtils.hasText(entityName), BadInfo.PARAM_EMPTY.getInfo());
        //校验数据库是否存在该主体
        return iEntityInfoService.validEntity(creditCode,entityName);
    }

    /**
     * @param entityDto
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    @RequiresPermissions("crm:entityInfo:add")
    @Log(title = "【确定该主体是新增后,填写具体要新增主体的信息】", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name = "entityInfoDto", value = "包含表中entity_info所有字段以及 haveCreditCode oldName 额外两个字段", paramType = "body")
    @PostMapping("/add")
    public R addEntity(@RequestBody EntityDto entityDto) {
        //TODO 新增主体
        iEntityInfoService.insertEntityInfo(entityDto);
        return R.ok();
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
    @ApiOperation(value="修改主体信息中的主体名称 & 汇总曾用名 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="creditCode",value="统一社会信用代码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="entityNewName",value="主体新名称",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="remarks",value="备注",paramType = "query",dataType = "String")
    })
    @Log(title = "【修改主体信息中的主体名称 & 汇总曾用名】", businessType = BusinessType.UPDATE)
    @PostMapping("/editEntityNameHis")
    public R editEntityNameHis(String creditCode,String entityNewName,String remarks){
        //修改主体名称  =>  修改主体曾用名
        return iEntityInfoService.editEntityNameHis(creditCode,entityNewName,remarks);
    }


    /**
     * 校验统一社会信用代码是否存在 by正杰
     * @author 正杰
     * @date 2022/9/28
     * @param creditCode
     * @return R
     */
    @ApiOperation(value="校验统一社会信用代码是否存在 by正杰")
    @ApiImplicitParam(name="creditCode",value="传入 企业统一社会信用代码",required = true , paramType = "query",dataType = "String")
    @Log(title = "【 校验统一社会信用代码是否存在 】", businessType = BusinessType.OTHER)
    @PostMapping("/checkCreditCode")
    public R<EntityInfoVo> checkCreditCode(String creditCode){
        return iEntityInfoService.checkCreditCode(creditCode);
    }

    /**
     * 校验主体名称是否存在
     * @author 正杰
     * @date 2022/9/28
     * @param entityName
     * @return R
     */
    @ApiOperation(value="校验主体名称是否存在 by正杰")
    @ApiImplicitParam(name="entityName",value="传入 主体名称",required = true , paramType = "query",dataType = "String")
    @Log(title = "【 校验主体名称是否存在 】", businessType = BusinessType.OTHER)
    @PostMapping("/checkEntityName")
    public R<EntityInfoVo> checkEntityName(String entityName){
        return iEntityInfoService.checkEntityName(entityName);
    }

}
