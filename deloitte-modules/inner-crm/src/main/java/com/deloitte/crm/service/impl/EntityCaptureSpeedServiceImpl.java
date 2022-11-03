package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityInfoList;
import com.deloitte.crm.dto.EntityCaptureSpeedDto;
import com.deloitte.crm.mapper.EntityCaptureSpeedMapper;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:19
 */
@Slf4j
@Service
public class EntityCaptureSpeedServiceImpl extends ServiceImpl<EntityCaptureSpeedMapper, EntityCaptureSpeed> implements EntityCaptureSpeedService {

    @Autowired
    private EntityCaptureSpeedMapper entityCaptureSpeedMapper;
    @Autowired
    private ICrmEntityTaskService crmEntityTaskService;

    /**
     * 更新记录角色3.4.5的任务进度
     *
     * @author 冉浩岑
     * @date 2022/10/27 14:25
     */
    @Override
    public void sendTFFSpeed(CrmSupplyTask crmSupplyTask, EntityInfo entityInfo) {
        log.info("  >>>> 更新记录角色3.4.5的任务进度,roleId=[{}],entityCode=[{}] <<<<  ", crmSupplyTask.getRoleId(), entityInfo.getEntityCode());
        Integer speedId = crmSupplyTask.getSpeedId();

        if (ObjectUtils.isEmpty(speedId)) {
            return;
        }
        EntityCaptureSpeed entityCaptureSpeed = entityCaptureSpeedMapper.selectById(speedId);
        if (ObjectUtils.isEmpty(entityCaptureSpeed)) {
            return;
        }
        EntityCaptureSpeed updateSpeed = new EntityCaptureSpeed();
        updateSpeed.setSupplementTime(new Date()).setId(speedId).setSupplement(1).setUpdater(SecurityUtils.getUsername()).setEntityName(entityInfo.getEntityName()).setEntityCode(entityInfo.getEntityCode()).setCreditCode(entityInfo.getCreditCode());
        entityCaptureSpeedMapper.updateById(updateSpeed);
    }

    @Override
    public IPage<EntityCaptureSpeedDto> search(String entityNameOrCode, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        Page<EntityInfoList> page = new Page<>(pageNum, pageSize);
        IPage<EntityCaptureSpeedDto> searchBypage = entityCaptureSpeedMapper.search(page, entityNameOrCode);
        List<EntityCaptureSpeedDto> records = searchBypage.getRecords();
        if (CollUtil.isEmpty(records)) {
            return searchBypage;
        }
//        Collections.reverse(records);
        List<EntityCaptureSpeedDto> collect = records.stream().sorted(Comparator.comparing(EntityCaptureSpeedDto::getId).reversed()).collect(Collectors.toList());
        searchBypage.setRecords(collect);
        return searchBypage;
    }
}
