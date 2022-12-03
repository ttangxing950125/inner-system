package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.PrsQualData;
import com.deloitte.additional.recording.domain.PrsQualDataTask;
import com.deloitte.additional.recording.mapper.PrsQualDataTaskMapper;
import com.deloitte.additional.recording.service.PrsQualDataService;
import com.deloitte.additional.recording.service.PrsQualDataTaskService;
import com.deloitte.additional.recording.util.MathUtils;
import com.deloitte.common.core.constant.Constants;
import com.deloitte.common.core.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @创建人 tangx
 * @创建时间 2022/11/30
 * @描述 BasEvdTaskData实体 业务处理层
 */
@Service("prsQualDataTaskServiceImpl")
public class PrsQualDataTaskServiceImpl extends ServiceImpl<PrsQualDataTaskMapper, PrsQualDataTask> implements PrsQualDataTaskService {


    @Autowired
    private PrsQualDataService prsQualDataService;

    @Override
    public List<PrsQualDataTask> findByQualCode(String qualCode) {
        return lambdaQuery().eq(PrsQualDataTask::getQualCode, qualCode).list();
    }


    @Override
    public PrsQualDataTask calculationTask(String qualCode, String dataYear) {
        //获取数据 排除字符类型
        List<PrsQualData> list = prsQualDataService.listByCodeAndTimeAndValueIsNotNull(qualCode, dataYear);
        removeContainChinese(list);
        //计算缺失率
        //1计算出主体总数
        long entityCount = prsQualDataService.countEntity(qualCode, dataYear);
        //缺失数
        long lose = prsQualDataService.countLose(qualCode, dataYear);
        BigDecimal loseRate = getRate(entityCount, lose);
        //最大值
        PrsQualData maxData = list.stream().max(Comparator.comparing(PrsQualData::getQualValue)).get();
        String max = maxData.getQualValue();
        //最小值
        PrsQualData minData = list.stream().min(Comparator.comparing(PrsQualData::getQualValue)).get();
        String min = minData.getQualValue();
        //获得所有的qual_value
        List<String> strings = list.stream().map(PrsQualData::getQualValue).collect(Collectors.toList());
        Collections.sort(strings);
        AtomicReference<BigDecimal> average = new AtomicReference<>(BigDecimal.ZERO);
        strings.forEach(value -> {
            BigDecimal bigDecimal = new BigDecimal(value);
            average.set(average.get().add(bigDecimal));
        });
        //平均值
        BigDecimal total = average.get();
        BigDecimal averageValue = total.divide(new BigDecimal(list.size()));
        //中位数
        String median = MathUtils.getMedianBigDecimal(strings);
        //众数 可能有多个
        String modes = MathUtils.mode(strings).toString();
        //方差 s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)
        String variance = MathUtils.variance(strings);
        //标准差
        String deviation = MathUtils.standardDeviation(variance);
        //获得集合长度
        int size = strings.size();
        //5%分为数
        int index_5 = MathUtils.getIndex(size, Constants.PERCENT5);
        String percent5 = strings.get(index_5);
        //25%分为数
        int index_25 = MathUtils.getIndex(size, Constants.PERCENT25);
        String percent25 = strings.get(index_25);
        //50%分为数
        int index_50 = MathUtils.getIndex(size, Constants.PERCENT50);
        String percent50 = strings.get(index_50);
        //75%分为数
        int index_75 = MathUtils.getIndex(size, Constants.PERCENT75);
        String percent75 = strings.get(index_75);
        //95%分为数
        int index_95 = MathUtils.getIndex(size, Constants.PERCENT95);
        String percent95 = strings.get(index_95);
        //组装数据
        return new PrsQualDataTask().init(qualCode, "T", loseRate.toString(), max, min, median, modes, variance, deviation, percent5, percent25, percent50, percent75, percent95);
    }

    /**
     * 排除集合中 qual_value包含中文的
     *
     * @param list
     */
    private static void removeContainChinese(List<PrsQualData> list) {
        //排除掉qual_value为中文的
        Iterator<PrsQualData> iterator = list.iterator();
        while (iterator.hasNext()) {
            PrsQualData qualData = iterator.next();
            String qualValue = qualData.getQualValue();
            if ("".equals(qualValue)) {
                iterator.remove();
            }
            //是否为中文
            if (StrUtil.isContainChinese(qualValue)) {
                iterator.remove();
            }
        }
    }

    private BigDecimal getRate(long entityTotal, long count) {

        BigDecimal total = new BigDecimal(entityTotal);
        BigDecimal entityCount = new BigDecimal(count);

        return entityCount.divide(total).setScale(2, RoundingMode.HALF_UP);//保留两位小数
    }


}
