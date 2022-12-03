package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.DataPlatformConfigDict;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;

import java.util.List;

/**
 * 数据平台配置字典  服务类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
public interface IDataPlatformConfigDictService extends IService<DataPlatformConfigDict> {
    /**
     * 根据父级字典编码查询数据优先级
     * @return
     */
    String getDataPriorityByParentCode();

    /**
     * 数据平台菜单 全部
     * @return
     */
    DataPlatformMenuVo getAllDataMenu();

    /**
     * 查询数据来源排序
     * @return
     */
    List<DataPlatformConfigDict> getReqList();

    /**
     * 根据关键字搜索财务数据配置
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto);

    /**
     * 获取质检规则菜单
     * @param
     * @return
     */
    DataPlatformMenuVo getBaseConfigDictSubordinate(String code);
}
