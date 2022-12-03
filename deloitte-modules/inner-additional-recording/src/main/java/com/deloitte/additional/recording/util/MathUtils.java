package com.deloitte.additional.recording.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

/**
 * @创建人 tangx
 * @创建时间 2022/12/2
 * @描述 数学相关算法工具类
 */
public class MathUtils {

    /**
     * 众数 结果可能有多个
     * 字符串数字类型
     *
     * @param list
     * @return
     */
    public static List<String> mode(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : list) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        List<Integer> list1 = new ArrayList<>();
        // 将出现的次数存入List集合
        map.forEach((k, v) -> {
            list1.add(v);
        });
        Collections.sort(list1);
        // 得到最大值 也就是出现的次数最大值
        int max = list1.get(list1.size() - 1);
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        // 结果
        List<String> res = new ArrayList<>();
        // 根据最大值获取众数
        for (Map.Entry<String, Integer> entry : set) {
            if (entry.getValue() == max) {
                res.add(entry.getKey());
            }
        }
        return res;
    }

    /**
     * 求中位数
     * 字符串数字类型
     *
     * @param list
     * @return BigDecimal
     */
    public static String getMedianBigDecimal(List<String> list) {
        int size = list.size();
        //偶数
        BigDecimal median = null;
        if (size % 2 != 1) {
            String s1 = list.get(size / 2 - 1);
            String s2 = list.get(size / 2);
            BigDecimal add = new BigDecimal(s1).add(new BigDecimal(s2));
            median = add.divide(new BigDecimal(2));
        } else {
            String s = list.get((size - 1) / 2);
            median = new BigDecimal(s);
        }
        return median.toString();
    }


    /**
     * 计算方差  s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)
     * 字符串数字类型
     *
     * @param list
     * @return
     */
    public static String variance(List<String> list) {

        BigDecimal total = BigDecimal.ZERO;
        for (String s : list) {
            //求和
            total = total.add(new BigDecimal(s));
        }
        //求平均值
        BigDecimal average = total.divide(new BigDecimal(list.size()));

        //方差 s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)
        BigDecimal variance = BigDecimal.ZERO;
        for (String s : list) {
            BigDecimal decimal = new BigDecimal(s);
            BigDecimal subtract = decimal.subtract(average);
            BigDecimal multiply = subtract.multiply(subtract);
            variance = variance.add(multiply);
        }
        return variance.divide(new BigDecimal(2)).toString();
    }


    /**
     * 求标准差 // 方差求平均根
     *
     * @param variance
     * @return
     */
    public static String standardDeviation(String variance) {
        BigDecimal bigDecimal = new BigDecimal(variance);
        BigDecimal sqrt = sqrt(bigDecimal);
        return sqrt.toString();
    }


    public static int getIndex(int size, double rate) {
        double v = size * rate;
        return (int) Math.ceil(v);
    }

    /**
     * 开平方根
     *
     * @param num
     * @return
     */
    //BigDecimal 的sqrt方法
    private static BigDecimal sqrt(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }


        BigDecimal x = num.divide(new BigDecimal("2"), MathContext.DECIMAL128);
        while (x.subtract(x = sqrtIteration(x, num)).abs().compareTo(new BigDecimal("0.0000000000000000000001")) > 0) ;
        return x;
    }

    private static BigDecimal sqrtIteration(BigDecimal x, BigDecimal n) {
        return x.add(n.divide(x, MathContext.DECIMAL128)).divide(new BigDecimal("2"), MathContext.DECIMAL128);
    }


    public static void main(String[] args) {
//        String st1 = "1";
//        String st2 = "2";
//        String st3 = "1.12";
//        String st4 = "-0.13213";
//        String st5 = "2315.3";
//        List<String> list = new ArrayList<>();
//        list.add(st1);
//        list.add(st1);
//        list.add(st1);
//        list.add(st2);
//        list.add(st2);
//        list.add(st2);
//        list.add(st3);
//        list.add(st4);
//        list.add(st5);
//        Collections.sort(list);
//        System.out.println(list);

        int size = 13;

        double v = 13 * 0.25;
        System.out.println(v);
        double ceil = Math.ceil(v);
        System.out.println(ceil);

    }


}
