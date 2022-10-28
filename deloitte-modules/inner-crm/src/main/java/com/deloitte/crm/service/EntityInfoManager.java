package com.deloitte.crm.service;

import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.vo.CheckVo;

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

    /**
     *   *****************
     *   *   绑定关联关系  *
     *   *****************
     * @param entityInfoInsertDTO
     * @param entityCode 如果是新增主体 那么 entityInfoInsertDOT 中的 entityCode 就为空
     * @param username
     */
    void bindData(EntityInfoInsertDTO entityInfoInsertDTO,String entityCode,String username);

}
