package com.deloitte;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.InnerAdditionalRecordingApplication;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import com.deloitte.additional.recording.dto.ModerQuanAndQualFactorDto;
import com.deloitte.additional.recording.request.TranspreQualinfo3rdRequest;
import com.deloitte.additional.recording.service.TranspreQualinfo3rdService;
import com.deloitte.additional.recording.service.center.ModelQualQuanGovFactorService;
import com.deloitte.additional.recording.vo.qualinfo3rd.TranspreQualinfo3rdPageVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/18
 * @描述 指标映射测试类
 */
@SpringBootTest(classes = InnerAdditionalRecordingApplication.class)
public class TranspreQualinfo3rdTests {


    @Autowired
    private TranspreQualinfo3rdService transpreQualinfo3rdService;

    @Autowired
    private ModelQualQuanGovFactorService modelQualQuanGovFactorService;


    @Test
    public void delete() {

        transpreQualinfo3rdService.removeById(294646);
    }

    @Test
    public void add() {
        TranspreQualinfo3rdRequest request = new TranspreQualinfo3rdRequest();
        request.setPlusVersion(45);
        request.setPrefix("xbzq");
        request.setDataYear("2021");
        request.setPlusQualcode("Q_000547");
        request.setPlusModelid(2);
        request.setPlusRuleid(4726);
        request.setPlusQualid(547);
        request.setPtTimes(1.0);
        request.setTarQualid("149");
        request.setTarType("1");
        request.setTarQualname("企业性质");
        request.setPlusUnit("元");
        String code = modelQualQuanGovFactorService.getQualFromDataCenter(request.getPrefix(), request.getTarQualid(), request.getTarType());
        //获取code
        transpreQualinfo3rdService.add(request, code);
    }


    @Test
    public void update() {
        TranspreQualinfo3rdRequest request = new TranspreQualinfo3rdRequest();
        request.setId(294646);
        request.setPlusVersion(45);
        request.setPrefix("xbzq");
        request.setDataYear("2021");
        request.setPlusQualcode("Q_000547");
        request.setPlusModelid(2);
        request.setPlusRuleid(4726);
        request.setPlusQualid(547);
        request.setPtTimes(1.0);
        request.setTarQualid("149");
        request.setTarType("1");
        request.setTarQualname("企业性质");
        request.setPlusUnit("元");
        String code = modelQualQuanGovFactorService.getQualFromDataCenter(request.getPrefix(), request.getTarQualid(), request.getTarType());
        //获取code
        transpreQualinfo3rdService.add(request, code);
    }


    @Test
    public void getQualListFromDataCenter() {
        Integer versionId = 136;
        Integer masterId = 42;
        String useYear = "2021";
        String type = "1";
        //根据敞口和版本查询到中心库对应的敞口code和前缀
        TranspreQualinfo3rd qualinfo3rd = transpreQualinfo3rdService.selectByVersionAndMaster(versionId, masterId, useYear);

        List<ModerQuanAndQualFactorDto> list = modelQualQuanGovFactorService.getQualListFromDataCenter(qualinfo3rd.getTarMid(), qualinfo3rd.getPrefix(), type);

        System.out.println(list);
    }


    @Test
    public void page() {
        Page<TranspreQualinfo3rdPageVO> page = transpreQualinfo3rdService.page("2021", "1", null, null, null, 1, 10);
        System.out.println(page.getRecords());
    }
}
