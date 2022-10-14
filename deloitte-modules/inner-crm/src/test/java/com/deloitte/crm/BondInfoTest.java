package com.deloitte.crm;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.common.core.constant.SecurityConstants;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityInfoLogs;
import com.deloitte.crm.mapper.EntityInfoLogsMapper;
import com.deloitte.crm.service.EntityInfoLogsService;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
@Slf4j
@SpringBootTest
public class BondInfoTest {

    @Resource
    private RedisService redisService;

    @Resource
    private RoleService roleService;

    @Resource
    private EntityInfoLogsService entityInfoLogsService;

    @Test
    void clearCache() {
//        List<SysDictData> roleByType = roleService.getRoleByType(SecurityConstants.INNER);
//        redisService.deleteObject(CacheName.BOND_CACHE);
        System.out.println(1111);
        redisService.redisTemplate.delete(CacheName.STOCK_CN_INFO);


    }

    @Resource
    private EntityInfoLogsMapper entityInfoLogsMapper;

    @Test
    void test002() {
        entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}));
        //最近7天向上偏移
        final String oldTime = DateUtil.format(DateUtil.offsetDay(new Date(), -7), DatePattern.NORM_DATE_PATTERN);
        final String nowStrDate = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
        final List<EntityInfoLogs> entityInfoLogs = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}).ge(EntityInfoLogs::getCreateTime, oldTime).le(EntityInfoLogs::getCreateTime, nowStrDate));
        System.out.println(JSON.toJSON(entityInfoLogs));
        final List<EntityInfoLogs> entityInfoLogs1 = entityInfoLogsMapper.selectList(new LambdaQueryWrapper<EntityInfoLogs>().in(EntityInfoLogs::getOperType, new String[]{"1", "2"}).eq(EntityInfoLogs::getCreateTime, nowStrDate));
        System.out.println("size===>" + entityInfoLogs1.size());
    }

    @Test
    void test0() {
        final Object allByType = entityInfoLogsService.findAllByType(Common.TYPE_STOCK);
        log.info("JSON:==>{}", JSON.toJSONString(allByType));
    }


}
