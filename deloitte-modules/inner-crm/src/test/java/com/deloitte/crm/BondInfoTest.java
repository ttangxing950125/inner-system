package com.deloitte.crm;

import com.deloitte.common.core.constant.SecurityConstants;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysDictData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
@SpringBootTest
public class BondInfoTest {

    @Resource
    private RedisService redisService;

    @Resource
    private RoleService roleService;

    @Test
    void clearCache(){
//        List<SysDictData> roleByType = roleService.getRoleByType(SecurityConstants.INNER);
//        redisService.deleteObject(CacheName.BOND_CACHE);
        System.out.println(1111);
        redisService.redisTemplate.delete(CacheName.STOCK_CN_INFO);
    }

}
