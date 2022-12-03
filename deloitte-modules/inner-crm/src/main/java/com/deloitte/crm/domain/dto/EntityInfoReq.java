package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.domain.StockThkInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/28
 */
@Getter
@Setter
public class EntityInfoReq extends EntityInfo {

    private BondInfo bond;

    private StockCnInfo cnStock;

    private StockThkInfo thkStock;

    private Boolean hasBond = false;

    private Boolean hasCnStock = false;

    private Boolean hasThkStock = false;

    private String isInvestment;

    private String masterCode;
}
