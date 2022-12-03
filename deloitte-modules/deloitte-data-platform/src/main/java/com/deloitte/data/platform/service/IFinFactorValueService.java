package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.FinFactorValue;
import com.deloitte.data.platform.dto.ApplyDataDetailExportDto;
import com.deloitte.data.platform.dto.ApplyDataDto;
import com.deloitte.data.platform.dto.ApplyDataExportDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import com.deloitte.data.platform.vo.ApplyDataExtractionDetailVo;
import com.deloitte.data.platform.vo.ApplyDataExtractionVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 指标值表 通过跑批计算出指标结果存入该表 服务类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface IFinFactorValueService extends IService<FinFactorValue> {
    /**
     * 提取数据 指标层数据结果
     * @param dto
     * @return
     */
    IPage<ApplyDataExtractionVo> getApplyDataExtractionPage(ApplyDataDto dto);

    /**
     * 导出指标层数据结果
     * @param response
     * @param dto
     */
    void applyDataExport(HttpServletResponse response, ApplyDataExportDto dto);

    /**
     * 导出指标层数据详情
     * @param response
     * @param dto
     */
    void applyDataDetailExport(HttpServletResponse response, ApplyDataDetailExportDto dto);

    /**
     * 提取数据 指标层详情数据结果
     * @param dto
     * @return
     */
    IPage<ApplyDataExtractionDetailVo> getApplyDataExtractionDetailPage(ApplyDataDto dto);

    IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewApplyPage(IPage<StatisticalDataAnalysisVo.OverviewVo> page, StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 查询有数据的最近三条年份
     *
     * @param
     * @return
     */
    List<String> getYears();
}
