package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.BaseOrm;
import com.deloitte.data.platform.mapper.BaseOrmMapper;
import com.deloitte.data.platform.service.IBaseOrmService;
import org.springframework.stereotype.Service;

/**
 * 德勤对象关系映射表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Service
@Master_1
public class BaseOrmServiceImpl extends ServiceImpl<BaseOrmMapper, BaseOrm> implements IBaseOrmService {

}
