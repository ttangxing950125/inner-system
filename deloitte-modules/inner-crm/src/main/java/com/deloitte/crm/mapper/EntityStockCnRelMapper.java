package com.deloitte.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityStockCnRel;
import org.apache.ibatis.annotations.Param;

/**
 * (EntityStockCnRel)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:36
 */
@Mapper
public interface EntityStockCnRelMapper extends BaseMapper<EntityStockCnRel> {

    /**
     * 查询关联关系
     * @param entityCode
     * @param stockDqCode
     * @return
     */
    EntityStockCnRel findByEntityStockDeCode(@Param("entityCode") String entityCode,
                                             @Param("dqCode") String stockDqCode);
}
