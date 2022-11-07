package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsProjectVersions;

/**
 * (PrsProjectVersions)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsProjectVersionsService extends IService<PrsProjectVersions> {
    /**
     * 查询版本配置列表
     *
     * @param year   年份
     * @param status 状态
     * @param param  搜索内容
     * @param pageNum  页码
     * @param pageSize  页面size
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 16:32
     */
    Object getPrsProjectVersions(String year, String status, String param,Integer pageNum,Integer pageSize);
}
