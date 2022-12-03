package com.deloitte.data.platform.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.datasource.annotation.Master_3;
import com.deloitte.data.platform.domian.EntityInfo;
import com.deloitte.data.platform.domian.EvidenceData;
import com.deloitte.data.platform.domian.FinancialDataConfig;
import com.deloitte.data.platform.domian.ModelEvidence;
import com.deloitte.data.platform.dto.MiddleDataDetailExportDto;
import com.deloitte.data.platform.dto.MiddleDataDto;
import com.deloitte.data.platform.dto.MiddleDataExportDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.mapper.EvidenceDataMapper;
import com.deloitte.data.platform.service.IEntityInfoService;
import com.deloitte.data.platform.service.IEvidenceDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.data.platform.service.IModelEvidenceService;
import com.deloitte.data.platform.vo.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 德勤Evidence数据表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Log4j2
@Service
@Master_3
public class EvidenceDataServiceImpl extends ServiceImpl<EvidenceDataMapper, EvidenceData> implements IEvidenceDataService {

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private IModelEvidenceService iModelEvidenceService;

    @Override
    public IPage<MiddleDataExtractionVo> getMiddleDataExtractionPage(MiddleDataDto dto) {
        Page<MiddleDataExtractionVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        page.setOptimizeCountSql(false);
        IPage<MiddleDataExtractionVo> result = this.baseMapper.getMiddleDataExtractionPage(page, dto);
        List<MiddleDataExtractionVo> records = result.getRecords();
        Set<String> codes = records.stream().map(MiddleDataExtractionVo::getCode).collect(Collectors.toSet());
        // 根据编码计算数据缺失率
        Map<String, String> dataMissRateMap = null;
        if (codes.size()>0){
            dataMissRateMap = this.getDataMissRateMap(codes);
        }
        if (dataMissRateMap!=null) {
            for (MiddleDataExtractionVo record : records) {
                record.setDataMissRate(dataMissRateMap.get(record.getCode()));
            }
        }
        return result;
    }

