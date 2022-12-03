package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.BasRecordingImg;
import com.deloitte.additional.recording.domain.SysConfig;
import com.deloitte.additional.recording.mapper.BasRecordingImgMapper;
import com.deloitte.additional.recording.mapper.FinancialTaskMapper;
import com.deloitte.additional.recording.service.FileService;
import com.deloitte.additional.recording.service.SysConfigService;
import com.deloitte.additional.recording.util.ApplicationContextHolder;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.exception.file.FileNameLengthLimitExceededException;
import com.deloitte.common.core.exception.file.FileSizeLimitExceededException;
import com.deloitte.common.core.exception.file.InvalidExtensionException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.file.FileTypeUtils;
import com.deloitte.common.core.utils.uuid.Seq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/28/17:05
 * @Description: 文件服务 LocalSysFileServiceImpl
 * @see LocalSysFileServiceImpl
 */
@Slf4j
@Service("localFileServiceImpl")
public class LocalFileServiceImpl implements FileService {
    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix:/}")
    public String localFilePrefix;

    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain:127.0.0.1}")
    public String domain;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private BasRecordingImgMapper basRecordingImgMapper;

    /**
     * 文件上传
     * @param file
     * @param taskId
     * @param taskType
     * @param entityCode
     * @return
     * @throws IOException
     * @throws InvalidExtensionException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String localFileUpload(MultipartFile file, Integer taskId, Integer taskType, String entityCode) throws IOException, InvalidExtensionException {
        final FinancialTaskMapper financialTaskMapper = ApplicationContextHolder.get().getBean(FinancialTaskMapper.class);
        Optional.ofNullable(financialTaskMapper.selectById(taskId)).orElseThrow(() -> new ServiceException("任务参数非法"));
        final SysConfig sysConfig = sysConfigService.getBaseMapper().selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getName, Common.SYS_CONF__NAME_IMGPATH));
        if (sysConfig == null || StrUtil.isEmpty(sysConfig.getValue())) {
            log.error("文件地址未配置");
            throw new ServiceException("文件地址未配置");
        }
        String baseDir = sysConfig.getValue();
        uploadFileTypeCheck(baseDir, file, Common.DEFAULT_IMAGES_EXTENSION);
        String fileName = extractFilename(file, entityCode);
        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        String pathFileName = getPathFileName(fileName);
        BasRecordingImg img = new BasRecordingImg();
        img.setPath(domain + localFilePrefix + pathFileName);
        img.setTaskId(taskId);
        img.setTaskType(taskType);
        this.basRecordingImgMapper.insert(img);
//        return getPathFileName(fileName);
        return domain + localFilePrefix + pathFileName;
    }

    /**
     * 拼装 文件名称
     * @param file
     * @param entityCode
     * @return
     */
    public static final String extractFilename(MultipartFile file, String entityCode) {
        return StrUtil.format("{}/{}_{}.{}", entityCode, DateUtil.dateTimeNow("yyyy/MM/dd"), Seq.getId(Seq.uploadSeqType), FileTypeUtils.getExtension(file));
    }

    /**
     * 文件上传地址 创建
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }

    /**
     * 获取文件名称
     * @param fileName
     * @return
     * @throws IOException
     */
    private static final String getPathFileName(String fileName) throws IOException {
        String pathFileName = "/" + fileName;
        return pathFileName;
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     * @see com.deloitte.common.core.utils.file.FileTypeUtils#getExtension(MultipartFile)
     */
    public static final void uploadFileTypeCheck(String baseDir, MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException, InvalidExtensionException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > DEFAULT_FILE_NAME_LENGTH) {
            log.error("上传文件名称太长,超过指定的长度");
            throw new ServiceException("上传文件名称太长超过指定的长度:" + DEFAULT_FILE_NAME_LENGTH);
        }
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            log.error("上传文件大小操作指定值");
            throw new ServiceException("上传文件大小操作指定值:50M");
        }
        String fileName = file.getOriginalFilename();
        //获取获取文件名的后缀
        String extension = FileTypeUtils.getExtension(file);
        if (!isAllowedExtension(extension, allowedExtension)) {
            throw new ServiceException("文件类型非法");
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     * @param extension        上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
