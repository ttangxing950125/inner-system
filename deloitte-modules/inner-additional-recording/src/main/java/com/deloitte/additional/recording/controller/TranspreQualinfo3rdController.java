package com.deloitte.additional.recording.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.service.TranspreQualinfo3rdService;
import com.deloitte.additional.recording.vo.qualinfo3rd.TranspreQualinfo3rdPageVO;
import com.deloitte.common.core.domain.MetaR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (TranspreQualinfo3rd)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@RestController
@RequestMapping("transpreQualinfo3rd")
@Api(tags = "指标映射")
public class TranspreQualinfo3rdController {
    /**
     * 服务对象
     */
    @Resource
    private TranspreQualinfo3rdService transpreQualinfo3rdService;

    @ApiOperation("分页查询指标映射分页列表")
    @GetMapping("getMapQualFactorPaging")
    public MetaR<Page<TranspreQualinfo3rdPageVO>> page(@ApiParam("年份") @RequestParam(value = "useYear", required = false) String useYear,
                                                       @ApiParam("关键字搜索") @RequestParam(value = "searchData", required = false) String searchData,
                                                       @ApiParam("关键字搜索") @RequestParam(value = "versionId", required = false) Integer versionId,
                                                       @ApiParam("敞口id") @RequestParam(value = "masterId", required = false) Integer masterId,
                                                       @ApiParam("版本id") @RequestParam(value = "tarType", required = false) String tarType,
                                                       @ApiParam("当前页码") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                       @ApiParam("当前页面数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {

        Page<TranspreQualinfo3rdPageVO> voPage = transpreQualinfo3rdService.page(useYear, tarType, masterId, versionId, searchData, page, pageSize);
        return MetaR.ok(voPage);
    }

}
