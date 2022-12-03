package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import org.springframework.web.multipart.MultipartFile;

/**
 * (BasEvdData)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:35
 */
public interface BasEvdDataService extends IService<BasEvdData> {

    /**
     * 绑定evd_data和evd_task和task_info
     * @param versionMaster
     * @param entityCode
     * @param taskYear
     * @author wpp
     * @return
     */
    boolean bindEvdData(PrsVersionMaster versionMaster, String entityCode, Integer taskYear);
    /**
     * wind导入数据
     * @param serviceFile 文件
     */
    void importWindFromExcel(MultipartFile serviceFile);


    /**
     * 根据实体code evdcode 年份查询 BasEvdData
     * @param entityCode 实体coed
     * @param evdCode evdcode
     * @param year 年份
     * @return BasEvdData
     */
    BasEvdData getByEntityAndEvdAndTime(String entityCode,String evdCode,String year);


}
