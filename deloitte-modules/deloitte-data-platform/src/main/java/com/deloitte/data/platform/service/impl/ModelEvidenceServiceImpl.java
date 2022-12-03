package com.deloitte.data.platform.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.script.ScriptUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.base.BaseException;
import com.deloitte.common.datasource.annotation.Master_3;

import com.deloitte.data.platform.domian.*;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.enums.HierarchyEnum;
import com.deloitte.data.platform.mapper.ModelEvidenceMapper;
import com.deloitte.data.platform.service.*;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Evidence模型配置
 *
 * @author XY
 * @date 2022/11/20
 */
@Service
@Master_3
public class ModelEvidenceServiceImpl extends ServiceImpl<ModelEvidenceMapper, ModelEvidence> implements IModelEvidenceService {

    private List<String> operatorList = Arrays.asList("+", "-", "*", "/", "(", ")");

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");

    @Autowired
    private IBaseFinDataService baseFinDataService;

    @Autowired
    private IDataPlatformConfigDictService dataPlatformConfigDictService;

    @Autowired
    private IModelRationFactorService modelRationFactorService;

    @Resource
    private IFinancialDataConfigService financialDataConfigService;

    @Resource
    private IModelDataCheckService modelDataCheckService;

    @Resource
    private IDataPlatformConfigDictService iDataPlatformConfigDictService;



