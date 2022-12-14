package com.deloitte.crm.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.dto.BondInfoDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 新债发行-新发行债券-20220801-20220914Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IBondNewIssService  extends IService<BondNewIss>
{
    /**
     * 查询新债发行-新发行债券-20220801-20220914
     * 
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 新债发行-新发行债券-20220801-20220914
     */
    public BondNewIss selectBondNewIssById(Long id);

    /**
     * 查询新债发行-新发行债券-20220801-20220914列表
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 新债发行-新发行债券-20220801-20220914集合
     */
    public List<BondNewIss> selectBondNewIssList(BondNewIss bondNewIss);

    /**
     * 新增新债发行-新发行债券-20220801-20220914
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    public int insertBondNewIss(BondNewIss bondNewIss);

    /**
     * 修改新债发行-新发行债券-20220801-20220914
     * 
     * @param bondNewIss 新债发行-新发行债券-20220801-20220914
     * @return 结果
     */
    public int updateBondNewIss(BondNewIss bondNewIss);

    /**
     * 批量删除新债发行-新发行债券-20220801-20220914
     * 
     * @param ids 需要删除的新债发行-新发行债券-20220801-20220914主键集合
     * @return 结果
     */
    public int deleteBondNewIssByIds(Long[] ids);

    /**
     * 删除新债发行-新发行债券-20220801-20220914信息
     * 
     * @param id 新债发行-新发行债券-20220801-20220914主键
     * @return 结果
     */
    public int deleteBondNewIssById(Long id);

    /**
     * 导入债券任务
     * @param windTask
     * @param isses
     * @return
     */
    public Object doTask(CrmWindTask windTask, List<BondNewIss> isses) throws Exception;

    /**
     * 根据taskId查询
     * @param taskId
     * @return
     */
    List<BondNewIss> findByTaskIdChangeType(Integer taskId, Integer...changeType);
}
