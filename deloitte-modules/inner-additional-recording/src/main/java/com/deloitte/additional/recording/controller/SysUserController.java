package com.deloitte.additional.recording.controller;



import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.dto.UserModel;
import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.additional.recording.service.SysUserService;
import com.deloitte.additional.recording.util.MetaSecurity;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.JwtUtil;
import com.deloitte.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * (SysUser)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:28
 */
@RestController
@RequestMapping("sysUser")
@Slf4j
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysDictDataService sysDictDataService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sendEvd")
    public String sendEvd(Integer count){
        for (Integer i = 0; i < count; i++) {
            BasEvdData evdData = new BasEvdData();
            evdData.setEntityCode("IB"+i);
            evdData.setEvdCode("E_"+i);
            evdData.setEvdVal(String.valueOf(i*100));
            SendResult sendResult = this.rocketMQTemplate.syncSendOrderly("updateEvdTopic", evdData,i+"");
            log.info("MQ发送顺序消息成功,key={} msg={},sendResult={}", "evdData", evdData, sendResult);
        }
        return "成功";
    }

    @GetMapping("/getLoginUser")
    public MetaR getLoginUser(){
        return MetaR.fail(MetaSecurity.getLoginUser());
    }

    @GetMapping("/login")
    public Object login(String username, String password){
        if(StringUtil.isBlank(username)){
            return MetaR.fail(801,"用户名不能为空。");
        }
        if(StringUtil.isBlank(password)){
            return MetaR.fail(801,"密码不能为空。");
        }
        UserModel userModel = new UserModel();
        SysUser u = sysUserService.login(username,password);//登录密码进行md5加密校验
        if (u==null){
            return MetaR.fail(801,"登录失败，用户名或密码错误。");
        }


        if(!Objects.equals("1", u.getStatus()) ){
            return MetaR.fail(801,"用户已注销。");
        }
        //登录成功，设置菜单属性


        SysMenu menu = sysUserService.findmenubyuser(u);
        if(menu.getMenus().size()<=0){
            return MetaR.fail(801,"用户还未授权，请设置权限。");
        }
        userModel.setMenu(menu);
        List<String> roleList = sysUserService.getRoleList(u);
        userModel.setRoleList(roleList);
        userModel.setUser(u);


        SysDictData curYear = sysDictDataService.findByTypeDefault("search_year");
        userModel.setCurYear(curYear.getDictValue());

        Map<String,Object> reuslt = new HashMap<String,Object>();

        reuslt.put("index", menu.getMenus().get(0).getUrl());
        userModel.setUser(u);
        //记录在线用户，辣鸡！
//        UserMapUtil.userMap.put(u.getId()+"", userModel);
        String token= JwtUtil.sign(u.getId()+"");
        reuslt.put("retcode", "200");
        reuslt.put("access_token", token);
        reuslt.put("userModel", userModel);
        reuslt.put("msg", "登录成功");


        return reuslt;
    }
    
}
