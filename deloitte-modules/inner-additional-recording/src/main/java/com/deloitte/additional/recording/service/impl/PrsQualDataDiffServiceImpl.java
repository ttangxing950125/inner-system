package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.BasEvdTaskData;
import com.deloitte.additional.recording.domain.PrsModelQualFactor;
import com.deloitte.additional.recording.domain.PrsQualDataDiff;
import com.deloitte.additional.recording.dto.BasEvdTaskInfoStatusCountDto;
import com.deloitte.additional.recording.mapper.PrsQualDataDiffMapper;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.vo.BasEvdTaskDataVO;
import com.deloitte.additional.recording.vo.qual.PrsQualDataDiffPageVO;
import com.deloitte.additional.recording.vo.qual.PrsQualDataPanelVO;
import com.deloitte.common.core.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/28
 * @描述 指标差异 业务处理层
 */
@Service
public class PrsQualDataDiffServiceImpl extends ServiceImpl<PrsQualDataDiffMapper, PrsQualDataDiff> implements PrsQualDataDiffService {


    @Autowired
    private PrsQualDataService qualDataService;

    @Autowired
    private PrsModelQualFactorService factorService;

    @Autowired
    private BasEvdTaskInfoService taskInfoService;

    @Autowired
    private BasEvdTaskDataService basEvdTaskDataService;


    @Override
    public List<PrsQualDataDiffPageVO> findByQual(String qualCode) {


        return this.baseMapper.findByQual(qualCode);
    }

    /**
     * 指标下 主体统计 分布面板
     *
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return 统计数据
     * 1、统计对应指标和对应年份下主体总数量
     * 2、获得对应指标下的所有挡位
     * 3、统计对应挡位下主体数量，并计算出占比
     */
    @Override
    public Map<String, PrsQualDataPanelVO> distributionPanel(String qualCode, String dataYear) {
        //主体数量统计
        long entityTotal = qualDataService.countEntity(qualCode, dataYear);
        //得到所有挡位相关信息
        Map<String, PrsQualDataPanelVO> map = new HashMap<>();
        List<PrsModelQualFactor> factorList = factorService.findByQualCode(qualCode);
        if (factorList != null) {
            for (PrsModelQualFactor factor : factorList) {
                //查询每个挡位的数量
                long count = qualDataService.countByValue(qualCode, dataYear, factor.getFactorValue());
                PrsQualDataPanelVO panelVO = new PrsQualDataPanelVO();
                panelVO.setEntityCount(count);
                panelVO.setRate(getRate(entityTotal, count));
                map.put(factor.getFactorValue(), panelVO);
            }
            //剩下的则为确实的 null he-999999.9999都是缺失的
            long lose = qualDataService.countLose(qualCode, dataYear);
            BigDecimal rate = getRate(entityTotal, lose);
            PrsQualDataPanelVO panelVO = new PrsQualDataPanelVO();
            panelVO.setEntityCount(lose);
            panelVO.setRate(getRate(entityTotal, lose));
            map.put("缺失", panelVO);
            return map;
        }

        return null;
    }

    /**
     * 1统计当前指标下的主体任务总数量
     * 2 分别统计任务不同状态下的数量
     * 3计算出对应的比例
     * <p>
     * * 0	待分配
     * * 1	补录中
     * * 2	审核打回
     * * 3	审核中
     * * 4	审核通过
     * *
     * * 审核通过=审核通过
     * * 审核中=审核中
     * * 其他=采集中
     */
    @Override
    public Map<String, BigDecimal> progressPanel(String qualCode, String dataYear) {
        //获得任务总的数量
        Integer taskCount = taskInfoService.taskCount(qualCode, dataYear);
        //得到不同的状态的任务总量
        List<BasEvdTaskInfoStatusCountDto> countDtos = taskInfoService.taskCountByStatus(qualCode, dataYear);
        if (countDtos != null) {
            Map<String, BigDecimal> map = new HashMap<>();
            for (BasEvdTaskInfoStatusCountDto dto : countDtos) {
                //判断相同状态下是否为空 不为空就做加法
                BigDecimal decimal = map.get(getStatusStr(dto.getStatus()));
                if (decimal != null) {
                    BigDecimal add = decimal.add(getRate(taskCount, dto.getCount()));
                    map.put(getStatusStr(dto.getStatus()), add);
                } else {
                    map.put(getStatusStr(dto.getStatus()), getRate(taskCount, dto.getCount()));
                }
            }
            return map;
        }
        return null;
    }

    @Override
    public List<BasEvdTaskDataVO> basePanel(String qualCode) {
        List<BasEvdTaskData> evdTaskData = basEvdTaskDataService.findByQualCode(qualCode);
        if (evdTaskData != null) {
            return BeanUtils.copy(evdTaskData, BasEvdTaskDataVO.class);
        }
        return null;
    }


    private String getStatusStr(Integer status) {

        String statusStr = null;
        switch (status) {
            case 1:
                statusStr = "采集中";
                break;
            case 2:
                statusStr = "采集中";
                break;
            case 3:
                statusStr = "审核中";
                break;
            case 4:
                statusStr = "审核完成";
                break;
            default:
                statusStr = "采集中";
        }

        return statusStr;
    }

    private BigDecimal getRate(long entityTotal, long count) {

        BigDecimal total = new BigDecimal(entityTotal);
        BigDecimal entityCount = new BigDecimal(count);

        return entityCount.divide(total).setScale(2, RoundingMode.HALF_UP);//保留两位小数
    }

    private BigDecimal getRate(Integer entityTotal, Integer count) {

        BigDecimal total = new BigDecimal(entityTotal);
        BigDecimal entityCount = new BigDecimal(count);

        return entityCount.divide(total).setScale(2, RoundingMode.HALF_UP);//保留两位小数
    }
}
