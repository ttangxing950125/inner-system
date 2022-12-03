package com.deloitte.additional.recording.vo;

import com.deloitte.additional.recording.domain.PrsModelQual;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 版本新增指标参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class InsertQualVo {
    /** 指标名称 */
    private String qualName;
    /** 指标类别，0-普通，1-定性，2-定量 */
    private Integer qualType;
    /** 指标描述 */
    private String description;
    /** 版本 */
    private Integer versionId;
    /** 敞口 */
    private Integer masterId;
    /** evdIds */
    private List<Integer> evdIds;
    /** 等级 */
    private List<String> factorItems;
    /** 规则描述 */
    private String itemRule;
    /** 是否自动化 */
    private Boolean isConfi;

    public PrsModelQual havePrsModelQual(){
        PrsModelQual prsModelQual = new PrsModelQual();
        prsModelQual.setQualName(this.qualName).setDescription(this.description).setQualType(this.qualType);
        return prsModelQual;
    }
}
