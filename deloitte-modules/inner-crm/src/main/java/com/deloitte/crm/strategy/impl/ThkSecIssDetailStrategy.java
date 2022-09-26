package com.deloitte.crm.strategy.impl;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ThkSecIssDetail;
import com.deloitte.crm.domain.ThkSecIssInfo;
import com.deloitte.crm.service.IThkSecIssDetailService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/26
 */
@Component
public class ThkSecIssDetailStrategy implements WindTaskStrategy {

    @Resource
    private IThkSecIssDetailService thkSecIssDetailService;

    /**
     * 是否支持当前wind任务
     * @param windDictId
     * @return
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(windDictId, WindTaskEnum.THK_SEC_ISS_DETAIL.getId());
    }

    /**
     * 开始执行任务
     *
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
//        读取文件
        ExcelUtil<ThkSecIssDetail> util = new ExcelUtil<ThkSecIssDetail>(ThkSecIssDetail.class);
        List<ThkSecIssDetail> thkSecIssInfos = util.importExcel(file.getInputStream());

        return thkSecIssDetailService.doTask(windTask, thkSecIssInfos);
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @param windTask
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        return null;
    }

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     *
     * @param windTask
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        return null;
    }
}
