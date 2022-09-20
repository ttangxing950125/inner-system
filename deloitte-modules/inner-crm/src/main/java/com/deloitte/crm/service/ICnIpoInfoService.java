package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.CnIpoInfo;

/**
 * IPO-新股发行资料-20210914-20221014Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICnIpoInfoService 
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
}
