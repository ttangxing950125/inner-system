package com.deloitte.additional.recording.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.domain.SysUserRole;
import com.deloitte.additional.recording.dto.BasRecordingLabelReqDto;
import com.deloitte.additional.recording.service.BasRecordingLabelService;
import com.deloitte.additional.recording.service.SysUserroleService;
import com.deloitte.additional.recording.util.MetaSecurity;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 中间补录层标签表 前端控制器
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/recordingLabel")
public class BasRecordingLabelController {

    @Resource
    private BasRecordingLabelService labelService;
    @Resource
    private SysUserroleService sysUserroleService;

    /**
     * 标签补录 分页查询
     * @param reqDto
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/getEcordingLabelList")
    public R getEcordingLabelList(@RequestBody BasRecordingLabelReqDto reqDto, HttpServletResponse response, HttpServletRequest request) {
        final SysUser sysUser = Optional.ofNullable(MetaSecurity.getLoginUser()).orElseThrow(() -> new ServiceException("用户未登录"));
        //当前用户是不是 ‘补录人员’
        final SysUserRole sysUserRoles = sysUserroleService.getBaseMapper().selectOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, sysUser.getId()).eq(SysUserRole::getRoleId, Common.SYS_ROLE_ID_COLLOCTER));
        Optional.ofNullable(sysUserRoles).orElseThrow(() -> new ServiceException("当前用户:" + sysUser.getLoginname() + "不具备补录任务的角色"));
        reqDto.setPeriodCollocterId(sysUserRoles.getUserId());
        return labelService.getEcordingLabelList(reqDto);
    }

    /**
     * 选择分配
     * @param request
     * @param response
     * @param taskId   任务ID
     * @return
     */
    @RequestMapping("editEcordingLabel")
    public R editEcordingLabel(HttpServletRequest request, HttpServletResponse response, Integer taskId) {
        Optional.ofNullable(taskId).orElseThrow(() -> new ServiceException("任务ID不可以为空"));
        return labelService.editEcordingLabel(taskId);
    }
}
