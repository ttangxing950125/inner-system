package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.deloitte.crm.excelUtils.ExcelImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 冉浩岑
 * @date 2022/10/25 17:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("entity_info")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntityImport implements Serializable {
    
    @ExcelImport("企业名称")
    private String entityName;
    @ExcelImport("企业德勤唯一识别码")
    private String entityCode;
    @ExcelImport("统一社会信用代码")
    private String creditCode;
    @ExcelImport("是否上市 0.否 1.是")
    private String list;
    @ExcelImport("是否金融机构 0.否 1.是")
    private String finance;
    @ExcelImport("是否发债 0.否 1.是")
    private String issueBonds;
    @ExcelImport("是否生效 0.否 1.是")
    private String status;
    @ExcelImport("统一社会信用代码是否异常 0-正常 1-异常")
    private String creditError;
    @ExcelImport("社会信用代码异常备注")
    private String creditErrorRemark;
    @ExcelImport("统一社会信用代码状态描述，1、吊销 2、注销 3、非大陆注册机构 4、其他未知原因 5、正常")
    private String creditErrorType;
    @ExcelImport("所有的曾用名或别称")
    private String entityNameHis;
}
