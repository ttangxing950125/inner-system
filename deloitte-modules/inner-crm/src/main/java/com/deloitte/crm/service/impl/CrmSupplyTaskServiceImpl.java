package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.mapper.CrmSupplyTaskMapper;
import com.deloitte.crm.mapper.SysUserRoleMapper;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import com.deloitte.system.api.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmSupplyTaskServiceImpl extends ServiceImpl<CrmSupplyTaskMapper, CrmSupplyTask> implements ICrmSupplyTaskService
{
    @Autowired
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmSupplyTask selectCrmSupplyTaskById(Long id)
    {
        return crmSupplyTaskMapper.selectCrmSupplyTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmSupplyTask> selectCrmSupplyTaskList(CrmSupplyTask crmSupplyTask)
    {
        return crmSupplyTaskMapper.selectCrmSupplyTaskList(crmSupplyTask);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmSupplyTask(CrmSupplyTask crmSupplyTask)
    {
        return crmSupplyTaskMapper.insertCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmSupplyTask(CrmSupplyTask crmSupplyTask)
    {
        return crmSupplyTaskMapper.updateCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskByIds(Long[] ids)
    {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskById(Long id)
    {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskById(id);
    }
    @Override
    public R getRoleSupplyTask() {
        //获取登录用户
        Long userId = SecurityUtils.getUserId();
        QueryWrapper<SysUserRole>userRleQuery=new QueryWrapper<>();
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(userRleQuery.lambda()
                .eq(SysUserRole::getUserId, userId)
                .in(SysUserRole::getRoleId, "5", "6", "7")
        );
        //不是 角色 3 4 5则不返回信息
        if (CollectionUtils.isEmpty(sysUserRoles)){
            return R.ok();
        }
        Long roleId = sysUserRoles.get(0).getRoleId();
        QueryWrapper<CrmSupplyTask>taskQuery=new QueryWrapper<>();
        return R.ok(crmSupplyTaskMapper.selectList(taskQuery.lambda().eq(CrmSupplyTask::getRoleId, roleId)));
    }



}
