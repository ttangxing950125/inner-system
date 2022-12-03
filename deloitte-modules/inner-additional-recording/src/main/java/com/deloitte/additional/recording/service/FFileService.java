package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.FFile;
import com.deloitte.common.core.domain.R;

import java.util.List;

public interface FFileService extends IService<FFile> {

    List<FFile> getFFileList();

    R queryReportList(String entityCode, Integer year);
}
