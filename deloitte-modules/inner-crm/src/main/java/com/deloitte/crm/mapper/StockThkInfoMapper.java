package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.StockThkInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 股票信息表(StockThkInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:19
 */
@Mapper
public interface StockThkInfoMapper extends BaseMapper<StockThkInfo> {

    /**
     * 根据证券代码查询港股信息（StockThkInfo）
     * @param secIssInfoCode
     * @return
     */
    StockThkInfo findByCode(String secIssInfoCode);

    Integer selectListByEntityCode(@Param("entityCode") String entityCode);
}
