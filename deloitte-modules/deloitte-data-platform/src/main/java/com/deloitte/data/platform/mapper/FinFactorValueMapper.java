package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.FinFactorValue;
import com.deloitte.data.platform.dto.ApplyDataDetailExportDto;
import com.deloitte.data.platform.dto.ApplyDataDto;
import com.deloitte.data.platform.dto.ApplyDataExportDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 指标值表 通过跑批计算出指标结果存入该表 Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface FinFactorValueMapper extends BaseMapper<FinFactorValue> {
    /**
     * 提取数据 指标层数据结果
     * @param page
     * @param dto
     * @param factorCodes
     * @return
     */
    IPage<ApplyDataExtractionVo> getApplyDataExtractionPage(IPage<ApplyDataExtractionVo> page,@Param("dto") ApplyDataDto dto,@Param("factorCodes") Set<String> factorCodes);

    /**
     * 导出指标层数据结果
     * @param dto
     * @param factorCodes
     * @return
     */
    List<ApplyDataExtractionExportVo>  getApplyDataExport(@Param("dto") ApplyDataExportDto dto,@Param("factorCodes") Set<String> factorCodes);

    /**
     *  根据编码计算数据缺失率
     * @param codes
     * @return
     */
    List<ApplyDataExtractionVo> getDataMissRate(@Param("codes") Set<String> codes);

    /**
     * 提取数据 指标层详情数据结果
     * @param page
     * @param dto
     * @return
     */
    IPage<ApplyDataExtractionDetailVo> getApplyDataExtractionDetailPage(IPage<ApplyDataExtractionDetailVo> page,@Param("dto") ApplyDataDto dto);

    /**
     * 数据统计分析 总览(指标层)
     * @param page
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewApplyPage(IPage<StatisticalDataAnalysisVo.OverviewVo> page,@Param("dto") StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 查询有数据的最近三条年份
     *
     * @param
     * @return
     */
    List<String> getYears();

    /**
     * 导出指标层数据详情
     * @param dto
     * @param entityCodes
     * @return
     */
    List<ApplyDataExtractionDetailExportVo> getExportApplyDataDetail(@Param("dto") ApplyDataDetailExportDto dto,@Param("entityCodes")Set<String> entityCodes);
}
