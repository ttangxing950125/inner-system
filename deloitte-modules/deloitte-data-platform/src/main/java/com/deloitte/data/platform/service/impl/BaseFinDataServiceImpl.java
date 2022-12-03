package com.deloitte.data.platform.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.script.ScriptUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.ListUtil;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.BaseFinData;
import com.deloitte.data.platform.domian.EntityInfo;
import com.deloitte.data.platform.domian.FinancialDataConfig;
import com.deloitte.data.platform.domian.ModelDataCheck;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.enums.HierarchyEnum;
import com.deloitte.data.platform.enums.SuggestSourceEnum;
import com.deloitte.data.platform.enums.WhetherEnum;
import com.deloitte.data.platform.mapper.BaseFinDataMapper;
import com.deloitte.data.platform.service.*;
import com.deloitte.data.platform.vo.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 德勤基础数据表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Log4j2
@Service
@Master_1
public class BaseFinDataServiceImpl extends ServiceImpl<BaseFinDataMapper, BaseFinData> implements IBaseFinDataService {

    @Resource
    private IFinancialDataConfigService iFinancialDataConfigService;

    @Resource
    private IFinFactorValueService iFinFactorValueService;

    @Resource
    private IEvidenceDataService iEvidenceDataService;

    @Resource
    private IDataPlatformConfigDictService iDataPlatformConfigDictService;

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private BaseFinDataMapper baseFinDataMapper;

    @Autowired
    private IModelDataCheckService modelDataCheckService;

    @Override
    public List<String> getBaseFinDataYears() {
        return this.baseFinDataMapper.getBaseFinDataYears();
    }

    @Override
    public IPage<BaseFinDataVo> getBaseFinDataPage(BaseDataDto dto) {
        IPage<BaseFinDataVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        IPage<BaseFinDataVo> result = this.baseMapper.getBaseFinDataPage(page, dto);
        List<BaseFinDataVo> records = result.getRecords();
        if (records.size() > 0) {
            Set<String> codes = records.stream().map(BaseFinDataVo::getCode).collect(Collectors.toSet());
            // 根据项目代码查询精度
            Map<String, FinancialDataConfig> configMap = iFinancialDataConfigService.getFinancialDataConfig(codes);
            // 查询数据优先级
            String dataPriority = iDataPlatformConfigDictService.getDataPriorityByParentCode();
            for (BaseFinDataVo record : records) {
                FinancialDataConfig financialDataConfig = configMap.get(record.getCode());
                if (financialDataConfig != null) {
                    record.setThresholdValue(financialDataConfig.getThresholdValue());
                    // 精度转换
                    if (financialDataConfig.getAccuracy() != null) {
                        record.setAccuracy(1 / Math.pow(10, financialDataConfig.getAccuracy()));
                    }
                }
                // 设置数据优先级
                record.setDataPriority(dataPriority);
                String isDataMiss = record.getIsDataMiss();
                // 处理推荐数据是否缺失
                if (StringUtils.isNotEmpty(isDataMiss)) {
                    record.setIsDataMiss(WhetherEnum.TRUE.getDesc());
                } else {
                    record.setIsDataMiss(WhetherEnum.FALSE.getDesc());
                }
                record.setIsThresholdExceeded(WhetherEnum.getDesc(record.getIsThresholdExceeded()));
                record.setIsArtificialRecording(WhetherEnum.getDesc(record.getIsArtificialRecording()));
                record.setIsSystemInspection(WhetherEnum.getDesc(record.getIsSystemInspection()));
                record.setIsArtificialInspection(WhetherEnum.getDesc(record.getIsArtificialInspection()));
            }
        }
        return result;
    }

    @Override
    public IPage<BaseDataExtractionVo> getBaseDataExtractionPage(BaseDataDto dto) {
        IPage<BaseFinDataVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        IPage<BaseDataExtractionVo> result = this.baseMapper.getBaseDataExtractionPage(page, dto);
        List<BaseDataExtractionVo> records = result.getRecords();
        if (records.size() > 0) {
            Set<String> codes = records.stream().map(BaseDataExtractionVo::getCode).collect(Collectors.toSet());
            // 根据项目代码查询精度
            Map<String, FinancialDataConfig> configMap = iFinancialDataConfigService.getFinancialDataConfig(codes);
            // 统计
            Map<String, BaseDataExtractionVo> rate = this.getBaseDataExtractionRateMap(codes, dto);
            for (BaseDataExtractionVo dataExtractionVo : records) {
                FinancialDataConfig financialDataConfig = configMap.get(dataExtractionVo.getCode());
                if (financialDataConfig != null) {
                    dataExtractionVo.setThresholdValue(financialDataConfig.getThresholdValue());
                }
                dataExtractionVo.setSuggestSource(SuggestSourceEnum.getSuggestSourceName(dataExtractionVo.getSuggestSource()));
                dataExtractionVo.setDataMissRate(rate.get(dataExtractionVo.getCode()) != null ? rate.get(dataExtractionVo.getCode()).getDataMissRate() : "");
                dataExtractionVo.setWindRate(rate.get(dataExtractionVo.getCode()) != null ? rate.get(dataExtractionVo.getCode()).getWindRate() : "");
                dataExtractionVo.setFlushRate(rate.get(dataExtractionVo.getCode()) != null ? rate.get(dataExtractionVo.getCode()).getFlushRate() : "");
                dataExtractionVo.setOcrRate(rate.get(dataExtractionVo.getCode()) != null ? rate.get(dataExtractionVo.getCode()).getFlushRate() : "");
                dataExtractionVo.setArtificialAddRecordRate(rate.get(dataExtractionVo.getCode()) != null ? rate.get(dataExtractionVo.getCode()).getArtificialAddRecordRate() : "");
            }
        }
        return result;
    }

