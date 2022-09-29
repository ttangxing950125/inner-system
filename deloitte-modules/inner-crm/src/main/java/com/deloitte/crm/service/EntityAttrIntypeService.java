package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.EntityAttrIntype;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/09/29 17:51
 */
public interface EntityAttrIntypeService extends IService<EntityAttrIntype> {

    List<EntityAttrIntype> getTypeByAttrId(Integer attrId);
}
