package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.DataCenterValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (BasEvdData)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:38
 */
@Mapper
public interface DataCenterValueMapper extends BaseMapper<DataCenterValue> {

    List<DataCenterValue> selectValues(@Param("param") String param);
}
