package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.base.BaseException;
import com.deloitte.common.datasource.annotation.Master_2;
import com.deloitte.data.platform.domian.FactorIndustryOrm;
import com.deloitte.data.platform.domian.ModelRationFactor;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.enums.UnitEnum;
import com.deloitte.data.platform.enums.WhetherEnum;
import com.deloitte.data.platform.mapper.FactorIndustryOrmMapper;
import com.deloitte.data.platform.mapper.ModelRationFactorMapper;
import com.deloitte.data.platform.service.IFactorIndustryOrmService;
import com.deloitte.data.platform.service.IModelRationFactorService;
import com.deloitte.data.platform.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 指标业务场景、业务线、敞口信息
 *
 * @author XY
 * @date 2022/11/23
 */
@Service
@Master_2
public class FactorIndustryOrmServiceImpl extends ServiceImpl<FactorIndustryOrmMapper, FactorIndustryOrm> implements IFactorIndustryOrmService {

    @Override
    public List<FactorOrmVo> list(List<String> factorCodeList, List<String> industryWhitewashList) {
        List<FactorOrmVo> voList = this.baseMapper.list(factorCodeList, industryWhitewashList);
        if (voList == null) {
            voList = new ArrayList<>();
        }
        return voList;
    }
}
