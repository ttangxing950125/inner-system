package com.deloitte.data.platform.handler;


import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Map;

/**
 * log 函数处理器
 *
 * @author XY
 * @date 2022-11-17
 */
@Service("log")
public class LogFunctionHandler implements FunctionHandler {

    @Override
    public String nameHandler() {
        return "Math.log";
    }

    @Override
    public String itemHandler(Map<String, Map<String, String>> entityFormulaItemDataMap, String item, LocalDate reportDate) {
        Map<String, String> itemDateMap = entityFormulaItemDataMap.get(reportDate.toString());
        if (CollectionUtils.isEmpty(itemDateMap)) {
            return "0";
        }
        if (StringUtils.hasLength(itemDateMap.get(item))) {
            return itemDateMap.get(item);
        }
        return "0";
    }


}
