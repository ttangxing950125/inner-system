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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.EntityInfoLogsService;
import com.deloitte.crm.service.IBondInfoService;
import com.deloitte.crm.vo.EntityInfoLogsByBondVo;
import com.deloitte.crm.vo.EntityInfoLogsBySockVo;
import com.deloitte.crm.vo.EntityInfoLogsExpand;
import com.google.common.collect.Maps;
import io.jsonwebtoken.lang.Collections;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private EntityInfoMapper entityInfoMapper;

    @Resource
    private EntityInfoLogsMapper entityInfoLogsMapper;
    @Resource
    private BondInfoMapper bondInfoMapper;
    @Resource
    private StockCnInfoMapper stockCnInfoMapper;
    @Resource
    private StockThkInfoMapper stockThkInfoMapper;
    @Resource
    private RedisService redisService;

    @Resource
    private EntityBondRelMapper entityBondRelMapper;

    @Resource
    private EntityStockCnRelMapper entityStockCnRelMapper;

    @Resource
    private EntityStockThkRelMapper entityStockThkRelMapper;


    /**
     * 根据类型查询
     * CompletableFuture 使用线程池为ForkJoinPool
     * {@link java.util.concurrent.ForkJoinPool}
     *
     * @param findType 查询类型
     * @return 股票 & 债券
     * @see EntityInfoLogs# operType
     */
    @SneakyThrows
    @Override
    public Object findAllByType(String findType,Integer pageNum,Integer pageSize) {
        /***
         * 处理债券
         * {@link Common#TYPE_BOND}
         */
        if (findType.equals(Common.TYPE_BOND)) {

            CompletableFuture<EntityInfoLogsByBondVo> baskTask = CompletableFuture.supplyAsync(() -> {
                EntityInfoLogsByBondVo sockVo = new EntityInfoLogsByBondVo();
                Integer pageN=pageNum==null ? 1: pageNum;
                Integer pageS=pageSize==null ? 10: pageSize;
                Page<EntityInfoLogs> page = new Page<>(pageN, pageS);
                final Page<EntityInfoLogs> infoLogs = entityInfoLogsMapper.selectPage(page,new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}).eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE));
                sockVo.setEntityInfoLogs(infoLogs);
                return sockVo;
            });

            final CompletableFuture<Void> getAddTodayCountTask = baskTask.thenAcceptAsync(e -> {
                String nowStrDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                final List<EntityInfoLogs> totalLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"3"}).eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE)
                        .eq(EntityInfoLogs::getCreateTime, nowStrDate));
                e.setAddToday(totalLists.size());
            });

            final CompletableFuture<Void> otherTask = baskTask.thenAcceptAsync(e1 -> {
                //获取当前日期:nowDayDate 当前日期往前7天的日期:sevenTradingDayDate 当前日期最近一个月的日期:latestMonthDayDate 格式为 yyyy-MM-dd
                String nowDayDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                String sevenTradingDayDate = DateUtil.format(DateUtil.offsetDay(new Date(), -7), DatePattern.NORM_DATE_PATTERN);
                String latestMonthDayDate = DateUtil.format(DateUtil.lastMonth(), DatePattern.NORM_DATE_PATTERN);
                // sevenTradingDayDate <= createTime <= nowDayDate  查询前7天的排除周六周天的数据 算平均数
                List<EntityInfoLogs> sevenTradingtotalListsFilter = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>()
                        .in(EntityInfoLogs::getOperType, new String[]{"3"})
                        .eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE)
                        .ge(EntityInfoLogs::getCreateTime, sevenTradingDayDate)
                        .le(EntityInfoLogs::getCreateTime, nowDayDate))
                        .stream().filter(e -> !DateUtil.isWeekend(e.getCreateTime()))
                        .collect(Collectors.toList());

                if (CollUtil.isNotEmpty(sevenTradingtotalListsFilter) && sevenTradingtotalListsFilter.size() >= 1) {
                    double x = Long.valueOf(sevenTradingtotalListsFilter.size()).doubleValue();
                    Long betweenDays = DateUtil.between(DateUtil.offsetDay(new Date(), -7), new Date(), DateUnit.DAY);
                    final double y = betweenDays.doubleValue();
                    final double div = NumberUtil.div(y, x);
                    e1.setSevenTradingDayAverages(String.valueOf(div));
                }
                // latestMonthDayDate <= createTime <= nowDayDate  当前日期最近一个月的日期 算平均数
                List<EntityInfoLogs> latestMonthDayDateFilter = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>()
                        .in(EntityInfoLogs::getOperType, new String[]{"3"})
                        .eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE)
                        .ge(EntityInfoLogs::getCreateTime, latestMonthDayDate)
                        .le(EntityInfoLogs::getCreateTime, nowDayDate));

                if (CollUtil.isNotEmpty(latestMonthDayDateFilter) && latestMonthDayDateFilter.size() >= 1) {
                    double x = Long.valueOf(latestMonthDayDateFilter.size()).doubleValue();
                    Long betweenDays = DateUtil.between(DateUtil.lastMonth(), new Date(), DateUnit.DAY);
                    final double y = betweenDays.doubleValue();
                    final double div = NumberUtil.div(y, x);
                    e1.setAverageDailyLatestMonth(String.valueOf(div));
                }

                HashMap<String, Object> resultMap = Maps.newHashMap();
                //拼接7天组装图
                latestMonthDayDateFilter.stream().map(e -> {
                    EntityInfoLogsExpand entityInfoLogsExpand = BeanUtil.copyProperties(e, EntityInfoLogsExpand.class);
                    entityInfoLogsExpand.setCreateTimeStr(DateUtil.format(e.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                    return entityInfoLogsExpand;
                }).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(EntityInfoLogsExpand::getCreateTimeStr)).forEach((k, v) -> {
                    long count = v.stream().count();
                    resultMap.put(k, count);
                });
                e1.setCylinderDatas(resultMap);
            });
            CompletableFuture.allOf(baskTask, getAddTodayCountTask, otherTask).exceptionally((ex) -> {
                log.error(" 发券企业-历史记录 页面出现异常:[{}]", ex);
                throw new ServiceException("发券企业-历史记录数据组装组装异常");
            }).join();
            log.info("发券企业-历史记录>>  结束:{} ", JSON.toJSONString(baskTask.get()));
            return R.ok(baskTask.get());
            /**
             * 处理股票
             *{@link Common#TYPE_STOCK}
             */
        } else if (Common.TYPE_STOCK.equals(findType)) {

            CompletableFuture<EntityInfoLogsBySockVo> baskTask = CompletableFuture.supplyAsync(() -> {
                EntityInfoLogsBySockVo sockVo = new EntityInfoLogsBySockVo();
              Integer pageN=pageNum==null ? 1: pageNum;
              Integer pageS=pageSize==null ? 10: pageSize;

                Page<EntityInfoLogs> page = new Page<>(pageN, pageS);
                Page<EntityInfoLogs> entoty = entityInfoLogsMapper.selectPage(page,new LambdaQueryWrapper<EntityInfoLogs>().between(EntityInfoLogs::getOperType, 1, 2).eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE));
                sockVo.setEntityInfoLogs(entoty);
                return sockVo;
            });

            final CompletableFuture<Void> getAddTodayCountTask = baskTask.thenAcceptAsync(e -> {
                List<EntityInfoLogs> totalLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>()
                        /**
                         * TYPE_STOCK 股票分为 A股和港股 1 2 连续多个使用 between数据库中创建了索引如使用in(EntityInfoLogs::getOperType, new String[]{"1", "2"})不会走索引
                         * 详情:EXPLAIN select * from entity_info_logs WHERE oper_type BETWEEN 1 and 2 and create_time>='2022-10-17' and create_time<='2022-10-18'
                         */
                        .between(EntityInfoLogs::getOperType, 1, 2)
                        .eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE)
                        .eq(EntityInfoLogs::getCreateTime, DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN)));
                e.setAddToday(totalLists.size());
            });

            final CompletableFuture<Void> otherTask = baskTask.thenAcceptAsync(e1 -> {
                String nowStrDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
                String sevenTradingDayDate = DateUtil.format(DateUtil.offsetDay(new Date(), -7), DatePattern.NORM_DATE_PATTERN);
                String latestMonthDayDate = DateUtil.format(DateUtil.lastMonth(), DatePattern.NORM_DATE_PATTERN);
                //查询7 天排除周六周天 并计算平均7天的
                List<EntityInfoLogs> sevenTradingDayAveragesEntityInfoLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>()
//                        .in(EntityInfoLogs::getOperType, new String[]{"1", "2"})
                        .between(EntityInfoLogs::getOperType, 1, 2)
                        .eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE)
                        .ge(EntityInfoLogs::getCreateTime, sevenTradingDayDate)
                        .le(EntityInfoLogs::getCreateTime, nowStrDate))
                        .stream().filter(e -> !DateUtil.isWeekend(e.getCreateTime()))
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(sevenTradingDayAveragesEntityInfoLists) && sevenTradingDayAveragesEntityInfoLists.size() >= 1) {
                    double x = Long.valueOf(sevenTradingDayAveragesEntityInfoLists.size()).doubleValue();
                    Long betweenDays = DateUtil.between(DateUtil.offsetDay(new Date(), -7), new Date(), DateUnit.DAY);
                    final double y = betweenDays.doubleValue();
                    final double div = NumberUtil.div(y, x);
                    e1.setSevenTradingDayAverages(String.valueOf(div));
                }
                //当前日期最近一个月的 并求平均一个月的
                List<EntityInfoLogs> lastMothEntityInfoLogsLists = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>()
