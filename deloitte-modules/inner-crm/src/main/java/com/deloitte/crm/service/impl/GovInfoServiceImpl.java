package com.deloitte.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.GovInfoByDto;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.mapper.GovInfoMapper;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Integer updateInfoList(List<GovInfo> list) {
        list.stream().forEach(o->govInfoMapper.updateById(o));
        return list.size();
    }

    @Override
    public AjaxResult getInfoDetail(GovInfo govInfo) {
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper<>(govInfo);
        List<GovInfo> govInfos = govInfoMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(govInfos)&&govInfos.size()>1){
            return AjaxResult.error();
        }
        //TODO  添加主体其余详细信息

        return AjaxResult.success(govInfos.get(0));
    }


    /**
     * 统计政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     *
     */

    @Override
    public GovInfoDto getGovInfo() {
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
    @Override
    public AjaxResult getInfoList(GovInfoByDto govInfodto) {
        govInfodto.setGovInfo();
        GovInfo govInfo = govInfodto.getGovInfo();
        Integer pageNum = govInfodto.getPageNum();
        Integer pageSize = govInfodto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)){
            return AjaxResult.error("请输入页码");
        }
        if (ObjectUtils.isEmpty(pageSize)){
            pageSize= EntityUtils.DEFAULT_PAGE_SIZE;
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
    @Autowired
    private HttpUtils httpUtils;

    @Override
    public AjaxResult addOldName(GovInfo gov) {
        //获取操作用户
        String remoter = httpUtils.getRemoter();

        GovInfo govInfo = govInfoMapper.selectById(gov.getId());
        //修改曾用名记录
        String govNameHis = govInfo.getGovNameHis();
        if (ObjectUtils.isEmpty(govNameHis)){
            govInfo.setGovNameHis(gov.getGovNameHis());
        }else {
            govInfo.setGovNameHis(govNameHis+ EntityUtils.NAME_USED_SIGN+gov.getGovNameHis());
        }
        String nameHisRemarks = gov.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)){
            govInfo.setEntityNameHisRemarks(gov.getUpdated()+remoter+gov.getEntityNameHisRemarks());
        }else {
            govInfo.setEntityNameHisRemarks(govNameHis+ EntityUtils.NAME_USED_REMARK_SIGN+gov.getUpdated()+remoter+gov.getEntityNameHisRemarks());
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
    public AjaxResult checkGov(GovInfo govInfo) {
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper(govInfo);
        return AjaxResult.success(govInfoMapper.selectList(queryWrapper));
    }

    @Override
    public AjaxResult getListEntityByPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Page<GovInfo>pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<GovInfo>queryWrapper=new QueryWrapper<>();
        Page<GovInfo> entityInfoPage = govInfoMapper.selectPage(pageInfo, queryWrapper);

        //查询分页数据集
        Page<Map<String, Object>> pageResult=new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());

        //封装新的结果集
        List<Map<String,Object>> resultRecords = new ArrayList<>();
        //添加指标栏位
        List<GovInfo> records = entityInfoPage.getRecords();
        records.stream().forEach(o->{
            Map<String, Object> resultMap = JSON.parseObject(JSON.toJSONString(o), new TypeReference<Map<String, String>>() {});
            if (!CollectionUtils.isEmpty(mapList)){
                for (Map<String,String> map:mapList){
                    QueryWrapper<EntityAttrValue>valueQuer=new QueryWrapper<>();
                    EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                            .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                            .eq(EntityAttrValue::getEntityCode,o.getDqGovCode()));
                    //新增指标栏
                    Map<String,Object> more=new HashMap<>();
                    more.put(MORE_ENTITY_KPI_KEY,map.get(MORE_ENTITY_KPI_NAME));
                    if (attrValue==null){
                        more.put(MORE_ENTITY_KPI_VALUE,null);
                    }else {
                        more.put(MORE_ENTITY_KPI_KEY, attrValue.getValue());
                    }
                    resultMap.put(MORE_ENTITY_KPI_MORE, more);
                }
            }
            resultRecords.add(resultMap);
        });
        pageResult.setRecords(resultRecords);
        return AjaxResult.success(pageResult);
    }

    @Override
    public AjaxResult updateOldName(String dqCode,String oldName, String newOldName,String status) {
        //根据dqCode查询主体表
        QueryWrapper<GovInfo>infoQuery=new QueryWrapper<>();
        GovInfo govInfo = govInfoMapper.selectOne(infoQuery.lambda().eq(GovInfo::getDqGovCode, dqCode));
        //根据dqCode查询曾用名列表
        QueryWrapper<EntityNameHis>hisQuery=new QueryWrapper<>();
        EntityNameHis nameHis = nameHisMapper.selectOne(hisQuery.lambda()
                                             .eq(EntityNameHis::getDqCode, dqCode)
                                             .eq(EntityNameHis::getOldName, oldName));
        if (ObjectUtils.isEmpty(status)){
            //修改主体表中的数据
            govInfo.setGovNameHis(govInfo.getGovNameHis().replaceAll(oldName, newOldName));
            govInfo.setEntityNameHisRemarks(govInfo.getEntityNameHisRemarks().replaceAll(oldName,newOldName));
            govInfoMapper.updateById(govInfo);
            //修改曾用名表中的数据
            nameHis.setOldName(newOldName);
            nameHisMapper.updateById(nameHis);
            return AjaxResult.success();
        }

        //修改主体表中的数据
        govInfo.setGovNameHis(govInfo.getGovNameHis().replaceAll(oldName, ""));
        govInfo.setEntityNameHisRemarks(govInfo.getEntityNameHisRemarks().replaceAll(oldName,newOldName));
        String remarks = govInfo.getEntityNameHisRemarks();
        String[] split = remarks.split(EntityUtils.NAME_USED_REMARK_SIGN);
        String newRemarks="";
        //拆分曾用名
        for (String value:split){
            if (value.contains(oldName)){
                continue;
            }
            if (ObjectUtils.isEmpty(newRemarks)){
                newRemarks=value;
            }else {
                newRemarks=newRemarks+ EntityUtils.NAME_USED_REMARK_SIGN+value;
            }
        }
        govInfo.setEntityNameHisRemarks(newRemarks);
        govInfoMapper.updateById(govInfo);

        //修改曾用名表中的数据
        nameHis.setStatus(EntityUtils.INVALID);
        nameHisMapper.updateById(nameHis);
        return AjaxResult.success();
    }

    /**
     * 字段对应的名称
     * @author 冉浩岑
     * @date 2022/9/23 15:24
    */
    public static final String MORE_ENTITY_KPI_NAME="name";
    /**
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_ID="id";
    /**
     * 添加的指标封装的字段
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_MORE="more";
    /**
     * 新增指标的字段名称
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_KEY="key";
    /**
     * 新增指标的字段值
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_VALUE="value";

    private EntityAttrValueMapper entityAttrValueMapper;

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
                resultMap.put(EntityUtils.NAME_USED_NUM, count);
            }catch (Exception e){
                return resultMap;
            }
        }
        return resultMap;
    }
}
