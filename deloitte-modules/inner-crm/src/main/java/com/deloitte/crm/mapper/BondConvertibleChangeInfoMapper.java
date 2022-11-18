package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;

/**
 * 可交换转债发行预案(BondConvertibleChangeInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@Mapper
public interface BondConvertibleChangeInfoMapper extends BaseMapper<BondConvertibleChangeInfo> {

}
