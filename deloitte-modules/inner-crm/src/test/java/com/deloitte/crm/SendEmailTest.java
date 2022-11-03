package com.deloitte.crm;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.deloitte.crm.domain.CrmTypeInfo;
import com.deloitte.crm.mapper.StockThkImportMapper;
import com.deloitte.crm.service.CrmTypeInfoService;
import com.deloitte.crm.service.EntityInfoLogsService;
import com.deloitte.crm.service.SendEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/10/9
 */
@SpringBootTest
public class SendEmailTest {
    @Resource
    private SendEmailService sendEmailService;
    @Resource
    private CrmTypeInfoService crmTypeInfoService;
    @Resource
    private EntityInfoLogsService entityInfoLogsService;

    @Resource
    private StockThkImportMapper stockThkImportMapper;

    @Test
    void testSend() {
        sendEmailService.SendEmail(4, "新增主体3个待确认",
                "今日wind导入任务已完成，平台捕获3个疑似新增主体需要确认。\n" +
                        "请尽快登陆平台完成相关任务。");
    }

   /* @Test
    void test02() {
        System.out.println(JSON.toJSONString(entityCaptureSpeedMapper.search("哈尔滨")));
    }*/

    @Test
    void test03() {
        CrmTypeInfo crmTypeInfo = new CrmTypeInfo();
        crmTypeInfo.setName("非传统电信运营商");
        crmTypeInfo.setType("1");
        crmTypeInfo.setCode("WIN015");
        crmTypeInfo.setParentCode("WIN014");
        final Set<CrmTypeInfo> crmTypeInfos = crmTypeInfoService.findCodeByParent(crmTypeInfo, Integer.valueOf(1));
        final List<CrmTypeInfo> collect = crmTypeInfos.stream().sorted(Comparator.comparing(CrmTypeInfo::getLevel)).collect(Collectors.toList());
        final List<String> collect1 = collect.stream().map(CrmTypeInfo::getName).collect(Collectors.toList());
        String str2 = collect1.stream().collect(Collectors.joining("--"));
        System.out.println(">>>>" + JSON.toJSONString(collect));
        System.out.println(">>>>22222" + str2);

    }


    @Test
    void test04() {

        final ExcelReader reader = ExcelUtil.getReader("D:\\听姐\\10-29-灌数据-file-A\\A股-基本信息.xlsx");

        final List<List<Object>> read = reader.read();


    }
    @Test
    void test05() {

        final Object cancel = entityInfoLogsService.cancel(75);
        final Object type_stock = entityInfoLogsService.findAllByType("TYPE_BOND");

    }


}
