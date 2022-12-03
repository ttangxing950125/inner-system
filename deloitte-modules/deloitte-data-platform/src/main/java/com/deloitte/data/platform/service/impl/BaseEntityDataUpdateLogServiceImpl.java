package com.deloitte.data.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.BaseEntityDataUpdateLog;
import com.deloitte.data.platform.mapper.BaseEntityDataUpdateLogMapper;
import com.deloitte.data.platform.service.IBaseEntityDataUpdateLogService;
import com.deloitte.data.platform.vo.DataUpdateInfoVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础主体数据更新日志  服务实现类
 * </p>
 *
 * @author fangliu
 * @date 2022/11/15 11:40:39
 */
@Service
@Master_1
public class BaseEntityDataUpdateLogServiceImpl extends ServiceImpl<BaseEntityDataUpdateLogMapper, BaseEntityDataUpdateLog> implements IBaseEntityDataUpdateLogService {
    @Override
    public DataUpdateInfoVo getBaseDataConfigUpdateInfo() {
        LambdaQueryWrapper<BaseEntityDataUpdateLog> wrapper = Wrappers.lambdaQuery(BaseEntityDataUpdateLog.class);
        wrapper.orderByDesc(BaseEntityDataUpdateLog::getCreatedTime);
        wrapper.last("limit 1");
        BaseEntityDataUpdateLog baseEntityDataUpdateLog = this.baseMapper.selectOne(wrapper);
        return BeanUtil.copyProperties(baseEntityDataUpdateLog,DataUpdateInfoVo.class);
    }
}
