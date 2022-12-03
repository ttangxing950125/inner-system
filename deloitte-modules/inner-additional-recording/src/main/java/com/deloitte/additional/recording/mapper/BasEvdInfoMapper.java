package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.dto.EvidenceDistributionPageDto;
import com.deloitte.additional.recording.vo.EvidenceDistributionVo;
import com.deloitte.additional.recording.vo.EvidenceVo;
import com.deloitte.additional.recording.vo.evd.BasEvdInfoDetailVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (BasEvdInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface BasEvdInfoMapper extends BaseMapper<BasEvdInfo> {

    List<EvidenceDistributionVo> getEvidenceDistributionPage(Page<EvidenceDistributionVo> page,
                                                             @Param("distributionPageDto") EvidenceDistributionPageDto distributionPageDto,
                                                             @Param("versionNames") List<String> versionNames,
                                                             @Param("modelMasterIds")List<Integer> modelMasterIds,
                                                             @Param("qualIds")List<Integer> qualIds,
                                                             @Param("entityIds")List<Integer> entityIds,
                                                             @Param("evidenceIds") List<Integer> evidenceIds,
                                                             @Param("collocterIds") List<Integer> collocterIds,
                                                             @Param("approverIds") List<Integer> approverIds,
                                                             @Param("groupCollocterIds") List<Integer> groupCollocterIds);

    List<EvidenceVo> getLabel(@Param("codeList") List<String> codeList);

    List<BasEvdInfoDetailVO> findByQualCode(@Param("qualCode") String qualCOde);
}
