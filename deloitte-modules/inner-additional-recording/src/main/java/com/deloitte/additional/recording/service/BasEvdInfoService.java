package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.BasEvdInfo;

/**
 * (BasEvdInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface BasEvdInfoService extends IService<BasEvdInfo> {


    /**
     * 根据code 查询
     * @param evd_code code
     * @return BasEvdInfo
     */
    BasEvdInfo getByCodeAndName(String evd_code,String name);
}
