package com.deloitte.crm.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 正杰
 * @description: AttrValueMappingMap
 * @date 2022/9/28
 */
public enum AttrValueMappingMap {

    TRAD_CODE("交易代码",804),
    COLLECTIVE_BONDS("是否发行集合债",58),
    BOND_FULL_NAME("债券全称",48),
    WHETHER_VIOLATION_ID("是否违约",108);

    private final Map<String,Integer> map = new HashMap<>();

    AttrValueMappingMap(String key,Integer value){
        map.put(key,value);
    }

    public Map<String,Integer> get(){return map;}

}
