package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.additional.recording.vo.DataListGetDropDownBoxVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.system.api.domain.SysDictData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/07/15:57
 * @Description: 数据清单 通用入口
 */
@RestController
@RequestMapping("dataList")
public class DataListController extends BaseController {

    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;
    @Resource
    private PrsModelMasterService prsModelMasterService;

    /**
     * 获取下拉框 月份 版本、敞口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getDropDownBox")
    public R getDropDownBox(HttpServletRequest request, HttpServletResponse response) {
        DataListGetDropDownBoxVo dataListGetDropDownBoxVo = new DataListGetDropDownBoxVo();
        List<String> allPrsProjectVersions = prsProjectVersionsService.findAllPrsProjectVersions();
        dataListGetDropDownBoxVo.setPrsProjectVersionList(allPrsProjectVersions);
        List<String> prsModelMasterLists = prsModelMasterService.finAllPrsModelMaster();
        dataListGetDropDownBoxVo.setPrsModelMasterLists(prsModelMasterLists);
        return R.ok(dataListGetDropDownBoxVo);
    }
}
