package com.deloitte.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.bean.BeanUtils;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoByDto;
import com.deloitte.crm.domain.dto.EntityInfoResult;
import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;
import com.deloitte.crm.mapper.EntityAttrMapper;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.IEntityNameHisService;
import com.deloitte.crm.utils.HttpUtils;
import com.deloitte.crm.vo.EntityInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper,EntityInfo> implements IEntityInfoService
{

    private IEntityNameHisService iEntityNameHisService;

    private EntityInfoMapper entityInfoMapper;

    private EntityNameHisMapper nameHisMapper;

    private EntityAttrMapper entityAttrMapper;

    private EntityAttrValueMapper entityAttrValueMapper;

    @Autowired
    private HttpUtils httpUtils;

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

    /**
     *
     *统计企业主体信息
     * @return List<EntityInfoDto>
     * @author penTang
     * @date 2022/9/22 22:40
    */
    @Override
    public EntityInfoDto getEntityInfo(){

        EntityInfoDto entityInfoDto = new EntityInfoDto();
        List<EntityInfo> list = this.list();

        //TODO issue_bonds 是否发债 0-未发债 1-已发债
        List<EntityInfo> bonds = list().stream()
                .filter(row -> row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //TODO finance 是否金融机构
        List<EntityInfo> finance = list().stream()
                .filter(row -> row.getFinance() == 1)
                .collect(Collectors.toList());

        //TODO list 是否上市 0-未上市 1-已上市
        List<EntityInfo> entityInfoList = list().stream()
                .filter(row -> row.getList() == 1)
                .collect(Collectors.toList());

        //TODO 即是上市又是发债
        List<EntityInfo> listAndBonds = list().stream()
                .filter(row -> row.getList() == 1)
                .filter(row->row.getIssueBonds()==1)
                .collect(Collectors.toList());

        //TODO !即是上市又是发债
        List<EntityInfo> notListAndBonds = list().stream()
                .filter(row -> row.getList() == 0)
                .filter(row->row.getIssueBonds()==0)
                .collect(Collectors.toList());
        entityInfoDto.setBondsAndList(bonds.size());
        entityInfoDto.setEntitySum(list.size());
        entityInfoDto.setNotBondsAndList(notListAndBonds.size());
        entityInfoDto.setList(entityInfoList.size());
        entityInfoDto.setBondsAndList(listAndBonds.size());
        entityInfoDto.setFinance(finance.size());

        return entityInfoDto;

    }

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityInfo selectEntityInfoById(Long id)
    {
        return entityInfoMapper.selectEntityInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo)
    {
        return entityInfoMapper.selectEntityInfoList(entityInfo);
    }

    private final String IB = "IB";

    private final String ZERO = "0";

    private final Integer CODE_NUMBER = 6;

    /**
     * 新增【请填写功能名称】
     * 
     * @param entityDto 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityInfo(EntityDto entityDto) {
        EntityInfo entityInfo = new EntityInfo();
        BeanUtils.copyBeanProp(entityInfo,entityDto);

        //TODO 生成entity_code 那么将该值的 用 IB+0..+id  例 IB000001
        baseMapper.insert(entityInfo);
        Integer id = entityInfo.getId();
        StringBuilder sb = new StringBuilder(IB);
        for (int j = 0; j < CODE_NUMBER - String.valueOf(id).length(); j++) {sb.append(ZERO);}
        entityInfo.setEntityCode(sb.toString()+id);

        UpdateWrapper<EntityInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .eq(EntityInfo::getId,id)
                .set(EntityInfo::getEntityCode,entityInfo.getEntityCode());

        //TODO 新增曾用名 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        //1-企业主体
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfo.getEntityCode());
        entityNameHis.setOldName(entityDto.getOldName());
        //1-曾用名为自动生曾
        entityNameHis.setSource(1);
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHis.setCreated(new Date());
        iEntityNameHisService.insertEntityNameHis(entityNameHis);

        return entityInfoMapper.update(entityInfo,wrapper);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityInfo(EntityInfo entityInfo)
    {
        return entityInfoMapper.updateEntityInfo(entityInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoByIds(Long[] ids)
    {
        return entityInfoMapper.deleteEntityInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoById(Long id)
    {
        return entityInfoMapper.deleteEntityInfoById(id);
    }

    private final String CREDIT_CODE = "credit_code";

    private final String ENTITY_NAME = "entity_name";

    private final String DQ_CODE = "dq_code";

    /**
     * 传入社会信用代码于企业名称
     *  => 存在该社会信用代码 返回 比较信息为 false
     *     ==> 前端跳转调用人工对比信息，并确认
     *
     *  => 不存在社会信用代码 但存在相同企业名称 返回 比较信息 false
     *     ==> 前端跳转调用人工对比信息，并确认
     *
     *  => 不存在社会信用代码 也不存在相同企业名称 返回 比较信息 true
     *     ==> 确认新增主体 生成企业主体德勤代码、统一社会信用代码相关字段
     *
     * @author 正杰
     * @date 2022/9/22
     * @param creditCode 传入 企业统一社会信用代码
     * @param entityName 传入 企业名称
     * @return 比较信息结果
     */
    @Override
    public AjaxResult validEntity(String creditCode, String entityName) {
        //TODO 校验数据库是否存在该主体
        EntityInfo entityByCode = baseMapper.selectOne(new QueryWrapper<EntityInfo>().eq(CREDIT_CODE, creditCode));
        //库内无该社会信用代码
        if(entityByCode==null){
            EntityInfo entityByName = baseMapper.selectOne(new QueryWrapper<EntityInfo>().eq(ENTITY_NAME, entityName));
            //库内无该主体 是新增
            if(entityByName==null){
                return AjaxResult.success(new EntityInfoVo().setMsg(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo()));
            }

            //库内存在该主体 但是不存在该社会信用代码
            else{
                return AjaxResult.success(new EntityInfoVo()
                        .setEntityInfo(entityByName)
                        .setBo(BadInfo.GET)
                        .setMsg(BadInfo.EXITS_ENTITY_NAME.getInfo()));
            }
        }
        //库内已存在该社会信用代码
        return AjaxResult.success(new EntityInfoVo()
                .setEntityInfo(entityByCode)
                .setBo(BadInfo.GET)
                .setMsg(BadInfo.EXITS_CREDIT_CODE.getInfo()));
    }

    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     * @author 正杰
     * @date 2022/9/22
     * @param creditCode 统一社会信用代码
     * @param entityNewName 主体新名称
     * @param remarks 备注
     * @return 修改返回信息
     */
    @Override
    public AjaxResult editEntityNameHis(String creditCode, String entityNewName,String remarks) {

        //TODO 修改主体
        EntityInfo entity = baseMapper.selectOne(new QueryWrapper<EntityInfo>().eq(CREDIT_CODE,creditCode));
        String oldName = entity.getEntityName();

        //修改主体曾用名 entity_name_his 时 需要用 、 拼接
        entity.setEntityNameHis(entity.getEntityNameHis()+"、"+ oldName);
        //修改主体曾用名 entity_name_his_remarks 时 需要用 日期+更新人+备注;
        entity.setEntityNameHisRemarks(entity.getEntityNameHisRemarks()
                +"\r\n"
                +"；"
                + new Date()
                + " "
                + SecurityUtils.getUsername()
                + " "
                + remarks
        );

        UpdateWrapper<EntityInfo> entityInfoUpdateWrapper = new UpdateWrapper<>();
        entityInfoUpdateWrapper.lambda().eq(EntityInfo::getCreditCode,creditCode)
                .set(EntityInfo::getEntityName,entityNewName)
                .set(EntityInfo::getEntityNameHis,entity.getEntityNameHis())
                .set(EntityInfo::getEntityNameHisRemarks,entity.getEntityNameHisRemarks())
                .set(EntityInfo::getUpdated,entity.getUpdated());
        baseMapper.update(entity,entityInfoUpdateWrapper);

        //TODO 新增曾用名 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        //1-企业主体
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entity.getEntityCode());
        entityNameHis.setOldName(oldName);
        entityNameHis.setRemarks(remarks);
        //1-曾用名为自动生曾
        entityNameHis.setSource(1);
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHis.setCreated(new Date());
        iEntityNameHisService.insertEntityNameHis(entityNameHis);

        return AjaxResult.success();
    }

    /**
     * 根据名称查询主体
     * @param entityName
     * @return
     */
    @Override
    public List<EntityInfo> findByName(String entityName) {
        return baseMapper.findByName(entityName);
    }

    @Override
    public R addOldName(EntityInfo entity) {
        //获取操作用户
        String remoter = httpUtils.getRemoter();

        EntityInfo entityInfo = entityInfoMapper.selectById(entity.getId());
        //修改曾用名记录
        String entityNameHis = entityInfo.getEntityNameHis();
        if (ObjectUtils.isEmpty(entityNameHis)){
            entityInfo.setEntityNameHis(entity.getEntityNameHis());
        }else {
            entityInfo.setEntityNameHis(entityNameHis+ EntityUtils.NAME_USED_SIGN+entity.getEntityNameHis());
        }
        String nameHisRemarks = entity.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)){
            entityInfo.setEntityNameHisRemarks(entity.getUpdated()+remoter+entity.getEntityNameHisRemarks());
        }else {
            entityInfo.setEntityNameHisRemarks(entityNameHis+ EntityUtils.NAME_USED_REMARK_SIGN+entity.getUpdated()+remoter+entity.getEntityNameHisRemarks());
        }
        entityInfoMapper.updateById(entityInfo);

        //插入曾用名记录表
        EntityNameHis nameHis = new EntityNameHis();
        nameHis.setDqCode(entityInfo.getEntityCode());
        nameHis.setOldName(entity.getEntityNameHis());
        nameHis.setEntityType(2);
        nameHis.setHappenDate(entity.getUpdated());
        nameHis.setRemarks(entity.getEntityNameHisRemarks());
        nameHis.setSource(2);
        nameHisMapper.insert(nameHis);
        return R.ok();
    }

    @Override
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        //根据dqCode查询主体表
        QueryWrapper<EntityInfo>infoQuery=new QueryWrapper<>();
        EntityInfo entityInfo = entityInfoMapper.selectOne(infoQuery.lambda().eq(EntityInfo::getEntityCode, dqCode));
        //根据dqCode查询曾用名列表
        QueryWrapper<EntityNameHis>hisQuery=new QueryWrapper<>();
        EntityNameHis nameHis = nameHisMapper.selectOne(hisQuery.lambda()
                .eq(EntityNameHis::getDqCode, dqCode)
                .eq(EntityNameHis::getOldName, oldName));
        if (ObjectUtils.isEmpty(status)){
            //修改主体表中的数据
            entityInfo.setEntityNameHis(entityInfo.getEntityNameHis().replaceAll(oldName, newOldName));
            entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks().replaceAll(oldName,newOldName));
            entityInfoMapper.updateById(entityInfo);
            //修改曾用名表中的数据
            nameHis.setOldName(newOldName);
            nameHisMapper.updateById(nameHis);
            return R.ok();
        }

        //修改主体表中的数据
        entityInfo.setEntityNameHis(entityInfo.getEntityNameHis().replaceAll(oldName, ""));
        entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks().replaceAll(oldName,newOldName));
        String remarks = entityInfo.getEntityNameHisRemarks();
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
        entityInfo.setEntityNameHisRemarks(newRemarks);
        entityInfoMapper.updateById(entityInfo);

        //修改曾用名表中的数据
        nameHis.setStatus(EntityUtils.INVALID);
        nameHisMapper.updateById(nameHis);
        return R.ok();
    }

    @Override
    public R getInfoDetail(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper<>(entityInfo);
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(entityInfos)&&entityInfos.size()>1){
            return R.fail();
        }
        //TODO  添加主体其余详细信息

        return R.ok(entityInfos.get(0));
    }

    @Override
    public R getInfoList(EntityInfoByDto entityInfoDto) {
        entityInfoDto.setEntityInfo();
        EntityInfo entityInfo = entityInfoDto.getEntityInfo();
        Integer pageNum = entityInfoDto.getPageNum();
        Integer pageSize = entityInfoDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)){
            return R.fail("请输入页码");
        }
        if (ObjectUtils.isEmpty(pageSize)){
            pageSize= EntityUtils.DEFAULT_PAGE_SIZE;
        }
        Page<EntityInfo> pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper<>(entityInfo);

        Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //创建结果集
        Page<Map<String, Object>> pageResult=new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());
        //封装结果集
        List<Map<String,Object>> records = new ArrayList<>();
        entityInfoPage.getRecords().stream().forEach(o->
            records.add(getResultMap(o))
        );
        pageResult.setRecords(records);
        return R.ok(pageResult);
    }

    @Override
    public Integer updateInfoList(List<EntityInfo> list) {
        list.stream().forEach(o->entityInfoMapper.updateById(o));
        return list.size();
    }

    @Override
    public List<EntityInfo> checkEntity(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper(entityInfo);
        return entityInfoMapper.selectList(queryWrapper);
    }
    @Override
    public Object getListEntityByPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)&&ObjectUtils.isEmpty(pageSize)){
            return getListEntityAll(entityAttrDto);
        } else{
            return getListEntityPage(entityAttrDto);
        }
    }
    /**
     * 全量导出
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:04
    */
    public List<EntityInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto) {

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(EntityInfo::getList,1);
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper);

        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();

        entityInfos.stream().forEach(o->{

            EntityInfoResult entityInfoResult=new EntityInfoResult();
            entityInfoResult.setEntityInfo(o);

            if (!CollectionUtils.isEmpty(mapList)){
                List<Map<String, Object>> more = new ArrayList<>();
                for (Map<String,String> map:mapList){
                    QueryWrapper<EntityAttrValue>valueQuer=new QueryWrapper<>();
                    EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                            .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                            .eq(EntityAttrValue::getEntityCode,o.getEntityCode()));

                    Map<String, Object> mapMore = new HashMap<>();
                    //新增指标栏
                    mapMore.put(MORE_ENTITY_KPI_KEY,map.get(MORE_ENTITY_KPI_NAME));
                    if (attrValue==null){
                        mapMore.put(MORE_ENTITY_KPI_VALUE,null);
                    }else {
                        mapMore.put(MORE_ENTITY_KPI_KEY, attrValue.getValue());
                    }
                    more.add(mapMore);
                }
                entityInfoResult.setMore(more);
            }

            resultRecords.add(entityInfoResult);
        });
        return resultRecords;
    }
    /**
     * 分页查询
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:04
     */
    public Page<EntityInfoResult> getListEntityPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Page<EntityInfo>pageInfo=new Page<>(pageNum,pageSize);
        QueryWrapper<EntityInfo>queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(EntityInfo::getList,1);
        Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //查询分页数据集
        Page<EntityInfoResult> pageResult=new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());

        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();
        //添加指标栏位
        List<EntityInfo> records = entityInfoPage.getRecords();
        records.stream().forEach(o->{
            EntityInfoResult entityInfoResult = new EntityInfoResult();
            entityInfoResult.setEntityInfo(o);


            if (!CollectionUtils.isEmpty(mapList)){
                List<Map<String, Object>> more = new ArrayList<>();
                for (Map<String,String> map:mapList){
                    QueryWrapper<EntityAttrValue>valueQuer=new QueryWrapper<>();
                    EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                            .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                            .eq(EntityAttrValue::getEntityCode,o.getEntityCode()));

                    Map<String,Object>moreMap=new HashMap<>();
                    //新增指标栏
                    moreMap.put(MORE_ENTITY_KPI_KEY,map.get(MORE_ENTITY_KPI_NAME));
                    if (attrValue==null){
                        moreMap.put(MORE_ENTITY_KPI_VALUE,null);
                    }else {
                        moreMap.put(MORE_ENTITY_KPI_KEY, attrValue.getValue());
                    }
                    more.add(moreMap);
                }
                entityInfoResult.setMore(more);
            }
            resultRecords.add(entityInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    /**
     * EntityInfo 对象转 map,并查询 曾用名条数
     *
     * @param entityInfo
     * @return Map<String,Object>
     * @author 冉浩岑
     * @date 2022/9/22 23:45
     */
    public Map<String, Object> getResultMap(EntityInfo entityInfo) {
        Map<String, Object> resultMap = new HashMap();
        if (null != entityInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(entityInfo), new TypeReference<Map<String, String>>() {
            });
            try {
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                Long count = nameHisMapper.selectCount(wrapper.lambda().eq(EntityNameHis::getDqCode, entityInfo.getEntityCode()));
                resultMap.put(EntityUtils.NAME_USED_NUM, count);
            }catch (Exception e){
                return resultMap;
            }
        }
        return resultMap;
    }
}