package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.dto.MainBodyPageDto;
import com.deloitte.additional.recording.dto.PrincipalManifestVo;
import com.deloitte.additional.recording.vo.EntityNameCodeVo;

import java.util.List;

/**
 * (EntityInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface EntityInfoService extends IService<EntityInfo> {
    IPage<PrincipalManifestVo> queryPrincipalManifestPage(MainBodyPageDto mainBodyDto);
    //查询主体名称编码
    List<EntityNameCodeVo> getAllEntityNameCode();

    /**
     * 根据实体code查询
     * @param entity_code code
     * @return EntityInfo
     */
    EntityInfo getByCode(String entity_code);

    /**
     * 根据社会信用统一代码查询
     * @param creditCode 社会信用统一代码
     * @return
     */
    EntityInfo getByCreditCode(String creditCode);
}
