package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.copier.Copier;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityCaptureSpeedDto;
import com.deloitte.crm.mapper.EntityCaptureSpeedMapper;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import org.springframework.beans.factory.annotation.Autowired;
import com.deloitte.crm.service.IEntityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:19
 */
@Slf4j
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
        if (ObjectUtils.isEmpty(speedId)) {
            return;
        }
        EntityCaptureSpeed entityCaptureSpeed = entityCaptureSpeedMapper.selectById(speedId);
        if (ObjectUtils.isEmpty(entityCaptureSpeed)) {
            return;
        }
        EntityCaptureSpeed updateSpeed = new EntityCaptureSpeed();
        updateSpeed.setId(speedId).setSupplement(1).setUpdated(new Date()).setUpdater(SecurityUtils.getUsername());
        entityCaptureSpeedMapper.updateById(updateSpeed);
    }

    @Override
    public R search(String entityNameOrCode) {
        List<EntityCaptureSpeedDto> search = entityCaptureSpeedMapper.search(entityNameOrCode);
        return R.ok(search);
    }
}
