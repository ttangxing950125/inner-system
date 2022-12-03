package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.domain.BasEvdValrange;
import com.deloitte.additional.recording.mapper.BasEvdInfoMapper;
import com.deloitte.additional.recording.mapper.BasEvdValrangeMapper;
import com.deloitte.additional.recording.service.BasEvdValrangeService;
import com.deloitte.common.core.domain.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (BasEvdValrange)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("basEvdValrangeService")
public class BasEvdValrangeServiceImpl extends ServiceImpl<BasEvdValrangeMapper, BasEvdValrange> implements BasEvdValrangeService {

    @Resource
    private BasEvdValrangeMapper basEvdValrangeMapper;

    @Resource
    private BasEvdInfoMapper basEvdInfoMapper;
    @Override
    public R getAllEvdDataDict() {
        return R.ok(basEvdValrangeMapper.selectList(new QueryWrapper<>()));
    }
    /**
     * evdCode赋值
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/16 9:22
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R setBasEvdValrange() {
        List<BasEvdValrange> basEvdValranges = basEvdValrangeMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isNotEmpty(basEvdValranges)){
            basEvdValranges.forEach(o->{
                String evdCode = o.getEvdCode();
                if (ObjectUtils.isNotEmpty(evdCode)){
                    BasEvdInfo basEvdInfo = basEvdInfoMapper.selectById(Integer.valueOf(evdCode));
                    if (ObjectUtils.isNotEmpty(basEvdInfo)){
                        BasEvdValrange basEvdValrange = new BasEvdValrange();
                        basEvdValrange.setEvdCode(basEvdInfo.getCode()).setId(o.getId());
                        basEvdValrangeMapper.updateById(basEvdValrange);
                    }
                }
            });
        }
        return null;
    }
}
