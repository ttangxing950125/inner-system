package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsProjectVersions;

import java.util.List;

/**
 * (PrsProjectVersions)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsProjectVersionsService extends IService<PrsProjectVersions> {

    List<String> findAllPrsProjectVersions();
}
