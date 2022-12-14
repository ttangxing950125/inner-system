package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.EntityInfoLogsService;
import com.deloitte.crm.service.EntityInfoManager;
import io.seata.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ****************
 * *    通用方法   *
 * ****************
 *
 * @author 正杰
 * @description: EntityInfoManager
 * @date 2022/9/29
 */
@Component
@Slf4j
public class EntityInfoManagerImpl implements EntityInfoManager {

    @Resource
    private EntityInfoMapper entityInfoMapper;

    @Resource
    private EntityNameHisMapper entityNameHisMapper;

    @Resource
    private BondInfoMapper bondInfoMapper;

    @Resource
    private EntityBondRelMapper entityBondRelMapper;

    @Resource
    private GovInfoMapper govInfoMapper;

    @Resource
    private StockCnInfoMapper stockCnInfoMapper;

    @Resource
    private EntityStockCnRelMapper entityStockCnRelMapper;

    @Resource
    private StockThkInfoMapper stockThkInfoMapper;

    @Resource
    private EntityStockThkRelMapper entityStockThkRelMapper;

    @Resource
    private EntityInfoLogsService entityInfoLogsService;


    /**
     * ******************
     * *    修改主体名称  *
     * ******************
     *
     * @param entity        主体
     * @param entityNewName 主体新名称
     * @param remarks       备注信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateEntityName(EntityInfo entity, String entityNewName, String remarks) {
        //如果备注为空 自动改为  remarks = 系统自动生成
        if (StrUtil.isBlank(remarks)) {
            remarks = "系统自动生成";
        }
        String username = SecurityUtils.getUsername();
        if (ObjectUtils.isEmpty(entity)) {
            throw new ServiceException(BadInfo.EMPTY_ENTITY_INFO.getInfo());
        }

        //修改主体曾用名 entity_name_his 时 需要用 ， 拼接
        String oldName = entity.getEntityName();
        if (!Arrays.stream(oldName.split(",")).collect(Collectors.toMap(row -> row, row -> row)).containsKey(entityNewName)) {
            entity.setEntityNameHis(entity.getEntityNameHis() + "," + oldName);
            //修改主体曾用名 entity_name_his_remarks 时 需要用 日期+更新人+备注;
            entity.setEntityNameHisRemarks(entity.getEntityNameHisRemarks()
                    + "\r\n"
                    + "；"
                    + DateUtil.format(new Date(), "yyyy-MM-dd")
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

    /**
     * 主体code
     */
    private final String ENTITY_CODE = "ENTITY_CODE";
    /**
     * 统一社会信用代码
     */
    private final String CREDIT_CODE = "CREDIT_CODE";
    /**
     * 主体名称
     */
    private final String ENTITY_NAME = "ENTITY_NAME";
    /**
     * 债券全称
     */
    private final String BOND_FULL_NAME = "BOND_FULL_NAME";
    /**
     * 债券简称
     */
    private final String BOND_SHORT_NAME = "BOND_SHORT_NAME";
    /**
     * 政府名称
     */
    private final String GOV_NAME = "GOV_NAME";
    /**
     * 政府行政编码
     */
    private final String GOV_CODE = "GOV_CODE";

    /**
     * 债券代码
     */
    private final String BOND_CODE = "BOND_CODE";
    /**
     * A股代码
     */
    private final String STOCK_CN_CODE = "STOCK_CN_CODE";
    /**
     * 港股代码
     */
    private final String STOCK_HK_CODE = "STOCK_HK_CODE";
    /**
     * A股简称
     */
    private final String STOCK_A_NAME = "STOCK_A_NAME";
    /**
     * 港股简称
     */
    private final String STOCK_HK_NAME = "STOCK_HK_NAME";


