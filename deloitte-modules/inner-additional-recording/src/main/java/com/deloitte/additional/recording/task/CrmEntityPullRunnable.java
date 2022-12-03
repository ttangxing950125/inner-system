package com.deloitte.additional.recording.task;

import com.deloitte.additional.recording.domain.ModelTask;
import com.deloitte.additional.recording.domain.PrsVerMasEntity;
import com.deloitte.additional.recording.service.PrsVerMasEntityService;
import com.deloitte.additional.recording.util.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/28
 */
@Slf4j
public class CrmEntityPullRunnable implements Runnable {

    private List<PrsVerMasEntity> verMasEntities;


    private ModelTask modelTask;

    @Override
    public void run() {
        PrsVerMasEntityService entityService = ApplicationContextHolder.getBean(PrsVerMasEntityService.class);
        if (entityService==null){
            log.info("请在spring环境启动");
            return;
        }
        entityService.saveVerMasEntities(verMasEntities, modelTask);
    }

    public CrmEntityPullRunnable(List<PrsVerMasEntity> verMasEntities, ModelTask modelTask) {
        this.verMasEntities = verMasEntities;
        this.modelTask = modelTask;
    }
}
