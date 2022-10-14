package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.UndoStInfo;

/**
 * 撤销ST(摘帽)(UndoStInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@Mapper
public interface UndoStInfoMapper extends BaseMapper<UndoStInfo> {

}
