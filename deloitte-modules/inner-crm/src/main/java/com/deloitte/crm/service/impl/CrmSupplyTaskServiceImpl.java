package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.domain.dto.SupplyTaskDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import com.deloitte.system.api.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmSupplyTaskServiceImpl extends ServiceImpl<CrmSupplyTaskMapper, CrmSupplyTask> implements ICrmSupplyTaskService {
    @Autowired
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private EntityInfoMapper entityInfoMapper;

    @Autowired
    private GovInfoMapper govInfoMapper;

    @Autowired
    private EntityAttrMapper attrMapper;

    @Autowired
    private EntityAttrValueMapper valueMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmSupplyTask selectCrmSupplyTaskById(Long id) {
        return crmSupplyTaskMapper.selectCrmSupplyTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param crmSupplyTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmSupplyTask> selectCrmSupplyTaskList(CrmSupplyTask crmSupplyTask) {
        return crmSupplyTaskMapper.selectCrmSupplyTaskList(crmSupplyTask);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmSupplyTask(CrmSupplyTask crmSupplyTask) {
        return crmSupplyTaskMapper.insertCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmSupplyTask(CrmSupplyTask crmSupplyTask) {
        return crmSupplyTaskMapper.updateCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskByIds(Long[] ids) {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskById(Long id) {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskById(id);
    }

    @Override
    public R getRoleSupplyTask(String taskDate) {
        //获取登录用户
        Long userId = SecurityUtils.getUserId();
        QueryWrapper<SysUserRole> userRleQuery = new QueryWrapper<>();
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(userRleQuery.lambda()
                .eq(SysUserRole::getUserId, userId)
                .in(SysUserRole::getRoleId, "5", "6", "7")
        );
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(6L);
        sysUserRoles.add(sysUserRole);
        //不是 角色 3 4 5则不返回信息
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return R.ok();
        }
        Long roleId = sysUserRoles.get(0).getRoleId();
        QueryWrapper<CrmSupplyTask> taskQuery = new QueryWrapper<>();
        List<CrmSupplyTask> crmSupplyTasks = crmSupplyTaskMapper.selectList(taskQuery.lambda()
                .eq(CrmSupplyTask::getRoleId, roleId)
                .eq(CrmSupplyTask::getTaskDate, taskDate)
        );
        if (CollectionUtils.isEmpty(crmSupplyTasks)) {
            return R.ok();
        }
        List<SupplyTaskDto> taskList = new ArrayList<>();

        crmSupplyTasks.stream().forEach(o -> {
            SupplyTaskDto taskDto = new SupplyTaskDto();
            taskDto.setCrmSupplyTask(o);
            String entityCode = o.getEntityCode();
            QueryWrapper<EntityInfo> entityQuery = new QueryWrapper<>();
            EntityInfo entityInfo = entityInfoMapper.selectOne(entityQuery.lambda().eq(EntityInfo::getEntityCode, entityCode));
            taskDto.setEntityInfo(entityInfo);
            if (ObjectUtils.isEmpty(entityInfo)) {
                QueryWrapper<GovInfo> govQuery = new QueryWrapper<>();
                GovInfo govInfo = govInfoMapper.selectOne(govQuery.lambda().eq(GovInfo::getDqGovCode, entityCode));
                taskDto.setGovInfo(govInfo);
            }
            QueryWrapper<EntityAttr> attrQuery = new QueryWrapper<>();
            List<EntityAttr> entityAttrs = attrMapper.selectList(attrQuery.lambda().eq(EntityAttr::getAttrCateName, Common.ATTR_FIN));
            if (!CollectionUtils.isEmpty(entityAttrs)) {
                List<Long> ids = new ArrayList<>();
                entityAttrs.stream().forEach(x -> {
                    ids.add(x.getId());
                });
                QueryWrapper<EntityAttrValue> valueQuery = new QueryWrapper<>();
                List<EntityAttrValue> attrValueList = valueMapper.selectList(valueQuery.lambda().in(EntityAttrValue::getAttrId, ids));
                taskDto.setValues(attrValueList);
            }
            taskList.add(taskDto);
        });
        return R.ok(taskList);
    }
}
