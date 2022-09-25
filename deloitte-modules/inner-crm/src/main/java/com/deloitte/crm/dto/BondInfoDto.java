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
     * 债券
     */
    private BondInfo bondInfo;


    private Integer resStatus;

}
