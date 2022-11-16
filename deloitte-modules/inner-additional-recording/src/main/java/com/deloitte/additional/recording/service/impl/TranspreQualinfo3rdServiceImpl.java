package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import com.deloitte.additional.recording.dto.TranspreQualinfo3rdDto;
import com.deloitte.additional.recording.mapper.TranspreQualinfo3rdMapper;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.additional.recording.service.TranspreQualinfo3rdService;
import com.deloitte.additional.recording.vo.qualinfo3rd.TranspreQualinfo3rdPageVO;
import com.deloitte.common.core.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TranspreQualinfo3rd)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@Service("transpreQualinfo3rdService")
public class TranspreQualinfo3rdServiceImpl extends ServiceImpl<TranspreQualinfo3rdMapper, TranspreQualinfo3rd> implements TranspreQualinfo3rdService {


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

    @Override
    public Page<TranspreQualinfo3rdPageVO> page(String useYear, String tarType, Integer masterId, Integer versionId, String searchData, Integer page, Integer pageSize) {

        //根据条件查询
        List<TranspreQualinfo3rdDto> dtoList = transpreQualinfo3rdMapper.page(useYear, tarType, masterId, versionId, searchData, page-1, pageSize);
        long count = transpreQualinfo3rdMapper.pageCount(useYear, tarType, masterId, versionId, searchData, page, pageSize);
        if (count > 0) {
            Page<TranspreQualinfo3rdPageVO> pageVo = new Page<>(page, pageSize);
            return getVoPage(dtoList, count, pageVo);
        }

        return null;
    }

    private Page<TranspreQualinfo3rdPageVO> getVoPage(List<TranspreQualinfo3rdDto> dtoList, long count, Page<TranspreQualinfo3rdPageVO> pageVo) {
        List<TranspreQualinfo3rdPageVO> pageVOS = BeanUtils.copy(dtoList, TranspreQualinfo3rdPageVO.class);
        pageVOS.forEach(vo -> {
            //敞口
            PrsModelMaster modelMaster = masterService.getById(vo.getMasterId());
            vo.setMasterName(modelMaster == null ? null : modelMaster.getName());
            //查询版本
            PrsProjectVersions projectVersions = versionsService.getById(vo.getVersionId());
            vo.setVersionName(projectVersions == null ? null : projectVersions.getName());
            //查询指标
            PrsModelQual modelQual = qualService.getById(vo.getPlusQualid());
            vo.setQualName(modelQual == null ? null : modelQual.getQualName());
        });

        pageVo.setRecords(pageVOS);
        pageVo.setTotal(count);
        return pageVo;
    }
}
