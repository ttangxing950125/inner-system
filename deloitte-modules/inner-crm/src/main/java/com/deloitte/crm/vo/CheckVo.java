package com.deloitte.crm.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: checkVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class CheckVo {

    private Object data;

    private String msg;

}
