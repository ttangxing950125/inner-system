package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.StockCnInfo;
import org.apache.ibatis.annotations.Param;

/**
 * a股信息表，大陆股票(StockCnInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
@Mapper
public interface StockCnInfoMapper extends BaseMapper<StockCnInfo> {

    /**
     * 查询大陆股票信息
     * @param code
     * @return
     */
    StockCnInfo findByCode(String code);

    Integer selectListByEntityCode(@Param("entityCode") String entityCode);
}