    public Map<String, BaseDataExtractionVo> getBaseDataExtractionRateMap(Set<String> codes, BaseDataDto dto) {
        List<BaseDataExtractionVo> result = this.baseMapper.getBaseDataExtractionAllRate(codes, dto);
        return result.stream().collect(Collectors.toMap(BaseDataExtractionVo::getCode, Function.identity()));
    }

    @Override
    public IPage<DataExtractionDetailVo> getDataExtractionDetailPage(BaseDataDto dto) {
        IPage<DataExtractionDetailVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        IPage<DataExtractionDetailVo> result = this.baseMapper.getDataExtractionDetailPage(page, dto);
        List<DataExtractionDetailVo> records = result.getRecords();
        if (records.size() > 0) {
            Set<String> entityCodes = records.stream().map(DataExtractionDetailVo::getEntityCode).collect(Collectors.toSet());
            // 根据主题代码、主题名称
            Map<String, EntityInfo> configMap = iEntityInfoService.getEntityInfo(entityCodes);
            for (DataExtractionDetailVo dataExtractionDetailVo : records) {
                EntityInfo entityInfo = configMap.get(dataExtractionDetailVo.getEntityCode());
                if (entityInfo != null) {
                    dataExtractionDetailVo.setEntityName(entityInfo.getEntityName());
                    dataExtractionDetailVo.setEntityCode(entityInfo.getCreditCode());
                }
                SuggestSourceEnum suggestSourceEnum = SuggestSourceEnum.getSuggestSourceEnum(dataExtractionDetailVo.getSuggestSource());
                if (suggestSourceEnum != null) {
                    dataExtractionDetailVo.setSuggestSource(suggestSourceEnum.getName());
                }
            }
        }
        return result;
    }

    @Override
    public IPage<CodeEntityVo> getCodeEntityPage(CodeEntityDto dto) {
        // 根据关键字查询主题code
        List<EntityInfo> entityInfos = iEntityInfoService.getEntityInfoByName(dto.getKeyWord());
        Set<String> codes = null;
        if (entityInfos != null) {
            codes = entityInfos.stream().map(EntityInfo::getEntityCode).collect(Collectors.toSet());
        }
        IPage<CodeEntityVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<CodeEntityVo> result = this.baseMapper.getCodeEntityPage(page, dto, codes);
        List<CodeEntityVo> records = result.getRecords();
        if (records.size() > 0) {
            Set<String> entityCodes = records.stream().map(CodeEntityVo::getEntityCode).collect(Collectors.toSet());
            // 根据主题代码、主题名称
            Map<String, EntityInfo> configMap = iEntityInfoService.getEntityInfo(entityCodes);
            for (CodeEntityVo record : records) {
                EntityInfo entityInfo = configMap.get(record.getEntityCode());
                if (entityInfo != null) {
                    record.setEntityName(entityInfo.getEntityName());
                    record.setEntityCode(entityInfo.getEntityCode());
                    record.setCreditCode(entityInfo.getCreditCode());
                }
                record.setIsInspection(WhetherEnum.getDesc(record.getIsInspection()));
                record.setIsSystemInspection(WhetherEnum.getDesc(record.getIsSystemInspection()));
                record.setIsArtificialInspection(WhetherEnum.getDesc(record.getIsArtificialInspection()));
            }
        }
        return result;
    }

    @Override
    public void updateIsArtificialInspection(String year,Integer dataId) {
        this.baseMapper.updateIsArtificialInspection(year,dataId);
    }

    @Override
    public Map<String, QualityTestRateVo> getQualityTestRate(EntityInfoDto dto, Set<String> entityCodes) {
        List<QualityTestRateVo> result = this.getBaseMapper().getQualityTestRate(dto, entityCodes);
        Map<String, QualityTestRateVo> resultMap = new HashMap<>();
        result.forEach(a -> {

        });
        return result.stream().collect(Collectors.toMap(QualityTestRateVo::getEntityCode, Function.identity()));
    }

    @Override
    public IPage<BaseQualityVo> getBaseQualityPage(BaseQualityDto dto) {
        IPage<BaseFinDataVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        IPage<BaseQualityVo> result = this.baseMapper.getBaseQualityPage(page, dto);
        List<BaseQualityVo> records = result.getRecords();
        if (records.size() > 0) {
            Set<String> codes = records.stream().map(BaseQualityVo::getCode).collect(Collectors.toSet());
            // 根据项目代码查询精度
            Map<String, FinancialDataConfig> configMap = iFinancialDataConfigService.getFinancialDataConfig(codes);
            // 查询数据优先级
            String dataPriority = iDataPlatformConfigDictService.getDataPriorityByParentCode();
            for (BaseQualityVo record : records) {
                FinancialDataConfig financialDataConfig = configMap.get(record.getCode());
                if (financialDataConfig != null) {
                    // 值域
                    record.setThresholdValue(financialDataConfig.getThresholdValue());
                    // 精度转换
                    if (financialDataConfig.getAccuracy() != null) {
                        record.setAccuracy(1 / Math.pow(10, financialDataConfig.getAccuracy()));
                    }
                }
                // 设置数据优先级
                record.setDataPriority(dataPriority);
                record.setIsArtificialRecording(WhetherEnum.getDesc(record.getIsArtificialRecording()));
                record.setIsSystemInspection(WhetherEnum.getDesc(record.getIsSystemInspection()));
                record.setIsArtificialInspection(WhetherEnum.getDesc(record.getIsArtificialInspection()));
            }
        }
        return result;
    }

