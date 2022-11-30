package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.BasEvdDataTab;
import org.springframework.web.multipart.MultipartFile;

/**
 * @创建人 tangx
 * @创建时间 2022/11/22
 * @描述 BasEvdDataTab 表业务层
 */
public interface BasEvdDataTabService extends IService<BasEvdDataTab> {

    /**
     * 导入子表数据
     * @param serviceFile 文件
     */
    void importSubTableFromExcel(MultipartFile serviceFile);
}
