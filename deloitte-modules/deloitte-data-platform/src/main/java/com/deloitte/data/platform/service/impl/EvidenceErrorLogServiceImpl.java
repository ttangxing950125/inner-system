package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_3;
import com.deloitte.data.platform.domian.EvidenceErrorLog;
import com.deloitte.data.platform.mapper.EvidenceErrorLogMapper;
import com.deloitte.data.platform.service.IEvidenceErrorLogService;
import org.springframework.stereotype.Service;

/**
 * 中间层公式执行结果错误日志
 *
 * @author XY
 * @date 2022/11/22
 */
@Service
@Master_3
public class EvidenceErrorLogServiceImpl extends ServiceImpl<EvidenceErrorLogMapper, EvidenceErrorLog> implements IEvidenceErrorLogService {

}
