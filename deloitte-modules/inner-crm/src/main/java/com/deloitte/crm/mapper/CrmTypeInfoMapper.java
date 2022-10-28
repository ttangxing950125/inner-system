package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CrmTypeInfo;

/**
 * (CrmTypeInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-28 13:11:01
 */
@Mapper
public interface CrmTypeInfoMapper extends BaseMapper<CrmTypeInfo> {

}
