package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnIpoFail;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * IPO-发行失败(CnIpoFail)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:49
 */
public interface CnIpoFailService extends IService<CnIpoFail> {

    /**
     * 根据code查询最新的一条 CnIpoFail
     * @param code
     * @return
     */
    CnIpoFail findLastByCode(String code);

    /**
     * 导入IPO-发行暂缓(CnIpoPause) 的任务
     * @param windTask
     * @param list
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnIpoFail> list);
}
