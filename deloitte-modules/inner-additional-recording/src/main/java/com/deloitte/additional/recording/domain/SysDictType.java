package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysDictType)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysDictType implements Serializable {
    private static final long serialVersionUID = 653709312907650333L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 字典分类
     */     @Excel(name = "字典分类")
    private String typeId;
    /**
     * 描述信息
     */     @Excel(name = "描述信息")
    private String typeDes;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;


}
