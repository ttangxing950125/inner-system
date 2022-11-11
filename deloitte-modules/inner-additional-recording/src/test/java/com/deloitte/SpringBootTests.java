package com.deloitte;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.InnerAdditionalRecordingApplication;
import com.deloitte.additional.recording.application.service.SysGroupUserRoleService;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.request.SysUserRequest;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.service.SysRoleService;
import com.deloitte.additional.recording.service.SysUserService;
import com.deloitte.additional.recording.vo.DataListCustomEntityInfoVo;
import com.deloitte.additional.recording.vo.DataListFindPrsProjectVersionsByYearVo;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import com.deloitte.additional.recording.vo.user.SysUserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/08/15:44
 * @Description:
 */
@SpringBootTest(classes = InnerAdditionalRecordingApplication.class)
public class SpringBootTests {
    @Resource
    private PrsModelQualService prsModelQualService;
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;

    @Autowired
    private SysGroupUserRoleService sysGroupUserRoleService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService roleService;

    @Test
    public void test() {
        //24
//        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql("M_029", "2021", "第一创业");
        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail("M_029", "2021", "第一创业");
        System.out.println(JSON.toJSONString(dataListPageTataiVos));
    }

    /**
     *
     */
    @Test
    public void test2() {
        final List<DataListFindPrsProjectVersionsByYearVo> dataListFindPrsProjectVersionsByYearVos = prsProjectVersionsMapper.finPrsProjectVersionsByYear(new Integer[]{2020, 2021});
        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.stream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        System.out.println(JSON.toJSONString(collect));
    }

    @Test
    public void test3() {
        final List<DataListCustomEntityInfoVo> results = prsProjectVersionsMapper.getCustomEntityInfoByVersionIdAndModelId("2021", "Q_000044", "449");
//        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.stream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        System.out.println(JSON.toJSONString(results));
    }

    @Test
    public void userPage() {

        Page<SysUserVO> page = sysGroupUserRoleService.page(null, "张三", "1", "1,2", "1,2", 1, 10);

        System.out.println(page.getRecords());
    }

    @Test
    public void addUser() {
        SysUserRequest saveRequest = new SysUserRequest();
        saveRequest.setEmail("1164490300@qq.com");
        saveRequest.setSex("男");
        saveRequest.setGroupid("1,2");
        saveRequest.setName("张三");
        saveRequest.setRoles("1,2");
        saveRequest.setStatus("1");
        sysGroupUserRoleService.addSave("张三", "1164490300@qq.com", "男", "1", "1,2", "1,2");
    }

    @Test
    public void resetPassword() {
        sysUserService.resetPassword(1383469057);

    }

    @Test
    public void modfiysave() {
        sysGroupUserRoleService.modify(1383469057, "李四", "207425690@qq.com", "男", "1", "1", "2");
    }

    @Test
    public void rolePage() {

    }
}
