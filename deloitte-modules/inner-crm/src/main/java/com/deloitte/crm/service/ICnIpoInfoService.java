package com.deloitte.crm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnIpoInfo;
import com.deloitte.crm.domain.CrmWindTask;

/**
 * IPO-新股发行资料-20210914-20221014Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICnIpoInfoService extends IService<CnIpoInfo>
{
    /**
     * 查询IPO-新股发行资料-20210914-20221014
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return IPO-新股发行资料-20210914-20221014
     */
    public CnIpoInfo selectCnIpoInfoById(Long id);

    /**
     * 查询IPO-新股发行资料-20210914-20221014列表
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return IPO-新股发行资料-20210914-20221014集合
     */
    public List<CnIpoInfo> selectCnIpoInfoList(CnIpoInfo cnIpoInfo);

    /**
     * 新增IPO-新股发行资料-20210914-20221014
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return 结果
     */
    public int insertCnIpoInfo(CnIpoInfo cnIpoInfo);

    /**
     * 修改IPO-新股发行资料-20210914-20221014
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return 结果
     */
    public int updateCnIpoInfo(CnIpoInfo cnIpoInfo);

    /**
     * 批量删除IPO-新股发行资料-20210914-20221014
     * 
     * @param ids 需要删除的IPO-新股发行资料-20210914-20221014主键集合
     * @return 结果
     */
    public int deleteCnIpoInfoByIds(Long[] ids);

    /**
     * 删除IPO-新股发行资料-20210914-20221014信息
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return 结果
     */
    public int deleteCnIpoInfoById(Long id);

    /**
     * 根据code查询最后一个  IPO-新股发行资料
     * @param code
     * @return
     */
    CnIpoInfo findLastByCode(String code);

    /**
     * 导入完成IPO-新股发行资料 的任务
     * @param windTask
     * @param list
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnIpoInfo> list);
}
