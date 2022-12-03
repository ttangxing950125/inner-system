package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.MetaEvdData;
import com.deloitte.additional.recording.domain.ModelTaskDetails;
import com.deloitte.additional.recording.dto.GetMetaEvdDatasDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/11:03
 * @Description:
 */
@Mapper
public interface MetaEvdDataMapper extends BaseMapper<MetaEvdData> {

    IPage<MetaEvdData> getMetaEvdDatasByPage(@Param("page") Page<MetaEvdData> page, GetMetaEvdDatasDto getMetaEvdDatasDto);
}
