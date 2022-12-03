package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import com.deloitte.additional.recording.mapper.PrsModelMasterMapper;
import com.deloitte.additional.recording.mapper.PrsVerMasQualMapper;
import com.deloitte.additional.recording.mapper.PrsVersionMasterMapper;
import com.deloitte.additional.recording.service.PrsVersionMasterService;
import com.deloitte.additional.recording.vo.VersionMasterEvdBackVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.common.core.domain.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (PrsVersionMaster)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Service("prsVersionMasterService")
public class PrsVersionMasterServiceImpl extends ServiceImpl<PrsVersionMasterMapper, PrsVersionMaster> implements PrsVersionMasterService {
    @Resource
    private PrsVersionMasterMapper prsVersionMasterMapper;
    @Resource
    private PrsVerMasQualMapper prsVerMasQualMapper;
    @Resource
    private PrsModelMasterMapper prsModelMasterMapper;


    /**
     * 根据版本和敞口查询对应指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:08
     */
    @Override
    public R getVersionEvdByMasters(VersionMasterEvdVo versionMasterEvdVo) {
        Integer prjId = versionMasterEvdVo.getPrjId();
        Integer pageNum = versionMasterEvdVo.getPageNum();
        Integer pageSize = versionMasterEvdVo.getPageSize();
        List<String> modelCodes = versionMasterEvdVo.getModelCodes();
        Page<VersionMasterEvdBackVo>pageInfo=new Page<>(pageNum,pageSize);

        Page<VersionMasterEvdBackVo>result=prsVersionMasterMapper.getVersionEvdByMasters(pageInfo,prjId,modelCodes);

        return R.ok(result);
    }

    /**
     * 根据版本和敞口查询对应指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:08
     */
    @Override
    public R paging(VersionMasterEvdVo versionMasterEvdVo) {
        Integer prjId = versionMasterEvdVo.getVersionId();
        Integer pageNum = versionMasterEvdVo.getCurrPage();
        Integer pageSize = versionMasterEvdVo.getPageSize();
        List<Integer> modelIds = versionMasterEvdVo.getIndustry();
        List<String> modelCodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(modelIds)) {
            List<PrsModelMaster> prsModelMasters = prsModelMasterMapper.selectList(new QueryWrapper<PrsModelMaster>().lambda().in(PrsModelMaster::getId, modelIds));
            if (CollectionUtils.isNotEmpty(prsModelMasters)) {
                prsModelMasters.forEach(o -> modelCodes.add(o.getModelCode()));
            }
        }
        Page<VersionMasterEvdBackVo> pageInfo = new Page<>(pageNum, pageSize);
        Page<VersionMasterEvdBackVo> result = prsVersionMasterMapper.getVersionEvdByMasters(pageInfo, prjId, modelCodes);
        return R.ok(result);
    }

    /**
     * 查询关联关系，不区分状态
     *
     * @param versionId
     * @param modelCode
     * @return
     */
    @Override
    public PrsVersionMaster findByVersionMaster(Integer versionId, String modelCode) {
        LambdaQueryWrapper<PrsVersionMaster> wrapper = Wrappers.<PrsVersionMaster>lambdaQuery()
                .eq(PrsVersionMaster::getPrjId, versionId)
                .eq(PrsVersionMaster::getModelCode, modelCode);

        return this.getOne(wrapper);
    }

}
