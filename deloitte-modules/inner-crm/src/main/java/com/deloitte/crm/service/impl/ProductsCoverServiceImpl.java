package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.CoverRule;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.ProductCoverDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.ProductsCoverService;
import com.deloitte.crm.vo.EntityOrGovByAttrVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author PenTang
 * @date 2022/10/12 16:51
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductsCoverServiceImpl extends ServiceImpl<ProductsCoverMapper, ProductsCover> implements ProductsCoverService {


    private EntityInfoMapper entityInfoMapper;

    private ProductsCoverMapper productCoverMapper;

    private ProductsMapper productsMapper;

    private EntityStockCnRelMapper entityStockCnRelmapper;

    private StockCnInfoMapper stockCnInfomapper;


    /**
     * 查询覆盖情况
     *
     * @return List<ProductCoverDto>
     * @author penTang
     * @date 2022/10/12 18:58
     */
    @Override
    public Page<ProductCoverDto> getProducts(EntityOrGovByAttrVo entityOrGovByAttrVo) {
        // 获取 需要查询的产品
        List<Integer> proId = entityOrGovByAttrVo.getProId();
        //参数进行校验
        if (proId.contains(9999) || CollUtil.isEmpty(proId)) {
            proId = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
        }
        //返回结果
        List<ProductCoverDto> result = new ArrayList<>();

        //分页查询多少主体
        Page<EntityInfo> page = new Page<>(entityOrGovByAttrVo.getPageNum(), entityOrGovByAttrVo.getPageSize());
        QueryWrapper<EntityInfo> like = new QueryWrapper<EntityInfo>();
        LambdaQueryWrapper<EntityInfo> like1;
        if (entityOrGovByAttrVo.getEntityName() == null || entityOrGovByAttrVo.getEntityName().equals("")) {
            like1 = like.lambda();
        } else {
            like1 = like.lambda().like(EntityInfo::getEntityName, entityOrGovByAttrVo.getEntityName());
        }
        Page<EntityInfo> page1 = entityInfoMapper.selectPage(page, like1);
        List<EntityInfo> records = page1.getRecords();
        if (CollUtil.isEmpty(records)) {
            R.fail("未查询到该主体");
        }

        //组装返回结果
        List<Integer> proIds = proId;
        records.forEach(o -> {
            //返回添加列的结果
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            ProductCoverDto temp = new ProductCoverDto();
            for (Integer integer : proIds) {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                //当前主体关联的产品覆盖情况
                ProductsCover productsCover = productCoverMapper.selectOne(new QueryWrapper<ProductsCover>().lambda().eq(ProductsCover::getProId, integer).eq(ProductsCover::getEntityCode, o.getEntityCode()).eq(ProductsCover::getIsGov, 0));
                if (productsCover == null) {
                    Products products = productsMapper.selectById(integer);
                    stringStringHashMap.put("key", products.getProName());
                    stringStringHashMap.put("value", "未覆盖");
                    stringStringHashMap.put("color", "0");
                    maps.add(stringStringHashMap);
                } else {
                    //当前主体的产品名称
                    Products products = productsMapper.selectById(integer);
                    stringStringHashMap.put("key", products.getProName());
                    stringStringHashMap.put("value", productsCover.getCoverDes());
                    stringStringHashMap.put("color", productsCover.getIsCover());
                    maps.add(stringStringHashMap);
                }
            }
            temp.setEntityCode(o.getEntityCode());
            temp.setCreditCode(o.getCreditCode());
            temp.setEntityName(o.getEntityName());
            temp.setStatus(o.getStatus());
            temp.setResult(maps);
            result.add(temp);
        });
        Page<ProductCoverDto> oageResult = new Page<>(entityOrGovByAttrVo.getPageNum(), entityOrGovByAttrVo.getPageSize());
        oageResult.setRecords(result).setTotal(page1.getTotal());
        return oageResult;
    }

    @Override
    public void CoverRule() {
        log.info("=>> " + DateUtil.dateTimeNow() + "组装覆盖情况");
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(null);
        List<ProductsCover> productsCovers = new ArrayList<>();
        for (int i = 0; i < entityInfos.size(); i++) {
            log.info("进度=>" + i + 1 + "<=");
            //组装ESG
            List<ProductsCover> productsCoversE = this.CoverRuleEsg(entityInfos.get(i), productsCovers);
            //组装产业链
            List<ProductsCover> productsCoversC = this.CoverRuleClTE(entityInfos.get(i), productsCoversE);
            //组装股票
            this.CoverRuleSTOCK(entityInfos.get(i), productsCoversC);

        }
        System.out.println(productsCovers);
        //入库
        int updateSum = 0;
        int saveSum = 0;
        for (ProductsCover productsCover : productsCovers) {
            LambdaQueryWrapper<ProductsCover> qw = new LambdaQueryWrapper<ProductsCover>().eq(ProductsCover::getEntityCode, productsCover.getEntityCode())
                    .eq(ProductsCover::getProId, productsCover.getProId())
                    .eq(ProductsCover::getIsGov, 0);
            ProductsCover one = getOne(qw);
            if (one != null) {
                one.setIsCover(productsCover.getIsCover());
                one.setCoverDes(productsCover.getCoverDes());
                boolean b = updateById(one);
                if (b) {
                    // 统计更新了多少条
                    updateSum++;
                }
            } else {
                boolean save = save(productsCover);
                if (save) {
                    //统计新增了多少条
                    saveSum++;
                }

            }

        }
        log.info("总数：{}", productsCovers.size());
        log.info("更新：{}", updateSum);
        log.info("新增：{}", saveSum);
        productsCovers.clear();
    }

    /**
     * 组装
     *
     * @param entityInfo
     * @param productsCovers
     * @return List<ProductsCover>
     * @author penTang
     * @date 2022/10/28 15:11
     */
    public List<ProductsCover> CoverRuleEsg(EntityInfo entityInfo, List<ProductsCover> productsCovers) {
        log.info("组装ESG");
        LambdaQueryWrapper<EntityStockCnRel> eq = new LambdaQueryWrapper<EntityStockCnRel>().eq(EntityStockCnRel::getEntityCode, entityInfo.getEntityCode());
        List<EntityStockCnRel> entityStockCnRels = entityStockCnRelmapper.selectList(eq);

        ArrayList<StockCnInfo> stockCnInfos = new ArrayList<>();
        ProductsCover productsCoverC = new ProductsCover();
        if (CollUtil.isNotEmpty(entityStockCnRels)) {
            for (EntityStockCnRel entityStockCnRel : entityStockCnRels) {
                LambdaQueryWrapper<StockCnInfo> eq1 = new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockDqCode, entityStockCnRel.getStockDqCode());
                StockCnInfo stockCnInfo = Optional.ofNullable(stockCnInfomapper.selectOne(eq1)).orElse(null);
                if (stockCnInfo != null) {
                    stockCnInfos.add(stockCnInfo);
                }

            }
        }
        if (CollUtil.isNotEmpty(stockCnInfos)) {
            for (StockCnInfo stockCnInfo : stockCnInfos) {
                if (Objects.equals(stockCnInfo.getStockStatus(), 6) || stockCnInfo.getListDate() != null) {
                    productsCoverC.setProId(CoverRule.ESG_ID);
                    productsCoverC.setIsCover("1");
                    productsCoverC.setEntityCode(entityInfo.getEntityCode());
                    productsCoverC.setIsGov("0");
                    productsCoverC.setCoverDes("应覆盖（A股上市）");
                    productsCovers.add(productsCoverC);
                    break;
                } else if (stockCnInfo.getDelistingDate() == null || !Objects.equals(stockCnInfo.getStockStatus(), 9)) {
                    productsCoverC.setProId(CoverRule.ESG_ID);
                    productsCoverC.setIsCover("0");
                    productsCoverC.setEntityCode(entityInfo.getEntityCode());
                    productsCoverC.setIsGov("0");
                    productsCoverC.setCoverDes("未覆盖（未A股上市）");
                    productsCovers.add(productsCoverC);
                    break;
                }
            }
            if (CollUtil.isEmpty(productsCovers)) {
                productsCoverC.setProId(CoverRule.ESG_ID);
                productsCoverC.setIsCover("0");
                productsCoverC.setEntityCode(entityInfo.getEntityCode());
                productsCoverC.setIsGov("0");
                productsCoverC.setCoverDes("不再覆盖（A股退市）");
                productsCovers.add(productsCoverC);
            }


        } else {

            productsCoverC.setProId(CoverRule.ESG_ID);
            productsCoverC.setIsCover("0");
            productsCoverC.setEntityCode(entityInfo.getEntityCode());
            productsCoverC.setIsGov("0");
            productsCoverC.setCoverDes("未覆盖（未A股上市）");
            productsCovers.add(productsCoverC);
        }
        return productsCovers;
    }

    public List<ProductsCover> CoverRuleClTE(EntityInfo entityInfo, List<ProductsCover> productsCovers) {
        log.info("组装CLTE");
        LambdaQueryWrapper<EntityStockCnRel> eq = new LambdaQueryWrapper<EntityStockCnRel>().eq(EntityStockCnRel::getEntityCode, entityInfo.getEntityCode());
        List<EntityStockCnRel> entityStockCnRels = entityStockCnRelmapper.selectList(eq);
        ArrayList<StockCnInfo> stockCnInfos = new ArrayList<>();
        ProductsCover productsCoverC = new ProductsCover();
        if (!entityStockCnRels.isEmpty()) {
            for (EntityStockCnRel entityStockCnRel : entityStockCnRels) {
                LambdaQueryWrapper<StockCnInfo> eq1 = new LambdaQueryWrapper<StockCnInfo>().
                        eq(StockCnInfo::getStockDqCode, entityStockCnRel.getStockDqCode());
                StockCnInfo stockCnInfo = stockCnInfomapper.selectOne(eq1);
                if (stockCnInfo != null) {
                    stockCnInfos.add(stockCnInfo);
                }
            }
        }
        if (!stockCnInfos.isEmpty()) {
            for (StockCnInfo stockCnInfo : stockCnInfos) {
                if (Objects.equals(stockCnInfo.getStockStatus(), 6) || stockCnInfo.getListDate() != null) {
                    productsCoverC.setProId(CoverRule.CT_LE_ID);
                    productsCoverC.setIsCover("1");
                    productsCoverC.setEntityCode(entityInfo.getEntityCode());
                    productsCoverC.setIsGov("0");
                    productsCoverC.setCoverDes("应覆盖（A股上市）");
                    productsCovers.add(productsCoverC);
                    break;
                } else if (stockCnInfo.getDelistingDate() == null || !Objects.equals(stockCnInfo.getStockStatus(), 9)) {
                    productsCoverC.setProId(CoverRule.CT_LE_ID);
                    productsCoverC.setIsCover("0");
                    productsCoverC.setEntityCode(entityInfo.getEntityCode());
                    productsCoverC.setIsGov("0");
                    productsCoverC.setCoverDes("未覆盖（未A股上市）");
                    productsCovers.add(productsCoverC);
                    break;
                }
            }
            if (productsCovers.isEmpty()) {
                productsCoverC.setProId(CoverRule.CT_LE_ID);
                productsCoverC.setIsCover("0");
                productsCoverC.setEntityCode(entityInfo.getEntityCode());
                productsCoverC.setIsGov("0");
                productsCoverC.setCoverDes("不再覆盖（A股退市）");
                productsCovers.add(productsCoverC);
            }

        }

        return productsCovers;

    }


    public List<ProductsCover> CoverRuleSTOCK(EntityInfo entityInfo, List<ProductsCover> productsCovers) {
        log.info("组装股票");
        LambdaQueryWrapper<EntityStockCnRel> eq = new LambdaQueryWrapper<EntityStockCnRel>().eq(EntityStockCnRel::getEntityCode, entityInfo.getEntityCode());
        List<EntityStockCnRel> entityStockCnRels = entityStockCnRelmapper.selectList(eq);
        ArrayList<StockCnInfo> stockCnInfos = new ArrayList<>();
        ProductsCover productsCoverC = new ProductsCover();
        if (!entityStockCnRels.isEmpty()) {
            for (EntityStockCnRel entityStockCnRel : entityStockCnRels) {
                LambdaQueryWrapper<StockCnInfo> eq1 = new LambdaQueryWrapper<StockCnInfo>().
                        eq(StockCnInfo::getStockDqCode, entityStockCnRel.getStockDqCode());
                StockCnInfo stockCnInfo = stockCnInfomapper.selectOne(eq1);
                if (stockCnInfo != null) {
                    stockCnInfos.add(stockCnInfo);
                }

            }
        }
        if (!stockCnInfos.isEmpty()) {
            for (StockCnInfo stockCnInfo : stockCnInfos) {
                if (Objects.equals(stockCnInfo.getStockStatus(), 6) || stockCnInfo.getListDate() != null) {
                    productsCoverC.setProId(CoverRule.STOCK_ID);
                    productsCoverC.setIsCover("1");
                    productsCoverC.setEntityCode(entityInfo.getEntityCode());
                    productsCoverC.setIsGov("0");
                    productsCoverC.setCoverDes("应覆盖（A股上市）");
                    productsCovers.add(productsCoverC);
                    break;
                } else if (stockCnInfo.getDelistingDate() == null || !Objects.equals(stockCnInfo.getStockStatus(), 9)) {
                    productsCoverC.setProId(CoverRule.STOCK_ID);
                    productsCoverC.setIsCover("0");
                    productsCoverC.setEntityCode(entityInfo.getEntityCode());
                    productsCoverC.setIsGov("0");
                    productsCoverC.setCoverDes("未覆盖（未A股上市）");
                    productsCovers.add(productsCoverC);
                    break;
                }
            }
            if (productsCovers.isEmpty()) {
                productsCoverC.setProId(CoverRule.STOCK_ID);
                productsCoverC.setIsCover("0");
                productsCoverC.setEntityCode(entityInfo.getEntityCode());
                productsCoverC.setIsGov("0");
                productsCoverC.setCoverDes("不再覆盖（A股退市）");
                productsCovers.add(productsCoverC);
            }

        }
        return productsCovers;
    }


}
