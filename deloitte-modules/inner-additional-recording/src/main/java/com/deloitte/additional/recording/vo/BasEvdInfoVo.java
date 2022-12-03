package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * evd查询参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class BasEvdInfoVo {
    /** 显示类型 */
    private List<String> dispType;
    /** 页码 */
    private Integer page=1;
    /** 页面size */
    private Integer pagesize=10;
    /** 1.可用 0.禁用 */
    private Integer status;
    /** 搜索条件 evd名字或者编码 */
    private String searchData;
    /** 数据来源 */
    private Integer src;

}
