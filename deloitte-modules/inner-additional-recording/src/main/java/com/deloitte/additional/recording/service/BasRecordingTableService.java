package com.deloitte.additional.recording.service;

import com.deloitte.additional.recording.domain.BasRecordingTable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.vo.recording.FindLableByTableCodeResultVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中间补录层表描述 服务类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
public interface BasRecordingTableService extends IService<BasRecordingTable> {
    /**
     * 获取全部表名 不带分页
     * @return
     */
    List<BasRecordingTable> getAllRecordingTable();

    /**
     * 通过表查询标签
     * @param tableCode
     * @return
     */
    FindLableByTableCodeResultVo findLableByTableCode(String tableCode);
}
