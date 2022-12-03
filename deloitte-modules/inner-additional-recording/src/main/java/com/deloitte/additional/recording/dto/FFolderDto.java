package com.deloitte.additional.recording.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FFolderDto implements Serializable {

    private Integer id;

    /**
     *文件夹编码
     */
    private String code;

    /**
     * 文件夹名称
     */
    private String name;

    /**
     * 上层文件夹编码
     */
    private String pcode;

    /**
     * 在服务器上显示编码
     */
    private String displaycode;

    /**
     * 所属上级文件夹
     */
    private FFolderDto fFolderDto;

}
