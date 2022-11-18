package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysMenuRole;

import javax.validation.constraints.NotBlank;

/**
 * (SysMenurole)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:24
 */
public interface SysMenuroleService extends IService<SysMenuRole> {


    void  addMenRole(@NotBlank(message = "角色名称不能为空") String name, String status, String menunodes);

    /**
     * 设置角色权限
     * @param id 角色id
     * @param menunodes 菜单权限ID字符串
     */
    void roleMenuSetting(Integer id, String menunodes);
}
