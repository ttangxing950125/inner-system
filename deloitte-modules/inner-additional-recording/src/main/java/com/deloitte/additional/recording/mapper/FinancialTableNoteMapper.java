package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.domain.FinancialTableNote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-28
 */
public interface FinancialTableNoteMapper extends BaseMapper<FinancialTableNote> {

    void financialTableNoteInnerSysData();
}
