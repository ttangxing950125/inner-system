package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.data.platform.mapper.SysDictDataMapper;
import com.deloitte.data.platform.service.ISysDictDataService;
import com.deloitte.data.platform.domian.SysDictData;
import com.deloitte.data.platform.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典 业务层处理
 * 
 * @author lipeng
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper,SysDictData> implements ISysDictDataService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }
   /**
    *查询crm角色
    *
    * @return List<SysDictData>
    * @author penTang
    * @date 2022/9/22 19:00
   */
   @Override
    public List<SysDictData> selectDictDataListByType(){

        return list(new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getDictType,"task_role_type")
        );

    }


    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes)
    {
        for (Long dictCode : dictCodes)
        {
            SysDictData data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData data)
    {
        int row = dictDataMapper.insertDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData data)
    {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     *查询产品年份
     *
     * @return List<String>
     * @author penTang
     * @date 2022/10/17 12:52
    */
    @Override
    public List<String> getDictData(){
        LambdaQueryWrapper<SysDictData> qw = new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, "Products_master_rel");
        ArrayList<String> dataYearList = new ArrayList<>();
        List<SysDictData> list = list(qw);
        list.forEach(o->{
            dataYearList.add(o.getDictValue());
        });
        return dataYearList;
    }

}