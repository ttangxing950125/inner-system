package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsModelQualFactor;

import java.util.List;

/**
 * (PrsModelQualFactor)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsModelQualFactorService extends IService<PrsModelQualFactor> {


    /**
     * 查询指标相关挡位
     * @param qualCode 指标code
     * @return
     */
    List<PrsModelQualFactor> findByQualCode(String qualCode);

}
