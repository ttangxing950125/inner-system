package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.shaded.com.google.common.base.Objects;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.dto.KpiHoursDto;
import com.deloitte.additional.recording.dto.KpiWorkCalDto;
import com.deloitte.additional.recording.dto.KpiWorkFillDto;
import com.deloitte.additional.recording.mapper.KpiWorkFillMapper;
import com.deloitte.additional.recording.mapper.SysGroupMapper;
import com.deloitte.additional.recording.mapper.SysGroupUserMapper;
import com.deloitte.additional.recording.service.KpiWorkFillService;
import com.deloitte.additional.recording.util.MetaSecurity;
import com.deloitte.additional.recording.vo.group.SysGroupSelectVo;
import com.deloitte.additional.recording.vo.kpi.*;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author PenTang
 * @date 2022/11/24 15:04
 */
@Service("KpiWorkFillService")
@Slf4j
public class KpiWorkFillServiceImpl extends ServiceImpl<KpiWorkFillMapper, KpiWorkFill> implements KpiWorkFillService {
    @Resource
    private KpiWorkFillMapper kpiWorkFillMapper;

    @Resource
    private SysGroupUserMapper sysGroupUserMapper;

    @Resource
    private SysGroupMapper sysGroupMapper;

    @Override
    public R getKpiWorkView(String Date) {
        //获取当前登录用户
        SysUser loginUser = MetaSecurity.getLoginUser();
        Integer id = loginUser.getId();
        LambdaQueryWrapper<KpiWorkFill> queryWrapper = new LambdaQueryWrapper<KpiWorkFill>().eq(KpiWorkFill::getUserId, id);
        if (!StrUtil.equals(Date, null)) {
            String startDate = Date + "-01";
            LocalDate today = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDay = today.with(TemporalAdjusters.lastDayOfMonth());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String endTime = endDay.format(formatter);
            queryWrapper.between(KpiWorkFill::getWorkDay, startDate, endTime);
        }


        List<KpiWorkFill> kpiWorkFills = kpiWorkFillMapper.selectList(queryWrapper);
        KpiWorkViewVo kpiWorkViewVo = new KpiWorkViewVo();
        //获取当前月第一天
        int i1 = DateUtil.beginOfMonth(new Date()).dayOfMonth();
        int i2 = DateUtil.endOfMonth(new Date()).dayOfMonth();
        Integer i = (i2 - i1) + 1;
        //计算当月有多少天
        kpiWorkViewVo.setKpiWorkFills(kpiWorkFills);
        kpiWorkViewVo.setDateCount(i);
        return R.ok(kpiWorkViewVo);
    }

