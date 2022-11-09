package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * (SysUser)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:28
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据名称查找用户
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 查询按钮菜单。。多久重构下吧，看着头疼
     * @param userId
     * @return
     */
    List<Map<String, Object>> findMenuMap(Integer userId);

    /**
     * 查询角色列表
     * @param user
     * @return
     */
    List<String> findSysRoleByUser(SysUser user);
}
