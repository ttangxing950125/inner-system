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
    private Integer id;

    private String table;
    private String name;
    private String value;

    private String remarks;

    private List<AttrValueMapDto> children;

    public AttrValueMapDto(Integer id, String table, String name, String value){
        this.id = id;
        this.table = table;
        this.name = name;
        this.value = value;
    }

    public AttrValueMapDto(Integer id, String table, String name, String value,String remarks){
        this.id = id;
        this.table = table;
        this.name = name;
        this.value = value;
        this.remarks = remarks;
    }

}
