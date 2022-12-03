package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.BasEvdDataTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/12/2
 * @描述
 */
public interface BasEvdDataTaskMapper extends BaseMapper<BasEvdDataTask> {

    /**
     * 查询val_ualue类型为数字集合
     * @param evdCode
     * @param timeValue
     * @return List<BasEvdDataTask>
     */
    List<BasEvdDataTask> findByEvdCodeAndTimeAndType(@Param("evdCode")String evdCode,@Param("timeValue")String timeValue);
}
