package com.deloitte.additional.recording.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysRole)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysRole implements Serializable {
    private static final long serialVersionUID = 779302907325580421L;
         @Excel(name = "${column.comment}")
         @TableId
    private Integer id;
    /**
     * 角色名称
     */     @Excel(name = "角色名称")
    private String name;
    /**
     * 角色状态
     */     @Excel(name = "角色状态")
    private String status;


}
