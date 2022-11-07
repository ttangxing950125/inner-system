package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.system.api.domain.SysDictData;

import java.util.List;
import java.util.Map;

/**
 * 字典表接口
 *
 * @author 冉浩岑
 * @date 2022/11/7 17:17
*/
public interface SysDictDataService extends IService<SysDictData> {

    List<Map<String, Object>> finAllsysDictData();
}
