package com.deloitte.additional.recording.vo;

import com.deloitte.additional.recording.domain.PrsModelQual;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 指标查询返回信息工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class QualInfoBackVo {
    /** 基础信息 */
    private PrsModelQual prsModelQual;
    /** evd信息 */
    private List<QualEvdVo> evdInfos;
    /** 挡位信息 */
    private List<QualFactorVo> factors;
}
