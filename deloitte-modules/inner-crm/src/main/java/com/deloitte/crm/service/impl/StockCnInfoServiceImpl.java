package com.deloitte.crm.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
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
    private StockCnInfoMapper mapper;
    /**
     *添加股票代码查重
     *
     * @param StockBode
     * @return R
     * @author penTang
     * @date 2022/9/28 22:40
    */
    @Override
     public R checkStockCnInfo(String StockBode){
         LambdaQueryWrapper<StockCnInfo> Wrapper = new LambdaQueryWrapper<StockCnInfo>();
         StockCnInfo stockCnInfo = mapper.selectOne(Wrapper.eq(StockCnInfo::getStockCode, StockBode));
         if (ObjectUtils.isEmpty(stockCnInfo)) {
             return R.ok(stockCnInfo);
         }
         return  R.ok(stockCnInfo);
     }
     /**
      *股票简称查重
      *
      * @param SortName
      * @return R
      * @author penTang
      * @date 2022/9/28 22:55
     */
     @Override
     public R checkSortName(String SortName){
         LambdaQueryWrapper<StockCnInfo> Wrapper = new LambdaQueryWrapper<StockCnInfo>();
         StockCnInfo stockCnInfo = mapper.selectOne(Wrapper.eq(StockCnInfo::getStockShortName, SortName));
         if (ObjectUtils.isEmpty(stockCnInfo)){
             return R.ok(stockCnInfo);
         }
         return  R.ok(stockCnInfo);
     }


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

    public static void main(String[] args) {
        String str = "IB_00005256," +
                "IB_00003631," +
                "IB_00010710," +
                "IB_00021925," +
                "IB_00018105," +
                "IB_00006221," +
                "IB_00013139," +
                "IB_00015540," +
                "IB_00015540," +
                "IB_00005677," +
                "IB_00000730," +
                "IB_00015106," +
                "IB_00006800," +
                "IB_00017838," +
                "IB_00027590," +
                "IB_00003345," +
                "IB_00002707," +
                "IB_00028181," +
                "IB_00028180," +
                "IB_00028132," +
                "IB_00026576,";

        String[] split = str.split(",");
        for (String item : split) {
            System.out.print("'"+item+"',");
        }
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

        redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_THK_INFO, cnInfo.getStockCode());

        return cnInfo;
    }
}
