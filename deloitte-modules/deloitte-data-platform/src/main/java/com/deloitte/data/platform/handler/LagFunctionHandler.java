package com.deloitte.data.platform.handler;


import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Map;

/**
 * lag（上一年） 函数处理器
 *
 * @author XY
 * @date 2022-11-17
 */
@Service("lag")
public class LagFunctionHandler implements FunctionHandler {


    @Override
    public String nameHandler() {
        return "";
    }

    @Override
    public String itemHandler(Map<String, Map<String, String>> entityFormulaItemDataMap, String item, LocalDate reportDate) {
        //获取上一年
        LocalDate lagReportDate = reportDate.minusYears(1L);
        Map<String, String> itemDateMap = entityFormulaItemDataMap.get(lagReportDate.toString());
        if (CollectionUtils.isEmpty(itemDateMap)) {
            return "0";
        }
        if (StringUtils.hasLength(itemDateMap.get(item))) {
            return itemDateMap.get(item);
        }
        return "0";
    }


}
