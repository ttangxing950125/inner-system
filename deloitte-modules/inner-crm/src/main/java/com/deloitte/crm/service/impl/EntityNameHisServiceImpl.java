package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.service.IEntityNameHisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@Slf4j
public class EntityNameHisServiceImpl extends ServiceImpl<EntityNameHisMapper,EntityNameHis> implements IEntityNameHisService
{
    @Resource
    private EntityNameHisMapper entityNameHisMapper;

    @Resource
    private EntityInfoMapper entityInfoMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityNameHis selectEntityNameHisById(Long id)
    {
        return entityNameHisMapper.selectEntityNameHisById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityNameHis 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityNameHis> selectEntityNameHisList(EntityNameHis entityNameHis)
    {
        return entityNameHisMapper.selectEntityNameHisList(entityNameHis);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityNameHis(EntityNameHis entityNameHis)
    {
        return entityNameHisMapper.insertEntityNameHis(entityNameHis);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityNameHis 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityNameHis(EntityNameHis entityNameHis)
    {
        return entityNameHisMapper.updateEntityNameHis(entityNameHis);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityNameHisByIds(Long[] ids)
    {
        return entityNameHisMapper.deleteEntityNameHisByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityNameHisById(Long id)
    {
        return entityNameHisMapper.deleteEntityNameHisById(id);
    }

    @Override
    public List<EntityNameHis> getNameListByDqCoded(String dqCode) {
        QueryWrapper<EntityNameHis>queryWrapper=new QueryWrapper<>();
        return entityNameHisMapper.selectList(
                queryWrapper.lambda()
                        .eq(EntityNameHis::getDqCode,dqCode));
    }

    @Override
    public List<Map<String, Object>> getGovHisNameList(String param) {
        return entityNameHisMapper.getGovHisNameList(param);
    }

    @Override
    public List<Map<String, Object>> getEntityHisNameList(String param) {
        return entityNameHisMapper.getEntityHisNameList(param);
    }

    /**
     * 新增库中主体的曾用名 by正杰
     * @param entityCode
     * @param entityName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addEntityNameHis(String entityCode, String entityName) {
        log.info("  =>> 角色7 新增主体曾用名:开始 <<=  ");
        EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode));
        if(ObjectUtils.isEmpty(entityInfo)){return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());}
        EntityNameHis entityNameHis = baseMapper.selectOne(new QueryWrapper<EntityNameHis>().lambda().eq(EntityNameHis::getOldName, entityName));
        if(!ObjectUtils.isEmpty(entityNameHis)){return R.fail(BadInfo.EXITS_ENTITY_OLD_NAME.getInfo());}

        // 新增一条数据进入曾用名列表
        baseMapper.insert(new EntityNameHis()
                .setEntityType(1)
                .setStatus(1)
                .setDqCode(entityCode)
                .setOldName(entityName)
                .setHappenDate(new Date())
                .setRemarks("系统自动生成")
                .setCreater(SecurityUtils.getUsername()));

        // 对主体曾用名列表进行操作
        String oldNames = entityInfo.getEntityNameHis();
        if(ObjectUtils.isEmpty(oldNames)){
            //当列表为空时 直接新增
            entityInfo.setEntityNameHis(entityName);
        }else{
            //当列表不为空时 再次校验曾用名是否被使用过
            String[] oldNameArr = oldNames.split(",");
            if(Arrays.asList(oldNameArr).contains(entityName)){
                //如果被使用过 证明 曾用名表中 没有存储到这个字段 但是 主表中数据已经存了 所以只需要return 不需要断言
                log.info("  =>> 角色7 新增曾用名出现在主表中 并没有出现再历史表 <<=  ");
                return R.ok(SuccessInfo.SUCCESS.getInfo());
            }else{
                //如果没被使用过 那么就再此基础上 再添加一条
                entityInfo.setEntityNameHis(entityInfo.getEntityNameHis()+","+entityName);
            }
        }
        entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks()+ DateUtil.format(new Date(),"yyyy-MM-dd")+ " "+ SecurityUtils.getUsername()+ " " + "系统自动生成");
        entityInfoMapper.updateById(entityInfo);
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }

    public R addEntityNameHis(String entityCode,String entityName,String updated,String remarks) {
        log.info("  =>> 角色7 新增主体曾用名:开始 <<=  ");
        EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode));
        if(ObjectUtils.isEmpty(entityInfo)){return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());}
        EntityNameHis entityNameHis = baseMapper.selectOne(new QueryWrapper<EntityNameHis>().lambda().eq(EntityNameHis::getOldName, entityName));
        if(!ObjectUtils.isEmpty(entityNameHis)){return R.fail(BadInfo.EXITS_ENTITY_OLD_NAME.getInfo());}

        // 新增一条数据进入曾用名列表
        baseMapper.insert(new EntityNameHis()
                .setEntityType(1)
                .setStatus(1)
                .setDqCode(entityCode)
                .setOldName(entityName)
                .setHappenDate(new Date())
                .setRemarks(remarks)
                .setCreater(SecurityUtils.getUsername()));

        // 对主体曾用名列表进行操作
        String oldNames = entityInfo.getEntityNameHis();
        if(ObjectUtils.isEmpty(oldNames)){
            //当列表为空时 直接新增
            entityInfo.setEntityNameHis(entityName);
        }else{
            //当列表不为空时 再次校验曾用名是否被使用过
            String[] oldNameArr = oldNames.split(",");
            if(Arrays.asList(oldNameArr).contains(entityName)){
                //如果被使用过 证明 曾用名表中 没有存储到这个字段 但是 主表中数据已经存了 所以只需要return 不需要断言
                log.info("  =>> 角色7 新增曾用名出现在主表中 并没有出现再历史表 <<=  ");
                return R.ok(SuccessInfo.SUCCESS.getInfo());
            }else{
                //如果没被使用过 那么就再此基础上 再添加一条
                entityInfo.setEntityNameHis(entityInfo.getEntityNameHis()+","+entityName);
            }
        }
        entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks()+ DateUtil.format(new Date(),"yyyy-MM-dd")+ " "+ SecurityUtils.getUsername()+ " " + "系统自动生成");
        entityInfoMapper.updateById(entityInfo);
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }
}
