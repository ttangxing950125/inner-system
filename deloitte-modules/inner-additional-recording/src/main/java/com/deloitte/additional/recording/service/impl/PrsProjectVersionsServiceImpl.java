package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import org.springframework.stereotype.Service;

/**
 * (PrsProjectVersions)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsProjectVersionsService")
public class PrsProjectVersionsServiceImpl extends ServiceImpl<PrsProjectVersionsMapper, PrsProjectVersions> implements PrsProjectVersionsService {

}
