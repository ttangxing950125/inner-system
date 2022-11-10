package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.SysMenuRole;

/**
 * (SysMenurole)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:26
 */
@Mapper
public interface SysMenuRoleMapper extends BaseMapper<SysMenuRole> {

}
