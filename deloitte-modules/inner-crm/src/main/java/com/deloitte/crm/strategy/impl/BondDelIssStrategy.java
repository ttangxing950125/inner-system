package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.BondStatus;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.service.BondDelIssService;
import com.deloitte.crm.service.IBondInfoService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
@Component
public class BondDelIssStrategy implements WindTaskStrategy {

    @Resource
    private BondDelIssService bondDelIssService;

    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    /**
     * 根据导入的BondNewIss信息，处理bondinfo表和entityattrvalue
     * @param delIss
     * @param timeNow
     * @return 如果这条 delIss 是新增的，changeType返回1
     *         如果这条 delIss 是原有基础上有修改，changeType返回2
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<BondInfoDto> doBondImport(BondDelIss delIss, Date timeNow, CrmWindTask windTask){
        try {
            //查询债券是否存在
            String shortName = delIss.getBondShortName();

            Integer changeType = null;

            //查询有没有这个债券
            BondInfo bondInfo = bondInfoService.findByShortName(shortName,Boolean.FALSE);
            if (bondInfo==null){
                bondInfo = new BondInfo();
            }

            bondInfo.setBondShortName(shortName);
            bondInfo.setOriCode(delIss.getBondCode());

            //查询有没有这条数据
            List<BondDelIss> bondDelIsses = bondDelIssService.findByBondName(shortName);
            if (CollUtil.isEmpty(bondDelIsses)){
                changeType = DataChangeType.INSERT.getId();
            }


            //当债券进入 推迟或取消发行债券表 时，记为“推迟发行”
            bondInfo.setBondStatus(BondStatus.DELAY_ISSUE.getId());
            if (Objects.equals(delIss.getEvent(), BondStatus.ISSUE_FAIL.getName() )){
                bondInfo.setBondStatus(BondStatus.ISSUE_FAIL.getId());
            }
            bondInfo = bondInfoService.saveOrUpdate(bondInfo);

            //更新债券属性
            //更新当前债券属性
            int updateCount = entityAttrValueService.updateBondAttr(bondInfo.getBondCode(), delIss);
            if (changeType==null && updateCount>0){
                changeType = DataChangeType.UPDATE.getId();
            }


            delIss.setChangeType(changeType);
            delIss.setTaskId(windTask.getId());
            bondDelIssService.save(delIss);

            BondInfoDto bondInfoDto = new BondInfoDto();
            bondInfoDto.setBondInfo(bondInfo);
            bondInfoDto.setResStatus(changeType);

            return new AsyncResult(bondInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult(e);
        }
    }

    /**
     * 是否支持当前wind任务
     * @param windDictId
     * @return
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(windDictId, WindTaskEnum.BOND_DEL_ISS.getId());
    }

    /**
     * 开始执行任务
     * @param windTaskContext wind文件上下文对象，包含各种需要的对象
     * @return
     */
    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
//        读取文件
        ExcelUtil<BondDelIss> util = new ExcelUtil<BondDelIss>(BondDelIss.class);
        List<BondDelIss> delIsses = util.importExcel(windTaskContext.getFileStream(), true);


        return bondDelIssService.doTask(windTask, delIsses);
    }

    /**
     * 获得任务详情页，上传的数据的表头
     * @param windTask
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        ArrayList<String> arr = new ArrayList<>();
        //证券代码
        //证券简称
        //公司中文名称
        arr.add("导入日期");
        arr.add("变化状态");


        arr.add("债券代码");
        arr.add("债券简称");
        arr.add("发行人简称");


        return arr;
    }

    /**
     * 获得任务详情页，上传的详情数据
     * key：表头
     * value：库中的数据
     * @param windTask
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Wrapper<BondDelIss> wrapper = Wrappers.<BondDelIss>lambdaQuery()
                .eq(BondDelIss::getTaskId, taskId)
                .in(BondDelIss::getChangeType, 1, 2);


        return bondDelIssService.list(wrapper).stream().map(item->{
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("债券代码", item.getBondCode());
            dataMap.put("债券简称", item.getBondShortName());
            dataMap.put("发行人简称", item.getIssorShortName());


            return dataMap;
        }).collect(Collectors.toList());
    }
}
