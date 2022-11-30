package com.deloitte.additional.recording.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import com.deloitte.additional.recording.dto.ModerQuanAndQualFactorDto;
import com.deloitte.additional.recording.request.TranspreQualinfo3rdRequest;
import com.deloitte.additional.recording.service.TranspreQualinfo3rdService;
import com.deloitte.additional.recording.service.center.ModelQualQuanGovFactorService;
import com.deloitte.additional.recording.vo.qualinfo3rd.TranspreQualinfo3rdPageVO;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    private ModelQualQuanGovFactorService modelQualQuanGovFactorService;

    @ApiOperation("分页查询指标映射分页列表")
    @GetMapping("getMapQualFactorPaging")
    public MetaR<Page<TranspreQualinfo3rdPageVO>> page(@ApiParam("年份") @RequestParam(value = "useYear", required = false) String useYear,
                                                       @ApiParam("关键字搜索") @RequestParam(value = "searchData", required = false) String searchData,
                                                       @ApiParam("关键字搜索") @RequestParam(value = "versionId", required = false) Integer versionId,
                                                       @ApiParam("敞口id") @RequestParam(value = "masterId", required = false) Integer masterId,
                                                       @ApiParam("版本id") @RequestParam(value = "tarType", required = false) String tarType,
                                                       @ApiParam("当前页码") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                       @ApiParam("当前页面数量") @RequestParam(value = "pageSize", required = false,
                                                               defaultValue = "10") Integer pageSize) {

        Page<TranspreQualinfo3rdPageVO> voPage = transpreQualinfo3rdService.page(useYear, tarType, masterId, versionId, searchData, page, pageSize);
        return MetaR.ok(voPage);
    }


    @ApiOperation("导出指标映射列表为EXCEL")
    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse response
            , @ApiParam("年份") @RequestParam(value = "useYear", required = false) String useYear,
                             @ApiParam("关键字搜索") @RequestParam(value = "searchData", required = false) String searchData,
                             @ApiParam("关键字搜索") @RequestParam(value = "versionId", required = false) Integer versionId,
                             @ApiParam("敞口id") @RequestParam(value = "masterId", required = false) Integer masterId,
                             @ApiParam("版本id") @RequestParam(value = "tarType", required = false) String tarType) {
        List<TranspreQualinfo3rdPageVO> list = transpreQualinfo3rdService.exportExcelData(useYear, searchData, versionId, masterId, tarType);
        ExcelUtil<TranspreQualinfo3rdPageVO> util = new ExcelUtil<>(TranspreQualinfo3rdPageVO.class);
        util.exportExcel(response, list, "指标映射表");
    }

    @ApiOperation("获取中心库指标下拉框选择列表")
    @GetMapping("getMapQualListFromDataCenter")
    public MetaR<List<ModerQuanAndQualFactorDto>> getQualListFromDataCenter(@ApiParam("敞口id") @RequestParam("masterId") Integer masterId, @ApiParam("版本id") @RequestParam("versionId") Integer versionId, @ApiParam("年份") @RequestParam("useYear") String useYear, @ApiParam("前缀") @RequestParam("prefix") String prefix, @ApiParam("类型：1-定性 2-定量 3-政府") @RequestParam("type") String type) {
        //根据敞口和版本查询到中心库对应的敞口code和前缀
        TranspreQualinfo3rd qualinfo3rd = transpreQualinfo3rdService.selectByVersionAndMaster(versionId, masterId, useYear);
        if (qualinfo3rd == null) {
            return MetaR.ok();
        }
        List<ModerQuanAndQualFactorDto> list = modelQualQuanGovFactorService.getQualListFromDataCenter(qualinfo3rd.getTarMid(), qualinfo3rd.getPrefix(), type);

        return MetaR.ok(list);
    }

    @ApiOperation("新增映射指标")
    @PostMapping("addMap")
    public MetaR add(@RequestBody @Valid TranspreQualinfo3rdRequest request) {

        //获得中心库敞口code 建议直接从前端传过来
        String code = modelQualQuanGovFactorService.getQualFromDataCenter(request.getPrefix(), request.getTarQualid(), request.getTarType());
        transpreQualinfo3rdService.add(request, code);
        return MetaR.ok("新增成功");
    }

    @ApiOperation("新增映射指标")
    @PostMapping("updateMap")
    public MetaR update(@RequestBody @Valid TranspreQualinfo3rdRequest request) {

        //获得中心库敞口code
        String code = modelQualQuanGovFactorService.getQualFromDataCenter(request.getPrefix(), request.getTarQualid(), request.getTarType());
        transpreQualinfo3rdService.update(request, code);
        return MetaR.ok("更新成功");
    }

    @ApiOperation("删除指标映射")
    @GetMapping("deleteMapping")
    public MetaR delete(@RequestParam("ids") Integer ids) {

        boolean boo = transpreQualinfo3rdService.removeById(ids);
        if (!boo) {
            throw new GlobalException("删除失败，请稍后再试！");
        }
        return MetaR.ok("删除成功");

    }
}
