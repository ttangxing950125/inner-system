package com.deloitte.additional.recording.service.impl;

import com.deloitte.additional.recording.domain.FinancialTableNote;
import com.deloitte.additional.recording.mapper.FinancialTableNoteMapper;
import com.deloitte.additional.recording.service.FinancialTableNoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-28
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class FinancialTableNoteServiceImpl extends ServiceImpl<FinancialTableNoteMapper, FinancialTableNote> implements FinancialTableNoteService {

}
