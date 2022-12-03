package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.CommonByKpi;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.deloitte.additional.recording.domain.KpiWorkScore;
import com.deloitte.additional.recording.dto.KpiWorkScoreDto;
import com.deloitte.additional.recording.mapper.KpiWorkScoreMapper;
import com.deloitte.additional.recording.service.KpiWorkScoreService;
import com.deloitte.additional.recording.vo.kpi.KpiWorkScoreVo;
import com.deloitte.additional.recording.vo.kpi.KpiWorkScoreVoByH;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author PenTang
 * @date 2022/11/28 11:11
 */
@Slf4j
@Service("KpiWorkScoreService")
public class KpiWorkScoreServiceImpl extends ServiceImpl<KpiWorkScoreMapper, KpiWorkScore> implements KpiWorkScoreService {

    @Resource
    private KpiWorkScoreMapper mapper;

    /**
     * 实习生工作成果按天
     *
     * @return List<KpiWorkScoreVo>
     * @author penTang
     * @date 2022/11/28 14:19
     */
    @Override
    public R getKpiWorkScore(KpiWorkScoreDto kpiWorkScoreDto) {
        ArrayList<KpiWorkScoreVo> kpiWorkScoreVos = new ArrayList<>();
        List<KpiWorkScoreVo> kpiWorkScore1 = mapper.getKpiWorkScore(kpiWorkScoreDto);
        if (StrUtil.equals(kpiWorkScoreDto.getStatistical(), CommonByKpi.HOURS)) {
            KpiWorkScoreVoByH kpiWorkScoreVoByH = new KpiWorkScoreVoByH();
            KpiWorkScoreVo kpiWorkScoreVo = new KpiWorkScoreVo();
            kpiWorkScoreVoByH.setKpiWorkScoreVoList(kpiWorkScore1);
            List<KpiWorkScoreVo> collect1 = kpiWorkScore1.stream().filter(temp -> temp != null).collect(Collectors.toList());
            //当前人在当前日期补录总量
            int RecordTotal = collect1.stream().mapToInt(KpiWorkScoreVo::getRecordTotal).sum();
            kpiWorkScoreVo.setRecordTotal(RecordTotal);
            //当前人在当前日期审核通过总量
            int CheckTotal = collect1.stream().mapToInt(KpiWorkScoreVo::getCheckTotal).sum();
            kpiWorkScoreVo.setCheckTotal(CheckTotal);
            //打回总量
            int RepulseTotal = collect1.stream().mapToInt(KpiWorkScoreVo::getRepulseTotal).sum();
            kpiWorkScoreVo.setRepulseTotal(RepulseTotal);
            //得分总数
            int Score = collect1.stream().mapToInt(KpiWorkScoreVo::getScore).sum();
            kpiWorkScoreVo.setEmptyTotal(Score);
            //当天的工时
            kpiWorkScoreVo.setHours(collect1.size());
            kpiWorkScoreVoByH.setKpiWorkScoreVo(kpiWorkScoreVo);
            return R.ok(kpiWorkScoreVoByH);
        } else if (StrUtil.equals(kpiWorkScoreDto.getStatistical(), CommonByKpi.DAY)) {
            //由于年份与月份为必选项 所以根据user_id 分组
            Map<Integer, Map<Date, List<KpiWorkScoreVo>>> collect = kpiWorkScore1.stream().collect(Collectors.groupingBy(KpiWorkScoreVo::getUserId, Collectors.groupingBy(KpiWorkScoreVo::getWorkDay)));
            Set<Integer> integers = collect.keySet();
            integers.stream().forEach(row -> {
                        //当前人的所有工作日期
                        Map<Date, List<KpiWorkScoreVo>> dateListMap = collect.get(row);
                        Set<Date> dates = dateListMap.keySet();
                        dates.forEach(day -> {
                            KpiWorkScoreVo kpiWorkScoreVo = new KpiWorkScoreVo();
                            List<KpiWorkScoreVo> kpiWorkScoreVos1 = dateListMap.get(day);
                            kpiWorkScoreVo.setUserId(row);
                            kpiWorkScoreVo.setWorkDay(day);
                            //过滤掉为空的
                            List<KpiWorkScoreVo> collect1 = kpiWorkScoreVos1.stream().filter(temp -> temp != null).collect(Collectors.toList());
                            //当前人在当前日期补录总量
                            int RecordTotal = collect1.stream().mapToInt(KpiWorkScoreVo::getRecordTotal).sum();
                            kpiWorkScoreVo.setRecordTotal(RecordTotal);
                            //当前人在当前日期审核通过总量
                            int CheckTotal = collect1.stream().mapToInt(KpiWorkScoreVo::getCheckTotal).sum();
                            kpiWorkScoreVo.setCheckTotal(CheckTotal);
                            //打回总量
                            int RepulseTotal = collect1.stream().mapToInt(KpiWorkScoreVo::getRepulseTotal).sum();
                            kpiWorkScoreVo.setRepulseTotal(RepulseTotal);
                            //补录数据为空
                            int sum = collect1.stream().mapToInt(KpiWorkScoreVo::getEmptyTotal).sum();
                            kpiWorkScoreVo.setEmptyTotal(sum);
                            //得分总数
                            int Score = collect1.stream().mapToInt(KpiWorkScoreVo::getScore).sum();
                            kpiWorkScoreVo.setScore(Score);
                            //当天的工时
                            kpiWorkScoreVo.setHours(collect1.size());
                            kpiWorkScoreVos.add(kpiWorkScoreVo);

                        });
                    }
            );
            return R.ok(kpiWorkScoreVos);
        }
        return  null;
    }


}
