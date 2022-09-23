package com.deloitte.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.GovInfoDto;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.mapper.GovInfoMapper;
import com.deloitte.crm.service.IGovInfoService;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.dto.GovInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

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

    @Autowired
    private EntityNameHisMapper nameHisMapper;

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

    @Override
    public Page<GovInfo> getInfoList(GovInfo govInfo, Integer pageNum, Integer pageSize) {
        Page<GovInfo> pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper<>(govInfo);
        return govInfoMapper.selectPage(pageInfo, queryWrapper);
    }

    @Override
    public Integer updateInfoList(List<GovInfo> list) {
        list.stream().forEach(o->govInfoMapper.updateById(o));
        return list.size();
    }

    @Override
    public AjaxResult getNewInfo(GovInfo govInfo) {
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper<>(govInfo);
        List<GovInfo> govInfos = govInfoMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(govInfos)&&govInfos.size()>1){
            return AjaxResult.error();
        }
        //TODO  添加主体其余详细信息

        return AjaxResult.success(govInfos.get(0));
    }


    @Override
    public AjaxResult getInfoList(GovInfoDto govInfodto) {
        govInfodto.setGovInfo();
        GovInfo govInfo = govInfodto.getGovInfo();
        Integer pageNum = govInfodto.getPageNum();
        Integer pageSize = govInfodto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)){
            return AjaxResult.error("请输入页码");
        }
        if (ObjectUtils.isEmpty(pageSize)){
            pageSize=GovInfoUtil.DEFAULT_PAGE_SIZE;
        }
        Page<GovInfo> pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper<>(govInfo);

        Page<GovInfo> govInfoPage = govInfoMapper.selectPage(pageInfo, queryWrapper);

        //创建结果集
        Page<Map<String, Object>> pageResult=new Page<>();
        pageResult.setTotal(govInfoPage.getTotal()).setPages(govInfoPage.getPages()).setCurrent(govInfoPage.getCurrent());
        //封装结果集
        List<Map<String,Object>> records = new ArrayList<>();
        govInfoPage.getRecords().stream().forEach(o-> {
            records.add(getResultMap(o));
        });
        pageResult.setRecords(records);
        return AjaxResult.success(pageResult);
    }

    @Override
    public AjaxResult updateOldName(GovInfo gov) {
        GovInfo govInfo = govInfoMapper.selectById(gov.getId());
        //修改曾用名记录
        String govNameHis = govInfo.getGovNameHis();
        if (ObjectUtils.isEmpty(govNameHis)){
            govInfo.setGovNameHis(gov.getGovNameHis());
        }else {
            govInfo.setGovNameHis(govNameHis+GovInfoUtil.NAME_USED_SIGN+gov.getGovNameHis());
        }
        String nameHisRemarks = gov.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)){
            govInfo.setEntityNameHisRemarks(gov.getUpdated()+gov.getEntityNameHisRemarks());
        }else {
            govInfo.setEntityNameHisRemarks(govNameHis+GovInfoUtil.NAME_USED_REMARK_SIGN+gov.getUpdated()+gov.getEntityNameHisRemarks());
        }
        govInfoMapper.updateById(govInfo);
        //插入曾用名记录表
        EntityNameHis entityNameHis = new EntityNameHis();
        entityNameHis.setDqCode(govInfo.getDqGovCode());
        entityNameHis.setOldName(gov.getGovNameHis());
        entityNameHis.setEntityType(2);
        entityNameHis.setHappenDate(gov.getUpdated());
        entityNameHis.setRemarks(gov.getEntityNameHisRemarks());
        entityNameHis.setSource(2);
        nameHisMapper.insert(entityNameHis);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult checkList(GovInfo govInfo) {
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper(govInfo);
        return AjaxResult.success(govInfoMapper.selectList(queryWrapper));
    }

    /**
     * GovInfo 对象转 map,并查询 曾用名条数
     *
     * @param govInfo
     * @return Map<String,Object>
     * @author 冉浩岑
     * @date 2022/9/22 23:45
    */
    public Map<String, Object> getResultMap(GovInfo govInfo) {
        Map<String, Object> resultMap = new HashMap();
        if (null != govInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(govInfo), new TypeReference<Map<String, String>>() {
            });
            try {
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                Long count = nameHisMapper.selectCount(wrapper.lambda().eq(EntityNameHis::getDqCode, govInfo.getDqGovCode()));
                resultMap.put(GovInfoUtil.NAME_USED_NUM, count);
            }catch (Exception e){
                return resultMap;
            }
        }
        return resultMap;
    }
}
class GovInfoUtil{
    /***
     * 默认页面条数
    */
    public static final Integer DEFAULT_PAGE_SIZE=9;
    /***
     * 曾用名
     */
    public static final String NAME_USED_NUM="nameUsedNum";
    /***
     * 曾用名分隔符
     */
    public static final String NAME_USED_SIGN=",";
    /***
     * 曾用名备注分隔符
     */
    public static final String NAME_USED_REMARK_SIGN=";";
}