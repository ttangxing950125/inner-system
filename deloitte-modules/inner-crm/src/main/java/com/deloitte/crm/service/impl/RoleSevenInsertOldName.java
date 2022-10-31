package com.deloitte.crm.service.impl;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.RoleSevenTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 正杰
 * @description: RoleSevenInsertOldName
 * @date 2022/10/28
 */
@Component
@AllArgsConstructor
public class RoleSevenInsertOldName implements RoleSevenTask {

    private EntityNameHisServiceImpl entityNameHisService;

    private EntityInfoManager entityInfoManager;
    private ICrmEntityTaskService iCrmEntityTaskService;

    @Override
    public R finishTask(EntityInfoInsertDTO entityInfoInsertDTO) {
        String entityCode = entityInfoInsertDTO.getEntityCode();
        //添加主体曾用名
        entityNameHisService.addEntityNameHis(entityCode,entityInfoInsertDTO.getEntityName());
        //绑定关联数据
        entityInfoManager.bindData(entityInfoInsertDTO,null, SecurityUtils.getUsername());
        //完成任务
        return iCrmEntityTaskService.finishTask(entityInfoInsertDTO.getTaskId(),1,null);
    }
}
