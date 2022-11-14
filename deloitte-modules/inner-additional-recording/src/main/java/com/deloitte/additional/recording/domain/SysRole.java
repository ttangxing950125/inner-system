package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String name;
    /**
     * 角色状态
     */
    @Excel(name = "角色状态")
    private String status;

    public SysRole createBy(String name, String status) {
        this.name = name;
        this.status = status;
        if (StringUtils.isBlank(status)) {
            this.status = "1";
        }
        return this;
    }
}