    /**
     * *****************
     * *    匹配关键字   *
     * *****************
     *
     * @param keyword 需要校验的字段类型
     * @param target  校验的字段
     * @return 匹配是否有效
     */
    @Override
    public R matchByKeyword(String keyword, String target) {
        if (StringUtils.isEmpty(keyword)) {
            return R.fail(BadInfo.PARAM_EMPTY.getInfo());
        }
        if (target == null || target.trim().length() == 0) {
            return R.ok(null, BadInfo.PARAM_EMPTY.getInfo());
        }
        switch (keyword) {
            //主体的Code
            case ENTITY_CODE:
                EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, target));
                return Optional.ofNullable(entityInfo).map(r -> (R.fail(null, BadInfo.EXITS_ENTITY_CODE.getInfo()))).orElseGet(() -> R.ok(entityInfo, SuccessInfo.ENABLE_CREAT_ENTITY.getInfo()));
            //主体的统一社会信用代码
            case CREDIT_CODE:
                if (!target.matches(Common.REGEX_CREDIT_CODE)) {
                    return R.fail(BadInfo.VALID_PARAM.getInfo());
                }
                EntityInfo byCreditCode = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode, target));
                return Optional.ofNullable(byCreditCode).map(e -> (R.fail(null, BadInfo.EXITS_ENTITY_CODE.getInfo()))).orElseGet(() -> R.ok(byCreditCode, SuccessInfo.ENABLE_CREAT_ENTITY.getInfo()));
            //主体名称
            case ENTITY_NAME:
                EntityInfo info = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityName, target));
                return Optional.ofNullable(info).map(e -> (R.fail(null, BadInfo.EXITS_ENTITY_CODE.getInfo()))).orElseGet(() -> R.ok(info, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            //债券简称
            case BOND_SHORT_NAME:
                BondInfo bondName = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondShortName, target).eq(BondInfo::getIsDeleted, Boolean.FALSE));
                return Optional.ofNullable(bondName).map(e -> (R.fail(null, BadInfo.EXITS_BOND_SHORT_NAME.getInfo()))).orElseGet(() -> R.ok(bondName, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            //债券全称
            case BOND_FULL_NAME:
                BondInfo bondFullName = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondName, target).eq(BondInfo::getIsDeleted, Boolean.FALSE));
                Optional<BondInfo> bondFullName1 = Optional.ofNullable(bondFullName);

                Optional<R<Object>> objectR = bondFullName1.map(e -> R.fail(null, BadInfo.EXITS_BOND_FULL_NAME.getInfo()));

                R<Object> objectR1 = objectR.orElseGet(() -> R.ok(bondFullName, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
                return objectR1;
            //新地方政府地方名称
            case GOV_NAME:
                GovInfo govByName = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovName, target));
                return Optional.ofNullable(govByName).map(e -> (R.fail(null, BadInfo.EXITS_GOV_NAME.getInfo()))).orElseGet(() -> R.ok(govByName, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            //新地方政府行政编码
            case GOV_CODE:
                if (!target.matches(Common.REGEX_GOV_CODE)) {
                    return R.fail(BadInfo.PARAM_GOV_VALIDA.getInfo());
                }
                GovInfo govByCode = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovCode, target));
                return Optional.ofNullable(govByCode).map(e -> (R.fail(null, BadInfo.EXITS_GOV_CODE.getInfo()))).orElseGet(() -> R.ok(govByCode, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            //债券代码查重
            case BOND_CODE:
                BondInfo bondOriCode = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getOriCode, target).eq(BondInfo::getIsDeleted, Boolean.FALSE));
                return Optional.ofNullable(bondOriCode).map(e -> (R.fail(null, BadInfo.EXITS_BOND_CODE.getInfo()))).orElseGet(() -> R.ok(bondOriCode, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            //A股查重
            case STOCK_CN_CODE:
                StockCnInfo stockCnInfo = stockCnInfoMapper.selectOne(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockCode, target).eq(StockCnInfo::getIsDeleted, Boolean.FALSE));
                return Optional.ofNullable(stockCnInfo).map(e -> (R.fail(null, BadInfo.EXITS_STOCK_CODE.getInfo()))).orElseGet(() -> R.ok(stockCnInfo, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            //港股查重
            case STOCK_HK_CODE:
                StockThkInfo stockThkInfo = stockThkInfoMapper.selectOne(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockCode, target).eq(StockThkInfo::getIsDeleted, Boolean.FALSE));
                return Optional.ofNullable(stockThkInfo).map(e -> (R.fail(null, BadInfo.EXITS_STOCK_CODE.getInfo()))).orElseGet(() -> R.ok(stockThkInfo, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            case STOCK_A_NAME:
                StockCnInfo stockCnInfoByName = stockCnInfoMapper.selectOne(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockShortName, target).eq(StockCnInfo::getIsDeleted, Boolean.FALSE));
                return Optional.ofNullable(stockCnInfoByName).map(e -> (R.fail(null, BadInfo.EXITS_STOCK_SHO_NAME.getInfo()))).orElseGet(() -> R.ok(stockCnInfoByName, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            case STOCK_HK_NAME:
                StockThkInfo stockThkInfoByName = stockThkInfoMapper.selectOne(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockName, target).eq(StockThkInfo::getIsDeleted, Boolean.FALSE));
                return Optional.ofNullable(stockThkInfoByName).map(e -> (R.fail(null, BadInfo.EXITS_STOCK_SHO_NAME.getInfo()))).orElseGet(() -> R.ok(stockThkInfoByName, SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
            default:
                return R.ok(null, BadInfo.PARAM_PROBABLY_BE_VALIDA.getInfo());
        }

    }

    /**
     * *****************
     * *   绑定关联关系  *
     * *****************
     *
     * @param entityInfoInsertDTO
     * @param entityCode          如果是新增主体 那么 entityInfoInsertDOT 中的 entityCode 就为空
     * @param username
     */
    @Override
    public void bindData(EntityInfoInsertDTO entityInfoInsertDTO, String entityCode, String username) {
        if (!ObjectUtils.isEmpty(entityInfoInsertDTO.getEntityCode())) {
            entityCode = entityInfoInsertDTO.getEntityCode();
        }
        log.info("  =>> 角色7 主体 {} 主体其余信息导入 <<=  ", entityInfoInsertDTO.getEntityName());
        if (StrUtil.isNotBlank(entityInfoInsertDTO.getDataCode())) {
            switch (entityInfoInsertDTO.getDataSource()) {
                case 1:
                    this.bindBondInfo(entityInfoInsertDTO, entityCode, username);
                    break;
                case 2:
                    this.bindStockThkInfo(entityInfoInsertDTO, entityCode, username);
                    break;
                case 3:
                    this.bindStockCnInfo(entityInfoInsertDTO, entityCode, username);
                    break;
                default:
                    throw new RuntimeException(BadInfo.COULD_NOT_FIND_SOURCE.getInfo());
            }
        }
    }

    /**
     * 绑定 债券 bond_info
     */
    @Transactional(rollbackFor = Exception.class)
    void bindBondInfo(EntityInfoInsertDTO entityInfoInsertDTO, String entityCode, String username) {
        log.info("  =>> 关联信息导入 bond_info 与 entity_bond_rel <<=  ");
        String dataCode = entityInfoInsertDTO.getDataCode();
        BondInfo bondInfo = Optional.ofNullable(bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondCode, dataCode).eq(BondInfo::getIsDeleted, 0))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_BOND_INFO.getInfo()));
        if (ObjectUtils.isEmpty(entityBondRelMapper.selectOne(new QueryWrapper<EntityBondRel>().lambda().eq(EntityBondRel::getEntityCode, entityCode).eq(EntityBondRel::getBdCode, dataCode)))) {
            entityBondRelMapper.insert(new EntityBondRel().setEntityCode(entityCode).setBdCode(dataCode).setStatus(1));
            log.info("  =>> 成功新增一条关联信息 至entity_bond_rel <<== ");
        } else {
            log.info("  =>> 库中已存有一条关联数据 至entity_bond_rel <<== ");
        }

        String oriCode = bondInfo.getOriCode();
        String bondShortName = bondInfo.getBondShortName();
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs().setDeCode(dataCode).setCode(oriCode).setName(bondShortName).setEntityCode(entityCode).setCreateTime(new Date()).setOperType(3).setEntityName(entityInfoInsertDTO.getEntityName()).setOperName(username).setSource(3);
        this.bindEntityInfoLogs(entityInfoLogs);
    }

    /**
     * 绑定 港股 stock_thk_info
     */
    @Transactional(rollbackFor = Exception.class)
    void bindStockThkInfo(EntityInfoInsertDTO entityInfoInsertDTO, String entityCode, String username) {
        log.info("  =>> 主体导入 stock_thk_info 与 entity_stock_thk_rel <<=  ");
        String dataCode = entityInfoInsertDTO.getDataCode();
        StockThkInfo stockThkInfo = Optional.ofNullable(stockThkInfoMapper.selectOne(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockDqCode, dataCode).eq(StockThkInfo::getIsDeleted, 0))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_CN_STOCK_INFO.getInfo()));
        if (ObjectUtils.isEmpty(entityStockThkRelMapper.selectOne(new QueryWrapper<EntityStockThkRel>().lambda().eq(EntityStockThkRel::getEntityCode, entityCode).eq(EntityStockThkRel::getStockDqCode, dataCode)))) {
            entityStockThkRelMapper.insert(new EntityStockThkRel().setEntityCode(entityCode).setStockDqCode(dataCode).setStatus(true));
            log.info("  =>> 成功新增一条关联信息 entity_stock_thk_rel <<== ");
        } else {
            log.info("  =>> 库中已存有一条关联数据 entity_stock_thk_rel <<== ");
        }

        String stockCode = stockThkInfo.getStockCode();
        String stockName = stockThkInfo.getStockName();
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs().setDeCode(dataCode).setCode(stockCode).setName(stockName).setEntityCode(entityCode).setOperType(2).setCreateTime(new Date()).setEntityName(entityInfoInsertDTO.getEntityName()).setOperName(username).setSource(3);
        this.bindEntityInfoLogs(entityInfoLogs);
    }

    /**
     * 绑定 A股 stock_cn_info
     */
    @Transactional(rollbackFor = Exception.class)
    void bindStockCnInfo(EntityInfoInsertDTO entityInfoInsertDTO, String entityCode, String username) {
        log.info("  =>> 主体导入 stock_cn_info 与 entity_stock_cn_rel <<=  ");
        String dataCode = entityInfoInsertDTO.getDataCode();
        StockCnInfo stockCnInfo = Optional.ofNullable(stockCnInfoMapper.selectOne(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockDqCode, dataCode).eq(StockCnInfo::getIsDeleted, 0))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_CN_STOCK_INFO.getInfo()));
        if (ObjectUtils.isEmpty(entityStockCnRelMapper.selectOne(new QueryWrapper<EntityStockCnRel>().lambda().eq(EntityStockCnRel::getEntityCode, entityCode).eq(EntityStockCnRel::getStockDqCode, dataCode)))) {
            entityStockCnRelMapper.insert(new EntityStockCnRel().setEntityCode(entityCode).setStockDqCode(dataCode).setStatus(true));
            log.info("  =>> 成功新增一条关联信息 entity_stock_cn_rel <<== ");
        } else {
            log.info("  =>> 库中已存有一条关联数据 entity_stock_cn_rel <<== ");
        }

        String stockCode = stockCnInfo.getStockCode();
        String stockShortName = stockCnInfo.getStockShortName();
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs().setDeCode(dataCode).setCode(stockCode).setName(stockShortName).setEntityCode(entityCode).setOperType(1).setCreateTime(new Date()).setEntityName(entityInfoInsertDTO.getEntityName()).setOperName(username).setSource(3);
        this.bindEntityInfoLogs(entityInfoLogs);
    }

    /**
     * 新增一条 entity_info_logs 的数据
     */
    @Transactional(rollbackFor = Exception.class)
    void bindEntityInfoLogs(EntityInfoLogs entityInfoLogs) {
        log.info("  =>> 新增一条信息至 entity_info_logs <<=  ");
        entityInfoLogsService.getBaseMapper().insert(entityInfoLogs);
    }

}
