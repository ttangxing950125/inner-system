package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * evd元数据信息(MetaEvdData)实体类
 *
 * @author makejava
 * @since 2022-11-16 10:41:50
 */
@Data
@Builder
@TableName("meta_evd_data")
@AllArgsConstructor
@NoArgsConstructor
public class MetaEvdData implements Serializable {
    private static final long serialVersionUID = 936728389360293101L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 关联prs_model_master中的mode_code
     */
    private String modeCode;
    /**
     * 关联prs_project_versions中的主键Id
     */
    private Integer versionId;
    /**
     * 关联prs_model_qual中的qual_code
     */
    private String qualCode;
    /**
     * 关联bas_evd_info中的code
     */
    private String evidenceCode;
    /**
     * 关联bas_evd_info中的name属性
     */
    private String evidenceName;
    /**
     * 样本总数
     */
    private String total;
    /**
     * 缺失
     */
    private String miss;
    /**
     * 缺失率
     */
    private String missRate;
    /**
     * 最小值
     */
    private String minValue;
    /**
     * 最大值
     */
    private String maxValue;
    /**
     * 前5%，p表示百分比，a-j表示1-9
     */
    private String pe;
    /**
     * 前25%，p表示百分比，a-j表示1-9
     */
    private String pbe;
    /**
     * 前50%，p表示百分比，a-j表示1-9
     */
    private String pe0;
    /**
     * 前75%，p表示百分比，a-j表示1-9
     */
    private String pge;
    /**
     * 前95%，p表示百分比，a-j表示1-9
     */
    private String pie;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;
    /**
     * 时间
     */
    private String timeValue;
    /**
     * 版本名称
     */
    private String versioNname;




}

