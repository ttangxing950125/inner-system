package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.BasEvdDataMapper;
import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.service.BasEvdDataService;
import org.springframework.stereotype.Service;

/**
 * (BasEvdData)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:36
 */
@Service("basEvdDataService")
public class BasEvdDataServiceImpl extends ServiceImpl<BasEvdDataMapper, BasEvdData> implements BasEvdDataService {

}
