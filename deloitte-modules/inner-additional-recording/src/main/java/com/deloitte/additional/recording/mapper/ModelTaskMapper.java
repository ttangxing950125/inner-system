package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.ModelTask;

/**
 * 任务表，补录平台的耗时操作全部扔到任务里面来执行(ModelTask)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface ModelTaskMapper extends BaseMapper<ModelTask> {

}
