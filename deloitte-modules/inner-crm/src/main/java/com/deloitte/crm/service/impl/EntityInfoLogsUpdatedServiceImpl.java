package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.annotation.UpdateLog;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;
import com.deloitte.crm.mapper.EntityInfoLogsUpdatedMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.mapper.StockThkInfoMapper;
import com.deloitte.crm.service.EntityInfoLogsUpdatedService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @author 正杰
 * @description: EntityInfoLogsUpdatedServiceImpl
 * @date 2022/10/17
 */
@Service
@AllArgsConstructor
@Slf4j
public class EntityInfoLogsUpdatedServiceImpl extends ServiceImpl<EntityInfoLogsUpdatedMapper, EntityInfoLogsUpdated> implements EntityInfoLogsUpdatedService {

    private EntityInfoMapper entityInfoMapper;

    private StockCnInfoMapper stockCnInfoMapper;

    private StockThkInfoMapper stockThkInfoMapper;

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

    @Override
    public void insert(String table, Object old, Object now, Integer tableType) {
        switch (tableType){
            case 1:
                switch (table){
                    case "entity_info":
                        Class<EntityInfo> entityInfoClass = EntityInfo.class;
                        for (Field declaredField : entityInfoClass.getDeclaredFields()) {
                            UpdateLog annotation = declaredField.getAnnotation(UpdateLog.class);
                            String value = null;
                            if (annotation==null){break;}
                            try {
                                Object obj = declaredField.get(old);
                            } catch (IllegalAccessException e) {
                                continue;
                            }
                            String fieldName = annotation.fieldName();
                            String tableFieldName = annotation.tableFieldName();
                            EntityInfoLogsUpdated entityInfoLogsUpdated = new EntityInfoLogsUpdated();

                        }
                        return;
                    case "stock_cn_info":
                        return;
                    case "stock_thk_info":
                        return;
                    default:
                        return;
                }
            case 2:
                return;
            default:
                break;
        }
    }
}
