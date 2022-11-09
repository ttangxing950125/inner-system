package com.deloitte.additional.recording.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysUserrole)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysUserrole implements Serializable {
    private static final long serialVersionUID = -75751226410434836L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 用户id
     */     @Excel(name = "用户id")
    private Integer userId;
    /**
     * 角色id
     */     @Excel(name = "角色id")
    private Integer roleId;


}
