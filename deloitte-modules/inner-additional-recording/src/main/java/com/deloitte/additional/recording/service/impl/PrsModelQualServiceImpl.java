package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.*;
import com.deloitte.additional.recording.vo.qual.PrsQualDataSelectVO;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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
    @Resource
    private PrsModelQualEvdMapper qualEvdMapper;
    @Resource
    private BasEvdInfoMapper basEvdInfoMapper;
    @Resource
    private PrsModelQualFactorMapper qualFactorMapper;
    @Resource
    private PrsQualDataMapper qualDataMapper;
    /**
     * 获取表头 根据 版本、敞口、年份 获取 指标
     *
     * @param modelCode
     * @param timeValue
     * @param Id
     * <P> use queryByPageStatsdetailNoSql</P>
     * @see PrsModelQualServiceImpl#queryByPageStatsdetailNoSql
     */
    @Deprecated
    @Override
    public List<DataListPageTataiVo> queryByPageStatsdetail(String modelCode, String timeValue, Integer Id) {
        log.info("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 开始 ", Id, modelCode, timeValue);
        List<DataListPageTataiVo> dataListPageTataiVoLists = prsModelQualMapper.queryByPageStatsdetail(modelCode, timeValue, Id);
        if (CollUtil.isEmpty(dataListPageTataiVoLists)) {
            log.warn("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 介绍 查询数据为空! ", Id, modelCode, timeValue);
            return dataListPageTataiVoLists;
        }
        List<DataListPageTataiVo> collect = dataListPageTataiVoLists.stream().map(e -> {
            String qualCode = e.getQualCode();
            e.setQualName(e.getQualName() + "-" + qualCode);
            return e;
        }).collect(Collectors.toList());
        log.warn("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 结束<<<:{} ", Id, modelCode, timeValue, JSON.toJSONString(collect));


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
    public List<DataListPageTataiVo> queryByPageStatsdetailNoSql(String modelCode, String timeValue, Integer name) {
        final ArrayList<DataListPageTataiVo> dataListPageTataiVos = new ArrayList<>();
        final ArrayList<String> notData = new ArrayList<>();
        log.info("==> 根据 版本:{}:、敞口:{}、年份:{} 获取 指标信息 开始 ", name, modelCode, timeValue);
        //1.根据版本名称和年份查询版本表 确定唯一条记录
        PrsProjectVersions prsProjectVersions = prsProjectVersionsMapper.selectOne(new LambdaQueryWrapper<PrsProjectVersions>().eq(PrsProjectVersions::getId, name).eq(PrsProjectVersions::getTimeValue, timeValue).eq(PrsProjectVersions::getStatus, 1));
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
                dataListPageTataiVos.add(DataListPageTataiVo.builder().id(prsModelQual.getId()).description(prsModelQual.getDescription()).qualCode(prsModelQual.getQualCode()).qualName(prsModelQual.getQualName() + "-" + prsModelQual.getQualCode()).build());
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

    /**
     * 分页查询全部指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:56
     */
    @Override
    public R paging(VersionMasterEvdVo versionMasterEvdVo) {
        String searchData = versionMasterEvdVo.getSearchData();
        Integer status = versionMasterEvdVo.getStatus();

        Page<PrsModelQual> pageInfo = new Page<>(versionMasterEvdVo.getPageNum(), versionMasterEvdVo.getPageSize());
        QueryWrapper<PrsModelQual> query = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(status)){
            query.lambda().eq(PrsModelQual::getStatus, status);
        }
        if (!ObjectUtils.isEmpty(searchData)){
            query.and(wapper->wapper.lambda().like(PrsModelQual::getQualName, searchData).or().like(PrsModelQual::getQualCode, searchData));
        }
        Page<PrsModelQual> modelQualPage = prsModelQualMapper.selectPage(pageInfo, query);
        return R.ok(modelQualPage);
    }
    /**
     * 根据指标Code查询指标信息
     *
     * @param qualId
     * @return R
     * @author 冉浩岑
     * @date 2022/11/11 16:58
     */
    @Override
    public R getQualDetal(Integer qualId) {

        QualInfoBackVo qualInfoBackVo = new QualInfoBackVo();

        //获取基本信息
        PrsModelQual prsModelQual = prsModelQualMapper.selectById(qualId);
        qualInfoBackVo.setPrsModelQual(prsModelQual);

        //获取Evd信息
        List<PrsModelQualEvd> prsModelQualEvds = qualEvdMapper.selectList(new QueryWrapper<PrsModelQualEvd>().lambda().eq(PrsModelQualEvd::getQualCode, prsModelQual.getQualCode()));
        List<QualEvdVo>evdVoList=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prsModelQualEvds)){
            prsModelQualEvds.forEach(o->{
                BasEvdInfo basEvdInfo = basEvdInfoMapper.selectOne(new QueryWrapper<BasEvdInfo>().lambda().eq(BasEvdInfo::getCode, o.getEvdCode()).last(Common.SQL_LIMIT_ONE));
                QualEvdVo evdVo=new QualEvdVo();
                evdVo.setCode(o.getQualCode());
                if (!ObjectUtils.isEmpty(basEvdInfo)){
                    evdVo.setName(basEvdInfo.getName()).setDispType(basEvdInfo.getDispType()).setDisp_type(basEvdInfo.getDispType());
                }
                evdVoList.add(evdVo);
            });
        }
        qualInfoBackVo.setEvdInfos(evdVoList);

        //获取挡位信息
        List<PrsModelQualFactor> prsModelQualFactors = qualFactorMapper.selectList(new QueryWrapper<PrsModelQualFactor>().lambda().eq(PrsModelQualFactor::getQualCode, prsModelQual.getQualCode()));
        List<QualFactorVo>factorVoList=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prsModelQualFactors)){
            prsModelQualFactors.forEach(o->{
                QualFactorVo factorVo=new QualFactorVo();
                factorVo.setFactorItem(o.getFactorItem()).setFactorValue(o.getFactorValue()).setId(o.getId());
            });
        }
        qualInfoBackVo.setFactors(factorVoList);
        return R.ok(qualInfoBackVo);
    }
    /**
     * 新增指标
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/11 17:05
     */
    @Override
    public R addQual(InsertQualVo insertQualVo) {
        List<Integer> evdIds = insertQualVo.getEvdIds();
        List<String> factorItems = insertQualVo.getFactorItems();


        PrsModelQual prsModelQual = insertQualVo.havePrsModelQual();
        PrsModelQual lastOne = prsModelQualMapper.selectOne(new QueryWrapper<PrsModelQual>().lambda().orderByDesc(PrsModelQual::getId).last(Common.SQL_LIMIT_ONE));
        Integer lastId = lastOne.getId() + 1;
        String qualCode = createQualCode(lastId);
        prsModelQual.setQualCode(qualCode);
        //插入指标表
        prsModelQualMapper.insert(prsModelQual);
        //插入指标evd表
        if (CollectionUtils.isNotEmpty(evdIds)){
            evdIds.forEach(o->{
                BasEvdInfo basEvdInfo = basEvdInfoMapper.selectById(o);
                PrsModelQualEvd prsModelQualEvd=new PrsModelQualEvd();
                prsModelQualEvd.setQualCode(qualCode).setEvdCode(basEvdInfo.getCode());
                qualEvdMapper.insert(prsModelQualEvd);
            });
        }
        //插入指标挡位表
        if (CollectionUtils.isNotEmpty(factorItems)){
            AtomicReference<Integer> value= new AtomicReference<>(1);
            factorItems.forEach(o->{
                PrsModelQualFactor prsModelQualFactor=new PrsModelQualFactor();
                prsModelQualFactor.setQualCode(qualCode).setFactorValue(value.get().toString()).setFactorItem(o);
                qualFactorMapper.insert(prsModelQualFactor);
                value.getAndSet(value.get() + 1);
            });
        }
        return R.ok(Common.INSERT_SUCCESS);
    }

    @Override
    public R disableQual(List<Integer> qualIds) {
        return updateQualStatusByList(qualIds,0,false);
    }

    @Override
    public R openQual(List<Integer> qualIds) {
        return updateQualStatusByList(qualIds,1,true);
    }
    /**
     * 修改指标
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/11 17:05
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateQualFactor(QualInfoBackVo qualInfoBackVo) {
        PrsModelQual prsModelQual = qualInfoBackVo.getPrsModelQual();
        prsModelQualMapper.updateById(prsModelQual);
        List<QualEvdVo> evdInfos = qualInfoBackVo.getEvdInfos();
        qualEvdMapper.delete(new QueryWrapper<PrsModelQualEvd>().lambda().eq(PrsModelQualEvd::getQualCode,prsModelQual.getQualCode()));
        if (CollectionUtils.isNotEmpty(evdInfos)){
            evdInfos.forEach(o->{
                PrsModelQualEvd prsModelQualEvd=new PrsModelQualEvd();
                prsModelQualEvd.setQualCode(prsModelQual.getQualCode()).setEvdCode(o.getCode());
                qualEvdMapper.insert(prsModelQualEvd);
            });
        }
        List<QualFactorVo> factors = qualInfoBackVo.getFactors();
        qualFactorMapper.delete(new QueryWrapper<PrsModelQualFactor>().lambda().eq(PrsModelQualFactor::getQualCode,prsModelQual.getQualCode()));
        if (CollectionUtils.isNotEmpty(factors)){
            factors.forEach(o->{
                PrsModelQualFactor prsModelQualFactor=new PrsModelQualFactor();
                prsModelQualFactor.setQualCode(prsModelQual.getQualCode()).setFactorItem(o.getFactorItem()).setFactorValue(o.getFactorValue());
                qualFactorMapper.insert(prsModelQualFactor);
            });
        }
        return R.ok(Common.UPDATE_SUCCESS);
    }

    @Override
    public R getqualrule(String qualcode) {
        PrsModelQual prsModelQual = prsModelQualMapper.selectOne(new QueryWrapper<PrsModelQual>().lambda().eq(PrsModelQual::getQualCode, qualcode).last(Common.SQL_LIMIT_ONE));
        return R.ok(prsModelQual);
    }

    @Override
    public R qualrule(PrsModelQual prsModelQual) {
        prsModelQualMapper.update(prsModelQual,new QueryWrapper<PrsModelQual>().lambda().eq(PrsModelQual::getQualCode,prsModelQual.getQualCode()));
        return R.ok(Common.UPDATE_SUCCESS);
    }

    /**
     *  版本Id和敞口Code
     * @param versionId
     * @param modelCode
     * @return
     */
    @Override
    public List<PrsModelQual> getPrsModelQualByVersionIdAndModelCode(String versionId, String modelCode) {
        return prsModelQualMapper.getPrsModelQualByVersionIdAndModelCode(versionId,modelCode);
    }

    /**
     * 查询所有指标名称和指标code
     * @return
     */
    @Override
    public List<QualNameCodeVo> getAllQualNameCod() {
        LambdaQueryWrapper<PrsModelQual> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PrsModelQual::getId,PrsModelQual::getQualName,PrsModelQual::getQualCode);
        List<PrsModelQual> list = this.list(wrapper);
        List<QualNameCodeVo> qualNameCodeVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(p -> {
                QualNameCodeVo qualNameCodeVo = new QualNameCodeVo();
                BeanUtils.copyProperties(p,qualNameCodeVo);
                qualNameCodeVo.setQualId(p.getId());
                qualNameCodeVos.add(qualNameCodeVo);
            });
        }

        return qualNameCodeVos;
    }

    @Override
    public List<PrsQualDataSelectVO> selectByMasterAndVersion(Integer versionId, String modelCode) {
        return null;
    }

    @Override
    public String getByCode(String code) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public R updateQualStatusByList(List<Integer> qualIds, Integer status,Boolean state) {
        if (CollectionUtils.isEmpty(qualIds)){
            return R.fail(Common.PLEASE_SEND_PARAM);
        }
        QueryWrapper<PrsModelQual> query = new QueryWrapper<>();
        query.lambda().in(PrsModelQual::getId, qualIds);
        //修改原本的指标
        List<PrsModelQual> quals = prsModelQualMapper.selectList(query);
        prsModelQualMapper.update(new PrsModelQual().setStatus(status),query);
        List<String>qualCodes=new ArrayList<>();
        quals.forEach(o->qualCodes.add(o.getQualCode()));
        //修改指标挡位
        qualFactorMapper.update(new PrsModelQualFactor().setStatus(status), new QueryWrapper<PrsModelQualFactor>().lambda().in(PrsModelQualFactor::getQualCode,qualCodes));
       //修改敞口指标
        qualEvdMapper.update(new PrsModelQualEvd().setStatus(state), new QueryWrapper<PrsModelQualEvd>().lambda().in(PrsModelQualEvd::getQualCode,qualCodes));
        return R.ok(Common.UPDATE_SUCCESS);
    }

    /**
     * 生成新的指标Code
     *
     * @param lastId
     * @return String
     * @author 冉浩岑
     * @date 2022/11/11 17:25
    */
    public String createQualCode(Integer lastId){
        String code=Common.QUAL_CODE_FRONT;
//        Q_003605
        int length = lastId.toString().length();
        for (int i=0;i<6-length;i++){
            code=code+0;
        }
        code=code+lastId;
        return code;
    }
}
