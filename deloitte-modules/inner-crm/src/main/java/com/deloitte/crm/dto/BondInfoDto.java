package com.deloitte.crm.dto;

import com.deloitte.crm.domain.BondInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 传输对象，当前操作的债券信息， newIss 的新增和修改状态
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
@Getter
@Setter
public class BondInfoDto {

    /**
     * 债券信息
     */
    private BondInfo bondInfo;

    /**
     * 这条债券的数据新增了，还是发生更新了
     * 如果这条 newIss 是新增的，返回1
     * 如果这条 newIss 是原有基础上有修改，返回2
     */
    private Integer resStatus;

}
