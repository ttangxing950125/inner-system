package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_2;
import com.deloitte.data.platform.domian.FactorErrorLog;
import com.deloitte.data.platform.mapper.FactorErrorLogMapper;
import com.deloitte.data.platform.service.IFactorErrorLogService;
import org.springframework.stereotype.Service;

/**
 * 指标层公式执行结果错误日志
 *
 * @author XY
 * @date 2022/11/22
 */
@Service
@Master_2
public class FactorErrorLogServiceImpl extends ServiceImpl<FactorErrorLogMapper, FactorErrorLog> implements IFactorErrorLogService {

}
