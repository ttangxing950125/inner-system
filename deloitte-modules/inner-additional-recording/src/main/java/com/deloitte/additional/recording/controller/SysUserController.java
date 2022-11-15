package com.deloitte.additional.recording.controller;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.application.service.SysGroupUserRoleService;
import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.dto.UserModel;
import com.deloitte.additional.recording.request.SysUserPageRequest;
import com.deloitte.additional.recording.request.SysUserRequest;
import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.additional.recording.service.SysUserService;
import com.deloitte.additional.recording.util.MetaSecurity;
import com.deloitte.additional.recording.vo.user.SysUserVO;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.utils.JwtUtil;
import com.deloitte.system.api.domain.SysDictData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.deloitte.common.core.domain.MetaR.ok;

/**
 * (SysUser)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:28
 */
@RestController
@RequestMapping("sysUser")
@Slf4j
@Api("用户控制层")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysGroupUserRoleService sysGroupUserRoleService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sendEvd")
    public String sendEvd(Integer count) {
        for (Integer i = 0; i < count; i++) {
            BasEvdData evdData = new BasEvdData();
            evdData.setEntityCode("IB" + i);
            evdData.setEvdCode("E_" + i);
            evdData.setEvdVal(String.valueOf(i * 100));
            SendResult sendResult = this.rocketMQTemplate.syncSendOrderly("updateEvdTopic", evdData, i + "");
            log.info("MQ发送顺序消息成功,key={} msg={},sendResult={}", "evdData", evdData, sendResult);
        }
        return "成功";
    }

    @GetMapping("/getLoginUser")
    public MetaR getLoginUser() {
        return MetaR.ok(MetaSecurity.getLoginUser());
    }

    @GetMapping("/login")
    public Object login(String username, String password) {
        if (StringUtil.isBlank(username)) {
            return MetaR.fail(801, "用户名不能为空。");
        }
        if (StringUtil.isBlank(password)) {
            return MetaR.fail(801, "密码不能为空。");
        }
        UserModel userModel = new UserModel();
        SysUser u = sysUserService.login(username, password);//登录密码进行md5加密校验
        if (u == null) {
            return MetaR.fail(801, "登录失败，用户名或密码错误。");
        }


        if (!Objects.equals("1", u.getStatus())) {
            return MetaR.fail(801, "用户已注销。");
        }
        //登录成功，设置菜单属性


        SysMenu menu = sysUserService.findmenubyuser(u);
        if (menu.getMenus().size() <= 0) {
            return MetaR.fail(801, "用户还未授权，请设置权限。");
        }
        userModel.setMenu(menu);
        List<String> roleList = sysUserService.getRoleList(u);
        userModel.setRoleList(roleList);
        userModel.setUser(u);


        SysDictData curYear = sysDictDataService.findByTypeDefault("search_year");
        userModel.setCurYear(curYear.getDictValue());

        Map<String, Object> reuslt = new HashMap<String, Object>();

        reuslt.put("index", menu.getMenus().get(0).getUrl());
        userModel.setUser(u);
        //记录在线用户，辣鸡！
//        UserMapUtil.userMap.put(u.getId()+"", userModel);
        String token = JwtUtil.sign(u.getId() + "");
        reuslt.put("retcode", "200");
        reuslt.put("access_token", token);
        reuslt.put("userModel", userModel);
        reuslt.put("msg", "登录成功");


        return reuslt;
    }


    @ApiOperation("新增用户")
    @GetMapping("addsave")
    public MetaR add(@Valid SysUserRequest saveRequest) {
        sysGroupUserRoleService.addSave(saveRequest.getName(), saveRequest.getEmail(), saveRequest.getSex(), saveRequest.getStatus(), saveRequest.getGroupid(), saveRequest.getRoles());
        return ok();
    }

    @ApiOperation("修改用户信息")
    @GetMapping("modfiysave")
    public MetaR modify(@Valid SysUserRequest modifyRequest) {

        sysGroupUserRoleService.modify(modifyRequest.getId(), modifyRequest.getName(), modifyRequest.getEmail(), modifyRequest.getSex(), modifyRequest.getStatus(), modifyRequest.getGroupid(), modifyRequest.getRoles());

        return ok();
    }

    @ApiOperation("重置密码")
    @GetMapping("resetsave")
    public MetaR resetPassword(@RequestParam("id") Integer userId) {

        sysUserService.resetPassword(userId);
        return ok();
    }

    @ApiOperation("禁用用户")
    @GetMapping("disableUser")
    public MetaR disableUser(@RequestParam("id") Integer userId) {
        sysUserService.disableUser(userId);
        return ok();
    }

    @ApiOperation("分页条件查询用户列表")
    @GetMapping("paging")
    public MetaR<Page<SysUserVO>> page(@Valid SysUserPageRequest pageRequest) {

        Page<SysUserVO> page = sysGroupUserRoleService.page(pageRequest.getName(), pageRequest.getNickname(), pageRequest.getStatus(), pageRequest.getGroups(), pageRequest.getRole(), pageRequest.getPage(), pageRequest.getPagesize());

        return ok(page);
    }


}
