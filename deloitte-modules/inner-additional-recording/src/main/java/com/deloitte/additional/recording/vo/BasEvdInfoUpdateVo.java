package com.deloitte.additional.recording.vo;

import com.deloitte.additional.recording.domain.BasEvdInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * evd修改参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class BasEvdInfoUpdateVo {
    /** id */
    private Integer evdId;
    /** evd名称 */
    private String name;
    /** 值类型 */
    private String valType;
    /** 显示类型 */
    private String dispType;
    /** 状态 */
    private Integer status;
    /** 备注 */
    private String remark;
    public BasEvdInfo haveBasEvdInfo(){
        BasEvdInfo basEvdInfo=new BasEvdInfo();
        basEvdInfo.setStatus(this.status).setName(this.name).setId(this.evdId).setValType(this.valType).setDispType(this.dispType).setRemark(this.remark);
        return basEvdInfo;
    }
    /** 单位 */
    private String unit;
    public BasEvdInfo newBasEvdInfo(){
        BasEvdInfo basEvdInfo=new BasEvdInfo();
        basEvdInfo.setName(this.name).setValType(this.valType).setDispType(this.dispType).setRemark(this.remark).setUnit(this.unit);
        return basEvdInfo;
    }

    //暂时无用字段
    /** 子级Evd */
    private List<String> childEvds;
    /** 字典 */
    private List<String> dicts;

}
