package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.DataUpdateLog;
import com.deloitte.data.platform.vo.DataUpdateInfoVo;

/**
 * 基础数据更新日志  服务类
 *
 * @author fangliu
 * @date 2022/11/15 11:40:39
 */
public interface IDataUpdateLogService extends IService<DataUpdateLog> {
    /**
     * 最新更新信息
     * @return
     */
    DataUpdateInfoVo getBaseDataConfigUpdateInfo();
}
