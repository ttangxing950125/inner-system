package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.common.core.domain.R;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (PrsModelQual)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsModelQualService extends IService<PrsModelQual> {
    /**
     * 获取动态表头 改SQL 已做过较好优化
     * 详情:EXPAN SELECT
     * 	model.id AS id,
     * 	model.qual_code AS qualCode,
     * 	model.qual_name AS qualName,
     * 	model.description AS description
     * FROM
     * 	(
     * 	SELECT
     * 		t2.ver_mas_id,
     * 		t2.qual_code
     * 	FROM
     * 		prs_ver_mas_qual t2
     * 		LEFT JOIN (
     * 		SELECT
     * 			m.id,
     * 			m.prj_id,
     * 			m.model_code,
     * 			prs.NAME,
     * 			prs.time_value
     * 		FROM
     * 			prs_project_versions prs
     * 			LEFT JOIN prs_version_master m ON prs.id = m.prj_id
     * 		WHERE
     * 			prs.`name` = ?
     * 			AND prs.`time_value` = ?
     * 			AND m.`model_code` = ?
     * 		) t1 ON t2.ver_mas_id = t1.id
     * 	WHERE
     * 		t2.ver_mas_id = t1.id
     * 	) t3
     * 	LEFT JOIN prs_model_qual model ON t3.qual_code = model.qual_code
     * WHERE
     * 	t3.qual_code = model.qual_code
     * @param modelCode
     * @param timeValue
     * @param name
     * @return
     */
    List<DataListPageTataiVo> queryByPageStatsdetail(String modelCode, String timeValue, String name);
    /**
     * 分页查询全部指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:56
     */
    R getAllQualOfPage(VersionMasterEvdVo versionMasterEvdVo);
}
