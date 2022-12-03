package com.deloitte.additional.recording.vo.recording;

import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/24/10:39
 * @Description:
 */
@Data
public class EditEcordingLabelVo implements Serializable {
    /**
     * 主体任务信息 主要包含
     * entityCode & entityName
     * periodReportTime报告期
     * tableName 表名称
     */
    private BasRecordingTaskInfo taskInfo;
    /**
     * 字段详情
     */
    private List<BasEvdData> filedLists;
}
