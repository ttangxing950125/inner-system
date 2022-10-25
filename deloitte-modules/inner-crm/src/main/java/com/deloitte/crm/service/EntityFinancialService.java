package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.vo.EntitySupplyMsgBack;

/**
 * (EntityFinancial)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
public interface EntityFinancialService extends IService<EntityFinancial> {

    R addFinEntitySubtableMsg(EntitySupplyMsgBack entitySupplyMsgBack);
}
