package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.EntityInfoMapper;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.service.EntityInfoService;
import org.springframework.stereotype.Service;

/**
 * (EntityInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("entityInfoService")
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper, EntityInfo> implements EntityInfoService {

}
