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
