package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.TreeService;
import com.deloitte.additional.recording.vo.TreeVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.system.api.domain.SysDictData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 冉浩岑
 * @date 2022/11/22 9:54
 */
@Service
public class TreeServiceImpl implements TreeService {
    @Resource
    private PrsProjectVersionsMapper versionsMapper;

    @Resource
    private PrsVersionMasterMapper versionMasterMapper;

    @Resource
    private PrsModelMasterMapper modelMasterMapper;

    @Resource
    private PrsVerMasQualMapper verMasQualMapper;

    @Resource
    private PrsModelQualMapper modelQualMapper;

    @Resource
    private PrsModelQualEvdMapper modelQualEvdMapper;

    @Resource
    private BasEvdInfoMapper evdInfoMapper;

    @Resource
    private SysDictDataMapper dictDataMapper;
    /**
     * 树性结构统计
     *
     * @param type 查询类型 0.版本 1.敞口 2.指标 3.evd
     * @param value  参数 Id
     * @param selYearValue  年份 版本年份
     * @return R
     * @author 冉浩岑
     * @date 2022/11/21 10:01
     */
    @Override
    public R treeDataList(Integer type, String value, String selYearValue) {
        List<TreeVo>result=new ArrayList<>();
        if (ObjectUtils.isEmpty(type)){
            type=0;
        }
        switch (type){
            //查询版本
            case 0:
                QueryWrapper<PrsProjectVersions> versionsQueryWrapper = new QueryWrapper<>();
                if (ObjectUtils.isNotEmpty(selYearValue)){
                    versionsQueryWrapper.lambda().eq(PrsProjectVersions::getTimeValue,selYearValue);
                }
                List<PrsProjectVersions> versions = versionsMapper.selectList(versionsQueryWrapper);
                if (CollectionUtils.isNotEmpty(versions)){
                    versions.forEach(o->{
                        TreeVo treeVo=new TreeVo();
                        treeVo.setId(o.getId()).setValue(o.getName()+"_"+ selYearValue).setParentId(value);
                        result.add(treeVo);
                    });
                }
                break;
            //查询敞口
            case 1:
                QueryWrapper<PrsVersionMaster> versionMasterQueryWrapper = new QueryWrapper<>();
                if (ObjectUtils.isNotEmpty(value)){
                    versionMasterQueryWrapper.lambda().eq(PrsVersionMaster::getPrjId,value);
                }
                List<PrsVersionMaster> versionMasters = versionMasterMapper.selectList(versionMasterQueryWrapper);
                if (CollectionUtils.isNotEmpty(versionMasters)){
                    List<String>masterCodes=new ArrayList<>();
                    versionMasters.forEach(o-> masterCodes.add(o.getModelCode()));
                    List<PrsModelMaster> prsModelMasters = modelMasterMapper.selectList(new QueryWrapper<PrsModelMaster>().lambda().in(PrsModelMaster::getModelCode, masterCodes));
                    if (CollectionUtils.isNotEmpty(prsModelMasters)){
                        prsModelMasters.forEach(o->{
                            TreeVo treeVo=new TreeVo();
                            treeVo.setId(o.getId()).setValue(o.getName()+"_"+ o.getModelCode()).setParentId(value);
                            result.add(treeVo);
                        });
                    }
                }
                break;
            //查询指标
            case 2:
                QueryWrapper<PrsVerMasQual> verMasQualQuery = new QueryWrapper<>();
                if (ObjectUtils.isNotEmpty(value)){
                    verMasQualQuery.lambda().eq(PrsVerMasQual::getVerMasId,value);
                }
                List<PrsVerMasQual> verMasQuals = verMasQualMapper.selectList(verMasQualQuery);
                if (CollectionUtils.isNotEmpty(verMasQuals)){
                    List<String>qualCodes=new ArrayList<>();
                    verMasQuals.forEach(o->qualCodes.add(o.getQualCode()));
                    List<PrsModelQual> prsModelQuals = modelQualMapper.selectList(new QueryWrapper<PrsModelQual>().lambda().in(PrsModelQual::getQualCode, qualCodes));
                    if (CollectionUtils.isNotEmpty(prsModelQuals)){
                        prsModelQuals.forEach(o->{
                            TreeVo treeVo=new TreeVo();
                            treeVo.setId(o.getQualCode()).setValue(o.getQualName()+"_"+ o.getQualCode()).setParentId(value);
                            result.add(treeVo);
                        });
                    }
                }
                break;
            //查询evd
            case 3:
                QueryWrapper<PrsModelQualEvd> modelQualEvdQuery = new QueryWrapper<>();
                if (ObjectUtils.isNotEmpty(value)){
                    modelQualEvdQuery.lambda().eq(PrsModelQualEvd::getQualCode,value);
                }
                List<PrsModelQualEvd> modelQualEvds = modelQualEvdMapper.selectList(modelQualEvdQuery);
                if (CollectionUtils.isNotEmpty(modelQualEvds)){
                    List<String>evdCodes=new ArrayList<>();
                    modelQualEvds.forEach(o->evdCodes.add(o.getEvdCode()));
                    List<BasEvdInfo> evdInfos = evdInfoMapper.selectList(new QueryWrapper<BasEvdInfo>().lambda().in(BasEvdInfo::getCode, evdCodes));
                    List<SysDictData> evdDispTypes = dictDataMapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, "evdDispType"));
                    if (CollectionUtils.isNotEmpty(evdInfos)){
                        evdInfos.forEach(o->{
                            TreeVo treeVo=new TreeVo();
                            String varType="";
                            if (CollectionUtils.isNotEmpty(evdDispTypes)){
                                List<SysDictData> collect = evdDispTypes.stream().filter(evdDispType -> evdDispType.getDictValue().equals(o.getDispType())).collect(Collectors.toList());
                                if (CollectionUtils.isNotEmpty(collect)){
                                    varType=collect.get(0).getDictLabel();
                                }
                            }
                            treeVo.setId(o.getCode()).setValue(o.getName()+"("+ o.getCode()+")"+"_"+varType).setParentId(value);
                            result.add(treeVo);
                        });
                    }
                }
                break;
        }
        return R.ok(result);
    }
}
