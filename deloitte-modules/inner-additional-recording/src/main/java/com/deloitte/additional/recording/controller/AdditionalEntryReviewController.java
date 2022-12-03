package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.dto.AdditionalEntryReviewDto;
import com.deloitte.additional.recording.dto.EvidenceBatchDto;
import com.deloitte.additional.recording.dto.TaskReassignDto;
import com.deloitte.additional.recording.service.BasEvdTaskInfoService;
import com.deloitte.common.core.domain.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/evidence")
@Api("补录审核列表")
public class AdditionalEntryReviewController {

    @Resource
    private BasEvdTaskInfoService basEvdTaskInfoService;

    @PostMapping("/getAdditionalEntryReviewList")
    public R getAdditionalEntryReviewList(@RequestBody AdditionalEntryReviewDto additionalEntryReview){
     return    basEvdTaskInfoService.getAdditionalEntryReviewList(additionalEntryReview);
    }

    /**
     * 常规政府批量通过
     * @param evidenceBatchDto
     * @return
     */
    @PutMapping("/auditPassInBulk")
    public R auditPassInBulk(@RequestBody EvidenceBatchDto evidenceBatchDto){
        return basEvdTaskInfoService.auditPassInBulk(evidenceBatchDto);
    }

    /**
     * 审核改派
     */
    @PutMapping("/updateTaskReassign")
    public R updateTaskReassign(@RequestBody TaskReassignDto taskReassignDto){
        return basEvdTaskInfoService.updateTaskReassign(taskReassignDto);
    }

    /**
     * 任务详情
     */
    @GetMapping("/getCollectionDetails")
    public R getCollectionDetails(String entityCode,String evdCode){
        return   basEvdTaskInfoService.getCollectionDetails(entityCode,evdCode);
    }

    /**
     * 查询下拉框
     */
    @GetMapping("/getDropDownBox")
    public R getDropDownBox(String evdCode){
        return basEvdTaskInfoService.getDropDownBox(evdCode);
    }
}
