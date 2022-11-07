package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsQualEvdDetails;

/**
 * evd字段比较大，单独拎出来了(PrsQualEvdDetails)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsQualEvdDetailsMapper extends BaseMapper<PrsQualEvdDetails> {

}
