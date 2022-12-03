package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.DataPlatformConfigDict;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.mapper.DataPlatformConfigDictMapper;
import com.deloitte.data.platform.service.IDataPlatformConfigDictService;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据平台配置字典  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Service
@Master_1
public class DataPlatformConfigDictServiceImpl extends ServiceImpl<DataPlatformConfigDictMapper, DataPlatformConfigDict> implements IDataPlatformConfigDictService {
    @Override
    public String getDataPriorityByParentCode(){
        return this.baseMapper.getDataPriorityByParentCode();
    }

    @Override
    public DataPlatformMenuVo getAllDataMenu() {
        return this.baseMapper.getAllDataMenu();
    }

    @Override
    public List<DataPlatformConfigDict> getReqList() {
        return this.baseMapper.getReqList();
    }

    @Override
    public List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto) {
        return this.baseMapper.getTopDataMenu(dto);
    }

    @Override
    public DataPlatformMenuVo getBaseConfigDictSubordinate(String code) {
        return this.baseMapper.getBaseConfigDictSubordinate(code);
    }

}
