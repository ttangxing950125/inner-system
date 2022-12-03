package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 树型统计返回工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class TreeVo {
    /** 显示类型 */
    private Object parentId;
    /** id */
    private Object id;
    /** 页面size */
    private String value;
    /** 子级 */
    private List<TreeVo> children;

}
