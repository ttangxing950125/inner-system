package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.RoleSevenTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @author 正杰
 * @description: RoleSevenIgnore
 * @date 2022/10/28
 */
@Component
@AllArgsConstructor
public class RoleSevenIgnore implements RoleSevenTask {

    private CrmEntityTaskMapper crmEntityTaskMapper;

    private EntityInfoMapper entityInfoMapper;

    private EntityInfoManager entityInfoManager;

    private ICrmEntityTaskService iCrmEntityTaskService;

    @Override
    public R finishTask(EntityInfoInsertDTO entityInfoInsertDTO) {
        EntityInfo entityInfo;
        if(ObjectUtils.isEmpty(entityInfoInsertDTO.getCreditCode())){
            entityInfo = Optional.ofNullable(entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityName, entityInfoInsertDTO.getEntityName()))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_ENTITY_INFO.getInfo()));
        }else{
            entityInfo = Optional.ofNullable(entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode, entityInfoInsertDTO.getCreditCode()))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_ENTITY_INFO.getInfo()));
        }
        entityInfoInsertDTO.setEntityCode(entityInfo.getEntityCode());
        //绑定关联关系
        entityInfoManager.bindData(entityInfoInsertDTO,null, SecurityUtils.getUsername());
        //完成任务
        return iCrmEntityTaskService.finishTask(entityInfoInsertDTO.getTaskId(),1,null);
    }
}
