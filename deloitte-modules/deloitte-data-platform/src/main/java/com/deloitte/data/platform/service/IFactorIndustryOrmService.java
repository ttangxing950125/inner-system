package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.FactorIndustryOrm;
import com.deloitte.data.platform.vo.FactorOrmVo;

import java.util.List;

/**
 * 指标计算
 *
 * @author XY
 * @date 2022/11/18
 */
public interface IFactorIndustryOrmService extends IService<FactorIndustryOrm> {

    List<FactorOrmVo> list(List<String> factorCodeList, List<String> industryWhitewashList);

}
