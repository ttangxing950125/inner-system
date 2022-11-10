package com.deloitte.additional.recording.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysMenurole)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysMenuRole implements Serializable {
    private static final long serialVersionUID = -58298697479700190L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 菜单id
     */     @Excel(name = "菜单id")
    private Integer menuId;
    /**
     * 角色id
     */     @Excel(name = "角色id")
    private Integer roleId;


}
