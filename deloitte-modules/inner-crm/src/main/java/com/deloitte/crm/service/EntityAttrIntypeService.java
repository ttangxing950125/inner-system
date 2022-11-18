package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityAttrIntype;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/09/29 17:51
 */
public interface EntityAttrIntypeService extends IService<EntityAttrIntype> {

    List<EntityAttrIntype> getTypeByAttrId(Integer attrId);

    /**
     * 通过 id 查询 wind 一二级 类型 一级attr_id 为92 二级为85
     * @author 正杰
     * @date 2022/10/11
     * @param id
     * @return
     */
    R<List<EntityAttrIntype>> getWindBondType(Integer id);
}
