package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.domain.StockThkInfo;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.service.StockCnInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;

/**
 * a股信息表，大陆股票(StockCnInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
@Service("stockCnInfoService")
public class StockCnInfoServiceImpl extends ServiceImpl<StockCnInfoMapper, StockCnInfo> implements StockCnInfoService {

    @Resource
    private RedisService redisService;

    /**
     * 根据code查询 大陆股票
     * @param code
     * @return
     */
    @Override
    public StockCnInfo findByCode(String code) {
        //去缓存中查询
        StockCnInfo stockCnInfo = redisService.getCacheMapValue(CacheName.STOCK_CN_INFO, code);

        if (stockCnInfo==null){
            stockCnInfo = baseMapper.findByCode(code);
            redisService.setCacheMapValue(CacheName.STOCK_CN_INFO, code, stockCnInfo);
        }



        return stockCnInfo;
    }

    /**
     * 保存或更新，会删缓存
     * @param cnInfo
     * @return
     */
    @Override
    public StockCnInfo saveOrUpdateNew(StockCnInfo cnInfo) {
        if (cnInfo.getId()!=null){
            int count = baseMapper.updateById(cnInfo);
            cnInfo = count>0 ?  baseMapper.selectById(cnInfo.getId()) : cnInfo;
        }else {
            baseMapper.insert(cnInfo);
            DecimalFormat g1=new DecimalFormat("000000");
            String startZeroStr = g1.format(cnInfo.getId());
            cnInfo.setStockDqCode("SA"+startZeroStr);

            baseMapper.updateById(cnInfo);
        }

        redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_CN_INFO, cnInfo.getStockCode());

        return cnInfo;
    }
}
