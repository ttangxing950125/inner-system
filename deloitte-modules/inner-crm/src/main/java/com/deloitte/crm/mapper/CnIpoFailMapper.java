package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnIpoFail;

/**
 * IPO-发行失败(CnIpoFail)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:49
 */
@Mapper
public interface CnIpoFailMapper extends BaseMapper<CnIpoFail> {

}
