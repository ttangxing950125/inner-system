package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsQualDataService;
import com.deloitte.common.core.domain.MetaR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * (PrsQualData)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsQualData")
@Api(tags = "主体指标表接口")
public class PrsQualDataController {
    /**
     * 服务对象
     */
    @Resource
    private PrsQualDataService prsQualDataService;


    @ApiOperation("导入指标数据")
    @PostMapping("importQualFromExcel")
    @ApiImplicitParam(name = "file", value = "上传的文件", dataType = "java.io.File", required = true, allowMultiple = true, paramType = "query")
    public MetaR importQualFromExcel(@RequestPart @RequestParam("file") MultipartFile serviceFile) {

        prsQualDataService.importQualFromExcel(serviceFile);
        return MetaR.ok();
    }
}
