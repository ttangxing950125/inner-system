package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.EntityInfoLogs;

import java.util.List;

/**
 * (EntityInfoLogs)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-10 13:55:11
 */
public interface EntityInfoLogsService extends IService<EntityInfoLogs> {

    /**
     * 查询全部 根据分类型
     * @return
     */
    Object findAllByType(String type);

    /**
     * 撤销
     * @param code
     * @return
     */
    Object cancel(Integer code);
}
