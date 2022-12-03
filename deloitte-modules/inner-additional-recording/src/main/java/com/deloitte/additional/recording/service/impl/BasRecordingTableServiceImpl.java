package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.additional.recording.domain.BasRecordingLabel;
import com.deloitte.additional.recording.domain.BasRecordingTable;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import com.deloitte.additional.recording.mapper.BasRecordingLabelMapper;
import com.deloitte.additional.recording.mapper.BasRecordingTableMapper;
import com.deloitte.additional.recording.mapper.BasRecordingTaskInfoMapper;
import com.deloitte.additional.recording.service.BasRecordingTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.vo.recording.FindLableByTableCodeResultVo;
import com.deloitte.common.core.exception.ServiceException;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 中间补录层表描述 服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@Service
public class BasRecordingTableServiceImpl extends ServiceImpl<BasRecordingTableMapper, BasRecordingTable> implements BasRecordingTableService {

    @Resource
    private BasRecordingTableMapper basRecordingTableMapper;
    @Resource
    private BasRecordingLabelMapper basRecordingLabelMapper;
    @Resource
    private BasRecordingTaskInfoMapper taskInfoMapper;


    /**
     * 获取全部表名 不带分页
     *
     * @return List<BasRecordingTable>
     */
    @Override
    public List<BasRecordingTable> getAllRecordingTable() {
        final List<BasRecordingTable> basRecordingTables = basRecordingTableMapper.selectList(new LambdaQueryWrapper<BasRecordingTable>().eq(BasRecordingTable::getStatus, 1));
        return basRecordingTables;
    }

    /**
     * 通过表查询标签
     * @param tableCode
     * @return
     */
    @Override
    public FindLableByTableCodeResultVo findLableByTableCode(String tableCode) {
        final BasRecordingTable basRecordingTables = basRecordingTableMapper.selectOne(new LambdaQueryWrapper<BasRecordingTable>()
                .eq(BasRecordingTable::getCode, tableCode)
                .eq(BasRecordingTable::getStatus, 1));
        Optional.ofNullable(basRecordingTables).orElseThrow(() -> new ServiceException("表名不存在或已删除:" + tableCode));
        final List<BasRecordingLabel> basRecordingLabels = basRecordingLabelMapper.selectList(new LambdaQueryWrapper<BasRecordingLabel>()
                .eq(BasRecordingLabel::getTableCode, tableCode)
                .eq(BasRecordingLabel::getStatus, 1));
        if (basRecordingLabels.isEmpty()) {
            FindLableByTableCodeResultVo vo = new FindLableByTableCodeResultVo();
            return vo;
        }
        //统计这个表下面的--这个标签名下面的待补录任务、条数
        ArrayList<List<BasRecordingTaskInfo>> baseInfos = new ArrayList<>();
        basRecordingLabels.stream().forEach(e -> {
            final List<BasRecordingTaskInfo> basRecordingTaskInfos = taskInfoMapper.selectList(new LambdaQueryWrapper<BasRecordingTaskInfo>()
                    .eq(BasRecordingTaskInfo::getLableCode, e.getCode())
                    .eq(BasRecordingTaskInfo::getTableCode, e.getTableCode())
                    .eq(BasRecordingTaskInfo::getPeriodReportStatus, 1)
                    .eq(BasRecordingTaskInfo::getStatus, 1));
            if (CollUtil.isNotEmpty(basRecordingTaskInfos)) {
                baseInfos.add(basRecordingTaskInfos);
            }
        });

        FindLableByTableCodeResultVo vo = new FindLableByTableCodeResultVo();
        vo.setInfo(basRecordingLabels);
        vo.setTount(CollUtil.isEmpty(baseInfos)?0:baseInfos.size());
        return vo;
    }
}
