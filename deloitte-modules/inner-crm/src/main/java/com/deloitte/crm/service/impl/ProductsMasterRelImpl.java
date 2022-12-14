package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.ProCustomerDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.ProductsMasterRelService;
import com.deloitte.crm.vo.ProductsMasterRelVo;
import com.deloitte.system.api.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.insert;

/**
 * @author PenTang
 * @date 2022/10/17 11:16
 */
@Service
@AllArgsConstructor
public class ProductsMasterRelImpl extends ServiceImpl<ProductsMasterRelMapper,ProductsMasterRel> implements ProductsMasterRelService {
        private  ProductsMasterRelMapper mapper;
        private ProducysMasterRelHisMapper producysMasterRelHisMapper;

        private EntityMasterMapper entityMasterMapper;

        private ProductsMasterMappingMapper productsMasterMappingMapper;

        private RoleService roleService;

        private  ProductsMasterDictMapper productsMasterDictmapper;

        /**
         *查询客户产品敞口映射
         *
         * @param entityCode
         * @return List<ProCustomerDto>
         * @author penTang
         * @date 2022/10/17 14:58
        */
        @Override
        public List<ProCustomerDto> getProductsMasterRelList(String entityCode,String dataYear,Integer ProId) {

                List<ProCustomerDto> proCustomerDtos = mapper.futureList(ProId);
                System.out.println(proCustomerDtos);
                for (ProCustomerDto proCustomerDto : proCustomerDtos) {
                        LambdaQueryWrapper<ProductsMasterRel> qw = new LambdaQueryWrapper<ProductsMasterRel>()
                                .eq(ProductsMasterRel::getEntityCode, entityCode)
                                .eq(ProductsMasterRel::getProCustId, proCustomerDto.getProCumId())
                                .eq(ProductsMasterRel::getDataYear, dataYear);
                        ProductsMasterRel productsMasterRel = getOne(qw);

                        if (productsMasterRel==null){
                                proCustomerDto.setDictId(null);
                                proCustomerDto.setMasterName(null);

                        }else{
                                LambdaQueryWrapper<ProductsMasterDict> eq = new LambdaQueryWrapper<ProductsMasterDict>().eq(ProductsMasterDict::getId, productsMasterRel.getProMasDictId())
                                        .eq(ProductsMasterDict::getProCustId, proCustomerDto.getProCumId());
                                ProductsMasterDict productsMasterDict = productsMasterDictmapper.selectOne(eq);
                                proCustomerDto.setDictId(productsMasterRel.getProMasDictId().toString());
                                proCustomerDto.setMasterName(productsMasterDict.getMasterName());
                                proCustomerDto.setUpdateMark(productsMasterRel.getUpdateMark());
                        }
                }


                return  proCustomerDtos;
        }
                /**
                 修改敞口关联关系
                 *
                 * @param productsMasterRelVo
                 * @return Boolean
                 * @author penTang
                 * @date 2022/10/17 19:18
                */
        @Override
        @Transactional
        public  Boolean updateRel(ProductsMasterRelVo productsMasterRelVo){
                LambdaQueryWrapper<ProductsMasterRel> qw = new LambdaQueryWrapper<ProductsMasterRel>()
                        .eq(ProductsMasterRel::getEntityCode, productsMasterRelVo.getEntityCode())
                        .eq(ProductsMasterRel::getProCustId, productsMasterRelVo.getProCustId())
                .eq(ProductsMasterRel::getDataYear, productsMasterRelVo.getDataYear());
                ProductsMasterRel productsMasterRel = getOne(qw);
                if (productsMasterRel!= null){
                        productsMasterRel.setProMasDictId(productsMasterRelVo.getDictIdNew());
                        productsMasterRel.setUpdateMark("人工映射");
                        boolean b = updateById(productsMasterRel);
                        //新增历史记录表
                        ProductsMasterRelHis productsMasterRelHis = new ProductsMasterRelHis();
                        productsMasterRelHis.setAddHis(DateUtil.getDate());
                        productsMasterRelHis.setEntityCode(productsMasterRelVo.getEntityCode());
                        productsMasterRelHis.setEntityNameHis(productsMasterRelVo.getEntityName());
                        productsMasterRelHis.setProCustId(productsMasterRelVo.getProCustId());
                        productsMasterRelHis.setMasterDictId(productsMasterRelVo.getDictIdOld());
                        productsMasterRelHis.setMasterName(productsMasterRelVo.getMasterNameOld());
                        productsMasterRelHis.setMasterDictIdNew(productsMasterRelVo.getDictIdNew());
                        productsMasterRelHis.setMasterNameNew(productsMasterRelVo.getMasterNameNew());
                        producysMasterRelHisMapper.insert(productsMasterRelHis);
                        return b;
                }else{
                        ProductsMasterRel productsMasterRelSave = new ProductsMasterRel();
                        productsMasterRelSave.setEntityCode(productsMasterRelVo.getEntityCode());
                        productsMasterRelSave.setDataYear(productsMasterRelVo.getDataYear());
                        productsMasterRelSave.setProCustId(productsMasterRelVo.getProCustId());
                        productsMasterRelSave.setProMasDictId(productsMasterRelVo.getDictIdNew());
                        productsMasterRelSave.setUpdateMark("人工映射");
                        boolean save = save(productsMasterRelSave);
                        if (save) {
                                //新增历史记录表
                                ProductsMasterRelHis productsMasterRelHis = new ProductsMasterRelHis();
                                productsMasterRelHis.setAddHis(DateUtil.getDate());
                                productsMasterRelHis.setEntityCode(productsMasterRelVo.getEntityCode());
                                productsMasterRelHis.setEntityNameHis(productsMasterRelVo.getEntityName());
                                productsMasterRelHis.setProCustId(productsMasterRelVo.getProCustId());
                                productsMasterRelHis.setMasterDictId(productsMasterRelVo.getDictIdOld()==null ? 0 : productsMasterRelVo.getDictIdOld());
                                productsMasterRelHis.setMasterName(productsMasterRelVo.getMasterNameOld()==null ? null : productsMasterRelVo.getMasterNameOld());
                                productsMasterRelHis.setMasterDictIdNew(productsMasterRelVo.getDictIdNew());
                                productsMasterRelHis.setMasterNameNew(productsMasterRelVo.getMasterNameNew());
                                producysMasterRelHisMapper.insert(productsMasterRelHis);
                                return true;
                        }
                        return true;

                }


        }


