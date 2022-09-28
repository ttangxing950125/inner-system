package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnIecSmpcCheckResult;

/**
 * IPO-发审委上市委审核结果(CnIecSmpcCheckResult)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:48
 */
@Mapper
public interface CnIecSmpcCheckResultMapper extends BaseMapper<CnIecSmpcCheckResult> {

    /**
     * 根据公司名称查询 IPO-发审委上市委审核结果(CnIecSmpcCheckResult))执行导入任务 表的最后一条记录
     * @param entityName
     * @return
     */
    CnIecSmpcCheckResult findLastByEntityName(String entityName);
}
