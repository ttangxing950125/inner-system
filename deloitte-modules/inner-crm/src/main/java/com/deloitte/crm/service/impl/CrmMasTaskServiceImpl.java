package com.deloitte.crm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.EntityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmMasTaskMapper;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.service.ICrmMasTaskService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmMasTaskServiceImpl extends ServiceImpl<CrmMasTaskMapper, CrmMasTask> implements ICrmMasTaskService
{
    @Autowired
    private CrmMasTaskMapper crmMasTaskMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmMasTask selectCrmMasTaskById(Long id)
    {
        return crmMasTaskMapper.selectCrmMasTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmMasTask> selectCrmMasTaskList(CrmMasTask crmMasTask)
    {
        return crmMasTaskMapper.selectCrmMasTaskList(crmMasTask);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmMasTask(CrmMasTask crmMasTask)
    {
        return crmMasTaskMapper.insertCrmMasTask(crmMasTask);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmMasTask(CrmMasTask crmMasTask)
    {
        return crmMasTaskMapper.updateCrmMasTask(crmMasTask);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskByIds(Long[] ids)
    {
        return crmMasTaskMapper.deleteCrmMasTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskById(Long id)
    {
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
    public boolean createTasks(List<EntityInfo> entityInfos, String taskCategory, Date taskDate) {
        List<CrmMasTask> masTasks = entityInfos.stream().map(item -> {
            CrmMasTask masTask = new CrmMasTask();
            masTask.setSourceName(taskCategory);
            masTask.setEntityCode(item.getEntityCode());
            masTask.setTaskDate(taskDate);
            return masTask;
        }).collect(Collectors.toList());

        //保存进库
        this.saveBatch(masTasks);

        return true;
    }
}
