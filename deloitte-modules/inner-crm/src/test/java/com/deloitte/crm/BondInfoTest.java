package com.deloitte.crm;

import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
@SpringBootTest
public class BondInfoTest {

    @Resource
    private RedisService redisService;

    @Test
    void clearCache(){
        redisService.deleteObject(CacheName.BOND_CACHE);
    }

}
