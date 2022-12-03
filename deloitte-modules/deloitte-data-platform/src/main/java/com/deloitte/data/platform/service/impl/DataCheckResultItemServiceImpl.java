package com.deloitte.data.platform.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.DataCheckResult;
import com.deloitte.data.platform.domian.DataCheckResultItem;
import com.deloitte.data.platform.domian.ModelDataCheck;
import com.deloitte.data.platform.dto.HookArticulationDetailDto;
import com.deloitte.data.platform.mapper.DataCheckResultItemMapper;
import com.deloitte.data.platform.service.IDataCheckResultItemService;
import com.deloitte.data.platform.service.IDataCheckResultService;
import com.deloitte.data.platform.service.IModelDataCheckService;
import com.deloitte.data.platform.vo.HookArticulationDetailVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据校验结果表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
@Service
@Master_1
public class DataCheckResultItemServiceImpl extends ServiceImpl<DataCheckResultItemMapper, DataCheckResultItem> implements IDataCheckResultItemService {
    @Resource
    private IDataCheckResultService iDataCheckResultService;
    @Resource
    private IModelDataCheckService iModelDataCheckService;
    @Override
    public HookArticulationDetailVo getHookArticulationDetailPage(HookArticulationDetailDto dto) {
        HookArticulationDetailVo hookArticulationDetailVo = new HookArticulationDetailVo();
        List<String> headers = new ArrayList<>();
        headers.add("校验规则");
        headers.add("主体名称");
        headers.add("主体代码");
        headers.add("通过情况");
        headers.add("数据时间");
        IPage<DataCheckResultItem> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<DataCheckResultItem> result = this.baseMapper.getDataCheckResultItemPage(page, dto);
        List<DataCheckResultItem> records = result.getRecords();
        List<List<String>> data = new ArrayList<>();
        Integer checkResultId = dto.getCheckResultId();
        ModelDataCheck modelDataCheck = new ModelDataCheck();
        if (checkResultId!=null){
            DataCheckResult dataCheckResult = iDataCheckResultService.getById(checkResultId);
            if (dataCheckResult!=null){
                 modelDataCheck = iModelDataCheckService.getById(dataCheckResult.getCheckId());
            }
        }
        for (DataCheckResultItem record : records) {
            List<String> detail = new ArrayList<>();
            detail.add(modelDataCheck.getCheckDescribe());
            detail.add(record.getEntityName());
            detail.add(record.getEntityCode());
            detail.add(record.getIsAdopt());
            detail.add(LocalDateTimeUtil.format(record.getReportDate(), DatePattern.NORM_DATE_PATTERN) );
            String checkKeyValue = record.getCheckKeyValue();
            if (StringUtils.isNotEmpty(checkKeyValue)){
                JSONObject jsonObject = JSONUtil.parseObj(checkKeyValue);
                Set<String> keys = jsonObject.keySet();
                for (String key : keys) {
                    headers.add(key);
                    detail.add(jsonObject.getStr(key));
                }
            }
            data.add(detail);
        }
        hookArticulationDetailVo.setHeaders(headers);
        hookArticulationDetailVo.setRecords(data);
        hookArticulationDetailVo.setPages(result.getPages());
        hookArticulationDetailVo.setCurrent(result.getCurrent());
        hookArticulationDetailVo.setSize(result.getSize());
        hookArticulationDetailVo.setTotal(result.getTotal());
        return hookArticulationDetailVo;
    }
}
