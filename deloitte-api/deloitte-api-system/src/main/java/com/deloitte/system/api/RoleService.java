package com.deloitte.system.api;

import com.deloitte.common.core.constant.ServiceNameConstants;
import com.deloitte.system.api.domain.SysDictData;
import com.deloitte.system.api.factory.GetRoleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 查询角色
 * @author PenTang
 * @date 2022/09/22 19:31
 */


@FeignClient(contextId = "GetRoleService", value = ServiceNameConstants.SYSTEM_SERVICE,fallbackFactory = GetRoleFallbackFactory.class)
public interface RoleService {

    /**
     *查询角色
     *
     * @return List<SysDictData>
     * @author penTang
     * @date 2022/9/22 19:40
    */
    @PostMapping("dict/data/queryRoles")
     List<SysDictData> getRoleByType();

}
