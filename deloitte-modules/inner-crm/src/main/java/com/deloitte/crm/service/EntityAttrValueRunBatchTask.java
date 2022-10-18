package com.deloitte.crm.service;

import com.deloitte.crm.domain.EntityAttrValue;

import java.util.List;

/**
 *
 * 批量导入数据进入 => entityAttrValue
 *
 * @author 正杰
 * @description: EntityAttrValueRunBatchTask
 * @date 2022/10/14
 */
public interface EntityAttrValueRunBatchTask {

    /**
     * 需要参数 {@link com.deloitte.crm.domain.EntityAttrValue}
     * 获取当前表中所有需要上传的参数
     * @return EntityAttrValue
     * @throws Exception
     */
    List<EntityAttrValue> getPrams();

    /**
     * 开始导入数据
     */
    void runBatchData();

}
