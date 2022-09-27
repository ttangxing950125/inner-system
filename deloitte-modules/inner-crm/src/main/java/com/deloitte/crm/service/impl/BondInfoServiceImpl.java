package com.deloitte.crm.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.service.IEntityAttrService;
import com.deloitte.crm.service.IEntityAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.BondInfoMapper;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.service.IBondInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-23
 */
@Service
public class BondInfoServiceImpl implements IBondInfoService 
{
    @Resource
    private BondInfoMapper bondInfoMapper;

    @Resource
    private IEntityAttrService entityAttrService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private RedisService redisService;



    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public BondInfo selectBondInfoById(Long id)
    {
        return bondInfoMapper.selectBondInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param bondInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<BondInfo> selectBondInfoList(BondInfo bondInfo)
    {
        return bondInfoMapper.selectBondInfoList(bondInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param bondInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertBondInfo(BondInfo bondInfo)
    {
        return bondInfoMapper.insertBondInfo(bondInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param bondInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateBondInfo(BondInfo bondInfo)
    {
        return bondInfoMapper.updateBondInfo(bondInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBondInfoByIds(Long[] ids)
    {
        return bondInfoMapper.deleteBondInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBondInfoById(Long id)
    {
        return bondInfoMapper.deleteBondInfoById(id);
    }


    /**
     * 根据债券简称查询
     * @param shortName
     * @return
     */
    @Override
    public BondInfo findByShortName(String shortName) {
        BondInfo bondInfo = redisService.getCacheMapValue(CacheName.BOND_CACHE, shortName);
        if (bondInfo==null){
            bondInfo = bondInfoMapper.findByShortName(shortName);
            redisService.setCacheMapValue(CacheName.BOND_CACHE, shortName, bondInfo);
        }

        return bondInfo;
    }

    /**
     * 根据id保存或新增
     *
     * @param bondInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BondInfo saveOrUpdate(BondInfo bondInfo) {
        if (bondInfo.getId()!=null){
            int count = bondInfoMapper.updateBondInfo(bondInfo);
            bondInfo = count>0 ? bondInfoMapper.selectBondInfoById(bondInfo.getId()) : bondInfo;
        }else{
            //新增债券
            bondInfoMapper.insertBondInfo(bondInfo);
            DecimalFormat g1=new DecimalFormat("000000");
            String startZeroStr = g1.format(bondInfo.getId());
            bondInfo.setBondCode("BD"+startZeroStr);

            bondInfoMapper.updateBondInfo(bondInfo);
        }

        redisService.redisTemplate.opsForHash().delete(CacheName.BOND_CACHE, bondInfo.getBondShortName());


        return bondInfo;
    }

}
