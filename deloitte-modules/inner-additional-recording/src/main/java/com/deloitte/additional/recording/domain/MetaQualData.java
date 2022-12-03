package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * (MetaQualData)实体类
 *
 * @author makejava
 * @since 2022-11-16 10:42:47
 */
@TableName("meta_qual_data")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class MetaQualData implements Serializable {
    private static final long serialVersionUID = 689974572505609860L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 关联prs_model_master(敞口表)中的mode_code
     */
    private String modeCode;
    /**
     * 关联prs_project_versions(版本表)中的主键id
     */
    private Integer versionId;
    /**
     * 关联prs_model_qual(指标)中的qual_code
     */
    private String qualCode;
    /**
     * 关联prs_model_qual中的name
     */
    private String qualName;

    private String total;

    private String miss;
    /**
     * 缺失率
     */
    private String missRate;
    /**
     * 1档，f表示档位，a-j表示1-9
     */
    private String fa;
    /**
     * 2档，f表示档位，a-j表示1-9
     */
    private String fb;
    /**
     * 3档，f表示档位，a-j表示1-9
     */
    private String fc;
    /**
     * 4档，f表示档位，a-j表示1-9
     */
    private String fd;
    /**
     * 5档，f表示档位，a-j表示1-9
     */
    private String fe;
    /**
     * 6档，f表示档位，a-j表示1-9
     */
    private String ff;
    /**
     * 7档，f表示档位，a-j表示1-9
     */
    private String fg;
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
    private String versioName;
    /**
     * 敞口名称
     */
    private String masterName;


}

