package com.deloitte.crm.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 正杰
 * @description: AttrValueMappingMap
 * @date 2022/9/28
 */
public enum AttrValueMappingMap {

    COLLECTIVE_BONDS("是否发行集合债",58),
    BOND_SOURCE_NAME("是否为集合债",58),
    IS_ABS("是否为ABS",617),
    TRAD_CODE("债券交易代码",804),
    BOND_FULL_NAME("债券全称",48),
    VALUE_DATE("起息日",52),
    DUE_CASHING_DATE("到期兑付日",53),
    BOND_SOURCE("债务关系来源",null),
    ISSUER_NAME("前发行人名称",58),
    ISSUER_BD_CODE("前发行人德勤主体代码",null),
    ISSUER_CREDIT_CODE("前发行人统一社会信用代码",null),
    RECEIVER_NAME("接收方名称",null),
    RECEIVE_BD_CODE("接收方德勤主体代码",null),
    RECEIVE_CREDIT_CODE("是否违约",108),
    VIOLATION_TYPE("违约类型",111),
    VIOLATION_DATE("违约日期",109),
    WHETHER_VIOLATION_ID("是否违约",108);

    private final Map<String,Integer> map = new HashMap<>();

    AttrValueMappingMap(String key,Integer value){
        map.put(key,value);
    }

    public Map<String,Integer> get(){return map;}

}
