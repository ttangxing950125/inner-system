package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.domain.PrsVerMasQual;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import com.deloitte.additional.recording.mapper.PrsModelQualMapper;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.mapper.PrsVerMasQualMapper;
import com.deloitte.additional.recording.mapper.PrsVersionMasterMapper;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.additional.recording.vo.qual.PrsQualDataSelectVO;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (PrsModelQual)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Slf4j
@Service("prsModelQualService")
public class PrsModelQualServiceImpl extends ServiceImpl<PrsModelQualMapper, PrsModelQual> implements PrsModelQualService {
    @Resource
    private PrsModelQualMapper prsModelQualMapper;
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;
    @Resource
    private PrsVersionMasterMapper prsVersionMasterMapper;
    @Resource
    private PrsVerMasQualMapper prsVerMasQualMapper;


    /**
     * 获取表头 根据 版本、敞口、年份 获取 指标
     *
     * @param modelCode
     * @param timeValue
     * @param name
     */
    @Override
    public List<DataListPageTataiVo> queryByPageStatsdetail(String modelCode, String timeValue, String name) {
        log.info("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 开始 ", name, modelCode, timeValue);
        List<DataListPageTataiVo> dataListPageTataiVoLists = prsModelQualMapper.queryByPageStatsdetail(modelCode, timeValue, name);
        if (CollUtil.isEmpty(dataListPageTataiVoLists)) {
            log.warn("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 介绍 查询数据为空! ", name, modelCode, timeValue);
            return dataListPageTataiVoLists;
        }
        List<DataListPageTataiVo> collect = dataListPageTataiVoLists.stream().map(e -> {
            String qualCode = e.getQualCode();
            e.setQualName(e.getQualName() + "-" + qualCode);
            return e;
        }).collect(Collectors.toList());
        log.warn("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 结束<<<:{} ", name, modelCode, timeValue, JSON.toJSONString(collect));


        return collect;
    }

    /**
     * 获取表头 根据 版本、敞口、年份 获取 指标
     *
     * @param modelCode
     * @param timeValue
     * @param name
     * @return
     */
    @Override
    public List<DataListPageTataiVo> queryByPageStatsdetailNoSql(String modelCode, String timeValue, String name) {
        final ArrayList<DataListPageTataiVo> dataListPageTataiVos = new ArrayList<>();
        final ArrayList<String> notData = new ArrayList<>();
        log.info("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 开始 ", name, modelCode, timeValue);
        //1.根据版本名称和年份查询版本表 确定唯一条记录
        PrsProjectVersions prsProjectVersions = prsProjectVersionsMapper.selectOne(new LambdaQueryWrapper<PrsProjectVersions>().eq(PrsProjectVersions::getName, name).eq(PrsProjectVersions::getTimeValue, timeValue).eq(PrsProjectVersions::getStatus, 1));
        PrsProjectVersions prsProjectVersions1 = Optional.ofNullable(prsProjectVersions).orElseThrow(() -> new ServiceException("参数非法根据版本名称" + name + "和年份:" + timeValue + "查询不到版本数据"));
        //2.通过版本code和id 敞口表获取 唯一一条数据
        PrsVersionMaster prsVersionMasters = prsVersionMasterMapper.selectOne(new LambdaQueryWrapper<PrsVersionMaster>().eq(PrsVersionMaster::getPrjId, prsProjectVersions1.getId()).eq(PrsVersionMaster::getStatus, 1).eq(PrsVersionMaster::getModelCode, modelCode));
        if (prsVersionMasters == null) {
            log.warn("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 结束 版本敞口表  prjId={},modeCode={} 无对应数据！！！！<<<< ", name, modelCode, timeValue, prsProjectVersions1.getId(), modelCode);
            return dataListPageTataiVos;
        }
        List<PrsVerMasQual> prsVerMasQuals = prsVerMasQualMapper.selectList(new LambdaQueryWrapper<PrsVerMasQual>().eq(PrsVerMasQual::getVerMasId, prsVersionMasters.getId()).eq(PrsVerMasQual::getStatus, 1));
        if (CollUtil.isEmpty(prsVerMasQuals)) {
            log.warn("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 结束 版本敞口指标表为空!!!  verMasId={} 无对应数据！！！！<<<< ", name, modelCode, timeValue, prsVersionMasters.getId());
            return dataListPageTataiVos;
        }
        for (PrsVerMasQual prsVerMasQual : prsVerMasQuals) {
            PrsModelQual prsModelQual = prsModelQualMapper.selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, prsVerMasQual.getQualCode()).eq(PrsModelQual::getStatus, 1));
            if (prsModelQual != null) {
                DataListPageTataiVo voBuid = DataListPageTataiVo.builder()
                        .id(prsModelQual.getId())
                        .description(prsModelQual.getDescription())
                        .qualCode(prsModelQual.getQualCode())
                        .qualName(prsModelQual.getQualName() + "-" + prsModelQual.getQualCode()).build();
                dataListPageTataiVos.add(voBuid);
            } else {
                log.warn("指标编码qual_code={},查询指标信息不存在！！！！！！");
                notData.add(prsVerMasQual.getQualCode());
            }
        }
        log.info("根据 版本敞口:年份: 获取 指标信息 结束 获取条数>>>:{},不存在指标信息表条数>>>>:{},详情>>>:{}", dataListPageTataiVos.size(), notData.size(), JSON.toJSONString(notData));
        return dataListPageTataiVos;
    }

    /**
     * 分页查询全部指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:56
     */
    @Override
    public R getAllQualOfPage(VersionMasterEvdVo versionMasterEvdVo) {
        Page<PrsModelQual> pageInfo = new Page<>(versionMasterEvdVo.getPageNum(), versionMasterEvdVo.getPageSize());
        Page<PrsModelQual> modelQualPage = prsModelQualMapper.selectPage(pageInfo, new QueryWrapper<PrsModelQual>().lambda().eq(PrsModelQual::getStatus, 1));


        return R.ok(modelQualPage);
    }

    @Override
    public PrsModelQual getByNameAndCode(String qualName, String qualCode) {
        return lambdaQuery().eq(PrsModelQual::getQualName, qualName)
                .eq(PrsModelQual::getQualCode, qualCode)
                .one();
    }

    @Override
    public List<PrsQualDataSelectVO> selectByMasterAndVersion(Integer versionId, String modelCode) {

        return prsModelQualMapper.selectByMasterAndVersion(versionId,modelCode);
    }

    @Override
    public String getByCode(String code) {
        return this.baseMapper.getNameByCode(code);
    }
}
