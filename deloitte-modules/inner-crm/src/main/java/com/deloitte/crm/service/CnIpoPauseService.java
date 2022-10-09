package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnIpoPause;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * IPO-发行暂缓(CnIpoPause)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:53
 */
public interface CnIpoPauseService extends IService<CnIpoPause> {

    /**
     * 根据code查询最后一条记录
     * @param code
     * @return
     */
    CnIpoPause findLastByCode(String code);

    /**
     * 导入IPO-发行暂缓(CnIpoPause) 的任务
     * @param windTask
     * @param list
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnIpoPause> list);
}
