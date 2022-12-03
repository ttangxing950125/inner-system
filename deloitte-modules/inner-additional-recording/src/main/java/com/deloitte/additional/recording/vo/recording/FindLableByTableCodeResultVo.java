package com.deloitte.additional.recording.vo.recording;

import com.deloitte.additional.recording.domain.BasRecordingLabel;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/21/14:33
 * @Description:
 */
@Data
public class FindLableByTableCodeResultVo implements Serializable {

    private List<BasRecordingLabel> info;

    private Integer tount;
}
