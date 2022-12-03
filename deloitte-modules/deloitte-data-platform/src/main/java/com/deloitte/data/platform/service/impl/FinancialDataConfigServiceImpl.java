package com.deloitte.data.platform.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.base.BaseException;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.DataPlatformConfigDict;
import com.deloitte.data.platform.domian.FinancialDataConfig;
import com.deloitte.data.platform.dto.BaseDataConfigDto;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.enums.UnitEnum;
import com.deloitte.data.platform.mapper.FinancialDataConfigMapper;
import com.deloitte.data.platform.service.IDataPlatformConfigDictService;
import com.deloitte.data.platform.service.IFinancialDataConfigService;
import com.deloitte.data.platform.vo.BaseDataConfigVo;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 财务数据配置  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Service
@Master_1
public class FinancialDataConfigServiceImpl extends ServiceImpl<FinancialDataConfigMapper, FinancialDataConfig> implements IFinancialDataConfigService {

    @Autowired
    private IDataPlatformConfigDictService dataPlatformConfigDictService;

    @Override
    public IPage<BaseDataConfigVo> getFinancialBaseDataConfigPage(BaseDataConfigDto dto) {
        IPage<BaseDataConfigVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<BaseDataConfigVo> result = this.baseMapper.getFinancialBaseDataConfigPage(page, dto);
        List<BaseDataConfigVo> records = result.getRecords();
        for (BaseDataConfigVo record : records) {
            record.setAccuracy(record.getAccuracy()!=0.0?1 / Math.pow(10, record.getAccuracy()):record.getAccuracy());
            record.setUnit(UnitEnum.getUnitDesc(record.getUnit()));
        }
        return result;
    }

    @Override
    public Map<String, FinancialDataConfig> getFinancialDataConfig(Set<String> codes) {
        LambdaQueryWrapper<FinancialDataConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CollectionUtil.isNotEmpty(codes),FinancialDataConfig::getCode, codes);
        List<FinancialDataConfig> financialDataConfigs = this.baseMapper.selectList(queryWrapper);
        return financialDataConfigs.stream().collect(Collectors.toMap(FinancialDataConfig::getCode, Function.identity(), (oldValue, newValue) -> newValue));
    }

    @Override
    public Set<String> getFinancialDataConfigCode(String keyWord) {
        if (StringUtils.isBlank(keyWord)){
            return null;
        }
        LambdaQueryWrapper<FinancialDataConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(FinancialDataConfig::getCode);
        queryWrapper.likeLeft(FinancialDataConfig::getName,keyWord);
        List<FinancialDataConfig> list = this.list(queryWrapper);
        return list.stream().map(FinancialDataConfig::getCode).collect(Collectors.toSet());
    }

    @Override
    public R updateBase(ParameterConfigDto.UpdateBaseDto dto) {
        FinancialDataConfig financialDataConfig = this.baseMapper.selectById(dto.getId());
        if (financialDataConfig == null) {
            throw new BaseException("ID不存在");
        }
        financialDataConfig.setName(dto.getName());
        financialDataConfig.setCode(dto.getCode());
        financialDataConfig.setAccuracy(dto.getAccuracy());
        financialDataConfig.setChangeRateUpper(dto.getChangeRateUpper());
        financialDataConfig.setThresholdValue(dto.getThresholdValue());
        int i = this.baseMapper.updateById(financialDataConfig);
        if (i > 0) {
            //修改来源顺序
            List<DataPlatformConfigDict> reqList = dataPlatformConfigDictService.getReqList();
            for(DataPlatformConfigDict dict:reqList){
                switch (dict.getName()){
                    case "wind":
                        dict.setCode(dto.getWindSeq());
                    case "wind客户端":
                        dict.setCode(dto.getArtificialRecordingSeq());
                    case "同花顺":
                        dict.setCode(dto.getFlushSeq());
                    case "ocr":
                        dict.setCode(dto.getOcrSeq());
                }
            }
            return R.ok();
        } else {
            return R.fail("修改失败");
        }
    }

    @Override
    public R addBase(ParameterConfigDto.UpdateBaseDto dto) {
        FinancialDataConfig financialDataConfig = new FinancialDataConfig();
        financialDataConfig.setName(dto.getName());
        financialDataConfig.setCode(dto.getCode());
        financialDataConfig.setAccuracy(dto.getAccuracy());
        financialDataConfig.setChangeRateUpper(dto.getChangeRateUpper());
        financialDataConfig.setThresholdValue(dto.getThresholdValue());
        int i = this.baseMapper.insert(financialDataConfig);
        if (i > 0) {
            //修改来源顺序
            List<DataPlatformConfigDict> reqList = dataPlatformConfigDictService.getReqList();
            for(DataPlatformConfigDict dict:reqList){
                switch (dict.getName()){
                    case "wind":
                        dict.setCode(dto.getWindSeq());
                    case "wind客户端":
                        dict.setCode(dto.getArtificialRecordingSeq());
                    case "同花顺":
                        dict.setCode(dto.getFlushSeq());
                    case "ocr":
                        dict.setCode(dto.getOcrSeq());
                }
            }
            return R.ok();
        } else {
            return R.fail("新增失败");
        }
    }

    @Override
    public IPage<ModelEvidenceVo.FinancialDataConfigListVo> getFinancialDataConfigList(IPage<ModelEvidenceVo.FinancialDataConfigListVo> page, ModelEvidenceDto.FinancialDataConfigListDto dto) {
        return this.baseMapper.getFinancialDataConfigList(page,dto);
    }
}
