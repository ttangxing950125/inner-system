package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.dto.AdditionalEntryReviewDto;
import com.deloitte.additional.recording.dto.EvidenceBatchDto;
import com.deloitte.additional.recording.dto.KpIInfoByQuryDto;
import com.deloitte.additional.recording.dto.TaskReassignDto;
import com.deloitte.common.core.domain.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.deloitte.additional.recording.dto.BasEvdTaskInfoStatusCountDto;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述
 */
public interface BasEvdTaskInfoService  extends IService<BasEvdTaskInfo> {
    /**
     * 统计任务总量
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return Integer
     */
    Integer taskCount(String qualCode, String dataYear);

    /**
     * evidence批量分配
     * @param evidenceBatchDto
     * @return
     */
    R updatEevidenceBatch(EvidenceBatchDto evidenceBatchDto);

    /**
     * evidence批量通过
     * @param evidenceBatchDto
     * @return
     */
    R updatEevidenceThrough(EvidenceBatchDto evidenceBatchDto);

    /**
     * 批量打回
     * @param file
     * @param response
     * @throws IOException
     */
    void excelBatchCallBack(MultipartFile file, HttpServletResponse response) throws IOException;

    /**
     *根据条件查询实习生工时成果
     *
     * @param evidenceBatchDto
     * @return R
     * @author penTang
     * @date 2022/11/25 15:25
    */

    R getKpiInfoView(KpIInfoByQuryDto evidenceBatchDto);




    /**
     * 查询补录审核列表
     * @param additionalEntryReview
     * @return
     */
    R getAdditionalEntryReviewList(AdditionalEntryReviewDto additionalEntryReview);

    /**
     * 常规政府批量通过
     * @param evidenceBatchDto
     * @return
     */
    R auditPassInBulk(EvidenceBatchDto evidenceBatchDto);

    /**
     * 审核改派
     * @param taskReassignDto
     * @return
     */
    R updateTaskReassign(TaskReassignDto taskReassignDto);

    /**
     * 任务详情
     * @param entityCode
     * @param evdCode
     * @return
     */
    R getCollectionDetails(String entityCode, String evdCode);

    /**
     * 查询下拉框
     * @param evdCode
     * @return
     */
    R getDropDownBox(String evdCode);
    List<BasEvdTaskInfoStatusCountDto> taskCountByStatus(String qualCode, String dataYear);
}
