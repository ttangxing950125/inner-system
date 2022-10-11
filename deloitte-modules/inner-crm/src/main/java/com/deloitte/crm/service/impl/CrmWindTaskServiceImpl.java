package com.deloitte.crm.service.impl;
import java.util.*;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.dto.TaskDto;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategyManage;
import com.deloitte.crm.vo.CrmTaskVo;
import com.deloitte.crm.vo.WindTaskDetailsVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.dto.CrmWindTaskDto;
import com.deloitte.system.api.model.LoginUser;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmWindTaskMapper;
import com.deloitte.crm.domain.CrmWindTask;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 角色1的每日任务，导入wind文件的任务Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmWindTaskServiceImpl extends ServiceImpl<CrmWindTaskMapper, CrmWindTask> implements ICrmWindTaskService
{
    @Resource
    private CrmWindTaskMapper crmWindTaskMapper;

    @Resource
    private IBondNewIssService bondNewIssService;

    @Resource
    private ICrmDailyTaskService dailyTaskService;

    @Resource
    private WindTaskStrategyManage windTaskStrategyManage;

    @Resource
    private ICrmEntityTaskService crmEntityTaskService;

    @Resource
    private ICrmMasTaskService crmMasTaskService;

    @Resource
    private SendEmailService sendEmailService;

    /**
     * 导入wind文件
     * @param taskId
     * @param file
     * @return
     */
    @Override
    public Object doTask(Long taskId, MultipartFile file) throws Exception {
        //查询当前任务
        CrmWindTask windTask = crmWindTaskMapper.selectCrmWindTaskById(taskId);
        if (windTask==null){
            throw new GlobalException("没有该任务");
        }
        //任务是否完成，已完成不允许上传
        if (!Objects.equals(windTask.getComplete(), 0)){
            throw new GlobalException("只能处理未处理的任务");
        }

        Date taskDate = windTask.getTaskDate();

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        int compare = DateUtil.compare(taskDate, timeNow, "yyyy-MM-dd");

        //只能完成当天的
        if (compare!=0){
            throw new GlobalException("只能完成当天的任务");
        }

        WindTaskContext taskContext = new WindTaskContext();
        taskContext.setFile(file);
        taskContext.setTimeNow(timeNow);
        taskContext.setWindTask(windTask);
        taskContext.setFileStream(file.getInputStream());

        windTaskStrategyManage.doTask(taskContext);

        return true;
    }

    /**
     * 检查指定日期任务完成状态，如果全部都是已完成，那么更改今天角色3的日常任务状态
     *
     * @param timeNow
     * @return
     */
    @Override
    public boolean checkAllComplete(Date timeNow) {
        //查询今天有没有状态为 0 和 2 的
        Wrapper<CrmWindTask> wrapper = Wrappers.<CrmWindTask>lambdaQuery()
                .eq(CrmWindTask::getTaskDate, timeNow)
                .in(CrmWindTask::getComplete, 0, 2);

        long count = this.count(wrapper);
        if (count!=0){
            //代表还有任务未完成
            return false;
        }

        //修改每日任务状态
        LambdaUpdateWrapper<CrmDailyTask> updateDaily = Wrappers.<CrmDailyTask>lambdaUpdate()
                .eq(CrmDailyTask::getTaskDate, timeNow)
                .eq(CrmDailyTask::getTaskRoleType, RoleInfo.ROLE1.getId())
                .eq(CrmDailyTask::getTaskStatus, 2)
                .set(CrmDailyTask::getTaskStatus, 3);

        //查询今天有多少主体新增的任务
        Wrapper<CrmEntityTask> entityTaskQue = Wrappers.<CrmEntityTask>lambdaQuery()
                .eq(CrmEntityTask::getTaskDate, timeNow);

        //发邮件
        long entityTaskCount = crmEntityTaskService.count(entityTaskQue);

        //发送邮件给角色6|7
        sendEmailService.SendEmail(RoleInfo.ROLE6.getId(),"新任务：新增主体"+entityTaskCount+"个待确认",
                "今日wind导入任务已完成，平台捕获"+entityTaskCount+"个疑似新增主体需要确认。" +
                "请尽快登陆平台完成相关任务。");

        //发邮件给角色2
        Wrapper<CrmMasTask> masTaskQue = Wrappers.<CrmMasTask>lambdaQuery()
                .eq(CrmMasTask::getTaskDate, timeNow);

        long entityMasCount = crmMasTaskService.count(masTaskQue);

        sendEmailService.SendEmail(RoleInfo.ROLE2.getId(),"新任务：待划分敞口主体"+entityMasCount+"个",
                "今日新增主体确认任务已完成，共计新增 "+entityMasCount+" 个主体需划分敞口。" +
                        "请尽快登陆平台完成相关任务。" +
                        "请尽快登陆平台完成相关任务。");


        dailyTaskService.update(updateDaily);


        return true;
    }

    /**
     * 角色1 任务详情页面
     * @param taskCateId
     * @param taskDate
     * @return
     */
    @Override
    public List<WindTaskDetailsVo> findTaskDetails(Integer taskCateId, String taskDate) {
        //查询今天某个分类的全部任务
        Wrapper<CrmWindTask> wrapper = Wrappers.<CrmWindTask>lambdaUpdate()
                .eq(CrmWindTask::getTaskDate, taskDate)
                .eq(CrmWindTask::getTaskCateId, taskCateId);
        List<CrmWindTask> windTasks = this.list(wrapper);
        return windTasks.stream().map(item -> {
            WindTaskDetailsVo detailsVo = new WindTaskDetailsVo();
            detailsVo.setWindTask(item);
            detailsVo.setTaskFileName(item.getTaskFileName());
            detailsVo.setTaskStatus(item.getComplete());
            List<Map<String, Object>> data = windTaskStrategyManage.getDetail(item);
            //查询展示到列表上的信息
            List<String> header = windTaskStrategyManage.getDetailHeader(item);
            detailsVo.setHeader(header);
            detailsVo.setData(data);
            return detailsVo;
        }).collect(Collectors.toList());
    }


    /**
     * 查询某组任务信息详情的接口实现
     *
     * @param TaskDate
     * @param TaskCateId
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 17:04
    */
    @Override
    public List<CrmWindTask> selectCrmWindTask(@RequestBody String TaskDate, String TaskCateId){
       return list(new LambdaQueryWrapper<CrmWindTask>()
                .eq(CrmWindTask ::getTaskDate,TaskDate)
                .eq(CrmWindTask :: getTaskCateId,TaskCateId));
    }


    /**
     *根据指定日期查询任务完成度实现
     *
     * @return List<CrmWindTaskDto>
     * @author penTang
     * @date 2022/9/22 10:51
    */
    @Override
    public List<CrmWindTaskDto> selectComTaskByDate(CrmTaskVo crmTaskVo){
        Page<CrmWindTaskDto> page = new Page<>(crmTaskVo.getPageNum(),crmTaskVo.getPageSize());
        List<CrmWindTaskDto> crmWindTaskDtos = crmWindTaskMapper.selectComWindByDate(page,crmTaskVo.getTaskDate());
        return crmWindTaskDtos;
    }

    /**
     *根据指定日期查询任务完成信息
     *
     * @param taskDate
     * @return List<TaskDto>
     * @author penTang
     * @date 2022/10/11 18:56
    */
    public List<TaskDto> getTaskCompletedByDate(String taskDate){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return null;
    }


    /**
     *批量保存每日角色任务信息
     *
     * @return List<CrmWindTaskDto>
     * @author penTang
     * @date 2022/9/22 10:51
     */
    @Override
    public Boolean saveCrmWindTas(List<CrmWindTask> crmWind){
       return saveBatch(crmWind);
    }


    /**
     * 查询角色1的每日任务，导入wind文件的任务
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 角色1的每日任务，导入wind文件的任务
     */
    @Override
    public CrmWindTask selectCrmWindTaskById(Long id)
    {
        return crmWindTaskMapper.selectCrmWindTaskById(id);
    }

    /**
     * 查询角色1的每日任务，导入wind文件的任务列表
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 角色1的每日任务，导入wind文件的任务
     */
    @Override
    public List<CrmWindTask> selectCrmWindTaskList(CrmWindTask crmWindTask)
    {
        return crmWindTaskMapper.selectCrmWindTaskList(crmWindTask);
    }

    /**
     * 新增角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    @Override
    public int insertCrmWindTask(CrmWindTask crmWindTask)
    {
        return crmWindTaskMapper.insertCrmWindTask(crmWindTask);
    }

    /**
     * 修改角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    @Override
    public int updateCrmWindTask(CrmWindTask crmWindTask)
    {
        return crmWindTaskMapper.updateCrmWindTask(crmWindTask);
    }

    /**
     * 批量删除角色1的每日任务，导入wind文件的任务
     * 
     * @param ids 需要删除的角色1的每日任务，导入wind文件的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmWindTaskByIds(Long[] ids)
    {
        return crmWindTaskMapper.deleteCrmWindTaskByIds(ids);
    }

    /**
     * 删除角色1的每日任务，导入wind文件的任务信息
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmWindTaskById(Long id)
    {
        return crmWindTaskMapper.deleteCrmWindTaskById(id);
    }

}
