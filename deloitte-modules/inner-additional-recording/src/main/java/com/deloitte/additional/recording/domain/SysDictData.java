//package com.deloitte.additional.recording.domain;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;
//
//import java.io.Serializable;
//
///**
// * @author 冉浩岑
// * @date 2022/11/07 16:58
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(chain = true)
//public class SysDictData implements Serializable {
//    private static final long serialVersionUID = -27693076242756973L;
//
//    /** 字典编码 */
//    private Integer dictCode;
//    /** 字典排序 */
//    private Integer dictSort;
//    /** 字典标签 */
//    private String dictLabel;
//    /** 字典键值 */
//    private String dictValue;
//    /** 字典类型 */
//    private String dictType;
//    /** 样式属性 */
//    private String cssClass;
//    /** 表格回显样式 */
//    private String listClass;
//    /** 是否默认（Y是 N否） */
//    private String isDefault;
//    /** 状态（0正常 1停用） */
//    private String status;
//    /** 创建者 */
//    private String createBy;
//    /** 创建时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private String createTime;
//    /** 更新者 */
//    private String updateBy;
//    /** 更新时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private String updateTime;
//    /** 备注 */
//    private String remark;
//
//}
