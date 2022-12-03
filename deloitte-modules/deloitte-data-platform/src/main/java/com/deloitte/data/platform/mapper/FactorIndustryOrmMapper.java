package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.data.platform.domian.FactorIndustryOrm;
import com.deloitte.data.platform.vo.FactorOrmVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 指标业务场景、业务线、敞口信息
 *
 * @author XY
 * @date 2022/11/23
 */
public interface FactorIndustryOrmMapper extends BaseMapper<FactorIndustryOrm> {

    List<FactorOrmVo> list(@Param("factorCodeList") List<String> factorCodeList, @Param("industryWhitewashList") List<String> industryWhitewashList);

}
