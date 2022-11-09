package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.mapper.PrsVersionMasterMapper;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.additional.recording.vo.DataListFindPrsProjectVersionsByYearVo;
import com.deloitte.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (PrsProjectVersions)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Slf4j
@Service("prsProjectVersionsService")
public class PrsProjectVersionsServiceImpl extends ServiceImpl<PrsProjectVersionsMapper, PrsProjectVersions> implements PrsProjectVersionsService {
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;
    @Resource
    private PrsVersionMasterMapper prsVersionMasterMapper;

    /**
     * 查询版本配置列表
     *
     * @param year     年份
     * @param status   状态
     * @param name     版本名称
     * @param pageNum  页码
     * @param pageSize 页面size
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 16:32
     */
    @Override
    public Page<PrsProjectVersions> getPrsProjectVersions(String year, String status, String name, Integer pageNum, Integer pageSize) {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = Common.DEFAUT_PAGE_NUM;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = Common.DEFAUT_PAGE_SIZE;
        }
        Page<PrsProjectVersions> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<PrsProjectVersions> query = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(year)) {
            query.lambda().eq(PrsProjectVersions::getTimeValue, year);
        }
        if (!ObjectUtils.isEmpty(status)) {
            query.lambda().eq(PrsProjectVersions::getStatus, year);
        }
        if (!ObjectUtils.isEmpty(name)) {
            query.lambda().like(PrsProjectVersions::getName, name);
        }
        query.lambda().orderByAsc(PrsProjectVersions::getId);
        return prsProjectVersionsMapper.selectPage(pageInfo, query);
    }

    @Override
    public List<String> findAllPrsProjectVersions() {
        List<PrsProjectVersions> prsProjectVersions = prsProjectVersionsMapper.selectList(new LambdaQueryWrapper<PrsProjectVersions>().eq(PrsProjectVersions::getStatus, 1));
        List<String> collect = prsProjectVersions.parallelStream().filter(e -> StringUtils.isNotEmpty(e.getName())).map(PrsProjectVersions::getName).distinct().collect(Collectors.toList());
        return collect;
    }

    /**
     * 一键禁用
     *
     * @param ids 版本id列表
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:51
     */
    @Override
    public Integer updateStatusToDownByIds(List<Integer> ids) {
        return updateStatusByIds(ids, Common.DELETE);
    }

    /**
     * 一键启用
     *
     * @param ids 版本id列表
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:51
     */
    @Override
    public Integer updateStatusToUpByIds(List<Integer> ids) {
        return updateStatusByIds(ids, Common.NORMAL);
    }

    /**
     * 新增版本
     *
     * @param prsProjectVersions
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 18:00
     */
    @Override
    public R insertPrsProjectVersions(PrsProjectVersions prsProjectVersions) {
        prsProjectVersionsMapper.insert(prsProjectVersions);
        return R.ok(Common.INSERT_SUCCESS);
    }

    @Override
    public R finPrsProjectVersionsByYear(String year) {
        long start = System.currentTimeMillis();
        log.info("==> 通过年份:{}查询指标值 开始 ", year);
        List<DataListFindPrsProjectVersionsByYearVo> dataListFindPrsProjectVersionsByYearVos = prsProjectVersionsMapper.finPrsProjectVersionsByYear(new String[]{year});
        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.parallelStream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        long end = System.currentTimeMillis();
        log.info("==> 通过年份:{}>>查询指标值 结束 耗时: " + (end - start) + "ms", year);
        return R.ok(collect);
    }

    /**
     * 批量修改版本状态
     *
     * @param ids
     * @param status
     * @return Integer
     * @author 冉浩岑
     * @date 2022/11/7 17:58
     */
    private Integer updateStatusByIds(List<Integer> ids, Integer status) {
        PrsProjectVersions prsProjectVersions = new PrsProjectVersions();
        prsProjectVersions.setStatus(status);
        return prsProjectVersionsMapper.update(prsProjectVersions, new QueryWrapper<PrsProjectVersions>().lambda().in(PrsProjectVersions::getId, ids));
    }

}
