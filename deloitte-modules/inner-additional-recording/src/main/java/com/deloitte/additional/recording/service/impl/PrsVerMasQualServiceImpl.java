package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.mapper.PrsModelMasterMapper;
import com.deloitte.additional.recording.mapper.PrsVerMasQualMapper;
import com.deloitte.additional.recording.domain.PrsVerMasQual;
import com.deloitte.additional.recording.service.PrsVerMasQualService;
import com.deloitte.additional.recording.vo.MasterEvdVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdBackVo;
import com.deloitte.common.core.domain.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * (PrsVerMasQual)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Service("prsVerMasQualService")
public class PrsVerMasQualServiceImpl extends ServiceImpl<PrsVerMasQualMapper, PrsVerMasQual> implements PrsVerMasQualService {

    @Resource
    private PrsVerMasQualMapper masQualMapper;
    @Resource
    private PrsModelMasterMapper modelMasterMapper;
    /**
     * 增加配置
     *
     * @param masterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 17:27
     */
    @Override
    public R addMasterEvd(MasterEvdVo masterEvdVo) {
        List<String> qualCodes = masterEvdVo.getQualCodes();
        String masterCode = masterEvdVo.getMasterCode();
        if (CollectionUtils.isEmpty(qualCodes)){
            return R.fail(Common.INSERT_FAIL);
        }
        PrsModelMaster prsModelMaster = modelMasterMapper.selectOne(new QueryWrapper<PrsModelMaster>().lambda().eq(PrsModelMaster::getModelCode, masterCode).last(Common.SQL_LIMIT_ONE));
        AtomicReference<String> msg= new AtomicReference<>("");
        qualCodes.forEach(o->{
            if (ObjectUtils.isNotEmpty(msg.get())){
                return;
            }
            Long count = masQualMapper.selectCount(new QueryWrapper<PrsVerMasQual>().lambda().eq(PrsVerMasQual::getQualCode, o).eq(PrsVerMasQual::getVerMasId, prsModelMaster.getId()));
            if (count>0){
                msg.set(Common.REPEAT_MAS_EVD);
                return;
            }
            PrsVerMasQual prsVerMasQual = new PrsVerMasQual();
            prsVerMasQual.setQualCode(o).setVerMasId(prsModelMaster.getId());
            masQualMapper.insert(prsVerMasQual);
        });
        if (ObjectUtils.isNotEmpty(msg.get())){
            return R.fail(Common.INSERT_FAIL+":"+msg);
        }
        return R.ok(Common.INSERT_SUCCESS);
    }
    /**
     * 批量删除
     *
     * @param list
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 17:27
     */
    @Override
    public R deleteMasterEvd(List<VersionMasterEvdBackVo> list) {
        if (CollectionUtils.isEmpty(list)){
            return R.fail(Common.PLEASE_SEND_PARAM);
        }
        list.forEach(o->{

        });
        return null;
    }
}
