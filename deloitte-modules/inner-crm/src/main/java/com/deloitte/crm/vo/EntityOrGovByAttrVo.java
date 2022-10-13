package com.deloitte.crm.vo;

import com.deloitte.crm.domain.Products;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
    private List<Integer> proId;

    /***
     *政府主体-"GV"  企业主体-"Q"
     */
    private  String entityType;

    /***
     *主体名称(企业主体或者政府主体)
     *
     */
    @NotNull(message = "主体名称不能为空")
    private  String entityName;

    private  Integer  pageNum;

    private  Integer pageSize;

}
