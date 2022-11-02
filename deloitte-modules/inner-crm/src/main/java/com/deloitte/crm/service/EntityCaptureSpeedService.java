package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityCaptureSpeedDto;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:19
 */
public interface EntityCaptureSpeedService extends IService<EntityCaptureSpeed> {

    void sendTFFSpeed(CrmSupplyTask crmSupplyTask, EntityInfo entityInfo);

    List<EntityCaptureSpeedDto> search(String entityNameOrCode);
}
