package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.RoleSevenTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

    private EntityInfoManager entityInfoManager;

    private ICrmEntityTaskService iCrmEntityTaskService;

    @Override
    public R finishTask(EntityInfoInsertDTO entityInfoInsertDTO) {
        Integer taskId = entityInfoInsertDTO.getTaskId();
        //查询当条数据
        CrmEntityTask crmEntityTask = Optional.ofNullable(crmEntityTaskMapper.selectOne(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getId,taskId))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
        //绑定关联关系
//        entityInfoInsertDTO.set
        entityInfoManager.bindData(entityInfoInsertDTO,null, SecurityUtils.getUsername());
        //完成任务
        return iCrmEntityTaskService.finishTask(taskId,1,null);
    }
}