    @Override
    public R getKpiWorkView(String date, Integer id, String userName) {

        String startDate = date + "-01";
        LocalDate today = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDay = today.with(TemporalAdjusters.lastDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endTime = endDay.format(formatter);
        LambdaQueryWrapper<KpiWorkFill> queryWrapper = new LambdaQueryWrapper<KpiWorkFill>().eq(KpiWorkFill::getUserId, id).eq(KpiWorkFill::getStatus, 1).between(KpiWorkFill::getWorkDay, startDate, endTime);
        List<KpiWorkFill> kpiWorkFills = kpiWorkFillMapper.selectList(queryWrapper);
        List<KpiWorkAroVo> kpiWorkAroVos1 = BeanUtil.copyToList(kpiWorkFills, KpiWorkAroVo.class);
        for (KpiWorkAroVo kpiWorkAroVo : kpiWorkAroVos1) {
            kpiWorkAroVo.setUserName(userName);
        }
        return R.ok(kpiWorkAroVos1);

    }

    @Override
    public R aprKpiWorkView(KpiWorkFill KpiWorkFill) {

        boolean b = updateById(KpiWorkFill);
        if (b) {
            return R.ok(true);
        } else {
            return R.ok(false);
        }

    }

    @Override
    public R kpiWorkSubmit(KpiWorkFillDto kpiWorkFillDto) {
        //计算工时
        KpiHoursDto kpiHoursDto = BeanUtil.copyProperties(kpiWorkFillDto, KpiHoursDto.class, "id", "userId", "status", "totalHour", "workDate", "kMonth", "kYear");
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(kpiHoursDto);
        Collection<Object> values = stringObjectMap.values();
        //过滤掉为null的value：
        List<Object> collect = values.stream().filter(row -> !Objects.equal(row, null)).collect(Collectors.toList());
        ArrayList<Double> doubles = new ArrayList<>();
        collect.stream().forEach(row -> {
            double v = Double.parseDouble(row.toString());
            doubles.add(v);
        });
        Long count = doubles.stream().filter(row -> Double.doubleToLongBits(row) == Double.doubleToLongBits(1.0)).count();
        double v = count.doubleValue();
        kpiWorkFillDto.setTotalHour(v);
        //取出当时间
        Date workDate = DateUtil.parseDate(kpiWorkFillDto.getWorkDay());
        kpiWorkFillDto.setKYear(cn.hutool.core.date.DateUtil.year(workDate));
        kpiWorkFillDto.setKMonth(DateUtil.month(workDate) + 1);
        KpiWorkFill kpiWorkFill = BeanUtils.copyEntity(kpiWorkFillDto, KpiWorkFill.class);
        kpiWorkFill.setWorkDay(workDate);
        //入库操作
        boolean b = saveOrUpdate(kpiWorkFill);

        return R.ok(b, "操作成功");

    }

    @Override
    public R KpiWorkCal(KpiWorkCalDto kpiWorkCalDto) {
        //index = (currentPage-1) * pageSize
        List<SysGroupUser> sysGroupUsers = null;
        if (kpiWorkCalDto.getGroupId() != null) {
            LambdaQueryWrapper<SysGroupUser> eq = new LambdaQueryWrapper<SysGroupUser>().eq(SysGroupUser::getGroupId, kpiWorkCalDto.getGroupId());
            sysGroupUsers = sysGroupUserMapper.selectList(eq);
        }
        List<KpiWorkCalVo> kpiWorkCal = kpiWorkFillMapper.getKpiWorkCal(sysGroupUsers, kpiWorkCalDto);

        ArrayList<KpiWorkCalViewVo> kpiWorkCalViewVos = new ArrayList<KpiWorkCalViewVo>();
        KpiWorkScoreVoByPage kpiWorkScoreVoByPage = null;
        //按日统计
        if (kpiWorkCalDto.getStatistical() == 1) {
             kpiWorkScoreVoByPage = this.CalByDay(kpiWorkCal, kpiWorkCalDto, kpiWorkCalViewVos);
        }
        //按月统计
        if (kpiWorkCalDto.getStatistical() == 2) {
            kpiWorkScoreVoByPage = this.CalByMonth(kpiWorkCal, kpiWorkCalDto, kpiWorkCalViewVos);

        } 

        return R.ok(kpiWorkScoreVoByPage);
    }

    public KpiWorkScoreVoByPage CalByDay(List<KpiWorkCalVo> kpiWorkCal, KpiWorkCalDto kpiWorkCalDto, ArrayList<KpiWorkCalViewVo> kpiWorkCalViewVos) {
        Map<Integer, Map<Date, List<KpiWorkCalVo>>> collect1 = kpiWorkCal.stream().collect(
                Collectors.groupingBy(KpiWorkCalVo::getUserId,
                        Collectors.groupingBy(KpiWorkCalVo::getWorkDay)));

        Set<Integer> userIds = collect1.keySet();
        userIds.stream().forEach(userid -> {
            Map<Date, List<KpiWorkCalVo>> dateListMap = collect1.get(userid);
            Set<Date> dates = dateListMap.keySet();
            dates.stream().forEach(day -> {
                List<KpiWorkCalVo> kpiWorkCalVos = dateListMap.get(day);
                KpiWorkCalViewVo kpiWorkCalViewVo = new KpiWorkCalViewVo();
                if (kpiWorkCalDto.getGroupId() != null) {
                    SysGroup sysGroup = sysGroupMapper.selectOne(new LambdaQueryWrapper<SysGroup>().eq(SysGroup::getId, kpiWorkCalDto.getGroupId()));
                    kpiWorkCalViewVo.setGroupId(sysGroup.getId());
                    kpiWorkCalViewVo.setGroupName(sysGroup.getGroupName());
                } else {
                    LambdaQueryWrapper<SysGroupUser> eq = new LambdaQueryWrapper<SysGroupUser>().eq(SysGroupUser::getUserId, userid);
                    List<SysGroupUser> sysGroupUsers1 = sysGroupUserMapper.selectList(eq);
                    SysGroup sysGroup = sysGroupMapper.selectOne(new LambdaQueryWrapper<SysGroup>().eq(SysGroup::getId, sysGroupUsers1.get(0).getGroupId()));
                    kpiWorkCalViewVo.setGroupId(sysGroup.getId());
                    kpiWorkCalViewVo.setGroupName(sysGroup.getGroupName());
                }
                kpiWorkCalViewVo.setUserId(userid);
                kpiWorkCalViewVo.setUserName(kpiWorkCalVos.get(0).getUserName());
                kpiWorkCalViewVo.setTotalHour(kpiWorkCalVos.stream().mapToDouble(KpiWorkCalVo::getTotalHour).sum());
                kpiWorkCalViewVo.setWorkDay(DateUtil.parseDateToStr("YYYY-MM-DD", day));
                kpiWorkCalViewVos.add(kpiWorkCalViewVo);

            });
        });
        //当前第几页
        int pageNo = kpiWorkCalDto.getPageNum();
        //一页五条
        int pageSize = kpiWorkCalDto.getPageSize();
        //总数
        int total = kpiWorkCalViewVos.size();
        //总页数
        int pageSum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        //分页
        List<KpiWorkCalViewVo> collect = kpiWorkCalViewVos.stream().skip((pageNo - 1) * pageSize).limit(pageSize).
                collect(Collectors.toList());
        KpiWorkScoreVoByPage kpiWorkScoreVoByPage = new KpiWorkScoreVoByPage();
        kpiWorkScoreVoByPage.setKpiWorkScoreVoList(collect);
        kpiWorkScoreVoByPage.setTotal(total);
        return kpiWorkScoreVoByPage;
    }

    public KpiWorkScoreVoByPage CalByMonth(List<KpiWorkCalVo> kpiWorkCal, KpiWorkCalDto kpiWorkCalDto, ArrayList<KpiWorkCalViewVo> kpiWorkCalViewVos) {
        //结构{userid：{year：{mouth：{}}}}
        Map<Integer, Map<Integer, Map<Integer, List<KpiWorkCalVo>>>> collect1 = kpiWorkCal.stream().collect(
                Collectors.groupingBy(KpiWorkCalVo::getUserId,
                        Collectors.groupingBy(KpiWorkCalVo::getKYear,
                                Collectors.groupingBy(KpiWorkCalVo::getKMonth))));
        //取出所有的userIds
        Set<Integer> userIds = collect1.keySet();
        userIds.stream().forEach(userId -> {
            Map<Integer, Map<Integer, List<KpiWorkCalVo>>> integerMapMap = collect1.get(userId);
            Set<Integer> years = integerMapMap.keySet();
            years.stream().forEach(year -> {
                Map<Integer, List<KpiWorkCalVo>> integerListMap = integerMapMap.get(year);
                Set<Integer> mouths = integerListMap.keySet();
                mouths.stream().forEach(mouth -> {
                    List<KpiWorkCalVo> kpiWorkCalVos = integerListMap.get(mouth);
                    KpiWorkCalViewVo kpiWorkCalViewVo = new KpiWorkCalViewVo();
                    if (kpiWorkCalDto.getGroupId() != null) {
                        SysGroup sysGroup = sysGroupMapper.selectOne(new LambdaQueryWrapper<SysGroup>().eq(SysGroup::getId, kpiWorkCalDto.getGroupId()));
                        kpiWorkCalViewVo.setGroupId(sysGroup.getId());
                        kpiWorkCalViewVo.setGroupName(sysGroup.getGroupName());
                    } else {
                        LambdaQueryWrapper<SysGroupUser> eq = new LambdaQueryWrapper<SysGroupUser>().eq(SysGroupUser::getUserId, userId);
                        List<SysGroupUser> sysGroupUsers1 = sysGroupUserMapper.selectList(eq);
                        SysGroup sysGroup = sysGroupMapper.selectOne(new LambdaQueryWrapper<SysGroup>().eq(SysGroup::getId, sysGroupUsers1.get(0).getGroupId()));
                        kpiWorkCalViewVo.setGroupId(sysGroup.getId());
                        kpiWorkCalViewVo.setGroupName(sysGroup.getGroupName());
                    }

                    kpiWorkCalViewVo.setUserId(userId);
                    kpiWorkCalViewVo.setUserName(kpiWorkCalVos.get(0).getUserName());
                    kpiWorkCalViewVo.setTotalHour(kpiWorkCalVos.stream().mapToDouble(KpiWorkCalVo::getTotalHour).sum());
                    kpiWorkCalViewVo.setWorkDay(year + "-" + mouth);
                    kpiWorkCalViewVos.add(kpiWorkCalViewVo);
                });
            });

        });

        //当前第几页
        int pageNo = kpiWorkCalDto.getPageNum();
        //一页五条
        int pageSize = kpiWorkCalDto.getPageSize();
        //总数
        int total = kpiWorkCalViewVos.size();
        //总页数
        int pageSum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        //分页
        List<KpiWorkCalViewVo> collect = kpiWorkCalViewVos.stream().skip((pageNo - 1) * pageSize).limit(pageSize).
                collect(Collectors.toList());
        KpiWorkScoreVoByPage kpiWorkScoreVoByPage = new KpiWorkScoreVoByPage();
        kpiWorkScoreVoByPage.setKpiWorkScoreVoList(collect);
        kpiWorkScoreVoByPage.setTotal(total);
        return kpiWorkScoreVoByPage;
    }
}
