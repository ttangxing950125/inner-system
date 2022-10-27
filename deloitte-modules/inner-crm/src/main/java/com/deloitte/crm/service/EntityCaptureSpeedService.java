package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityCaptureSpeed;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:19
 */
public interface EntityCaptureSpeedService extends IService<EntityCaptureSpeed> {
    void sendTFFSpeed(CrmSupplyTask crmSupplyTask);
}
