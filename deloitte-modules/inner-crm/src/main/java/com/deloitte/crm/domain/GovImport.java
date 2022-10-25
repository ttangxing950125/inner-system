package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.deloitte.crm.excelUtils.ExcelImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/25 17:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("gov_info")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GovImport {

    private int rowNum;

    @ExcelImport("政府名称")
    private String govName;
    @ExcelImport("政府统一社会代码")
    private String govCode;
    @ExcelImport("上级政府统一社会代码")
    private String preCode;
    @ExcelImport("上级政府德勤唯一识别码")
    private String preGovCode;
    @ExcelImport("上级政府名称")
    private String preGovName;
    @ExcelImport("政府德勤唯一识别码")
    private String dqGovCode;
    @ExcelImport("政府主体行政单位级别-大类")
    private String govLevelBig;
    @ExcelImport("政府主体行政单位级别-小类")
    private String govLevelSmall;
}
