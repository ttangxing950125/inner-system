package com.deloitte.additional.recording.vo;

import com.deloitte.additional.recording.domain.PrsModelMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 敞口查询参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class ModelMasterVo {
    /**
     * 页面大小，默认10
     */
    private Integer pageSize = 10;
    /**
     * 页码，默认1
     */
    private Integer page = 1;
    /**
     * 输入搜索选项
     */
    private String searchData;
    /**
     * 选择是否可用 1.正常 0.删除
     */
    private Integer status;
    /**
     * 指标名称
     */
    private String name;
    /**
     * 指标ID
     */
    private Integer masterId;
    /**
     * 指标描述
     */
    private String description;

    public PrsModelMaster havePrsModelMaster(){
        PrsModelMaster prsModelMaster = new PrsModelMaster();
        prsModelMaster.setStatus(this.status).setName(this.name).setId(this.masterId).setDescription(this.description);
        return prsModelMaster;
    }
}

