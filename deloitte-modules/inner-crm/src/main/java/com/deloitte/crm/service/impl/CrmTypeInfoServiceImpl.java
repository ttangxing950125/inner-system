package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.CrmTypeInfoMapper;
import com.deloitte.crm.domain.CrmTypeInfo;
import com.deloitte.crm.service.CrmTypeInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (CrmTypeInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-28 13:11:01
 */
@Service("crmTypeInfoService")
public class CrmTypeInfoServiceImpl extends ServiceImpl<CrmTypeInfoMapper, CrmTypeInfo> implements CrmTypeInfoService {

    /**
     * 查询树结构根据分类
     *
     * @param type
     * @return
     * @author 吴鹏鹏
     */
    @Override
    public List<CrmTypeInfo> findTreeByType(String parentCode, Integer type) {
        Wrapper<CrmTypeInfo> wrapper = Wrappers.<CrmTypeInfo>lambdaQuery()
                .eq(CrmTypeInfo::getType, type);

        List<CrmTypeInfo> categories = getBaseMapper().selectList(wrapper);
        return this.findTreeLoop(parentCode, categories);
    }


    private List<CrmTypeInfo> findTreeLoop(String parentCode, List<CrmTypeInfo> categories) {
        //用来存放返回出去的集合
        List<CrmTypeInfo> returnArr = new ArrayList<>();

        //循环这个categories
        for (int i = 0; i < categories.size(); i++) {
            CrmTypeInfo category = categories.get(i);
            //等于0的数据没有问题了
            if (Objects.equals(category.getParentCode(), parentCode)){



                category.setChildren(findTreeLoop(category.getCode(), categories));

                categories.remove(i);

                returnArr.add(category);
                i--;
                //这些数据都没有儿子
            }
        }

        return returnArr;
    }
}
