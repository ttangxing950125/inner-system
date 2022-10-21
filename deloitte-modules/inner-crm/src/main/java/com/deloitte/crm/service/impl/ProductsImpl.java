package com.deloitte.crm.service.impl;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.deloitte.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.mapper.ProductsMapper;
import com.deloitte.crm.service.ProductsService;
import lombok.AllArgsConstructor;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/12 18:33
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductsImpl extends ServiceImpl<ProductsMapper,Products> implements ProductsService {

    @Resource
    private  ProductsMapper mapper;
    /**
     *添加方法描述
     *
     * @return List<Products>
     * @author penTang
     * @date 2022/10/12 18:44
    */
    @Override
    public List<Products> getProducts() {

        return list();
    }

    /**
     *查询产品的覆盖规则
     *
     * @param id
     * @param response
     * @return Products
     * @author penTang
     * @date 2022/10/20 17:29
    */
    @Override
    public R getProductsExcel(Integer id, HttpServletResponse response)  {
        LambdaQueryWrapper<Products> eq = new LambdaQueryWrapper<Products>().eq(Products::getId, id);
        Products one = getOne(eq);
        String path =one.getFilePath();
        String filename="未知文件.xlsx";
        String[] split = path.split("/");
        if (split!=null&&split.length > 1) {
            String s = split[1];
            filename = s;
        }
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
         inputStream = Resources.getResourceAsStream(path);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("charset", "utf-8");
        response.addHeader("Pragma", "no-cache");
        String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);
        servletOutputStream = response.getOutputStream();
        IOUtils.copy(inputStream, servletOutputStream);
        response.flushBuffer();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (servletOutputStream != null) {
                servletOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            // jvm的垃圾回收
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




        return R.ok("下载成功");
    }

   /**
    *查询产品信息
    *
    * @param id
    * @return R
    * @author penTang
    * @date 2022/10/20 18:35
   */
    @Override
    public R getProductsOne(Integer id) {
        LambdaQueryWrapper<Products> eq = new LambdaQueryWrapper<Products>().eq(Products::getId, id);
        Products one = getOne(eq);
        return R.ok(one);
    }

}
