package com.deloitte.crm.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.dto.CrmWindTaskDto;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmWindTaskMapper;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

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

    /***
     *根据指定日期查询当月的任务
     *
     * @param TaskDate
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 10:46
    */
    @Override
    public List<CrmWindTask> selectCrmWindTaskByDate(String TaskDate){

        String firstDay=  TaskDate+"-01";
        LocalDate today = LocalDate.parse(firstDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDay = today.with(TemporalAdjusters.lastDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endTime = endDay.format(formatter);

        return null;
    }
    /**
     *根据指定日期查询任务完成度实现
     *
     * @return List<CrmWindTaskDto>
     * @author penTang
     * @date 2022/9/22 10:51
    */
    @Override
    public List<CrmWindTaskDto> selectComTaskByDate(String TaskDate){
        List<CrmWindTaskDto> crmWindTaskDtos = crmWindTaskMapper.selectComWindByDate(TaskDate);

        return crmWindTaskDtos;
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
