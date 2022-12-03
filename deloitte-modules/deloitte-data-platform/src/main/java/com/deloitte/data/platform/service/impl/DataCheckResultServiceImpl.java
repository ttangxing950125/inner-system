package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.DataCheckResult;
import com.deloitte.data.platform.dto.HookArticulationDto;
import com.deloitte.data.platform.mapper.DataCheckResultMapper;
import com.deloitte.data.platform.service.IDataCheckResultService;
import com.deloitte.data.platform.enums.WhetherEnum;
import com.deloitte.data.platform.vo.HookArticulationVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据校验结果表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
@Service
@Master_1
public class DataCheckResultServiceImpl extends ServiceImpl<DataCheckResultMapper, DataCheckResult> implements IDataCheckResultService {

    @Override
    public IPage<HookArticulationVo> getHookArticulationPage(HookArticulationDto dto) {
        IPage<HookArticulationVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<HookArticulationVo> result = this.baseMapper.getHookArticulationPage(page, dto);
        List<HookArticulationVo> records = result.getRecords();
        for (HookArticulationVo record : records) {
            record.setIsSystemInspection(WhetherEnum.getDesc(record.getIsSystemInspection()));
            record.setIsNeedArtificialInspection(WhetherEnum.getDesc(record.getIsNeedArtificialInspection()));
        }
        return result;
    }
}