    @Override
    public IPage<StatisticalDataAnalysisVo.OverviewVo> overview(StatisticalDataAnalysisDto.OverviewDto dto) {
        IPage<StatisticalDataAnalysisVo.OverviewVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        // 如果层级为空，默认基础层
        if (dto.getHierarchy() == null) {
            dto.setHierarchy(HierarchyEnum.BASE.getCode());
        }
        IPage<StatisticalDataAnalysisVo.OverviewVo> result = null;
        if (dto.getHierarchy().equals(HierarchyEnum.BASE.getCode())) {
            // 如果时间为空 使用最近年份
            if (years == null) {
                years = new ArrayList<>();
                years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
                dto.setYears(years);
            }
            result = baseFinDataMapper.getOverviewPage(page, dto);
            List<StatisticalDataAnalysisVo.OverviewVo> records = result.getRecords();
            if (records.size() == 0) {
                return result;
            }
            List<String> entityCodes = ListUtil.getIdList(StatisticalDataAnalysisVo.OverviewVo.class, records, "entityCode");
            // 查询主体名称
            List<EntityInfo> entityInfos = iEntityInfoService.list(new QueryWrapper<EntityInfo>().in("entity_code", entityCodes));
            // 查询数据优先级
            String dataPriority = iDataPlatformConfigDictService.getDataPriorityByParentCode();
            for (StatisticalDataAnalysisVo.OverviewVo record : records) {
                // 设置精度
                record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
                record.setDataPriority(dataPriority);
                record.setHierarchy(dto.getHierarchy());
                record.setUseScenarios(dto.getBusinessScene());
                // 主体名称
                for (EntityInfo entityInfo : entityInfos) {
                    if (entityInfo.getEntityCode().equals(record.getEntityCode())) {
                        record.setEntityName(entityInfo.getEntityName());
                    }
                }
            }
        } else if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            // 如果时间为空 不进行时间筛选
            if (years == null) {
                years = new ArrayList<>();
                dto.setYears(years);
            }
            result = iEvidenceDataService.getOverviewMiddlePage(page, dto);
            List<StatisticalDataAnalysisVo.OverviewVo> records = result.getRecords();
            List<String> entityCodes = ListUtil.getIdList(StatisticalDataAnalysisVo.OverviewVo.class, records, "entityCode");
            // 查询主体名称
            if (entityCodes.size() > 0) {
                List<EntityInfo> entityInfos = iEntityInfoService.list(new QueryWrapper<EntityInfo>().in("entity_code", entityCodes));
                for (StatisticalDataAnalysisVo.OverviewVo record : records) {
                    record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
                    record.setHierarchy(dto.getHierarchy());
                    record.setUseScenarios(dto.getBusinessScene());
                    for (EntityInfo entityInfo : entityInfos) {
                        if (entityInfo.getEntityCode().equals(record.getEntityCode())) {
                            record.setEntityName(entityInfo.getEntityName());
                        }
                    }
                }
            }
        } else if (dto.getHierarchy().equals(HierarchyEnum.APPLY.getCode())) {
            // 如果时间为空 不进行时间筛选
            if (years == null) {
                years = new ArrayList<>();
                dto.setYears(years);
            }
            result = iFinFactorValueService.getOverviewApplyPage(page, dto);
            List<StatisticalDataAnalysisVo.OverviewVo> records = result.getRecords();
            List<String> entityCodes = ListUtil.getIdList(StatisticalDataAnalysisVo.OverviewVo.class, records, "entityCode");
            if (entityCodes.size() > 0) {
                // 查询主体名称
                List<EntityInfo> entityInfos = iEntityInfoService.list(new QueryWrapper<EntityInfo>().in("entity_code", entityCodes));
                for (StatisticalDataAnalysisVo.OverviewVo record : records) {
                    record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
                    record.setHierarchy(dto.getHierarchy());
                    record.setUseScenarios(dto.getBusinessScene());
                    for (EntityInfo entityInfo : entityInfos) {
                        if (entityInfo.getEntityCode().equals(record.getEntityCode())) {
                            record.setEntityName(entityInfo.getEntityName());
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.printf(String.valueOf(1 / Math.pow(10, Double.parseDouble("1"))));
    }

    @Override
    public StatisticalDataAnalysisVo.CoverageVo overviewSourceCoverage(StatisticalDataAnalysisDto.OverviewDto dto) {
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        StatisticalDataAnalysisVo.CoverageVo vo;
        if (dto.getCoverageType() == null || dto.getCoverageType() == 1) {
            vo = baseFinDataMapper.getOverviewSourceCoverage(dto);
        } else {
            vo = baseFinDataMapper.getOverviewSourceCoverageSuggest(dto);
        }
        List<String> title = new ArrayList<>();
        title.add("wind");
        title.add("同花顺");
        title.add("ocr自动化");
        title.add("人工补录");
        List<String> value = new ArrayList<>();
        if (vo == null) {
            vo = new StatisticalDataAnalysisVo.CoverageVo();
        }
        value.add(StringUtils.isBlank(vo.getWindRate()) ? "-" : vo.getWindRate());
        value.add(StringUtils.isBlank(vo.getFlushRate()) ? "-" : vo.getFlushRate());
        value.add(StringUtils.isBlank(vo.getOcrRate()) ? "-" : vo.getOcrRate());
        value.add(StringUtils.isBlank(vo.getArtificialAddRecordRate()) ? "-" : vo.getArtificialAddRecordRate());
        vo.setTitle(title);
        vo.setValue(value);
        return vo;
    }

    @Override
    public StatisticalDataAnalysisVo.BusinessScenarioMissRateVo businessScenarioMissRate(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        StatisticalDataAnalysisVo.BusinessScenarioMissRateVo vo = baseFinDataMapper.getBusinessScenarioMissRate(dto);
        List<String> title = new ArrayList<>();
        title.add("0%");
        title.add("0%-30%");
        title.add("30%-60%");
        title.add("60%-90%");
        title.add("90%-100%");
        title.add("100%");
        List<String> value = new ArrayList<>();
        if (vo == null) {
            vo = new StatisticalDataAnalysisVo.BusinessScenarioMissRateVo();
        }
        value.add(StringUtils.isBlank(vo.getMissRate1()) ? "-" : vo.getMissRate1());
        value.add(StringUtils.isBlank(vo.getMissRate2()) ? "-" : vo.getMissRate2());
        value.add(StringUtils.isBlank(vo.getMissRate3()) ? "-" : vo.getMissRate3());
        value.add(StringUtils.isBlank(vo.getMissRate4()) ? "-" : vo.getMissRate4());
        value.add(StringUtils.isBlank(vo.getMissRate5()) ? "-" : vo.getMissRate5());
        value.add(StringUtils.isBlank(vo.getMissRate6()) ? "-" : vo.getMissRate6());
        vo.setTitle(title);
        vo.setValue(value);
        return vo;
    }

    @Override
    public List<BaseFinData> listBaseFinData(List<ListBaseFinDataDto> dtoList) {
        List<BaseFinData> baseFinDataList = this.baseMapper.listInEntityCodeAndReportDate(dtoList);
        if (baseFinDataList == null) {
            baseFinDataList = new ArrayList<>();
        }
        return baseFinDataList;
    }

    @Override
    public IPage<ModelEvidenceVo.CalculationVo> getCalculationPage(IPage<ModelEvidenceVo.CalculationVo> page, Set<String> codes, String year) {
        return this.baseMapper.getCalculationPage(page, codes, year);
    }

    @Override
    public List<ModelEvidenceVo.FinDataVo> getCalculationAllData(Set<String> codes, Set<String> entityCodes, Set<String> reportDates, String year) {
        return this.baseMapper.getCalculationAllData(codes, entityCodes, reportDates, year);
    }

    @Override
    public IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page, ParameterConfigDto.ListDto dto) {
        return this.baseMapper.getParameterConfigListPage(page, dto);
    }

    @Override
    public List<StatisticalDataAnalysisVo.GetDataMenuVo> getDataMenu(StatisticalDataAnalysisDto.GetDataMenuDto dto) {
        return this.baseMapper.getDataMenu(dto);
    }

    @Override
    public List<String> years(StatisticalDataAnalysisDto.GetDataMenuDto dto) {
        // 如果层级为空，默认基础层
        if (dto.getHierarchy() == null) {
            dto.setHierarchy(HierarchyEnum.BASE.getCode());
        }
        List<String> years;
        if (dto.getHierarchy().equals(HierarchyEnum.BASE.getCode())) {
            years = this.baseMapper.getYears();
        } else if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            years = iEvidenceDataService.getYears();
        } else {
            years = iFinFactorValueService.getYears();
        }
        return years;
    }

    @Override
    public StatisticalDataAnalysisVo.BusinessScenarioMissRateAllVo missRateAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        List<String> years = dto.getYears();
        // 如果时间为空 使用最近年份
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        StatisticalDataAnalysisVo.BusinessScenarioMissRateAllVo vo = baseFinDataMapper.getBusinessScenarioMissRateAll(dto);
        vo.setNoArtificialInspection(100 - vo.getDataMissRate());
        return vo;
    }

    @Override
    public StatisticalDataAnalysisVo.RecordingAllVo recordingAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        // 如果时间为空 使用最近年份
        List<String> years = dto.getYears();
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        StatisticalDataAnalysisVo.RecordingAllVo vo = this.baseMapper.getRecordingAll(dto);
        vo.setRecordingIng(100-vo.getAlredyRecording());
        return vo;
    }

