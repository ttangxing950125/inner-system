package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.BondDelIss;

/**
 * 新债发行-推迟或取消发行债券(BondDelIss)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 12:11:32
 */
@Mapper
public interface BondDelIssMapper extends BaseMapper<BondDelIss> {

}