    @Override
    public void middleDataExport(HttpServletResponse response, MiddleDataExportDto dto) {
        // 查询实体信息
        Map<String, EntityInfo> entityInfoMap;
        if (StringUtils.isEmpty(dto.getEntityCode())){
            return;
        }else{
            HashSet<String> codes = new HashSet<>();
            codes.add(dto.getEntityCode());
            entityInfoMap = iEntityInfoService.getEntityInfo(codes);
            if (entityInfoMap.size() == 0) {
                return;
            }
        }
        //  查询Evidence模型配置
        Set<String> modelEvidenceCodes = iModelEvidenceService.getModelEvidenceCode(dto.getKeyWord());
        Map<String, ModelEvidence> modelEvidenceMap= iModelEvidenceService.getModelEvidenceMap(dto.getKeyWord());
        // 导出EXCEL
        ExcelWriter excelWriter = null;
        try {
            String fileName = "中间层数据提取"+System.currentTimeMillis()+".xlsx";
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/vnd.ms-excel");
            String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+fileNameUrl+";"+"filename*=utf-8''"+fileNameUrl);
            excelWriter = EasyExcel.write(fileName).build();
            // 查询导出数据
            List<MiddleDataExtractionExportVo> result = this.baseMapper.getExportMiddleData(dto,modelEvidenceCodes);
            // 按照每个excel 100W处理
            int size = 1000000;
            int count = 0;
            int sheetNum = 0;
            List<MiddleDataExtractionExportVo> list = new ArrayList<>();
            for (MiddleDataExtractionExportVo middleDataExtractionExportVo : result) {
                EntityInfo entityInfo = entityInfoMap.get(middleDataExtractionExportVo.getEntityCode());
                middleDataExtractionExportVo.setEntityName(entityInfo != null ? entityInfo.getEntityName() : "");
                middleDataExtractionExportVo.setCreditCode(entityInfo != null ? entityInfo.getCreditCode() : "");
                middleDataExtractionExportVo.setYear(dto.getYears().get(0));
                ModelEvidence modelEvidence = modelEvidenceMap.get(middleDataExtractionExportVo.getCode());
                middleDataExtractionExportVo.setName(modelEvidence!=null?modelEvidence.getEvidenceName():"");
                list.add(middleDataExtractionExportVo);
                count++;
                if (count == size) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet" + sheetNum).head(MiddleDataExtractionExportVo.class).build();
                    excelWriter.write(list, writeSheet);
                    sheetNum++;
                    count = 0;
                    list.clear();
                }
            }
            // 不满100万直接处理
            if(count<size){
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet"+sheetNum).head(MiddleDataExtractionExportVo.class).build();
                excelWriter.write(list, writeSheet);
                list.clear();
            }
        } catch (Exception e){
            log.info("导出Excel异常！",e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public void middleDataDetailExport(HttpServletResponse response, MiddleDataDetailExportDto dto) {
        try {
            // 查询实体信息
            Map<String, EntityInfo> entityInfoMap;
            if (StringUtils.isNotEmpty(dto.getEntityCode())){
                HashSet<String> codes = new HashSet<>();
                codes.add(dto.getEntityCode());
                entityInfoMap = iEntityInfoService.getEntityInfo(codes);
                if (entityInfoMap.size()==0){
                    return;
                }
            }else{
                entityInfoMap = iEntityInfoService.getEntityInfo(null);
            }
            // 查询企业主体代码
            Set<String> entityCodes = new HashSet<>();
            if (StringUtils.isNotBlank(dto.getEntityCode())){
                entityCodes.add(dto.getEntityCode());
            }
            List<EntityInfo> entityInfoList = iEntityInfoService.getEntityInfoByName(dto.getKeyWord());
            if (entityInfoList!=null){
                entityCodes = entityInfoList.stream().map(EntityInfo::getEntityCode).collect(Collectors.toSet());
            }
            ExcelWriter excelWriter = null;
            try {
                String fileName = "中间层数据详情提取"+System.currentTimeMillis()+".xlsx";
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setHeader("content-Type", "application/vnd.ms-excel");
                String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename="+fileNameUrl+";"+"filename*=utf-8''"+fileNameUrl);
                excelWriter = EasyExcel.write(fileName).build();
                // 从数据库查询数据
                List<MiddleDataExtractionDetailExportVo> result = this.baseMapper.getExportMiddleDataDetail(dto,entityCodes);
                // 按照每个excel 100W处理
                int size = 1000000;
                int count = 0;
                int sheetNum = 0;
                List<MiddleDataExtractionDetailExportVo> list = new ArrayList<>();
                for (MiddleDataExtractionDetailExportVo middleDataExtractionDetailExportVo : result) {
                    EntityInfo entityInfo = entityInfoMap.get(middleDataExtractionDetailExportVo.getEntityCode());
                    middleDataExtractionDetailExportVo.setEntityName(entityInfo!=null?entityInfo.getEntityName():"");
                    middleDataExtractionDetailExportVo.setCreditCode(entityInfo!=null?entityInfo.getCreditCode():"");
                    middleDataExtractionDetailExportVo.setYear(dto.getYears().get(0));
                    middleDataExtractionDetailExportVo.setReportDate(dto.getYears().get(0)+"-12-31");
                    list.add(middleDataExtractionDetailExportVo);
                    count++;
                    // 如果超过100万 分Sheet处理
                    if (count==size){
                        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet"+sheetNum).head(MiddleDataExtractionDetailExportVo.class).build();
                        excelWriter.write(list, writeSheet);
                        sheetNum++;
                        count=0;
                        list.clear();
                    }
                }
                // 不满100万直接处理
                if(count<size){
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet"+sheetNum).head(MiddleDataExtractionDetailExportVo.class).build();
                    excelWriter.write(list, writeSheet);
                    list.clear();
                }
            } finally {
                if (excelWriter != null) {
                    excelWriter.finish();
                }
            }
        } catch (Exception e) {
            log.info("导出Excel异常！", e);
        }
    }

    /**
     * 根据编码计算数据缺失率
     */
    public Map<String, String> getDataMissRateMap(Set<String> codes){
        List<MiddleDataExtractionVo> result = this.baseMapper.getDataMissRate(codes);
        return result.stream().collect(Collectors.toMap(MiddleDataExtractionVo::getCode, MiddleDataExtractionVo::getDataMissRate));
    }

    @Override
    public IPage<MiddleDataExtractionDetailVo> getMiddleDataExtractionDetailPage(MiddleDataDto dto){
        IPage<MiddleDataExtractionDetailVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<MiddleDataExtractionDetailVo> result = this.baseMapper.getMiddleDataExtractionDetailPage(page, dto);
        List<MiddleDataExtractionDetailVo> records = result.getRecords();
        if (records.size()>0) {
            Set<String> entityCodes = records.stream().map(MiddleDataExtractionDetailVo::getEntityCode).collect(Collectors.toSet());
            // 根据主题代码、主题名称
            Map<String, EntityInfo> configMap = iEntityInfoService.getEntityInfo(entityCodes);
            for (MiddleDataExtractionDetailVo middleDataExtractionDetailVo : records) {
                EntityInfo entityInfo = configMap.get(middleDataExtractionDetailVo.getEntityCode());
                if (entityInfo != null) {
                    middleDataExtractionDetailVo.setEntityName(entityInfo.getEntityName());
                    middleDataExtractionDetailVo.setEntityCode(entityInfo.getCreditCode());
                }
            }
        }
        return result;
    }

    @Override
    public IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewMiddlePage(IPage<StatisticalDataAnalysisVo.OverviewVo> page, StatisticalDataAnalysisDto.OverviewDto dto) {
        return this.baseMapper.getOverviewMiddlePage(page,dto);
    }

    @Override
    public List<String> getYears() {
        return this.baseMapper.getYears();
    }
}
