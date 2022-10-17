package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;
import com.deloitte.crm.mapper.EntityInfoLogsUpdatedMapper;
import com.deloitte.crm.service.EntityInfoLogsUpdatedService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

/**
 * @author 正杰
 * @description: EntityInfoLogsUpdatedServiceImpl
 * @date 2022/10/17
 */
@Service
@AllArgsConstructor
public class EntityInfoLogsUpdatedServiceImpl extends ServiceImpl<EntityInfoLogsUpdatedMapper, EntityInfoLogsUpdated> implements EntityInfoLogsUpdatedService {

    /**
     *  查询上市企业或是地方政府的更新记录
     *
     * @param tableType -> 1-企业上市信息 || 2-地方政府上市信息
     * @param pageNum default 1
     * @param pageSize default 10
     * @return {@link EntityInfoLogsUpdated}
     */
    @Override
    public R<Page<EntityInfoLogsUpdated>> getInfo(Integer tableType, Integer pageNum, Integer pageSize) {
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        return R.ok(baseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<EntityInfoLogsUpdated>().lambda().eq(EntityInfoLogsUpdated::getTableType,tableType).orderBy(true,false,EntityInfoLogsUpdated::getUpdated)), SuccessInfo.SUCCESS.getInfo());
    }
}
