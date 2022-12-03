package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.DataCheckResult;
import com.deloitte.data.platform.dto.HookArticulationDto;
import com.deloitte.data.platform.vo.HookArticulationVo;

/**
 * 数据校验结果表  服务类
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
public interface IDataCheckResultService extends IService<DataCheckResult> {
    /**
     * 统计分析 勾稽关系分页查询
     * @param dto
     * @return
     */
    IPage<HookArticulationVo> getHookArticulationPage(HookArticulationDto dto);
}
