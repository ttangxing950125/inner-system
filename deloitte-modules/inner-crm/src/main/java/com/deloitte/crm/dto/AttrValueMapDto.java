package com.deloitte.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 正杰
 * @description: AttrValueMap
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AttrValueMapDto {

    /**
     *  table=> entity_attr 的 id
     */
    private Integer attrId;

    /**
     * table=> entity_attr 的 name
     */
    private String name;

    /**
     * table=> entity_attr 的 remarks
     */
    private String remarks;

    /**
     * table=> entity_attr_value 的 id
     */
    private Integer valueId;

    /**
     * table=> entity_attr_value 的 value
     */
    private String value;

    /**
     * 子集
     */
    private List<AttrValueMapDto> children;

    public AttrValueMapDto(Integer attrId,String name,String remarks,Integer valueId,String value){
        this.attrId = attrId;
        this.name = name;
        this.remarks = remarks;
        this.valueId = valueId;
        this.value = value;
    }

}
