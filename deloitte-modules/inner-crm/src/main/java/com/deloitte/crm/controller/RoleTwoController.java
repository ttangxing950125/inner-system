package com.deloitte.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.GovLevel;
import com.deloitte.crm.domain.ModelMaster;
import com.deloitte.crm.dto.MasDto;
import com.deloitte.crm.service.ICrmMasTaskService;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.service.IGovLevelService;
import com.deloitte.crm.service.IModelMasterService;
import com.deloitte.crm.vo.CrmMasTaskVo;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final IGovInfoService iGovInfoService;

    private final IGovLevelService iGovLevelService;

    /**
     * 角色2今日运维模块
     * @author 正杰
     * @date 2022/9/27
     * @return R<Page<CrmMasTaskVo>> 当日任务
     * @param date 请传入参数 yyyy-MM-dd
     * @param sourceName 来源
     * @param pageNum 页码
     * @param pageSize 页数
     */
    @ApiOperation(value="查询当日任务 by正杰")
    @ApiResponse(code=200,message = "操作成功",response = CrmMasTaskVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name="date",value="请传入参数 yyyy-MM-dd",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="sourceName",value="来源",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="pageNum",value="页码",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name="pageSize",value="页数",paramType = "query",dataType = "Integer")
    })
    @PostMapping("/getTaskInfo")
    @Log(title = "【 查询当日任务 】", businessType = BusinessType.OTHER)
    public R<Page<CrmMasTaskVo>> getTaskInfo(String date,String sourceName, Integer pageNum, Integer pageSize){
        return iCrmMasTaskService.getTaskInfo(date,sourceName,pageNum,pageSize);
    }

    /**
     * 敞口划分 选中单行开始工作 传入id后返回窗口 by正杰
     * @param id
     * @return
     */
    @ApiOperation(value="敞口划分，选中单行开始工作 传入id后返回窗口 by正杰")
    @ApiImplicitParam(name="id",value="传入 id",paramType = "query",dataType = "Integer")
    @ApiResponse(code = 200,message = "操作成功",response = MasDto.class)
    @Log(title = "敞口划分，选中单行开始工作", businessType = BusinessType.OTHER)
    @PostMapping("/getTable")
    public R<MasDto> getTable(Integer id){
        return iModelMasterService.getTable(id);
    }

    /**
     * 确定新增 by正杰
     * 此处调用 浩辰方法
     * @param govInfo
     * @return
     */
    @ApiOperation(value="确定新增地方政府 by正杰")
    @ApiImplicitParam(name="govInfo",value="govInfo对象",paramType = "body",dataTypeClass = GovInfo.class )
    @Log(title = "新增政府", businessType = BusinessType.INSERT)
    @PostMapping("/insertGov")
    public R insertGov(@RequestBody GovInfo govInfo){
        return iGovInfoService.insertGovInfo(govInfo);
    }

    /**
     * 获取上级地方政府行政编码 by正杰
     * @param govCode
     * @return
     */
    @ApiOperation(value="获取上级地方政府行政编码 by正杰")
    @ApiImplicitParam(name="govCode",value="上级地方政府名称",paramType = "query",dataType = "String" )
    @Log(title = "获取上级地方政府行政名称", businessType = BusinessType.OTHER)
    @PostMapping("/getPreGovName")
    public R<String> getPreGovName(String govCode){
        return iGovInfoService.getPreGovName(govCode);
    }

    /**
     * 获取金融细分领域多选框
     * @return
     */
    @ApiOperation(value="获取金融细分领域多选框 by正杰")
    @Log(title = "获取金融细分领域多选框", businessType = BusinessType.OTHER)
    @PostMapping("/getFinances")
    public R<List<String>> getFinances(){
        return iModelMasterService.getFinances();
    }

    /**
     * 敞口划分 保存并提交
     * @param masDto
     * @return
     */
    @ApiOperation(value="提交表单 by正杰")
    @ApiImplicitParam(name="masDto",value="masDto对象",dataTypeClass = MasDto.class )
    @Log(title = "提交表单", businessType = BusinessType.INSERT)
    @PostMapping("/insertMas")
    public R insertMas(@RequestBody MasDto masDto){
        return iModelMasterService.insert(masDto);
    }

    /**
     * 获取顶级
     * @return
     */
    @ApiOperation(value="获取顶级 by正杰")
    @Log(title = "获取顶级", businessType = BusinessType.OTHER)
    @PostMapping("/getGovLevelBig")
    public R<List<GovLevel>> getGovLevelBig(){
        return iGovLevelService.getGovLevelBig();
    }

    /**
     * 获取子集
     * @param id
     * @return
     */
    @ApiOperation(value="获取子集 by正杰")
    @ApiImplicitParam(name="id",value="id",paramType = "query",dataType = "Integer")
    @Log(title = "获取子集", businessType = BusinessType.OTHER)
    @PostMapping("/getGovLevelSmall")
    public R<List<GovLevel>> getGovLevelSmall(Integer id){
        return iGovLevelService.getGovLevelSmall(id);
    }

    /**
     * 获取敞口信息 by正杰
     * @return
     */
    @ApiOperation(value="获取敞口信息 by正杰")
    @ApiResponse(code = 200 ,message = "操作成功", response = ModelMaster.class)
    @Log(title = "获取敞口信息", businessType = BusinessType.OTHER)
    @PostMapping("/getModelMaster")
    public R<List<ModelMaster>> getModelMaster(){
        return iModelMasterService.getModelMaster();
    }

}
