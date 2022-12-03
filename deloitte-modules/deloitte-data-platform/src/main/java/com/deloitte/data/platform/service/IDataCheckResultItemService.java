package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.DataCheckResultItem;
import com.deloitte.data.platform.dto.HookArticulationDetailDto;
import com.deloitte.data.platform.vo.HookArticulationDetailVo;

/**
 * 数据校验结果表  服务类
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
public interface IDataCheckResultItemService extends IService<DataCheckResultItem> {
    /**
     * 勾稽关系详情
     * @param dto
     * @return
     */
    HookArticulationDetailVo getHookArticulationDetailPage(HookArticulationDetailDto dto);
}
