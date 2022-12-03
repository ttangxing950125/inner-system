package com.deloitte.data.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.DataUpdateLog;
import com.deloitte.data.platform.mapper.DataUpdateLogMapper;
import com.deloitte.data.platform.service.IDataUpdateLogService;
import com.deloitte.data.platform.vo.DataUpdateInfoVo;
import org.springframework.stereotype.Service;

/**
 * 基础数据更新日志  服务实现类
 *
 * @author fangliu
 * @date 2022/11/15 11:40:39
 */
@Service
@Master_1
public class DataUpdateLogServiceImpl extends ServiceImpl<DataUpdateLogMapper, DataUpdateLog> implements IDataUpdateLogService {

    @Override
    public DataUpdateInfoVo getBaseDataConfigUpdateInfo() {
        LambdaQueryWrapper<DataUpdateLog> wrapper = Wrappers.lambdaQuery(DataUpdateLog.class);
        wrapper.orderByDesc(DataUpdateLog::getCreatedTime);
        wrapper.last("limit 1");
        DataUpdateLog dataUpdateLog = this.baseMapper.selectOne(wrapper);
        DataUpdateInfoVo dataUpdateInfoVo = BeanUtil.copyProperties(dataUpdateLog, DataUpdateInfoVo.class);
        dataUpdateInfoVo.setUpdateFrequency(dataUpdateLog.getUpdateFrequency());
        return dataUpdateInfoVo;
    }
}
