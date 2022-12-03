package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.data.platform.domian.EntityInfo;
import com.deloitte.data.platform.dto.EntityInfoDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.vo.EntityInfoVo;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fangliu
 * @date 2022/11/14 11:40:39
 */
public interface IEntityInfoService extends IService<EntityInfo> {
    /**
     * 根据codes查询财务数据配置
     * @param name
     * @return
     */
    List<EntityInfo> getEntityInfoByName(String name);
    /**
     * 根据codes查询财务数据配置
     * @param codes
     * @return
     */
    Map<String, EntityInfo> getEntityInfo(Set<String> codes);

    /**
     * 分页查询数据质检信息
     * @param dto
     * @return
     */
    IPage<EntityInfoVo> getEntityInfoPage(EntityInfoDto dto);

    /**
     * 业务场景-客户列表
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.CustomerListVo> customerPage(StatisticalDataAnalysisDto.CustomerListDto dto);

    /**
     * 根据关键字搜索主体信息
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto);
}
