package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/11/17 10:25
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class CenterMasterVo {
    /** 版本ID */
    private Integer versionId;
    /** 中心库表前缀 */
    private String prefix;
    /** 版本年份 */
    private String dataYear;
    /** 模糊查询 */
    private String searchData;
    /** 页码 */
    private Integer page=1;
    /** 页面size */
    private Integer pagesize=10;
}
