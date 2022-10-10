package com.deloitte.crm.vo;

import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author 正杰
 * @description: ModleMasterInfoVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class ModelMasterInfoVo {

    /**
     * 企业名称
     */
    private String entityName;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 来源
     */
    private String source;

    /**
     * 用作 wind 和 申万 的值展示与修改
     */
    private Map<String,AttrValueMapDto> attrs;

}
