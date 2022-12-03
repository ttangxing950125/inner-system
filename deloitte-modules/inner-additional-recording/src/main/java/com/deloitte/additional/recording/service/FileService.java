package com.deloitte.additional.recording.service;

import com.deloitte.common.core.exception.file.InvalidExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/28/17:05
 * @Description:
 */
public interface FileService {

    String localFileUpload(MultipartFile file, Integer taskType,Integer taskId, String entityCode) throws IOException, InvalidExtensionException;
}
