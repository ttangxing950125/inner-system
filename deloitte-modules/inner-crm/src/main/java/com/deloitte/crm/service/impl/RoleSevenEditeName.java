package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.RoleSevenTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 正杰
 * @description: RoleSevenEditeName
 * @date 2022/10/28
 */
@Component
@AllArgsConstructor
public class RoleSevenEditeName implements RoleSevenTask {

    private EntityInfoMapper entityInfoMapper;

    private EntityInfoManager entityInfoManager;

    private ICrmEntityTaskService iCrmEntityTaskService;

    @Override
    public R finishTask(EntityInfoInsertDTO entityInfoInsertDTO) {
        //修改主体名称
        String entityCode = entityInfoInsertDTO.getEntityCode();
        EntityInfo entityInfo = Optional.ofNullable(entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_ENTITY_INFO.getInfo()));
        entityInfoManager.updateEntityName(entityInfo,entityInfoInsertDTO.getEntityName(),null);
        //绑定关联关系
        entityInfoManager.bindData(entityInfoInsertDTO,null, SecurityUtils.getUsername());
        //完成任务
        return iCrmEntityTaskService.finishTask(entityInfoInsertDTO.getTaskId(),1,null,entityInfoInsertDTO.getRemarks());
    }
}
