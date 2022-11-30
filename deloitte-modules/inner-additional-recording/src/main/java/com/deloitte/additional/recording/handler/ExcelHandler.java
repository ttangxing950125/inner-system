package com.deloitte.additional.recording.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/26
 * @描述 excel handler
 */

public class ExcelHandler implements SheetWriteHandler {

    /**
     * 下拉框值
     */
    private Map<Integer,String[]> dropDownMap;

    /**
     * 多少行有下拉
     */
    private final static Integer rowSize = 200;

    public ExcelHandler(Map<Integer,String[]> dropDownMap) {
        this.dropDownMap = dropDownMap;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();

        dropDownMap.forEach((celIndex, strings) -> {
            // 区间设置
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, rowSize, celIndex, celIndex);
            // 下拉内容
            DataValidationConstraint constraint = helper.createExplicitListConstraint(strings);
            DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(dataValidation);
        });
    }
}
