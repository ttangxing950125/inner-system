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
     *
     * @param findType 查询类型
     * @return 股票 & 债券
     * @see EntityInfoLogs#operType
     */
    @SneakyThrows
    @Override
    public Object findAllByType(String findType) {
        List<EntityInfoLogs> entityInfoLogs = new ArrayList<>();
        if (findType.equals(Common.TYPE_BOND)) {
            entityInfoLogs = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}));
        } else if (Common.TYPE_STOCK.equals(findType)) {
            /**
             * CompletableFuture 使用线程池为ForkJoinPool
             * {@link java.util.concurrent.ForkJoinPool}
             */
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
                //最近7天向上偏移
                String oldTime = DateUtil.format(DateUtil.offsetDay(new Date(), -7), DatePattern.NORM_DATE_PATTERN);
                //最近一个月 向上偏移 工具类直接用当前日期到上个月的这一天
                String lastMonth = DateUtil.format(DateUtil.lastMonth(), DatePattern.NORM_DATE_PATTERN);
                List<EntityInfoLogs> sevenTradingDayAveragesEntityInfoLists = entityInfoLogsMapper.selectList(
                        new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"})
                                .ge(EntityInfoLogs::getCreateTime, oldTime).le(EntityInfoLogs::getCreateTime, nowStrDate));
                List<EntityInfoLogs> lastMothEntityInfoLogsLists = entityInfoLogsMapper.selectList(
                        new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"})
                                .ge(EntityInfoLogs::getCreateTime, lastMonth).le(EntityInfoLogs::getCreateTime, nowStrDate));

                int lastMothtotalSize = lastMothEntityInfoLogsLists.size();
                if (lastMothtotalSize >= 1) {
                    Long betweenDays = DateUtil.between(DateUtil.lastMonth(), new Date(), DateUnit.DAY);
                    double betweenDaysDouble = betweenDays.doubleValue();
                    double lastMothtotalSizeDouble = Long.valueOf(lastMothtotalSize).doubleValue();
                    double averageDailyLatestMonth = NumberUtil.div(betweenDaysDouble, lastMothtotalSizeDouble);
                    //最近一月平均每日新上市主体
                    e1.setAverageDailyLatestMonth(averageDailyLatestMonth + "");
                }
                //拼接7天组装图 数据格式:{}
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
            entityInfoLogs = entityInfoLogsMapper.selectList(null);
        }
        return entityInfoLogs;
    }
}
