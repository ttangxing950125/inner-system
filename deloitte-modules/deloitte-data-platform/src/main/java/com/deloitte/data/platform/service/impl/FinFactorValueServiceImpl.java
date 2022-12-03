package com.deloitte.data.platform.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.datasource.annotation.Master_2;
import com.deloitte.data.platform.domian.EntityInfo;
import com.deloitte.data.platform.domian.FinFactorValue;
import com.deloitte.data.platform.domian.ModelEvidence;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.mapper.FinFactorValueMapper;
import com.deloitte.data.platform.service.IEntityInfoService;
import com.deloitte.data.platform.service.IFinFactorValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.data.platform.service.IModelRationFactorService;
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
 * 指标值表 通过跑批计算出指标结果存入该表 服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Log4j2
@Service
@Master_2
public class FinFactorValueServiceImpl extends ServiceImpl<FinFactorValueMapper, FinFactorValue> implements IFinFactorValueService {

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private IModelRationFactorService iModelRationFactorService;
    @Override
    public IPage<ApplyDataExtractionVo> getApplyDataExtractionPage(ApplyDataDto dto) {
        Set<String> factorCodes = null;
        if (dto.getKeyWord()!=null){
            factorCodes = iModelRationFactorService.getFactorCode(dto.getKeyWord());
        }
        IPage<ApplyDataExtractionVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ApplyDataExtractionVo> result = this.baseMapper.getApplyDataExtractionPage(page, dto,factorCodes);
        List<ApplyDataExtractionVo> records = result.getRecords();
        Set<String> codes = records.stream().map(ApplyDataExtractionVo::getCode).collect(Collectors.toSet());
        // 根据编码计算数据缺失率
        Map<String, String> dataMissRateMap = null;
        Map<String,ApplyDataExtractionVo> applyDataExtractionVoMap = null;
        if (codes.size()>0){
            dataMissRateMap = this.getDataMissRateMap(codes);
            applyDataExtractionVoMap = iModelRationFactorService.getApplyDataExtractionMap(codes);
        }
        if (dataMissRateMap!=null){
            for (ApplyDataExtractionVo record : records) {
                record.setDataMissRate(dataMissRateMap.get(record.getCode()));
                ApplyDataExtractionVo applyDataExtractionVo = applyDataExtractionVoMap.get(record.getCode());
                if (applyDataExtractionVo!=null){
                    record.setName(applyDataExtractionVo.getName());
                    record.setBusinessScene(applyDataExtractionVo.getBusinessScene());
                }
            }
        }
        return result;
    }

