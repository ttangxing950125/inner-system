package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 版本查询指标参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class ProjectVersionVo {
    /**
     * 页面大小，默认10
     */
    private Integer pageSize = 10;
    /**
     * 页码，默认1
     */
    private Integer page = 1;
    /**
     * 时间选项
     */
    private String time_value;
    /**
     * 输入搜索选项
     */
    private String searchData;
    /**
     * 选择是否可用 1.正常 0.删除
     */
    private Integer status;
}

