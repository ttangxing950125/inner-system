package com.deloitte.crm;

import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.ProductsMasterRelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/8
 */
@SpringBootTest
@Slf4j
public class EntityInfoTest {

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    private ICrmEntityTaskService crmEntityTaskService;

    @Resource
    private ProductsMasterRelService productsMasterRelService;

    @Resource
    private RedisService redisService;

    @Test
    void test1(){
        Map<String, Object> cacheMap = redisService.getCacheMap("crm_pull_task:1d0ed29de9da45eeb37d06fe3cd5d5e3");
        System.out.println(cacheMap);
    }

}
