package com.deloitte.additional.recording.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import com.deloitte.additional.recording.handler.ExcelHandler;
import com.deloitte.additional.recording.service.PrsQualEfficiencyService;
import com.deloitte.additional.recording.util.EasyExcelImportUtils;
import com.deloitte.additional.recording.vo.qual.PrsQualEfficiencyVO;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述
 */
@RestController
@RequestMapping("prsQualEfficiency")
@Api(tags = "指标任务控制层")
public class PrsQualEfficiencyController {

    @Autowired
    private PrsQualEfficiencyService prsQualEfficiencyService;


    @ApiOperation("分页查询指标列表")
    @GetMapping("page")
    public MetaR<Page<PrsQualEfficiencyVO>> page(@RequestHeader("Authorization") String token, @ApiParam("版本id") @RequestParam(value = "versionId", required = false) Integer versionId,
                                                 @ApiParam("敞口code") @RequestParam(value = "modelCode", required = false) String modelCode,
                                                 @ApiParam("年份") @RequestParam(value = "userYear", required = false) String userYear,
                                                 @ApiParam("指标code,用,隔开，例如：1,2,3") @RequestParam("qualCodes") String qualCodes,
                                                 @ApiParam("搜索关键字") @RequestParam("searchKey") String searchKey,
                                                 @ApiParam("风险级别:1-高风险 2-中风险 3-低风险 4-无风险") @RequestParam("riskLevel") Integer riskLevel,
                                                 @ApiParam("当前页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @ApiParam("当前页面数据量") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<PrsQualEfficiencyVO> voPage = prsQualEfficiencyService.page(token, versionId, modelCode, qualCodes, searchKey, riskLevel, userYear, page, pageSize);
        return MetaR.ok(voPage);
    }


    @ApiOperation("根据选择的指标下载excel模板")
    @GetMapping("downLoad")
    public void downLoadTemplate(HttpServletResponse response, @RequestHeader("Authorization") String token,
                                 @ApiParam("指标codes，用，分隔") @RequestParam("codes") String qualCodes) {

        long exportStartTime = System.currentTimeMillis();
        Map<String, List<PrsQualDataExcelDto>> map = prsQualEfficiencyService.findByCodes(qualCodes, token);
        //获取模板(模板你可以放在任何位置，前提是你能获取到。这里放在resource下)
        ClassPathResource couponOrderTemplateResource = new ClassPathResource("任务操作模板.xlsx");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            String excelFileName = URLEncoder.encode("任务操作", "UTF-8")
                    .replaceAll("\\+", "%20");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            // attachment这个代表要下载的，如果去掉就直接打开了(attachment-作为附件下载,inline-在线打开)
            // excelFileName是文件名，另存为或者下载时，为默认的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + excelFileName + ".xlsx");
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        InputStream templateInputStream = null;
        ExcelWriter excelWriter = null;
        ServletOutputStream outputStream = null;
        WriteSheet sheet = null;
        try {
            outputStream = response.getOutputStream();
            templateInputStream = couponOrderTemplateResource.getInputStream();
            // 指定下拉框
            HashMap<Integer, String[]> dropDownMap = new HashMap<>();
            String[] status = {"分配", "打回", "通过", "改派", "-"};
            dropDownMap.put(5, status);
            ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outputStream, PrsQualDataExcelDto.class).registerWriteHandler(EasyExcelImportUtils.getDefaultHorizontalCellStyleStrategy()).registerWriteHandler(new ExcelHandler(dropDownMap));
            excelWriter = excelWriterBuilder.build();
        } catch (IOException e) {
            throw new GlobalException("获取模板失败");
        }
        Iterator<Map.Entry<String, List<PrsQualDataExcelDto>>> iterator = map.entrySet().iterator();
        int sheetNo = 0;//表示当前从第一个sheet开始写入
        ExcelWriterSheetBuilder excelWriterSheetBuilder;
        WriteSheet writeSheet;
        //循环map 有几个指标就写入几个sheet
        while (iterator.hasNext()) {
            Map.Entry<String, List<PrsQualDataExcelDto>> next = iterator.next();
            String key = next.getKey();
            List<PrsQualDataExcelDto> list = next.getValue();
            excelWriterSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
            excelWriterSheetBuilder.sheetNo(sheetNo);
            excelWriterSheetBuilder.sheetName(key);
            writeSheet = excelWriterSheetBuilder.build();
            excelWriter.write(list, writeSheet);
            sheetNo += 1;//sheet下表加一
        }
        excelWriter.finish();
        //关闭
        IOUtils.closeQuietly(templateInputStream);
        IOUtils.closeQuietly(outputStream);
        IOUtils.closeQuietly((Closeable) excelWriter);
        System.out.println("报表导出结束时间:" + new Date() + ";写入耗时: " + (System.currentTimeMillis() - exportStartTime) + "ms");
    }

    @ApiOperation("导入指标任务excel")
    @PostMapping("upload")
    @ApiImplicitParam(name = "file", value = "上传的文件", dataType = "java.io.File", required = true,
            allowMultiple = true, paramType = "query")
    public MetaR importExcel(@RequestPart @RequestParam("file") MultipartFile serviceFile, @RequestHeader("Authorization") String token) throws IOException {
        prsQualEfficiencyService.importExcel(serviceFile, token);
        return MetaR.ok("导入成功");
    }


}
