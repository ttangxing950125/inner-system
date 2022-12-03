package com.deloitte.data.platform.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_4;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.service.IBaseFinDataService;
import com.deloitte.data.platform.enums.WhetherEnum;
import com.deloitte.data.platform.domian.EntityInfo;
import com.deloitte.data.platform.dto.EntityInfoDto;
import com.deloitte.data.platform.mapper.EntityInfoMapper;
import com.deloitte.data.platform.service.IEntityInfoService;
import com.deloitte.data.platform.vo.EntityInfoVo;
import com.deloitte.data.platform.vo.QualityTestRateVo;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fangliu
 * @date 2022/11/14 11:40:39
 */
@Service
@Master_4
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper, EntityInfo> implements IEntityInfoService {

    @Resource
    IBaseFinDataService iBaseFinDataService;

    @Resource
    EntityInfoMapper entityInfoMapper;

    @Override
    public List<EntityInfo> getEntityInfoByName(String name) {
        if (StringUtils.isNotEmpty(name)){
            LambdaQueryWrapper<EntityInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(EntityInfo::getEntityCode,EntityInfo::getEntityName);
            queryWrapper.likeRight(EntityInfo::getEntityName,name).or().likeRight(EntityInfo::getEntityCode,name);
            return this.list(queryWrapper);
        }
        return null;
    }

    @Override
    public Map<String, EntityInfo> getEntityInfo(Set<String> codes) {
        LambdaQueryWrapper<EntityInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(EntityInfo::getEntityCode,EntityInfo::getEntityName, EntityInfo::getCreditCode);
        queryWrapper.in(CollectionUtil.isNotEmpty(codes),EntityInfo::getEntityCode,codes);
        List<EntityInfo> entityInfos = this.baseMapper.selectList(queryWrapper);
        return entityInfos.stream().collect(Collectors.toMap(EntityInfo::getEntityCode, Function.identity()));
    }

    @Override
    public IPage<EntityInfoVo> getEntityInfoPage(EntityInfoDto dto) {
        IPage<EntityInfoVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<EntityInfoVo> result = this.baseMapper.getEntityInfoPage(page, dto);
        List<EntityInfoVo> records = result.getRecords();
        if (records.size()>0){
            Set<String> entityCodes = records.stream().map(EntityInfoVo::getEntityCode).collect(Collectors.toSet());
            // 根据主体编码计算质检通过比率
            Map<String, QualityTestRateVo> rateMap = iBaseFinDataService.getQualityTestRate(dto,entityCodes);
            for (EntityInfoVo record : records) {
                // 质检通过比率
                QualityTestRateVo qualityTestRateVo = rateMap.get(record.getEntityCode());
                if (qualityTestRateVo!=null){
                    record.setTotalRate(qualityTestRateVo.getTotalRate());
                }
                record.setList(WhetherEnum.getDesc(record.getList()));
                record.setIssueBonds(WhetherEnum.getDesc(record.getIssueBonds()));
            }
        }
        return result;
    }

    @Override
    public IPage<StatisticalDataAnalysisVo.CustomerListVo> customerPage(StatisticalDataAnalysisDto.CustomerListDto dto) {
        IPage<StatisticalDataAnalysisVo.CustomerListVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return entityInfoMapper.getCustomerPage(page, dto);
    }

    @Override
    public List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto) {
        return this.baseMapper.getTopDataMenu(dto);
    }

}
