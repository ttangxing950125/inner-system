package com.deloitte.additional.recording.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.FFile;
import com.deloitte.additional.recording.dto.FFileDto;
import com.deloitte.additional.recording.dto.FFolderDto;
import com.deloitte.additional.recording.mapper.FFileMapper;
import com.deloitte.additional.recording.service.FFileService;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("ffileServiceImpl")
public class FFileServiceImpl extends ServiceImpl<FFileMapper, FFile> implements FFileService {

    @Autowired
    private FFileMapper fFileMapper;

    @Override
    public List<FFile> getFFileList() {
        QueryWrapper<FFile> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(FFile::getId,1);
        List<FFile> fFiles = fFileMapper.selectList(wrapper);
        return fFiles;
    }

    @DS("ibond")
    @Override
    public R queryReportList(String entityCode, Integer year) {

        //查询文件
        List<FFileDto> fFileList = fFileMapper.getFFileList(entityCode, year);
        if(CollectionUtils.isEmpty(fFileList)){
            throw new ServiceException("暂无数据");
        }
       // List<FFolderDto> fFolderDtos = fFileMapper.getFFolderList();
//        fFileList.forEach(f -> {
//            //f.setFolderDto(getFFolderDto(f.getFolderDto(),fFolderDtos));
//        });
        Map<String, List<FFileDto>> collect = fFileList.stream().collect(Collectors.groupingBy(f -> f.getFolderDto().getName()));
        return R.ok(collect);
    }

    public FFolderDto getFFolderDto(FFolderDto fFolderDto,List<FFolderDto> fFolderDtos){
        for (FFolderDto folderDto: fFolderDtos){
            if(fFolderDto.getPcode().equals(folderDto.getCode())){
                folderDto.setFFolderDto(getFFolderDto(fFolderDto,fFolderDtos));
            }
        }
        return fFolderDto;
    }
}
