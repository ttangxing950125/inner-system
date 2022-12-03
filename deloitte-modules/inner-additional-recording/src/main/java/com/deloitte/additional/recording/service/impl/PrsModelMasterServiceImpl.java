package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import com.deloitte.additional.recording.mapper.PrsModelMasterMapper;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.mapper.PrsVersionMasterMapper;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.vo.master.PrsModelMasterSelectVO;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.bean.BeanUtils;
import com.deloitte.additional.recording.util.ApplicationContextHolder;
import com.deloitte.additional.recording.vo.CenterMasterVo;
import com.deloitte.additional.recording.vo.ModelMasterVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * (PrsModelMaster)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsModelMasterService")
public class PrsModelMasterServiceImpl extends ServiceImpl<PrsModelMasterMapper, PrsModelMaster> implements PrsModelMasterService {
    @Resource
    private PrsModelMasterMapper prsModelMasterMapper;
    @Resource
    private PrsVersionMasterMapper prsVersionMasterMapper;

    /**
     * 统计-数据清单模块 下拉框专用 获取敞口数据
     *
     * @return {@link java.util.HashMap}
     * {@link com.deloitte.additional.recording.domain.PrsModelMaster}
     * 方法停用 使用 finAllPrsModelMasterByPrjId
     * @see PrsModelMasterServiceImpl#finAllPrsModelMasterByPrjId
     */
    @Deprecated
    @Override
    public List<Map<String, Object>> finAllPrsModelMaster() {
        CopyOnWriteArrayList<Map<String, Object>> datas = new CopyOnWriteArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        prsModelMasterMapper.selectList(new LambdaQueryWrapper<PrsModelMaster>().eq(PrsModelMaster::getStatus, 1)).stream().filter(e -> StringUtils.isNotEmpty(e.getName())).forEach(e -> {
            //id
            maps.put("id", e.getId());
            //敞口编码
            maps.put("modelCode", e.getModelCode());
            //敞口名称
            maps.put("name", e.getName());
            datas.add(maps);
        });
        return datas;
    }

    /**
     * 获取所有敞口基础数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 15:54
     */
    @Override
    public R getAllMaster() {
        return R.ok(prsModelMasterMapper.selectList(new QueryWrapper<PrsModelMaster>().lambda().eq(PrsModelMaster::getStatus, 1)));
    }

    @Override
    public List<PrsModelMasterSelectVO> selectList() {
        List<PrsModelMaster> list = lambdaQuery().eq(PrsModelMaster::getStatus, 1).list();
        if (list != null) {
            return BeanUtils.copy(list, PrsModelMasterSelectVO.class);
        }
        return null;
    }
    /**
     * 获取所有敞口基础数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 15:54
     */
    @Override
    public R getAvailableMaster() {
        return R.ok(prsModelMasterMapper.selectList(new QueryWrapper<PrsModelMaster>().lambda().eq(PrsModelMaster::getStatus, 1)));
    }


