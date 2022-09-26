package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnIpoPause;

/**
 * IPO-发行暂缓(CnIpoPause)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:54
 */
@Mapper
public interface CnIpoPauseMapper extends BaseMapper<CnIpoPause> {

}
