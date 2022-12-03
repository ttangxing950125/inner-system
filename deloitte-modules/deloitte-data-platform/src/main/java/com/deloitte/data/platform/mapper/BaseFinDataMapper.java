package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.BaseFinData;
import com.deloitte.data.platform.domian.DataPlatformConfigDict;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 德勤基础数据表  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Mapper
public interface BaseFinDataMapper extends BaseMapper<BaseFinData> {
    /**
     * 获取基础数据的最近三年
     *
     * @return
     */
    List<String> getBaseFinDataYears();

    /**
     * 数据质检结果分页信息
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<BaseFinDataVo> getBaseFinDataPage(IPage<BaseFinDataVo> page, @Param("dto") BaseDataDto dto);


    /**
     * 数据提取结果分页信息
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<BaseDataExtractionVo> getBaseDataExtractionPage(IPage<BaseFinDataVo> page, @Param("dto") BaseDataDto dto);

    /**
     * 查询数据缺失百分比
     *
     * @param codes
     * @param dto
     * @return
     */
    List<BaseDataExtractionVo> getBaseDataExtractionAllRate(@Param("codes") Set<String> codes, @Param("dto") BaseDataDto dto);

    /**
     * 数据提取结果详情分页信息
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<DataExtractionDetailVo> getDataExtractionDetailPage(IPage<DataExtractionDetailVo> page, @Param("dto") BaseDataDto dto);

    /**
     * 根据code查询数据质检
     *
     * @param page
     * @param dto
     * @param codes
     * @return
     */
    IPage<CodeEntityVo> getCodeEntityPage(IPage<CodeEntityVo> page, @Param("dto") CodeEntityDto dto, @Param("codes") Set<String> codes);

    /**
     * 根据主体编码计算质检通过比率
     *
     * @param dto
     * @param entityCodes
     * @return
     */
    List<QualityTestRateVo> getQualityTestRate(@Param("dto") EntityInfoDto dto, @Param("entityCodes") Set<String> entityCodes);

    /**
     * 根据条件查询导出的总数
     *
     * @param year
     * @param dto
     * @param financialCodes
     * @return
     */
    Integer getBaseFinDataTotal(@Param("year") String year, @Param("dto") BaseDataExportDto dto, @Param("financialCodes") Set<String> financialCodes);

    /**
     * 修改通过人工质检状态
     * @param year
     * @param dataId
     */
    void updateIsArtificialInspection(@Param("year")String year,@Param("dataId") Integer dataId);

    /**
     * 根据条件导出数据
     *
     * @param year
     * @param dto
     * @param financialCodes
     * @return
     */
    List<BaseDataExtractionExportVo> getExportBaseData(@Param("year") String year, @Param("dto") BaseDataExportDto dto, @Param("financialCodes") Set<String> financialCodes);

    /**
     * 根据条件导出数据
     *
     * @param year
     * @param dto
     * @return
     */
    List<BaseDataExtractionDetailExportVo> getExportBaseDetailData(@Param("year") String year, @Param("dto") BaseDataDetailExportDto dto);

    /**
     * 数据统计分析 基础质量分页信息
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<BaseQualityVo> getBaseQualityPage(IPage<BaseFinDataVo> page, @Param("dto") BaseQualityDto dto);


    List<BaseFinData> listInEntityCodeAndReportDate(@Param("dtoList") List<ListBaseFinDataDto> dtoList);

    /**
     * 数据统计分析 总览(基础层)
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewPage(IPage<StatisticalDataAnalysisVo.OverviewVo> page, @Param("dto") StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 数据统计分析 总览-数据来源覆盖度
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.CoverageVo getOverviewSourceCoverage(StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 数据统计分析 总览-数据来源覆盖度-推荐数据
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.CoverageVo getOverviewSourceCoverageSuggest(StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 数据统计分析 业务场景
     *
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.BusinessScenarioVo> getBusinessScenarioPage(IPage<StatisticalDataAnalysisVo.BusinessScenarioVo> page,
                                                                                @Param("dto") StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 数据统计分析 业务场景-数据来源覆盖度
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.CoverageVo getBusinessScenarioSourceCoverage(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 数据统计分析 业务场景-数据来源覆盖度-推荐数据
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.CoverageVo getBusinessScenarioSourceCoverageSuggest(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 数据统计分析 业务场景-数据缺失率
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.BusinessScenarioMissRateVo getBusinessScenarioMissRate(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 查询计算分页数据
     *
     * @param page
     * @param
     * @return
     */
    IPage<ModelEvidenceVo.CalculationVo> getCalculationPage(IPage<ModelEvidenceVo.CalculationVo> page, @Param("codes") Set<String> codes, @Param("year") String year);

    /**
     * 查询计算所有数据
     *
     * @param codes
     * @param
     * @return
     */
    List<ModelEvidenceVo.FinDataVo> getCalculationAllData(@Param("codes") Set<String> codes,
                                                          @Param("entityCodes") Set<String> entityCodes,
                                                          @Param("reportDates") Set<String> reportDates,
                                                          @Param("year") String year);

    /**
     * 参数配置列表
     *
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page, @Param("dto") ParameterConfigDto.ListDto dto);

    /**
     * 使用场景列表
     *
     * @param dto
     * @return
     */
    List<StatisticalDataAnalysisVo.GetDataMenuVo> getDataMenu(StatisticalDataAnalysisDto.GetDataMenuDto dto);

    /**
     * 查询有数据的最近三条年份
     *
     * @param
     * @return
     */
    List<String> getYears();

    /**
     * 数据缺失率总占比统计
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.BusinessScenarioMissRateAllVo getBusinessScenarioMissRateAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 补录总占比统计
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.RecordingAllVo getRecordingAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 保存数据校验结果
     *
     * @param year
     * @param baseFinDataList
     */
    void saveBatchCheckResultById(@Param("year") int year, @Param("baseFinDataList") List<BaseFinData> baseFinDataList);

    /**
     * 已人工补录条数
     *
     * @param
     * @return
     */
    Map<String, Object> getArtificialRecordingBar(@Param("dto")StatisticalDataAnalysisDto.BusinessScenarioDto dto,
                                                  @Param("configs")List<DataPlatformConfigDict> configs);

    /**
     * @param page
     * @param year
     * @param reportDate
     * @param entityCodeList
     * @return
     */
    IPage<String> entityCodePage(IPage page, @Param("year") int year, @Param("reportDate") LocalDate reportDate, @Param("entityCodeList") List<String> entityCodeList);




}
