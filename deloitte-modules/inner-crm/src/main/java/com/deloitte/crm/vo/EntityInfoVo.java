package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityInfo;
import lombok.Data;

/**
 * 该类为 EntityInfoService 接口的 Vo 传输对象
 * @author 正杰
 * @description: EntityInfoVo
 * @date 2022/9/22
 */
@Data
public class EntityInfoVo {

    /**
     * 查询到的 主体信息
     */
    private EntityInfo entityInfo;

    /**
     * 该主体是否为 新增
     */
    private Boolean bo = true;

    /**
     * 详细说明信息
     */
    private String msg;

    public EntityInfoVo setEntityInfo(EntityInfo entityInfo) {
        this.entityInfo = entityInfo;
        return this;
    }

    public EntityInfoVo setBo(Boolean bo) {
        this.bo = bo;
        return this;
    }

    public EntityInfoVo setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
