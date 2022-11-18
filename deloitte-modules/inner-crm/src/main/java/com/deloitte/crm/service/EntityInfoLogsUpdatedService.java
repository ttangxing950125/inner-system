package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;

/**
 * @author 正杰
 * @description: EntityInfoLogsUpdatedService
 * @date 2022/10/17
 */
public interface EntityInfoLogsUpdatedService extends IService<EntityInfoLogsUpdated> {

    /**
     *  查询上市企业或是地方政府的更新记录
     *
     * @param tableType -> 1-企业上市信息 || 2-地方政府上市信息
     * @param pageNum default 1
     * @param pageSize default 10
     * @return {@link EntityInfoLogsUpdated}
     */
    R<Page<EntityInfoLogsUpdated>> getInfo(Integer tableType, Integer pageNum, Integer pageSize);

    /**
     * 修改后 对修改的值进行新增
     * @param code
     * @param stockShortName
     * @param old
     * @param now
     */
    void insert(String code,String stockShortName,Object old,Object now);

}
