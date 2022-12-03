package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.EvidenceModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.dto.MiddleDataConfigDto;
import com.deloitte.data.platform.dto.MiddleQualityDto;
import com.deloitte.data.platform.vo.MiddleDataConfigVo;
import com.deloitte.data.platform.vo.MiddleQualityVo;

/**
 * Evidence模型配置  服务类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface IEvidenceModelService extends IService<EvidenceModel> {
    /**
     * 中间层数据字典分页
     * @param dto
     * @return
     */
    IPage<MiddleDataConfigVo> getMiddleDataConfigPage(MiddleDataConfigDto dto);

    /**
     * 中间层质检结果
     * @param dto
     * @return
     */
    IPage<MiddleQualityVo> getMiddleQualityPage(MiddleQualityDto dto);
}
