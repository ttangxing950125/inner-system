package com.deloitte.additional.recording.service.biz;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.additional.recording.dto.GetTaskTargetvalPageListDto;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private PrsModelQualService prsModelQualService;

    /**
     * 分页查
     *
     * @param dto
     * @return
     */
    public Object queryByPage(GetTaskTargetvalPageListDto dto) {
        Integer pageNum = dto.getPageNum();
        Integer pagesize = dto.getPagesize();
        if (pageNum == null || pageNum <= 0) {
            dto.setPageNum(1);
        }
        if (pagesize == null || pagesize <= 0) {
            dto.setPagesize(10);
        }
        return null;

    }

    public Object queryByPageStatsdetail(String modelCode, String timeValue, String name) {
        List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail(modelCode, timeValue, name);
        return dataListPageTataiVos;
    }
}
