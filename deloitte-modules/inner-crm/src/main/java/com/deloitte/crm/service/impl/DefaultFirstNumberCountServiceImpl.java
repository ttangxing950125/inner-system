package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.DefaultFirstNumberCountMapper;
import com.deloitte.crm.domain.DefaultFirstNumberCount;
import com.deloitte.crm.service.DefaultFirstNumberCountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (DefaultFirstNumberCount)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@Service("defaultFirstNumberCountService")
public class DefaultFirstNumberCountServiceImpl extends ServiceImpl<DefaultFirstNumberCountMapper, DefaultFirstNumberCount> implements DefaultFirstNumberCountService {

    @Override
    public Object doTask(CrmWindTask windTask, List<DefaultFirstNumberCount> delIsses) {
        return null;
    }
}