    @Override
    public void applyDataExport(HttpServletResponse response, ApplyDataExportDto dto) {
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
        Set<String> factorCodes = null;
        if (dto.getKeyWord()!=null){
            factorCodes = iModelRationFactorService.getFactorCode(dto.getKeyWord());
        }
        // 查询定量指标模型
        Map<String, ApplyDataExtractionVo> applyDataExtractionMap = iModelRationFactorService.getApplyDataExtractionMap(factorCodes);
        // 导出EXCEL
        ExcelWriter excelWriter = null;
        try {
            String fileName = "指标层数据提取"+System.currentTimeMillis()+".xlsx";
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/vnd.ms-excel");
            String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+fileNameUrl+";"+"filename*=utf-8''"+fileNameUrl);
            excelWriter = EasyExcel.write(fileName).build();
            // 查询导出数据
            List<ApplyDataExtractionExportVo> result =this.baseMapper.getApplyDataExport(dto,factorCodes);
            // 按照每个excel 100W处理
            int size = 1000000;
            int count = 0;
            int sheetNum = 0;
            List<ApplyDataExtractionExportVo> list = new ArrayList<>();
            for (ApplyDataExtractionExportVo applyDataExtractionExportVo : result) {
                EntityInfo entityInfo = entityInfoMap.get(applyDataExtractionExportVo.getEntityCode());
                applyDataExtractionExportVo.setEntityName(entityInfo != null ? entityInfo.getEntityName() : "");
                applyDataExtractionExportVo.setCreditCode(entityInfo != null ? entityInfo.getCreditCode() : "");
                applyDataExtractionExportVo.setYear(dto.getYears().get(0));
                applyDataExtractionExportVo.setReportDate(dto.getYears().get(0)+"-12-31");
                ApplyDataExtractionVo applyDataExtractionVo = applyDataExtractionMap.get(applyDataExtractionExportVo.getCode());
                applyDataExtractionExportVo.setName(applyDataExtractionVo!=null?applyDataExtractionVo.getName():"");
                list.add(applyDataExtractionExportVo);
                count++;
                if (count == size) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet" + sheetNum).head(ApplyDataExtractionExportVo.class).build();
                    excelWriter.write(list, writeSheet);
                    sheetNum++;
                    count = 0;
                    list.clear();
                }
            }
            // 不满100万直接处理
            if(count<size){
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet"+sheetNum).head(ApplyDataExtractionExportVo.class).build();
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
    public void applyDataDetailExport(HttpServletResponse response, ApplyDataDetailExportDto dto) {
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
                String fileName = "指标层数据详情提取"+System.currentTimeMillis()+".xlsx";
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setHeader("content-Type", "application/vnd.ms-excel");
                String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename="+fileNameUrl+";"+"filename*=utf-8''"+fileNameUrl);
                excelWriter = EasyExcel.write(fileName).build();
                // 从数据库查询数据
                List<ApplyDataExtractionDetailExportVo> result = this.baseMapper.getExportApplyDataDetail(dto,entityCodes);
                // 按照每个excel 100W处理
                int size = 1000000;
                int count = 0;
                int sheetNum = 0;
                List<ApplyDataExtractionDetailExportVo> list = new ArrayList<>();
                for (ApplyDataExtractionDetailExportVo applyDataExtractionDetailExportVo : result) {
                    EntityInfo entityInfo = entityInfoMap.get(applyDataExtractionDetailExportVo.getEntityCode());
                    applyDataExtractionDetailExportVo.setEntityName(entityInfo!=null?entityInfo.getEntityName():"");
                    applyDataExtractionDetailExportVo.setCreditCode(entityInfo!=null?entityInfo.getCreditCode():"");
                    applyDataExtractionDetailExportVo.setYear(dto.getYears().get(0));
                    applyDataExtractionDetailExportVo.setReportDate(dto.getYears().get(0)+"-12-31");
                    list.add(applyDataExtractionDetailExportVo);
                    count++;
                    // 如果超过100万 分Sheet处理
                    if (count==size){
                        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet"+sheetNum).head(ApplyDataExtractionDetailExportVo.class).build();
                        excelWriter.write(list, writeSheet);
                        sheetNum++;
                        count=0;
                        list.clear();
                    }
                }
                // 不满100万直接处理
                if(count<size){
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet"+sheetNum).head(ApplyDataExtractionDetailExportVo.class).build();
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
        List<ApplyDataExtractionVo> result = this.baseMapper.getDataMissRate(codes);
        return result.stream().collect(Collectors.toMap(ApplyDataExtractionVo::getCode, ApplyDataExtractionVo::getDataMissRate));
    }

    @Override
    public IPage<ApplyDataExtractionDetailVo> getApplyDataExtractionDetailPage(ApplyDataDto dto){
        IPage<ApplyDataExtractionDetailVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ApplyDataExtractionDetailVo> result = this.baseMapper.getApplyDataExtractionDetailPage(page, dto);
        List<ApplyDataExtractionDetailVo> records = result.getRecords();
        if (records.size()>0) {
            Set<String> entityCodes = records.stream().map(ApplyDataExtractionDetailVo::getEntityCode).collect(Collectors.toSet());
            // 根据主题代码、主题名称
            Map<String, EntityInfo> configMap = iEntityInfoService.getEntityInfo(entityCodes);
            for (ApplyDataExtractionDetailVo applyDataExtractionDetailVo : records) {
                EntityInfo entityInfo = configMap.get(applyDataExtractionDetailVo.getEntityCode());
                if (entityInfo != null) {
                    applyDataExtractionDetailVo.setEntityName(entityInfo.getEntityName());
                    applyDataExtractionDetailVo.setEntityCode(entityInfo.getCreditCode());
                }
            }
        }
        return result;
    }

    @Override
    public IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewApplyPage(IPage<StatisticalDataAnalysisVo.OverviewVo> page, StatisticalDataAnalysisDto.OverviewDto dto) {
        return this.baseMapper.getOverviewApplyPage(page,dto);
    }

    @Override
    public List<String> getYears() {
        return this.baseMapper.getYears();
    }
}
