package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.DataCenterValue;
import com.deloitte.additional.recording.domain.SynTable;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import com.deloitte.additional.recording.dto.TranspreQualinfo3rdDto;
import com.deloitte.additional.recording.mapper.DataCenterValueMapper;
import com.deloitte.additional.recording.mapper.SynTableMapper;
import com.deloitte.additional.recording.mapper.TranspreQualinfo3rdMapper;
import com.deloitte.additional.recording.request.TranspreQualinfo3rdRequest;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.additional.recording.service.TranspreQualinfo3rdService;
import com.deloitte.additional.recording.vo.qualinfo3rd.TranspreQualinfo3rdPageVO;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * (TranspreQualinfo3rd)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@Service("transpreQualinfo3rdService")
public class TranspreQualinfo3rdServiceImpl extends ServiceImpl<TranspreQualinfo3rdMapper, TranspreQualinfo3rd> implements TranspreQualinfo3rdService {
    @Resource
    private SynTableMapper synTableMapper;
    @Resource
    private DataCenterValueMapper dataCenterValueMapper;

    @Autowired
    private TranspreQualinfo3rdMapper transpreQualinfo3rdMapper;
    /**
     * 版本
     */
    @Autowired
    private PrsProjectVersionsService versionsService;

    /**
     * 敞口
     */
    @Autowired
    private PrsModelMasterService masterService;

    @Autowired
    private PrsModelQualService qualService;

  /*  @Autowired
    @Qualifier("taskExecutor")
    private Executor executor;
*/
    @Override
    public Page<TranspreQualinfo3rdPageVO> page(String useYear, String tarType, Integer masterId, Integer versionId, String searchData, Integer page, Integer pageSize) {

        //根据条件查询
        List<TranspreQualinfo3rdDto> dtoList = transpreQualinfo3rdMapper.page(useYear, tarType, masterId, versionId, searchData, page - 1, pageSize);
        long count = transpreQualinfo3rdMapper.pageCount(useYear, tarType, masterId, versionId, searchData, page, pageSize);
        if (count > 0) {
            Page<TranspreQualinfo3rdPageVO> pageVo = new Page<>(page, pageSize);
            return getVoPage(dtoList, count, pageVo);
        }

        return null;
    }

    @Override
    public TranspreQualinfo3rd selectByVersionAndMaster(Integer versionId, Integer masterId, String useYear) {
        return lambdaQuery().eq(TranspreQualinfo3rd::getPlusVersion, versionId)
                .eq(TranspreQualinfo3rd::getPlusModelid, masterId)
                .eq(TranspreQualinfo3rd::getDataYear, useYear)
                .one();
    }

    @Override
    public void add(TranspreQualinfo3rdRequest request, String code) {
        //根据 前缀 - 类型 - 中心库id- 年份查询
        check(request, code);
        TranspreQualinfo3rd qualinfo3rd = BeanUtils.copyEntity(request, TranspreQualinfo3rd.class);
        qualinfo3rd.setTarMid(code);
        save(qualinfo3rd);
    }

    private void check(TranspreQualinfo3rdRequest request, String code) {
        TranspreQualinfo3rd one = lambdaQuery().eq(TranspreQualinfo3rd::getPrefix, request.getPrefix())
                .eq(TranspreQualinfo3rd::getTarType, request.tarType)
                .eq(TranspreQualinfo3rd::getTarMid, code)
                .eq(TranspreQualinfo3rd::getDataYear, request.getDataYear()).one();
        //
        if (one != null) {
            throw new ServiceException("已存在相同映射指标，请勿重新添加。");
        }
    }

    @Override
    public void update(TranspreQualinfo3rdRequest request, String code) {
        TranspreQualinfo3rd byId = getById(request.getId());
        if (byId == null) {
            throw new ServiceException("更新失败，请稍后再试");
        }
        check(request, code);
        TranspreQualinfo3rd qualinfo3rd = BeanUtils.copyEntity(request, TranspreQualinfo3rd.class);
        qualinfo3rd.setTarMid(code);
        updateById(qualinfo3rd);
    }


    @Override
    public List<TranspreQualinfo3rdPageVO> exportExcelData(String useYear, String searchData, Integer versionId, Integer masterId, String tarType) {

        List<TranspreQualinfo3rdDto> search = transpreQualinfo3rdMapper.search(useYear, tarType, masterId, versionId, searchData);
        List<TranspreQualinfo3rdPageVO> list = new ArrayList<>();
        if (search != null) {
            //多线程处理
            list = BeanUtils.copy(search, TranspreQualinfo3rdPageVO.class);

        }
        return list;
    }

    @Override
    public R masterMapping(List<String> names) {
        List<SynTable> synTables = synTableMapper.selectList(new QueryWrapper<SynTable>().lambda().in(SynTable::getName, names));
        List<DataCenterValue>result=new ArrayList<>();
        synTables.forEach(o->{
            String tableName=o.getPrefix()+ Common.MODEL_MASTER;
            List<DataCenterValue>centerValues=dataCenterValueMapper.selectValues(tableName);
            result.addAll(centerValues);
        });
        return null;
    }

    @Override
    public R centerMaster(Integer versionId) {
        SynTable synTables = synTableMapper.selectById(versionId);
        return null;
    }

    private Page<TranspreQualinfo3rdPageVO> getVoPage(List<TranspreQualinfo3rdDto> dtoList, long count, Page<TranspreQualinfo3rdPageVO> pageVo) {
        List<TranspreQualinfo3rdPageVO> pageVOS = BeanUtils.copy(dtoList, TranspreQualinfo3rdPageVO.class);
        pageVo.setRecords(pageVOS);
        pageVo.setTotal(count);
        return pageVo;
    }
}
