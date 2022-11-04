package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.CoverRule;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.CoverStatus;
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

import static cn.hutool.poi.excel.sax.ElementName.row;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

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

    private EntityStockThkRelMapper entityStockThkRelmapper;

    private StockThkInfoMapper stockThkInfoMapper;

    private StockCnInfoMapper stockCnInfomapper;

    private EntityBondRelMapper entityBondRelMapper;

    private BondInfoMapper bondInfoMapper;

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
        //主体
        for (int i = 0; i < entityInfos.size(); i++) {
            log.info("进度=>" + i + 1 + "<=");
            //查询改主体下的A股票
            LambdaQueryWrapper<EntityStockCnRel> eq = new LambdaQueryWrapper<EntityStockCnRel>().eq(EntityStockCnRel::getEntityCode, entityInfos.get(i).getEntityCode());
            List<EntityStockCnRel> entityStockCnRels = entityStockCnRelmapper.selectList(eq);
            ArrayList<StockCnInfo> stockCnInfos = new ArrayList<>();
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
            //查询该主体下的港股
            LambdaQueryWrapper<EntityStockThkRel> eq1 = new LambdaQueryWrapper<EntityStockThkRel>().eq(EntityStockThkRel::getEntityCode, entityInfos.get(i).getEntityCode());
            List<EntityStockThkRel> entityStockThkRels = entityStockThkRelmapper.selectList(eq1);
            ArrayList<StockThkInfo> stockThkInfo = new ArrayList<>();
            if (!entityStockThkRels.isEmpty()) {
                for (EntityStockThkRel entityStockThkRel : entityStockThkRels) {
                    LambdaQueryWrapper<StockThkInfo> sth = new LambdaQueryWrapper<StockThkInfo>().
                            eq(StockThkInfo::getStockDqCode, entityStockThkRel.getStockDqCode());
                    StockThkInfo stockThkInfo1 = stockThkInfoMapper.selectOne(sth);
                    if (stockThkInfo1 != null) {
                        stockThkInfo.add(stockThkInfo1);
                    }

                }
            }
            //查询该主体的债券
            LambdaQueryWrapper<EntityBondRel> be = new LambdaQueryWrapper<EntityBondRel>().eq(EntityBondRel::getEntityCode, entityInfos.get(i).getEntityCode());
            List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(be);
            ArrayList<BondInfo> BondInfos = new ArrayList<>();
            if (!entityBondRels.isEmpty()) {
                for (EntityBondRel EntityBondRel : entityBondRels) {
                    LambdaQueryWrapper<BondInfo> sth = new LambdaQueryWrapper<BondInfo>().
                            eq(BondInfo::getBondCode, EntityBondRel.getBdCode());
                    BondInfo bondInfo = bondInfoMapper.selectOne(sth);
                    if (bondInfo != null) {
                        BondInfos.add(bondInfo);
                    }

                }
            }
            //组装ESG
            List<ProductsCover> productsCoversE = this.CoverRuleEsg(entityInfos.get(i), productsCovers, stockCnInfos);
            //组装产业链
            List<ProductsCover> productsCoversC = this.CoverRuleClTE(entityInfos.get(i), productsCoversE, stockCnInfos);
            //组装股票
            List<ProductsCover> productsCoversS = this.CoverRuleSTOCK(entityInfos.get(i), productsCoversC, stockCnInfos);
            //组装财报智评
            this.CoverRuleIDOU(entityInfos.get(i), productsCoversS, stockCnInfos, stockThkInfo, BondInfos);

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
     * ESG判断规则
     *
     * @param entityInfo
     * @param productsCovers
     * @return List<ProductsCover>
     * @author penTang
     * @date 2022/10/28 15:11
     */
    public List<ProductsCover> CoverRuleEsg(EntityInfo entityInfo, List<ProductsCover> productsCovers, ArrayList<StockCnInfo> stockCnInfos) {
        log.info("组装ESG");
        ProductsCover productsCoverC = new ProductsCover();

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

    /**
     * 产业链的判断规则
     *
     * @param entityInfo
     * @param productsCovers
     * @param stockCnInfos
     * @return List<ProductsCover>
     * @author penTang
     * @date 2022/11/3 18:38
     */
    public List<ProductsCover> CoverRuleClTE(EntityInfo entityInfo, List<ProductsCover> productsCovers, ArrayList<StockCnInfo> stockCnInfos) {
        log.info("组装CLTE");
        ProductsCover productsCoverC = new ProductsCover();
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

    /**
     * 股票的判断规则
     *
     * @param entityInfo
     * @param productsCovers
     * @param stockCnInfos
     * @return List<ProductsCover>
     * @author penTang
     * @date 2022/11/3 18:38
     */
    public List<ProductsCover> CoverRuleSTOCK(EntityInfo entityInfo, List<ProductsCover> productsCovers, ArrayList<StockCnInfo> stockCnInfos) {
        log.info("组装股票");
        ProductsCover productsCoverC = new ProductsCover();
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

    /**
     * 财报智评-判断规则
     *
     * @param entityInfo     主体
     * @param productsCovers 入库的规则
     * @param stockCnInfos   股票
     * @return List<ProductsCover>
     * @author penTang
     * @date 2022/11/3 18:38
     */
    public List<ProductsCover> CoverRuleIDOU(EntityInfo entityInfo, List<ProductsCover> productsCovers, ArrayList<StockCnInfo> stockCnInfos, ArrayList<StockThkInfo> stockThkInfos, ArrayList<BondInfo> BondInfoList) {
        log.info("组装财报智评");
        ArrayList<CoverStatus> coverStatuses = new ArrayList<>();
        //A股的初始覆盖状态
        CoverStatus coverStatusA = new CoverStatus();
        //港股的初始覆盖状态
        CoverStatus coverStatusG = new CoverStatus();
        //债券的初始覆盖状态
        CoverStatus coverStatusB = new CoverStatus();
        //入库的对象
        ProductsCover productsCover = new ProductsCover();
        productsCover.setProId(CoverRule.CA_ZP_ID);
        productsCover.setEntityCode(entityInfo.getEntityCode());
        productsCover.setIsGov("0");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("银行");
        strings.add("金融");
        strings.add("多元金融");
        strings.add("保险II");
        strings.add("非银金融");
        ArrayList<String> ArrayList = new ArrayList<>();
        ArrayList.add("银行");
        ArrayList.add("非银金融");

        //ShenWan的触发规则
        String[] splitS = entityInfo.getShenWanMaster().split("--");
        List<String> collectByS = Arrays.stream(splitS).collect(Collectors.toList());
        //取一级
        String s1 = collectByS.get(0);
        //wind触发规则
        String[] splitW = entityInfo.getWindMaster().split("--");
        List<String> collectByW = Arrays.stream(splitW).collect(Collectors.toList());
        String s2 = collectByW.get(1);

        //根据ShenWan判断是否金融机构
        boolean containsW = ArrayList.contains(s1);
        //根据Wand判断是否金融机构
        boolean containsS = strings.contains(s2);

        boolean fins = containsW || containsS;
        //A股
        if (CollUtil.isNotEmpty(stockCnInfos)) {
            boolean A = stockCnInfos.stream().anyMatch(row -> Objects.equals(row.getStockStatus(), 6) || row.getListDate() != null);
            if (A) {
                if (A && fins) {
                    coverStatusA.setStatus("覆盖");
                    coverStatusA.setCoverDes("应覆盖(A股上市非金融)");
                    coverStatuses.add(coverStatusA);
                } else {
                    coverStatusA.setStatus("未覆盖");
                    coverStatusA.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融");
                    coverStatuses.add(coverStatusA);
                }

            } else {
                boolean b = stockCnInfos.stream().allMatch(row -> row.getDelistingDate() != null || Objects.equals(row.getStockStatus(), 9));
                if (b) {
                    coverStatusA.setStatus("不再覆盖");
                    coverStatusA.setCoverDes("不再覆盖(A股退市)");
                    coverStatuses.add(coverStatusA);
                } else {
                    coverStatusA.setStatus("未覆盖");
                    coverStatusA.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融）");
                    coverStatuses.add(coverStatusA);
                }
            }
        } else {
            coverStatusA.setStatus("未覆盖");
            coverStatusA.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融）");
            coverStatuses.add(coverStatusA);
        }
        //港股
        if (CollUtil.isNotEmpty(stockThkInfos)) {
            boolean G = stockThkInfos.stream().anyMatch(row -> Objects.equals(row.getStockStatus(), 3) || row.getListDate() != null);
            if (G) {
                if (G && fins) {
                    coverStatusG.setStatus("覆盖");
                    coverStatusG.setCoverDes("应覆盖(A股上市非金融)");
                    coverStatuses.add(coverStatusG);
                } else {
                    coverStatusG.setStatus("未覆盖");
                    coverStatusG.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融");
                    coverStatuses.add(coverStatusG);
                }

            } else {
                boolean b = stockCnInfos.stream().allMatch(row -> row.getDelistingDate() != null || Objects.equals(row.getStockStatus(), 4));
                if (b) {
                    coverStatusG.setStatus("不再覆盖");
                    coverStatusG.setCoverDes("不再覆盖(A股退市)");
                    coverStatuses.add(coverStatusG);
                } else {
                    coverStatusG.setStatus("未覆盖");
                    coverStatusG.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融）");
                    coverStatuses.add(coverStatusG);
                }

            }
        } else {
            coverStatusG.setStatus("未覆盖");
            coverStatusG.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融）");
            coverStatuses.add(coverStatusG);
        }
        //债券
        if (CollUtil.isNotEmpty(BondInfoList)) {
            boolean b = BondInfoList.stream().anyMatch(row -> Objects.equals(row.getRaiseType(), 0)
                    && Objects.equals(row.getColl(), 0)
                    && Objects.equals(row.getAbs(), 0)
                    && Objects.equals(row.getCitiinvestWindTag(), "否")
                    && Objects.equals(row.getCitiinvestYyTag(), "否")
                    && Objects.equals(row.getBondState(), 0)
            );
            if (b) {
                if (b && fins) {
                    coverStatusB.setStatus("覆盖");
                    coverStatusB.setCoverDes("应覆盖(存续债券非金融)");
                    coverStatuses.add(coverStatusB);
                } else {
                    coverStatusB.setStatus("未覆盖");
                    coverStatusB.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融");
                    coverStatuses.add(coverStatusB);
                }
            } else {
                List<BondInfo> collect = BondInfoList.stream().filter(o -> Objects.equals(o.getRaiseType(), 0)).collect(Collectors.toList());
                boolean b1 = collect.stream().anyMatch(row -> Objects.equals(row.getBondStatus(), 7) || Objects.equals(row.getBondStatus(), 8) || Objects.equals(row.getBondStatus(), 9));
                if (b1) {
                    coverStatusB.setStatus("不在覆盖");
                    coverStatusG.setCoverDes("不在覆盖(无存续公募债)");
                    coverStatuses.add(coverStatusB);
                } else {
                    coverStatusB.setStatus("未覆盖");
                    coverStatusB.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融");
                    coverStatuses.add(coverStatusB);
                }

            }

        } else {
            coverStatusB.setStatus("未覆盖");
            coverStatusB.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融");
            coverStatuses.add(coverStatusB);
        }
        //最终组装结果
        boolean cover = coverStatuses.stream().anyMatch(row -> row.getStatus().equals("覆盖"));
        if (cover) {
            productsCover.setIsCover("1");
            List<CoverStatus> coverListC = coverStatuses.stream().filter(o -> o.getStatus().equals("覆盖")).collect(Collectors.toList());
            StringBuffer string = new StringBuffer("");
            for (int i = 0; i < coverListC.size(); ++i) {
                if (i == 0) {
                    string.append(coverListC.get(i).getCoverDes());
                } else {
                    string.append("," + coverListC.get(i).getCoverDes());
                }
            }
            productsCover.setCoverDes(string.toString());

        } else {
            boolean covers = coverStatuses.stream().anyMatch(row -> row.getStatus().equals("不再覆盖"));
            List<CoverStatus> coverListN = coverStatuses.stream().filter(o -> o.getStatus().equals("不再覆盖")).collect(Collectors.toList());
            StringBuffer string = new StringBuffer("");
            if (covers) {
                productsCover.setIsCover("0");
                for (int i = 0; i < coverListN.size(); ++i) {
                    if (i == 0) {
                        string.append(coverListN.get(i).getCoverDes());
                    } else {
                        string.append("," + coverListN.get(i).getCoverDes());
                    }
                }
                productsCover.setCoverDes(string.toString());
            } else {
                productsCover.setIsCover("0");
                productsCover.setCoverDes("未覆盖（不是A/港股上市或发公募债的非金融");
            }
        }

        productsCovers.add(productsCover);

        return productsCovers;
    }


}
