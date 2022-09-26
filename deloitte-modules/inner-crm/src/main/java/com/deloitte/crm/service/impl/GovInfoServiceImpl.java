package com.deloitte.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.GovInfoByDto;
import com.deloitte.crm.domain.dto.GovInfoResult;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.mapper.GovInfoMapper;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class GovInfoServiceImpl extends ServiceImpl<GovInfoMapper, GovInfo> implements IGovInfoService {
    @Resource
    private GovInfoMapper govInfoMapper;

    @Resource
    private EntityNameHisMapper nameHisMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public GovInfo selectGovInfoById(Long id) {
        return govInfoMapper.selectGovInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param govInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<GovInfo> selectGovInfoList(GovInfo govInfo) {
        return govInfoMapper.selectGovInfoList(govInfo);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertGovInfo(GovInfo govInfo) {
        return govInfoMapper.insertGovInfo(govInfo);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateGovInfo(GovInfo govInfo) {
        return govInfoMapper.updateGovInfo(govInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovInfoByIds(Long[] ids) {
        return govInfoMapper.deleteGovInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovInfoById(Long id) {
        return govInfoMapper.deleteGovInfoById(id);
    }

    @Transactional
    @Override
    public R updateInfoList(List<GovInfo> list) {
        list.stream().forEach(o -> {
            GovInfo govInfo = govInfoMapper.selectById(o.getId());
            govInfoMapper.updateById(o);
            //修改政府主体名称时，需要添加曾用名
            if (!ObjectUtils.isEmpty(o.getGovName())){
                String oldName = govInfo.getGovName();
                GovInfo addOldName = new GovInfo();
                addOldName.setId(o.getId());
                addOldName.setGovNameHis(oldName);
                addOldName.setEntityNameHisRemarks(o.getEntityNameHisRemarks());
                addOldName(addOldName);
            }
            //修改政府主体代码时，需要修改主体历史表中的政府主体代码
            if (!ObjectUtils.isEmpty(o.getDqGovCode())){
                String oldDqCode = govInfo.getDqGovCode();
                QueryWrapper<EntityNameHis>wrapper=new QueryWrapper<>();
                EntityNameHis nameHis = new EntityNameHis();
                nameHis.setDqCode(o.getDqGovCode());
                nameHisMapper.update(nameHis,wrapper.lambda().eq(EntityNameHis::getDqCode,oldDqCode));
            }
        });
        return R.ok(list.size());
    }

    @Override
    public R getInfoDetail(GovInfo govInfo) {
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>(govInfo);
        List<GovInfo> govInfos = govInfoMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(govInfos)){
            return R.fail("异常查询，数据为空");
        }
        if (govInfos.size() > 1) {
            return R.fail("异常查询，唯一识别码查出多条数据");
        }
        //TODO  查询上级行政code
        String preGovCode = govInfos.get(0).getPreGovCode();
        queryWrapper.clear();
        Map<String, Object> result = new HashMap<>();
        result.put("govInfo", govInfos.get(0));
        if (ObjectUtils.isEmpty(preGovCode)) {
            return R.ok(result);
        }
        GovInfo preGov = govInfoMapper.selectOne(queryWrapper.lambda().eq(GovInfo::getDqGovCode, preGovCode));
        result.put("preGov", preGov);
        return R.ok(result);
    }


    /**
     * 统计政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     */

    @Override
    public GovInfoDto getGovInfo() {
        List<GovInfo> list = this.list();
        GovInfoDto govInfoDto = new GovInfoDto();
//TODO gov_level_big 是否 省  1-是
        List<GovInfo> province = list().stream()
                .filter(row -> row.getGovLevelBig()!=null && row.getGovLevelBig() == 1)
                .collect(Collectors.toList());

//TODO gov_level_big 是否 市  2-是
        List<GovInfo> city = list().stream()
                .filter(row -> row.getGovLevelBig()!=null && row.getGovLevelBig() == 2)
                .collect(Collectors.toList());

//TODO gov_level_big 是否 县  3-是
        List<GovInfo> county = list().stream()
                .filter(row ->row.getGovLevelBig()!=null && row.getGovLevelBig() == 3)
                .collect(Collectors.toList());

//TODO gov_level_big 是否 经开  4-是
        List<GovInfo> open = list().stream()
                .filter(row -> row.getGovLevelBig()!=null && row.getGovLevelBig() == 4)

                .collect(Collectors.toList());

        govInfoDto.setProvince(province.size());
        govInfoDto.setCity(city.size());
        govInfoDto.setCounty(county.size());
        govInfoDto.setOpen(open.size());
        govInfoDto.setGovSum(list.size());
        return govInfoDto;
    }

    @Override
    public R getInfoList(GovInfoByDto govInfodto) {
        govInfodto.setGovInfo();
        GovInfo govInfo = govInfodto.getGovInfo();
        Integer pageNum = govInfodto.getPageNum();
        Integer pageSize = govInfodto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)) {
            return R.fail("请输入页码");
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = EntityUtils.DEFAULT_PAGE_SIZE;
        }
        Page<GovInfo> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>(govInfo);

        Page<GovInfo> govInfoPage = govInfoMapper.selectPage(pageInfo, queryWrapper);

        //创建结果集
        Page<Map<String, Object>> pageResult = new Page<>();
        pageResult.setTotal(govInfoPage.getTotal()).setPages(govInfoPage.getPages()).setCurrent(govInfoPage.getCurrent());
        //封装结果集
        List<Map<String, Object>> records = new ArrayList<>();
        govInfoPage.getRecords().stream().forEach(o -> {
            records.add(getResultMap(o));
        });
        pageResult.setRecords(records);
        return R.ok(pageResult);
    }

    @Autowired
    private HttpUtils httpUtils;

    @Transactional
    @Override
    public R addOldName(GovInfo gov) {
        //获取操作用户
        String remoter = httpUtils.getRemoter();

        GovInfo govInfo = govInfoMapper.selectById(gov.getId());

        String govCode = govInfo.getGovCode();
        String nameHis = gov.getGovNameHis();
        QueryWrapper<EntityNameHis>queryWrapper=new QueryWrapper<>();
        Long aLong = nameHisMapper.selectCount(queryWrapper.lambda().eq(EntityNameHis::getDqCode, govCode).eq(EntityNameHis::getOldName, nameHis));
        if (aLong>0){
            return R.fail("曾用名重复，请重新输入");
        }

        //修改曾用名记录
        String govNameHis = govInfo.getGovNameHis();
        if (ObjectUtils.isEmpty(govNameHis)) {
            govInfo.setGovNameHis(nameHis);
        } else {
            govInfo.setGovNameHis(govNameHis + EntityUtils.NAME_USED_SIGN + nameHis);
        }
        String nameHisRemarks = gov.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            govInfo.setEntityNameHisRemarks(new Date() + remoter + gov.getEntityNameHisRemarks());
        } else {
            govInfo.setEntityNameHisRemarks(govNameHis + EntityUtils.NAME_USED_REMARK_SIGN + new Date() + remoter + gov.getEntityNameHisRemarks());
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
        return R.ok();
    }

    @Override
    public R checkGov(GovInfo govInfo) {
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper(govInfo);
        return R.ok(govInfoMapper.selectList(queryWrapper));
    }

    @Override
    public Object getListEntityByPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum) && ObjectUtils.isEmpty(pageSize)) {
            return getListEntityAll(entityAttrDto);
        } else {
            return getListEntityPage(entityAttrDto);
        }
    }

    /**
     * 全量导出
     *
     * @param entityAttrDto
     * @return List<GovInfoResult>
     * @author 冉浩岑
     * @date 2022/9/26 00:35
     */
    public List<GovInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto) {

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>();
        List<GovInfo> govInfos = govInfoMapper.selectList(queryWrapper);

        //封装新的结果集
        List<GovInfoResult> resultRecords = new ArrayList<>();

        govInfos.stream().forEach(o -> {
            GovInfoResult govInfoResult = new GovInfoResult();
            govInfoResult.setGovInfo(o);

            if (!CollectionUtils.isEmpty(mapList)) {
                List<Map<String, Object>> more = new ArrayList<>();

                for (Map<String, String> map : mapList) {
                    QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                    EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                            .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                            .eq(EntityAttrValue::getEntityCode, o.getDqGovCode()));
                    Map<String, Object> moreMap = new HashMap<>();
                    //新增指标栏
                    moreMap.put(MORE_ENTITY_KPI_KEY, map.get(MORE_ENTITY_KPI_NAME));
                    if (ObjectUtils.isEmpty(attrValue)) {
                        moreMap.put(MORE_ENTITY_KPI_VALUE, null);
                    } else {
                        moreMap.put(MORE_ENTITY_KPI_VALUE, attrValue.getValue());
                    }
                    more.add(moreMap);
                }
                govInfoResult.setMore(more);
            }
            resultRecords.add(govInfoResult);
        });
        return resultRecords;
    }

    /**
     * 分页查询
     *
     * @param entityAttrDto
     * @return Page<GovInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:05
     */
    public Page<GovInfoResult> getListEntityPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Page<GovInfo> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>();
        Page<GovInfo> entityInfoPage = govInfoMapper.selectPage(pageInfo, queryWrapper);

        //查询分页数据集
        Page<GovInfoResult> pageResult = new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());

        //封装新的结果集
        List<GovInfoResult> resultRecords = new ArrayList<>();
        //添加指标栏位
        List<GovInfo> records = entityInfoPage.getRecords();
        records.stream().forEach(o -> {
            GovInfoResult govInfoResult = new GovInfoResult();
            govInfoResult.setGovInfo(o);
            if (!CollectionUtils.isEmpty(mapList)) {
                List<Map<String, Object>> more = new ArrayList<>();
                for (Map<String, String> map : mapList) {
                    QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                    EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                            .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                            .eq(EntityAttrValue::getEntityCode, o.getDqGovCode()));
                    //新增指标栏
                    Map<String, Object> moreMap = new HashMap<>();
                    moreMap.put(MORE_ENTITY_KPI_KEY, map.get(MORE_ENTITY_KPI_NAME));
                    if (ObjectUtils.isEmpty(attrValue)) {
                        moreMap.put(MORE_ENTITY_KPI_VALUE, null);
                    } else {
                        moreMap.put(MORE_ENTITY_KPI_VALUE, attrValue.getValue());
                    }
                    more.add(moreMap);
                }
                govInfoResult.setMore(more);
            }
            resultRecords.add(govInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    @Transactional
    @Override
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        //根据dqCode查询主体表
        QueryWrapper<GovInfo> infoQuery = new QueryWrapper<>();
        GovInfo govInfo = govInfoMapper.selectOne(infoQuery.lambda().eq(GovInfo::getDqGovCode, dqCode));
        //根据dqCode查询曾用名列表
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        EntityNameHis nameHis = nameHisMapper.selectOne(hisQuery.lambda()
                .eq(EntityNameHis::getDqCode, dqCode)
                .eq(EntityNameHis::getOldName, oldName));
        if (ObjectUtils.isEmpty(status)) {
            //修改主体表中的数据
            govInfo.setGovNameHis(govInfo.getGovNameHis().replaceAll(oldName, newOldName));
            govInfo.setEntityNameHisRemarks(govInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
            govInfoMapper.updateById(govInfo);
            //修改曾用名表中的数据
            nameHis.setOldName(newOldName);
            nameHisMapper.updateById(nameHis);
            return R.ok();
        }

        //修改主体表中的数据
        govInfo.setGovNameHis(govInfo.getGovNameHis().replaceAll(oldName, ""));
        govInfo.setEntityNameHisRemarks(govInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
        String remarks = govInfo.getEntityNameHisRemarks();
        String[] split = remarks.split(EntityUtils.NAME_USED_REMARK_SIGN);
        String newRemarks = "";
        //拆分曾用名
        for (String value : split) {
            if (value.contains(oldName)) {
                continue;
            }
            if (ObjectUtils.isEmpty(newRemarks)) {
                newRemarks = value;
            } else {
                newRemarks = newRemarks + EntityUtils.NAME_USED_REMARK_SIGN + value;
            }
        }
        govInfo.setEntityNameHisRemarks(newRemarks);
        govInfoMapper.updateById(govInfo);

        //修改曾用名表中的数据
        nameHis.setStatus(EntityUtils.INVALID);
        nameHisMapper.updateById(nameHis);
        return R.ok();
    }


    /**
     * 字段对应的名称
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_NAME = "name";
    /**
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_ID = "id";
    /**
     * 添加的指标封装的字段
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_MORE = "more";
    /**
     * 新增指标的字段名称
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_KEY = "key";
    /**
     * 新增指标的字段值
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_VALUE = "value";

    private EntityAttrValueMapper entityAttrValueMapper;

    /**
     * GovInfo 对象转 map,并查询 曾用名条数
     *
     * @param govInfo
     * @return Map<String, Object>
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
            } catch (Exception e) {
                return resultMap;
            }
        }
        return resultMap;
    }
}
