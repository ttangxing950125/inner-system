package com.deloitte.additional.recording.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.additional.recording.domain.MetaEvdData;
import com.deloitte.additional.recording.dto.GetMetaEvdDatasDto;
import com.deloitte.additional.recording.dto.GetMetaQualDatasDto;
import com.deloitte.additional.recording.dto.GetTaskTargetvalPageListDto;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;

import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.additional.recording.service.biz.DataListBizComponentService;
import com.deloitte.additional.recording.util.ApplicationContextHolder;
import com.deloitte.additional.recording.vo.DataListGetDropDownBoxVo;
import com.deloitte.additional.recording.vo.excel.EvidenceDataExcelVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.rocketmq.common.filter.impl.Op;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/07/15:57
 * @Description: 数据清单 通用入口
 */
@Slf4j
@RestController
@RequestMapping("dataList")
public class DataListController extends BaseController {

    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;
    @Resource
    private PrsModelMasterService prsModelMasterService;
    @Resource
    private SysDictDataService sysDictDataService;
    @Resource
    private DataListBizComponentService dataListBizService;
    @Resource(name = "commonTaskExecutor")
    private ThreadPoolTaskExecutor commonTaskExecutor;

    /**
     * TODO 该接口 不在维护
     * 获取下拉框 月份 版本、敞口
     * 不通用 推荐使用 如果获取年份使用 getSysDictDataByTimeValue()
     * 版本必须传递参数年份 使用getVersionByTimeValue()
     * 敞口数据 必须传递 版本 使用
     *
     * @param request
     * @param response
     * @return DataListGetDropDownBoxVo
     * {@link com.deloitte.additional.recording.vo.DataListGetDropDownBoxVo}
     * //获取下拉框 版本 带上年份
     * @see DataListController#getVersionByTimeValue(HttpServletRequest, HttpServletResponse, String)
     * //获取下拉框 日期
     * @see DataListController#getSysDictDataByTimeValue(HttpServletRequest, HttpServletResponse)
     * //获取下拉框  敞口 带上版本Id
     * @see DataListController#getPrsModelMasterByPrjId(HttpServletRequest, HttpServletResponse, Integer)
     */
    @Deprecated
    @PostMapping("/getDropDownBox")
    public R getDropDownBox(HttpServletRequest request, HttpServletResponse response) {
        DataListGetDropDownBoxVo dataListGetDropDownBoxVo = new DataListGetDropDownBoxVo();
        List<String> allPrsProjectVersions = prsProjectVersionsService.findAllPrsProjectVersions();
        dataListGetDropDownBoxVo.setPrsProjectVersionList(allPrsProjectVersions);
        List<Map<String, Object>> prsModelMasterLists = prsModelMasterService.finAllPrsModelMaster();
        dataListGetDropDownBoxVo.setPrsModelMasterLists(prsModelMasterLists);
        List<Map<String, Object>> sysDictListData = sysDictDataService.finAllsysDictData();
        dataListGetDropDownBoxVo.setSysDitMonthLists(sysDictListData);
        return R.ok(dataListGetDropDownBoxVo);
    }

    /**
     * 获取下拉框 版本 带上年份
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getVersionByTimeValue")
    public R getVersionByTimeValue(HttpServletRequest request, HttpServletResponse response, String timeValue) {
        Optional.ofNullable(timeValue).orElseThrow(() -> new ServiceException("获取版本数据年份不可以为空"));
        return prsProjectVersionsService.getVersionByTimeValue(timeValue);
    }

    /**
     * 获取 下拉框 年份
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getSysDictDataByTimeValue")
    public R getSysDictDataByTimeValue(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> sysDictListData = sysDictDataService.finAllsysDictData();
        return R.ok(sysDictListData);
    }

    /**
     * 获取 下拉框 敞口数据 带版本Id
     * @param request
     * @param response
     * @param prjId
     * @return
     */
    @GetMapping("/getPrsModelMasterByPrjId")
    public R getPrsModelMasterByPrjId(HttpServletRequest request, HttpServletResponse response, Integer prjId) {
        Optional.ofNullable(prjId).orElseThrow(() -> new ServiceException("版本Id不可以为空"));
        return prsModelMasterService.finAllPrsModelMasterByPrjId(prjId);
    }

    /**
     * 获取分页数据
     * @param dto
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/dataListPage")
    public R queryByPage(@RequestBody GetTaskTargetvalPageListDto dto, HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(dto).orElseThrow(() -> new ServiceException("参数不为空"));
        Optional.ofNullable(dto.getModelCode()).orElseThrow(() -> new ServiceException("敞口编码不为空"));
        Optional.ofNullable(dto.getYear()).orElseThrow(() -> new ServiceException("年份不可以为空"));
        Optional.ofNullable(dto.getId()).orElseThrow(() -> new ServiceException("版本Id不可以为空"));
        return R.ok(dataListBizService.queryByPage(dto));
    }

    /**
     * 获取指标头
     * @param modelCode 敞口Code
     * @param timeValue 年份
     * @param name      版本
     * @return
     */
    @Deprecated
    @RequestMapping("/queryByPageStatsdetail")
    public R queryByPageStatsdetail(String modelCode, String timeValue, Integer Id) {
        return R.ok(dataListBizService.queryByPageStatsdetail(modelCode, timeValue, Id));
    }

