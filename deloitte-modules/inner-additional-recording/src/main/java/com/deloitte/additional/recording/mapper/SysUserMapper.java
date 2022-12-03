package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.vo.TaskUserVo;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.vo.recording.ChooseDistributionPeriodCollocterVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.SysUser;
import org.apache.ibatis.annotations.Param;

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
     * 更新者： tangx
     * 时间：2022-22-15
     */
//    List<Map<String,Object>> findMenuMap(Integer userId);
    List<SysMenu> findMenuMap(Integer userId);

    /**
     * 查询角色列表
     * @param user
     * @return
     */
    List<String> findSysRoleByUser(SysUser user);

    /**
     * 查询补录人员和审核人员
     * @param name
     * @return
     */
    List<TaskUserVo> getUserNameList(@Param("name") String name);

    /**
     *通过userId查询角色名称
     */
    List<String> getUserIdRoleName(@Param("userId") Integer userId,@Param("name")String name,@Param("groupLeader")String groupLeader);

    /**
     * 专查询补录人员
     * @return
     */
    List<ChooseDistributionPeriodCollocterVo> chooseDistributionPeriodCollocter(@Param("rname") String rname);
}
