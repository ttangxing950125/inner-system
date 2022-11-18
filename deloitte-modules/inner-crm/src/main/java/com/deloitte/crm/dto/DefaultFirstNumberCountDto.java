package com.deloitte.crm.dto;

import com.deloitte.crm.domain.DefaultFirstNumberCount;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/09/14:37
 * @Description:
 */
@Getter
@Setter
public class DefaultFirstNumberCountDto {
    /**
     * 企业首次违约报表 信息
     */
    private DefaultFirstNumberCount info;

    /**
     * 这条债券的数据新增了，还是发生更新了
     * 如果这条 newIss 是新增的，返回1
     * 如果这条 newIss 是原有基础上有修改，返回2
     */
    private Integer resStatus;
}
