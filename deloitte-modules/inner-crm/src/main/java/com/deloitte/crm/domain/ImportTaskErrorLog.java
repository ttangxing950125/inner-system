package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (ImportTaskErrorLog)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 18:33:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImportTaskErrorLog implements Serializable {
    private static final long serialVersionUID = -16622148051354030L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 任务id
     */     @Excel(name = "任务id")
    private Integer taskId;
    /**
     * 任务分类
     */     @Excel(name = "任务分类")
    private String taskCategory;
    /**
     * 保存的策略模式类名
     */     @Excel(name = "保存的策略模式类名")
    private String simpleClassName;
    /**
     * 导入的数据
     */     @Excel(name = "导入的数据")
    private String importItem;
    /**
     * 提示消息
     */     @Excel(name = "提示消息")
    private String message;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;


}
