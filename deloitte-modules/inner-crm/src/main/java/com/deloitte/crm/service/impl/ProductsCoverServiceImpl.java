package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
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
import org.apache.commons.math3.stat.descriptive.summary.Product;
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
    public Page<ProductCoverDto> getProducts(EntityOrGovByAttrVo entityOrGovByAttrVo) {
        // 获取 需要查询的产品(去重)
        List<Integer> proId =entityOrGovByAttrVo.getProId().stream().distinct().collect(Collectors.toList());

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
        records.forEach(o -> {
            //返回添加列的结果
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            ProductCoverDto temp = new ProductCoverDto();
            for (Integer integer : proId) {
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
        Page<ProductCoverDto> oageResult = new Page<>();
        oageResult.setRecords(result).setTotal(page1.getTotal());
        return oageResult;
    }


}
