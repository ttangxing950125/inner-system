package com.deloitte.crm.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.ProductsMasterRelHis;
import com.deloitte.crm.mapper.ProducysMasterRelHisMapper;
import com.deloitte.crm.service.ProducysMasterRelHisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/18 16:53
 */
@Service
@AllArgsConstructor
public class ProducysMasterRelHisImpl extends ServiceImpl<ProducysMasterRelHisMapper, ProductsMasterRelHis> implements ProducysMasterRelHisService
{

    /**
     *
     * 查询历史记录
     * @return List<ProductsMasterRelHis>
     * @author penTang
     * @date 2022/10/18 17:39
    */
    @Override
    public List<ProductsMasterRelHis> getHis(){
        return list();
    }



}
