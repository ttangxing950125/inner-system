package com.deloitte.crm.service;

import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.vo.CheckVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 正杰
 *
 *   ****************
 *   *    通用方法   *
 *   ****************
 *
 * @description: EntityInfoManager
 * @date 2022/9/29
 */
public interface EntityInfoManager {

    /**
     *   *******************
     *   *    修改主体名称   *
     *   *******************
     * @param entity
     * @param entityNewName
     * @param remarks
     * @return
     */
    String updateEntityName(EntityInfo entity, String entityNewName, String remarks);

    /**
     *   *****************
     *   *    匹配关键字   *
     *   *****************
     * @param keyword
     * @param target
     * @return
     */
    CheckVo matchByKeyword(String keyword, String target);

}
