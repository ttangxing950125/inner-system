package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.BasEvdDataService;
import com.deloitte.additional.recording.service.BasEvdDataTabService;
import com.deloitte.common.core.domain.MetaR;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * (BasEvdData)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:36
 */
@RestController
@RequestMapping("basEvdData")
public class BasEvdDataController {
    /**
     * 服务对象
     */
    @Resource
    private BasEvdDataService basEvdDataService;

    @Resource
    private BasEvdDataTabService basEvdDataTabService;


    @ApiOperation("导入wind数据")
    @PostMapping("importWindFromExcel")
    @ApiImplicitParam(name = "file", value = "上传的文件", dataType = "java.io.File", required = true,
            allowMultiple = true, paramType = "query")
    public MetaR importWindFromExcel(
            @RequestPart @RequestParam("file") MultipartFile serviceFile) {

        basEvdDataService.importWindFromExcel(serviceFile);
        return MetaR.ok();
    }

    @ApiOperation("导入子表数据")
    @PostMapping("importSubtableFromExcel")
    @ApiImplicitParam(name = "file", value = "上传的文件", dataType = "java.io.File", required = true,
            allowMultiple = true, paramType = "query")
    public MetaR importSubTableFromExcel(@RequestPart @RequestParam("file") MultipartFile serviceFile) {
        basEvdDataTabService.importSubTableFromExcel(serviceFile);
        return MetaR.ok();
    }

}
