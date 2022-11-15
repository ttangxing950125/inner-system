package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.utils.MD5;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.deloitte.common.core.constant.Constants.PASSWORD;

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
     */
    @Excel(name = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 登陆名
     */
    @Excel(name = "登陆名")
    private String loginname;
    /**
     * 显示名称
     */
    @Excel(name = "显示名称")
    private String name;
    /**
     * 登陆密码
     */
    @Excel(name = "登陆密码")
    private String pwd;
    /**
     * 状态：1：正常 0：注销
     */
    @Excel(name = "状态：1：正常 0：注销")
    private String status;
    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phone;
    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;
    /**
     * 电子邮件
     */
    @Excel(name = "电子邮件")
    private String email;
    @Excel(name = "${column.comment}")
    private Date created;
    /**
     * 失效时间
     */
    @Excel(name = "失效时间")
    private Date validTime;
    /**
     * 关联的补录账号
     */
    @Excel(name = "关联的补录账号")
    private Integer inputer;


    public SysUser init(String name, String email, String sex, Date validTime, Integer inputer) {
        this.loginname = email;
        this.name = name;
        this.pwd = getPwdString();
        this.sex = sex;
        this.email = email;
        this.inputer = inputer;
        this.status = "1";
        this.validTime = validTime == null ? new Date() : validTime;
        return this;
    }

    public String getPwdString() {
        MD5 md5 = new MD5();
        String md5Password = md5.getMD5ofStr(PASSWORD.toLowerCase());
        md5Password = md5.getMD5ofStr(md5Password.toLowerCase());
        return md5Password.toLowerCase();
    }
}
