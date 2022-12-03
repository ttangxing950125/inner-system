package com.deloitte.additional.recording.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalManifestVo implements Serializable {

    /**
     * 主体名称
     */
    private String entityName;


    /**
     * 版本号
     */
    private String versionName;


    /**
     * 敞口
     */
    private String exposureTo;


    /**
     * 年份
     */
    private String timeValue;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 是否上市
     */
    private Byte list;

    /**
     * 是否发债
     */
    private Byte entityBondTag;

    /**
     * 新发债是否推送
     */
    private Byte incrStatus;

    /**
     * 状态
     */
    private Byte status;

    /**
     *版本代码
     */
    private String prjId;

    /**
     * 敞口编码
     */
    private String modelCode;

    /**
     * 版本敞口主体Id
     */
    private Integer masEntityId;

}
