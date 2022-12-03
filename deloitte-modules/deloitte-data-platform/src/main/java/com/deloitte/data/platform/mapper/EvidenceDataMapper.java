package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.EvidenceData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.data.platform.dto.MiddleDataDetailExportDto;
import com.deloitte.data.platform.dto.MiddleDataDto;
import com.deloitte.data.platform.dto.MiddleDataExportDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 德勤Evidence数据表  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface EvidenceDataMapper extends BaseMapper<EvidenceData> {
    /**
     * 提取数据 中间层数据结果
     * @param page
     * @param dto
     * @return
     */
    IPage<MiddleDataExtractionVo> getMiddleDataExtractionPage( IPage<MiddleDataExtractionVo> page,@Param("dto") MiddleDataDto dto);

    /**
     *  根据编码计算数据缺失率
     * @param codes
     * @return
     */
    List<MiddleDataExtractionVo> getDataMissRate(@Param("codes") Set<String> codes);

    /**
     * 提取数据详情 中间层数据结果
     * @param page
     * @param dto
     * @return
     */
    IPage<MiddleDataExtractionDetailVo> getMiddleDataExtractionDetailPage(IPage<MiddleDataExtractionDetailVo> page,@Param("dto") MiddleDataDto dto);

    /**
     * 查询导出中间层数据
     * @param dto
     * @param modelEvidenceCodes
     * @return
     */
    List<MiddleDataExtractionExportVo> getExportMiddleData(@Param("dto") MiddleDataExportDto dto,@Param("modelEvidenceCodes") Set<String> modelEvidenceCodes);

    /**
     * 导出中间层详情数据结果
     * @param dto
     * @param entityCodes
     * @return
     */
    List<MiddleDataExtractionDetailExportVo> getExportMiddleDataDetail(@Param("dto") MiddleDataDetailExportDto dto,@Param("entityCodes") Set<String> entityCodes);

    /**
     * 数据统计分析 总览(中间层)
     * @param page
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewMiddlePage(IPage<StatisticalDataAnalysisVo.OverviewVo> page,@Param("dto") StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 查询有数据的最近三条年份
     *
     * @param
     * @return
     */
    List<String> getYears();
}
