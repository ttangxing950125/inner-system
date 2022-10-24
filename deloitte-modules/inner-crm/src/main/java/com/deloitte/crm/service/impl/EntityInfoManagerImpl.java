package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.vo.CheckVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *   ****************
 *   *    通用方法   *
 *   ****************
 *
 * @author 正杰
 * @description: EntityInfoManager
 * @date 2022/9/29
 */
@Component
@AllArgsConstructor
@Slf4j
public class EntityInfoManagerImpl implements EntityInfoManager {

    private final EntityInfoMapper entityInfoMapper;

    private final EntityNameHisMapper entityNameHisMapper;

    private final BondInfoMapper bondInfoMapper;

    private final GovInfoMapper govInfoMapper;

    private final StockCnInfoMapper stockCnInfoMapper;

    private final StockThkInfoMapper stockThkInfoMapper;



    private final String SEPARATE ="，";

    /**
     *   ******************
     *   *    修改主体名称  *
     *   ******************
     * @param entity
     * @param entityNewName
     * @param remarks
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateEntityName(EntityInfo entity,String entityNewName,String remarks) {
        //如果备注为空 自动改为系统自动生成
        if (StrUtil.isBlank(remarks)) {remarks = "系统自动生成";}
        String username = SecurityUtils.getUsername();
        if (StrUtil.isBlank(username)) {return BadInfo.VALID_EMPTY_USERNAME.getInfo();}
        if (ObjectUtils.isEmpty(entity)) {return BadInfo.VALID_EMPTY_TARGET.getInfo();}

        //修改主体曾用名 entity_name_his 时 需要用 ， 拼接
        String oldName = entity.getEntityName();
        if (!Arrays.stream(oldName.split(SEPARATE)).collect(Collectors.toMap(row -> row, row -> row)).containsKey(entityNewName)) {
            entity.setEntityNameHis(entity.getEntityNameHis() + "，" + oldName);
            //修改主体曾用名 entity_name_his_remarks 时 需要用 日期+更新人+备注;
            entity.setEntityNameHisRemarks(entity.getEntityNameHisRemarks()
                    + "\r\n"
                    + "；"
                    + DateUtil.format(new Date(),"yyyy-MM-dd")
                    + " "
                    + SecurityUtils.getUsername()
                    + " "
                    + remarks
            );
        }

        //修改主体曾用名列表以及目前名称
        UpdateWrapper<EntityInfo> entityInfoUpdateWrapper = new UpdateWrapper<>();
        entityInfoUpdateWrapper.lambda().eq(EntityInfo::getCreditCode, entity.getCreditCode())
                .set(EntityInfo::getEntityName, entityNewName)
                .set(EntityInfo::getEntityNameHis, entity.getEntityNameHis())
                .set(EntityInfo::getEntityNameHisRemarks, entity.getEntityNameHisRemarks())
                .set(EntityInfo::getUpdated, entity.getUpdated())
                .set(EntityInfo::getUpdater, username);
        entityInfoMapper.update(entity, entityInfoUpdateWrapper);

        //查询新名称是否与曾用名重复
        List<EntityNameHis> entityNameHis1 = entityNameHisMapper.selectList(new QueryWrapper<EntityNameHis>().lambda()
                .eq(EntityNameHis::getDqCode, entity.getEntityCode())
                .eq(EntityNameHis::getOldName, oldName));
        if (entityNameHis1.size() == 0) {
            //新增曾用名 entity_name_his
            EntityNameHis entityNameHis = new EntityNameHis();
            //EntityType1  => 企业主体
            entityNameHis.setEntityType(1);
            entityNameHis.setDqCode(entity.getEntityCode());
            entityNameHis.setOldName(oldName);
            entityNameHis.setRemarks(remarks);
            //Source => 1-曾用名为自动生曾
            entityNameHis.setSource(1);
            entityNameHis.setCreater(SecurityUtils.getUsername());
            entityNameHis.setCreated(new Date());
            entityNameHisMapper.insertEntityNameHis(entityNameHis);
        }

        return null;
    }


    private final String ENTITY_CODE = "ENTITY_CODE";

    private final String CREDIT_CODE = "CREDIT_CODE";

    private final String ENTITY_NAME = "ENTITY_NAME";

    private final String BOND_FULL_NAME = "BOND_FULL_NAME";

    private final String BOND_SHORT_NAME = "BOND_SHORT_NAME";

    private final String GOV_NAME = "GOV_NAME";

    private final String GOV_CODE = "GOV_CODE";

    //债券简称
    private final String BOND_CODE = "BOND_CODE";
    //A股代码
    private final String STOCK_CN_CODE = "STOCK_CN_CODE";
    //港股代码
    private final String STOCK_HK_CODE = "STOCK_HK_CODE";
    //A股简称
    private final String STOCK_A_NAME = "STOCK_A_NAME";
    //港股简称
    private final String STOCK_HK_NAME = "STOCK_HK_NAME";




    /**
     *   *****************
     *   *    匹配关键字   *
     *   *****************
     * @param keyword
     * @param target
     * @return
     */
    @Override
    public CheckVo matchByKeyword(String keyword, String target) {
        if(keyword==null){return new CheckVo().setMsg(BadInfo.PARAM_EMPTY.getInfo());}
        if(target==null||target.trim().length()==0){return new CheckVo().setMsg(BadInfo.PARAM_EMPTY.getInfo());}

        switch (keyword){
            //主体的Code
            case ENTITY_CODE:
                EntityInfo byCode = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, target));
                if(byCode==null){return new CheckVo().setMsg(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
                }else{return new CheckVo().setData(byCode).setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo());}
                //主体的统一社会信用代码
            case CREDIT_CODE:
                if(!keyword.matches(Common.REGEX_CREDIT_CODE)){return new CheckVo().setMsg(BadInfo.VALID_PARAM.getInfo());}
                EntityInfo byCreditCode = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode, target));
                if(byCreditCode==null){return new CheckVo().setMsg(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
                }else{return new CheckVo().setData(byCreditCode).setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo());}
            //主体名称
            case ENTITY_NAME:
                EntityInfo byName = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityName, target));
                if(byName==null){return new CheckVo().setMsg(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
                }else{return new CheckVo().setData(byName).setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo());}
            //债券简称
            case BOND_SHORT_NAME:
                BondInfo bondName = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondShortName, target)
                        .eq(BondInfo::getIsDeleted,Boolean.FALSE));
                if(bondName==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else{return new CheckVo().setData(bondName).setMsg(BadInfo.EXITS_BOND_CODE.getInfo());}
            //债券全称
            case BOND_FULL_NAME:
                BondInfo bondFName = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondName, target)
                        .eq(BondInfo::getIsDeleted,Boolean.FALSE));
                if(bondFName==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else{return new CheckVo().setData(bondFName).setMsg(BadInfo.EXITS_BOND_CODE.getInfo());}

            //新地方政府地方名称
            case GOV_NAME:
                GovInfo govByName = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovName, target));
                if(govByName==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else{return new CheckVo().setData(govByName).setMsg(BadInfo.EXITS_BOND_CODE.getInfo());}
            //新地方政府行政编码
            case GOV_CODE:
                GovInfo govByCode = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovCode, target));
                if(govByCode==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else{return new CheckVo().setData(govByCode).setMsg(BadInfo.EXITS_BOND_CODE.getInfo());}
                //债券代码查重
            case BOND_CODE:
                BondInfo bondOriCode = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getOriCode, target)
                        .eq(BondInfo::getIsDeleted,Boolean.FALSE)
                );
                if (bondOriCode==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else{return new CheckVo().setData(bondOriCode).setMsg(BadInfo.EXITS_BOND_CODE.getInfo());}
                //A股查重
            case STOCK_CN_CODE:
                StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockCode, target)
                        .eq(StockCnInfo::getIsDeleted,Boolean.FALSE)
                );
                if (stockCnInfo==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else {return new CheckVo().setData(stockCnInfo).setMsg(BadInfo.EXITS_STOCK_CODE.getInfo());}
                //港股查重
            case STOCK_HK_CODE:
                StockThkInfo stockTHKInfo = stockThkInfoMapper.selectOne(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockCode, target)
                        .eq(StockThkInfo::getIsDeleted,Boolean.FALSE)
                );
                if (stockTHKInfo==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else {return new CheckVo().setData(stockTHKInfo).setMsg(BadInfo.EXITS_STOCK_CODE.getInfo());}
            case STOCK_A_NAME:
                StockCnInfo stockCnInfoByName = stockCnInfoMapper.selectOne(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockShortName, target)
                        .eq(StockCnInfo::getIsDeleted,Boolean.FALSE)
                );
                if (stockCnInfoByName==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else {return new CheckVo().setData(stockCnInfoByName).setMsg(BadInfo.EXITS_STOCK_SHO_NAME.getInfo());}
            case STOCK_HK_NAME:
                StockThkInfo stockTHKInfoByName = stockThkInfoMapper.selectOne(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockName, target)
                        .eq(StockThkInfo::getIsDeleted,Boolean.FALSE)
                );
                if (stockTHKInfoByName==null){return new CheckVo().setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo());
                }else {return new CheckVo().setData(stockTHKInfoByName).setMsg(BadInfo.EXITS_STOCK_SHO_NAME.getInfo());}
            default:
                return new CheckVo().setMsg(BadInfo.PARAM_PROBABLY_BE_VALIDA.getInfo());
        }

    }
}
