package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.data.platform.domian.DataPlatformMyLibraryTable;
import com.deloitte.data.platform.dto.MyLibraryTableDto;
import com.deloitte.data.platform.mapper.DataPlatformMyLibraryTableMapper;
import com.deloitte.data.platform.service.IDataPlatformMyLibraryTableService;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;
import com.deloitte.system.api.model.LoginUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据平台我的库表 服务实现类
 *
 * @author fangliu
 * @date 2022/11/23 17:40:39
 */
@Service
public class DataPlatformMyLibraryTableServiceImpl extends ServiceImpl<DataPlatformMyLibraryTableMapper, DataPlatformMyLibraryTable> implements IDataPlatformMyLibraryTableService {

    @Override
    public void addMyLibraryTable(MyLibraryTableDto dto) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        LambdaQueryWrapper<DataPlatformMyLibraryTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataPlatformMyLibraryTable::getUserId,loginUser.getUserid());
        queryWrapper.eq(DataPlatformMyLibraryTable::getCode,dto.getCode());
        DataPlatformMyLibraryTable one = this.getOne(queryWrapper);
        if (one==null){
            DataPlatformMyLibraryTable dataPlatformMyLibraryTable = new DataPlatformMyLibraryTable();
            dataPlatformMyLibraryTable.setCode(dto.getCode());
            dataPlatformMyLibraryTable.setParentCode(dto.getParentCode());
            dataPlatformMyLibraryTable.setUserId(loginUser.getUserid());
            this.save(dataPlatformMyLibraryTable);
        }
        MyLibraryTableDto child = dto.getChild();
        if (child!=null){
            this.addMyLibraryTable(child);
        }
    }

    @Override
    public void deleteMyLibraryTable(Integer id) {
        this.removeById(id);
    }

    @Override
    public DataPlatformMenuVo getMyLibraryTable(){
//        LoginUser loginUser = SecurityUtils.getLoginUser();
        return this.baseMapper.getMyLibraryTable(12);
    }
}
