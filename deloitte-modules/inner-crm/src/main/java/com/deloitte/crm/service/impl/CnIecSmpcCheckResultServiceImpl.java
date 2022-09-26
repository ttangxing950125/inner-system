package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.CnIecSmpcCheckResultMapper;
import com.deloitte.crm.domain.CnIecSmpcCheckResult;
import com.deloitte.crm.service.CnIecSmpcCheckResultService;
import org.springframework.stereotype.Service;

/**
 * IPO-发审委上市委审核结果(CnIecSmpcCheckResult)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:48
 */
@Service("cnIecSmpcCheckResultService")
public class CnIecSmpcCheckResultServiceImpl extends ServiceImpl<CnIecSmpcCheckResultMapper, CnIecSmpcCheckResult> implements CnIecSmpcCheckResultService {

}
