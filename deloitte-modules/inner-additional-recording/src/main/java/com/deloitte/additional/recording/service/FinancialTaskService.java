package com.deloitte.additional.recording.service;

import com.deloitte.additional.recording.domain.FinancialTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.dto.EntityInfoByFinDto;
import com.deloitte.common.core.domain.R;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
public interface FinancialTaskService extends IService<FinancialTask> {
    /**
     * 生成financial_task数据 三表
     */
    Object cretateFinancialTaskByFinDataRecording();

    /**
     * 生成financial_task数据 结构化附注表
     */
    Object cretateFinancialTaskByStructuredNotes();

    /**
     * 三表补录审核详情页
     * @param entityCode
     * @param code
     * @param reportDate
     * @param dataTypeCode
     * @return
     */
    R getTaskDetail(Integer entityCode, String code, String reportDate, String dataTypeCode);

   /**
    *三表1一注补录列表(年份下拉框)
    *
    * @return R
    * @author penTang
    * @date 2022/11/29 11:41
   */
    R getDataYear();

    /**
     *查询三表一注的列表
     *
     * @return R
     * @author penTang
     * @date 2022/11/29 13:46
     */
    R getFinalEntityView(EntityInfoByFinDto entityInfoByFinDto);
}
