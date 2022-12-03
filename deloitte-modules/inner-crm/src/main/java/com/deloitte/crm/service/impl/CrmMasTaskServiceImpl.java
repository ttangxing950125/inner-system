package com.deloitte.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmMasTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.CrmMasTaskVo;
import com.deloitte.crm.vo.EmailCrmTask;
import com.deloitte.crm.vo.EmailVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
@Slf4j
public class CrmMasTaskServiceImpl extends ServiceImpl<CrmMasTaskMapper, CrmMasTask> implements ICrmMasTaskService {
    private final CrmMasTaskMapper crmMasTaskMapper;

    private ICrmDailyTaskService dailyTaskService;

    private IEntityInfoService iEntityInfoService;

    private EntityMasterMapper entityMasterMapper;

    private EntityCaptureSpeedMapper entityCaptureSpeedMapper;

    private EntityInfoMapper entityInfoMapper;

    private ModelMasterMapper modelMasterMapper;

    private EmailVo emailVo;


    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmMasTask selectCrmMasTaskById(Integer id) {
        return crmMasTaskMapper.selectCrmMasTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param crmMasTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmMasTask> selectCrmMasTaskList(CrmMasTask crmMasTask) {
        return crmMasTaskMapper.selectCrmMasTaskList(crmMasTask);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmMasTask(CrmMasTask crmMasTask) {
        return crmMasTaskMapper.insertCrmMasTask(crmMasTask);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmMasTask(CrmMasTask crmMasTask) {
        return crmMasTaskMapper.updateCrmMasTask(crmMasTask);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskByIds(Long[] ids) {
        return crmMasTaskMapper.deleteCrmMasTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskById(Long id) {
        return crmMasTaskMapper.deleteCrmMasTaskById(id);
    }

    /**
     * 创建敞口划分任务
     *
     * @param entityInfos  主体信息
     * @param taskCategory 数据来源
     * @param taskDate     任务日期
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createTasks(List<EntityInfo> entityInfos, String taskCategory, Date taskDate) {
        if (CollUtil.isEmpty(entityInfos)) {
            return false;
        }

        List<CrmMasTask> masTasks = entityInfos.stream()
                .filter((item -> {
                    EntityMaster entityMaster = entityMasterMapper.selectOne(
                            Wrappers.<EntityMaster>lambdaQuery()
                                    .eq(EntityMaster::getEntityCode, item.getEntityCode())
                    );

                    return entityMaster == null;
                })).map(item -> {

                    CrmMasTask masTask = new CrmMasTask();
                    masTask.setSourceName(taskCategory);
                    masTask.setEntityCode(item.getEntityCode());
                    masTask.setTaskDate(taskDate);
                    return masTask;
                }).collect(Collectors.toList());

        //保存进库
        this.saveBatch(masTasks);

        //修改当天任务状态
        dailyTaskService.updateToUnhandled(taskDate, RoleInfo.ROLE2);

        return true;
    }

    /**
     * 角色2今日运维模块
     *
     * @param date       请传入参数 yyyy-MM-dd
     * @param sourceName 来源
     * @param pageNum    页码
     * @param pageSize   页数
     * @return R<Page < CrmMasTaskVo>> 当日任务
     * @author 正杰
     * @date 2022/9/27
     */
    @Override
    public R<Page<CrmMasTaskVo>> getTaskInfo(String date, String sourceName, Integer pageNum, Integer pageSize) {
        log.info("  =>> 角色2 {} 查询 <<=  ", date);
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 5 : pageSize;
        Date dateDay = DateUtil.parseDate(date);
        QueryWrapper<CrmMasTask> crmMasTaskQueryWrapper = new QueryWrapper<>();
        crmMasTaskQueryWrapper.lambda().eq(CrmMasTask::getTaskDate, dateDay);

        // 如果 来源信息不为空 那么就为其添加来源数据查询条件
        if (!ObjectUtils.isEmpty(sourceName)) {
            crmMasTaskQueryWrapper.lambda().eq(CrmMasTask::getSourceName, sourceName);
        }

        Page<CrmMasTask> crmMasTaskPage = baseMapper.selectPage(new Page<>(pageNum, pageSize), crmMasTaskQueryWrapper.lambda().orderBy(true, true, CrmMasTask::getState));
        List<CrmMasTask> crmMasTasks = crmMasTaskPage.getRecords();

        Page<CrmMasTaskVo> result = new Page<>(pageNum, pageSize, crmMasTaskPage.getTotal());
        ArrayList<CrmMasTaskVo> crmMasTaskVos = new ArrayList<>();
        crmMasTasks.forEach(row -> {
            CrmMasTaskVo crmMasTaskVo = new CrmMasTaskVo();
            BeanUtil.copyProperties(row, crmMasTaskVo);
            if (row.getEntityCode() != null) {
                EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                        .eq(EntityInfo::getEntityCode, row.getEntityCode()));
                if (!ObjectUtils.isEmpty(entityInfo)) {
                    crmMasTaskVo.setEntityName(entityInfo.getEntityName());
                    crmMasTaskVo.setCreditCode(entityInfo.getCreditCode());
                }
            } else {
                log.warn("  =>> 角色2 出现无效信息 taskId {} entity_code 为空  <<=  !!!", row.getId());
            }
            crmMasTaskVos.add(crmMasTaskVo);
        });
        result.setRecords(crmMasTaskVos);
        log.info("  =>> 角色2 查询结束  <<=  ");
        return R.ok(result, SuccessInfo.GET_SUCCESS.getInfo());

    }

    /**
     * 角色2 完成任务
     *
     * @param taskId
     * @param username
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Date finishTask(Integer taskId, String username) {
        CrmMasTask crmMasTask = Optional.ofNullable(baseMapper.selectCrmMasTaskById(taskId)).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
        Assert.isTrue(crmMasTask.getState() == 0, BadInfo.EXITS_TASK_FINISH.getInfo());
        crmMasTask.setState(1).setHandleUser(username);
        baseMapper.updateById(crmMasTask);
        // 为状态表中 修改当前状态表数据中的 状态 entity_capture_speed
        EntityCaptureSpeed entityCaptureSpeed = entityCaptureSpeedMapper.selectOne(new QueryWrapper<EntityCaptureSpeed>().lambda().eq(EntityCaptureSpeed::getId, crmMasTask.getSpeedId()));
        if (ObjectUtils.isEmpty(entityCaptureSpeed)) {
            log.warn("  =>> 角色2任务 {} 未查询到关联 entity_capture_speed 表 id为 {} 的数据", taskId, crmMasTask.getSpeedId());
        } else {
            entityCaptureSpeed.setDivide(1);
            entityCaptureSpeed.setDivideTime(new Date());
            entityCaptureSpeedMapper.updateById(entityCaptureSpeed);

        }
        return crmMasTask.getTaskDate();
    }

    @Override
    public boolean isTaskFinished(List<CrmMasTask> crmMasTasks) {
        try {
            ArrayList<EmailCrmTask> emailCrmTasks = new ArrayList<>();
            for (CrmMasTask crmMasTask : crmMasTasks) {
                EmailCrmTask emailCrmTask = new EmailCrmTask();
                EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, crmMasTask.getEntityCode()));
                EntityMaster entityMaster = entityMasterMapper.selectOne(new LambdaQueryWrapper<EntityMaster>().eq(EntityMaster::getEntityCode, crmMasTask.getEntityCode()));
                ModelMaster modelMaster = modelMasterMapper.selectOne(new LambdaQueryWrapper<ModelMaster>().eq(ModelMaster::getMasterCode, entityMaster.getMasterCode()));
                emailCrmTask.setEntityName(entityInfo.getEntityName());
                emailCrmTask.setCode(entityInfo.getCreditCode());
                emailCrmTask.setSourceName(Objects.equals(crmMasTask.getSpeedId(),null)?crmMasTask.getSourceName():"新增主体");
                emailCrmTask.setWindMaster(Objects.equals(entityInfo.getWindMaster(),null)?"-":entityInfo.getWindMaster());
                emailCrmTask.setShenWanMaster(Objects.equals(entityInfo.getShenWanMaster(),null)?"-":entityInfo.getShenWanMaster());
                emailCrmTask.setResult(modelMaster.getMasterName());
                emailCrmTask.setRemarks(Objects.equals(entityMaster.getRemark(),null)?"-":entityMaster.getRemark());
                emailCrmTasks.add(emailCrmTask);
            }
            String title = "角色2完成任务情况：";
            //画表格
            StringBuffer content = new StringBuffer();
            if (crmMasTasks.size() != 0) {
                content.append("<h2 style=\" font-size: 14px;\">角色2完成任务情况详细信息如下:</h2>");
                content.append("<table border=\"1\" style=\"border:solid 1px #E8F2F9;font-size=14px;\">");
                content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>主体名称</th><th>统一社会性代码</th><th>来源</th><th>Wind</th><th>申万</th><th>划分结果</th><th>备注</th></tr>");
                for (EmailCrmTask row : emailCrmTasks) {
                    content.append("<tr>");
                    //主体名称
                    content.append("<td align=\"center\">" + row.getEntityName() + "</td>");
                    //统一社会性代码
                    content.append("<td align=\"center\">" + row.getCode() + "</td>");
                    //来源
                    content.append("<td align=\"center\">" + row.getSourceName() + "</td>");
                    //Wind
                    content.append("<td align=\"center\">" + row.getWindMaster() + "</td>");
                    //申万
                    content.append("<td align=\"center\">" + row.getShenWanMaster() + "</td>");
                    //划分结果
                    content.append("<td align=\"center\">" + row.getResult() + "</td>");
                    //备注
                    content.append("<td align=\"center\">" + row.getRemarks() + "</td>");
                    content.append("</tr>");
                }
                content.append("</table>");
            } else {
                content.append("<h2 style=\" font-size: 14px;\">角色2未提供相应的完成情况</h2>");
            }
            List<String> passwords = emailVo.getPasswords();

            for (String s : passwords) {
                EmailUtil.sendTemplateEmail(title, content.toString(), s);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }
}
