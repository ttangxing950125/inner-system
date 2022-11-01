package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.ThkSecRetiredInfo;

/**
 * 证券发行-港股-已退市证券一览(ThkSecRetiredInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-01 11:45:44
 */
@Mapper
public interface ThkSecRetiredInfoMapper extends BaseMapper<ThkSecRetiredInfo> {

}
