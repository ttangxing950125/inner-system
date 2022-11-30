package com.deloitte.additional.recording.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.deloitte.additional.recording.domain.PrsQualData;
import com.deloitte.additional.recording.service.PrsQualDataService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/26
 * @描述 // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */
@Slf4j
@Getter
@Setter
public class DataListener<T> implements ReadListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = new ArrayList<>(BATCH_COUNT);


    /**
     * sheets
     */
    private List<ReadSheet> sheets = new ArrayList<>();

    @Autowired
    private PrsQualDataService qualDataService;

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param t       one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T t, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(t));
        cachedDataList.add(t);
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        sheets.add(readSheetHolder.getReadSheet());
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM todo 后面再说

    }


    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
