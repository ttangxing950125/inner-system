package com.deloitte.crm.service.impl;

import java.util.List;
import java.util.Map;

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

    private IEntityAttrValueService iEntityAttrValueService;

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
        Map<String, AttrValueMapDto> attrs = modelMasterInfoVo.getAttrs();

        CrmMasTask crmMasTask = iCrmMasTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmMasTask>().lambda().eq(CrmMasTask::getId, id));
        Assert.notNull(crmMasTask, BadInfo.VALID_EMPTY_TARGET.getInfo());
        modelMasterInfoVo.setCrmMasTask(crmMasTask);

        String entityCode = crmMasTask.getEntityCode();
        EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode));
        Assert.notNull(entityInfo, BadInfo.VALID_EMPTY_TARGET.getInfo());

        //TODO 参数组装
        //企业主体
        modelMasterInfoVo.setEntityInfo(entityInfo);

        //wind行业划分
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,652));

        //申万行业划分
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,650));

        //是否为金融机构
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,640));

        //TODO 金融细分领域
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,656));

        //是否为城投机构（YY)
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,644));

        //中诚信-是否为城投机构
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,645));

        // IB-是否为城投机构
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,642));

        //TODO 机构对应城投政府
        attrs.put("机构对应城投政府",new AttrValueMapDto().setName("机构对应城投政府"));

        //TODO 机构对应城投政府行政代码
        attrs.put("机构对应城投政府行政代码",new AttrValueMapDto().setName("机构对应城投政府行政代码"));

        //TODO 机构对应城投政府德勤内部代码
        attrs.put("机构对应城投政府德勤内部代码",new AttrValueMapDto().setName("机构对应城投政府德勤内部代码"));

        modelMasterInfoVo.setAttrs(attrs);
        return R.ok(modelMasterInfoVo);
    }
}