    /**
     * 自定义查询 版本敞口
     * @param ids 年份 支持多选
     * @return
     */
    @RequestMapping("/getCustomEntity/{ids}")
    public R getCustomEntity(@PathVariable Integer[] ids) {
        Optional.ofNullable(ids).orElseThrow(() -> new ServiceException("参数不可以为空"));
        return R.ok(prsProjectVersionsService.finPrsProjectVersionsByYear(ids));
    }

    /**
     * 自定义查询 版本敞口查询公司
     * @param year
     * @param versionId
     * @param industryId
     * @return
     */
    @RequestMapping("/getCustomEntityInfoByVersionIdAndModelId")
    public R getCustomEntityInfoByVersionIdAndModelId(String year, String qualCode, String verMasId) {
        Optional.ofNullable(year).orElseThrow(() -> new ServiceException("年份不可以为空"));
        Optional.ofNullable(qualCode).orElseThrow(() -> new ServiceException("指标编码不可以为空"));
        Optional.ofNullable(verMasId).orElseThrow(() -> new ServiceException("verMasId不可以为空"));
        return R.ok(prsProjectVersionsService.getCustomEntityInfoByVersionIdAndModelId(year, qualCode, verMasId));
    }

    /**
     * 自定义查询 获取下一步
     * @param year      年份
     * @param versionId 版本Id =prjId
     * @param modelCode 敞口编码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getCustomQualEntityInfoByVersionIdAndModelIdNoPage")
    public R getCustomQualByVersionIdAndModelCodeNotPage(String year, String versionId, String modelCode, HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(year).orElseThrow(() -> new ServiceException("年份参数不可以为空"));
        Optional.ofNullable(versionId).orElseThrow(() -> new ServiceException("版本Id不可以为空"));
        Optional.ofNullable(modelCode).orElseThrow(() -> new ServiceException("不可以为空"));
        return R.ok(dataListBizService.getCustomQualByVersionIdAndModelCodeNotPage(year, versionId, modelCode));
    }

    /**
     * 获取Evdence数据分页
     * @param requestParam 请求参数
     * @param request
     * @param response
     * @return
     * @see GetMetaEvdDatasDto
     */
    @RequestMapping("/evidence/getMetaEvdDatas")
    public R getMetaEvdDatas(@RequestBody GetMetaEvdDatasDto requestParam, HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(requestParam).orElseThrow(() -> new ServiceException("参数不为空"));
        return R.ok(dataListBizService.getMetaEvdDatas(requestParam));
    }

    /**
     * 获取指标数据 带分页
     * @param getMetaQualDatasDto
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/qualData/getMetaQualDatas")
    public R getMetaQualDatas(@RequestBody GetMetaQualDatasDto getMetaQualDatasDto, HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(getMetaQualDatasDto).orElseThrow(() -> new ServiceException("参数不为空"));
        return R.ok(dataListBizService.getMetaQualDatas(getMetaQualDatasDto));
    }

    /**
     * evidence Excel 导出
     * @param metaEvdDatasDto
     * @param request
     * @param response
     * @return
     * @desc: 优先采用阿里巴巴的EasyExcel
     * {@link com.alibaba.excel.ExcelWriter}
     * @see com.alibaba.excel
     */
    @RequestMapping("/evidence/exportEvdTableData")
    public void exportEvdTableData(@RequestBody GetMetaEvdDatasDto metaEvdDatasDto, HttpServletRequest request, HttpServletResponse response) {
        String format = DateUtil.format(new Date(), "yyyy-MM-dd");
        String filename = "evidence数据" + format;
        try {
            IPage<MetaEvdData> pageResult = this.dataListBizService.getMetaEvdDatas(metaEvdDatasDto);
            final List<MetaEvdData> records = pageResult.getRecords();
            if (CollUtil.isEmpty(records)) {
                throw new ServiceException("暂无数据！！！！");
            }
            List<EvidenceDataExcelVo> evidenceDataExcelVoList = records.stream().map(e -> CompletableFuture.supplyAsync(() -> {
                EvidenceDataExcelVo vo = new EvidenceDataExcelVo();
                BeanUtil.copyProperties(e, vo);
                return vo;
            }, commonTaskExecutor)).map(CompletableFuture::join).collect(Collectors.toList());

            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());

            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");

            //设置 excelwirter的基本参数
            ExcelWriter build = new ExcelWriterBuilder()
                    .autoCloseStream(true)
                    .head(EvidenceDataExcelVo.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .file(response.getOutputStream())
                    .build();

            //sheet格式
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetNo(1);
            writeSheet.setSheetName("evidence分布数据");

            build.write(evidenceDataExcelVoList, writeSheet);

            build.finish();


        } catch (IOException e) {
            log.error("导出evidence出现异常>>>>>:{}", e);
            throw new ServiceException("导出evdence出现异常 请联系管理员");
        }
    }
}
