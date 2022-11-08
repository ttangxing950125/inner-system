package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.ImportTaskErrorLogMapper;
import com.deloitte.crm.domain.ImportTaskErrorLog;
import com.deloitte.crm.service.ImportTaskErrorLogService;
import org.springframework.stereotype.Service;

/**
 * (ImportTaskErrorLog)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 18:33:44
 */
@Service("importTaskErrorLogService")
public class ImportTaskErrorLogServiceImpl extends ServiceImpl<ImportTaskErrorLogMapper, ImportTaskErrorLog> implements ImportTaskErrorLogService {

}
