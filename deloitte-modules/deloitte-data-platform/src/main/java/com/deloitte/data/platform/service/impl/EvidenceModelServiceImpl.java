package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.datasource.annotation.Master_3;
import com.deloitte.data.platform.domian.EvidenceModel;
import com.deloitte.data.platform.dto.MiddleDataConfigDto;
import com.deloitte.data.platform.dto.MiddleQualityDto;
import com.deloitte.data.platform.enums.UnitEnum;
import com.deloitte.data.platform.enums.WhetherEnum;
import com.deloitte.data.platform.mapper.EvidenceModelMapper;
import com.deloitte.data.platform.service.IEvidenceModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.data.platform.vo.MiddleDataConfigVo;
import com.deloitte.data.platform.vo.MiddleQualityVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Evidence模型配置  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Service
@Master_3
public class EvidenceModelServiceImpl extends ServiceImpl<EvidenceModelMapper, EvidenceModel> implements IEvidenceModelService {

    @Override
    public IPage<MiddleDataConfigVo> getMiddleDataConfigPage(MiddleDataConfigDto dto) {
        IPage<MiddleDataConfigVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<MiddleDataConfigVo> result = this.baseMapper.getMiddleDataConfigPage(page, dto);
        List<MiddleDataConfigVo> records = result.getRecords();
        for (MiddleDataConfigVo record : records) {
            if (record.getAccuracy()!=null){
                record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
            }
            record.setUnit(UnitEnum.getUnitDesc(record.getUnit()));
        }
        return result;
    }

    @Override
    public IPage<MiddleQualityVo> getMiddleQualityPage(MiddleQualityDto dto) {
        IPage<MiddleQualityVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        if (years!=null){
            List<String> newYears =  new ArrayList<>();
            for (String year : years) {
                newYears.add(year+"-12-31");
            }
            dto.setYears(newYears);
        }
        IPage<MiddleQualityVo> result = this.baseMapper.getMiddleQualityPage(page, dto);
        List<MiddleQualityVo> records = result.getRecords();
        for (MiddleQualityVo record : records) {
            if (record.getAccuracy()!=null){
                record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
            }
            record.setIsSystemInspection(WhetherEnum.getDesc(record.getIsSystemInspection()));
        }
        return result;
    }
}
