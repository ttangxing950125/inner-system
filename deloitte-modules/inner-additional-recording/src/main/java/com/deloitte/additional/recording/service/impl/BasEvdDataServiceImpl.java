package com.deloitte.additional.recording.service.impl;

import com.alibaba.excel.util.IoUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.mapper.BasEvdDataMapper;
import com.deloitte.additional.recording.mapper.BasEvdTaskInfoMapper;
import com.deloitte.additional.recording.mapper.BasEvdTaskLogMapper;
import com.deloitte.additional.recording.mapper.PrsModelQualEvdMapper;
import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.mapper.BasEvdDataMapper;
import com.deloitte.additional.recording.service.BasEvdDataService;
import com.deloitte.additional.recording.service.PrsModelQualEvdService;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import com.deloitte.additional.recording.service.EntityInfoService;
import com.deloitte.additional.recording.util.EasyExcelImportUtils;
import com.deloitte.common.core.constant.Constants;
import com.deloitte.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (BasEvdData)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:36
 */
@Service("basEvdDataService")
public class BasEvdDataServiceImpl extends ServiceImpl<BasEvdDataMapper, BasEvdData> implements BasEvdDataService {

    @Resource
    private PrsModelQualEvdMapper prsModelQualEvdMapper;

    @Resource
    private BasEvdTaskInfoMapper basEvdTaskInfoMapper;

    @Resource
    private BasEvdTaskLogMapper basEvdTaskLogMapper;

    /**
     * 绑定evd_data和evd_task和task_info
     * @param versionMaster
     * @param entityCode
     * @param taskYear
     * @author wpp
     * @return
     */
    @Override
    public boolean bindEvdData(PrsVersionMaster versionMaster, String entityCode, Integer taskYear) {

        List<BasEvdInfo> qualEvds = prsModelQualEvdMapper.findLackEvd(versionMaster.getId(), entityCode, taskYear);

        qualEvds.forEach(item->{
            //evdData
            BasEvdData evdData = BasEvdData.builder()
                    .evdCode(item.getCode())
                    .entityCode(entityCode)
                    .timeValue(taskYear.toString())
                    .unit(item.getUnit())
                    .build();

            Wrapper<BasEvdData> wrapper = Wrappers.<BasEvdData>lambdaQuery()
                    .eq(BasEvdData::getEvdCode, evdData.getEvdCode())
                    .eq(BasEvdData::getEntityCode, evdData.getEntityCode())
                    .eq(BasEvdData::getTimeValue, taskYear);

            BasEvdData dbEvdData = getOne(wrapper);
            if (dbEvdData!=null){
                return;
            }

            baseMapper.insert(evdData);

            //task
            BasEvdTaskInfo taskInfo = BasEvdTaskInfo.builder()
                    .dataId(evdData.getId())
                    .dataType(1)
                    .build();

            basEvdTaskInfoMapper.insert(taskInfo);

            //log
            BasEvdTaskLog evdTaskLog = BasEvdTaskLog.builder()
                    .taskId(taskInfo.getId())
                    .actType(1)
                    .remark("拉取主体创建任务")
                    .build();

            basEvdTaskLogMapper.insert(evdTaskLog);
        });



        return true;
    }
    @Autowired
    private EntityInfoService entityInfoService;

    @Autowired
    private BasEvdInfoService basEvdInfoService;

    @Override
    public void importWindFromExcel(MultipartFile serviceFile) {

        InputStream in = null;
        try {
            in = serviceFile.getInputStream();
            byte[] bytes = IoUtils.toByteArray(in);
            //默认读取第一行为表头，约定的固定格式
            List<Map<String, String>> maps = EasyExcelImportUtils.parseExcelToData(bytes, 1);
            //获得所有的表头
            List<String> headers = new ArrayList<>();
            Set<String> strings = maps.get(0).keySet();
            for (String value : strings) {
                if (!Constants.IMPORT_CONTANTS.contains(value)) {
                    headers.add(value);
                }
            }
            //声明一个集合用来接收导入的数据
            List<BasEvdData> list = new CopyOnWriteArrayList<>();
            getDataList(maps, headers, list);
            //需要便利集合 验证数据库中是否存在，存在则更新 evd_val 不存在则插入
            saveData(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    //关闭流
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void getDataList(List<Map<String, String>> maps, List<String> headers, List<BasEvdData> list) {
        maps.forEach(map -> {
            String entityCode = map.get(Constants.ENTITY_CODE);
            EntityInfo info = entityInfoService.getByCode(entityCode);
            if (info == null) {
                throw new ServiceException(entityCode + " 实体代码导入有误,请重试");
            }
            //验证表头  evd名称-evdcode
            headers.forEach(head -> {
                String[] split = head.split("-");
                //得到evdcode
                String evdCode = split[1];
                checkEvdInfo(split[0], evdCode);
                BasEvdData evdData = new BasEvdData().createBy(entityCode, evdCode, map.get(head), map.get(Constants.DATA_YEAR));
                list.add(evdData);
            });
        });
    }

    /**
     * 验证evdInfo
     *
     * @param evdName     evdName
     * @param evdCode     evd_code
     */
    private BasEvdInfo checkEvdInfo( String evdName, String evdCode) {
        BasEvdInfo evdInfo = basEvdInfoService.getByCodeAndName(evdCode,evdName);
        if (evdInfo == null) {
            throw new ServiceException("导入信息有误,不存在" + evdCode);
        }
        return evdInfo;
    }

    @Async
    public void saveData(List<BasEvdData> list) {
        Iterator<BasEvdData> iterator = list.iterator();
        while (iterator.hasNext()) {
            BasEvdData next = iterator.next();
            BasEvdData evdData = getByEntityAndEvdAndTime(next.getEntityCode(), next.getEvdCode(), next.getTimeValue());
            if (evdData != null) {
                //需要更新
                evdData.setEvdVal(next.getEvdVal());
                evdData.setUpdated(new Date());
                updateById(evdData);
                //移除已更新的数据
                list.remove(next);
            }
        }
        //批量插入
        this.saveBatch(list);
    }

    @Override
    public BasEvdData getByEntityAndEvdAndTime(String entityCode, String evdCode, String year) {
        return lambdaQuery().eq(BasEvdData::getEntityCode, entityCode)
                .eq(BasEvdData::getEvdCode, evdCode)
                .eq(BasEvdData::getTimeValue, year)
                .one();
    }

}
