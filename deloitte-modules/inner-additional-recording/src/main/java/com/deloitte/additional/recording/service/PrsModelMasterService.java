package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsModelMaster;

import java.util.List;
import java.util.Map;

/**
 * (PrsModelMaster)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsModelMasterService extends IService<PrsModelMaster> {

    List<Map<String, Object>> finAllPrsModelMaster();
}
