package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.data.platform.domian.DataPlatformMyLibraryTable;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;

/**
 * 数据平台我的库表 Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/23 17:40:39
 */
public interface DataPlatformMyLibraryTableMapper extends BaseMapper<DataPlatformMyLibraryTable> {
    /**
     * 根据用户id 查询我的库表
     * @param userId
     * @return
     */
    DataPlatformMenuVo getMyLibraryTable(long userId);
}
