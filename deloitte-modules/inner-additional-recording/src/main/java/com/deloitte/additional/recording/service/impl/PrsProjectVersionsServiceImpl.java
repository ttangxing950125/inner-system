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
import com.deloitte.additional.recording.vo.DataListCustomEntityInfoVo;
import com.deloitte.additional.recording.vo.DataListFindPrsProjectVersionsByYearVo;
import com.deloitte.additional.recording.vo.version.PrsProjectVersionSelectVO;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
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

    /**
     * parallelStream 并发流执行 使用forkjoinpool
     * {@link java.util.concurrent.ForkJoinPool}
     * 统计-数据清单模块 查询版本 下拉框专用方法  版本存在重复 使用 distinct去重 后续参数传名称
     *
     * @return
     */
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

    /**
     * 自定义查询-版本敞口
     *
     * @param year
     * @return
     */
    @Override
    public R finPrsProjectVersionsByYear(final Integer[] year) {

        long start = System.currentTimeMillis();
        log.info("==> 通过年份:{}查询指标值 开始 ", year);
        List<DataListFindPrsProjectVersionsByYearVo> dataListFindPrsProjectVersionsByYearVos = prsProjectVersionsMapper.finPrsProjectVersionsByYear(year);
        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.parallelStream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        long end = System.currentTimeMillis();
        log.info("==> 通过年份:{}>>查询指标值 结束 耗时: " + (end - start) + "ms", year);
        return R.ok(collect);
    }

    /**
     * 自定义查询-版本敞口查询公司
     *
     * @param year
     * @param qualCode
     * @param verMasId
     * @return
     */
    @Override
    public R getCustomEntityInfoByVersionIdAndModelId(String year, String qualCode, String verMasId) {
        List<DataListCustomEntityInfoVo> resultList = prsProjectVersionsMapper.getCustomEntityInfoByVersionIdAndModelId(year, qualCode, verMasId);
        if (CollUtil.isEmpty(resultList)) {
            R.ok();
        }
        return R.ok(resultList);
    }

    @Override
    public List<PrsProjectVersionSelectVO> selectList(String userYear) {

        List<PrsProjectVersions> list = lambdaQuery().eq(StrUtil.isNotBlank(userYear), PrsProjectVersions::getTimeValue, userYear)
                .list();
        if (null != list) {
            return BeanUtils.copy(list, PrsProjectVersionSelectVO.class);

        }
        return null;
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
