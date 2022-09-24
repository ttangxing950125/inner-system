package com.deloitte.crm.service.impl;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.service.IBondNewIssService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.vo.WindTaskDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.dto.CrmWindTaskDto;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmWindTaskMapper;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private IBondNewIssService bondNewIssService;

    @Resource
    private ICrmDailyTaskService dailyTaskService;

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
        //任务是否完成，已完成不允许上传
        if (!Objects.equals(windTask.getComplete(), 0)){
            throw new GlobalException("只能处理未处理的任务");
        }

        Date taskDate = windTask.getTaskDate();

        Date date = new Date();

        int compare = DateUtil.compare(taskDate, date, "yyyy-MM-dd");

        //只能完成当天的
        if (compare!=0){
            throw new GlobalException("只能完成当天的任务");
        }


        //改任务未完成，读取文件
        //暂时不做枚举和策略模式了。。。先跑起来
        if (Objects.equals(windTask.getTaskDictId(), 15L)){
            //读取文件
            ExcelUtil<BondNewIss> util = new ExcelUtil<BondNewIss>(BondNewIss.class);
            List<BondNewIss> isses = util.importExcel(file.getInputStream());
            return bondNewIssService.doTask(windTask, isses);
        }else {
            throw new GlobalException("暂不支持:"+windTask.getTaskFileName());
        }



//        return true;
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

        LambdaUpdateWrapper<CrmDailyTask> updateDaily = Wrappers.<CrmDailyTask>lambdaUpdate()
                .eq(CrmDailyTask::getTaskDate, timeNow)
                .eq(CrmDailyTask::getTaskRoleType, RoleInfo.ROLE1.getId())
                .eq(CrmDailyTask::getTaskStatus, 2)
                .set(CrmDailyTask::getTaskStatus, 3);

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

        List<WindTaskDetailsVo> detailsVos = windTasks.stream().map(item -> {
            WindTaskDetailsVo detailsVo = new WindTaskDetailsVo();
            detailsVo.setWindTask(item);
            detailsVo.setTaskFileName(item.getTaskFileName());
            detailsVo.setTaskStatus(item.getComplete());
            //查询展示到列表上的信息
            List<Map<String, Object>> data = this.findImportDetail(item);

            List<String> header = this.findImportDetailHeader(item.getTaskDictId());


            detailsVo.setHeader(header);
            detailsVo.setData(data);

            return detailsVo;
        }).collect(Collectors.toList());

        return detailsVos;
    }

    /**
     * 角色1查询有过修改的数据列表
     *
     *                   key: excel中的列名
     *                   value: 数据
     * @return
     */
    @Override
    public List<Map<String, Object>> findImportDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Long dictId = windTask.getTaskDictId();
        Date taskDate = windTask.getTaskDate();

        if (Objects.equals(dictId, 15L)){
            List<BondNewIss> bondNewIsses = bondNewIssService.findByTaskIdChangeType(taskId, 1,2);
            return bondNewIsses.stream().map(item->{
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("导入日期", item.getImportTime());
                dataMap.put("ID", item.getId());
                dataMap.put("债券简称", item.getBondShortName());
                dataMap.put("发行人全称", item.getIssorName());
                dataMap.put("变化状态", item.getChangeType());

                return dataMap;
            }).collect(Collectors.toList());
        }


        return null;
    }

    /**
     * 角色1查询有过修改的数据列表表头
     *
     * @param taskDictId
     * @return
     */
    @Override
    public List<String> findImportDetailHeader(Long taskDictId) {
        ArrayList<String> arr = new ArrayList<>();

        if (Objects.equals(taskDictId, 15L)){
            arr.add("导入日期");
            arr.add("债券简称");
            arr.add("交易代码");
            arr.add("发行人全称");
            arr.add("变化状态");
        }


        return arr;
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