    @Override
    public List<PrsModelMasterSelectVO> selectList(Integer versionId) {

        List<PrsModelMaster> list =  prsModelMasterMapper.listByVersion(versionId);
        if (list != null) {
            return BeanUtils.copy(list, PrsModelMasterSelectVO.class);
        }
        return null;
    }
    /**
     * 版本ID拿敞口数据
     * @param prjId
     * @return
     */
    @Override
    public R finAllPrsModelMasterByPrjId(Integer prjId) {
        List<PrsModelMaster> results = new ArrayList<>();
        PrsProjectVersions prsProjectVersions = Optional.ofNullable(ApplicationContextHolder.get().getBean(PrsProjectVersionsMapper.class).selectById(prjId)).orElseThrow(() -> new ServiceException("版本数据不存在:" + prjId));
        List<PrsVersionMaster> prsVersionMasters = prsVersionMasterMapper.selectList(new LambdaQueryWrapper<PrsVersionMaster>().eq(PrsVersionMaster::getPrjId, prsProjectVersions.getId()).eq(PrsVersionMaster::getStatus, 1));
        if (CollUtil.isEmpty(prsVersionMasters)) {
            log.warn(" 通过版本ID" + prjId + "查询版本敞口中间表数据不存在");
            return R.ok();
        }
        for (PrsVersionMaster pvm : prsVersionMasters) {
            PrsModelMaster prsModelMaster = prsModelMasterMapper.selectOne(new LambdaQueryWrapper<PrsModelMaster>().eq(PrsModelMaster::getModelCode, pvm.getModelCode()).eq(PrsModelMaster::getStatus, 1));
            results.add(prsModelMaster);
        }
        return R.ok(results);

    }
    /**
     * 分页查询敞口列表
     *
     * @param modelMasterVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 11:32
     */
    @Override
    public R paging(ModelMasterVo modelMasterVo) {
        Page<PrsModelMaster>pageInfo=new Page<>(modelMasterVo.getPage(),modelMasterVo.getPageSize());
        QueryWrapper<PrsModelMaster> query = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(modelMasterVo.getStatus())){
            query.lambda().eq(PrsModelMaster::getStatus,modelMasterVo.getStatus());
        }
        if (ObjectUtils.isNotEmpty(modelMasterVo.getSearchData())){
            query.and(wapper->wapper.lambda().like(PrsModelMaster::getName,modelMasterVo.getSearchData()).or().like(PrsModelMaster::getModelCode,modelMasterVo.getSearchData()));
        }
        return R.ok(prsModelMasterMapper.selectPage(pageInfo, query));
    }

    @Override
    public R openMaster(List<Integer> masterIds) {
        return updateMasterStatus(masterIds, Common.NORMAL);
    }
    @Override
    public R disableMaster(List<Integer> masterIds) {
        return updateMasterStatus(masterIds, Common.DELETE);
    }
    /**
     * 修改敞口
     *
     * @param modelMasterVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    @Override
    public R updateMaster(ModelMasterVo modelMasterVo) {
        PrsModelMaster prsModelMaster = modelMasterVo.havePrsModelMaster();
        prsModelMasterMapper.updateById(prsModelMaster);
        return R.ok(Common.UPDATE_SUCCESS);
    }
    /**
     * 新增敞口
     *
     * @param prsModelMaster
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:32
     */
    @Override
    public R addMaster(PrsModelMaster prsModelMaster) {
        PrsModelMaster lastOne = prsModelMasterMapper.selectOne(new QueryWrapper<PrsModelMaster>().lambda().orderByDesc(PrsModelMaster::getId).last(Common.SQL_LIMIT_ONE));
        Integer lastId = lastOne.getId() + 1;
        String qualCode = createMasterCode(lastId);
        prsModelMaster.setModelCode(qualCode);
        prsModelMasterMapper.insert(prsModelMaster);
        return R.ok(Common.INSERT_SUCCESS);
    }
    /**
     * 敞口映射查询
     *
     * @param centerMasterVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/17 10:48
     */
    @Override
    public R tranPaging(CenterMasterVo centerMasterVo) {
        Page<PrsModelMaster>pageInfo=new Page<>(centerMasterVo.getPage(), centerMasterVo.getPagesize());
        Integer versionId = centerMasterVo.getVersionId();
        String searchData = centerMasterVo.getSearchData();
        String dataYear = centerMasterVo.getDataYear();

        Page<PrsModelMaster> prsVersionMasterPage = prsVersionMasterMapper.selectMasterByPage(pageInfo,versionId,searchData,dataYear);
//        List<PrsModelMaster> prsVersionMasterPage = prsVersionMasterMapper.selectMasterByPage(versionId,searchData,dataYear);
        System.out.println(prsVersionMasterPage);
        return null;
    }

    /**
     * 修改敞口状态
     *
     * @param masterIds
     * @param status
     * @return R
     * @author 冉浩岑
     * @date 2022/11/14 9:48
    */
    public R updateMasterStatus(List<Integer> masterIds,Integer status) {
        PrsModelMaster prsModelMaster = new PrsModelMaster();
        prsModelMaster.setStatus(status);
        int update = prsModelMasterMapper.update(prsModelMaster, new QueryWrapper<PrsModelMaster>().lambda().in(PrsModelMaster::getId, masterIds));
        return R.ok(Common.UPDATE_SUCCESS+update+"条数据");
    }
    /**
     * 生成新的敞口Code
     *
     * @param lastId
     * @return String
     * @author 冉浩岑
     * @date 2022/11/14 15:25
     */
    public String createMasterCode(Integer lastId){
        String code=Common.MASTER_FRONT;
        int length = lastId.toString().length();
        for (int i=0;i<3-length;i++){
            code=code+0;
        }
        code=code+lastId;
        return code;
    }

    @Override
    public R queryExposureToList() {
        LambdaQueryWrapper<PrsModelMaster> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PrsModelMaster::getId,PrsModelMaster::getName,PrsModelMaster::getModelCode);
        wrapper.eq(PrsModelMaster::getStatus,1);
        List<PrsModelMaster> prsModelMasters = prsModelMasterMapper.selectList(wrapper);
        return R.ok(prsModelMasters);
    }

    /**
     * 根据类型查询敞口
     * @param type
     * @return
     */
    @Override
    public R getTypeModelMaster(Integer type) {
        LambdaQueryWrapper<PrsModelMaster> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PrsModelMaster::getId,PrsModelMaster::getName,PrsModelMaster::getModelCode)
                .eq(PrsModelMaster::getModelType,type).eq(PrsModelMaster::getStatus,1);
        List<PrsModelMaster> list = this.list(wrapper).stream().filter(f -> StringUtils.isNotEmpty(f.getName())).collect(Collectors.toList());
        List<Map<String,Object>> mapList = new ArrayList<>();
        list.forEach(f -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id", f.getId());
            //敞口编码
            map.put("modelCode", f.getModelCode());
            //敞口名称
            map.put("name", f.getName());
            mapList.add(map);
        });
        return R.ok(mapList);
    }
}
