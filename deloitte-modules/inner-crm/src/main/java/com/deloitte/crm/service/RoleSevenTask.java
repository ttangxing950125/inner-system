package com.deloitte.crm.service;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.dto.EntityInfoInsertDTO;

/**
 * @author 正杰
 * @description: RoleSevenTask
 * @date 2022/10/28
 */
public interface RoleSevenTask {

    /**
     * 完成 角色7的 任务方法
     * @param entityInfoInsertDTO
     */
    R finishTask(EntityInfoInsertDTO entityInfoInsertDTO);

}
