package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.PrsModelQualMapper;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.common.core.domain.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (PrsModelQual)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsModelQualService")
public class PrsModelQualServiceImpl extends ServiceImpl<PrsModelQualMapper, PrsModelQual> implements PrsModelQualService {
    @Resource
    private PrsModelQualMapper prsModelQualMapper;

    @Override
    public List<DataListPageTataiVo> queryByPageStatsdetail(String modelCode, String timeValue, String name) {
        List<DataListPageTataiVo> dataListPageTataiVoLists = prsModelQualMapper.queryByPageStatsdetail(modelCode, timeValue, name);
        if (CollUtil.isEmpty(dataListPageTataiVoLists)) {
            return dataListPageTataiVoLists;
        }
        List<DataListPageTataiVo> collect = dataListPageTataiVoLists.stream().map(e -> {
            String qualCode = e.getQualCode();
            e.setQualName(e.getQualName() + "-" + qualCode);
            return e;
        }).collect(Collectors.toList());
        return collect;
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
        Page<PrsModelQual>pageInfo=new Page<>(versionMasterEvdVo.getPageNum(),versionMasterEvdVo.getPageSize());
        Page<PrsModelQual> modelQualPage = prsModelQualMapper.selectPage(pageInfo, new QueryWrapper<>());
        return R.ok(modelQualPage);
    }
}
