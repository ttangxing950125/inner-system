package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.PrsQualEvdDetailsMapper;
import com.deloitte.additional.recording.domain.PrsQualEvdDetails;
import com.deloitte.additional.recording.service.PrsQualEvdDetailsService;
import org.springframework.stereotype.Service;

/**
 * evd字段比较大，单独拎出来了(PrsQualEvdDetails)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsQualEvdDetailsService")
public class PrsQualEvdDetailsServiceImpl extends ServiceImpl<PrsQualEvdDetailsMapper, PrsQualEvdDetails> implements PrsQualEvdDetailsService {

}
