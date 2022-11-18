package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SynTableMapper;
import com.deloitte.additional.recording.domain.SynTable;
import com.deloitte.additional.recording.service.SynTableService;
import org.springframework.stereotype.Service;

/**
 * (SynTable)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Service("synTableService")
public class SynTableServiceImpl extends ServiceImpl<SynTableMapper, SynTable> implements SynTableService {

}
