package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysDictDataMapper;
import com.deloitte.system.api.domain.SysDictData;
import org.springframework.stereotype.Service;

/**
 * 字典表服务层
 *
 * @author 冉浩岑
 * @date 2022/11/7 17:09
*/
@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

}
