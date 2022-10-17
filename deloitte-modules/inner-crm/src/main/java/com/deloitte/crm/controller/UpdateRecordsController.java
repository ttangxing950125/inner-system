package com.deloitte.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;
import com.deloitte.crm.service.EntityInfoLogsUpdatedService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 正杰
 * @description: UpdateRecordsController
 * @date 2022/10/17
 */
@Slf4j
@AllArgsConstructor
@Api(tags = "更新记录")
@RestController
@RequestMapping("/updateRecords")
public class UpdateRecordsController {

    private EntityInfoLogsUpdatedService entityInfoLogsUpdatedService;

    /**
     *  查询上市企业或是地方政府的更新记录
     *
     * @param tableType -> 1-企业上市信息 || 2-地方政府上市信息
     * @param pageNum default 1
     * @param pageSize default 10
     * @return {@link EntityInfoLogsUpdated}
     */
    @ApiOperation(value="上市企业/地方政府-更新记录 by正杰")
    @ApiResponse(code = 200,message = "查询成功",response = EntityInfoLogsUpdated.class)
    @ApiImplicitParams({
    @ApiImplicitParam(name="tableType",value="1-企业上市信息 2-地方政府上市信息",paramType = "query",dataType = "Integer",example = "1"),
    @ApiImplicitParam(name="pageNum",value="pageNum",paramType = "query",dataType = "Integer",example = "1"),
    @ApiImplicitParam(name="pageSize",value="pageSize",paramType = "query",dataType = "Integer",example = "10")})
    @Log(title = "获取子集", businessType = BusinessType.OTHER)
    @PostMapping("/getInfo")
    public R<Page<EntityInfoLogsUpdated>> getInfo(Integer tableType,Integer pageNum,Integer pageSize){
        return entityInfoLogsUpdatedService.getInfo(tableType,pageNum,pageSize);
    }

}
