package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.CnIpoPauseMapper;
import com.deloitte.crm.domain.CnIpoPause;
import com.deloitte.crm.service.CnIpoPauseService;
import org.springframework.stereotype.Service;

/**
 * IPO-发行暂缓(CnIpoPause)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:53
 */
@Service("cnIpoPauseService")
public class CnIpoPauseServiceImpl extends ServiceImpl<CnIpoPauseMapper, CnIpoPause> implements CnIpoPauseService {

}
