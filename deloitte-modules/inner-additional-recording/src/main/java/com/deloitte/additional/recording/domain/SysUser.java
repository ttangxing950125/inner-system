package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysUser)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysUser implements Serializable {
    private static final long serialVersionUID = 647313932395874631L;
    /**
     * 主键
     */     @Excel(name = "主键")
    private Integer id;
    /**
     * 登陆名
     */     @Excel(name = "登陆名")
    private String loginname;
    /**
     * 显示名称
     */     @Excel(name = "显示名称")
    private String name;
    /**
     * 登陆密码
     */     @Excel(name = "登陆密码")
    private String pwd;
    /**
     * 状态：1：正常 0：注销
     */     @Excel(name = "状态：1：正常 0：注销")
    private String status;
    /**
     * 联系电话
     */     @Excel(name = "联系电话")
    private String phone;
    /**
     * 性别
     */     @Excel(name = "性别")
    private String sex;
    /**
     * 电子邮件
     */     @Excel(name = "电子邮件")
    private String email;
         @Excel(name = "${column.comment}")
    private Date created;
    /**
     * 关联的补录账号
     */     @Excel(name = "关联的补录账号")
    private Integer inputer;


}
