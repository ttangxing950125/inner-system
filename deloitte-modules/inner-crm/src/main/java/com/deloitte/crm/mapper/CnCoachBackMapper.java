package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnCoachBack;

/**
 * IPO-辅导备案(CnCoachBack)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:48
 */
@Mapper
public interface CnCoachBackMapper extends BaseMapper<CnCoachBack> {

    /**
     * 查询最后一条 IPO-辅导备案(CnCoachBack)
     * @param entityName
     * @return
     */
    CnCoachBack findLastByEntityName(String entityName);
}
