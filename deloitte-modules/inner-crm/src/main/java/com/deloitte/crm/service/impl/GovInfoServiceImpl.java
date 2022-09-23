package com.deloitte.crm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.dto.GovInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.GovInfoMapper;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.service.IGovInfoService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class GovInfoServiceImpl extends ServiceImpl<GovInfoMapper, GovInfo> implements IGovInfoService
{
    @Autowired
    private GovInfoMapper govInfoMapper;


    /**
     * 查询政府主体信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:22
    */
    @Override
    public GovInfoDto getGovInfo(){
        List<GovInfo> list = this.list();
        GovInfoDto govInfoDto = new GovInfoDto();
        //TODO gov_level_big 是否 省  1-是
        List<GovInfo>  province = list().stream()
                .filter(row -> row.getGovLevelBig() == 1)
                .collect(Collectors.toList());

        //TODO gov_level_big 是否 市  2-是
        List<GovInfo> city = list().stream()
                .filter(row -> row.getGovLevelBig() == 2)
                .collect(Collectors.toList());

        //TODO gov_level_big 是否 县  3-是
        List<GovInfo> county = list().stream()
                .filter(row -> row.getGovLevelBig() == 3)
                .collect(Collectors.toList());

        //TODO gov_level_big 是否 经开  4-是
        List<GovInfo> open = list().stream()
                .filter(row -> row.getGovLevelBig() == 4)

                .collect(Collectors.toList());

        govInfoDto.setProvince(province.size());
        govInfoDto.setCity(city.size());
        govInfoDto.setCounty(county.size());
        govInfoDto.setOpen(open.size());
        govInfoDto.setGovSum(list.size());
        return govInfoDto;
    }

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public GovInfo selectGovInfoById(Long id)
    {
        return govInfoMapper.selectGovInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param govInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<GovInfo> selectGovInfoList(GovInfo govInfo)
    {
        return govInfoMapper.selectGovInfoList(govInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertGovInfo(GovInfo govInfo)
    {
        return govInfoMapper.insertGovInfo(govInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateGovInfo(GovInfo govInfo)
    {
        return govInfoMapper.updateGovInfo(govInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovInfoByIds(Long[] ids)
    {
        return govInfoMapper.deleteGovInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovInfoById(Long id)
    {
        return govInfoMapper.deleteGovInfoById(id);
    }
}
