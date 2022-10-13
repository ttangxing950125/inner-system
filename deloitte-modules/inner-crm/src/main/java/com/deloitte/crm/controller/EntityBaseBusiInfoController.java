package com.deloitte.crm.controller;

import com.deloitte.crm.service.EntityBaseBusiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冉浩岑
 * @date 2022/10/13 15:17
 */
@RestController
@RequestMapping("/baseBusi")
public class EntityBaseBusiInfoController {
    @Autowired
    private EntityBaseBusiInfoService entityBaseBusiInfoService;
}
