package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.dto.EvidenceDistributionPageDto;
import com.deloitte.additional.recording.vo.BasEvdInfoUpdateVo;
import com.deloitte.additional.recording.vo.BasEvdInfoVo;
import com.deloitte.additional.recording.vo.EvdNameCodeVo;
import com.deloitte.additional.recording.vo.evd.BasEvdInfoDetailVO;
import com.deloitte.common.core.domain.R;

import java.util.List;

/**
 * (BasEvdInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface BasEvdInfoService extends IService<BasEvdInfo> {

    /**
     *添加方法描述
     *
     * @param basEvdInfoVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    R paging(BasEvdInfoVo basEvdInfoVo);
    /**
     * 根据code 查询
     * @param evd_code code
     * @return BasEvdInfo
     */
    R addNewEvd(BasEvdInfoUpdateVo basEvdInfoUpdateVo);
    /**
     * 一键启用
     *
     * @param eviIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    R openEvidence(List<Integer> eviIds);
    /**
     * 一键禁用
     *
     * @param eviIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    R disableEvidence(List<Integer> eviIds);
    /**
     * 修改Evd
     *
     * @param basEvdInfoUpdateVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    R updateNewEvd(BasEvdInfoUpdateVo basEvdInfoUpdateVo);
    /**
     * evidence批量分配
     * @param distributionPageDto
     * @return
     */
    R getEvidenceDistributionList(EvidenceDistributionPageDto distributionPageDto);

    /**
     * 查询evidence的名称和code
     * @return
     */
    List<EvdNameCodeVo> getAllEvdNameCode();
    BasEvdInfo getByCodeAndName(String evd_code,String name);

    /**
     * 查询指标下的evdence
     * @param qualCOde 指标code
     * @return
     */
    List<BasEvdInfoDetailVO> findByQualCode(String qualCOde);
}
