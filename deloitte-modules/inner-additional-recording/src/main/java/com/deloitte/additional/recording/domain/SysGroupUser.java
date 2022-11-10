package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (SysGroupUser)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysGroupUser implements Serializable {
    private static final long serialVersionUID = -33057971120376963L;
    @Excel(name = "${column.comment}")
    @TableId
    private Integer id;
    @Excel(name = "${column.comment}")
    private Integer groupId;
    @Excel(name = "${column.comment}")
    private Integer userId;


    public SysGroupUser init(Integer userId, Integer groupId) {
        this.userId = userId;
        this.groupId = groupId;
        return this;
    }
}
