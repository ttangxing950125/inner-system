package com.deloitte.additional.recording.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.SysRole;
import com.deloitte.additional.recording.service.SysMenuroleService;
import com.deloitte.additional.recording.service.SysRoleService;
import com.deloitte.additional.recording.vo.role.SysRoleVo;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.utils.bean.BeanUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.deloitte.common.core.domain.MetaR.ok;

/**
 * (SysRole)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:32
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuroleService menuRoleService;


    @ApiOperation("分页列表")
    @GetMapping("paging")
    public MetaR<Page<SysRoleVo>> page(@ApiParam(name = "角色名称") @RequestParam("name") String name,
                                       @ApiParam(name = "角色状态") @RequestParam("status") String status,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<SysRole> sysRolePage = sysRoleService.page(name, status, page, pageSize);
        Page<SysRoleVo> roleVoPage = new Page<>(page, pageSize);
        if (sysRolePage.getRecords() != null) {
            roleVoPage.setRecords(BeanUtils.copy(sysRolePage.getRecords(), SysRoleVo.class));
            roleVoPage.setTotal(sysRolePage.getTotal());
            roleVoPage.setPages(sysRolePage.getPages());
        }
        return ok(roleVoPage);
    }

    @ApiOperation("新增角色")
    @GetMapping("addsave")
    public MetaR add(@ApiParam("角色名称") @RequestParam(value = "name") String name,
                     @ApiParam("角色状态：0：禁用 1：正常") @RequestParam(value = "status", required = false, defaultValue = "1") String status,
                     @ApiParam(value = "菜单权限id字符串，用，拼接", example = "1,2") @RequestParam(value = "menunodes", required = false) String menunodes) {

        menuRoleService.addMenRole(name, status, menunodes);
        return ok();
    }

    @ApiOperation("修改角色")
    @GetMapping("modfiysave")
    public MetaR modify(@RequestParam("id") Integer id,
                        @ApiParam("角色名称") @RequestParam("name") String name,
                        @ApiParam("角色状态：0：禁用 1：正常") @RequestParam("status") String status) {

        sysRoleService.modify(id, name, status);
        return ok();
    }

    @ApiOperation("权限设置")
    @GetMapping("rolesettingsave")
    public MetaR roleSetting(@RequestParam("id") Integer id,
                             @ApiParam(value = "菜单权限id字符串，用，拼接", example = "1,2") @RequestParam(value = "menunodes", required = false) String menunodes) {

        menuRoleService.roleMenuSetting(id, menunodes);
        return ok();
    }
}
