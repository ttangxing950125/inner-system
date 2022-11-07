package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.ModelTaskDetailsMapper;
import com.deloitte.additional.recording.domain.ModelTaskDetails;
import com.deloitte.additional.recording.service.ModelTaskDetailsService;
import org.springframework.stereotype.Service;

/**
 * (ModelTaskDetails)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("modelTaskDetailsService")
public class ModelTaskDetailsServiceImpl extends ServiceImpl<ModelTaskDetailsMapper, ModelTaskDetails> implements ModelTaskDetailsService {

}
