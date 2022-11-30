package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsQualDataDiff;
import com.deloitte.additional.recording.vo.qual.PrsQualDataDiffPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/28
 * @描述
 */
public interface PrsQualDataDiffMapper  extends BaseMapper<PrsQualDataDiff> {

    /**
     * 查询指标下的企业指标差异
     * @param qualCode 指标code
     * @return
     */

    List<PrsQualDataDiffPageVO> findByQual(@Param("qualCode") String qualCode);
}
