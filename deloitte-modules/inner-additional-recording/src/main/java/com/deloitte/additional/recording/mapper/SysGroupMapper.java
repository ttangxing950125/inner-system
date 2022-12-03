package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.SysGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysGroup)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:30
 */
@Mapper
public interface SysGroupMapper extends BaseMapper<SysGroup> {

    List<Integer> getGroupCollocterIds(@Param("groupIds") List<Integer> groupIds);

    List<Long> getGroupIds(@Param("currentUserId") Integer currentUserId, @Param("name") String name);

    List<Long> getUserGroupIds(@Param("groupIds") List<Long> groupIds);
}
