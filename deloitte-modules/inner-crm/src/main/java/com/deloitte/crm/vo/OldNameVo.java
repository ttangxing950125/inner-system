package com.deloitte.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/31 13:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OldNameVo {
        private String dqCode;
        private String oldName;
        private String newOldName;
        private String status;
        private String remarks;
}
