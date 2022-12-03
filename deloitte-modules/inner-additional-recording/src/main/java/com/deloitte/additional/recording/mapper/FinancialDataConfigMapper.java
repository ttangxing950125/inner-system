package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.domain.FinancialDataConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.vo.CreateFinTaskMetaDataVo;
import com.deloitte.additional.recording.vo.GetTaskDataDetailByParamVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 财务数据配置  Mapper 接口
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
public interface FinancialDataConfigMapper extends BaseMapper<FinancialDataConfig> {
     /**
      * 获取生成任务的数据 三表
      * <sql>
      *     SELECT
      *             data.id,
      *             data.entity_code as entityCode,
      *             conf.data_type_code as dataTypeCode,
      *             conf.table_type as tableType,
      *             YEAR ( data.report_date ) as year,
      *             data.report_date   as reportDate
      *         FROM
      *             base_fin_data_recording  data
      *             INNER JOIN financial_data_config conf ON data.code = conf.code
      *             WHERE `data`.is_create_recording = 0
      *             AND data.is_deleted=0 and conf.is_deleted=0
      *             GROUP BY conf.data_type_code, data.entity_code, data.report_date
      * </sql>
      * @return
      */
     List<CreateFinTaskMetaDataVo> getFinancialDataCreateTask();

     /**
      * 获取生成任务的数据  结构化附注表
      * <sql>
      *     SELECT
      *     note.id,
      * 	conf.data_type_code,
      * 	note.report_date,
      * 	YEAR ( note.report_date ),
      * 	conf.table_type,
      * 	note.entity_code
      * FROM
      * 	base_structured_notes note
      * 	INNER JOIN financial_data_config conf ON note.CODE = conf.`code` and conf.is_deleted=0 and note.is_deleted=0
      * WHERE
      * 	note.is_create_recording = 0
      * GROUP BY
      * 	conf.data_type_code,
      * 	note.report_date,
      * 	note.entity_code
      * </sql>
      */
     List<CreateFinTaskMetaDataVo> getStructuredNotesCreateTask();

     /**
      *
      * @param entityCode
      * @param reportDate
      * @param dataTypeCode
      * @return
      */
     List<GetTaskDataDetailByParamVo> getTaskDataDetailByParam(@Param("entityCode") String entityCode, @Param("reportDate") String reportDate, @Param("dataTypeCode") String dataTypeCode);
}
