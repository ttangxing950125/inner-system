package com.deloitte.data.platform.handler;

import com.deloitte.data.platform.component.ApplicationContextRegister;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface FunctionHandler {

    List<String> operator = Arrays.asList("+", "-", "*", "/");


    /**
     * 函数名称处理器
     *
     * @return
     */
    String nameHandler();

    /**
     * 函数 item 值处理器
     *
     * @param entityFormulaItemDataMap 该公式所需的科目
     * @param item                     函数值
     * @param reportDate               上报时间
     * @return
     */
    String itemHandler(Map<String, Map<String, String>> entityFormulaItemDataMap, String item, LocalDate reportDate);

    /**
     * 函数执行器
     * <p>
     * <p>
     * 返回 formulaItemList 的下标，标示函数处理到了哪个位置
     *
     * @param formulaItemList          公式数组
     * @param i                        数组执行的位置
     * @param entityCode               主体编码
     * @param reportDate               上报时间
     * @param entityFormulaItemDataMap 该公式所需的科目
     * @return
     */
    default int executer(String entityCode, LocalDate reportDate, List<String> formulaItemList, int i, Map<String, Map<String, String>> entityFormulaItemDataMap) {
        int j = i;
        //括号记录器
        int k = 0;
        int formulaItemSize = formulaItemList.size();
        for (; j < formulaItemSize; j++) {
            if (j == i) {
                //替换 函数符号
                formulaItemList.set(j, this.nameHandler());
                continue;
            }
            String item = formulaItemList.get(j);
            if (this.isOperator(item)) {
                //如果是运算符直接下一次
                continue;
            }
            if (item.matches("\\d")) {
                //本身就是数字，直接跳过
                continue;
            }
            if ("(".equals(item)) {
                //记录下左括号的位置
                k += 1;
                continue;
            }
            if (")".equals(item)) {
                if ((k -= 1) == 0) {
                    //如果 k = 0 说明该函数已经结束
                    return j;
                }
                continue;
            }
            //查看该项是不是函数，这里主要防止变量名与函数名一样,若后面一下跟的是左括号，说明是函数
            if (j + 1 < formulaItemSize && "(".equals(formulaItemList.get(j + 1))) {
                //说明是函数，选择函数处理器处理
                FunctionHandler functionHandler = ApplicationContextRegister.getBean(item, FunctionHandler.class);
                Assert.notNull(functionHandler, "未知函数：" + item);
                //交给函数处理器处理
                int index = functionHandler.executer(entityCode, reportDate, formulaItemList, j, entityFormulaItemDataMap);
                j = index;
                continue;
            }
            //如果不是，则需要查询替换掉当前元素
            formulaItemList.set(j, this.itemHandler(entityFormulaItemDataMap, item, reportDate));
        }
        return j;
    }


    /**
     * str 是不是运算符
     *
     * @param str
     * @return
     */
    default boolean isOperator(String str) {
        return operator.contains(str);
    }

}
