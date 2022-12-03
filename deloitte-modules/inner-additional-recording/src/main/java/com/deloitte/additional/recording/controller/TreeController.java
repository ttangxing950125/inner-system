package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.TreeService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 树型结构统计控制层
 *
 * @author 冉浩岑
 * @date 2022/11/21 9:50
*/
@RestController
@RequestMapping("tree")
public class TreeController {
    /**
     * 服务对象
     */
    @Resource
    private TreeService treeService;
    /**
     * 树性结构统计
     *
     * @param type 查询类型 0.版本 1.敞口 2.指标 3.evd
     * @param value  参数 Id
     * @param selYearValue  年份 版本年份
     * @return R
     * @author 冉浩岑
     * @date 2022/11/21 10:01
    */
    @RequestMapping("/treeDataList")
    public R treeDataList(Integer type, String value, String selYearValue){
        return treeService.treeDataList( type, value, selYearValue);
    }
}
