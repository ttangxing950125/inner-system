package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.EntityBaseBusiInfo;

/**
 * @author 冉浩岑
 * @date 2022/10/13 15:15
 */
public interface EntityBaseBusiInfoService  extends IService<EntityBaseBusiInfo> {
    EntityBaseBusiInfo getInfoByEntityCode(String entityCode);
}
