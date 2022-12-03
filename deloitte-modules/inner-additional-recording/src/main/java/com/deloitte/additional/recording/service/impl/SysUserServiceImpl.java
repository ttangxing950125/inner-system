package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.mapper.SysUserMapper;
import com.deloitte.additional.recording.service.SysUserService;
import com.deloitte.additional.recording.vo.TaskUserVo;
import com.deloitte.additional.recording.util.TreeParserUtil;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.MD5;
import com.deloitte.common.core.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (SysUser)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:28
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 登录
     *
     * @param username
     * @param password w吴鹏
     * @return
     */
    @Override
    public SysUser login(String username, String password) {
        SysUser u = null;
        // 密码MD5加密
        u = sysUserMapper.getUserByUsername(username);
        String md5Password = MD5.getMD5ofStr(password);
        if (u != null && !md5Password.equals(u.getPwd().toUpperCase())) {
            return null;
        }
        return u;
    }

    /**
     * 查询用户的菜单
     * 谁有空重写一下，之前的可能会空指针
     *
     * @param user
     * @return
     * @// TODO: 2022/11/10
     * 吴鹏鹏
     * 更新者：tangx
     * 时间：2022-11-15
     */
    @Override
    public SysMenu findmenubyuser(SysUser user) {
        SysMenu menu = new SysMenu();
        menu.setName("菜单");
        //查询列表并组织树形结构
        List<SysMenu> menus = sysUserMapper.findMenuMap(user.getId());
        //转为树形菜单
        List<SysMenu> treeList = TreeParserUtil.getTreeList(0L, menus);
        menu.setMenus(treeList);
        //此处为优化前内容
//        List<Map<String, Object>> list = sysUserMapper.findMenuMap(user.getId());
//
//        Map<Long, SysMenu> treemenumap = new TreeMap();
//        for (int i = 0; i < list.size(); i++) {
//            Map<String, Object> op = list.get(i);
//            SysMenu _menu = new SysMenu();
//            this.copymenu(_menu, op);
//            treemenumap.put(Long.valueOf(op.get("id").toString()), _menu);
//
//        }
//
//        //循环组织树形结构
//        for (int i = 0; i < list.size(); i++) {
//            Map<String, Object> op = list.get(i);
//            SysMenu _menu = (SysMenu) treemenumap.get(op.get("id"));
//            if (Integer.parseInt(op.get("parentid") + "") != 0) {
//                SysMenu _pmenu = (SysMenu) treemenumap.get(Long.valueOf(op.get("parentid").toString()));
//                _pmenu.getMenus().add(_menu);
//            } else {
//                menu.getMenus().add(_menu);
//            }
//
//        }
        return menu;
    }

    /**
     * 查询角色列表
     * @param user
     * @return
     */
    @Override
    public List<String> getRoleList(SysUser user) {
        return sysUserMapper.findSysRoleByUser(user);
    }

    @Override
    public List<TaskUserVo> getUserNameList(String loginName) {
        List<TaskUserVo> taskUserVoList = sysUserMapper.getUserNameList(loginName);
        return taskUserVoList;
    }

    @Override
    public SysUser getByEmail(String email) {

        return lambdaQuery().eq(SysUser::getEmail, email).one();
    }

    @Override
    public void resetPassword(Integer userId) {
        SysUser user = checkSysUser(userId);
        user.setPwd(user.getPwdString());
        updateById(user);
    }


    @Override
    public void disableUser(Integer userId) {
        SysUser sysUser = checkSysUser(userId);
        sysUser.setStatus("0");
        updateById(sysUser);
    }

    @Override
    public Page<SysUser> selectPage(String name, String nickname, String status, Set<Integer> userIds, Page<SysUser> userPage) {
        return lambdaQuery()
                .eq(StrUtil.isNotBlank(name), SysUser::getLoginname, name)
                .likeRight(StrUtil.isNotBlank(nickname), SysUser::getName, nickname)
                .eq(StrUtil.isNotBlank(status), SysUser::getStatus, status)
                .in(userIds != null, SysUser::getId, userIds).page(userPage);
    }

    @Override
    public void logout() {

    }

    @Override
    public SysUser getByName(String userName) {
        return lambdaQuery().eq(SysUser::getName, userName).one();
    }

    private void copymenu(SysMenu menu, Map<String, Object> val) {
        menu.setId(Long.valueOf(val.get("id") + ""));
        menu.setName((String) val.get("name"));
        menu.setParentid(Long.valueOf(val.get("parentid") + ""));
        menu.setUrl((String) val.get("url"));
        menu.setSortkey(Integer.parseInt(val.get("sortkey") + ""));
        menu.setStatus((String) val.get("status"));
        menu.setIconpath((String) val.get("iconpath"));
    }


    private SysUser checkSysUser(Integer userId) {
        SysUser user = getById(userId);
        if (user == null) {
            log.info("重置密码失败，用户不存在.[userId:{}]", userId);
            throw new ServiceException("重置密码失败，用户不存在", HttpStatus.NOT_FOUND.value());
        }
        return user;
    }
}
