package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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
    private PrsProjectVersionsMapper prsProjectVersionsMapper;

    /**
     * 查询版本配置列表
     *
     * @param year   年份
     * @param status 状态
     * @param param  搜索内容
     * @param pageNum  页码
     * @param pageSize  页面size
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 16:32
     */
    @Override
    public Object getPrsProjectVersions(String year, String status, String param,Integer pageNum,Integer pageSize) {
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum= Common.DEFAUT_PAGE_NUM;
        }
        if (ObjectUtils.isEmpty(pageSize)){
            pageSize= Common.DEFAUT_PAGE_SIZE;
        }
        Page<PrsProjectVersions>pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<PrsProjectVersions> query = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(year)){
            query.lambda().eq(PrsProjectVersions::getTimeValue,year);
        }
        if (!ObjectUtils.isEmpty(status)){
            query.lambda().eq(PrsProjectVersions::getStatus,year);
        }
        if (!ObjectUtils.isEmpty(param)){
            query.lambda().eq(PrsProjectVersions::getName,param);
        }
        return prsProjectVersionsMapper.selectPage(pageInfo,query);
    }
    @Override
    public List<String> findAllPrsProjectVersions() {
        List<PrsProjectVersions> prsProjectVersions = prsProjectVersionsMapper.selectList(new LambdaQueryWrapper<PrsProjectVersions>().eq(PrsProjectVersions::getStatus, 1));
        List<String> collect = prsProjectVersions.parallelStream().filter(e -> StringUtils.isNotEmpty(e.getName())).map(PrsProjectVersions::getName).distinct().collect(Collectors.toList());
        return collect;
    }
}
