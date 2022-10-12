package com.deloitte.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 正杰
 * @description: CrmEntityTaskVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class CrmEntityTaskVo {

    private Integer id;

    private String taskCategory;

    private Integer sourceId;

    private Integer sourceType;

    private String dataCode;

    private Integer dataSource;

    private String dataShow;

    /**
     * 引发主体新增的文件记录json
     */
    private String details;

    /**
     * 债券代码，债券简称
     * 股票代码，股票简称之类
     */
    private String infos;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date taskDate;

    private Integer state;

    private String handleUser;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date update;

    private String bondShortName;

    private String bondFullName;

    private String creditCode;

    private Boolean enableCreditCode;

}
