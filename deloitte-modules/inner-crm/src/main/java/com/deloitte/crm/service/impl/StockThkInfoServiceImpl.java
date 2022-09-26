package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.mapper.StockThkInfoMapper;
import com.deloitte.crm.domain.StockThkInfo;
import com.deloitte.crm.service.StockThkInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;

/**
 * 股票信息表(StockThkInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:19
 */
@Service("stockThkInfoService")
public class StockThkInfoServiceImpl extends ServiceImpl<StockThkInfoMapper, StockThkInfo> implements StockThkInfoService {

    @Resource
    private RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockThkInfo saveOrUpdateNew(StockThkInfo thkInfo) {
        if (thkInfo.getId()!=null){
            int count = baseMapper.updateById(thkInfo);
            thkInfo = count>0 ?  baseMapper.selectById(thkInfo.getId()) : thkInfo;
        }else {
            baseMapper.insert(thkInfo);
            DecimalFormat g1=new DecimalFormat("000000");
            String startZeroStr = g1.format(thkInfo.getId());
            thkInfo.setStockDqCode("HK"+startZeroStr);

            baseMapper.updateById(thkInfo);
        }

        redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_THK_INFO, thkInfo.getStockCode());

        return thkInfo;
    }

    /**
     * 根据证券代码查询港股信息（StockThkInfo）
     * @param secIssInfoCode
     * @return
     */
    @Override
    public StockThkInfo findByCode(String secIssInfoCode) {
        //去缓存中查询
        StockThkInfo stockThkInfo = redisService.getCacheMapValue(CacheName.STOCK_THK_INFO, secIssInfoCode);
        if (stockThkInfo==null){
            stockThkInfo = baseMapper.findByCode(secIssInfoCode);
            redisService.setCacheMapValue(CacheName.STOCK_THK_INFO, secIssInfoCode, stockThkInfo);
        }


        return stockThkInfo;
    }
}
