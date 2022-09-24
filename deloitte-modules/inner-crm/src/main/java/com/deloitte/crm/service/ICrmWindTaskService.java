package com.deloitte.crm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.vo.WindTaskDetailsVo;
import org.springframework.web.multipart.MultipartFile;
import com.deloitte.crm.dto.CrmWindTaskDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 角色1的每日任务，导入wind文件的任务Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmWindTaskService extends IService<CrmWindTask>

{
    /**
     *查询某组任务信息详情的接口
     *
     * @param TaskDate
     * @param TaskCateId
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 17:05
    */
    List<CrmWindTask> selectCrmWindTask(@RequestBody String TaskDate, String TaskCateId);

    /**
     *根据指定日期查询当月的任务
     *
     * @param TaskDate
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 10:48
    */
    List<CrmWindTask> selectCrmWindTaskByDate(String TaskDate);

    /**
     * 根据指定日期查询任务完成度接口
     *
     * @param TaskDate
     * @return List<CrmWindTaskDto>
     * @author penTang
     * @date 2022/9/22 17:06
    */
    List<CrmWindTaskDto> selectComTaskByDate(String TaskDate);

    Boolean saveCrmWindTas(List<CrmWindTask> crmWind);

    /**
     * 查询角色1的每日任务，导入wind文件的任务
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 角色1的每日任务，导入wind文件的任务
     */
    public CrmWindTask selectCrmWindTaskById(Long id);

    /**
     * 查询角色1的每日任务，导入wind文件的任务列表
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 角色1的每日任务，导入wind文件的任务集合
     */
    public List<CrmWindTask> selectCrmWindTaskList(CrmWindTask crmWindTask);

    /**
     * 新增角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    public int insertCrmWindTask(CrmWindTask crmWindTask);

    /**
     * 修改角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    public int updateCrmWindTask(CrmWindTask crmWindTask);

    /**
     * 批量删除角色1的每日任务，导入wind文件的任务
     * 
     * @param ids 需要删除的角色1的每日任务，导入wind文件的任务主键集合
     * @return 结果
     */
    public int deleteCrmWindTaskByIds(Long[] ids);

    /**
     * 删除角色1的每日任务，导入wind文件的任务信息
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 结果
     */
    public int deleteCrmWindTaskById(Long id);


    Object doTask(Long taskId, MultipartFile file) throws Exception;

    /**
     * 检查指定日期任务完成状态，如果全部都是已完成，那么更改今天角色3的日常任务状态
     * @param timeNow
     * @return
     */
    boolean checkAllComplete(Date timeNow);

    /**
     * 角色1 任务详情页面需要的数据
     * @param taskCateId
     * @param taskDate
     * @return
     */
    List<WindTaskDetailsVo> findTaskDetails(Integer taskCateId, String taskDate);

    /**
     * 角色1查询有过修改的数据列表
     * @param windTasks 具体分类id
     * key: excel中的列名
     * value: 数据
     * @return
     */
    List<Map<String, Object>> findImportDetail(CrmWindTask windTasks);

    /**
     * 角色1查询有过修改的数据列表表头
     * @param taskDictId
     * @return
     */
    List<String> findImportDetailHeader(Long taskDictId);
}