        /**
         *自动映射产品敞口
         *
         * @return boolean
         * @author penTang
         * @date 2022/10/21 11:08
        */
        @Override
        public  boolean updateAuto(String entityCode){
                EntityMaster entityMaster = entityMasterMapper.selectOne(new LambdaQueryWrapper<EntityMaster>().eq(EntityMaster::getEntityCode, entityCode));
                if (entityMaster != null){
                        List<ProductsMasterMapping> productsMasterMappings = productsMasterMappingMapper.selectList(new LambdaQueryWrapper<ProductsMasterMapping>().eq(ProductsMasterMapping::getCrmMasterCode, entityMaster.getMasterCode()));
                        List<ProductsMasterRel> productsMasterRels = new ArrayList<>();
                        for (ProductsMasterMapping productsMasterMapping : productsMasterMappings) {
                                ProductsMasterRel productsMasterRel = new ProductsMasterRel();
                                productsMasterRel.setProCustId(productsMasterMapping.getProCustomerId());
                                productsMasterRel.setProMasDictId(productsMasterMapping.getProMasDictId());
                                productsMasterRel.setEntityCode(entityMaster.getEntityCode());
                                productsMasterRel.setDataYear(roleService.getConfigId());
                                productsMasterRels.add(productsMasterRel);
                        }
                        for (ProductsMasterRel productsMasterRel : productsMasterRels) {
                                ProductsMasterRel one = getOne(new LambdaQueryWrapper<ProductsMasterRel>()
                                        .eq(ProductsMasterRel::getEntityCode, productsMasterRel.getEntityCode())
                                        .eq(ProductsMasterRel::getDataYear, productsMasterRel.getDataYear())
                                        .eq(ProductsMasterRel::getProCustId, productsMasterRel.getProCustId())
                                );
                                if (one != null) {
                                        productsMasterRel.setId(one.getId());
                                        productsMasterRel.setUpdateMark("自动映射");
                                        boolean b = updateById(productsMasterRel);
                                }else{
                                        boolean save = save(productsMasterRel);
                                }
                        }
                        return true;

                }else {
                        return false;
                }
        }

}
