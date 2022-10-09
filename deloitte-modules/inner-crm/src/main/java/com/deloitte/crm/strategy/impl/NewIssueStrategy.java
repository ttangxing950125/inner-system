package com.deloitte.crm.strategy.impl;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.IBondNewIssService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
@Component
public class NewIssueStrategy implements WindTaskStrategy {

    @Resource
    private IBondNewIssService bondNewIssService;

    /**
     * 是否支持当前wind任务
     *
     * @param windDictId
     * @return
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(windDictId, WindTaskEnum.BOND_NEW_ISS.getId());
    }

    /**
     * 开始执行任务
     *
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        //读取文件
        ExcelUtil<BondNewIss> util = new ExcelUtil<BondNewIss>(BondNewIss.class);
        List<BondNewIss> isses = util.importExcel(windTaskContext.getFileStream(), true);;
        return bondNewIssService.doTask(windTask, isses);
    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        ArrayList<String> arr = new ArrayList<>();

        arr.add("导入日期");
        arr.add("债券简称");
        arr.add("交易代码");
        arr.add("发行人全称");
        arr.add("变化状态");


        return arr;
    }

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     *
     * @param windTask
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();

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
}
