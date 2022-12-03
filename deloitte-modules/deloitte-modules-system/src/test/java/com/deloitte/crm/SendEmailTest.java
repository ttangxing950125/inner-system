package com.deloitte.crm;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.deloitte.common.core.domain.R;

import com.deloitte.system.DeloitteSystemApplication;
import com.deloitte.system.api.domain.SysDictData;
import com.deloitte.system.mapper.SysDictDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/10/9
 */
@SpringBootTest(classes = DeloitteSystemApplication.class)
public class SendEmailTest {
    @Resource
    private SysDictDataMapper dictDataMapper;
    @Test
    public void test01(){
        final List<SysDictData> list = dictDataMapper.selectDictDataByType("sys_normal_disable");
        System.out.println(JSON.toJSONString(list));
    }





}
