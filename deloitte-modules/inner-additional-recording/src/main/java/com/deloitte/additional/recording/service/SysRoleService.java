package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysRole;

/**
 * (SysRole)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:32
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     * @param name name
     * @param page 当前页码
     * @param pageSize 当前页面数量大小
     * @return
     */
    Page<SysRole> page(String name,String status, Integer page, Integer pageSize);

    /**
     * 根据名称查询角色
     * @param name 角色名称
     * @return SysRole
     */
    SysRole getByName(String name);

    /**
     * 更新角色信息
     * @param id id
     * @param name name
     * @param status 状态
     */
    void modify(Integer id, String name, String status);
}
