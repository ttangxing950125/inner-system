package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import com.deloitte.additional.recording.service.BasEvdValrangeService;
import com.deloitte.additional.recording.vo.BasEvdInfoUpdateVo;
import com.deloitte.additional.recording.vo.BasEvdInfoVo;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BasEvdInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("evidence")
public class BasEvdInfoController {
    /**
     * 服务对象
     */
    @Resource
    private BasEvdInfoService basEvdInfoService;
    /**
     * 服务对象
     */
    @Resource
    private BasEvdValrangeService basEvdValrangeService;
    /**
     * 查询所有的 basEvdDataDict
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/15 17:49
     */
    @RequestMapping("/getAllEvdDataDict")
    public R getAllEvdDataDict(){
        return basEvdValrangeService.getAllEvdDataDict();
    }

    @GetMapping("/findAll")
    public R<List<BasEvdInfo>> findAll(){
        return R.ok(basEvdInfoService.list());
    }
    /**
     * 分页查询evd
     *
     * @param basEvdInfoVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
    */
    @RequestMapping("/paging")
    public R paging(@RequestBody BasEvdInfoVo basEvdInfoVo){
        return basEvdInfoService.paging(basEvdInfoVo);
    }
    /**
     * 一键启用
     *
     * @param eviIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @RequestMapping("/openEvidence")
    public R openEvidence(@RequestBody List<Integer> eviIds){
        return basEvdInfoService.openEvidence(eviIds);
    }
    /**
     * 一键禁用
     *
     * @param eviIds
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @RequestMapping("/disableEvidence")
    public R disableEvidence(@RequestBody List<Integer> eviIds){
        return basEvdInfoService.disableEvidence(eviIds);
    }
    /**
     * 修改Evd
     *
     * @param basEvdInfoUpdateVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @PostMapping("/updateNewEvd")
    public R updateNewEvd(@RequestBody BasEvdInfoUpdateVo basEvdInfoUpdateVo){
        return basEvdInfoService.updateNewEvd(basEvdInfoUpdateVo);
    }
    /**
     * 新增Evd
     *
     * @param basEvdInfoUpdateVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/13 15:45
     */
    @RequestMapping("/addNewEvd")
    public R addNewEvd(@RequestBody BasEvdInfoUpdateVo basEvdInfoUpdateVo){
        return basEvdInfoService.addNewEvd(basEvdInfoUpdateVo);
    }

}
