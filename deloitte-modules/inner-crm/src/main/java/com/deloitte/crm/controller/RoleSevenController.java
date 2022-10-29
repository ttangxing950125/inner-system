package com.deloitte.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.impl.RoleSevenTaskFactory;
import com.deloitte.crm.vo.CrmEntityTaskVo;
import com.deloitte.crm.vo.EntityInfoVo;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
@Slf4j
public class RoleSevenController {

    private final ICrmEntityTaskService iCrmEntityTaskService;

    private final IEntityInfoService iEntityInfoService;

    private final RoleSevenTaskFactory roleSevenTaskFactory;

    /**
     * 角色7今日运维模块
     * @author 正杰
     * @param date 请传入参数 yyyy-mm-dd
     * @param pageNum
     * @param pageSize
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当日任务情况
     */
    @ApiOperation(value="查询当日任务 by正杰")
    @ApiResponse(code = 200,message = "操作成功",response = CrmEntityTaskVo.class)
    @ApiImplicitParam(name="date",value="请传入参数 yyyy-mm-dd",paramType = "query",dataType = "String",example = "2022-10-12")
    @PostMapping("/getDayTaskInfo")
    @Log(title = "【 查询当日任务情况 】", businessType = BusinessType.OTHER)
    public R<Page<CrmEntityTask>> getDayTaskInfo(String date, Integer pageNum, Integer pageSize){
        log.info("==> 角色7当日查询 <==");
        return iCrmEntityTaskService.getTaskInfo(date,pageNum,pageSize);
    }

    /**
     * 忽略该任务
     * @author 正杰
     * @date 2022/9/22
     * @param entityInfoInsertDTO
     * @return 操作成功与否
     */
    @ApiOperation(value="忽略该任务 by正杰")
    @ApiImplicitParam(name="entityInfoInsertDTO",value="传入taskId,外部传entity_name 内部传 credit_code" , paramType = "body",dataTypeClass = EntityInfoInsertDTO.class)
    @Log(title = "【忽略该任务】", businessType = BusinessType.UPDATE)
    @PostMapping("/ignoreTask")
    public R ignoreTask(@RequestBody EntityInfoInsertDTO entityInfoInsertDTO){
        return roleSevenTaskFactory.getFactory(Common.IGNORE).finishTask(entityInfoInsertDTO);
    }

    /**
     * 新增主体 并绑定关联债券||股票信息
     * @param entityInfoInsertDTO
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    //@RequiresPermissions("crm:entityInfo:add")
    @ApiOperation(value="新增主体 by正杰")
    @Log(title = "【确定该主体是新增后,填写具体要新增主体的信息】", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name="entityInfoInsertDTO",value="传入taskId,entityName，creditCode，dataSource，dataCode,不适用加creditErrorType" , paramType = "body",dataTypeClass = EntityInfoInsertDTO.class)
    @PostMapping("/add")
    public R addEntity(@RequestBody EntityInfoInsertDTO entityInfoInsertDTO) {
        return roleSevenTaskFactory.getFactory(Common.INSERT_ENTITY).finishTask(entityInfoInsertDTO);
    }

    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     * @author 正杰
     * @date 2022/9/22
     * @param entityInfoInsertDTO
     * @return 修改返回信息
     */
    @ApiOperation(value="修改主体信息中的主体名称 & 汇总曾用名 by正杰")
    @ApiImplicitParam(name="entityInfoInsertDTO",value="传入taskId,entityName，dataSource，dataCode，entityCode" , paramType = "body",dataTypeClass = EntityInfoInsertDTO.class)
    @Log(title = "【修改主体信息中的主体名称 & 汇总曾用名】", businessType = BusinessType.UPDATE)
    @PostMapping("/editEntityNameHis")
    public R editEntityNameHis(@RequestBody EntityInfoInsertDTO entityInfoInsertDTO){
        log.info("  =>> 角色7 修改主体名称 将其命名为{} <<=  ",entityInfoInsertDTO.getEntityName());
        return roleSevenTaskFactory.getFactory(Common.EDITE_NAME).finishTask(entityInfoInsertDTO);
    }

    /**
     * 校验 统一社会信用代码，主体名称
     * @author 正杰
     * @param creditCode
     * @param entityName
     * @return
     */
    @ApiOperation(value="校验主体名称及代码 by正杰")
    @ApiImplicitParams({
    @ApiImplicitParam(name="creditCode",value="传入 社会信用代码", paramType = "query",dataType = "String",example = "91230100128025258G"),
    @ApiImplicitParam(name="entityName",value="传入 主体名称" , paramType = "query",dataType = "String",example = "哈尔滨哈投投资股份有限公司")})
    @Log(title = "【 校验主体名称及代码 】", businessType = BusinessType.OTHER)
    @PostMapping("/validateCodeAndName")
    public R<EntityInfoVo> validateCodeAndName(String creditCode,@NotNull(message = "主体名称不能为空") String entityName){
        return iEntityInfoService.validateCodeAndName(creditCode,entityName);
    }

    /**
     * 修改库中主体的统一社会信用代码 by正杰
     * @param entityCode
     * @param creditCode
     * @return
     */
    @ApiOperation(value="修改库中主体的统一社会信用代码 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="entityCode",value="传入 德勤内部代码", paramType = "query",dataType = "String",example = "IB000001"),
            @ApiImplicitParam(name="creditCode",value="传入 社会信用代码" , paramType = "query",dataType = "String",example = "91230100128025258G")})
    @Log(title = "【 修改库中主体的统一社会信用代码 】", businessType = BusinessType.OTHER)
    @PostMapping("/editeCreditCode")
    public R editeCreditCode(@NotNull(message = "德勤代码不能为空") String entityCode,@NotNull(message = "统一社会信用代码不能为空") String creditCode){
        return iEntityInfoService.editeCreditCode(entityCode,creditCode);
    }

    /**
     * 新增库中主体的曾用名 by正杰
     * @param entityInfoInsertDTO 传入taskId,entityName，dataSource，dataCode，entityCode
     * @return
     */
    @ApiOperation(value="新增库中主体的曾用名 by正杰")
    @ApiImplicitParam(name="entityInfoInsertDTO",value="传入taskId,entityName，dataSource，dataCode，entityCode" , paramType = "body",dataTypeClass = EntityInfoInsertDTO.class)
    @Log(title = "【 新增库中主体的曾用名 】", businessType = BusinessType.OTHER)
    @PostMapping("/addEntityNameHis")
    public R addEntityNameHis(@RequestBody EntityInfoInsertDTO entityInfoInsertDTO){
        return roleSevenTaskFactory.getFactory(Common.INSERT_OLD_NAME).finishTask(entityInfoInsertDTO);
    }

    /**
     * 删除该条任务 by正杰
     * @param id 传入taskId
     * @return
     */
    @ApiOperation(value="删除该条任务 by正杰")
    @ApiImplicitParam(name="id",value="传入taskId",paramType = "body",dataType = "Integer")
    @Log(title = "【 删除该条任务 】", businessType = BusinessType.OTHER)
    @PostMapping("/deleteTask/{id}")
    public R deleteTask(@PathVariable Integer id) {
        return roleSevenTaskFactory.getFactory(Common.DELETE_TASK).finishTask(new EntityInfoInsertDTO().setTaskId(id));
    }

}
