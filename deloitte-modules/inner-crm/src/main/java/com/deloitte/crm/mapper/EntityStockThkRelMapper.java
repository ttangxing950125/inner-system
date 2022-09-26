package com.deloitte.crm.mapper;

import com.deloitte.crm.domain.EntityBondRel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityStockThkRel;
import org.apache.ibatis.annotations.Param;

/**
 * (EntityStockThkRel)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:18
 */
@Mapper
public interface EntityStockThkRelMapper extends BaseMapper<EntityStockThkRel> {

    /**
     * 查询是否存在这个关联关系
     * @param entityCode
     * @param stockDqCode
     * @return
     */
    EntityStockThkRel findByEntityStockDeCode(@Param("entityCode") String entityCode,
                                          @Param("stockDqCode") String stockDqCode);
}
