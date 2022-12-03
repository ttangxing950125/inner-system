package com.deloitte.crm.service;

import com.deloitte.crm.dto.OpenCrmByEntityCodeDto;
import com.deloitte.crm.dto.OpenCrmDto;
import com.deloitte.crm.vo.OpenCrmVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/24 10:55
 */
public interface OpenCrmService {

    ArrayList<OpenCrmVo> getDataByScene(List<OpenCrmDto> OpenCrmDtoS);

    ArrayList<OpenCrmVo> getDataByCode(List<OpenCrmByEntityCodeDto> OpenCrmByEntityCodes);
}
