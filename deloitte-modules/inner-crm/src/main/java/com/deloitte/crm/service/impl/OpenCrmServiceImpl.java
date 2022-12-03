package com.deloitte.crm.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.OpenCrmByEntityCodeDto;
import com.deloitte.crm.dto.OpenCrmDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.OpenCrmService;
import com.deloitte.crm.vo.OpenCrmVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * (用于资产平台调用)
 *
 * @author PenTang
 * @date 2022/11/24 10:55
 */
@Slf4j
@Service
@AllArgsConstructor
public class OpenCrmServiceImpl implements OpenCrmService {

    private ProductsMapper productsMapper;

    private ProductsCustomerMapper productsCustomerMapper;


    private ProductsMasterDictMapper productsMasterDictMapper;

    private ProductsMasterMappingMapper productsMasterMappingMapper;

    private EntityMasterMapper entityMasterMapper;

    private EntityInfoMapper entityInfoMapper;

    /**
     * 1、根据业务场景、业务线、敞口 获取 主体信息
     *
     * @param OpenCrmDtoS
     * @return ArrayList<OpenCrmVo>
     * @author penTang
     * @date 2022/11/24 11:51
     */
    @Override
    public ArrayList<OpenCrmVo> getDataByScene(List<OpenCrmDto> OpenCrmDtoS) {

        ArrayList<OpenCrmVo> openCrmVos = new ArrayList<>();
        for (OpenCrmDto openCrmDto : OpenCrmDtoS) {
            //查询对应的产品
            Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getProCode, openCrmDto.getBusinessScene()));
            //查询对应的产品的客户
            ProductsCustomer productsCustomer = productsCustomerMapper.selectOne(new LambdaQueryWrapper<ProductsCustomer>()
                    .eq(ProductsCustomer::getProductsId, products.getId())
                    .eq(ProductsCustomer::getPrcCode, openCrmDto.getBusinessLine())
            );
            //查询对应的产品的客户的敞口
            List<ProductsMasterDict> productsMasterDicts = productsMasterDictMapper.selectList(new LambdaQueryWrapper<ProductsMasterDict>().eq(ProductsMasterDict::getProCustId, productsCustomer));
            for (ProductsMasterDict productsMasterDict : productsMasterDicts) {
                //查询对应的产品的客户的敞口与crm敞口的映射
                List<ProductsMasterMapping> productsMasterMappings = productsMasterMappingMapper.selectList(new LambdaQueryWrapper<ProductsMasterMapping>().eq(ProductsMasterMapping::getProMasDictId, productsMasterDict.getId()));
                for (ProductsMasterMapping productsMasterMapping : productsMasterMappings) {
                    //根据映射查询crm敞口下的主体
                    List<EntityMaster> entityMasters = entityMasterMapper.selectList(new LambdaQueryWrapper<EntityMaster>().eq(EntityMaster::getMasterCode, productsMasterMapping.getCrmMasterCode()));
                    for (EntityMaster entityMaster : entityMasters) {
                        OpenCrmVo openCrmVo = new OpenCrmVo();
                        //查询的主体信息
                        EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, entityMaster.getEntityCode()));
                        log.info("  装数=>>");
                        openCrmVo.setBusinessScene(openCrmDto.getBusinessScene());
                        openCrmVo.setBusinessLine(openCrmDto.getBusinessLine());
                        openCrmVo.setIndustryWhitewash(openCrmDto.getIndustryWhitewash());
                        openCrmVo.setEntityName(entityInfo.getEntityName());
                        openCrmVo.setEntityCode(entityInfo.getEntityCode());
                        openCrmVo.setEntityStatus(entityInfo.getStatus());
                        openCrmVos.add(openCrmVo);
                    }
                }
            }
        }
        return openCrmVos;
    }

    /**
     * 2、根据主体Code（企业code）获取所属敞口（注：是资产平台的敞口，不是crm的敞口）
     *
     * @return ArrayList<OpenCrmVo>
     * @author penTang
     * @date 2022/11/24 11:53
     */
    @Override
    public ArrayList<OpenCrmVo> getDataByCode(List<OpenCrmByEntityCodeDto> OpenCrmByEntityCodes) {
        ArrayList<OpenCrmVo> openCrmVos = new ArrayList<>();

        for (OpenCrmByEntityCodeDto openCrmByEntityCode : OpenCrmByEntityCodes) {
            EntityMaster entityMaster = entityMasterMapper.selectOne(new LambdaQueryWrapper<EntityMaster>().eq(EntityMaster::getEntityCode, openCrmByEntityCode.getEntityCode()));
            EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, openCrmByEntityCode.getEntityCode()));
            List<ProductsMasterDict> productsMasterDicts = productsMasterDictMapper.selectList(new LambdaQueryWrapper<ProductsMasterDict>().eq(ProductsMasterDict::getMasterKey, entityMaster.getMasterCode()));
            for (ProductsMasterDict productsMasterDict : productsMasterDicts) {
                ProductsCustomer productsCustomer = productsCustomerMapper.selectOne(new LambdaQueryWrapper<ProductsCustomer>().eq(ProductsCustomer::getId, productsMasterDict.getProCustId()));
                Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, productsCustomer.getProductsId()));
                OpenCrmVo openCrmVo = new OpenCrmVo();
                openCrmVo.setEntityName(entityInfo.getEntityName());
                openCrmVo.setEntityCode(entityInfo.getEntityCode());
                openCrmVo.setEntityStatus(entityInfo.getStatus());
                openCrmVo.setIndustryWhitewash(productsCustomer.getPrcCode() + "-" + productsMasterDict.getMasterKey());
                openCrmVo.setBusinessLine(productsCustomer.getPrcCode());
                openCrmVo.setBusinessScene(products.getProCode());
                openCrmVos.add(openCrmVo);
            }

        }

        return openCrmVos;
    }

}
