package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.CnApprdWaitIssMapper;
import com.deloitte.crm.domain.CnApprdWaitIss;
import com.deloitte.crm.service.CnApprdWaitIssService;
import org.springframework.stereotype.Service;

/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
@Service("cnApprdWaitIssService")
public class CnApprdWaitIssServiceImpl extends ServiceImpl<CnApprdWaitIssMapper, CnApprdWaitIss> implements CnApprdWaitIssService {

}
