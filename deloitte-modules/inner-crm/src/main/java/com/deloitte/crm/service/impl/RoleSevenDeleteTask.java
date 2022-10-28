package com.deloitte.crm.service.impl;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.RoleSevenTask;
import org.springframework.stereotype.Component;

/**
 * @author 正杰
 * @description: RoleSevenDeleteTask
 * @date 2022/10/28
 */
@Component
public class RoleSevenDeleteTask implements RoleSevenTask {

    private ICrmEntityTaskService iCrmEntityTaskService;

    @Override
    public R finishTask(EntityInfoInsertDTO entityInfoInsertDTO) {
        return iCrmEntityTaskService.finishTask(entityInfoInsertDTO.getTaskId(),3,null);
    }
}
