package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.base.BaseException;
import com.deloitte.common.datasource.annotation.Master_2;
import com.deloitte.data.platform.domian.ModelRationFactor;
import com.deloitte.data.platform.dto.ApplyDataConfigDto;
import com.deloitte.data.platform.dto.ApplyQualityDto;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.enums.UnitEnum;
import com.deloitte.data.platform.enums.WhetherEnum;
import com.deloitte.data.platform.mapper.ModelRationFactorMapper;
import com.deloitte.data.platform.service.IModelRationFactorService;
import com.deloitte.data.platform.vo.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 定量指标模型  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Service
@Master_2
public class ModelRationFactorServiceImpl extends ServiceImpl<ModelRationFactorMapper, ModelRationFactor> implements IModelRationFactorService {

    @Override
    public Set<String> getFactorCode(String factorName) {
        return this.baseMapper.getFactorCode(factorName);
    }

    @Override
    public IPage<ApplyDataConfigVo> getApplyDataConfigPage(ApplyDataConfigDto dto) {
        IPage<ApplyDataConfigVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ApplyDataConfigVo> result = this.baseMapper.getApplyDataConfigPage(page, dto);
        List<ApplyDataConfigVo> records = result.getRecords();
        for (ApplyDataConfigVo record : records) {
            if(record.getAccuracy()!=null){
                record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
            }
            record.setUnit(UnitEnum.getUnitDesc(record.getUnit()));
        }
        return result;
    }

    @Override
    public IPage<ApplyQualityVo> getApplyQualityPage(ApplyQualityDto dto) {
        IPage<ApplyQualityVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        List<String> years = dto.getYears();
        if (years!=null){
            List<String> newYears =  new ArrayList<>();
            for (String year : years) {
                newYears.add(year+"-12-31");
            }
            dto.setYears(newYears);
        }
        IPage<ApplyQualityVo> result = this.baseMapper.getApplyQualityPage(page, dto);
        List<ApplyQualityVo> records = result.getRecords();
        for (ApplyQualityVo record : records) {
            if(record.getAccuracy()!=null){
                record.setAccuracy(1 / Math.pow(10, record.getAccuracy()));
            }
            record.setIsSystemInspection(WhetherEnum.getDesc(record.getIsSystemInspection()));
        }
        return result;
    }

    @Override
    public Map<String, ApplyDataExtractionVo> getApplyDataExtractionMap(Set<String> codes){
        List<ApplyDataExtractionVo> result = this.baseMapper.getApplyDataExtractions(codes);
        return result.stream().collect(Collectors.toMap(ApplyDataExtractionVo::getCode, Function.identity(), (oldValue, newValue) -> newValue));
    }

    @Override
    public IPage<ModelEvidenceVo.ListVo> getModelRationListPage(IPage<ModelEvidenceVo.ListVo> page, ModelEvidenceDto.ListDto dto) {
        return this.baseMapper.getModelRationListPage(page, dto);
    }

    @Override
    public int deleteById(Integer id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int insert(ModelRationFactor modelRationFactor) {
        return this.baseMapper.insert(modelRationFactor);
    }

    @Override
    public List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto) {
        return this.baseMapper.getTopDataMenu(dto);
    }

    @Override
    public IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page, ParameterConfigDto.ListDto dto) {
        return this.baseMapper.getParameterConfigListPage(page,dto);
    }

    @Override
    public R updateModelRation(ParameterConfigDto.UpdateApplyDto dto) {
        ModelRationFactor modelRationFactor = this.baseMapper.selectById(dto.getId());
        if(modelRationFactor==null){
            throw new BaseException("ID不存在");
        }
        modelRationFactor.setFactorName(dto.getName());
        modelRationFactor.setFactorCode(dto.getCode());
        modelRationFactor.setAccuracy(dto.getAccuracy());
        modelRationFactor.setChangeRateUpper(dto.getChangeRateUpper());
        modelRationFactor.setThresholdValue(dto.getThresholdValue());
        modelRationFactor.setFactorFormula(dto.getFormula());
        modelRationFactor.setAbnormalValueHandle(dto.getAbnormalValueHandle());
        int i = this.baseMapper.updateById(modelRationFactor);
        if(i>0){
            return R.ok();
        }else{
            return R.fail("修改失败");
        }
    }

    @Override
    public R addModelRation(ParameterConfigDto.UpdateApplyDto dto) {
        ModelRationFactor modelRationFactor = new ModelRationFactor();
        modelRationFactor.setFactorName(dto.getName());
        modelRationFactor.setFactorCode(dto.getCode());
        modelRationFactor.setAccuracy(dto.getAccuracy());
        modelRationFactor.setChangeRateUpper(dto.getChangeRateUpper());
        modelRationFactor.setThresholdValue(dto.getThresholdValue());
        modelRationFactor.setFactorFormula(dto.getFormula());
        modelRationFactor.setAbnormalValueHandle(dto.getAbnormalValueHandle());
        int i = this.baseMapper.insert(modelRationFactor);
        if(i>0){
            return R.ok();
        }else{
            return R.fail("新增失败");
        }
    }

    @Override
    public IPage<ModelEvidenceVo.ListVo> getModelEvidenceListPage(IPage<ModelEvidenceVo.ListVo> page, ModelEvidenceDto.ListDto dto) {
        return this.baseMapper.getModelEvidenceListPage(page, dto);
    }
}