//                        .in(EntityInfoLogs::getOperType, new String[]{"1", "2"})
                        .between(EntityInfoLogs::getOperType, 1, 2).eq(EntityInfoLogs::getIsDeleted, Boolean.FALSE)
                        .ge(EntityInfoLogs::getCreateTime, latestMonthDayDate)
                        .le(EntityInfoLogs::getCreateTime, nowStrDate));
                if (CollUtil.isNotEmpty(lastMothEntityInfoLogsLists) && lastMothEntityInfoLogsLists.size() >= 1) {
                    Long betweenDays = DateUtil.between(DateUtil.lastMonth(), new Date(), DateUnit.DAY);
                    double betweenDaysDouble = betweenDays.doubleValue();
                    double lastMothtotalSizeDouble = Long.valueOf(lastMothEntityInfoLogsLists.size()).doubleValue();
                    double averageDailyLatestMonth = NumberUtil.div(betweenDaysDouble, lastMothtotalSizeDouble);
                    e1.setAverageDailyLatestMonth(averageDailyLatestMonth + "");
                }
                //绘制柱状体 7天的
                HashMap<String, Object> resultMap = Maps.newHashMap();
                sevenTradingDayAveragesEntityInfoLists.stream().map(e -> {
                    EntityInfoLogsExpand entityInfoLogsExpand = BeanUtil.copyProperties(e, EntityInfoLogsExpand.class);
                    entityInfoLogsExpand.setCreateTimeStr(DateUtil.format(e.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                    return entityInfoLogsExpand;
                }).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(EntityInfoLogsExpand::getCreateTimeStr)).forEach((k, v) -> {
                    long count = v.stream().count();
                    resultMap.put(k, count);
                });
                e1.setCylinderDatas(resultMap);
            });
            CompletableFuture.allOf(baskTask, getAddTodayCountTask, otherTask).exceptionally((ex) -> {
                log.error(" 企业主体-历史记录 页面出现异常:[{}]", ex);
                throw new ServiceException("企业主体-历史记录数据组装组装异常");
            }).join();
            log.info("企业主体-历史记录>>  结束:{} ", JSON.toJSONString(baskTask.get()));
            return R.ok(baskTask.get());
        } else {
            throw new ServiceException("参数非法");
        }
    }

    /**
     * 撤销
     *
     * @param id
     * @param
     * @return
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object cancel(Integer id) {
        LambdaQueryWrapper<EntityInfoLogs> entityInfoLogsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        EntityInfoLogs entityInfoLogs = Optional.ofNullable(entityInfoLogsMapper.selectOne(entityInfoLogsLambdaQueryWrapper.eq(EntityInfoLogs::getId, id))).orElseThrow(() -> new ServiceException("id为:" + id + "的数据不存在"));
        entityInfoLogs.setIsDeleted(Boolean.TRUE);
        entityInfoLogsMapper.updateById(entityInfoLogs);
        /***
         *  债券的删除逻辑 operType=3
         * {@link EntityInfoLogs#operType}
         */
        if (entityInfoLogs.getOperType() == 3) {
            LambdaQueryWrapper<BondInfo> bondInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            final BondInfo bondInfo = bondInfoMapper.selectOne(bondInfoLambdaQueryWrapper.eq(BondInfo::getOriCode, entityInfoLogs.getCode()).eq(BondInfo::getBondShortName, entityInfoLogs.getName()));
            if (bondInfo != null) {
                bondInfo.setIsDeleted(Boolean.TRUE);
                bondInfoMapper.updateById(bondInfo);
                LambdaQueryWrapper<EntityBondRel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                final EntityBondRel entityBondRel = entityBondRelMapper.selectOne(lambdaQueryWrapper.eq(EntityBondRel::getBdCode, bondInfo.getBondCode()));
                if (entityBondRel != null) {
                    entityBondRel.setStatus(0);
                    entityBondRelMapper.updateEntityBondRel(entityBondRel);
                    final EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, entityBondRel.getEntityCode()));
                    if (entityInfo != null) {
                        entityInfo.setStatus(0);//失效
                        entityInfoMapper.updateById(entityInfo);
                    }
                }
            }
            /***
             *  A股删除逻辑 1 operType=1
             * {@link EntityInfoLogs#operType}
             */
        } else if (entityInfoLogs.getOperType() == 1) {
            LambdaQueryWrapper<StockCnInfo> stockCnInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            final StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(stockCnInfoLambdaQueryWrapper.eq(StockCnInfo::getStockDqCode, entityInfoLogs.getDeCode()));
            if (stockCnInfo != null) {
                String stockDqCode = stockCnInfo.getStockDqCode();
                LambdaQueryWrapper<EntityStockCnRel> entityStockCnRelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                final EntityStockCnRel entityStockCnRel = entityStockCnRelMapper.selectOne(entityStockCnRelLambdaQueryWrapper.eq(EntityStockCnRel::getStockDqCode, stockDqCode));
                if (entityStockCnRel != null) {
                    entityStockCnRel.setStatus(Boolean.FALSE);
                    entityStockCnRelMapper.updateById(entityStockCnRel);
                    final EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, entityStockCnRel.getEntityCode()));
                    if (entityInfo != null) {
                        entityInfo.setStatus(0);
                        entityInfoMapper.updateById(entityInfo);
                    }
                }
                /**
                 * 本次删除逻辑存在部分bug 在不考虑分布式数据实时一致性的话 延时双删不是最好的最解决方式
                 * {@link StockCnInfoServiceImpl#saveOrUpdateNew(StockCnInfo)} (Object)}
                 */
                stockCnInfo.setIsDeleted(Boolean.TRUE);
                redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_CN_INFO, stockCnInfo.getStockCode());
                stockCnInfoMapper.updateById(stockCnInfo);
                Thread.sleep(100);
                redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_CN_INFO, stockCnInfo.getStockCode());
            }
            /***
             *  港股 删除逻辑 operType=2
             * {@link EntityInfoLogs#operType}
             */
        } else if (entityInfoLogs.getOperType() == 2) {
            LambdaQueryWrapper<StockThkInfo> stockThkInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            final StockThkInfo stockThkInfo = stockThkInfoMapper.selectOne(stockThkInfoLambdaQueryWrapper.eq(StockThkInfo::getStockDqCode, entityInfoLogs.getDeCode()));
            if (stockThkInfo != null) {
                /**
                 * 本次删除逻辑存在部分bug 在不考虑分布式数据实时一致性的话 延时双删不是最好的最解决方式
                 * {@link StockCnInfoServiceImpl#saveOrUpdateNew(StockCnInfo)}
                 */
                stockThkInfo.setIsDeleted(Boolean.TRUE);
                redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_THK_INFO, stockThkInfo.getStockCode());
                stockThkInfoMapper.updateById(stockThkInfo);
                Thread.sleep(100);
                redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_THK_INFO, stockThkInfo.getStockCode());
                String stockDqCode = stockThkInfo.getStockDqCode();
                LambdaQueryWrapper<EntityStockThkRel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                EntityStockThkRel entityStockThkRel = entityStockThkRelMapper.selectOne(lambdaQueryWrapper.eq(EntityStockThkRel::getStockDqCode, stockDqCode));
                if (entityStockThkRel != null) {
                    entityStockThkRel.setStatus(Boolean.FALSE);
                    entityStockThkRelMapper.updateById(entityStockThkRel);
                }
                final EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, entityStockThkRel.getEntityCode()));
                if (entityInfo != null) {
                    entityInfo.setStatus(0);
                    entityInfoMapper.updateById(entityInfo);
                }
            }
        }
        return R.ok();
    }
}
