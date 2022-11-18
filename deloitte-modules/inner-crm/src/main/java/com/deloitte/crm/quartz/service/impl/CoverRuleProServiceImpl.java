package com.deloitte.crm.quartz.service.impl;

import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.CoverRule;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.domain.ProductsCover;
import com.deloitte.crm.dto.EntityCoverDto;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.ProductsCoverMapper;
import com.deloitte.crm.mapper.ProductsMapper;
import com.deloitte.crm.quartz.service.CoverRuleProService;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author PenTang
 * @date 2022/10/28 11:45
 */
@Slf4j
@Service
public class CoverRuleProServiceImpl implements CoverRuleProService {

      @Resource
     private EntityInfoMapper entityInfoMapper;
      @Resource
      private ProductsMapper productsMapper;
      @Resource
      private ProductsCoverMapper productsCoverMapper;






}
