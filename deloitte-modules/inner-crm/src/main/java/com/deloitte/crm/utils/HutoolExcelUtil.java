package com.deloitte.crm.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.cell.CellUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.exception.ServiceException;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class HutoolExcelUtil {

    private static List<List<Object>> lineList = new ArrayList<>();

    public static void exportExcel(HttpServletResponse response, List<?> rows, Map<String, String> map) {
        // 通过工具类创建writer，默认创建xls格式, ExcelUtil.getWriter(true) xlsx
        ExcelWriter writer = ExcelUtil.getWriter(true);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            writer.addHeaderAlias(entry.getKey(), entry.getValue());
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        //out为OutputStream，需要写出到的目标流
        long fileName = System.currentTimeMillis();
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    public static <T> void exportExcel(HttpServletResponse response, List<?> rows, Class<T> clazz) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Field field : ReflectUtil.getFields(clazz)) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (annotation != null) {
                map.put(field.getName(), annotation.value());
            }
        }
        // 通过工具类创建writer，默认创建xls格式, ExcelUtil.getWriter(true) xlsx
        ExcelWriter writer = ExcelUtil.getWriter(true);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            writer.addHeaderAlias(entry.getKey(), entry.getValue());
        }

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        //out为OutputStream，需要写出到的目标流
        long fileName = System.currentTimeMillis();
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            log.error("获取流异常：{}", e);
        }
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
     * excel导入工具类
     *
     * @param file 文件
     * @return 返回数据集合
     * @throws IOException
     */
    public static <T> List<T> leading(MultipartFile file, Class<T> clazz) {
        String fileName = file.getOriginalFilename();
        // 上传文件为空
        if (Strings.isNullOrEmpty(fileName)) {
            throw new ServiceException("导入文件不能为空");
        }
        //上传文件大小为1000条数据
//        if (file.getSize() > 1024 * 1024 * 10) {
//            logger.error("upload | 上传失败: 文件大小超过10M，文件大小为：{}", file.getSize());
//            throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "上传失败: 文件大小不能超过10M!");
//        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new ServiceException("文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }

        //读取数据
        try {
            Excel07SaxReader excel07SaxReader = ExcelUtil.read07BySax(file.getInputStream(), 0, createRowHandler());
            //去除excel中的第一行数据
            lineList.remove(0);
            //将数据封装到list<Map>中
            List<T> list = new LinkedList();
            for (int i = 0; i < lineList.size(); i++) {
                if (null != lineList.get(i)) {
                    T t = ReflectUtil.newInstance(clazz);
                    Field[] fields = ReflectUtil.getFields(clazz);
                    for (int j = 0; j < fields.length; j++) {
                        Object property = lineList.get(i).get(j);
                        if (property != null && !"".equals((String.valueOf(property)).trim())) {
                            ReflectUtil.setFieldValue(t, fields[j], property);
                        }
                    }
                    list.add(t);
                } else {
                    break;
                }
            }
            return list;
        } catch (Exception e) {
            log.error("导入的数据有误>>>>ex:{}", e);
            throw new ServiceException("导入的数据有误，请导入正确数据！");
        }
    }


    /**
     * excel导入工具类
     *
     * @param file
     * @param clazz
     * @param createRowHandler 处理器
     * @param <T>
     * @return
     */
    public static <T> List<T> leading(MultipartFile file, Class<T> clazz, CreateRowHandler createRowHandler) {
        String fileName = file.getOriginalFilename();
        // 上传文件为空
        if (Strings.isNullOrEmpty(fileName)) {
            throw new ServiceException("导入文件不能为空");
        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new ServiceException("文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }
        //读取数据
        try {
            List<List<Object>> localLineList = new ArrayList<>();
            RowHandler rowHandler = createRowHandler.getRowHandler(localLineList);
            ExcelUtil.readBySax(file.getInputStream(), 0, rowHandler == null ? createRowHandler() : rowHandler);
            //将数据封装到list<Map>中
            List<T> list = new LinkedList();
            for (int i = 0; i < localLineList.size(); i++) {
                if (null != localLineList.get(i)) {
                    T t = ReflectUtil.newInstance(clazz);
                    Field[] fields = ReflectUtil.getFields(clazz);
                    for (int j = 0; j < fields.length; j++) {
                        Object property = localLineList.get(i).get(j);
                        if (property != null && !"".equals((String.valueOf(property)).trim())) {
                            ReflectUtil.setFieldValue(t, fields[j], property);
                        }
                    }
                    list.add(t);
                } else {
                    break;
                }
            }
            return list;
        } catch (ServiceException e) {
            log.error("e.getMessage()");
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("导入的数据有误，请导入正确数据！");
        }
    }

    /**
     * 通过实现handle方法编写我们要对每行数据的操作方式
     */
    private static RowHandler createRowHandler() {
        //清空一下集合中的数据
        lineList.removeAll(lineList);
        return new RowHandler() {
            @Override
            public void handle(int i, long l, List<Object> list) {
                //将读取到的每一行数据放入到list集合中
                JSONArray jsonObject = new JSONArray(list);
                lineList.add(jsonObject.toList(Object.class));
            }
        };
    }


    /**
     * 以String类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛异常
     * @return 没有获取到就返回null
     */
    public static String readCellStringValue(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        String res = null;
        try {
            res = value.toString();
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

    /**
     * 以boolean类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛出异常
     * @return 没有获取到就返回null
     */
    public static Boolean readCellBooleanValue(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        Boolean res = null;
        try {
            res = (Boolean) value;
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

    /**
     * 以Long类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛出异常
     * @return 没有获取到就返回null
     */
    public static Long readCellLongValue(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        Long res = null;
        try {
            res = (Long) value;
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

    /**
     * 以Integer类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛出异常
     * @return 没有获取到就返回null
     */
    public static Integer readCellIntegerValue(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        Integer res = null;
        try {
            //CellUtil转化的时候是返回的Long类型
            Long longValue = (Long) value;
            res = longValue.intValue();
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

    /**
     * 以Double类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛出异常
     * @return 没有获取到就返回null
     */
    public static Double readCellDoubleValue(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        Double res = null;
        try {
            res = (Double) value;
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

    /**
     * 以BigDecimal类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛出异常
     * @return 没有获取到就返回null
     */
    public static BigDecimal readCellBigDecimalValue(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        BigDecimal res = null;
        try {
            res = new BigDecimal(value.toString());
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

    /**
     * 以LocalDateTime类型返回cell的值
     *
     * @param cell
     * @param ex   抛出异常类，传了就抛出异常
     * @return 没有获取到就返回null
     */
    public static LocalDateTime readCellLocalDateTime(Cell cell, RuntimeException ex) {
        Object value = CellUtil.getCellValue(cell);
        if (value == null) {
            return null;
        }
        LocalDateTime res = null;
        try {
            DateTime time = (DateTime) value;
            res = DateUtil.toLocalDateTime(time);
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            }
        }
        return res;
    }

}
