package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.BondDelIss;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * 新债发行-推迟或取消发行债券(BondDelIss)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 12:11:32
 */
public interface BondDelIssService extends IService<BondDelIss> {

    /**
     * 完成当前wind文件的任务
     * @param windTask
     * @param delIsses
     * @return
     */
    Object doTask(CrmWindTask windTask, List<BondDelIss> delIsses);

    /**
     * 根据债券简称查询数据
     * @param shortName
     * @return
     */
    List<BondDelIss> findByBondName(String shortName);
}
