package com.deloitte.additional.recording.service.impl;

import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.IoUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.BasEvdDataTab;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.mapper.BasEvdDataTabMapper;
import com.deloitte.additional.recording.service.BasEvdDataTabService;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import com.deloitte.additional.recording.service.EntityInfoService;
import com.deloitte.additional.recording.util.EasyExcelImportUtils;
import com.deloitte.additional.recording.listener.EasyExcelListener;
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
 * @创建人 tangx
 * @创建时间 2022/11/22
 * @描述 BasEvdDataTab 表业务层
 */
@Service
public class BasEvdDataTabServiceImpl extends ServiceImpl<BasEvdDataTabMapper, BasEvdDataTab> implements BasEvdDataTabService {


    @Autowired
    private EntityInfoService entityInfoService;

    @Autowired
    private BasEvdInfoService basEvdInfoService;


    @Override
    public void importSubTableFromExcel(MultipartFile serviceFile) {
        //子表需要获取sheetName ：父evd名称-evdcode
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
            //sheets 获取sheetName 默认取第一个
            ReadSheet readSheet = EasyExcelListener.sheets.get(0);
            String sheetName = readSheet.getSheetName();
            String[] split = sheetName.split("-");
            BasEvdInfo basEvdInfoParent = checkEvdInfo( split[0], split[1]);
            //验证父子关系
            headers.forEach(head -> {
                String[] header = head.split("-");
                BasEvdInfo basEvdInfoSub = checkEvdInfo( header[0], header[1]);
                //验证父子间关系
                if (!basEvdInfoParent.getId().equals(basEvdInfoSub.getParentId())) {
                    throw new ServiceException("当前数据:" + head + "不属于" + sheetName + "组合下的子表数据");
                }
            });
            //声明集合放置表格数据
            List<BasEvdDataTab> list = new CopyOnWriteArrayList<>();
            //遍历数据集合
            getDataList(maps, headers, split, list);
            //需要便利集合 验证数据库中是否存在，存在则更新 evd_val 不存在则插入
            saveData(list);

            //多个sheets需要三层遍历
//            for (ReadSheet sheet : EasyExcelListener.sheets) {
//                String sheetName = sheet.getSheetName();
//                String[] split = sheetName.split("-");
//
//            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void getDataList(List<Map<String, String>> maps, List<String> headers, String[] split, List<BasEvdDataTab> list) {
        for (int i = 0; i < maps.size(); i++) {
            Map<String, String> map = maps.get(i);
            //拿到主体code 验证主体信息
            String entityCode = map.get(Constants.ENTITY_CODE);
            EntityInfo info = entityInfoService.getByCode(entityCode);
            if (info == null) {
                throw new ServiceException(entityCode + " 实体代码导入有误,请重试");
            }
            //得到值
            for (String head : headers) {
                String[] header = head.split("-");
                //得到evdcode
                String evdCode = header[1];
                //表格值
                String evdVal = map.get(head);
                BasEvdDataTab evdData = new BasEvdDataTab().createBy(entityCode, split[1], evdCode, evdVal, i + 2);//i从0开始，实际表格数据从2开始
                list.add(evdData);
            }
        }
    }

    @Async
    public void saveData(List<BasEvdDataTab> list) {
        Iterator<BasEvdDataTab> iterator = list.iterator();
        while (iterator.hasNext()) {
            BasEvdDataTab next = iterator.next();
            BasEvdDataTab evdData = getByEntityAndEvdAndEvdP(next.getEntityCode(), next.getEvdCode(), next.getParentCode());
            if (evdData != null) {
                //需要更新
                evdData.setEvdVal(next.getEvdVal());
                updateById(evdData);
                //移除已更新的数据
                list.remove(next);
            }
        }
        //批量插入
        this.saveBatch(list);
    }

    /**
     * 通过主体code evdCode evd父code 查询
     *
     * @param entityCode 主体code
     * @param evdCode    evdCode
     * @param parentCode evd父code
     * @return
     */
    private BasEvdDataTab getByEntityAndEvdAndEvdP(String entityCode, String evdCode, String parentCode) {

        return this.lambdaQuery().eq(BasEvdDataTab::getEntityCode, entityCode)
                .eq(BasEvdDataTab::getEvdCode, evdCode)
                .eq(BasEvdDataTab::getParentCode, parentCode)
                .one();
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
}
