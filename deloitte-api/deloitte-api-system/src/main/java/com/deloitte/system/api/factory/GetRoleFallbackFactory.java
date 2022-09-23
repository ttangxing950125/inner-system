package com.deloitte.system.api.factory;

import com.deloitte.system.api.RoleService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author PenTang
 * @date 2022/09/22 19:33
 */
@Component
public class GetRoleFallbackFactory implements FallbackFactory<RoleService> {

    @Override
    public RoleService create(Throwable cause) {
        return null;
    }
}
