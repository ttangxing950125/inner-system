package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.domain.BasEvdValrange;
import com.deloitte.additional.recording.mapper.SysGroupMapper;
import com.deloitte.additional.recording.vo.*;
import com.deloitte.additional.recording.dto.EvidenceDistributionPageDto;
import com.deloitte.additional.recording.mapper.BasEvdDataDictMapper;
import com.deloitte.additional.recording.mapper.BasEvdInfoMapper;
import com.deloitte.additional.recording.mapper.BasEvdValrangeMapper;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import com.deloitte.additional.recording.vo.*;
import com.deloitte.additional.recording.vo.evd.BasEvdInfoDetailVO;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (BasEvdInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("basEvdInfoService")
public class BasEvdInfoServiceImpl extends ServiceImpl<BasEvdInfoMapper, BasEvdInfo> implements BasEvdInfoService {
    @Resource
    private BasEvdInfoMapper basEvdInfoMapper;
    @Resource
    private BasEvdDataDictMapper basEvdDataDictMapper;
    @Resource
    private BasEvdValrangeMapper evdValrangeMapper;
    @Resource
    private SysGroupMapper sysGroupMapper;
    /**
     *添加方法描述
     *
     * @param basEvdInfoVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @Override
    public R paging(BasEvdInfoVo basEvdInfoVo) {
        List<String> dispType = basEvdInfoVo.getDispType();
        String searchData = basEvdInfoVo.getSearchData();
        Integer src = basEvdInfoVo.getSrc();
        Integer status = basEvdInfoVo.getStatus();

        Page<BasEvdInfo> pageInfo=new Page<>(basEvdInfoVo.getPage(),basEvdInfoVo.getPagesize());
        QueryWrapper<BasEvdInfo>query=new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(dispType)){
            query.lambda().in(BasEvdInfo::getDispType, dispType);
        }
        if (ObjectUtils.isNotEmpty(src)){
            query.lambda().eq(BasEvdInfo::getSrc, src);
        }
        if (ObjectUtils.isNotEmpty(status)){
            query.lambda().eq(BasEvdInfo::getStatus, status);
        }
        if (ObjectUtils.isNotEmpty(searchData)){
            query.and(wapper->wapper.lambda().like(BasEvdInfo::getCode, searchData).or().like(BasEvdInfo::getName, searchData));
        }
        Page<BasEvdInfo> page = basEvdInfoMapper.selectPage(pageInfo, query);
        return R.ok(page);
    }

//    @Override
//    public BasEvdInfo getByCodeAndName(String evd_code,String name) {
//        return lambdaQuery().eq(BasEvdInfo::getCode,evd_code).eq(BasEvdInfo::getName,name).one();
//        Page<BasEvdInfo> pageInfo=new Page(basEvdInfoVo.getPage(),basEvdInfoVo.getPagesize());
//        QueryWrapper<BasEvdInfo>query=new QueryWrapper<>();
//        if (CollectionUtils.isNotEmpty(dispType)){
//            query.lambda().in(BasEvdInfo::getDispType, dispType);
//        }
//        if (ObjectUtils.isNotEmpty(src)){
//            query.lambda().eq(BasEvdInfo::getSrc, src);
//        }
//        if (ObjectUtils.isNotEmpty(status)){
//            query.lambda().eq(BasEvdInfo::getStatus, status);
//        }
//        if (ObjectUtils.isNotEmpty(searchData)){
//            query.and(wapper->wapper.lambda().like(BasEvdInfo::getCode, searchData).or().like(BasEvdInfo::getName, searchData));
//        }
//        Page<BasEvdInfo> page = basEvdInfoMapper.selectPage(pageInfo, query);
//        return R.ok(page);
//    }
    /**
     * 一键启用
     *
     * @param eviIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @Override
    public R openEvidence(List<Integer> eviIds) {
        return updateEvdStatus(eviIds, Common.NORMAL);
    }
    /**
     * 一键禁用
     *
     * @param eviIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @Override
    public R disableEvidence(List<Integer> eviIds) {
        return updateEvdStatus(eviIds, Common.DELETE);
    }
    /**
     * 修改Evd
     *
     * @param basEvdInfoUpdateVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateNewEvd(BasEvdInfoUpdateVo basEvdInfoUpdateVo) {
        BasEvdInfo basEvdInfo = basEvdInfoUpdateVo.haveBasEvdInfo();
        basEvdInfoMapper.updateById(basEvdInfo);
        return R.ok(Common.UPDATE_SUCCESS);
    }
    /**
     * 新增Evd
     *
     * @param basEvdInfoUpdateVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @Override
    public R addNewEvd(BasEvdInfoUpdateVo basEvdInfoUpdateVo) {
        BasEvdInfo basEvdInfo = basEvdInfoUpdateVo.newBasEvdInfo();

        BasEvdInfo lastOne = basEvdInfoMapper.selectOne(new QueryWrapper<BasEvdInfo>().lambda().orderByDesc(BasEvdInfo::getId).last(Common.SQL_LIMIT_ONE));
        Integer lastId = lastOne.getId() + 1;
        String evdCode = createEvdCode(lastId);
        basEvdInfo.setCode(evdCode);
        basEvdInfoMapper.insert(basEvdInfo);
        List<String> dicts = basEvdInfoUpdateVo.getDicts();
        if (CollectionUtils.isNotEmpty(dicts)){
            dicts.forEach(o->{
                BasEvdValrange evdValrange = new BasEvdValrange();
                evdValrange.setEvdCode(evdCode).setValue(o);
                evdValrangeMapper.insert(evdValrange);
            });
        }
        return  R.ok(Common.INSERT_SUCCESS);
    }

    @Override
    public R getEvidenceDistributionList(EvidenceDistributionPageDto distributionPageDto) {
        List<Integer> groupCollocterIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(distributionPageDto.getGroupIds())){
            groupCollocterIds = sysGroupMapper.getGroupCollocterIds(distributionPageDto.getGroupIds());
        }
        Page<EvidenceDistributionVo> page = new Page<>(distributionPageDto.getPageNum(),distributionPageDto.getPagesize());
        List<EvidenceDistributionVo> evidenceDistributionVos = basEvdInfoMapper.getEvidenceDistributionPage(
                page,distributionPageDto,distributionPageDto.getVersionNames(),distributionPageDto.getModelMasterIds(),
                distributionPageDto.getQualIds(),distributionPageDto.getEntityIds(),distributionPageDto.getEvidenceIds(),
                distributionPageDto.getCollocterIds(),distributionPageDto.getApproverIds(),groupCollocterIds);
        if(CollectionUtils.isEmpty(evidenceDistributionVos)){
            throw new ServiceException("没有数据");
        }
        List<String> codeList = evidenceDistributionVos.stream().distinct().map(EvidenceDistributionVo::getCode).collect(Collectors.toList());
        List<EvidenceVo> evidenceVoList = basEvdInfoMapper.getLabel(codeList);
        if(!CollectionUtils.isEmpty(evidenceVoList)){
            Map<String, String> map = evidenceVoList.stream().collect(Collectors.toMap(EvidenceVo::getEvdCode, EvidenceVo::getLabel));
            evidenceDistributionVos.forEach(e -> {
                e.setLabel(map.get(e.getCode()));
            });
        }
        page.setRecords(evidenceDistributionVos);
        return R.ok(page);
    }

    @Override
    public List<EvdNameCodeVo> getAllEvdNameCode() {
        LambdaQueryWrapper<BasEvdInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(BasEvdInfo::getId,BasEvdInfo::getName,BasEvdInfo::getCode);
        List<BasEvdInfo> list = this.list(wrapper);
        List<EvdNameCodeVo> evdNameCodeVoList = new ArrayList<>();
        list.forEach(b -> {
            EvdNameCodeVo evdNameCodeVo = new EvdNameCodeVo();
            evdNameCodeVo.setEvidenceId(b.getId());
            evdNameCodeVo.setEvidenceName(b.getName());
            evdNameCodeVo.setEvidenceCode(b.getCode());
            evdNameCodeVoList.add(evdNameCodeVo);
        });
        return null;
    }

    @Override
    public BasEvdInfo getByCodeAndName(String evd_code,String name) {
        return lambdaQuery().eq(BasEvdInfo::getCode,evd_code)
                .eq(BasEvdInfo::getName,name).one();
    }

    @Override
    public List<BasEvdInfoDetailVO> findByQualCode(String qualCOde) {
        return this.baseMapper.findByQualCode(qualCOde);
    }

    /** 改变EVD状态 */
    public R updateEvdStatus(List<Integer> eviIds,Integer status) {
        BasEvdInfo basEvdInfo=new BasEvdInfo();
        basEvdInfo.setStatus(status);
        int update = basEvdInfoMapper.update(basEvdInfo, new QueryWrapper<BasEvdInfo>().lambda().in(BasEvdInfo::getId, eviIds));
        return R.ok(Common.UPDATE_SUCCESS+update+"条数据");
    }

    /**
     * 生成新的指标Code
     *
     * @param lastId
     * @return String
     * @author 冉浩岑
     * @date 2022/11/13 17:25
     */
    public String createEvdCode(Integer lastId){
        String code=Common.EVD_FRONT;
        int length = lastId.toString().length();
        for (int i=0;i<6-length;i++){
            code=code+0;
        }
        code=code+lastId;
        return code;
    }
}
