package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.mapper.SysUserMapper;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.service.SysUserService;
import com.deloitte.common.core.utils.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * (SysUser)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:28
 */
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
        if (u!=null&& !md5Password.equals(u.getPwd().toUpperCase())) {
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
     */
    @Override
    public SysMenu findmenubyuser(SysUser user) {
        SysMenu menu=new SysMenu();
        menu.setName("菜单");
        //查询列表并组织树形结构
        List<Map<String,Object>> list= sysUserMapper.findMenuMap(user.getId());
        Map<Long, SysMenu> treemenumap=new TreeMap();
        for(int i=0;i<list.size();i++){
            Map<String,Object> op=list.get(i);
            SysMenu _menu=new SysMenu();
            this.copymenu(_menu, op);
            treemenumap.put(Long.valueOf(op.get("id").toString()), _menu);

        }

        //循环组织树形结构
        for(int i=0;i<list.size();i++){
            Map<String,Object> op=list.get(i);
            SysMenu _menu=(SysMenu)treemenumap.get(op.get("id"));
            if(Integer.parseInt(op.get("parentid")+"")!=0){
                SysMenu _pmenu=(SysMenu)treemenumap.get(Long.valueOf(op.get("parentid").toString()));
                _pmenu.getMenus().add(_menu);
            }else{
                menu.getMenus().add(_menu);
            }

        }
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

    private void copymenu(SysMenu menu, Map<String, Object> val) {
        menu.setId(Long.valueOf(val.get("id")+"") );
        menu.setName((String)val.get("name"));
        menu.setParentid(Long.valueOf(val.get("parentid")+""));
        menu.setUrl((String)val.get("url"));
        menu.setSortkey(Integer.parseInt(val.get("sortkey")+""));
        menu.setStatus((String)val.get("status"));
        menu.setIconpath((String)val.get("iconpath"));
    }
}