    @Override
    public StatisticalDataAnalysisVo.ArtificialRecordingBarVo artificialRecordingBar(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        // 如果时间为空 使用最近年份
        List<String> years = dto.getYears();
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        //查询所有基础层字典
        DataPlatformMenuVo dataPlatformConfigDicts = iDataPlatformConfigDictService.getBaseConfigDictSubordinate("base_data_dic");
        StatisticalDataAnalysisVo.ArtificialRecordingBarVo vo = new StatisticalDataAnalysisVo.ArtificialRecordingBarVo();
        List<String> title = new ArrayList<>();
        for(DataPlatformMenuVo configDict:dataPlatformConfigDicts.getChild()){
            title.add(configDict.getName());
        }
        //查询条数
//        Map<String,Object> map = this.baseMapper.getArtificialRecordingBar(dto,dataPlatformConfigDicts);
        vo.setTitle(title);
        List<String> value = new ArrayList<>();
        value.add("122");
        value.add("142");
        vo.setNoArtificialRecordinge(value);
        List<String> lreadyArtificialRecordi = new ArrayList<>();
        lreadyArtificialRecordi.add("164");
        lreadyArtificialRecordi.add("513");
        vo.setAlreadyArtificialRecordinge(lreadyArtificialRecordi);

//        vo.setValue(value);
        return vo;
    }

