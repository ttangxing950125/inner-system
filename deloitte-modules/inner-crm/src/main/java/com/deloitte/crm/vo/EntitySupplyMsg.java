package com.deloitte.crm.vo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.UpdateLog;
import com.deloitte.crm.domain.EntityInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/22 14:56
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntitySupplyMsg {
    private static final long serialVersionUID = 1L;

    /**
     * 前端传入任务id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 企业名
     */
    @Excel(name = "企业名")
    @UpdateLog(fieldName = "企业名称",tableFieldName ="entity_name")
    private String entityName;

    /**
     * IB+自000001开始排序，每个企业唯一
     */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /**
     * 报告类型
     */
    @Excel(name = "报告类型")
    @UpdateLog(fieldName = "年报列示类型",tableFieldName ="report_type")
    private List<String> reportType;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "wind行业划分")
    private String windMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "申万行业划分")
    private String shenWanMaster;

    /**
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    @UpdateLog(fieldName = "统一社会信用代码",tableFieldName ="credit_code")
    private String creditCode;

    /**
     * 统一社会信用代码是否异常 0-正常 1-异常
     */

    /** 年报示例类型*/
    @ApiModelProperty(value="年报示例类型")
    @NotNull(message = "年报示例类型不能为空")
    private String listType;

    public EntityInfo getEntityInfo(){

        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setEntityCode(this.entityCode);
        entityInfo.setListType(this.listType);
        entityInfo.setWindMaster(this.windMaster);
        entityInfo.setShenWanMaster(this.shenWanMaster);
        entityInfo.setCreditCode(this.creditCode);
        entityInfo.setId(this.id);
        String value = "";
        if (!CollectionUtils.isEmpty(this.reportType)){
            StringBuffer str=new StringBuffer();
            for (int i=0;i<this.reportType.size();i++){
                str.append(reportType.get(i));
                if (i<this.reportType.size()-1){
                    str.append(",");
                }
            }
            value = str.toString();
        }
        entityInfo.setReportType(value);
        return entityInfo;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
