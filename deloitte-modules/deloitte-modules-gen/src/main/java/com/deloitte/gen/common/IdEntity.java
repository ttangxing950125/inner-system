package com.deloitte.gen.common;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 主键实体类
 * 指定主键生成策略
 * @author fangliu
 */
@Data
public class IdEntity implements Serializable {

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

}
