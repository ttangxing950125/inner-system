package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.dto.GetTaskTargetvalPageListDto;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;

import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.additional.recording.service.biz.DataListBizComponentService;
import com.deloitte.additional.recording.vo.DataListGetDropDownBoxVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.web.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Resource
    private DataListBizComponentService dataListBizService;

    /**
     * 获取下拉框 月份 版本、敞口
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/getDropDownBox")
    public R getDropDownBox(HttpServletRequest request, HttpServletResponse response) {
        DataListGetDropDownBoxVo dataListGetDropDownBoxVo = new DataListGetDropDownBoxVo();
        List<String> allPrsProjectVersions = prsProjectVersionsService.findAllPrsProjectVersions();
        dataListGetDropDownBoxVo.setPrsProjectVersionList(allPrsProjectVersions);
        List<Map<String, Object>> prsModelMasterLists = prsModelMasterService.finAllPrsModelMaster();
        dataListGetDropDownBoxVo.setPrsModelMasterLists(prsModelMasterLists);
        List<Map<String, Object>> sysDictListData = sysDictDataService.finAllsysDictData();
        dataListGetDropDownBoxVo.setSysDitMonthLists(sysDictListData);
        return R.ok(dataListGetDropDownBoxVo);
    }

    /**
     * 获取分页数据
     *
     * @param dto
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/dataListPage")
    public R queryByPage(@RequestBody GetTaskTargetvalPageListDto dto, HttpServletRequest request, HttpServletResponse response) {
        return R.ok(dataListBizService.queryByPage(dto));
    }

    /**
     * 获取指标头
     *
     * @param modelCode 敞口Code
     * @param timeValue 年份
     * @param name      版本
     * @return
     */
    @RequestMapping("/queryByPageStatsdetail")
    public R queryByPageStatsdetail(String modelCode, String timeValue, String name) {
        return R.ok(dataListBizService.queryByPageStatsdetail(modelCode, timeValue, name));

    }

    /**
     * 自定义查询
     * @param year
     * @return
     */
    @RequestMapping("/getCustomEntity/{ids}")
    public R getCustomEntity(@PathVariable Integer[] ids) {
        Optional.ofNullable(ids).orElseThrow(() -> new ServiceException("参数不可以为空"));
        return R.ok(prsProjectVersionsService.finPrsProjectVersionsByYear(ids));
    }


}
