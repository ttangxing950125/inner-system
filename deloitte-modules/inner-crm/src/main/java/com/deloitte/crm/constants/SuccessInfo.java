package com.deloitte.crm.constants;

/**
 * @author 正杰
 * @description: 成功信息常量
 * @date 2022/9/22
 */
public enum SuccessInfo {

    SUCCESS("操作成功"),GET_SUCCESS("获取信息成功"),ENABLE_CREAT_ENTITY("可以创建新主体"),EMPTY_ENTITY_CODE("无重复，可新增");

    public static final Boolean GET = true;

    private final String info;

    SuccessInfo(String info){
        this.info = info;
    }

    public String getInfo(){return info;};

}
