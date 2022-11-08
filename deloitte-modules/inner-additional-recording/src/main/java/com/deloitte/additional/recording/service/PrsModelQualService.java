package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;

import java.util.List;

/**
 * (PrsModelQual)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsModelQualService extends IService<PrsModelQual> {

    List<DataListPageTataiVo> queryByPageStatsdetail(String modelCode, String timeValue, String name);
}
