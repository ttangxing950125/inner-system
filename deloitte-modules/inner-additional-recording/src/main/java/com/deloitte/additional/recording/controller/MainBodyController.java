package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.dto.MainBodyPageDto;
import com.deloitte.additional.recording.service.*;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("mainBody")
public class MainBodyController {

    @Resource
    private EntityInfoService entityInfoService;

    /**
     * 服务对象
     */
    @Resource
    private PrsVerMasEntityService prsVerMasEntityService;

    @Resource
    private FFileService fFileService;


    /**
     *关闭按钮
     * @param id
     * @param stauts
     * @return
     */
    @PutMapping("updateIncrStatus/{id}/{status}")
    public R updateIncrStatus(@PathVariable("id")Integer id,@PathVariable("status") Integer stauts){
        return   prsVerMasEntityService.updateIncrStatus(id,stauts);
    }


    /**
     * 查询报告
     */
    @GetMapping("queryReportList")
    public R queryReportList(String entityCode,Integer year){
        return fFileService.queryReportList(entityCode,year);

    }


    /**
     *查询主体列表分页
     */
    @PostMapping("/queryPrincipalManifestPage")
    public R queryPrincipalManifestPage(@RequestBody MainBodyPageDto mainBodyDto){
        return R.ok(entityInfoService.queryPrincipalManifestPage(mainBodyDto));
    }

}
