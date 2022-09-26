package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnApprdWaitIss;

/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
@Mapper
public interface CnApprdWaitIssMapper extends BaseMapper<CnApprdWaitIss> {

}
