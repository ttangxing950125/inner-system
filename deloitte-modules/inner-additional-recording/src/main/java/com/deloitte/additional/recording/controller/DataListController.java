package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;

import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.additional.recording.vo.DataListGetDropDownBoxVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.system.api.domain.SysDictData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
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
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 获取下拉框 月份 版本、敞口
     * {
     *     "code": 200,
     *     "msg": null,
     *     "data": {
     *         "prsProjectVersionList": [
     *             "同业专题test",
     *             "交银理财子版"
     *         ],
     *         "prsModelMasterLists": [
     *             "传媒",
     *             "批发"
     *         ],
     *         "sysDitMonthLists": [
     *             {
     *                 "2019": "N",
     *                 "2018": "N",
     *                 "2021": "Y",
     *                 "2020": "N"
     *             }
     *         ]
     *     }
     * }
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/getDropDownBox")
    public R getDropDownBox(HttpServletRequest request, HttpServletResponse response) {
        DataListGetDropDownBoxVo dataListGetDropDownBoxVo = new DataListGetDropDownBoxVo();
        List<String> allPrsProjectVersions = prsProjectVersionsService.findAllPrsProjectVersions();
        dataListGetDropDownBoxVo.setPrsProjectVersionList(allPrsProjectVersions);
        List<String> prsModelMasterLists = prsModelMasterService.finAllPrsModelMaster();
        dataListGetDropDownBoxVo.setPrsModelMasterLists(prsModelMasterLists);
        List<Map<String, Object>> sysDictListData =  sysDictDataService.finAllsysDictData();
        dataListGetDropDownBoxVo.setSysDitMonthLists(sysDictListData);
        return R.ok(dataListGetDropDownBoxVo);
    }
}
