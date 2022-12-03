package com.deloitte.additional.recording.vo.recording;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/22/10:37
 * @Description:
 */
@Data
public class ChooseDistributionPeriodCollocterVo implements Serializable {
    //用户id
    private Integer sysUserId;
    //登录用户名称
    private String loginName;
    //用户名
    private String userName;
    //用户密码
    private String pwd;
    //用户状态
    private Integer userStatus;
    //用户性别
    private Integer sex;
    //用户邮箱
    private String email;
    //用户角色Id
    private Integer rid;
    //角色名称
    private String rname;
    //组id
    private  Integer gid;
    //组长id
    private Integer  groupLeader;
    //助长名称
    private String   groupLeaderName;
    //组名
    private String  groupName;
    //组名描述
    private String  groupDesc;
    //待补录任务
    private Integer periodCollocterTaskCount;
    //等待审核任务
    private Integer periodApproverTaskCount;

}
