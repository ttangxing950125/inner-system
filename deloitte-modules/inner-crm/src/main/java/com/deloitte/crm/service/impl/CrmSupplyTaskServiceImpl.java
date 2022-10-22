package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import com.deloitte.crm.utils.TimeFormatUtil;
import com.deloitte.crm.vo.SupplyTaskVo;
import com.deloitte.system.api.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmSupplyTaskServiceImpl extends ServiceImpl<CrmSupplyTaskMapper, CrmSupplyTask> implements ICrmSupplyTaskService {
    @Autowired
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private EntityInfoMapper entityInfoMapper;

    @Autowired
    private GovInfoMapper govInfoMapper;

    @Autowired
    private EntityAttrMapper attrMapper;

    @Autowired
    private EntityAttrValueMapper valueMapper;

    @Autowired
    private EntityGovRelMapper entityGovRelMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmSupplyTask selectCrmSupplyTaskById(Long id) {
        return crmSupplyTaskMapper.selectCrmSupplyTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param crmSupplyTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmSupplyTask> selectCrmSupplyTaskList(CrmSupplyTask crmSupplyTask) {
        return crmSupplyTaskMapper.selectCrmSupplyTaskList(crmSupplyTask);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmSupplyTask(CrmSupplyTask crmSupplyTask) {
        return crmSupplyTaskMapper.insertCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmSupplyTask(CrmSupplyTask crmSupplyTask) {
        return crmSupplyTaskMapper.updateCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskByIds(Long[] ids) {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskById(Long id) {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskById(id);
    }
    /** 角色3 -- 5 */
    private static Long ROLE_THREE = 5L;
    /** 角色4 -- 6 */
    private static Long ROLE_FOUR = 6L;
    /** 角色5 -- 7 */
    private static Long ROLE_FIVE = 7L;
    @Override
    public R getRoleSupplyTask(String taskDate, Integer pageNum,Integer pageSize) {
        if (ObjectUtils.isEmpty(pageNum)){
            return R.fail("请选择页码");
        }
        if (ObjectUtils.isEmpty(pageSize)){
            pageSize=9;
        }
        //获取登录用户
        Long userId = SecurityUtils.getUserId();

        QueryWrapper<SysUserRole> userRleQuery = new QueryWrapper<>();
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(userRleQuery.lambda()
                .eq(SysUserRole::getUserId, userId)
                .in(SysUserRole::getRoleId, ROLE_THREE, ROLE_FOUR, ROLE_FIVE)
        );

//        sysUserRoles.add(new SysUserRole().setRoleId(7L));



        //不是 角色 3 4 5则不返回信息
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return R.ok();
        }
        Long roleId = sysUserRoles.get(0).getRoleId();
        QueryWrapper<CrmSupplyTask> taskQuery = new QueryWrapper<>();
        Page<CrmSupplyTask>pageInfo=new Page<>(pageNum,pageSize);
        //获取角色的任务数据
        Page<CrmSupplyTask> taskPage = crmSupplyTaskMapper.selectPage(pageInfo,taskQuery.lambda()
                .eq(CrmSupplyTask::getRoleId, roleId)
                .eq(CrmSupplyTask::getTaskDate, taskDate)
        );
        List<CrmSupplyTask> crmSupplyTasks=taskPage.getRecords();
        if (CollectionUtils.isEmpty(crmSupplyTasks)) {
            return R.ok();
        }
        //封装响应的页面数据
        List<SupplyTaskVo> taskList = new ArrayList<>();

        crmSupplyTasks.stream().forEach(o -> {
            //创建单个响应对象,设置角色3，4，5通用属性
            SupplyTaskVo taskVo=setNormalValue(o,roleId);
            taskList.add(taskVo);
        });
        //响应的分页数据
        Page<SupplyTaskVo> resultPage = new Page<>(pageNum,pageSize);
        resultPage.setTotal(taskPage.getTotal()).setRecords(taskList);
        return R.ok(resultPage);
    }

    private SupplyTaskVo setNormalValue(CrmSupplyTask o,Long roleId) {
        SupplyTaskVo taskVo = new SupplyTaskVo();
        EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, o.getEntityCode()).last(" limit 1"));
        String entityCode = entityInfo.getEntityCode();
        // 唯一识别字段
        taskVo.setEntityCode(entityCode);

        // 来源
        taskVo.setSource(o.getFrom());
        // 企业名称
        taskVo.setSource(entityInfo.getEntityName());
        // 统一社会信用代码代码
        taskVo.setCreditCode(entityInfo.getCreditCode());
        // 是否为金融机构
        taskVo.setList(entityInfo.getList());
        // 任务状态
        taskVo.setState(o.getState());

        String value="";

        if (roleId == ROLE_THREE) {
            //角色3  金融机构细分行业  attrId = 656,attrName = "金融机构细分行业"
            value = getThreeValue(entityCode);
            taskVo.setFinIndustryGroup(value);
        } else if (roleId == ROLE_FOUR) {
            //角色4     城投机构对应地方政府名称
            value = getFourValue(entityCode);
            taskVo.setGovName(value);
        } else if (roleId == ROLE_FIVE) {
            //角色5    是否为城投机构
            value = getFiveValue(entityCode);
            taskVo.setIsUi(value);
        }
        return taskVo;
    }

    private String getFiveValue(String entityCode) {
        Long count = entityGovRelMapper.selectCount(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getEntityCode, entityCode));
        if (count>0){
            return IS_URBAN_INVESTMENT;
        }
        return NOT_URBAN_INVESTMENT;
    }

    private String getFourValue(String entityCode) {
        //角色4     城投机构对应地方政府名称
        EntityGovRel entityGovRel = entityGovRelMapper.selectOne(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getEntityCode, entityCode).last(" limit 1"));
        if (ObjectUtils.isEmpty(entityGovRel)){
            return null;
        }
        GovInfo govInfo = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, entityGovRel.getDqGovCode()).last(" limit 1"));
        if (ObjectUtils.isEmpty(govInfo)){
            return null;
        }
        return govInfo.getGovName();
    }

    private String getThreeValue(String entityCode) {
        //角色3  金融机构细分行业  attrId = 656,attrName = "金融机构细分行业"
        EntityAttrValue attrValue = valueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
                .lambda().eq(EntityAttrValue::getAttrId, 656).eq(EntityAttrValue::getEntityCode, entityCode).last(" limit 1"));
        if (ObjectUtils.isEmpty(attrValue)){
            return null;
        }
        return attrValue.getValue();
    }

    //是城投机构
    private static String IS_URBAN_INVESTMENT="Y";
    //不是城投机构
    private static String NOT_URBAN_INVESTMENT="N";
    @Override
    public TaskStatistics getTaskStatistics() {
        Long userId = SecurityUtils.getUserId();
        TaskStatistics taskStatistics=new TaskStatistics();

        //获取登录用户
        QueryWrapper<SysUserRole> userRleQuery = new QueryWrapper<>();
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(userRleQuery.lambda()
                .eq(SysUserRole::getUserId, userId)
                .in(SysUserRole::getRoleId, ROLE_THREE, ROLE_FOUR, ROLE_FIVE)
        );
        if (CollectionUtils.isEmpty(sysUserRoles)){
            return null;
        }
        SysUserRole sysUserRole = sysUserRoles.get(0);

        Long roleId = sysUserRole.getRoleId();
        String currentTime = TimeFormatUtil.getDayTime("yyyy-MM-dd",0);
        //设置日期
        taskStatistics.setTodayDate(currentTime);
        //设置周几
        String toWeek = TimeFormatUtil.dateToWeek(currentTime);
        taskStatistics.setTodayWeek(toWeek);

        QueryWrapper<CrmSupplyTask> taskQuery = new QueryWrapper<>();
        List<CrmSupplyTask> supplyTasks = crmSupplyTaskMapper.selectList(taskQuery.lambda()
                .eq(CrmSupplyTask::getRoleId, roleId)
                .eq(CrmSupplyTask::getTaskDate, currentTime)
        );
        //统计任务完成情况
        if (CollectionUtils.isEmpty(supplyTasks)){
            taskStatistics.setTaskComplete(0).setTaskTotal(0).setTaskWait(0);
        }else {
            //等待完成的任务
            AtomicReference<Integer> wait= new AtomicReference<>(0);
            //已经完成的任务
            AtomicReference<Integer> complete= new AtomicReference<>(0);
            supplyTasks.stream().forEach(o->{
                Integer state = o.getState();
                if (state==0){
                    wait.getAndSet(wait.get() + 1);
                }else if (state==1){
                    complete.getAndSet(complete.get() + 1);
                }
            });
            taskStatistics.setTaskComplete(complete.get()).setTaskTotal(supplyTasks.size()).setTaskWait(wait.get());
        }
        return taskStatistics;
    }

    @Override
    public void completeTaskById(Integer id) {
        CrmSupplyTask crmSupplyTask = new CrmSupplyTask();
        crmSupplyTask.setId(id);
        crmSupplyTask.setState(1);
        crmSupplyTask.setHandleUser(SecurityUtils.getUsername());
        crmSupplyTaskMapper.updateById(crmSupplyTask);
    }
}
