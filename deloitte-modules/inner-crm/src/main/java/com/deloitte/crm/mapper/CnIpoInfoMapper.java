package com.deloitte.crm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CnIpoInfo;

/**
 * IPO-新股发行资料-20210914-20221014Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface CnIpoInfoMapper extends BaseMapper<CnIpoInfo>
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
     * 删除IPO-新股发行资料-20210914-20221014
     * 
     * @param id IPO-新股发行资料-20210914-20221014主键
     * @return 结果
     */
    public int deleteCnIpoInfoById(Long id);

    /**
     * 批量删除IPO-新股发行资料-20210914-20221014
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCnIpoInfoByIds(Long[] ids);

    /**
     * 根据code查询最后一个  IPO-新股发行资料
     * @param code
     * @return
     */
    CnIpoInfo findLastByCode(String code);
}
