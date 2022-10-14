package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.UndoStInfoMapper;
import com.deloitte.crm.domain.UndoStInfo;
import com.deloitte.crm.service.UndoStInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 撤销ST(摘帽)(UndoStInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@Service("undoStInfoService")
public class UndoStInfoServiceImpl extends ServiceImpl<UndoStInfoMapper, UndoStInfo> implements UndoStInfoService {

    @Override
    public Object doTask(CrmWindTask windTask, List<UndoStInfo> delIsses) {
        return null;
    }
}
