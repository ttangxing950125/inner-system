package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.BaseStructuredNotes;
import com.deloitte.data.platform.mapper.BaseStructuredNotesMapper;
import com.deloitte.data.platform.service.IBaseStructuredNotesService;
import org.springframework.stereotype.Service;

/**
 * 结构化附注表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Service
@Master_1
public class BaseStructuredNotesServiceImpl extends ServiceImpl<BaseStructuredNotesMapper, BaseStructuredNotes> implements IBaseStructuredNotesService {

}
