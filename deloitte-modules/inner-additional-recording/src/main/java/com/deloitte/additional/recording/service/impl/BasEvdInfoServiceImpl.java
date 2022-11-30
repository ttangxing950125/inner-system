package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.BasEvdInfoMapper;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import org.springframework.stereotype.Service;

/**
 * (BasEvdInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("basEvdInfoService")
public class BasEvdInfoServiceImpl extends ServiceImpl<BasEvdInfoMapper, BasEvdInfo> implements BasEvdInfoService {

    @Override
    public BasEvdInfo getByCodeAndName(String evd_code,String name) {
        return lambdaQuery().eq(BasEvdInfo::getCode,evd_code)
                .eq(BasEvdInfo::getName,name).one();
    }
}
