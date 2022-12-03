package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * (SynTable)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SynTable implements Serializable {
    private static final long serialVersionUID = -27693076242756973L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String name;
    /**
     * 机构前缀
     */
    @Excel(name = "机构前缀")
    private String prefix;
    /**
     * 是否使用std_entity_info
     */
    @Excel(name = "是否使用std_entity_info")
    private Integer useStdEntity;
    /**
     * 状态，1-正常；0-禁用
     */
    @Excel(name = "状态，1-正常；0-禁用")
    private Integer status;
    /**
     * 是否推送evidence：1-是，0否
     */
    @Excel(name = "是否推送evidence：1-是，0否")
    private Integer isSendEvidence;
    /**
     * 版本的名字
     */
    @Excel(name = "版本的名字")
    private String versionName;

    private Integer list;

    private Integer bond;

    private Integer finance;

    private Integer gov;

    /**
     * 是否有上面的拉取条件 list bond finance gov
     */
    @TableField(exist = false)
    private boolean hasCondition;


    public boolean judgeHasCondition(){
        return Objects.equals(list,1)
                || Objects.equals(bond,1)
                || Objects.equals(finance,1)
                || Objects.equals(gov,1);
    }
}
