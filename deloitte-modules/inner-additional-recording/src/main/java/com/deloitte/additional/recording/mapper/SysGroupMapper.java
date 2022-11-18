package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.SysGroup;

/**
 * (SysGroup)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:30
 */
@Mapper
public interface SysGroupMapper extends BaseMapper<SysGroup> {

}
