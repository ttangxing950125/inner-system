package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.BaseEntityDataUpdateLog;
import com.deloitte.data.platform.vo.DataUpdateInfoVo;

/**
 * <p>
 * 基础主体数据更新日志  服务类
 * </p>
 *
 * @author fangliu
 * @date 2022/11/15 11:40:39
 */
public interface IBaseEntityDataUpdateLogService extends IService<BaseEntityDataUpdateLog> {
    /**
     * 最新更新信息
     * @return
     */
    DataUpdateInfoVo getBaseDataConfigUpdateInfo();
}
