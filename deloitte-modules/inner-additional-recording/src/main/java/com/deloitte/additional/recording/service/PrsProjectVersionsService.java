package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.vo.version.PrsProjectVersionSelectVO;
import com.deloitte.common.core.domain.R;

import java.util.List;

/**
 * (PrsProjectVersions)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsProjectVersionsService extends IService<PrsProjectVersions> {
    /**
     * 查询版本配置列表
     *
     * @param year   年份
     * @param status 状态
     * @param name  版本名称
     * @param pageNum  页码
     * @param pageSize  页面size
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 16:32
     */
    Page<PrsProjectVersions> getPrsProjectVersions(String year, String status, String name, Integer pageNum, Integer pageSize);

    /**
     * 统计-数据清单模块 -查询版本 下拉框专用方法
     * @return
     */
    List<String> findAllPrsProjectVersions();
    /**
     * 一键禁用
     *
     * @param ids  版本id列表
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:51
     */
    Integer updateStatusToDownByIds(List<Integer> ids);
    /**
     * 一键启用
     *
     * @param ids  版本id列表
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:51
     */
    Integer updateStatusToUpByIds(List<Integer> ids);
    /**
     * 新增版本
     *
     * @param prsProjectVersions
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 18:00
     */
    R insertPrsProjectVersions(PrsProjectVersions prsProjectVersions);

    /**
     * 自定义查询-版本敞口
     * @param year
     * @return
     */
    R finPrsProjectVersionsByYear(Integer[] year);

    /**
     * 自定义查询-版本敞口查询公司
     * @param year
     * @param versionId
     * @param industryId
     * @return
     */
    R getCustomEntityInfoByVersionIdAndModelId(String year, String versionId, String industryId);

    /**
     * 获取版本选择列表
     * @param userYear 年份
     * @return  List<PrsProjectVersionSelectVO>
     */
    List<PrsProjectVersionSelectVO> selectList(String userYear);
    /**
     * 获取下拉框 版本 带上年份
     * @param timeValue
     * @return
     */
    R getVersionByTimeValue(String timeValue);
    R getVersion();
}
