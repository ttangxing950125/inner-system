package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.mapper.EntityCaptureSpeedMapper;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:19
 */
@Service
public class EntityCaptureSpeedServiceImpl extends ServiceImpl<EntityCaptureSpeedMapper, EntityCaptureSpeed> implements EntityCaptureSpeedService {

    @Autowired
    private EntityCaptureSpeedMapper entityCaptureSpeedMapper;
    /**
     * 更新记录角色3.4.5的任务进度
     *
     * @author 冉浩岑
     * @date 2022/10/27 14:25
    */
    @Override
    public void sendTFFSpeed(CrmSupplyTask crmSupplyTask) {
        Integer speedId = crmSupplyTask.getSpeedId();
        if (ObjectUtils.isEmpty(speedId)){
            return;
        }
        EntityCaptureSpeed entityCaptureSpeed = entityCaptureSpeedMapper.selectById(speedId);
        if (ObjectUtils.isEmpty(entityCaptureSpeed)){
            return;
        }
        EntityCaptureSpeed updateSpeed = new EntityCaptureSpeed();
        updateSpeed.setId(speedId).setSupplement(1).setUpdated(new Date()).setUpdater(SecurityUtils.getUsername());
        entityCaptureSpeedMapper.updateById(updateSpeed);
    }
}
