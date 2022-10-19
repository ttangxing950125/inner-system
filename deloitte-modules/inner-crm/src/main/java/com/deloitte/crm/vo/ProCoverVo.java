package com.deloitte.crm.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/19 16:54
 */
@Data
@Accessors(chain = true)
public class ProCoverVo {
    /** 是否覆盖 */
    private NameValueVo isCover;
    /** 未覆盖原因 */
    private NameValueVo coverReason;
}
