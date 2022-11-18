package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsVerMasQual;
import com.deloitte.additional.recording.vo.MasterEvdVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdBackVo;
import com.deloitte.common.core.domain.R;

import java.util.List;

/**
 * (PrsVerMasQual)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
public interface PrsVerMasQualService extends IService<PrsVerMasQual> {
    /**
     * 增加配置
     *
     * @param masterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 17:27
     */
    R addMasterEvd(MasterEvdVo masterEvdVo);
    /**
     * 批量删除
     *
     * @param list
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 17:27
     */
    R deleteMasterEvd(List<VersionMasterEvdBackVo> list);
}
