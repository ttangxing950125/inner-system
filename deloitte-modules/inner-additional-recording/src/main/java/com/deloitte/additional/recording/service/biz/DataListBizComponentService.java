package com.deloitte.additional.recording.service.biz;

import com.deloitte.additional.recording.dto.GetTaskTargetvalPageListDto;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.additional.recording.service.SysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/07/18:22
 * @Description:
 */
@Component
@Slf4j
public class DataListBizComponentService {

    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;
    @Resource
    private PrsModelMasterService prsModelMasterService;
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 分页查
     *
     * @param dto
     * @return
     */
    public Object queryByPage(GetTaskTargetvalPageListDto dto) {
        Integer pageNum = dto.getPageNum();
        Integer pagesize = dto.getPagesize();
        return null;

    }
}
