package com.deloitte.additional.recording.util;

import com.deloitte.additional.recording.dto.UserModel;
import com.deloitte.common.redis.service.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/29
 */
@Component
public class UserMapUtil {

    public static final String USER_MAP = "user_map";

    @Resource
    private RedisService redisService;

    public UserModel get(String userId) {
        return redisService.getCacheMapValue(USER_MAP, userId);
    }


    public UserModel put(Integer userId, UserModel userModel){
        redisService.setCacheMapValue(USER_MAP,userId.toString(),userModel);

        return userModel;
    }
}
