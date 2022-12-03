package com.deloitte.additional.recording.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.FFile;
import com.deloitte.additional.recording.dto.FFileDto;
import com.deloitte.additional.recording.dto.FFolderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FFileMapper extends BaseMapper<FFile> {


    List<FFileDto> getFFileList(@Param("entityCode") String entityCode, @Param("year") Integer year);

    List<FFolderDto> getFFolderList();
}
