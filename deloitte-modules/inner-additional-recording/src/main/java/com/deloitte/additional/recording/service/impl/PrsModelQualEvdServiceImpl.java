package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.domain.PrsModelQualEvd;
import com.deloitte.additional.recording.domain.PrsModelQualFactor;
import com.deloitte.additional.recording.mapper.PrsModelQualEvdMapper;
import com.deloitte.additional.recording.mapper.PrsModelQualFactorMapper;
import com.deloitte.additional.recording.mapper.PrsModelQualMapper;
import com.deloitte.additional.recording.service.PrsModelQualEvdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (PrsModelQualEvd)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsModelQualEvdService")
public class PrsModelQualEvdServiceImpl extends ServiceImpl<PrsModelQualEvdMapper, PrsModelQualEvd> implements PrsModelQualEvdService {
    @Resource
    private PrsModelQualEvdMapper evdMapper;
    @Resource
    private PrsModelQualFactorMapper factorMapper;
    @Resource
    private PrsModelQualMapper qualMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setValue() {
        List<PrsModelQualEvd> prsModelQualEvds = evdMapper.selectList(new QueryWrapper<>());
        List<PrsModelQualFactor> prsModelQualFactors = factorMapper.selectList(new QueryWrapper<>());
        List<PrsModelQual> prsModelQuals = qualMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(prsModelQuals)){
            return;
        }
        Map<Integer, List<PrsModelQual>> collect =
                prsModelQuals.stream().collect(Collectors.groupingBy(PrsModelQual::getId));
        prsModelQualEvds.forEach(o->{
            String qualCode = o.getQualCode();
            if (ObjectUtils.isEmpty(qualCode)){
                return;
            }
            List<PrsModelQual> quals = collect.get(Integer.valueOf(qualCode));
            if (CollectionUtils.isEmpty(quals)){
                return;
            }
            String code = quals.get(0).getQualCode();
            PrsModelQualEvd info=new PrsModelQualEvd();
            info.setId(o.getId()).setQualCode(code);
            evdMapper.updateById(info);
        });

        prsModelQualFactors.forEach(o->{
            String qualCode = o.getQualCode();
            if (ObjectUtils.isEmpty(qualCode)){
                return;
            }
            List<PrsModelQual> quals = collect.get(Integer.valueOf(qualCode));
            if (CollectionUtils.isEmpty(quals)){
                return;
            }
            String code = quals.get(0).getQualCode();
            PrsModelQualFactor info=new PrsModelQualFactor();
            info.setId(o.getId()).setQualCode(code);
            factorMapper.updateById(info);
        });
    }
}
