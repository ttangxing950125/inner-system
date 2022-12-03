package com.deloitte.additional.recording.service.biz;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.config.ExecutorPoolConfig;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.dto.GetMetaEvdDatasDto;
import com.deloitte.additional.recording.dto.GetMetaQualDatasDto;
import com.deloitte.additional.recording.dto.GetTaskTargetvalPageListDto;
import com.deloitte.additional.recording.mapper.EntityInfoMapper;
import com.deloitte.additional.recording.mapper.MetaEvdDataMapper;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import com.deloitte.additional.recording.vo.QualInfoByVersionIdAndModeCodeVo;
import com.deloitte.common.core.exception.ServiceException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.filter.impl.Op;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/07/18:22
 * @Description:
 */
@Component
@Slf4j
public class DataListBizComponentService {

    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;
    @Resource
    private PrsModelMasterService prsModelMasterService;
    @Resource
    private SysDictDataService sysDictDataService;
    @Resource
    private PrsModelQualService prsModelQualService;
    @Resource
    private EntityInfoService entityInfoService;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Autowired
    private PrsQualDataService prsQualDataService;
    @Resource
    private BasEvdInfoService basEvdInfoService;
    @Resource
    private MetaEvdDataService metaEvdDataService;

    @Resource
    private MetaQualDataService metaQualDataService;
    /**
     * {@link com.deloitte.additional.recording.config.ExecutorPoolConfig#commonTaskExecutor()}
     */
    @Resource(name = "commonTaskExecutor")
    private ThreadPoolTaskExecutor commonTaskExecutor;
    /**
     * 分页查询获取下拉列表
     *
     * @param dto
     * @return {@link  IPage<EntityInfo>}
     * 代码后期优化
     * @see prsModelQualService.queryByPageStatsdetailNoSql
     * @see EntityInfoMapper#queryByPage(Page, Integer, String, String)
     */
    public IPage<EntityInfo> queryByPage(GetTaskTargetvalPageListDto dto) {

        final CompletableFuture<Map<String, Object>> taskOne = CompletableFuture.supplyAsync(() -> {
            Map<String, Object> taskOneMap = new HashMap<>();
            /**
             * 版本、敞口 、年份 、获取 指标数据
             * {@link com.deloitte.additional.recording.service.impl.PrsModelQualServiceImpl#queryByPageStatsdetailNoSql(String, String, Integer)}
             */
            List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql(dto.getModelCode(), dto.getYear(), dto.getId());
            //获取全部指标qual_code
            List<String> qualCodeLists = dataListPageTataiVos.stream().map(DataListPageTataiVo::getQualCode).collect(Collectors.toList());
            taskOneMap.put("qualCodeLists", qualCodeLists);
            taskOneMap.put("dataListPageTataiVos", dataListPageTataiVos);
            return taskOneMap;
        }, commonTaskExecutor);

        final  CompletableFuture<IPage<EntityInfo>> entityInfoIPageTask = CompletableFuture.supplyAsync(() -> {
            /**
             * 版本、敞口 、指标获取主体 信息 分页维度以 主体entity_info
             * <code>
             *      SELECT
             *      	   ent.*
             *      FROM
             * 	         prs_ver_mas_entity rel
             * 	    INNER JOIN
             * 	        entity_info ent ON rel.entity_code = ent.entity_code
             *      WHERE
             * 	        rel.ver_mas_id = (( SELECT id FROM prs_version_master WHERE prj_id = (( SELECT id FROM prs_project_versions WHERE id = 146 )) AND model_code = "M_002" ))
             *      AND
             *         entity_name like "%上海%"
             * </code>
             */
            Page<EntityInfo> page = new Page<>(dto.getPageNum(), dto.getPagesize());
            IPage<EntityInfo> entityInfoIPage = entityInfoMapper.queryByPage(page, dto.getId(), dto.getEntityName(), dto.getModelCode());
            return entityInfoIPage;
        }, commonTaskExecutor);
        //合并 两个Task的结果集 taskOne 和 entityInfoIPageTask
        final IPage<EntityInfo> pageResult = taskOne.thenCombine(entityInfoIPageTask, (e1, e2) -> {
            List<EntityInfo> records = e2.getRecords();
            List<EntityInfo> records2 = new ArrayList<>();
            List<String> qualCodeLists = (List<String>) e1.get("qualCodeLists");
            for (EntityInfo record : records) {
                //查询指标值 in
                List<PrsQualData> prsQualData = prsQualDataService.getBaseMapper().selectList(new LambdaQueryWrapper<PrsQualData>()
                        .eq(PrsQualData::getEntityCode, record.getEntityCode())
                        .in(PrsQualData::getQualCode, qualCodeLists));
                if (CollUtil.isNotEmpty(prsQualData)) {
                    List list = new ArrayList();
                    List<String> qualCodeListll = new ArrayList<>();
                    for (PrsQualData prsQualDatum : prsQualData) {
                        final PrsModelQual prsModelQual = prsModelQualService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, prsQualDatum.getQualCode()));
                        Map<String, Object> mmpp = new HashMap<>();
                        Map<String, Object> qualCodeHashMap = new HashMap<>();
                        mmpp.put(prsModelQual.getId() + "_id", Optional.ofNullable(prsQualDatum.getQualValue()).orElse("N/A"));
                        qualCodeListll.add(prsModelQual.getQualCode());
                        list.add(mmpp);
                        record.setMaps(list);
                    }
                    log.info("获取的指标信息:>>>:{}", qualCodeLists);
                    qualCodeLists.removeAll(qualCodeListll);
                    if (CollUtil.isNotEmpty(qualCodeLists)) {
                        for (String qualCode : qualCodeLists) {
                            final PrsModelQual prsModelQual = prsModelQualService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, qualCode));
                            Map<String, Object> otherMap = new HashMap<>();
                            otherMap.put(prsModelQual.getId() + "_id", "N/A");
                            record.getMaps().add(otherMap);
                        }
                        qualCodeLists = (List<String>) e1.get("qualCodeLists");
                    }
                } else {
                    ArrayList<Map<String, Object>> otherMapList = new ArrayList<>();
                    for (String qualCode : (List<String>) e1.get("qualCodeLists")) {
                        PrsModelQual prsModelQual = prsModelQualService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, qualCode));
                        Map<String, Object> otherMap = new HashMap<>();
                        otherMap.put(prsModelQual.getId() + "_id", "N/A");
                        otherMapList.add(otherMap);
                    }
                    record.setMaps(otherMapList);
                }
                records2.add(record);
            }
            e2.setRecords(records2);
            return e2;
        }).exceptionally((ex) -> {
            log.info("分页查询获取下拉列表 出现异常:[{}]", ex);
            throw new ServiceException("分页查询获取下拉列表");
        }).join();
        return pageResult;
    }

    /***
     * 获取指标头  版本敞口 年份获取指标
     * @param modelCode
     * @param timeValue
     * @param name
     * @return
     */
    public Object queryByPageStatsdetail(String modelCode, String timeValue, Integer id) {
        List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql(modelCode, timeValue, id);
        return dataListPageTataiVos;
    }

    /**
     * 自定义查询 获取下一步
     *
     * @param year      年份
     * @param versionId 版本Id
     * @param modelCode 敞口Code
     * @return
     */
    public List<QualInfoByVersionIdAndModeCodeVo> getCustomQualByVersionIdAndModelCodeNotPage(String year, String versionId, String modelCode) {
        log.info("=> 自定义查询 获取下一步开始 版本Id={},年份={},敞口编码={}", versionId, year, modelCode);
        PrsProjectVersions prsProjectVersions = Optional.ofNullable(prsProjectVersionsService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsProjectVersions>().eq(PrsProjectVersions::getId, versionId).eq(PrsProjectVersions::getStatus, 1))).orElseThrow(() -> new ServiceException("版本ID为" + versionId + "数据不存在或已经删除"));
        PrsModelMaster prsModelMaster = Optional.ofNullable(prsModelMasterService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelMaster>().eq(PrsModelMaster::getModelCode, modelCode).eq(PrsModelMaster::getStatus, 1))).orElseThrow(() -> new ServiceException("敞口编码为:" + modelCode + "删除或已不存在"));
        /**
         * SELECT
         *      a.*
         * FROM
         *      prs_ver_mas_qual  qual INNER JOIN prs_model_qual  a
         *      ON a.qual_code=qual.qual_code
         * WHERE
         *      qual.ver_mas_id = (( SELECT id FROM prs_version_master WHERE prj_id = (( SELECT id FROM prs_project_versions WHERE id = 146 )) AND model_code = "M_002" ))
         */
        List<QualInfoByVersionIdAndModeCodeVo> qualInfoByVersionIdAndModeCodeVoList = new ArrayList<>();
        List<PrsModelQual> prsModelQualList = prsModelQualService.getPrsModelQualByVersionIdAndModelCode(versionId, modelCode);
        if (CollUtil.isEmpty(prsModelQualList)) {
            log.warn("=> 自定义查询 获取下一步结束 为空!!!!!");
            return qualInfoByVersionIdAndModeCodeVoList;
        }
        qualInfoByVersionIdAndModeCodeVoList = prsModelQualList.stream().map(e -> QualInfoByVersionIdAndModeCodeVo.builder().qualCode(e.getQualCode()).qualName(e.getQualName()).qualId(e.getId()).lable(year + "#" + prsProjectVersions.getName() + "#" + prsModelMaster.getName() + "#" + e.getQualCode()).build()).collect(Collectors.toList());
        log.info("=> 自定义查询 获取下一步结束 版本Id={},年份={},敞口编码={},>>结果集合:{},", versionId, year, modelCode, JSON.toJSONString(prsModelQualList));
        return qualInfoByVersionIdAndModeCodeVoList;


    }

    /**
     * 获取Evdence数据信息 分页查询维度已 Evdence
     * {@link CompletableFuture}
     *
     * @param requestParam {@link GetMetaEvdDatasDto }
     * @return
     * @see CompletableFuture#supplyAsync(Supplier)
     * @see CompletableFuture#thenCombine(CompletionStage, BiFunction)
     * @since 1.8
     */
    public IPage<MetaEvdData> getMetaEvdDatas(GetMetaEvdDatasDto requestParam) {
      /*  //分页查询  版本、敞口、指标
        CompletableFuture<List<DataListPageTataiVo>> taskOne = CompletableFuture.supplyAsync(() -> {
            List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql(requestParam.getModelCode(), requestParam.getYear(), requestParam.getId());
            return dataListPageTataiVos;
        }, commonTaskExecutor);
        //查询Evdence 分页的维度是BasEvdInfo
        CompletableFuture<BasEvdInfo> getBasEvdInfoTask = CompletableFuture.supplyAsync(() -> {
            Page<BasEvdInfo> page = new Page<BasEvdInfo>(requestParam.getPageNum(), requestParam.getPagesize());
            return null;
        }, commonTaskExecutor);
        //合并结果集
        taskOne.thenCombine(getBasEvdInfoTask, (r, y) -> {

            return r;
        }).exceptionally((ex) -> {
            log.info("获取evdence 出现异常:[{}]", ex);
            throw new ServiceException("获取evdencel列表异常");
        }).join();
        return null;*/
        Page<MetaEvdData> page = new Page<MetaEvdData>(requestParam.getPageNum(), requestParam.getPagesize());
        IPage<MetaEvdData> list = metaEvdDataService.getMetaEvdDatasByPage(page, requestParam);
        return list;
    }

    /**
     * 获取指标数据
     * @param getMetaQualDatasDto
     * @return
     */
    public Object getMetaQualDatas(GetMetaQualDatasDto param) {
        Page<MetaQualData> page = new Page<MetaQualData>(param.getPageNum(), param.getPagesize());
        IPage<MetaQualData> listPage = metaQualDataService.getMetaQualDatasByPage(page, param);
        return listPage;
    }
}
