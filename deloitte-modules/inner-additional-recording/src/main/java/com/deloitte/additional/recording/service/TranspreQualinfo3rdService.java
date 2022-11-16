package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import com.deloitte.additional.recording.vo.qualinfo3rd.TranspreQualinfo3rdPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (TranspreQualinfo3rd)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@Service
public interface TranspreQualinfo3rdService extends IService<TranspreQualinfo3rd> {

    /**
     * 分页查询映射指标列表
     * @param useYear 年份
     * @param tarType 指标类型
     * @param masterId 敞口id
     * @param versionId 版本id
     * @param searchData 关键字搜索参数
     * @param page 当前页码
     * @param pageSize 页面数量大小
     * @return
     */

    Page<TranspreQualinfo3rdPageVO> page(String useYear, String tarType, Integer masterId, Integer versionId, String searchData, Integer page, Integer pageSize);
}
