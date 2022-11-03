package com.deloitte.crm.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/02 15:24
 */
@Data
@Configuration
public class EmailVo {
    @Value("#{'${user.pass:}'.empty ? null : '${user.pass:}'.split(',')}")
    private  List<String> passwords ;


}
