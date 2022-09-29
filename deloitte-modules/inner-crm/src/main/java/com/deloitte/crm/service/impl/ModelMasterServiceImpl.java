package com.deloitte.crm.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.service.ICrmMasTaskService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.ModelMasterInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.ModelMasterMapper;
import com.deloitte.crm.domain.ModelMaster;
import com.deloitte.crm.service.IModelMasterService;
import org.springframework.util.Assert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class ModelMasterServiceImpl implements IModelMasterService 
{
    private ModelMasterMapper modelMasterMapper;

    private ICrmMasTaskService iCrmMasTaskService;

    private IEntityInfoService iEntityInfoService;

    private EntityAttrValueMapper entityAttrValueMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ModelMaster selectModelMasterById(Long id)
    {
        return modelMasterMapper.selectModelMasterById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ModelMaster> selectModelMasterList(ModelMaster modelMaster)
    {
        return modelMasterMapper.selectModelMasterList(modelMaster);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertModelMaster(ModelMaster modelMaster)
    {
        return modelMasterMapper.insertModelMaster(modelMaster);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateModelMaster(ModelMaster modelMaster)
    {
        return modelMasterMapper.updateModelMaster(modelMaster);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterByIds(Long[] ids)
    {
        return modelMasterMapper.deleteModelMasterByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterById(Long id)
    {
        return modelMasterMapper.deleteModelMasterById(id);
    }


    /**
     * 敞口划分 选中单行开始工作 传入id后返回窗口 by正杰
     * @param id
     * @return
     */
    @Override
    public R<ModelMasterInfoVo> getTable(Integer id) {
        ModelMasterInfoVo modelMasterInfoVo = new ModelMasterInfoVo();
        List<AttrValueMapDto> list = modelMasterInfoVo.getList();

        CrmMasTask crmMasTask = iCrmMasTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmMasTask>().lambda().eq(CrmMasTask::getId, id));
        Assert.notNull(crmMasTask, BadInfo.VALID_EMPTY_TARGET.getInfo());

        String entityCode = crmMasTask.getEntityCode();
        EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode));
        Assert.notNull(entityInfo, BadInfo.VALID_EMPTY_TARGET.getInfo());

        Integer entityId = entityInfo.getId();

        //TODO 参数组装
        //企业名称
        list.add(new AttrValueMapDto(entityId, Common.TABLE_ENTITY_INFO,"企业名称",entityInfo.getEntityName()));

        //统一社会信用代码
        list.add(new AttrValueMapDto(entityId, Common.TABLE_ENTITY_INFO,"统一社会信用代码",entityInfo.getCreditCode()));

        //来源
        list.add(new AttrValueMapDto(id, Common.TABLE_CRM_MAS_TASK,"统一社会信用代码",entityInfo.getCreditCode()));

        //wind行业划分
        EntityAttrValue wind = entityAttrValueMapper.findValueByCodeAndAttrId(652, entityCode);
        if(wind!=null){
            list.add(new AttrValueMapDto(wind.getId(),Common.ENTITY_ATTR_VALUE,"wind行业划分",wind.getValue(),"wind口径下行业划分"));
        }else{list.add(new AttrValueMapDto().setName("wind行业划分"));}

        //申万行业划分
        EntityAttrValue shenWind = entityAttrValueMapper.findValueByCodeAndAttrId(650, entityCode);
        if(shenWind!=null){
            list.add(new AttrValueMapDto(shenWind.getId(),Common.ENTITY_ATTR_VALUE,"申万行业划分",shenWind.getValue(),"申万(2021)口径下行业划分"));
        }else{list.add(new AttrValueMapDto().setName("申万行业划分"));}

        //是否为金融机构
        EntityAttrValue finance = entityAttrValueMapper.findValueByCodeAndAttrId(640, entityCode);
        if(finance!=null){
            list.add(new AttrValueMapDto(finance.getId(),Common.ENTITY_ATTR_VALUE,"是否为金融机构",finance.getValue(),"1、Y：若在已有目前名单中存在，即为金融机构\n" +
                    "2、N：若在已有目前名单中不存在，即为非名单判断金融机构"));
        }else{list.add(new AttrValueMapDto().setName("是否为金融机构"));}

        //TODO 金融细分领域
        EntityAttrValue financeDetail = entityAttrValueMapper.findValueByCodeAndAttrId(656, entityCode);
        if(financeDetail!=null){
            list.add(new AttrValueMapDto(financeDetail.getId(),Common.ENTITY_ATTR_VALUE,"金融细分领域",financeDetail.getValue(),"1、Y：若在已有目前名单中存在，即为金融机构\n" +
                    "2、N：若在已有目前名单中不存在，即为非名单判断金融机构"));
        }else{list.add(new AttrValueMapDto().setName("金融细分领域"));}

        //是否为城投机构（YY)
        EntityAttrValue city = entityAttrValueMapper.findValueByCodeAndAttrId(644, entityCode);
        if(city!=null){
            list.add(new AttrValueMapDto(city.getId(),Common.ENTITY_ATTR_VALUE,"是否为城投机构（YY）",city.getValue(),"1、Y：是YY口径下城投机构\n" +
                    "2、N：不是YY口径下城投机构"));
        }else{list.add(new AttrValueMapDto().setName("是否为城投机构（YY"));}

        //中诚信-是否为城投机构
        EntityAttrValue cityZhong = entityAttrValueMapper.findValueByCodeAndAttrId(645, entityCode);
        if(cityZhong!=null){
            list.add(new AttrValueMapDto(cityZhong.getId(),Common.ENTITY_ATTR_VALUE,"中诚信-是否为城投机构",cityZhong.getValue(),"1、Y：是中诚信口径下城投机构\n" +
                    "2、N：不是中诚信口径下城投机构"));
        }else{list.add(new AttrValueMapDto().setName("中诚信-是否为城投机构"));}

        // IB-是否为城投机构
        EntityAttrValue cityIB = entityAttrValueMapper.findValueByCodeAndAttrId(642, entityCode);
        if(cityIB!=null){
            list.add(new AttrValueMapDto(cityIB.getId(),Common.ENTITY_ATTR_VALUE,"IB-是否为城投机构",cityIB.getValue(),"1、Y：是YY口径下城投机构\n" +
                    "2、N：不是YY口径下城投机构"));
        }else{list.add(new AttrValueMapDto().setName("IB-是否为城投机构"));}

        //TODO 机构对应城投政府
        list.add(new AttrValueMapDto().setName("机构对应城投政府"));

        //TODO 机构对应城投政府行政代码
        EntityAttrValue govCityCode = entityAttrValueMapper.findValueByCodeAndAttrId(642, entityCode);
        if(govCityCode!=null){
            list.add(new AttrValueMapDto(govCityCode.getId(),Common.ENTITY_ATTR_VALUE,"机构对应城投政府行政代码",govCityCode.getValue(),"1、Y：是YY口径下城投机构\n" +
                    "2、N：不是YY口径下城投机构"));
        }else{list.add(new AttrValueMapDto().setName("机构对应城投政府行政代码"));}

        //TODO 机构对应城投政府德勤内部代码
        EntityAttrValue govIBCode = entityAttrValueMapper.findValueByCodeAndAttrId(642, entityCode);
        if(govIBCode!=null){
            list.add(new AttrValueMapDto(govIBCode.getId(),Common.ENTITY_ATTR_VALUE,"机构对应城投政府德勤内部代码",govIBCode.getValue(),"1、Y：是YY口径下城投机构\n" +
                    "2、N：不是YY口径下城投机构"));
        }else{list.add(new AttrValueMapDto().setName("机构对应城投政府德勤内部代码"));}

        list.add(new AttrValueMapDto());
        modelMasterInfoVo.setList(list);
        return R.ok(modelMasterInfoVo);
    }
}
