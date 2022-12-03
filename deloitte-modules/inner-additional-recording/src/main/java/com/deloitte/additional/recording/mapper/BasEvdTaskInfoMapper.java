package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.deloitte.additional.recording.dto.BasEvdTaskInfoStatusCountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.deloitte.additional.recording.dto.AdditionalEntryReviewDto;
import com.deloitte.additional.recording.vo.CollectionDetailsVO;
import com.deloitte.additional.recording.vo.EvidenceDistributionVo;
import com.deloitte.additional.recording.vo.ExcelBatchCallBackVo;
import com.deloitte.additional.recording.vo.ModelMasterCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述 BasEvdTaskInfo 表 数据处理层
 */
public interface BasEvdTaskInfoMapper  extends BaseMapper<BasEvdTaskInfo> {

    List<Map<String, Object>> getEntityEvdName(@Param("dataIds") List<Integer> dataIds);

    List<ExcelBatchCallBackVo> getBatchCallBack(@Param("collectionNameList") List<String> collectionNameList,@Param("entityNameList") List<String> entityNameList, @Param("timeValueList")List<String> timeValueList, @Param("evidenceNameList")List<String> evidenceNameList);

    List<EvidenceDistributionVo> getAdditionalEntryReviewList(Page<EvidenceDistributionVo> page,
                                                              @Param("additionalEntryReview") AdditionalEntryReviewDto additionalEntryReview,
                                                              @Param("collocterIds") List<Integer> collocterIds,
                                                              @Param("approverIds") List<Integer> approverIds,
                                                              @Param("userGroupIds") List<Long> userGroupIds);

    CollectionDetailsVO getCollectionDetails(@Param("entityCode") String entityCode, @Param("evdCode")String evdCode);
    //获取敞口的分组数量
    List<ModelMasterCountVo> getModelMasterCount(@Param("entityCode") String entityCode);
    //查询下拉框
    List<Map<String, Object>> getDropDownBox(String evdCode);

    Integer taskCount(@Param("qualCode") String qualCode, @Param("dataYear") String dataYear);

    List<BasEvdTaskInfoStatusCountDto> taskCountByStatus(@Param("qualCode") String qualCode, @Param("dataYear") String dataYear);
}
