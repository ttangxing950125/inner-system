package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.MetaQualData;
import com.deloitte.additional.recording.dto.GetMetaQualDatasDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/11:09
 * @Description:
 */
@Mapper
public interface MetaQualDataMapper extends BaseMapper<MetaQualData> {

    IPage<MetaQualData> getMetaQualDatasByPage(@Param("page") Page<MetaQualData> page, GetMetaQualDatasDto param);
}
