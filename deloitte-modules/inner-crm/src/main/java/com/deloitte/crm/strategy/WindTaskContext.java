package com.deloitte.crm.strategy;

import com.deloitte.crm.domain.CrmWindTask;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
@Getter
@Setter
public class WindTaskContext {
    /**
     * 当前被操作的任务
     */
    private CrmWindTask windTask;
    /**
     * 上传的任务附件
     */
    private MultipartFile file;
    /**
     *今天
     */
    private Date timeNow;
}
