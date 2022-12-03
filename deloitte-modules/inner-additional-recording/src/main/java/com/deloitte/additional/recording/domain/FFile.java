package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FFile implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件编码
     */
    private String code;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 所在文件夹
     */
    private String fcode;

    /**
     * 文件所在路径
     */
    private String url;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 所属时间
     */
    private String timevalue;


}
