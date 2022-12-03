package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.ModelTask;
import com.deloitte.additional.recording.domain.PrsVerMasEntity;
import com.deloitte.common.core.domain.R;

import java.util.List;

/**
 * (PrsVerMasEntity)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
public interface PrsVerMasEntityService extends IService<PrsVerMasEntity> {

    R updateIncrStatus(Integer id, Integer stauts);

    /**
     * 拉取crm全量主体
     * @author wpp
     * @return
     */
    ModelTask pullCrmEntity(Integer versionId);

    /**
     * 保存主体关联关系
     * @param verMasEntities
     * @author wpp
     * @return
     */
    boolean saveVerMasEntities(List<PrsVerMasEntity> verMasEntities, ModelTask modelTask);

    /**
     * 保存主体关联关系
     * @param verMasEntities
     * @author wpp
     * @return
     */
    boolean saveVerMasEntity(PrsVerMasEntity verMasEntities, ModelTask modelTask);

    /**
     * 保存主体关联关系
     * @param verMasEntities
     * @author wpp
     * @return
     */
    boolean saveVerMasEntity(PrsVerMasEntity verMasEntities, Integer year);
}
