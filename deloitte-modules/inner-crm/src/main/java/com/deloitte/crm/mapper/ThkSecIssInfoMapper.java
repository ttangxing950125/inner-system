package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.ThkSecIssInfo;

/**
 * 证券发行-股票发行-聆讯信息一览(ThkSecIssInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:35
 */
@Mapper
public interface ThkSecIssInfoMapper extends BaseMapper<ThkSecIssInfo> {

    /**
     * 根据名称查询最后一条
     * @param entityCnName
     * @return
     */
    ThkSecIssInfo findLastByEntityName(String entityCnName);
}
