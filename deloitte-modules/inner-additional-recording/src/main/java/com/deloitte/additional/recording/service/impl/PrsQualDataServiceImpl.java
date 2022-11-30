package com.deloitte.additional.recording.service.impl;

import com.alibaba.excel.util.IoUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.domain.PrsQualData;
import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import com.deloitte.additional.recording.mapper.PrsQualDataMapper;
import com.deloitte.additional.recording.service.EntityInfoService;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.service.PrsQualDataService;
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

/**
 * (PrsQualData)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsQualDataService")
public class PrsQualDataServiceImpl extends ServiceImpl<PrsQualDataMapper, PrsQualData> implements PrsQualDataService {


    @Autowired
    private EntityInfoService entityInfoService;

    @Autowired
    private PrsModelQualService modelQualService;

    @Override
    public void importQualFromExcel(MultipartFile serviceFile) {
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
            List<PrsQualData> list = new CopyOnWriteArrayList<>();
            //验证表头  evd名称-evdcode
            maps.stream().forEach(map -> {
                String entityCode = map.get(Constants.ENTITY_CODE);
                EntityInfo info = entityInfoService.getByCode(entityCode);
                if (info == null) {
                    throw new ServiceException(entityCode + " 实体代码导入有误,请重试");
                }
                headers.forEach(head -> {
                    //校验指标
                    String[] split = head.split("-");
                    //指标名称
                    String qualName = split[0];
                    //指标code
                    String qualCode = split[1];
                    checkQual(qualName, qualCode);
                    //组装数据
                    PrsQualData qualData = new PrsQualData().createBy(qualCode, entityCode, map.get(head), map.get(Constants.DATA_YEAR));
                    list.add(qualData);
                });
            });
            //保存数据
            saveData(list);
        } catch (IOException e) {
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

    @Async
    public void saveData(List<PrsQualData> list) {
        Iterator<PrsQualData> iterator = list.iterator();
        while (iterator.hasNext()) {
            PrsQualData next = iterator.next();
            PrsQualData qualData = getByEntityAndQcodeAndTime(next.getEntityCode(), next.getQualCode(), next.getTimeValue());
            if (qualData != null) {
                //需要更新
                qualData.setQualValue(next.getQualValue());
                qualData.setUpdated(new Date());
                updateById(qualData);
                //移除已更新的数据
                list.remove(next);
            }
        }
        //批量插入
        this.saveBatch(list);
    }

    /**
     * 根据实体code 指标code 年份查询
     *
     * @param entityCode 实体code
     * @param qualCode   指标code
     * @param timeValue  年份
     * @return
     */
    @Override
    public PrsQualData getByEntityAndQcodeAndTime(String entityCode, String qualCode, String timeValue) {

        return lambdaQuery().eq(PrsQualData::getEntityCode, entityCode)
                .eq(PrsQualData::getQualCode, qualCode)
                .eq(PrsQualData::getTimeValue, timeValue)
                .one();
    }

    @Override
    public List<PrsQualDataExcelDto> findExcelListByCode(String code) {
        return this.baseMapper.findExcelListByCode(code);
    }

    @Override
    public long countEntity(String qualCode, String dataYear) {

        return lambdaQuery().eq(PrsQualData::getQualCode,qualCode).eq(PrsQualData::getTimeValue,dataYear).count();
    }

    @Override
    public long countByValue(String qualCode, String dataYear, String value) {


        return lambdaQuery().eq(PrsQualData::getQualCode,qualCode).eq(PrsQualData::getTimeValue,dataYear).eq(PrsQualData::getQualValue,value).count();

    }

    @Override
    public long countLose(String qualCode, String dataYear) {


        return this.baseMapper.countLose(qualCode,dataYear);
    }

    /**
     * 校验指标
     *
     * @param qualCode 指标code
     * @param qualName 指标名称
     */
    private void checkQual(String qualName, String qualCode) {

        //检验指标
        PrsModelQual modelQual = modelQualService.getByNameAndCode(qualName, qualCode);
        if (modelQual == null) {
            throw new ServiceException("表格数据有误：" + qualName + "-" + qualCode);
        }
    }
}
