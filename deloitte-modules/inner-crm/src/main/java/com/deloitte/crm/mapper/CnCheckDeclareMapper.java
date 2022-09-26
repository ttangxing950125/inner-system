package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnCheckDeclare;

/**
 * IPO-审核申报(CnCheckDeclare)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:47
 */
@Mapper
public interface CnCheckDeclareMapper extends BaseMapper<CnCheckDeclare> {

}
