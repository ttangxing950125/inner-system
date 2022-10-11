package com.deloitte.crm.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author PenTang
 * 用于企业和政府主体的快速覆盖查询
 * @date 2022/10/09 11:45
 */
@Data
@Accessors(chain = true)
public class EntityOrGovByAttrVo {
    /***
     *用于添加的列
    */
    List<Map<String,String>> mapList;

    /***
     *政府主体-"GV"  企业主体-"Q"
     */
    private  String EntityType;

    /***
     *主体名称(企业主体或者政府主体)
     *
     */
    private  String EntityName;

    private  Integer  pageNum;

    private  Integer pageSize;

}
