package com.deloitte.crm.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/19 19:02
 */
@Data
public class ImportDto {

    /**
     * uuid
     */
    private  String uuid;

    /**
     * 产品id
     */
    private List<Integer> proIds;


}
