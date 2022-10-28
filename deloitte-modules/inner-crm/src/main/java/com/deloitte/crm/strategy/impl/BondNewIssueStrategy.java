package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.BondStatus;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.mapper.BondNewIssMapper;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 * 新债发行-新发行债券-20220801-20220914
 */
@Component
public class BondNewIssueStrategy implements WindTaskStrategy {

    @Resource
    private IBondNewIssService bondNewIssService;

    @Resource
    private BondNewIssMapper bondNewIssMapper;

    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;


    @Resource
    private IEntityBondRelService bondRelService;

    @Resource
    private ICrmMasTaskService crmMasTaskService;

    @Resource
    private IEntityInfoService entityInfoService;


    /**
     * 根据导入的BondNewIss信息，处理bondinfo表和entityattrvalue
     *
     * @param newIss
     * @param timeNow
     * @return 如果这条 newIss 是新增的，返回1
     * 如果这条 newIss 是原有基础上有修改，返回2
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<BondInfoDto> doBondImport(BondNewIss newIss, Date timeNow, CrmWindTask windTask) {
        try {
            //设置任务相关信息进临时表
//            newIss.setImportTime(new Date());
            newIss.setTaskId(windTask.getId());

            Integer resStatus = null;

            //查询债券是否存在
            String shortName = newIss.getBondShortName();

            //查询有没有这个债券
            BondInfo bondInfo = bondInfoService.findByShortName(shortName, Boolean.FALSE);
            if (bondInfo == null) {
                bondInfo = new BondInfo();
            }

            bondInfo.setBondShortName(shortName);
            bondInfo.setOriCode(newIss.getTradeCode());
            bondInfo.setBondName(newIss.getBondName());
            bondInfo.setValueDate(newIss.getValueDate());
            bondInfo.setDueDate(newIss.getExpireDate());

            //看之前有没有导入过这个数据
            BondNewIss last = bondNewIssMapper.findLastByShortName(shortName);
            if (last == null) {
                resStatus = DataChangeType.INSERT.getId();
            } else {
                last.setWindIndustry(null);
                newIss.setWindIndustry(null);
                if (!Objects.equals(last, newIss)) {
                    resStatus = DataChangeType.UPDATE.getId();
                }
            }
            Integer newStatus = judgeBondStatus(bondInfo.getBondStatus(), newIss.getIssStartDate(), newIss.getIssEndDate(), newIss.getIpoDate(), timeNow);
            if (newStatus != null) {
                bondInfo.setBondStatus(newStatus);
            }


            //保存当前债券
            BondInfo newDbBond = bondInfoService.saveOrUpdate(bondInfo);

            BondInfoDto bondInfoDto = new BondInfoDto();
            bondInfoDto.setBondInfo(bondInfo);
            bondInfoDto.setResStatus(resStatus);

            //设置newIss的状态
            newIss.setChangeType(resStatus);
            /***
             * 设置Wind行业 新增发行也需记录wind行业，格式为 发行人Wind行业(一级)-发行人Wind行业(二级)
             */
            String windIndustry = null;
            //发行人Wind行业(一级)
            String issorIndustryFirst = newIss.getIssorIndustryFirst();
            //发行人Wind行业(二级)
            String issorIndustrySecond = newIss.getIssorIndustrySecond();
            if (StringUtils.isNotEmpty(issorIndustryFirst)) {
                if (StringUtils.isNotEmpty(issorIndustrySecond)) {
                    windIndustry = issorIndustryFirst + "--" + issorIndustrySecond;
                } else {
                    windIndustry = issorIndustryFirst;
                }
            }

            newIss.setWindIndustry(windIndustry);

            bondNewIssMapper.insert(newIss);

            //绑定债券和主体关系
            String entityName = newIss.getIssorName();
            bondRelService.bindRelOrCreateTask(entityName, bondInfo, newIss, windTask);


            //如果债券状态变为成功上市，创建敞口划分任务
            List<EntityInfo> entityInfos = entityInfoService.findByName(entityName);
            Integer bondStatus = bondInfo.getBondStatus();

            //债券发行成功，主体发债状态
            if (Objects.equals(bondStatus, BondStatus.WAIT_LIST.getId())) {
                entityInfos.forEach(entity -> {
                    entity.setIssueBonds(1);
                });
            }


            if (Objects.equals(bondStatus, BondStatus.LISTED.getId()) && CollUtil.isNotEmpty(entityInfos)) {
                newDbBond.setBondState(0);
                newDbBond = bondInfoService.saveOrUpdate(bondInfo);

                //修改主体的上市状态
                entityInfos.forEach(entity -> {
                    entity.setList(1);
                });

                //更新主体数据
                entityInfoService.updateBatchById(entityInfos);

                //新敞口划分任务
                crmMasTaskService.createTasks(entityInfos, windTask.getTaskCategory(), windTask.getTaskDate());
            }

            if (resStatus != null) {
                //更新当前债券属性
                entityAttrValueService.updateBondAttr(newDbBond.getBondCode(), newIss);
            }

            return new AsyncResult(bondInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult(e);
        }
    }


    /**
     * 判断债券状态
     *
     * @param bondStatus   当前状态
     * @param startDateStr 发行起始日
     * @param endDateStr   发行截止日
     * @param ipoDateStr   上市日期
     * @param timeNow      今天
     * @return
     */
    private Integer judgeBondStatus(Integer bondStatus, String startDateStr, String endDateStr, String ipoDateStr, Date timeNow) {
        //发行起始日
        Date startDate = DateUtil.parseDate(startDateStr);
        //发行截止日
        Date endDate = DateUtil.parseDate(endDateStr);
        //上市日期
        Date ipoDate = DateUtil.parseDate(ipoDateStr);

        int compare = DateUtil.compare(startDate, timeNow);
        if (compare > 0) {
            //当债券的【发行起始日】晚于今天，则记为“等待发行”
            return BondStatus.WAIT.getId();
        } else if (compare == 0 && Objects.equals(bondStatus, BondStatus.WAIT.getId())) {
            //当债券状态已经是“等待发行”的情况下，在【发行起始日】= 今天 时，改为“正在发行”
            return BondStatus.ISSUE.getId();
        }

        //当债券状态已经是“正在发行”的情况下，在【发行截止日】= 今天 时，改为“已发行待上市”
        if (Objects.equals(bondStatus, BondStatus.ISSUE.getId())
                &&
                DateUtil.compare(endDate, timeNow) == 0
        ) {
            return BondStatus.WAIT_LIST.getId();
        }

        //当债券状态已经是“已发行待上市”的情况下，在【上市日期】= 今天 时，改为“成功上市”
        if (Objects.equals(bondStatus, BondStatus.WAIT_LIST.getId())
                &&
                DateUtil.compare(ipoDate, timeNow) == 0
        ) {
            return BondStatus.LISTED.getId();
        }

        return null;
    }

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
        List<BondNewIss> isses = util.importExcel(windTaskContext.getFileStream(), true);
        Collections.reverse(isses);
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
        arr.add("变化状态");

        arr.add("债券简称");
        arr.add("交易代码");
        arr.add("发行人全称");


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

        List<BondNewIss> bondNewIsses = bondNewIssService.findByTaskIdChangeType(taskId, 1, 2);
        return bondNewIsses.stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());


            dataMap.put("债券简称", item.getBondShortName());
            dataMap.put("交易代码", item.getTradeCode());
            dataMap.put("发行人全称", item.getIssorName());

            return dataMap;
        }).collect(Collectors.toList());
    }
}

