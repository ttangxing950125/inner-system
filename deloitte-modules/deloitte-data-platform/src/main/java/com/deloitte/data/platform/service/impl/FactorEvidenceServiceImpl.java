package com.deloitte.data.platform.service.impl;

import cn.hutool.script.ScriptUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.deloitte.data.platform.component.ApplicationContextRegister;
import com.deloitte.data.platform.domian.*;
import com.deloitte.data.platform.dto.FactorCalculateDto;
import com.deloitte.data.platform.dto.FormulaItemDataDto;
import com.deloitte.data.platform.dto.FormulaResultDto;
import com.deloitte.data.platform.dto.ListBaseFinDataDto;
import com.deloitte.data.platform.enums.HierarchyEnum;
import com.deloitte.data.platform.handler.FunctionHandler;
import com.deloitte.data.platform.service.*;
import com.deloitte.data.platform.vo.FactorOrmVo;
import com.deloitte.data.platform.vo.MasterEntityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 指标和Evidence计算
 *
 * @author XY
 * @date 2022/11/19
 */
@Service
public class FactorEvidenceServiceImpl implements IFactorEvidenceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> operatorList = Arrays.asList("+", "-", "*", "/", "(", ")", "[", "]", "{", "}");

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private IFactorIndustryOrmService factorIndustryOrmService;

    @Autowired
    private IBaseFinDataService baseFinDataService;

    @Autowired
    private IBaseStructuredNotesService baseStructuredNotesService;

    @Autowired
    private IEvidenceDataService evidenceDataService;

    @Autowired
    private IModelEvidenceService modelEvidenceService;

    @Autowired
    private IEvidenceErrorLogService evidenceErrorLogService;

    @Autowired
    private IFinFactorValueService finFactorValueService;

    @Autowired
    private IFactorErrorLogService factorErrorLogService;


    @Override
    public void calculate(FactorCalculateDto dto) {
        long startTime = System.currentTimeMillis();
        //验证参数
        this.checkCalculateParam(dto);
        //获取主体集合
        List<MasterEntityVo> voList = this.listMasterEntity(dto);
        Assert.notEmpty(voList, "主体不存在");
        //由于数据量较大，这里拆去分页处理，每页处理 1000 个
        int pageSize = 1000;
        int size = voList.size();
        int pages = (size / pageSize) + (size % pageSize > 0 ? 1 : 0);

        for (int i = 0; i < pages; i++) {
            int startIndex = i * pageSize;
            int endIndex = (startIndex + pageSize - 1) > (size - 1) ? (size - 1) : (startIndex + pageSize - 1);
            List<MasterEntityVo> subVoList = voList.subList(startIndex, endIndex);
            //获取敞口集合
            List<String> industryWhitewashList = new ArrayList<>();
            //获取主体code集合
            List<String> entityCodeList = new ArrayList<>();
            for (MasterEntityVo masterEntityVo : subVoList) {
                entityCodeList.add(masterEntityVo.getEntityCode());
                industryWhitewashList.add(masterEntityVo.getIndustryWhitewash());
            }
            //获取指标集合
            List<FactorOrmVo> factorOrmVoList = this.listFactorOrmVo(dto, industryWhitewashList);
            if (StringUtils.hasLength(dto.getFactorCode())) {
                Assert.notEmpty(factorOrmVoList, "指标公式不存在");
            }
            //获取中间层公式集合
            factorOrmVoList.addAll(listModelEvidenceToFactorOrmVo());
            //获取公式 每项数据
            List<FormulaItemDataDto> formulaItemDataDtoList = listFormulaItemData(factorOrmVoList, entityCodeList, dto.getReportDate());
            //每个主体执行对应敞口的指标公式（当公式敞口为空时，能别所有主体执行）
            this.execute(subVoList, factorOrmVoList, formulaItemDataDtoList, dto.getReportDate());
        }
        logger.info("计算总时长：{}", System.currentTimeMillis() - startTime);
    }


    /**
     * 检查参数
     *
     * @param dto
     */
    private void checkCalculateParam(FactorCalculateDto dto) {
        Assert.notNull(dto.getYear(), "年份必填");
        //现在默认只有年报
        dto.setReportDate(LocalDate.parse(dto.getYear() + "-12-31"));
        if (LocalDate.now().compareTo(dto.getReportDate()) < 0) {
            throw new RuntimeException("不能计算未来年份");
        }
        //目前业务线场景只有 8  ，只有内评这个业务场景
        if (!StringUtils.hasLength(dto.getBusinessScene())) {
            dto.setBusinessScene("8");
        }
    }

    /**
     * 模拟通过 业务场景、业务线、敞口、主体code 获取主体信息
     *
     * @return
     */
    private List<MasterEntityVo> listMasterEntity(FactorCalculateDto dto) {
        List<MasterEntityVo> voList = new ArrayList<>();
        //因为在计算中 ，公式不关注 业务场景与业务线，这里直接根据敞口code与实体code过滤
        MasterEntityVo vo1 = new MasterEntityVo();
        vo1.setEntityCode("IB000001");
        vo1.setIndustryWhitewash("重工业");

        MasterEntityVo vo2 = new MasterEntityVo();
        vo2.setEntityCode("IB000002");
        vo2.setIndustryWhitewash("重工业");

        MasterEntityVo vo3 = new MasterEntityVo();
        vo3.setEntityCode("IB000004");
        vo3.setIndustryWhitewash("IT与电信");

        MasterEntityVo vo4 = new MasterEntityVo();
        vo4.setEntityCode("IB000006");
        vo4.setIndustryWhitewash("批发零售");

        MasterEntityVo vo5 = new MasterEntityVo();
        vo5.setEntityCode("IB000010");
        vo5.setIndustryWhitewash("医疗保健");

        MasterEntityVo vo6 = new MasterEntityVo();
        vo6.setEntityCode("IB000011");
        vo6.setIndustryWhitewash("建筑");

        MasterEntityVo vo7 = new MasterEntityVo();
        vo7.setEntityCode("IB000012");
        vo7.setIndustryWhitewash("农林牧渔与食品饮料");

        MasterEntityVo vo8 = new MasterEntityVo();
        vo8.setEntityCode("IB000014");
        vo8.setIndustryWhitewash("交通运输");

        MasterEntityVo vo9 = new MasterEntityVo();
        vo9.setEntityCode("IB000019");
        vo9.setIndustryWhitewash("公用事业");

        MasterEntityVo vo10 = new MasterEntityVo();
        vo10.setEntityCode("IB000024");
        vo10.setIndustryWhitewash("类银行金融机构");

        voList.add(vo1);
        voList.add(vo2);
        voList.add(vo3);
        voList.add(vo4);
        voList.add(vo5);
        voList.add(vo6);
        voList.add(vo7);
        voList.add(vo8);
        voList.add(vo9);
        voList.add(vo10);

        return voList;
    }

    /**
     * 根据指标code与敞口获取公式
     *
     * @return
     */
    private List<FactorOrmVo> listFactorOrmVo(FactorCalculateDto dto, List<String> industryWhitewashList) {
        List<String> factorCodeList = new ArrayList<>();
        if (StringUtils.hasLength(dto.getFactorCode())) {
            factorCodeList.add(dto.getFactorCode());
        }
        return this.factorIndustryOrmService.list(factorCodeList, industryWhitewashList);
    }


    /**
     * 获取中间层公式
     *
     * @return
     */
    private List<FactorOrmVo> listModelEvidenceToFactorOrmVo() {
        //获取中间层公式集合
        List<ModelEvidence> modelEvidenceList = this.modelEvidenceService.list();
        if (CollectionUtils.isEmpty(modelEvidenceList)) {
            return new ArrayList<>();
        }
        return modelEvidenceList.stream().map(modelEvidence -> {
            FactorOrmVo factorOrmVo = new FactorOrmVo();
            factorOrmVo.setCode(modelEvidence.getEvidenceCode());
            factorOrmVo.setFormula(modelEvidence.getEvidenceFormula());
            factorOrmVo.setIndustryWhitewash(null);
            factorOrmVo.setUnit(modelEvidence.getUnit());
            factorOrmVo.setAccuracy(modelEvidence.getAccuracy());
            factorOrmVo.setAbnormalValueHandle(modelEvidence.getAbnormalValueHandle());
            factorOrmVo.setChangeRateUpper(modelEvidence.getChangeRateUpper());
            factorOrmVo.setThresholdValue(modelEvidence.getThresholdValue());
            factorOrmVo.setHierarchy(HierarchyEnum.MIDDLE.getCode());
            return factorOrmVo;
        }).collect(Collectors.toList());
    }


    /**
     * 获取公式对应的所有科目
     *
     * @param factorOrmList
     * @param entityCodeList
     * @param reportDate
     * @return
     */
    private List<FormulaItemDataDto> listFormulaItemData(List<FactorOrmVo> factorOrmList, List<String> entityCodeList, LocalDate reportDate) {
        Map<String, LocalDate> reportDateMap = new HashMap<>();
        reportDateMap.put(reportDate.toString(), reportDate);
        //解析公式
        String lagRegex = "(.*)lag \\((.*)";
        String sagRegex = "(.*)sag \\((.*)";
        for (FactorOrmVo factorOrmVo : factorOrmList) {
            String formula = factorOrmVo.getFormula();
            if (!StringUtils.hasLength(formula)) {
                continue;
            }
            if (formula.matches(lagRegex)) {
                //匹配上 lagRegex 需要查询上一年的数据
                LocalDate lagDate = reportDate.minusYears(1L);
                reportDateMap.put(lagDate.toString(), lagDate);
            }
            if (formula.matches(sagRegex)) {
                //匹配上 sagRegex 需要查询上上一年（前年）的数据
                LocalDate sagDate = reportDate.minusYears(2L);
                reportDateMap.put(sagDate.toString(), sagDate);
            }
        }
        List<ListBaseFinDataDto> dtoList = reportDateMap.keySet().stream().map(key -> {
            ListBaseFinDataDto listBaseFinDataDto = new ListBaseFinDataDto();
            listBaseFinDataDto.setEntityCodeList(entityCodeList);
            listBaseFinDataDto.setReportDate(reportDateMap.get(key));
            listBaseFinDataDto.setYear(reportDateMap.get(key).getYear());
            return listBaseFinDataDto;
        }).collect(Collectors.toList());
        //查询 基础数据
        List<BaseFinData> baseFinDataList = this.baseFinDataService.listBaseFinData(dtoList);
        //结构化附注表
        QueryWrapper<BaseStructuredNotes> baseStructuredNotesQueryWrapper = new QueryWrapper<>();
        baseStructuredNotesQueryWrapper.lambda()
                .select(BaseStructuredNotes::getCode, BaseStructuredNotes::getValue, BaseStructuredNotes::getEntityCode, BaseStructuredNotes::getReportDate)
                .in(BaseStructuredNotes::getEntityCode, entityCodeList)
                .in(BaseStructuredNotes::getReportDate, reportDateMap.values());
        List<BaseStructuredNotes> baseStructuredNotesList = this.baseStructuredNotesService.list(baseStructuredNotesQueryWrapper);
        //查询 中间层公式数据
        QueryWrapper<EvidenceData> evidenceDataQueryWrapper = new QueryWrapper<>();
        evidenceDataQueryWrapper.lambda()
                .select(EvidenceData::getCode, EvidenceData::getValue, EvidenceData::getEntityCode, EvidenceData::getReportDate)
                .in(EvidenceData::getEntityCode, entityCodeList)
                .in(EvidenceData::getReportDate, reportDateMap.values());
        List<EvidenceData> evidenceDataList = this.evidenceDataService.list(evidenceDataQueryWrapper);
        //合并数据
        List<FormulaItemDataDto> formulaItemDataList = new ArrayList<>();
        List<FormulaItemDataDto> data1 = baseFinDataList.stream().map(baseFinData -> {
            FormulaItemDataDto formulaItemData = new FormulaItemDataDto();
            formulaItemData.setCode(baseFinData.getCode());
            formulaItemData.setValue(baseFinData.getSuggestValue());
            formulaItemData.setEntityCode(baseFinData.getEntityCode());
            formulaItemData.setReportDate(baseFinData.getReportDate());
            return formulaItemData;
        }).collect(Collectors.toList());
        formulaItemDataList.addAll(data1);
        if (!CollectionUtils.isEmpty(baseStructuredNotesList)) {
            List<FormulaItemDataDto> data2 = baseStructuredNotesList.stream().map(baseStructuredNotes -> {
                FormulaItemDataDto formulaItemData = new FormulaItemDataDto();
                formulaItemData.setCode(baseStructuredNotes.getCode());
                formulaItemData.setValue(baseStructuredNotes.getValue());
                formulaItemData.setEntityCode(baseStructuredNotes.getEntityCode());
                formulaItemData.setReportDate(baseStructuredNotes.getReportDate());
                return formulaItemData;
            }).collect(Collectors.toList());
            formulaItemDataList.addAll(data2);
        }
        if (!CollectionUtils.isEmpty(evidenceDataList)) {
            List<FormulaItemDataDto> data3 = evidenceDataList.stream().map(evidenceData -> {
                FormulaItemDataDto formulaItemData = new FormulaItemDataDto();
                formulaItemData.setCode(evidenceData.getCode());
                formulaItemData.setValue(evidenceData.getValue());
                formulaItemData.setEntityCode(evidenceData.getEntityCode());
                formulaItemData.setReportDate(evidenceData.getReportDate());
                return formulaItemData;
            }).collect(Collectors.toList());
            formulaItemDataList.addAll(data3);
        }
        return formulaItemDataList;
    }


    /**
     * 每个主体执行对应敞口的指标公式（当公式敞口为空时，能别所有主体执行）
     *
     * @param factorOrmList
     * @param masterEntityList
     * @param formulaItemDataDtoList
     * @param reportDate
     */
    private void execute(List<MasterEntityVo> masterEntityList, List<FactorOrmVo> factorOrmList, List<FormulaItemDataDto> formulaItemDataDtoList, LocalDate reportDate) {

        for (MasterEntityVo masterEntityVo : masterEntityList) {
            long entityStartTime = System.currentTimeMillis();
            /**
             * 这里在获取的时候就直接转成MAP  避免后续循环取值
             *
             * {
             *     "年份":{
             *         "公式项":"值"
             *     }
             * }
             */
            String entityCode = masterEntityVo.getEntityCode();
            Map<String, Map<String, String>> entityFormulaItemDataMap = new HashMap<>();
            for (FormulaItemDataDto formulaItemData : formulaItemDataDtoList) {
                if (!entityCode.equals(formulaItemData.getEntityCode())) {
                    continue;
                }
                Map<String, String> itemDataMap = entityFormulaItemDataMap.get(formulaItemData.getReportDate().toString());
                if (CollectionUtils.isEmpty(itemDataMap)) {
                    //说明之前没有该年份的值
                    itemDataMap = new HashMap<>();
                }
                itemDataMap.put(formulaItemData.getCode(), formulaItemData.getValue());
                entityFormulaItemDataMap.put(formulaItemData.getReportDate().toString(), itemDataMap);
            }
            //一个主体执行完再批量写库
            List<FormulaResultDto> formulaResultDtoList = new ArrayList<>();
            for (FactorOrmVo factorOrmVo : factorOrmList) {
                long factorStartTime = System.currentTimeMillis();
                //公式敞口等于空或空字符，表示所有主体都要执行，反之只执行敞口相同的主体
                if (StringUtils.hasLength(factorOrmVo.getIndustryWhitewash())
                        && !masterEntityVo.getIndustryWhitewash().equals(factorOrmVo.getIndustryWhitewash())) {
                    continue;
                }
                //解析公式
                String[] formulaItems = factorOrmVo.getFormula().split(" ");
                List<String> formulaItemList = Arrays.asList(formulaItems);
                int formulaItemSize = formulaItemList.size();
                for (int i = 0; i < formulaItemSize; i++) {
                    String item = formulaItemList.get(i);
                    if (item.matches("\\d") || operatorList.contains(item)) {
                        //本身就是数字，直接跳过
                        continue;
                    }
                    //是不是函数呢？
                    if (i + 1 < formulaItemSize && "(".equals(formulaItemList.get(i + 1))) {
                        //说明是函数，选择函数处理器处理
                        FunctionHandler functionHandler = ApplicationContextRegister.getBean(item, FunctionHandler.class);
                        Assert.notNull(functionHandler, "未知函数：" + item);
                        //交给函数处理器处理
                        int index = functionHandler.executer(entityCode, reportDate, formulaItemList, i, entityFormulaItemDataMap);
                        i = index;
                        continue;
                    }
                    //排除以上可能就是 科目，对应取值
                    formulaItemList.set(i, this.itemValue(entityFormulaItemDataMap, item, reportDate));
                }
                String revertFormula = String.join(" ", formulaItemList);
                String resultValue = this.formulaCalculation(revertFormula);
                //将结果写回 entityFormulaItemDataMap，避免后续公式会使用到前面公式计算出来的值
                this.writeBackToEntityFormulaItemDataMap(entityFormulaItemDataMap, reportDate, factorOrmVo.getCode(), resultValue);
                //将结果记录到 formulaResultDtoList 集合中
                this.addToFormulaResultList(formulaResultDtoList, entityCode, reportDate, factorOrmVo.getCode(), factorOrmVo.getHierarchy(), factorOrmVo.getFormula(), factorOrmVo.getChangeRateUpper(), revertFormula, resultValue);
                logger.info("指标 {} 使用时长：", factorOrmVo.getCode(), System.currentTimeMillis() - factorStartTime);
            }
            //该主体的计算结果 存库
            this.saveFormulaResult(formulaResultDtoList);
            logger.info("企业 {} 使用时长：", masterEntityVo.getEntityCode(), System.currentTimeMillis() - entityStartTime);
        }
    }

    /**
     * 公式项取值
     *
     * @param entityFormulaItemDataMap
     * @param item
     * @param reportDate
     * @return
     */
    private String itemValue(Map<String, Map<String, String>> entityFormulaItemDataMap, String item, LocalDate reportDate) {
        Map<String, String> itemDateMap = entityFormulaItemDataMap.get(reportDate.toString());
        if (CollectionUtils.isEmpty(itemDateMap)) {
            return "0";
        }
        if (StringUtils.hasLength(itemDateMap.get(item))) {
            return itemDateMap.get(item);
        }
        return "0";
    }


    /**
     * 公式具体执行者
     *
     * @param revertFormula 还原后的公式
     */
    private String formulaCalculation(String revertFormula) {
        //公式中是否有异常值 -999999.9999
        if (revertFormula.contains("-999999.9999")) {
            return "-999999.9999";
        }
        logger.info("公式：{}", revertFormula);
        //计算结果
        Object result = ScriptUtil.eval(revertFormula);
        if ("NaN".equals(result) || "-Infinity".equals(result)) {
            logger.info("结果异常：{}", JSON.toJSONString(result));
            //视为异常值
            return "-999999.9999";
        }
        return new BigDecimal(result.toString()).toString();
    }


    /**
     * 回写数据至 entityFormulaItemDataMap
     *
     * @param entityFormulaItemDataMap
     * @param reportDate
     * @param formulaCode
     * @param resultValue
     */
    private void writeBackToEntityFormulaItemDataMap(Map<String, Map<String, String>> entityFormulaItemDataMap, LocalDate reportDate, String formulaCode, String resultValue) {
        Map<String, String> itemDataMap = entityFormulaItemDataMap.get(reportDate.toString());
        if (CollectionUtils.isEmpty(itemDataMap)) {
            //说明之前没有该年份的值
            itemDataMap = new HashMap<>();
        }
        itemDataMap.put(formulaCode, resultValue);
        entityFormulaItemDataMap.put(reportDate.toString(), itemDataMap);
    }


    /**
     * 把计算结果存到 集合中
     *
     * @param formulaResultDtoList 该主体所有计算结果
     * @param entityCode           主体编码
     * @param reportDate           上报时间
     * @param formulaCode          公式编码
     * @param hierarchy            公式所在层（例  中间层 ，指标层）
     * @param resultValue          原始公式
     * @param resultValue          翻译后的公式
     * @param resultValue          公式计算结果
     */
    private void addToFormulaResultList(List<FormulaResultDto> formulaResultDtoList,
                                        String entityCode,
                                        LocalDate reportDate,
                                        String formulaCode,
                                        Integer hierarchy,
                                        String primalFormula,
                                        BigDecimal changeRateUpper,
                                        String revertFormula,
                                        String resultValue) {
        FormulaResultDto formulaResultDto = new FormulaResultDto();
        formulaResultDto.setEntityCode(entityCode);
        formulaResultDto.setReportDate(reportDate);
        formulaResultDto.setFormulaCode(formulaCode);
        formulaResultDto.setHierarchy(hierarchy);
        formulaResultDto.setResultValue(resultValue);
        formulaResultDto.setPrimalFormula(primalFormula);
        formulaResultDto.setChangeRateUpper(changeRateUpper);
        formulaResultDto.setTranslateFormula(revertFormula);
        formulaResultDtoList.add(formulaResultDto);
    }


    /**
     * 将该 同一批主体、同一上报时间   的计算结果保存到数据库
     *
     * @param formulaResultDtoList
     */
    private void saveFormulaResult(List<FormulaResultDto> formulaResultDtoList) {
        if (CollectionUtils.isEmpty(formulaResultDtoList)) {
            return;
        }
        String entityCode = formulaResultDtoList.get(0).getEntityCode();
        LocalDate reportDate = formulaResultDtoList.get(0).getReportDate();
        LocalDate lastReportDate = reportDate.minusYears(1L);
        //获取该上报日期的上一年的信息，用来计算变动率(中间层)
        Map<String, String> lastEvidenceDataMap = reportDateEvidenceData(entityCode, lastReportDate);
        //获取该上报日期的上一年的信息，用来计算变动率(指标层)
        Map<String, BigDecimal> lastFinFactorValueMap = reportDateFinFactorValue(entityCode, lastReportDate);
        //指标层错误集合
        List<FactorErrorLog> factorErrorLogList = new ArrayList<>();
        //中间层错误集合
        List<EvidenceErrorLog> evidenceErrorLogList = new ArrayList<>();
        List<EvidenceData> evidenceDataList = new ArrayList<>();
        List<FinFactorValue> finFactorValueList = new ArrayList<>();
        for (FormulaResultDto formulaResultDto : formulaResultDtoList) {
            if (HierarchyEnum.MIDDLE.getCode().equals(formulaResultDto.getHierarchy())) {
                EvidenceData evidenceData = getEvidenceData(formulaResultDto, lastEvidenceDataMap);
                evidenceDataList.add(evidenceData);
                //如果时错误结果，则还需要记录日志
                if ("-999999.9999".equals(formulaResultDto.getResultValue())) {
                    evidenceErrorLogList.add(this.getEvidenceErrorLog(formulaResultDto));
                }
            } else if (HierarchyEnum.APPLY.getCode().equals(formulaResultDto.getHierarchy())) {
                FinFactorValue finFactorValue = this.getFinFactorValue(formulaResultDto, lastFinFactorValueMap);
                finFactorValueList.add(finFactorValue);
                //如果时错误结果，则还需要记录日志
                if ("-999999.9999".equals(formulaResultDto.getResultValue())) {
                    factorErrorLogList.add(this.getFactorErrorLog(formulaResultDto));
                }
            }
        }
        transactionTemplate.executeWithoutResult(status -> {
            if (CollectionUtils.isEmpty(evidenceDataList)) {
                List<String> codeList = evidenceDataList.stream().map(EvidenceData::getCode).collect(Collectors.toList());
                UpdateWrapper<EvidenceData> evidenceDataUpdateWrapper = new UpdateWrapper<>();
                evidenceDataUpdateWrapper.lambda()
                        .set(EvidenceData::getIsDeleted, "1")
                        .eq(EvidenceData::getEntityCode, entityCode)
                        .eq(EvidenceData::getReportDate, reportDate)
                        .in(EvidenceData::getCode, codeList);
                //删除同期的旧数据
                this.evidenceDataService.update(evidenceDataUpdateWrapper);
                this.evidenceDataService.saveBatch(evidenceDataList);
                //保存错误结果的数据
                this.evidenceErrorLogService.saveBatch(evidenceErrorLogList);
            }
            if (CollectionUtils.isEmpty(finFactorValueList)) {
                List<String> codeList = finFactorValueList.stream().map(FinFactorValue::getFactorCode).collect(Collectors.toList());
                UpdateWrapper<FinFactorValue> finFactorValueUpdateWrapper = new UpdateWrapper<>();
                finFactorValueUpdateWrapper.lambda()
                        .set(FinFactorValue::getIsDeleted, "1")
                        .eq(FinFactorValue::getEntityCode, entityCode)
                        .eq(FinFactorValue::getReportDate, reportDate)
                        .in(FinFactorValue::getFactorCode, codeList);
                //删除同期的旧数据
                this.finFactorValueService.update(finFactorValueUpdateWrapper);
                this.finFactorValueService.saveBatch(finFactorValueList);
                //保存错误结果的数据
                this.factorErrorLogService.saveBatch(factorErrorLogList);
            }
        });
    }


    private Map<String, String> reportDateEvidenceData(String entityCode, LocalDate reportDate) {
        //获取该上报日期的上一年的信息，用来计算变动率(中间层)
        QueryWrapper<EvidenceData> evidenceDataQueryWrapper = new QueryWrapper<>();
        evidenceDataQueryWrapper.lambda()
                .select(EvidenceData::getCode, EvidenceData::getValue)
                .eq(EvidenceData::getEntityCode, entityCode)
                .eq(EvidenceData::getReportDate, reportDate);
        List<EvidenceData> evidenceDataList = this.evidenceDataService.list(evidenceDataQueryWrapper);
        if (CollectionUtils.isEmpty(evidenceDataList)) {
            return new HashMap<>();
        }
        return evidenceDataList.stream().collect(Collectors.toMap(EvidenceData::getCode, EvidenceData::getValue));
    }

    private Map<String, BigDecimal> reportDateFinFactorValue(String entityCode, LocalDate reportDate) {
        //获取该上报日期的上一年的信息，用来计算变动率(指标层)
        QueryWrapper<FinFactorValue> finFactorValueQueryWrapper = new QueryWrapper<>();
        finFactorValueQueryWrapper.lambda()
                .select(FinFactorValue::getFactorCode, FinFactorValue::getFactorValue)
                .eq(FinFactorValue::getEntityCode, entityCode)
                .eq(FinFactorValue::getReportDate, reportDate);
        List<FinFactorValue> oldFinFactorValueList = this.finFactorValueService.list(finFactorValueQueryWrapper);
        //转成MAP，避免多次循环
        if (CollectionUtils.isEmpty(oldFinFactorValueList)) {
            return new HashMap<>();
        }
        return oldFinFactorValueList.stream().collect(Collectors.toMap(FinFactorValue::getFactorCode, FinFactorValue::getFactorValue));

    }

    private EvidenceData getEvidenceData(FormulaResultDto formulaResultDto, Map<String, String> lastEvidenceDataMap) {
        EvidenceData evidenceData = new EvidenceData();
        evidenceData.setCode(formulaResultDto.getFormulaCode());
        evidenceData.setValue(formulaResultDto.getResultValue());
        evidenceData.setIsDeleted("0");
        evidenceData.setCreatedTime(LocalDateTime.now());
        evidenceData.setUpdatedTime(LocalDateTime.now());
        //变动率
        BigDecimal changeRate = null;
        String lastValue = lastEvidenceDataMap.get(formulaResultDto.getFormulaCode());
        boolean isLastErrorResult = "-999999.9999".equals(lastValue);
        boolean isErrorResult = "-999999.9999".equals(formulaResultDto.getResultValue());
        if (!StringUtils.hasLength(lastValue) || "0".equals(lastValue) || isLastErrorResult || isErrorResult) {
            //如果去年或今年是 null 或者 -999999.9999
            changeRate = new BigDecimal("1");
        } else {
            BigDecimal valueTemp = new BigDecimal(evidenceData.getValue());
            BigDecimal lastValueTemp = new BigDecimal(lastValue);
            changeRate = (valueTemp.subtract(lastValueTemp)).divide(lastValueTemp, 2, BigDecimal.ROUND_HALF_UP);
        }
        //校验是否超过值阈
        if (changeRate.compareTo(formulaResultDto.getChangeRateUpper()) > 0) {
            //超过值阈
            evidenceData.setSystemInspection(Boolean.TRUE);
        } else {
            evidenceData.setSystemInspection(Boolean.FALSE);
        }
        evidenceData.setChangeRate(changeRate);
        return evidenceData;
    }

    private FinFactorValue getFinFactorValue(FormulaResultDto formulaResultDto, Map<String, BigDecimal> lastEvidenceDataMap) {
        FinFactorValue finFactorValue = new FinFactorValue();
        finFactorValue.setEntityCode(formulaResultDto.getEntityCode());
        finFactorValue.setFactorCode(formulaResultDto.getFormulaCode());
        finFactorValue.setFactorValue(new BigDecimal(formulaResultDto.getResultValue()));
        finFactorValue.setReportDate(formulaResultDto.getReportDate());
        finFactorValue.setIsDeleted("0");
        finFactorValue.setCreatedTime(LocalDateTime.now());
        finFactorValue.setUpdatedTime(LocalDateTime.now());
        //变动率
        BigDecimal changeRate = null;

        boolean isErrorResult = "-999999.9999".equals(formulaResultDto.getResultValue());
        BigDecimal lastValue = lastEvidenceDataMap.get(formulaResultDto.getFormulaCode());
        boolean isLastErrorResult = "-999999.9999".equals(lastValue);
        if (lastValue == null || BigDecimal.ZERO.compareTo(lastValue) == 0 || isLastErrorResult || isErrorResult) {
            //如果去年或今年是 null 或者 -999999.9999
            changeRate = new BigDecimal("1");
        } else {
            changeRate = (finFactorValue.getFactorValue().subtract(lastValue)).divide(lastValue, 2, BigDecimal.ROUND_HALF_UP);
        }
        //校验是否超过值阈
        if (changeRate.compareTo(formulaResultDto.getChangeRateUpper()) > 0) {
            //超过值阈
            finFactorValue.setSystemInspection(Boolean.TRUE);
        } else {
            finFactorValue.setSystemInspection(Boolean.FALSE);
        }
        finFactorValue.setChangeRate(changeRate);
        return finFactorValue;
    }


    private EvidenceErrorLog getEvidenceErrorLog(FormulaResultDto formulaResultDto) {
        EvidenceErrorLog evidenceErrorLog = new EvidenceErrorLog();
        evidenceErrorLog.setEntityCode(formulaResultDto.getEntityCode());
        evidenceErrorLog.setReportDate(formulaResultDto.getReportDate());
        evidenceErrorLog.setEvidenceCode(formulaResultDto.getFormulaCode());
        evidenceErrorLog.setPrimalFormula(formulaResultDto.getPrimalFormula());
        evidenceErrorLog.setTranslateFormula(formulaResultDto.getTranslateFormula());
        evidenceErrorLog.setIsDeleted("0");
        evidenceErrorLog.setCreatedTime(LocalDateTime.now());
        evidenceErrorLog.setUpdatedTime(LocalDateTime.now());
        return evidenceErrorLog;
    }

    private FactorErrorLog getFactorErrorLog(FormulaResultDto formulaResultDto) {
        FactorErrorLog factorErrorLog = new FactorErrorLog();
        factorErrorLog.setEntityCode(formulaResultDto.getEntityCode());
        factorErrorLog.setReportDate(formulaResultDto.getReportDate());
        factorErrorLog.setFactorCode(formulaResultDto.getFormulaCode());
        factorErrorLog.setPrimalFormula(formulaResultDto.getPrimalFormula());
        factorErrorLog.setTranslateFormula(formulaResultDto.getTranslateFormula());
        factorErrorLog.setIsDeleted("0");
        factorErrorLog.setCreatedTime(LocalDateTime.now());
        factorErrorLog.setUpdatedTime(LocalDateTime.now());
        return factorErrorLog;
    }
}
