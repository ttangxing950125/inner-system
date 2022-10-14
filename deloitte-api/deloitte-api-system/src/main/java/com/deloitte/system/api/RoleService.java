package com.deloitte.system.api;

import com.deloitte.common.core.constant.SecurityConstants;
import com.deloitte.common.core.constant.ServiceNameConstants;
import com.deloitte.system.api.domain.SysDictData;
import com.deloitte.system.api.domain.SysRole;
import com.deloitte.system.api.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 查询角色
 *
 * @author PenTang
 * @date 2022/09/22 19:31
 */

@FeignClient(contextId = "RoleService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RoleService {

    /**
     * 查询角色
     *
     * @return List<SysDictData>
     * @author penTang
     * @date 2022/9/22 19:40
     */
    @PostMapping("/dict/data/queryRoles")
    List<SysDictData> getRoleByType();


    /**
     * 根据角色Id查询当前用户的信息
     *
     * @param RoleId
     * @return List<SysUser>
     * @author penTang
     * @date 2022/9/23 14:59
     */
    @PostMapping("/role/roleById")
    List<SysUser> selectUserListById(@RequestParam("RoleId") Integer RoleId);

    /**
     * 查询所有的角色(SysRole)
     *
     * @return List<SysRole>
     * @author penTang
     * @date 2022/9/26 17:49
     */
    @PostMapping("/role/roleList")
    List<SysRole> selectRoleList();

}
