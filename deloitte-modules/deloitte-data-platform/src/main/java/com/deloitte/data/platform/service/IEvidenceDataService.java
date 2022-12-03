package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.EvidenceData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.dto.MiddleDataDetailExportDto;
import com.deloitte.data.platform.dto.MiddleDataDto;
import com.deloitte.data.platform.dto.MiddleDataExportDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import com.deloitte.data.platform.vo.MiddleDataExtractionDetailVo;
import com.deloitte.data.platform.vo.MiddleDataExtractionVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 德勤Evidence数据表  服务类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface IEvidenceDataService extends IService<EvidenceData> {
    /**
     * 提取数据 中间层数据结果
     * @param dto
     * @return
     */
    IPage<MiddleDataExtractionVo> getMiddleDataExtractionPage(MiddleDataDto dto);

    /**
     * 导出中间层数据结果
     * @param response
     * @param dto
     */
    void middleDataExport(HttpServletResponse response, MiddleDataExportDto dto);

    /**
     * 导出中间层详情数据结果
     * @param response
     * @param dto
     */
    void middleDataDetailExport(HttpServletResponse response, MiddleDataDetailExportDto dto);

    /**
     * 提取数据详情 中间层数据结果
     * @param dto
     * @return
     */
    IPage<MiddleDataExtractionDetailVo> getMiddleDataExtractionDetailPage(MiddleDataDto dto);

    /**
     * 数据统计分析 总览(中间层)
     * @param page
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.OverviewVo> getOverviewMiddlePage(IPage<StatisticalDataAnalysisVo.OverviewVo> page, StatisticalDataAnalysisDto.OverviewDto dto);

    /**
     * 查询有数据的最近三条年份
     *
     * @param
     * @return
     */
    List<String> getYears();
}
