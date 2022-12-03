package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.domian.BaseFinData;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 德勤基础数据表  服务类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface IBaseFinDataService extends IService<BaseFinData> {
    /**
     * 获取基础数据的最近三年
     * @return
     */
    List<String> getBaseFinDataYears();
    /**
     * 质检结果
     *
     * @param dto
     * @return
     */
    IPage<BaseFinDataVo> getBaseFinDataPage(BaseDataDto dto);

    /**
     * 数据提取结果
     *
     * @param dto
     * @return
     */
    IPage<BaseDataExtractionVo> getBaseDataExtractionPage(BaseDataDto dto);

    /**
     * 数据提取结果 详情
     *
     * @param dto
     * @return
     */
    IPage<DataExtractionDetailVo> getDataExtractionDetailPage(BaseDataDto dto);

    /**
     *  根据code查询数据质检
     * @param dto
     * @return
     */
    IPage<CodeEntityVo> getCodeEntityPage(CodeEntityDto dto);

    /**
     * 修改通过人工质检状态
     * @param year
     * @param dataId
     */
    void updateIsArtificialInspection(String year,Integer dataId);

    /**
     * 根据主体编码计算质检通过比率
     *
     * @param dto
     * @param entityCodes
     * @return
     */
    Map<String, QualityTestRateVo> getQualityTestRate(EntityInfoDto dto, Set<String> entityCodes);

    /**
     * 数据统计分析 基础质量
     *
     * @param dto
     * @return
     */
    IPage<BaseQualityVo> getBaseQualityPage(BaseQualityDto dto);

    /**
     * 数据统计分析 总览
     *
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.OverviewVo> overview(StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 数据统计分析 总览-数据来源覆盖度
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.CoverageVo overviewSourceCoverage(StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 业务场景-缺失率统计
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.BusinessScenarioMissRateVo businessScenarioMissRate(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    List<BaseFinData> listBaseFinData(List<ListBaseFinDataDto> dtoList);

    /**
     * 查询计算分页数据
     *
     * @param codes
     * @return
     */
    IPage<ModelEvidenceVo.CalculationVo> getCalculationPage(IPage<ModelEvidenceVo.CalculationVo> page, Set<String> codes,String year);

    /**
     * 查询计算分页所有
     *
     * @param codes
     * @return
     */
    List<ModelEvidenceVo.FinDataVo> getCalculationAllData(Set<String> codes, Set<String> entityCodes, Set<String> reportDates,String year);

    /**
     * 查询参数配置
     *
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page, ParameterConfigDto.ListDto dto);

    /**
     * 查询使用场景列表
     *
     * @param dto
     * @return
     */
    List<StatisticalDataAnalysisVo.GetDataMenuVo> getDataMenu(StatisticalDataAnalysisDto.GetDataMenuDto dto);

    /**
     * 查询有数据的最近三条年份
     *
     * @param dto
     * @return
     */
    List<String> years(StatisticalDataAnalysisDto.GetDataMenuDto dto);

    /**
     * 数据缺失率总占比统计
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.BusinessScenarioMissRateAllVo missRateAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 补录总占比统计
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.RecordingAllVo recordingAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 人工补录字段情况-条形图表
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.ArtificialRecordingBarVo artificialRecordingBar(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 人工补录字段情况-圆形图表
     *
     * @param dto
     * @return
     */
    StatisticalDataAnalysisVo.ArtificialRecordingCircularVo artificialRecordingCircular(StatisticalDataAnalysisDto.BusinessScenarioDto dto);

    /**
     * 导出基础数据
     * @param dto
     * @param response
     */
    void baseDataExport(HttpServletResponse response, BaseDataExportDto dto);

    /**
     * 导出基础数据详情
     * @param dto
     * @param response
     */
    void baseDataDetailExport(HttpServletResponse response, BaseDataDetailExportDto dto);

    /**
     * 财报数据校验
     *
     * @param dto
     */
    void check(BaseFinDataCheckDto dto);
}
