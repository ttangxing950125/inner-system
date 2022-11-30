package com.deloitte.additional.recording.util;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.deloitte.additional.recording.listener.EasyExcelListener;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/21
 * @描述 导入excel动态表头工具类
 */
public class EasyExcelImportUtils {

    /**
     * 动态获取全部列和数据体
     */
    public static List<Map<String,String>> parseExcelToData(byte[] stream, Integer parseRowNumber) {
        EasyExcelListener readListener = new EasyExcelListener();
        EasyExcelFactory.read(new ByteArrayInputStream(stream)).registerReadListener(readListener).headRowNumber(parseRowNumber).sheet(0).doRead();
        List<Map<Integer, String>> headList = readListener.getHeadList();
        if(CollectionUtils.isEmpty(headList)){
            throw new RuntimeException("Excel表头不能为空");
        }
        List<Map<Integer, String>> dataList = readListener.getDataList();
        if(CollectionUtils.isEmpty(dataList)){
            throw new RuntimeException("Excel数据内容不能为空");
        }
        //获取头部,取最后一次解析的列头数据
        Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() -1);
        //封装数据体
        List<Map<String,String>> excelDataList = Lists.newArrayList();
        for (Map<Integer, String> dataRow : dataList) {
            Map<String,String> rowData = new LinkedHashMap<>();
            excelHeadIdxNameMap.entrySet().forEach(columnHead -> {
                rowData.put(columnHead.getValue(), dataRow.get(columnHead.getKey()));
            });
            excelDataList.add(rowData);
        }
        return excelDataList;
    }

    /**
     * 返回导入的所有数据
     */
    public static List<Map<String,String>> makeData(MultipartFile file){
        //转换成输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] stream = new byte[0];
        try {
            stream = IoUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(stream == null || stream.length == 0){
            return null;
        }
        List<Map<String,String>> dataList = parseExcelToData(stream, 1);//从动态获取全部列和数据体，默认从第一行开始解析数据
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    /**
     * 获取excel文件所有sheet
     * @param inputStream 文件流
     */
    public static List<ReadSheet> listSheet(InputStream inputStream){
        if(inputStream == null){
            throw new RuntimeException("inputStream is null");
        }
        ExcelReader build = EasyExcel.read(inputStream).build();
        return build.excelExecutor().sheetList();
    }
    /**
     * 获取默认表头内容的样式
     *
     * @return
     */
    public static HorizontalCellStyleStrategy getDefaultHorizontalCellStyleStrategy() {
        /** 表头样式 **/
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色（浅灰色）
        // 可以参考：https://www.cnblogs.com/vofill/p/11230387.html
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 字体大小
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        /** 内容样式 **/
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 内容字体样式（名称、大小）
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置内容垂直居中对齐
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置内容水平居中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 头样式与内容样式合并
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 文件导入测试

     */
    public static void main(String[] args) throws IOException {
        //属性项
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Chran-c\\Desktop\\ceshi2.xlsx"));
        byte[] stream = IoUtils.toByteArray(inputStream);
        List<Map<String,String>> dataList = parseExcelToData(stream, 1);//动态获取全部列和数据体，默认从第一行开始解析数据
        List<String> objects = new ArrayList<>();
        dataList.forEach(d->{
            objects.addAll(d.keySet());
        });
        System.out.println("属性项:"+objects);
        System.out.println("值:"+dataList);

        inputStream.close();

    }




}
