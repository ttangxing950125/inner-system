package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.dto.MainBodyPageDto;
import com.deloitte.additional.recording.dto.PrincipalManifestVo;
import com.deloitte.additional.recording.mapper.EntityInfoMapper;
import com.deloitte.additional.recording.mapper.PrsModelMasterMapper;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.service.EntityInfoService;
import com.deloitte.additional.recording.vo.EntityNameCodeVo;
import com.deloitte.common.core.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (EntityInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("entityInfoService")
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper, EntityInfo> implements EntityInfoService {
    @Autowired
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;
    @Resource
    private PrsModelMasterMapper prsModelMasterMapper;
    @Override
    public EntityInfo getByCode(String entity_code) {
        return lambdaQuery().eq(EntityInfo::getEntityCode, entity_code).one();
    }

    @Override
    public EntityInfo getByCreditCode(String creditCode) {
        return lambdaQuery().eq(EntityInfo::getCreditCode, creditCode).one();
    }
    public IPage<PrincipalManifestVo> queryPrincipalManifestPage(MainBodyPageDto mainBodyDto) {


        Page<PrincipalManifestVo> page = new Page<>(mainBodyDto.getPageNum(),mainBodyDto.getPagesize());
        List<PrincipalManifestVo> principalManifestVos = entityInfoMapper.queryPrincipalManifestPage(page,mainBodyDto);

        page.setRecords(principalManifestVos);
        return page;
    }

    /**
     * 查询主体名称编码
     * @return
     */
    @Override
    public List<EntityNameCodeVo> getAllEntityNameCode() {
        LambdaQueryWrapper<EntityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EntityInfo::getId,EntityInfo::getEntityCode,EntityInfo::getEntityName);
        wrapper.eq(EntityInfo::getStatus,1);
        List<EntityInfo> list = this.list(wrapper);
        List<EntityNameCodeVo> entityNameCodeVoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(e -> {
                EntityNameCodeVo entityNameCodeVo = new EntityNameCodeVo();
                BeanUtils.copyProperties(e,entityNameCodeVo);
                entityNameCodeVo.setEntityId(e.getId());
                entityNameCodeVoList.add(entityNameCodeVo);
            });
        }
        return entityNameCodeVoList;
    }
}
