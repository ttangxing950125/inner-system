package com.deloitte.additional.recording.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysConfig)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 935662972623716313L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 名称
     */     @Excel(name = "名称")
    private String name;
    /**
     * 值
     */     @Excel(name = "值")
    private String value;
    /**
     * 状态
     */     @Excel(name = "状态")
    private String status;
         @Excel(name = "${column.comment}")
    private String remark;


}
