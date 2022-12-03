package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.vo.CenterMasterVo;
import com.deloitte.additional.recording.vo.ModelMasterVo;
import com.deloitte.additional.recording.vo.master.PrsModelMasterSelectVO;
import com.deloitte.common.core.domain.R;

import java.util.List;
import java.util.Map;

/**
 * (PrsModelMaster)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsModelMasterService extends IService<PrsModelMaster> {
    /**
     * 统计-数据清单模块 -下拉框专用 获取敞口数据
     * @return
     */
    List<Map<String, Object>> finAllPrsModelMaster();
    /**
     * 获取所有敞口基础数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 15:54
     */
    R getAllMaster();

    /**
     * 下拉选择列表
     * @return List<PrsModelMasterSelectVO>
     */
    List<PrsModelMasterSelectVO> selectList();

    R getAvailableMaster();

    /**
     * 获取所有敞口基础数据 通过版本查询
     * @param prjId
     * @return
     */
    R finAllPrsModelMasterByPrjId(Integer prjId);
    /**
     * 分页查询敞口列表
     *
     * @param modelMasterVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    R paging(ModelMasterVo modelMasterVo);
    /**
     * 一键启用
     *
     * @param masterIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    R openMaster(List<Integer> masterIds);
    /**
     * 一键禁用
     *
     * @param masterIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    R disableMaster(List<Integer> masterIds);
    /**
     * 修改敞口
     *
     * @param modelMasterVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    R updateMaster(ModelMasterVo modelMasterVo);
    /**
     * 新增敞口
     *
     * @param prsModelMaster
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    R addMaster(PrsModelMaster prsModelMaster);
    /**
     * 敞口映射查询
     *
     * @param centerMasterVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/17 10:48
     */
    R tranPaging(CenterMasterVo centerMasterVo);

    /**
     * 查询敞口
     * @param
     * @return R
     * @author 王大鹏
     * @date 2022/11/14 9:32
     */
    R queryExposureToList();

    /**
     * 根据类型查询敞口
     * @param type
     * @return
     */
    R getTypeModelMaster(Integer type);
    List<PrsModelMasterSelectVO> selectList(Integer versionId);
}
