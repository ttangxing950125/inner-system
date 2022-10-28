package com.deloitte.crm.service.impl;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.CoverRule;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.domain.ProductsCover;
import com.deloitte.crm.dto.ProductCoverDto;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.ProductsCoverMapper;
import com.deloitte.crm.mapper.ProductsMapper;
import com.deloitte.crm.service.ProductsCoverService;
import com.deloitte.crm.vo.EntityOrGovByAttrVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    /**
     * 查询覆盖情况
     *
     * @return List<ProductCoverDto>
     * @author penTang
     * @date 2022/10/12 18:58
     */
    @Override
    public Page <ProductCoverDto> getProducts(EntityOrGovByAttrVo entityOrGovByAttrVo) {
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
        LambdaQueryWrapper<EntityInfo> like1 = like.lambda().like(EntityInfo::getEntityName, entityOrGovByAttrVo.getEntityName());
        if (like1 == null) {
            R.fail("未查询到该主体");
        }
        Page<EntityInfo> page1 = entityInfoMapper.selectPage(page, like1);
        List<EntityInfo> records = page1.getRecords();

        //组装返回结果
        List<Integer> proIds = proId;
        records.forEach(o -> {
            //返回添加列的结果
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            ProductCoverDto temp = new ProductCoverDto();
            for (Integer integer : proIds) {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                //当前主体关联的产品覆盖情况
                ProductsCover productsCover = productCoverMapper.selectOne(new QueryWrapper<ProductsCover>().lambda().eq(ProductsCover::getProId, integer).eq(ProductsCover::getEntityCode, o.getEntityCode()));
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
            temp.setResult(maps);
            result.add(temp);
        });
        Page<ProductCoverDto> oageResult = new Page<>(entityOrGovByAttrVo.getPageNum(), entityOrGovByAttrVo.getPageSize());
        oageResult.setRecords(result).setTotal(page1.getTotal());
        return oageResult;
    }





    @Override
    public void CoverRule() {
        log.info("=>> "+ DateUtil.dateTimeNow()+"组装覆盖情况");
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(null);
        List<ProductsCover> productsCovers =null;
        List<ProductsCover> productsCoverData = null;
        for (int i = 0; i < entityInfos.size(); i++) {
            log.info("进度=>"+i+0+"<=");
            //组装ESG
            List<ProductsCover> productsCoversE = this.CoverRuleEsg(entityInfos.get(i), productsCovers);
            //组装产业链
            productsCoverData = this.CoverRuleClTE(entityInfos.get(i), productsCoversE);
        }
        boolean b = saveBatch(productsCoverData);
        
    }

    /**
     *组装
     *
     * @param entityInfo
     * @param productsCovers
     * @return List<ProductsCover>
     * @author penTang
     * @date 2022/10/28 15:11
     */
    public List<ProductsCover> CoverRuleEsg(EntityInfo entityInfo,List<ProductsCover> productsCovers){
        log.info("组装ESG");
        ProductsCover productsCoverE = new ProductsCover();
        if (entityInfo.getList()==null || entityInfo.getList().equals(0)) {
            productsCoverE.setProId(CoverRule.ESG_ID);
            productsCoverE.setIsCover("0");
            productsCoverE.setEntityCode(entityInfo.getEntityCode());
            productsCoverE.setIsGov("0");
            productsCoverE.setCoverDes("非覆盖");
            productsCovers.add(productsCoverE);
        }else if (entityInfo.getList().equals(1)){
            productsCoverE.setProId(CoverRule.ESG_ID);
            productsCoverE.setIsCover("1");
            productsCoverE.setEntityCode(entityInfo.getEntityCode());
            productsCoverE.setIsGov("0");
            productsCoverE.setCoverDes("覆盖");
            productsCovers.add(productsCoverE);
        }
        return productsCovers;
    }

    public List<ProductsCover> CoverRuleClTE(EntityInfo entityInfo,List<ProductsCover> productsCovers){
        log.info("组装CLTE");
        ProductsCover productsCoverC = new ProductsCover();
        if (entityInfo.getList()==null || entityInfo.getList().equals(0)) {
            productsCoverC.setProId(CoverRule.CT_LE_ID);
            productsCoverC.setIsCover("0");
            productsCoverC.setEntityCode(entityInfo.getEntityCode());
            productsCoverC.setIsGov("0");
            productsCoverC.setCoverDes("非覆盖");
            productsCovers.add(productsCoverC);
        }else if (entityInfo.getList().equals(1)){
            productsCoverC.setProId(CoverRule.ESG_ID);
            productsCoverC.setIsCover("1");
            productsCoverC.setEntityCode(entityInfo.getEntityCode());
            productsCoverC.setIsGov("0");
            productsCoverC.setCoverDes("覆盖");
            productsCovers.add(productsCoverC);
        }
        return productsCovers;
    }


}
