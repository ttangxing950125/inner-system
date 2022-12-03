package com.deloitte.data.platform.handler;


import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Map;

/**
 * sag【上上一年（前年）】 函数处理器
 *
 * @author XY
 * @date 2022-11-17
 */
@Service("sag")
public class SagFunctionHandler implements FunctionHandler {


    @Override
    public String nameHandler() {
        return "";
    }

    @Override
    public String itemHandler(Map<String, Map<String, String>> entityFormulaItemDataMap, String item, LocalDate reportDate) {
        //获取上一年
        LocalDate sagReportDate = reportDate.minusYears(2L);
        Map<String, String> itemDateMap = entityFormulaItemDataMap.get(sagReportDate.toString());
        if (CollectionUtils.isEmpty(itemDateMap)) {
            return "0";
        }
        if (StringUtils.hasLength(itemDateMap.get(item))) {
            return itemDateMap.get(item);
        }
        return "0";
    }


}
