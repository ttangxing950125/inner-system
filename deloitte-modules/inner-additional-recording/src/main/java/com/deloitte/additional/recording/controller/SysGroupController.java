package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.application.service.SysGroupUserRoleService;
import com.deloitte.additional.recording.domain.SysGroup;
import com.deloitte.additional.recording.service.SysGroupService;
import com.deloitte.additional.recording.vo.group.SysGroupInfoVo;
import com.deloitte.additional.recording.vo.group.SysGroupSelectVo;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.utils.bean.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.deloitte.common.core.domain.MetaR.ok;

/**
 * (SysGroup)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:30
 */
@RestController
@RequestMapping("sysGroup")
@Api(tags = "SysGroup-小组控制层")
public class SysGroupController {
    /**
     * 服务对象
     */
    @Resource
    private SysGroupService sysGroupService;
    @Autowired
    private SysGroupUserRoleService groupUserRoleService;


    @ApiOperation("新增小组")
    @GetMapping("creategroup")
    public MetaR add(@ApiParam("小组名称") @RequestParam("groupName") String groupName,
                     @ApiParam("组长id") @RequestParam("groupLeader") Integer groupLeader,
                     @ApiParam("组员id，用,隔开") @RequestParam("groupMember") String groupMember) {
        groupUserRoleService.addGroup(groupName, groupLeader, groupMember);
        return ok();
    }

    @GetMapping("list")
    @ApiOperation("分组下拉选择列表")
    public MetaR<List<SysGroupSelectVo>> groupList() {
        List<SysGroup> groups = sysGroupService.list();
        if (groups == null) {
            return MetaR.fail(404, "分组信息不存在。");
        }
        return ok(BeanUtils.copy(groups, SysGroupSelectVo.class));
    }

    @ApiOperation("小组详情展示")
    @GetMapping("getGroupInfo")
    public MetaR<SysGroupInfoVo> getGroupInfo(@RequestParam("groupId") Integer groupId) {

        return ok(groupUserRoleService.getGroupInfo(groupId));

    }

    @ApiOperation("编辑小组")
    @GetMapping("updateGroup")
    public MetaR updateGroup(@ApiParam("小组id") @RequestParam("groupId") Integer groupId, @ApiParam("小组名称") @RequestParam("groupName") String groupName,
                             @ApiParam("组长id") @RequestParam("groupLeader") Integer groupLeader,
                             @ApiParam("组员id，用,隔开") @RequestParam("groupMember") String groupMember) {

        groupUserRoleService.updateGroup(groupId, groupName, groupLeader, groupMember);
        return ok();
    }

}
