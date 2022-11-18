package com.deloitte.additional.recording.service.impl.center;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.deloitte.additional.recording.dto.ModerQuanAndQualFactorDto;
import com.deloitte.additional.recording.mapper.center.ModelQualFactorMapper;
import com.deloitte.additional.recording.service.center.ModelQualQuanGovFactorService;
import com.deloitte.common.core.constant.Constants;
import com.deloitte.common.core.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @创建人 tangx
 * @创建时间 2022/11/17
 * @描述
 */
@Service
@DS("data_center")
public class ModelQualQuanGovFactorServiceImpl implements ModelQualQuanGovFactorService {

    @Autowired
    private ModelQualFactorMapper modelQualFactorMapper;


    @Override
    public List<ModerQuanAndQualFactorDto> getQualListFromDataCenter(String tarMid, String prefix, String type) {
        //判断type是否为空
        List<ModerQuanAndQualFactorDto> list = new ArrayList<>();
        if (StrUtil.isNotBlank(type)) {
            if (Constants.QUAL_TYPE.equals(type)) {
                //查询定性指标
                List<ModerQuanAndQualFactorDto> qualList = modelQualFactorMapper.findQualByPre(prefix, tarMid);
                list.addAll(qualList);
            }
            if (Constants.QUAN_TYPE.equals(type)) {
                //查询定量
                List<ModerQuanAndQualFactorDto> quanList = modelQualFactorMapper.findQuanByPre(prefix, tarMid);

                list.addAll(quanList);
            }
            //查询政府
            if (Constants.GOV_TYPE.equals(type)) {
                List<ModerQuanAndQualFactorDto> govList = modelQualFactorMapper.findGovByPre(prefix, tarMid);

                list.addAll(govList);
            }
            return list;

        }
        //用户没有传类型 需要三者一起查询 并行方式同时查询
        Future<List<ModerQuanAndQualFactorDto>> qualFuture = findQualByPre(prefix, tarMid);
        Future<List<ModerQuanAndQualFactorDto>> quanFuture = findQuanByPre(prefix, tarMid);
        Future<List<ModerQuanAndQualFactorDto>> govFuture = findGovByPre(prefix, tarMid);
        try {
            list.addAll(qualFuture.get());
            list.addAll(quanFuture.get());
            list.addAll(govFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public String getQualFromDataCenter(String prefix, String tarQualid, String type) {

            if (Constants.QUAL_TYPE.equals(type)) {
                //查询定性指标
                return modelQualFactorMapper.getQualByPre(prefix,tarQualid);
            }
            else if (Constants.QUAN_TYPE.equals(type)) {
                //查询定量
                return modelQualFactorMapper.getQuanByPre(prefix,tarQualid);

            }
            //查询政府
                return modelQualFactorMapper.getGovQualByPre(prefix,tarQualid);

    }
    /**
     * 异步获取定性
     */
    @Async
    public Future<List<ModerQuanAndQualFactorDto>> findQualByPre(String prefix, String tarMid) {
        List<ModerQuanAndQualFactorDto> qualList = modelQualFactorMapper.findQualByPre(prefix, tarMid);
        return new AsyncResult<>(qualList);
    }

    /**
     * 异步获取定量
     */
    @Async
    public Future<List<ModerQuanAndQualFactorDto>> findQuanByPre(String prefix, String tarMid) {
        List<ModerQuanAndQualFactorDto> quanList = modelQualFactorMapper.findQuanByPre(prefix, tarMid);
        return new AsyncResult<>(quanList);
    }

    /**
     * 异步获取政府
     */
    @Async
    public Future<List<ModerQuanAndQualFactorDto>> findGovByPre(String prefix, String tarMid) {
        List<ModerQuanAndQualFactorDto> govList = modelQualFactorMapper.findGovByPre(prefix, tarMid);
        return new AsyncResult<>(govList);
    }
}
