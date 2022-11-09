package com.deloitte.additional.recording.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
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
    private Integer id;
         @Excel(name = "${column.comment}")
    private Integer groupId;
         @Excel(name = "${column.comment}")
    private Integer userId;


}
