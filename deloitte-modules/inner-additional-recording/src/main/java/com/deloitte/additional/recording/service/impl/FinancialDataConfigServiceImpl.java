package com.deloitte.additional.recording.service.impl;

import com.deloitte.additional.recording.domain.FinancialDataConfig;
import com.deloitte.additional.recording.mapper.FinancialDataConfigMapper;
import com.deloitte.additional.recording.service.FinancialDataConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 财务数据配置  服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class FinancialDataConfigServiceImpl extends ServiceImpl<FinancialDataConfigMapper, FinancialDataConfig> implements FinancialDataConfigService {

}
