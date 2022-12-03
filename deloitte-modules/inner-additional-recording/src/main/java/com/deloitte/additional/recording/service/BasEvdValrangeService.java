package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.BasEvdValrange;
import com.deloitte.common.core.domain.R;

/**
 * (BasEvdValrange)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface BasEvdValrangeService extends IService<BasEvdValrange> {

    R getAllEvdDataDict();
    /**
     * evdCode赋值
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/16 9:22
     */
    R setBasEvdValrange();
}
