package com.deloitte.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.mapper.EntityInfoLogsMapper;
import com.deloitte.crm.domain.EntityInfoLogs;
import com.deloitte.crm.service.EntityInfoLogsService;
import com.deloitte.crm.vo.EntityInfoLogsByBondVo;
import com.deloitte.crm.vo.EntityInfoLogsBySockVo;
import com.deloitte.crm.vo.EntityInfoLogsExpand;
import com.google.common.collect.Maps;
import io.jsonwebtoken.lang.Collections;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * (EntityInfoLogs)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-10 13:55:11
 */
@Slf4j
@Service("entityInfoLogsService")
public class EntityInfoLogsServiceImpl extends ServiceImpl<EntityInfoLogsMapper, EntityInfoLogs> implements EntityInfoLogsService {
    @Resource
    private EntityInfoLogsMapper entityInfoLogsMapper;

    /**
     * 根据类型查询
     * CompletableFuture 使用线程池为ForkJoinPool
     * {@link java.util.concurrent.ForkJoinPool}
     * TODO 后期优化
     * @param findType 查询类型
     * @return 股票 & 债券
     * @see EntityInfoLogs#operType
     */
    @SneakyThrows
    @Override
    public Object findAllByType(String findType) {
        if (findType.equals(Common.TYPE_BOND)) {
            CompletableFuture<EntityInfoLogsByBondVo> baskTask = CompletableFuture.supplyAsync(() -> {
                EntityInfoLogsByBondVo sockVo = new EntityInfoLogsByBondVo();
                final List<EntityInfoLogs> infoLogs = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}));
                sockVo.setEntityInfoLogs(infoLogs);
                return sockVo;
            });
            final CompletableFuture<Void> getAddTodayCountTask = baskTask.thenAcceptAsync(e -> {
                String nowStrDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                final List<EntityInfoLogs> totalLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}).eq(EntityInfoLogs::getCreateTime, nowStrDate));
                e.setAddToday(totalLists.size());
            });
            final CompletableFuture<Void> otherTask = baskTask.thenAcceptAsync(e1 -> {
                //获取当前日期:nowDayDate 当前日期往前7天的日期:sevenTradingDayDate 当前日期最近一个月的日期:latestMonthDayDate 格式为 yyyy-MM-dd
                String nowDayDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                String sevenTradingDayDate = DateUtil.format(DateUtil.offsetDay(new Date(), -7), DatePattern.NORM_DATE_PATTERN);
                String latestMonthDayDate = DateUtil.format(DateUtil.lastMonth(), DatePattern.NORM_DATE_PATTERN);
                // sevenTradingDayDate <= createTime <= nowDayDate  查询前7天的排除周六周天的数据 算平均数
                List<EntityInfoLogs> sevenTradingtotalListsFilter = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}).ge(EntityInfoLogs::getCreateTime, sevenTradingDayDate).le(EntityInfoLogs::getCreateTime, nowDayDate)).stream().filter(e -> !DateUtil.isWeekend(e.getCreateTime())).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(sevenTradingtotalListsFilter) && sevenTradingtotalListsFilter.size() >= 1) {
                    double x = Long.valueOf(sevenTradingtotalListsFilter.size()).doubleValue();
                    Long betweenDays = DateUtil.between(DateUtil.offsetDay(new Date(), -7), new Date(), DateUnit.DAY);
                    final double y = betweenDays.doubleValue();
                    final double div = NumberUtil.div(y, x);
                    e1.setSevenTradingDayAverages(String.valueOf(div));
                }
                // latestMonthDayDate <= createTime <= nowDayDate  当前日期最近一个月的日期 算平均数
                List<EntityInfoLogs> latestMonthDayDateFilter = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}).ge(EntityInfoLogs::getCreateTime, latestMonthDayDate).le(EntityInfoLogs::getCreateTime, nowDayDate));
                if (CollUtil.isNotEmpty(latestMonthDayDateFilter) && latestMonthDayDateFilter.size() >= 1) {
                    double x = Long.valueOf(latestMonthDayDateFilter.size()).doubleValue();
                    Long betweenDays = DateUtil.between(DateUtil.lastMonth(), new Date(), DateUnit.DAY);
                    final double y = betweenDays.doubleValue();
                    final double div = NumberUtil.div(y, x);
                    e1.setAverageDailyLatestMonth(String.valueOf(div));
                }
                HashMap<String, Object> resultMap = Maps.newHashMap();
                //拼接7天组装图
                final Map<String, List<EntityInfoLogsExpand>> collect = latestMonthDayDateFilter.stream().map(e -> {
                    final EntityInfoLogsExpand entityInfoLogsExpand = BeanUtil.copyProperties(e, EntityInfoLogsExpand.class);
                    entityInfoLogsExpand.setCreateTimeStr(DateUtil.format(e.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                    return entityInfoLogsExpand;
                }).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(EntityInfoLogsExpand::getCreateTimeStr));
                collect.forEach((k, v) -> {
                    final long count = v.stream().count();
                    resultMap.put(k, count);
                });
                e1.setCylinderDatas(resultMap);
            });
            CompletableFuture.allOf(baskTask, getAddTodayCountTask, otherTask).exceptionally((ex) -> {
                log.error(" 发券企业-历史记录 页面出现异常:[{}]", ex);
                throw new ServiceException("发券企业-历史记录数据组装组装异常");
            }).join();
            log.info("发券企业-历史记录>>  结束:{} ", JSON.toJSONString(baskTask.get()));
            return baskTask.get();
        } else if (Common.TYPE_STOCK.equals(findType)) {
            CompletableFuture<EntityInfoLogsBySockVo> baskTask = CompletableFuture.supplyAsync(() -> {
                EntityInfoLogsBySockVo sockVo = new EntityInfoLogsBySockVo();
                List<EntityInfoLogs> entoty = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}));
                sockVo.setEntityInfoLogs(entoty);
                return sockVo;
            });
            final CompletableFuture<Void> getAddTodayCountTask = baskTask.thenAcceptAsync(e -> {
                String nowStrDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                final List<EntityInfoLogs> totalLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}).eq(EntityInfoLogs::getCreateTime, nowStrDate));
                e.setAddToday(totalLists.size());
            });
            final CompletableFuture<Void> otherTask = baskTask.thenAcceptAsync(e1 -> {
                String nowStrDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                String oldTime = DateUtil.format(DateUtil.offsetDay(new Date(), -7), DatePattern.NORM_DATE_PATTERN);
                String lastMonth = DateUtil.format(DateUtil.lastMonth(), DatePattern.NORM_DATE_PATTERN);
                List<EntityInfoLogs> sevenTradingDayAveragesEntityInfoLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}).ge(EntityInfoLogs::getCreateTime, oldTime).le(EntityInfoLogs::getCreateTime, nowStrDate)).stream().filter(e -> !DateUtil.isWeekend(e.getCreateTime())).collect(Collectors.toList());
                List<EntityInfoLogs> lastMothEntityInfoLogsLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}).ge(EntityInfoLogs::getCreateTime, lastMonth).le(EntityInfoLogs::getCreateTime, nowStrDate));
                int lastMothtotalSize = lastMothEntityInfoLogsLists.size();
                if (lastMothtotalSize >= 1) {
                    Long betweenDays = DateUtil.between(DateUtil.lastMonth(), new Date(), DateUnit.DAY);
                    double betweenDaysDouble = betweenDays.doubleValue();
                    double lastMothtotalSizeDouble = Long.valueOf(lastMothtotalSize).doubleValue();
                    double averageDailyLatestMonth = NumberUtil.div(betweenDaysDouble, lastMothtotalSizeDouble);
                    e1.setAverageDailyLatestMonth(averageDailyLatestMonth + "");
                }
                final Map<String, List<EntityInfoLogsExpand>> collect = sevenTradingDayAveragesEntityInfoLists.stream().map(e -> {
                    final EntityInfoLogsExpand entityInfoLogsExpand = BeanUtil.copyProperties(e, EntityInfoLogsExpand.class);
                    entityInfoLogsExpand.setCreateTimeStr(DateUtil.format(e.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                    return entityInfoLogsExpand;
                }).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(EntityInfoLogsExpand::getCreateTimeStr));
                final HashMap<String, Object> resultMap = Maps.newHashMap();
                collect.forEach((k, v) -> {
                    final long count = v.stream().count();
                    resultMap.put(k, count);
                });
                e1.setCylinderDatas(resultMap);
            });
            CompletableFuture.allOf(baskTask, getAddTodayCountTask, otherTask).exceptionally((ex) -> {
                log.error(" 企业主体-历史记录 页面出现异常:[{}]", ex);
                throw new ServiceException("企业主体-历史记录数据组装组装异常");
            }).join();
            log.info("企业主体-历史记录>>  结束:{} ", JSON.toJSONString(baskTask.get()));
            return baskTask.get();
        } else {
            throw new ServiceException("参数非法");
        }
    }
}