    @Override
    public StatisticalDataAnalysisVo.ArtificialRecordingCircularVo artificialRecordingCircular(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        // 如果时间为空 使用最近年份
        List<String> years = dto.getYears();
        if (years == null) {
            years = new ArrayList<>();
            years.add(DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN));
            dto.setYears(years);
        }
        StatisticalDataAnalysisVo.ArtificialRecordingCircularVo vo = new StatisticalDataAnalysisVo.ArtificialRecordingCircularVo();
        vo.setOcr(84.0);
        vo.setAlredyRecording(16.0);
        vo.setArtificialRecordIng(20.0);
        vo.setRecordingIng(30.0);
        vo.setOcrIng(10.0);
        vo.setDataCheckFailed(40.0);
        return vo;
    }

    @Override
    public void baseDataExport(HttpServletResponse response, BaseDataExportDto dto) {
        try {
            // 查询实体信息
            Map<String, EntityInfo> entityInfoMap;
            if (StringUtils.isNotEmpty(dto.getEntityCode())) {
                HashSet<String> codes = new HashSet<>();
                codes.add(dto.getEntityCode());
                entityInfoMap = iEntityInfoService.getEntityInfo(codes);
                if (entityInfoMap.size() == 0) {
                    return;
                }
            } else {
                entityInfoMap = iEntityInfoService.getEntityInfo(null);
            }
            // 根据关键字查询财务数据配置代码
            Set<String> financialCodes = iFinancialDataConfigService.getFinancialDataConfigCode(dto.getKeyWord());
            // 查询财务数据配置
            Map<String, FinancialDataConfig> financialDataConfigMap = iFinancialDataConfigService.getFinancialDataConfig(null);
            // 查询数据优先级
            String dataPriority = iDataPlatformConfigDictService.getDataPriorityByParentCode();
            ExcelWriter excelWriter = null;
            try {
                String fileName = "基础数据提取" + System.currentTimeMillis() + ".xlsx";
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType("application/vnd.ms-excel");
                String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + fileNameUrl + ";" + "filename*=utf-8''" + fileNameUrl);
                excelWriter = EasyExcel.write(fileName).build();
                // 从数据库查询数据
                List<BaseDataExtractionExportVo> result = this.baseMapper.getExportBaseData(dto.getYears().get(0), dto, financialCodes);
                // 按照每个excel 100W处理
                int size = 1000000;
                int count = 0;
                int sheetNum = 0;
                List<BaseDataExtractionExportVo> list = new ArrayList<>();
                for (BaseDataExtractionExportVo baseDataExtractionExportVo : result) {
                    EntityInfo entityInfo = entityInfoMap.get(baseDataExtractionExportVo.getEntityCode());
                    baseDataExtractionExportVo.setEntityName(entityInfo != null ? entityInfo.getEntityName() : "");
                    baseDataExtractionExportVo.setCreditCode(entityInfo != null ? entityInfo.getCreditCode() : "");
                    baseDataExtractionExportVo.setYear(dto.getYears().get(0));
                    FinancialDataConfig financialDataConfig = financialDataConfigMap.get(baseDataExtractionExportVo.getCode());
                    baseDataExtractionExportVo.setName(financialDataConfig != null ? financialDataConfig.getName() : "");
                    baseDataExtractionExportVo.setThresholdValue(financialDataConfig != null ? financialDataConfig.getThresholdValue() : "");
                    baseDataExtractionExportVo.setDataPriority(dataPriority);
                    list.add(baseDataExtractionExportVo);
                    count++;
                    if (count == size) {
                        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet" + sheetNum).head(BaseDataExtractionExportVo.class).build();
                        excelWriter.write(list, writeSheet);
                        sheetNum++;
                        count = 0;
                        list.clear();
                    }
                }
                // 不满100万直接处理
                if (count < size) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet" + sheetNum).head(BaseDataExtractionExportVo.class).build();
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

    @Override
    public void baseDataDetailExport(HttpServletResponse response, BaseDataDetailExportDto dto) {
        try {
            // 查询实体信息
            Map<String, EntityInfo> entityInfoMap;
            if (StringUtils.isNotEmpty(dto.getEntityCode())) {
                HashSet<String> codes = new HashSet<>();
                codes.add(dto.getEntityCode());
                entityInfoMap = iEntityInfoService.getEntityInfo(codes);
                if (entityInfoMap.size() == 0) {
                    return;
                }
            } else {
                entityInfoMap = iEntityInfoService.getEntityInfo(null);
            }
            // 查询数据优先级
            String dataPriority = iDataPlatformConfigDictService.getDataPriorityByParentCode();
            ExcelWriter excelWriter = null;
            try {
                String fileName = "基础数据详情提取" + System.currentTimeMillis() + ".xlsx";
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setHeader("content-Type", "application/vnd.ms-excel");
                String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + fileNameUrl + ";" + "filename*=utf-8''" + fileNameUrl);
                excelWriter = EasyExcel.write(fileName).build();
                // 从数据库查询数据
                List<BaseDataExtractionDetailExportVo> result = this.baseMapper.getExportBaseDetailData(dto.getYears().get(0), dto);
                // 按照每个excel 100W处理
                int size = 1000000;
                int count = 0;
                int sheetNum = 0;
                List<BaseDataExtractionDetailExportVo> list = new ArrayList<>();
                for (BaseDataExtractionDetailExportVo baseDataExtractionDetailExportVo : result) {
                    EntityInfo entityInfo = entityInfoMap.get(baseDataExtractionDetailExportVo.getEntityCode());
                    baseDataExtractionDetailExportVo.setEntityName(entityInfo != null ? entityInfo.getEntityName() : "");
                    baseDataExtractionDetailExportVo.setCreditCode(entityInfo != null ? entityInfo.getCreditCode() : "");
                    baseDataExtractionDetailExportVo.setYear(dto.getYears().get(0));
                    baseDataExtractionDetailExportVo.setDataPriority(dataPriority);
                    list.add(baseDataExtractionDetailExportVo);
                    count++;
                    // 如果超过100万 分Sheet处理
                    if (count == size) {
                        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet" + sheetNum).head(BaseDataExtractionDetailExportVo.class).build();
                        excelWriter.write(list, writeSheet);
                        sheetNum++;
                        count = 0;
                        list.clear();
                    }
                }
                // 不满100万直接处理
                if (count < size) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, "Sheet" + sheetNum).head(BaseDataExtractionDetailExportVo.class).build();
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
     * 财报数据校验
     *
     * @param dto
     */
    @Override
    public void check(BaseFinDataCheckDto dto) {
        Assert.notNull(dto.getYear(), "年份不能为空");
        //由于现在只有年报，所以直接处理
        LocalDate reportDate = LocalDate.parse(dto.getYear() + "-12-31");
        dto.setReportDate(reportDate);
        //由于数据量较大，这里采取手动分页的方式，多次查询处理
        IPage page = new Page(1, 100);
        //这里直接依赖数据库分页，不做单独计算分页效果
        IPage<String> entityCodePage = this.baseMapper.entityCodePage(page, dto.getYear(), reportDate, dto.getEntityCodeList());
        long pages = entityCodePage.getPages();
        for (int i = 1; i <= pages; i++) {
            if (i > 1) {
                page.setCurrent(i);
                entityCodePage = this.baseMapper.entityCodePage(page, dto.getYear(), reportDate, dto.getEntityCodeList());
            }

            List<BaseFinData> baseFinDataList = this.listBaseFinData(dto.getYear(), entityCodePage.getRecords());
            if (CollectionUtils.isEmpty(baseFinDataList)) {
                log.info("没有查询到数据");
                return;
            }
            Set<String> codeSet = new HashSet<>();
            List<BaseFinData> nowBaseFinDataList = new ArrayList<>();
            //这里直接把去年的数据转成map，避免后面多次循环，加快查询速度
            Map<String, String> lastBaseFinDataMap = new HashMap<>();
            for (BaseFinData baseFinData : baseFinDataList) {
                if (reportDate.compareTo(baseFinData.getReportDate()) == 0) {
                    codeSet.add(baseFinData.getCode());
                    nowBaseFinDataList.add(baseFinData);
                    continue;
                }
                //主体code + 科目code 作为 key
                String key = baseFinData.getEntityCode() + baseFinData.getCode();
                //获取推荐值
                String value = baseFinData.getSuggestValue();
                lastBaseFinDataMap.put(key, value);
            }
            //获取财务数据配置
            QueryWrapper<FinancialDataConfig> financialDataConfigWrapper = new QueryWrapper<>();
            financialDataConfigWrapper.lambda()
                    .select(FinancialDataConfig::getCode, FinancialDataConfig::getChangeRateUpper, FinancialDataConfig::getThresholdValue)
                    .eq(FinancialDataConfig::getIsDeleted, "0")
                    .in(FinancialDataConfig::getCode, codeSet);
            List<FinancialDataConfig> financialDataConfigList = this.iFinancialDataConfigService.list(financialDataConfigWrapper);
            List<String> passCodeList = this.checkThresholdValueAndChangeRate(nowBaseFinDataList, lastBaseFinDataMap, financialDataConfigList);
            if (CollectionUtils.isEmpty(passCodeList)) {
                //一个校验通过的都没，不再往下校验
                this.baseMapper.saveBatchCheckResultById(dto.getYear(), nowBaseFinDataList);
                return;
            }
            //获取数据校验模型
            QueryWrapper<ModelDataCheck> modelDataCheckWrapper = new QueryWrapper<>();
            modelDataCheckWrapper.lambda().select(ModelDataCheck::getCheckCode, ModelDataCheck::getCheckFormula)
                    .eq(ModelDataCheck::getIsDeleted, "0")
                    .in(ModelDataCheck::getCheckCode, passCodeList);
            List<ModelDataCheck> modelDataCheckList = modelDataCheckService.list(modelDataCheckWrapper);
            this.checkModelData(nowBaseFinDataList, modelDataCheckList);
            this.baseMapper.saveBatchCheckResultById(dto.getYear(), nowBaseFinDataList);
        }
    }


    /**
     * 获取财报数据
     *
     * @param year
     * @param entityCodeList
     * @return
     */
    private List<BaseFinData> listBaseFinData(int year, List<String> entityCodeList) {
        //由于要计算变动率 这里查两年的数据
        List<ListBaseFinDataDto> baseFinDataDtoList = new ArrayList<>();
        for (int i = 1; i >= 0; i--) {
            ListBaseFinDataDto baseFinDataDto = new ListBaseFinDataDto();
            //由于现在只有年报，所以直接处理
            int yearParam = year - i;
            LocalDate reportDate = LocalDate.parse(yearParam + "-12-31");
            baseFinDataDto.setYear(year);
            baseFinDataDto.setReportDate(reportDate);
//            if (org.springframework.util.StringUtils.hasLength(dto.getCode())) {
//                baseFinDataDto.setCode(dto.getCode());
//            }
            if (!CollectionUtils.isEmpty(entityCodeList)) {
                baseFinDataDto.setEntityCodeList(entityCodeList);
            }
            baseFinDataDtoList.add(baseFinDataDto);
        }
        //获取财报数据
        return this.listBaseFinData(baseFinDataDtoList);
    }

    /**
     * 校验值阈 和 变动率
     * <p>
     * 返回校验通过的科目 code
     *
     * @param baseFinDataList         要校验的数据
     * @param lastBaseFinDataMap      上一年的数据
     * @param financialDataConfigList 校验依据
     * @return
     */
    private List<String> checkThresholdValueAndChangeRate(List<BaseFinData> baseFinDataList,
                                                          Map<String, String> lastBaseFinDataMap,
                                                          List<FinancialDataConfig> financialDataConfigList) {
        //校验通过的科目 code
        List<String> passCodeList = new ArrayList<>();
        if (CollectionUtils.isEmpty(baseFinDataList)) {
            return passCodeList;
        }
        //把 财务数据配置 转成MAP，避免多次循环，加快查询速度
        Map<String, List<FinancialDataConfig>> financialDataConfigMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(financialDataConfigList)) {
            financialDataConfigMap = financialDataConfigList.stream().collect(Collectors.groupingBy(FinancialDataConfig::getCode));
        }
        if (CollectionUtils.isEmpty(lastBaseFinDataMap)) {
            lastBaseFinDataMap = new HashMap<>();
        }
        //
        FinancialDataConfig financialDataConfig = new FinancialDataConfig();
        List<FinancialDataConfig> financialDataConfigListTemp = null;
        //开始校验值阈 并 计算 变动率
        for (BaseFinData baseFinData : baseFinDataList) {
            financialDataConfigListTemp = financialDataConfigMap.get(baseFinData.getCode());
            if (!CollectionUtils.isEmpty(financialDataConfigListTemp)) {
                financialDataConfig = financialDataConfigListTemp.get(0);
            }
            //
            String suggestValue = baseFinData.getSuggestValue();
            //校验是否 通过值阈校验
            boolean isPassThresholdValue = this.checkThresholdValue(financialDataConfig.getThresholdValue(), suggestValue);
            baseFinData.setThresholdExceeded(isPassThresholdValue);
            if (!isPassThresholdValue) {
                //不通过  直接跳出
                baseFinData.setSystemInspection(Boolean.FALSE);
                continue;
            }
            //获取上年数据
            String lastSuggestValueKey = baseFinData.getEntityCode() + baseFinData.getCode();
            String lastSuggestValue = lastBaseFinDataMap.get(lastSuggestValueKey);
            //计算变动率：（今年 - 上年） / 上年
            BigDecimal changeRate = null;
            //不管是当前年 还是 当前年的上一年数据为空，都是为变动率为 1
            if (!org.springframework.util.StringUtils.hasLength(suggestValue)
                    || !org.springframework.util.StringUtils.hasLength(lastSuggestValue)) {
                //变动率设置为 1
                changeRate = new BigDecimal("1");
            } else {
                BigDecimal suggestValueTemp = new BigDecimal(suggestValue);
                BigDecimal lastSuggestValueTemp = new BigDecimal(lastSuggestValue);
                changeRate = (suggestValueTemp.subtract(lastSuggestValueTemp)).divide(lastSuggestValueTemp, 2, BigDecimal.ROUND_HALF_UP);
            }
            baseFinData.setChangeRate(changeRate);
            //变动率是否超过上限
            BigDecimal changeRateUpper = financialDataConfig.getChangeRateUpper();
            if (changeRateUpper == null) {
                //说明没有上限
                baseFinData.setSystemInspection(Boolean.TRUE);
                passCodeList.add(baseFinData.getCode());
                continue;
            }
            boolean isChangeRateUpper = changeRate.compareTo(changeRateUpper) <= 0;
            baseFinData.setSystemInspection(isChangeRateUpper);
            if (isChangeRateUpper) {
                passCodeList.add(baseFinData.getCode());
            }
        }
        return passCodeList;
    }

    /**
     * 是否通过 值阈 校验 校验
     * <p>
     * 通过：true
     *
     * @param thresholdValue 值阈
     * @param value          值
     * @return
     */
    private boolean checkThresholdValue(String thresholdValue, String value) {
        if (!org.springframework.util.StringUtils.hasLength(thresholdValue)) {
            return Boolean.TRUE;
        }
        if (!org.springframework.util.StringUtils.hasLength(value)) {
            return Boolean.FALSE;
        }
        //解析 值阈 ，形如：[0,+∞]，以逗号为界
        int index = thresholdValue.indexOf(",");
        if (index < 0) {
            log.error("值阈没有查询到分隔符： {} ", thresholdValue);
            return false;
        }
        //
        int thresholdValueLength = thresholdValue.length();
        //左边的阈值
        String leftThresholdValue = thresholdValue.substring(1, index);
        if ("-∞".equals(leftThresholdValue)) {
            leftThresholdValue = "-Infinity";
        }
        //右边的阈值
        String rightThresholdValue = thresholdValue.substring(index + 1, thresholdValueLength - 1);
        if ("+∞".equals(rightThresholdValue) || "∞".equals(rightThresholdValue)) {
            rightThresholdValue = "Infinity";
        }
        //获取第一个符号
        String leftOperator = thresholdValue.substring(0, 1);
        //获取最后一个符号
        String rightOperator = thresholdValue.substring(thresholdValueLength - 1);
        //组合运算公式
        StringBuilder eval = new StringBuilder();
        eval.append(leftThresholdValue);
        if ("(".equals(leftOperator)) {
            eval.append("<");
        } else if ("[".equals(leftOperator)) {
            eval.append("<=");
        } else {
            log.error("未知符号： {} ", leftOperator);
            return false;
        }
        eval.append(value);
        if (")".equals(rightOperator)) {
            eval.append("<");
        } else if ("]".equals(rightOperator)) {
            eval.append("<=");
        } else {
            log.error("未知符号： {} ", rightOperator);
            return false;
        }
        eval.append(rightThresholdValue);
        Boolean result = Boolean.FALSE;
        try {
            result = (Boolean) ScriptUtil.eval(eval.toString());
        } catch (Exception e) {
            log.error("值阈运行异常：{}", eval, e);
        }
        return result;
    }

    /**
     * 是否通过 勾稽校验
     * <p>
     * 通过：true
     *
     * @param baseFinDataList    要校验的数据
     * @param modelDataCheckList 勾稽配置
     */
    private void checkModelData(List<BaseFinData> baseFinDataList, List<ModelDataCheck> modelDataCheckList) {
        if (CollectionUtils.isEmpty(baseFinDataList)) {
            return;
        }
        //将勾稽转换成MAP  避免多次循环
        Map<String, String> modelDataCheckMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(modelDataCheckList)) {
            modelDataCheckMap = modelDataCheckList.stream().collect(Collectors.toMap(ModelDataCheck::getCheckCode, ModelDataCheck::getCheckFormula));
        }
        /*
        通过 主体 编码分组，再把科目转成map,防止多次循环
        {
        主体编码1：{
            科目编码1：推荐值，
            科目编码2：推荐值
            }
        主体编码2：{
            科目编码1：推荐值，
            科目编码2：推荐值
            }
        }
         */
        Map<String, Map<String, String>> baseFinDataByEntityCodeMap = new HashMap<>();
        for (BaseFinData baseFinData : baseFinDataList) {
            //值阈 和 变动率都没校验通过的数据，就不要参加了
            if (!baseFinData.getSystemInspection()) {
                continue;
            }
            Map<String, String> baseFinDataMap = baseFinDataByEntityCodeMap.get(baseFinData.getEntityCode());
            if (CollectionUtils.isEmpty(baseFinDataMap)) {
                baseFinDataMap = new HashMap<>();
            }
            baseFinDataMap.put(baseFinData.getCode(), baseFinData.getSuggestValue());
            baseFinDataByEntityCodeMap.put(baseFinData.getEntityCode(), baseFinDataMap);
        }
        String regex = "[a-zA-z]+([_]+)(.*)";
        Map<String, String> baseFinDataMap = null;
        //开始校验数据
        for (BaseFinData baseFinData : baseFinDataList) {
            //值阈 和 变动率都没校验通过的数据，就不要参加了
            if (!baseFinData.getSystemInspection()) {
                continue;
            }
            baseFinDataMap = baseFinDataByEntityCodeMap.get(baseFinData.getEntityCode());
            //查询科目勾稽校验公式
            String checkFormula = modelDataCheckMap.get(baseFinData.getCode());
            if (!org.springframework.util.StringUtils.hasLength(checkFormula)) {
                //如果没有公式，直接通过
                baseFinData.setDataCheck(Boolean.TRUE);
                baseFinData.setSystemInspection(Boolean.TRUE);
                continue;
            }
            //拆公式
            String[] checkFormulaItems = checkFormula.split(" ");
            for (int i = 0; i < checkFormulaItems.length; i++) {
                String checkFormulaItem = checkFormulaItems[i];
                if ("=".equals(checkFormulaItem)) {
                    checkFormulaItems[i] = "==";
                    continue;
                }
                if (!checkFormulaItem.matches(regex)) {
                    continue;
                }
                String suggestValue = baseFinDataMap.get(checkFormulaItem);
                if (suggestValue == null) {
                    //如果是空 当作 0 处理
                    suggestValue = "null";
                }
                checkFormulaItems[i] = suggestValue;
            }
            Boolean result = Boolean.FALSE;
            String eval = String.join(" ", checkFormulaItems);
            try {
                result = (Boolean) ScriptUtil.eval(eval);
            } catch (Exception e) {
                log.error("勾稽公式运行异常：{}", eval, e);
            }
            baseFinData.setDataCheck(result);
            baseFinData.setSystemInspection(result);
        }
    }



//    public static void main(String[] args) {
//        System.out.println(ScriptUtil.eval("1514465441.7900 == 655508125.0100 + 858957316.7800"));
//    }
}
