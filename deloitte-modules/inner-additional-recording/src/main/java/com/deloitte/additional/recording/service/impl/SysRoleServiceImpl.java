package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.SysRole;
import com.deloitte.additional.recording.mapper.SysRoleMapper;
import com.deloitte.additional.recording.service.SysRoleService;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.StrUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * (SysRole)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Page<SysRole> page(String name, String status, Integer page, Integer pageSize) {

        return lambdaQuery()
                .likeRight(StrUtil.isNotBlank(name), SysRole::getName, name)
                .eq(StrUtil.isNotBlank(status), SysRole::getStatus, status)
                .page(new Page<>(page, pageSize));
    }

    @Override
    public SysRole getByName(String name) {
        return lambdaQuery().eq(SysRole::getName, name).one();
    }

    @Override
    public void modify(Integer id, String name, String status) {
        SysRole role = getById(id);
        if (role == null) {
            throw new ServiceException("更新失败，角色不存在", HttpStatus.NOT_FOUND.value());
        }
        //判断名称
        SysRole roleByName = getByName(name);
        if (roleByName != null && !id.equals(roleByName.getId())) {
            throw new ServiceException("更新失败,角色名称已存在", HttpStatus.FOUND.value());
        }
        //保存
        role.setStatus(status);
        role.setName(name);
        updateById(role);
    }
}
