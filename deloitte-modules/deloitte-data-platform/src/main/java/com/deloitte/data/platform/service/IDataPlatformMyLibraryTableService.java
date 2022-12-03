package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.DataPlatformMyLibraryTable;
import com.deloitte.data.platform.dto.MyLibraryTableDto;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;

/**
 * 数据平台我的库表 服务类
 *
 * @author fangliu
 * @date 2022/11/23 17:40:39
 */
public interface IDataPlatformMyLibraryTableService extends IService<DataPlatformMyLibraryTable> {
    /**
     * 新增我的库表
     * @param dto
     */
    void addMyLibraryTable(MyLibraryTableDto dto);

    /**
     *  删除我的库表
     * @param id
     */
    void deleteMyLibraryTable(Integer id);

    /**
     * 查询我的库表
     * @return
     */
    DataPlatformMenuVo getMyLibraryTable();
}