    @Override
    public IPage<ModelEvidenceVo.ListVo> getModelEvidenceListPage(ModelEvidenceDto.ListDto dto) {
        Assert.notNull(dto.getHierarchy(), "数据层级不能为空");
        IPage<ModelEvidenceVo.ListVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ModelEvidenceVo.ListVo> result = null;
        if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            result = this.baseMapper.getModelEvidenceListPage(page, dto);
        } else {
            result = modelRationFactorService.getModelEvidenceListPage(page, dto);
        }
        List<ModelEvidenceVo.ListVo> records = result.getRecords();
        for (ModelEvidenceVo.ListVo record : records) {
            if (record.getAccuracy() != null) {
                record.setAccuracy((int) (1 / Math.pow(10, record.getAccuracy())));
            }
        }
        return result;
    }

    @Override
    public IPage<ModelEvidenceVo.FinancialDataConfigListVo> getFinancialDataConfigList(ModelEvidenceDto.FinancialDataConfigListDto dto) {
        IPage<ModelEvidenceVo.FinancialDataConfigListVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ModelEvidenceVo.FinancialDataConfigListVo> result = financialDataConfigService.getFinancialDataConfigList(page, dto);
        return result;
    }

    @Override
    public Map<String, ModelEvidence> getModelEvidenceMap(String keyWord) {
        LambdaQueryWrapper<ModelEvidence> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeLeft(StringUtils.isNotBlank(keyWord),ModelEvidence::getEvidenceName,keyWord);
        List<ModelEvidence> list = this.list(queryWrapper);
        return list.stream().collect(Collectors.toMap(ModelEvidence::getEvidenceCode, Function.identity(), (oldValue, newValue) -> newValue));
    }

    @Override
    public Set<String> getModelEvidenceCode(String keyWord) {
        if (StringUtils.isBlank(keyWord)){
            return null;
        }
        LambdaQueryWrapper<ModelEvidence> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ModelEvidence::getEvidenceCode);
        queryWrapper.likeLeft(ModelEvidence::getEvidenceName,keyWord);
        List<ModelEvidence> list = this.list(queryWrapper);
        return list.stream().map(ModelEvidence::getEvidenceCode).collect(Collectors.toSet());
    }

    @Override
    public R deleteById(ModelEvidenceDto.DeleteDto dto) {
        Assert.notNull(dto.getHierarchy(), "数据层级不能为空");
        if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            if (this.baseMapper.deleteById(dto.getId()) > 0) {
                return R.ok("删除数据配置成功");
            } else {
                return R.fail("删除数据配置失败");
            }
        } else {
            if (modelRationFactorService.deleteById(dto.getId()) > 0) {
                return R.ok("删除数据配置成功");
            } else {
                return R.fail("删除数据配置失败");
            }
        }

    }

    @Override
    public R add(ModelEvidenceDto.AddDto dto) {
        Assert.notNull(dto, "参数不能为空");
        if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            if (dto.getId() != null) {
                ModelEvidence modelEvidence = this.baseMapper.selectById(dto.getId());
                if (modelEvidence == null) {
                    return R.fail("id不存在！");
                }
                //如果公式发生变化,清空异常值处理
                if(!dto.getFormula().equals(modelEvidence.getEvidenceFormula())){
                    modelEvidence.setAbnormalValueHandle("");
                }
                modelEvidence.setEvidenceName(dto.getName());
                modelEvidence.setEvidenceCode(dto.getCode());
                modelEvidence.setEvidenceFormula(dto.getFormula());
                modelEvidence.setEvidenceFormulaDescribe(dto.getFormulaDescribe());
                modelEvidence.setUnit(dto.getUnit());
                modelEvidence.setAccuracy(dto.getAccuracy());
                this.baseMapper.updateById(modelEvidence);
            } else {
                ModelEvidence modelEvidence = new ModelEvidence();
                modelEvidence.setEvidenceName(dto.getName());
                modelEvidence.setEvidenceCode(dto.getCode());
                modelEvidence.setEvidenceFormula(dto.getFormula());
                modelEvidence.setEvidenceFormulaDescribe(dto.getFormulaDescribe());
                modelEvidence.setUnit(dto.getUnit());
                modelEvidence.setAccuracy(dto.getAccuracy());
                this.baseMapper.insert(modelEvidence);
            }
        } else {
            if (dto.getId() != null) {
                ModelRationFactor modelRationFactor = modelRationFactorService.getById(dto.getId());
                if (modelRationFactor == null) {
                    return R.fail("id不存在！");
                }
                //如果公式发生变化,清空异常值处理
                if(!dto.getFormula().equals(modelRationFactor.getFactorFormula())){
                    modelRationFactor.setAbnormalValueHandle("");
                }
                modelRationFactor.setFactorName(dto.getName());
                modelRationFactor.setFactorCode(dto.getCode());
                modelRationFactor.setFactorFormula(dto.getFormula());
                modelRationFactor.setFactorFormulaDescribe(dto.getFormulaDescribe());
                modelRationFactor.setUnit(dto.getUnit());
                modelRationFactor.setAccuracy(dto.getAccuracy());
                modelRationFactorService.updateById(modelRationFactor);
            } else {
                ModelRationFactor modelRationFactor = new ModelRationFactor();
                modelRationFactor.setFactorName(dto.getName());
                modelRationFactor.setFactorCode(dto.getCode());
                modelRationFactor.setFactorFormula(dto.getFormula());
                modelRationFactor.setFactorFormulaDescribe(dto.getFormulaDescribe());
                modelRationFactor.setUnit(dto.getUnit());
                modelRationFactor.setAccuracy(dto.getAccuracy());
                modelRationFactorService.insert(modelRationFactor);
            }
        }
        return R.ok();
    }

    @Override
    public R updateModelById(ModelEvidenceDto.UpdateDto dto) {
        Assert.notNull(dto, "参数不能为空");
        if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            ModelEvidence modelEvidence = this.baseMapper.selectById(dto.getId());
            if (modelEvidence == null) {
                return R.fail("id不存在！");
            }
            modelEvidence.setEvidenceName(dto.getName());
            modelEvidence.setEvidenceCode(dto.getCode());
            modelEvidence.setEvidenceFormula(dto.getFormula());
            modelEvidence.setEvidenceFormulaDescribe(dto.getFormulaDescribe());
            modelEvidence.setUnit(dto.getUnit());
            modelEvidence.setAccuracy(dto.getAccuracy());
            int i = this.baseMapper.updateById(modelEvidence);
        } else {
            ModelRationFactor modelRationFactor = modelRationFactorService.getById(dto.getId());
            if (modelRationFactor == null) {
                return R.fail("id不存在！");
            }
            modelRationFactor.setFactorName(dto.getName());
            modelRationFactor.setFactorCode(dto.getCode());
            modelRationFactor.setFactorFormula(dto.getFormula());
            modelRationFactor.setFactorFormulaDescribe(dto.getFormulaDescribe());
            modelRationFactor.setUnit(dto.getUnit());
            modelRationFactor.setAccuracy(dto.getAccuracy());
            boolean boo = modelRationFactorService.updateById(modelRationFactor);
        }
        return R.ok("数据配置保存成功");
    }

    @Override
    public IPage<ModelEvidenceVo.CalculationVo> calculation(ModelEvidenceDto.CalculationDto dto) throws ParseException {
        String formula = "";
        String evidenceName = "";
        String year = "";
        if (dto.getId() != null) {
            if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
                ModelEvidence modelEvidence = this.baseMapper.selectById(dto.getId());
                formula = modelEvidence.getEvidenceFormula();
                evidenceName = modelEvidence.getEvidenceName();
            } else {
                ModelRationFactor modelRationFactor = modelRationFactorService.getById(dto.getId());
                formula = modelRationFactor.getFactorFormula();
                evidenceName = modelRationFactor.getFactorName();
            }
            year = DateUtil.format(DateUtil.date(), DatePattern.NORM_YEAR_PATTERN);
        } else {
            Assert.notNull(dto.getFormula(), "公式不能为空");
            Assert.notNull(dto.getFormulaName(), "名称不能为空");
            Assert.notNull(dto.getYear(), "年份不能为空");
            formula = dto.getFormula();
            evidenceName = dto.getFormulaName();
            year = dto.getYear();
        }
        String[] codeItems = formula.split(" ");
        String yearLag = String.valueOf(Integer.parseInt(year) - 1);
        String yearSag = String.valueOf(Integer.parseInt(year) - 2);
        Set<String> codes = new HashSet<>();
        Set<String> codesLag = new HashSet<>();
        Set<String> codesSag = new HashSet<>();
        for (int i = 0; i < codeItems.length; i++) {
            if (codeItems[i].matches("\\d") || operatorList.contains(codeItems[i])) {
                //本身就是数字，直接跳过
                continue;
            }
            if (codeItems[i].equals("lag")) {
                //取后两位数值
                codesLag.add(codeItems[i + 2]);
                continue;
            }
            if (codeItems[i].equals("sag")) {
                codesSag.add(codeItems[i + 2]);
                continue;
            }
            codes.add(codeItems[i]);
        }
        //查询分页数据
        IPage<ModelEvidenceVo.CalculationVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ModelEvidenceVo.CalculationVo> result = null;
        result = baseFinDataService.getCalculationPage(page, codes, year); //主年份，用来分页
        List<ModelEvidenceVo.CalculationVo> records = result.getRecords();
        if (records.size() == 0) {
            return result;
        }
        //取出所有主体ID,日期,查询所有数据
        Set<String> entityCodes = new HashSet<>();
        Set<String> reportDates = new HashSet<>();
        for (ModelEvidenceVo.CalculationVo vo : records) {
            entityCodes.add(vo.getEntityCode());
            reportDates.add(vo.getReportDate());
        }
        List<ModelEvidenceVo.FinDataVo> baseFinDataList = baseFinDataService.getCalculationAllData(codes, entityCodes, reportDates, year); //主年份所有数据
        if (codesLag.size() > 0) {  //上一年所有数据
            reportDates = this.replaceYear(reportDates, yearLag);
            List<ModelEvidenceVo.FinDataVo> baseFinDataListLag = baseFinDataService.getCalculationAllData(codesLag, entityCodes, reportDates, yearLag); //上一年份所有数据
            for (ModelEvidenceVo.FinDataVo vo : baseFinDataListLag) {
                vo.setType("lag");
            }
            baseFinDataList.addAll(baseFinDataListLag);
        }
        if (codesSag.size() > 0) {  //上上一年所有数据
            reportDates = this.replaceYear(reportDates, yearSag);
            List<ModelEvidenceVo.FinDataVo> baseFinDataListSag = baseFinDataService.getCalculationAllData(codesSag, entityCodes, reportDates, yearSag); //上一年份所有数据
            for (ModelEvidenceVo.FinDataVo vo : baseFinDataListSag) {
                vo.setType("sag");
            }
            baseFinDataList.addAll(baseFinDataListSag);
        }
        Map<String, FinancialDataConfig> configMap = financialDataConfigService.getFinancialDataConfig(codes);
        for (ModelEvidenceVo.CalculationVo vo : records) {
            vo.setFormulaDescribe(evidenceName);
            getCalculationValue(vo, codeItems, baseFinDataList, configMap);
        }
        return result;
        //returnVo.setFields2(records.get);
    }

    @Override
    public ModelEvidenceVo.ListVo detail(ModelEvidenceDto.DeleteDto dto) {
        ModelEvidenceVo.ListVo vo = new ModelEvidenceVo.ListVo();
        if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            ModelEvidence modelEvidence = this.baseMapper.selectById(dto.getId());
            vo.setCode(modelEvidence.getEvidenceCode());
            vo.setId(modelEvidence.getId());
            vo.setName(modelEvidence.getEvidenceName());
            vo.setAccuracy(modelEvidence.getAccuracy());
            vo.setUnit(modelEvidence.getUnit());
            vo.setFormula(modelEvidence.getEvidenceFormula());
            vo.setFormulaDescribe(modelEvidence.getEvidenceFormulaDescribe());
        } else {
            ModelRationFactor modelRationFactor = modelRationFactorService.getById(dto.getId());
            vo.setCode(modelRationFactor.getFactorCode());
            vo.setId(modelRationFactor.getId());
            vo.setName(modelRationFactor.getFactorName());
            vo.setAccuracy(modelRationFactor.getAccuracy());
            vo.setUnit(modelRationFactor.getUnit());
            vo.setFormula(modelRationFactor.getFactorFormula());
            vo.setFormulaDescribe(modelRationFactor.getFactorFormulaDescribe());
        }
        return vo;
    }

    @Override
    public IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(ParameterConfigDto.ListDto dto) {
        Assert.notNull(dto.getHierarchy(), "数据层级不能为空");
        if (dto.getHierarchy() == null) {
            dto.setHierarchy(HierarchyEnum.BASE.getCode());
        }
        IPage<ParameterConfigVo.ListVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ParameterConfigVo.ListVo> result = null;
        List<ParameterConfigVo.ListVo> records = null;
        if (dto.getHierarchy().equals(HierarchyEnum.BASE.getCode())) {
            result = baseFinDataService.getParameterConfigListPage(page, dto);
            records = result.getRecords();
            //查询优先级
            List<DataPlatformConfigDict> reqList = dataPlatformConfigDictService.getReqList();
            for (ParameterConfigVo.ListVo vo : records) {
                for (DataPlatformConfigDict dict : reqList) {
                    switch (dict.getName()) {
                        case "wind":
                            vo.setWindSeq(dict.getCode());
                        case "wind客户端":
                            vo.setArtificialRecordingSeq(dict.getCode());
                        case "同花顺":
                            vo.setFlushSeq(dict.getCode());
                        case "ocr":
                            vo.setOcrSeq(dict.getCode());
                    }
                }
            }
        } else if (dto.getHierarchy().equals(HierarchyEnum.MIDDLE.getCode())) {
            result = this.baseMapper.getParameterConfigListPage(page, dto);
            records = result.getRecords();
            for (ParameterConfigVo.ListVo vo : records) {
                vo.setAbnormalValueHandleSelects(formulaSelectsVo(vo.getFormula(),vo.getFormulaDescribe())); //异常下拉列表
                vo.setAbnormalValueHandleList(jsontoSelectsVo(vo.getAbnormalValueHandle())); //异常JSON字符串转，异常数组
            }
        } else {
            result = modelRationFactorService.getParameterConfigListPage(page, dto);
            records = result.getRecords();
            for (ParameterConfigVo.ListVo vo : records) {
                vo.setAbnormalValueHandleSelects(formulaSelectsVo(vo.getFormula(),vo.getFormulaDescribe())); //异常下拉列表
                vo.setAbnormalValueHandleList(jsontoSelectsVo(vo.getAbnormalValueHandle())); //异常JSON字符串转，异常数组
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.printf(String.valueOf(Integer.MIN_VALUE));
    }

    public List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> jsontoSelectsVo(String AbnormalValueHandle){
        List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleList = new ArrayList<>();
        if (StringUtils.isBlank(AbnormalValueHandle)) {
            return abnormalValueHandleList;
        }
        JSONObject dataJsonObject = JSONObject.parseObject(AbnormalValueHandle);
        JSONArray dataArray = dataJsonObject.getJSONArray("data");
        for (Object o : dataArray) {
            JSONObject param = (JSONObject) o;
            ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo abnormalValueHandleListvo = JSON.parseObject(param.toJSONString(),
                    ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo.class);
            abnormalValueHandleList.add(abnormalValueHandleListvo);
        }

        return abnormalValueHandleList;
    }

    public List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> formulaSelectsVo(String formula,String formulaDescribe){
        List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleList = new ArrayList<>();
        if (StringUtils.isBlank(formula)||StringUtils.isBlank(formulaDescribe)) {
            return abnormalValueHandleList;
        }
        String[] datamodel = formula.split(" ");
        String[] datamodel2 = formulaDescribe.split(" ");
        List<String> operatorList = Arrays.asList("+", "-", "*", "/", "(", ")","lag","sag");
        List<String> codes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for(String st:datamodel){
            if (st.matches("\\d") || operatorList.contains(st)) {
                //本身就是数字，直接跳过
                continue;
            }
            codes.add(st);
        }
        for(String st:datamodel2){
            if (st.matches("\\d") || operatorList.contains(st)) {
                //本身就是数字，直接跳过
                continue;
            }
            names.add(st);
        }
        if(codes.size()!=names.size()){
            return abnormalValueHandleList;
        }
        for(int i=0;i<codes.size();i++){
            ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo vo = new ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo();
            vo.setCode(codes.get(i));
            vo.setName(names.get(i));
            abnormalValueHandleList.add(vo);
        }
        return abnormalValueHandleList;
    }

    @Override
    public R updateBase(ParameterConfigDto.UpdateBaseDto dto) {
        return financialDataConfigService.updateBase(dto);
    }

    @Override
    public R updateMiddle(ParameterConfigDto.UpdateMiddleDto dto) {
        ModelEvidence modelEvidence = this.baseMapper.selectById(dto.getId());
        if (modelEvidence == null) {
            throw new BaseException("ID不存在");
        }
        modelEvidence.setEvidenceName(dto.getName());
        modelEvidence.setEvidenceCode(dto.getCode());
        modelEvidence.setAccuracy(dto.getAccuracy());
        modelEvidence.setChangeRateUpper(dto.getChangeRateUpper());
        modelEvidence.setThresholdValue(dto.getThresholdValue());
        modelEvidence.setEvidenceFormula(dto.getFormula());
        int i = this.baseMapper.updateById(modelEvidence);
        if (i > 0) {
            return R.ok();
        } else {
            return R.fail("修改失败");
        }
    }

    @Override
    public R updateApply(ParameterConfigDto.UpdateApplyDto dto) {
        return modelRationFactorService.updateModelRation(dto);
    }

    @Override
    public List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto) {
        return this.baseMapper.getTopDataMenu(dto);
    }

    @Override
    public R addOrUpdateBase(ParameterConfigDto.UpdateBaseDto dto) {
        if(dto.getId()!=null){
            return financialDataConfigService.updateBase(dto);
        }else{
            return financialDataConfigService.addBase(dto);
        }
    }

    @Override
    public R addOrUpdateMiddle(ParameterConfigDto.UpdateMiddleDto dto) {
        if(dto.getId()!=null){
            ModelEvidence modelEvidence = this.baseMapper.selectById(dto.getId());
            if(modelEvidence==null){
                throw new BaseException("ID不存在");
            }
            modelEvidence.setEvidenceName(dto.getName());
            modelEvidence.setEvidenceCode(dto.getCode());
            modelEvidence.setAccuracy(dto.getAccuracy());
            modelEvidence.setChangeRateUpper(dto.getChangeRateUpper());
            modelEvidence.setThresholdValue(dto.getThresholdValue());
            modelEvidence.setEvidenceFormula(dto.getFormula());
            this.baseMapper.updateById(modelEvidence);
        }else{
            ModelEvidence modelEvidence = new ModelEvidence();
            modelEvidence.setEvidenceName(dto.getName());
            modelEvidence.setEvidenceCode(dto.getCode());
            modelEvidence.setAccuracy(dto.getAccuracy());
            modelEvidence.setChangeRateUpper(dto.getChangeRateUpper());
            modelEvidence.setThresholdValue(dto.getThresholdValue());
            modelEvidence.setEvidenceFormula(dto.getFormula());
            this.baseMapper.insert(modelEvidence);
        }
        return R.ok();
    }

    @Override
    public R addOrUpdateApply(ParameterConfigDto.UpdateApplyDto dto) {
        if(dto.getId()==null){
            return modelRationFactorService.addModelRation(dto);
        }else{
            return modelRationFactorService.updateModelRation(dto);
        }
    }

    @Override
    public IPage<ParameterConfigVo.ModelDataCheckListVo> getModelDataCheckListPage(ParameterConfigDto.modelDataCheckListPageDto dto) {
        IPage<ParameterConfigVo.ModelDataCheckListVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ParameterConfigVo.ModelDataCheckListVo> result = modelDataCheckService.getModelDataCheckListPage(page, dto);
        return result;
    }

    @Override
    public R updateOrAdd(ParameterConfigDto.UpdateOrAddDto dto) {
        if (dto.getId() != null) {
            ModelDataCheck modelDataCheck = modelDataCheckService.getById(dto.getId());
            if (modelDataCheck == null) {
                throw new BaseException("ID不存在");
            }
            BeanUtils.copyProperties(dto, modelDataCheck);
            modelDataCheckService.updateById(modelDataCheck);
        } else {
            ModelDataCheck modelDataCheck = new ModelDataCheck();
            BeanUtils.copyProperties(dto, modelDataCheck);
            modelDataCheckService.save(modelDataCheck);
        }
        return R.ok();
    }

    @Override
    public Boolean checkData(ParameterConfigDto.CheckDataDto dto) {
        Assert.notNull(dto, "参数不能为空");
        String checkFormula = dto.getCheckFormula();
        if (StringUtils.countMatches(checkFormula, "(") != StringUtils.countMatches(checkFormula, ")")) {
            return false;
        }
        return null;
    }

    @Override
    public DataPlatformMenuVo getBaseConfigDictSubordinate() {
        return iDataPlatformConfigDictService.getBaseConfigDictSubordinate("model_check");
    }

    private Set<String> replaceYear(Set<String> reportDates, String yearLag) {
        return reportDates.stream().map(str -> yearLag + str.substring(4)).collect(Collectors.toSet());
    }

    public void getCalculationValue(ModelEvidenceVo.CalculationVo vo, String[] codeItems, List<ModelEvidenceVo.FinDataVo> baseFinDataList, Map<String, FinancialDataConfig> configMap) throws ParseException {
        String[] codeItemsCopy = Arrays.copyOf(codeItems, codeItems.length);
        List<ModelEvidenceVo.CalculationVo.OtherFieldVo> otherFieldVos = new ArrayList<>();
        for (ModelEvidenceVo.FinDataVo baseFinData : baseFinDataList) {
            if (!vo.getEntityCode().equals(baseFinData.getEntityCode())) {
                continue;
            }
            if (!vo.getReportDate().substring(5).equals(baseFinData.getReportDate().toString().substring(5))) {
                continue;
            }
            if (!vo.getCode().equals(baseFinData.getCode())) {
                continue;
            }
            ModelEvidenceVo.CalculationVo.OtherFieldVo otherFieldVo = new ModelEvidenceVo.CalculationVo.OtherFieldVo();
            otherFieldVo.setFieldCode(baseFinData.getCode());
            otherFieldVo.setFieldValue(baseFinData.getSuggestValue());
            FinancialDataConfig financialDataConfig = configMap.get(baseFinData.getCode());
            if (financialDataConfig != null) {
                otherFieldVo.setFieldName(financialDataConfig.getName() + "(" + baseFinData.getCode() + ")");
            }
            otherFieldVos.add(otherFieldVo);
            for (int i = 0; i < codeItemsCopy.length; i++) { //替换公式里变量为具体值
                if (codeItemsCopy[i].equals(baseFinData.getCode())) {
                    if (i > 2) {
                        if (codeItemsCopy[i - 2].equals("lag")) {
                            if (!StringUtils.isBlank(baseFinData.getType()) && baseFinData.getType().equals("lag")) {
                                codeItemsCopy[i] = baseFinData.getSuggestValue();
                            }
                        } else if (codeItemsCopy[i - 2].equals("sag")) {
                            if (!StringUtils.isBlank(baseFinData.getType()) && baseFinData.getType().equals("lag")) {
                                codeItemsCopy[i] = baseFinData.getSuggestValue();
                            }
                        } else {
                            codeItemsCopy[i] = baseFinData.getSuggestValue();
                        }
                    } else {
                        codeItemsCopy[i] = baseFinData.getSuggestValue();
                    }

                }
            }
        }
        List<String> codeItemList = Arrays.asList(codeItemsCopy);
        codeItemList = codeItemList.stream().filter(f -> !f.equals("lag") && !f.equals("sag")).collect(Collectors.toList()); //去掉年份
        String revertFormula = String.join(" ", codeItemList);
        String value = "";
        if (revertFormula.contains("-999999.9999")) { //判断公式里是否有异常值
            value = "-999999.9999";
        } else {
            value = ScriptUtil.eval(revertFormula).toString(); //执行计算
        }
        ModelEvidenceVo.CalculationVo.OtherFieldVo otherFieldVo = new ModelEvidenceVo.CalculationVo.OtherFieldVo();
        otherFieldVo.setFieldCode(vo.getCode());
        otherFieldVo.setFieldValue(value);
        otherFieldVo.setFieldName(vo.getFormulaDescribe() + "(" + vo.getCode() + ")");
        otherFieldVos.add(otherFieldVo);
        vo.setFields(otherFieldVos);
    }

}
