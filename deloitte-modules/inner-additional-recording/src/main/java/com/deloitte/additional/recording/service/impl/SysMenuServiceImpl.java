package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysMenuMapper;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * (SysMenu)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:33
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

}
