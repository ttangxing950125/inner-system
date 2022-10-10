package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityInfoLogs;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/10/18:03
 * @Description:
 */
@Data
public class EntityInfoLogsExpand extends EntityInfoLogs {
    private String createTimeStr;
}
