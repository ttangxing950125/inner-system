package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.ModelDataCheck;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.mapper.ModelDataCheckMapper;
import com.deloitte.data.platform.service.IModelDataCheckService;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import org.springframework.stereotype.Service;

/**
 * 数据校验模型表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
@Service
@Master_1
public class ModelDataCheckServiceImpl extends ServiceImpl<ModelDataCheckMapper, ModelDataCheck> implements IModelDataCheckService {

    @Override
    public IPage<ParameterConfigVo.ModelDataCheckListVo> getModelDataCheckListPage(IPage<ParameterConfigVo.ModelDataCheckListVo> page,ParameterConfigDto.modelDataCheckListPageDto dto) {
        return this.baseMapper.getModelDataCheckListPage(page,dto);
    }
}
