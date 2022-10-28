package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmTypeInfo;

import java.util.List;
import java.util.Set;

/**
 * (CrmTypeInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-28 13:11:01
 */
public interface CrmTypeInfoService extends IService<CrmTypeInfo> {

    /**
     *查询树结构根据分类
     * @author 吴鹏鹏
     * @param type
     * @return
     */
    List<CrmTypeInfo> findTreeByType(String parentCode,Integer type);

    Set<CrmTypeInfo> findCodeByParent(CrmTypeInfo crmTypeInfo,Integer type);

}
