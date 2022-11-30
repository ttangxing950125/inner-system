package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.EntityInfo;

/**
 * (EntityInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface EntityInfoService extends IService<EntityInfo> {

    /**
     * 根据实体code查询
     * @param entity_code code
     * @return EntityInfo
     */
    EntityInfo getByCode(String entity_code);

    /**
     * 根据社会信用统一代码查询
     * @param creditCode 社会信用统一代码
     * @return
     */
    EntityInfo getByCreditCode(String creditCode);
}
