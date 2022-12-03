package com.deloitte.data.platform.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "inner-crm")
public interface MasterEntityService {

    @PostMapping(value = "/Products/openCrm")
    String openCrm(@RequestBody Object obj);

    @PostMapping(value = "/Products/openCrmByCode")
    String openCrmByCode(@RequestBody List<String> entityCodeList);
}
