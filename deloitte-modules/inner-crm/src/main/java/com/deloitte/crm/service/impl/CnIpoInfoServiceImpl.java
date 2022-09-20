package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CnIpoInfoMapper;
import com.deloitte.crm.domain.CnIpoInfo;
import com.deloitte.crm.service.ICnIpoInfoService;

/**
 * IPO-新股发行资料-20210914-20221014Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CnIpoInfoServiceImpl implements ICnIpoInfoService 
{
    @Autowired
    private CnIpoInfoMapper cnIpoInfoMapper;

    /**
     * 查询IPO-新股发行资料-20210914-20221014
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return IPO-新股发行资料-20210914-20221014
     */
    @Override
    public CnIpoInfo selectCnIpoInfoById(Long id)
    {
        return cnIpoInfoMapper.selectCnIpoInfoById(id);
    }

    /**
     * 查询IPO-新股发行资料-20210914-20221014列表
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return IPO-新股发行资料-20210914-20221014
     */
    @Override
    public List<CnIpoInfo> selectCnIpoInfoList(CnIpoInfo cnIpoInfo)
    {
        return cnIpoInfoMapper.selectCnIpoInfoList(cnIpoInfo);
    }

    /**
     * 新增IPO-新股发行资料-20210914-20221014
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return 结果
     */
    @Override
    public int insertCnIpoInfo(CnIpoInfo cnIpoInfo)
    {
        return cnIpoInfoMapper.insertCnIpoInfo(cnIpoInfo);
    }

    /**
     * 修改IPO-新股发行资料-20210914-20221014
     * 
     * @param cnIpoInfo IPO-新股发行资料-20210914-20221014
     * @return 结果
     */
    @Override
    public int updateCnIpoInfo(CnIpoInfo cnIpoInfo)
    {
        return cnIpoInfoMapper.updateCnIpoInfo(cnIpoInfo);
    }

    /**
     * 批量删除IPO-新股发行资料-20210914-20221014
     * 
     * @param ids 需要删除的IPO-新股发行资料-20210914-20221014主键
     * @return 结果
     */
    @Override
    public int deleteCnIpoInfoByIds(Long[] ids)
    {
        return cnIpoInfoMapper.deleteCnIpoInfoByIds(ids);
    }

    /**
     * 删除IPO-新股发行资料-20210914-20221014信息
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return 结果
     */
    @Override
    public int deleteCnIpoInfoById(Long id)
    {
        return cnIpoInfoMapper.deleteCnIpoInfoById(id);
    }
}
