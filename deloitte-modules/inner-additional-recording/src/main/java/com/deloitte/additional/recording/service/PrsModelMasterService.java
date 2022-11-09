package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.common.core.domain.R;

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
    /**
     * 获取所有敞口基础数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 15:54
     */
    R getAllMaster();
}
