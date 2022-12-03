package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author LIFEILIN
 * @since 2022-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasEvdTaskLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务id
     */
    private Integer taskId;

    private Integer newStatus;

    private Integer oldStatus;

    /**
     * 操作用户
     */
    private Integer actUser;

    /**
     * 操作类型增加-1，修改-2
     */
    private Integer actType;

    /**
     * 操作备注
     */
    private String remark;

    /**
     * 可能的数据标识(每一次系统逻辑上的操作，产生一个data_mark)
     */
    private String dataMark;

    private Date created;


}
