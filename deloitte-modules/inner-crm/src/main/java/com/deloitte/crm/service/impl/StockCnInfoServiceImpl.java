package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.mapper.StockThkInfoMapper;
import com.deloitte.crm.service.EntityStockCnRelService;
import com.deloitte.crm.service.EntityStockThkRelService;
import com.deloitte.crm.service.StockCnInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * a股信息表，大陆股票(StockCnInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
@Service("stockCnInfoService")
@Slf4j
public class StockCnInfoServiceImpl extends ServiceImpl<StockCnInfoMapper, StockCnInfo> implements StockCnInfoService {

    @Resource
    private StockCnInfoMapper cnMapper;
    @Resource
    private StockThkInfoMapper thkMapper;
    @Resource
    private EntityStockCnRelService stockCnRelService;
    @Resource
    private EntityStockThkRelService stockThkRelService;
    @Resource
    private EntityInfoMapper entityInfoMapper;

    /**
     * 添加股票代码查重
     *
     * @param StockBode
     * @return R
     * @author penTang
     * @date 2022/9/28 22:40
     */
    @Override
    public R checkStockCnInfo(String StockBode) {
        LambdaQueryWrapper<StockCnInfo> Wrapper = new LambdaQueryWrapper<StockCnInfo>();
        StockCnInfo stockCnInfo = cnMapper.selectOne(Wrapper.eq(StockCnInfo::getStockCode, StockBode));
        if (ObjectUtils.isEmpty(stockCnInfo)) {
            return R.ok(stockCnInfo);
        }
        return R.ok(stockCnInfo);
    }

    /**
     * 股票简称查重
     *
     * @param SortName
     * @return R
     * @author penTang
     * @date 2022/9/28 22:55
     */
    @Override
    public R checkSortName(String SortName) {
        LambdaQueryWrapper<StockCnInfo> Wrapper = new LambdaQueryWrapper<StockCnInfo>();
        StockCnInfo stockCnInfo = cnMapper.selectOne(Wrapper.eq(StockCnInfo::getStockShortName, SortName));
        if (ObjectUtils.isEmpty(stockCnInfo)) {
            return R.ok(stockCnInfo);
        }
        return R.ok(stockCnInfo);
    }


    @Resource
    private RedisService redisService;

    /**
     * 根据code查询 大陆股票
     *
     * @param code
     * @return
     */
    @Override
    public StockCnInfo findByCode(String code) {
        //去缓存中查询
        StockCnInfo stockCnInfo = redisService.getCacheMapValue(CacheName.STOCK_CN_INFO, code);
        if (stockCnInfo == null) {
            stockCnInfo = baseMapper.findByCode(code);
            redisService.setCacheMapValue(CacheName.STOCK_CN_INFO, code, stockCnInfo);
        }
        return stockCnInfo;
    }

    /**
     * 保存或更新，会删缓存
     *
     * @param cnInfo
     * @return
     */
    @Override
    public StockCnInfo saveOrUpdateNew(StockCnInfo cnInfo) {
        if (cnInfo.getId() != null) {
            int count = baseMapper.updateById(cnInfo);
            cnInfo = count > 0 ? baseMapper.selectById(cnInfo.getId()) : cnInfo;
        } else {
            baseMapper.insert(cnInfo);
            DecimalFormat g1 = new DecimalFormat("000000");
            String startZeroStr = g1.format(cnInfo.getId());
            cnInfo.setStockDqCode("SA" + startZeroStr);

            baseMapper.updateById(cnInfo);
        }
        redisService.redisTemplate.opsForHash().delete(CacheName.STOCK_CN_INFO, cnInfo.getStockCode());

        return cnInfo;
    }
    /**
     * 股票退市检测跑批
     *
     * @return void
     * @author 冉浩岑
     * @date 2022/11/3 10:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkStockStatus() {
        //修改所有退市日期小于当前日期的正在上市的股票状态
        Date now = new Date();
        //查询A股的退市股票
        List<StockCnInfo> stockCnInfos = cnMapper.selectList(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockStatus, 6).le(StockCnInfo::getDelistingDate, now));
        //查询港股的退市股票
        List<StockThkInfo> stockThkInfos = thkMapper.selectList(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockStatus, 6).le(StockThkInfo::getDelistingDate, now));

        Integer count = stockCnInfos.size();
        //修改条数为0则不执行后续操作
        if (stockCnInfos.size()<1&&stockThkInfos.size()<1){
            log.info(" ===> 当日退市股票数量为 "+count+" 个");
            return;
        }

        //需要退市的所有A股股票的code
        List<String>stockCnCodes=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(stockCnInfos)){
            stockCnInfos.forEach(o->{
                o.setStockStatus(9);
                stockCnCodes.add(o.getStockDqCode());
                cnMapper.updateById(o);
            });
            log.info(" ===> 当日退市A股数量为 count=[{}]个,股票德勤唯一识别码 codes=[{}]",stockCnCodes.size(),stockCnCodes);
        }

        //需要退市的所有港股股票的code
        List<String>stockThkCodes=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(stockThkInfos)){
            stockThkInfos.forEach(o->{
                o.setStockStatus(9);
                stockThkCodes.add(o.getStockDqCode());
                thkMapper.updateById(o);
            });
            log.info(" ===> 当日退市A股数量为 count=[{}]个,股票德勤唯一识别码 codes=[{}]",stockThkCodes.size(),stockThkCodes);
        }
        //所有被修改的所有股票对应的主体Code
        List<String>entityCodes=new ArrayList<>();
        //查询A股所有被修改状态对应的主体
        if (CollectionUtils.isNotEmpty(stockCnCodes)){
            List<EntityStockCnRel>stockCnRels=stockCnRelService.selectEntityStockCnRelListByBondCodes(stockCnCodes);
            stockCnRels.forEach(o->{
                if (!entityCodes.contains(o.getEntityCode())){
                    entityCodes.add(o.getEntityCode());
                }
            });
        }
        //查询港股所有被修改状态对应的主体
        if (CollectionUtils.isNotEmpty(stockCnCodes)){
            List<EntityStockThkRel>stockThkRels=stockThkRelService.selectEntityStockThkRelListByBondCodes(stockThkCodes);
            stockThkRels.forEach(o->{
                if (!entityCodes.contains(o.getEntityCode())){
                    entityCodes.add(o.getEntityCode());
                }
            });
        }

        //根据主体code查询并修改股票发行状态
        //当前退市主体code
        List<String> downEntityCodes=new ArrayList<>();
        entityCodes.forEach(o->{
            //查询主体A股上市中的股票信息
            Integer stockCnUpCount=cnMapper.selectListByEntityCode(o);
            //查询主体港股上市中的股票信息
            Integer stockThkUpCount=thkMapper.selectListByEntityCode(o);
            //没有股票还在上市的主体，则修改主体状态
            if (stockCnUpCount<1&&stockThkUpCount<1){
                downEntityCodes.add(o);
                EntityInfo entityInfo = new EntityInfo();
                entityInfo.setEntityCode(o).setList(0);
                entityInfoMapper.update(entityInfo,new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode,o));
            }
        });
        log.info(" ===> 当日股票退市数量 count=[{}]个,主体代码 codes=[{}]",downEntityCodes.size(),downEntityCodes);


        int i=1/0;
        System.out.println(i);
    }
}
