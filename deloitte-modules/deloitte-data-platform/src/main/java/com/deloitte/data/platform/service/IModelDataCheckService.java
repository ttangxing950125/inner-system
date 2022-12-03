package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.ModelDataCheck;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.vo.ParameterConfigVo;

/**
 * 数据校验模型表  服务类
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
public interface IModelDataCheckService extends IService<ModelDataCheck> {

    /**
     * 质检规则列表
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ModelDataCheckListVo> getModelDataCheckListPage(IPage<ParameterConfigVo.ModelDataCheckListVo> page,ParameterConfigDto.modelDataCheckListPageDto dto);
}
