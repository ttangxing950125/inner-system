package com.deloitte.crm.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @param date 请传入参数 yyyy-mm-dd
     * @param pageNum
     * @param pageSize
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当日任务情况
     */
    @ApiOperation(value="查询当日任务 by正杰")
    @ApiResponse(code = 200,message = "操作成功",response = CrmEntityTaskVo.class)
    @ApiImplicitParam(name="date",value="请传入参数 yyyy-mm-dd",paramType = "query",dataType = "String")
    @PostMapping("/getDayTaskInfo")
    @Log(title = "【 查询当日任务情况 】", businessType = BusinessType.OTHER)
    public R<Page<CrmEntityTaskVo>> getDayTaskInfo(String date, Integer pageNum, Integer pageSize){
        return iCrmEntityTaskService.getTaskInfo(date,pageNum,pageSize);
    }

    /**
     * 忽略该任务
     * @author 正杰
     * @date 2022/9/22
     * @param id 传入 id
     * @return 操作成功与否
     */
    @ApiOperation(value="忽略该任务 by正杰")
    @ApiImplicitParams(
    @ApiImplicitParam(name="id",value="传入 id",paramType = "query",dataType = "Integer"))
    @Log(title = "【忽略该任务】", businessType = BusinessType.UPDATE)
    @PostMapping("/ignoreTask")
    public R ignoreTask(Integer id){
        // 已有主体状态为 1
        return iCrmEntityTaskService.finishTask(id,1);
    }

    /**
     * @param entityDto
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    //@RequiresPermissions("crm:entityInfo:add")
    @ApiOperation(value="新增主体 by正杰")
    @Log(title = "【确定该主体是新增后,填写具体要新增主体的信息】", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name = "entityDto", value = "注意 此处id为 当日任务id", paramType = "body" , dataTypeClass = EntityDto.class)
    @PostMapping("/add")
    public R addEntity(@RequestBody EntityDto entityDto) {
        return iEntityInfoService.insertEntityInfo(entityDto);
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
