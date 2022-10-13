package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.BondConvertibleInfo;

/**
 * 可转债发行预案(BondConvertibleInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@Mapper
public interface BondConvertibleInfoMapper extends BaseMapper<BondConvertibleInfo> {

}
