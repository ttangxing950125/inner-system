package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.common.core.domain.R;

/**
 * (PrsVersionMaster)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
public interface PrsVersionMasterService extends IService<PrsVersionMaster> {
    /**
     * 根据版本和敞口查询对应指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:08
     */
    R paging(VersionMasterEvdVo versionMasterEvdVo);

    /**
     * 查询关联关系，不区分状态
     * @param versionId
     * @param modelCode
     * @return
     */
    PrsVersionMaster findByVersionMaster(Integer versionId, String modelCode);
    R getVersionEvdByMasters(VersionMasterEvdVo versionMasterEvdVo);
}
