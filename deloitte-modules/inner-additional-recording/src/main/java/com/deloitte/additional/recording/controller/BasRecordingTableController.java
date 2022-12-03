package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.BasRecordingTableService;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 中间补录层
 * @author chenjiang
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/recordingTable")
public class BasRecordingTableController {

    @Resource
    private BasRecordingTableService basRecordingTableService;

    /**
     * 获取全部表名 不带分页
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/getAllRecordingTable")
    public R getAllRecordingTable(HttpServletResponse response, HttpServletRequest request) {
        return R.ok(basRecordingTableService.getAllRecordingTable());
    }

    /**
     * 通过表查询标签
     * @param tableCode
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/findLableByTableCode")
    public R findLableByTableCode(String tableCode, HttpServletResponse response, HttpServletRequest request) {
        Optional.ofNullable(tableCode).orElseThrow(() -> new ServiceException("表编码不可以为空"));
        return R.ok(basRecordingTableService.findLableByTableCode(tableCode));
    }

}
