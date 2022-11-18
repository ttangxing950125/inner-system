package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.mapper.CrmTypeInfoMapper;
import com.deloitte.crm.domain.CrmTypeInfo;
import com.deloitte.crm.service.CrmTypeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (CrmTypeInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-28 13:11:01
 */
@Service("crmTypeInfoService")
public class CrmTypeInfoServiceImpl extends ServiceImpl<CrmTypeInfoMapper, CrmTypeInfo> implements CrmTypeInfoService {
    @Resource
    private RedisService redisService;

    /**
     * 查询树结构根据分类
     *
     * @param type
     * @return
     * @author 吴鹏鹏
     */
    @Override
    public List<CrmTypeInfo> findTreeByType(String parentCode, Integer type) {
        Wrapper<CrmTypeInfo> wrapper = Wrappers.<CrmTypeInfo>lambdaQuery().eq(CrmTypeInfo::getType, type);
        List<CrmTypeInfo> categories = getBaseMapper().selectList(wrapper);
        return this.findTreeLoop(parentCode, categories);
    }

    @Override
    public Set<CrmTypeInfo> findCodeByParent(CrmTypeInfo crmTypeInfo, Integer type) {
        Wrapper<CrmTypeInfo> wrapper = Wrappers.<CrmTypeInfo>lambdaQuery().eq(CrmTypeInfo::getType, type);
        List<CrmTypeInfo> categories = getBaseMapper().selectList(wrapper);
        return getDataUpList(categories, crmTypeInfo);
    }

    /**
     * 缓存添加
     * 拼装redis缓存为type:code:实体类
     *
     * @return Boolean
     */
    @Override
    public Boolean cacheAll() {

        final List<CrmTypeInfo> list = this.list();

        redisService.deleteObject(CacheName.CRM_TYPE_INFO);

        Map<String, CrmTypeInfo> attrMap = list.stream().collect(Collectors.toMap(item -> item.getType() + "::" + item.getCode(), Function.identity()));

        redisService.redisTemplate.opsForHash().putAll(CacheName.CRM_TYPE_INFO, attrMap);

        return true;

    }

    private Set<CrmTypeInfo> getDataUpList(List<CrmTypeInfo> categories, CrmTypeInfo crmTypeInfo) {
        if (CollectionUtil.isNotEmpty(categories)) {
            Set<CrmTypeInfo> set = new HashSet<>();
            String parentCode = crmTypeInfo.getParentCode();
            List<CrmTypeInfo> crmTypeInfos = categories.stream().filter(item -> item.getCode().equals(parentCode)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(crmTypeInfos)) {
                CrmTypeInfo parentDept = crmTypeInfos.get(0);
                set.add(parentDept);
                final Set<CrmTypeInfo> deptUpTree = getDataUpList(categories, parentDept);
                if (deptUpTree != null) {
                    set.addAll(deptUpTree);
                }
                return set;
            }
        }
        return null;
    }

    private List<CrmTypeInfo> findTreeLoop(String parentCode, List<CrmTypeInfo> categories) {
        //用来存放返回出去的集合
        List<CrmTypeInfo> returnArr = new ArrayList<>();

        //循环这个categories
        for (int i = 0; i < categories.size(); i++) {
            CrmTypeInfo category = categories.get(i);
            //等于0的数据没有问题了
            if (Objects.equals(category.getParentCode(), parentCode)) {


                category.setChildren(findTreeLoop(category.getCode(), categories));

                categories.remove(i);

                returnArr.add(category);
                i--;
                //这些数据都没有儿子
            }
        }

        if (CollUtil.isEmpty(returnArr)) {
            return null;
        }

        return returnArr;
    }
}
