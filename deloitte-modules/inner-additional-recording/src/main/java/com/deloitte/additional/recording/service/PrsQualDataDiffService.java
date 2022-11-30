package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsQualDataDiff;
import com.deloitte.additional.recording.vo.BasEvdTaskDataVO;
import com.deloitte.additional.recording.vo.qual.PrsQualDataDiffPageVO;
import com.deloitte.additional.recording.vo.qual.PrsQualDataPanelVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/28
 * @描述
 */
public interface
PrsQualDataDiffService extends IService<PrsQualDataDiff> {
    /**
     * 查询指标下的企业主体差异
     * @param qualCode 指标code
     * @return List<PrsQualDataDiffPageVO>
     */
    List<PrsQualDataDiffPageVO> findByQual(String qualCode);

    Map<String, PrsQualDataPanelVO> distributionPanel(String qualCode, String dataYear);

    /**
     * 进度面板统计
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return map
     */
    Map<String, BigDecimal> progressPanel(String qualCode, String dataYear);

    /**
     * 基本面数据列表
     * @param qualCode 指标code
     * @return   List<BasEvdTaskDataVO>
     */
    List<BasEvdTaskDataVO> basePanel(String qualCode);
}
