package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.deloitte.additional.recording.domain.BaseFinDataRecording;
import com.deloitte.additional.recording.domain.BaseStructuredNotes;
import com.deloitte.additional.recording.domain.FinancialTask;
import com.deloitte.additional.recording.mapper.BaseStructuredNotesMapper;
import com.deloitte.additional.recording.mapper.FinancialDataConfigMapper;
import com.deloitte.additional.recording.mapper.FinancialTaskMapper;
import com.deloitte.additional.recording.service.BaseFinDataRecordingService;
import com.deloitte.additional.recording.service.BaseStructuredNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.vo.CreateFinTaskMetaDataVo;
import com.deloitte.common.core.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 结构化附注表  服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class BaseStructuredNotesServiceImpl extends ServiceImpl<BaseStructuredNotesMapper, BaseStructuredNotes> implements BaseStructuredNotesService {

}
