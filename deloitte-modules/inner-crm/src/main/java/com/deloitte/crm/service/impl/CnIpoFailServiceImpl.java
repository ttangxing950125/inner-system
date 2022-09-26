package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.CnIpoFailMapper;
import com.deloitte.crm.domain.CnIpoFail;
import com.deloitte.crm.service.CnIpoFailService;
import org.springframework.stereotype.Service;

/**
 * IPO-发行失败(CnIpoFail)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:49
 */
@Service("cnIpoFailService")
public class CnIpoFailServiceImpl extends ServiceImpl<CnIpoFailMapper, CnIpoFail> implements CnIpoFailService {

}
