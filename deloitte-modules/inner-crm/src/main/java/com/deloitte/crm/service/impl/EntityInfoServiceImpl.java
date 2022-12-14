package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.cell.CellUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.domain.dto.*;
import com.deloitte.crm.dto.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.*;
import com.deloitte.crm.utils.TimeFormatUtil;
import com.deloitte.crm.utils.excel.ExcelUtils;
import com.deloitte.crm.vo.*;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * ???????????????????????????Service???????????????
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@Slf4j
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper, EntityInfo> implements IEntityInfoService {

    @Resource
    private EntityStockCnRelMapper cnRelMapper;

    @Resource
    private EntityStockThkRelMapper thkRelMapper;

    @Resource
    private EntityFinancialMapper financialMapper;

    @Resource
    private ICrmSupplyTaskService crmSupplyTaskService;

    @Resource
    private EntityBaseBusiInfoMapper entityBaseBusiInfoMapper;

    @Resource
    private ProductsMasterRelMapper productsMasterRelMapper;

    @Resource
    private StockCnInfoMapper stockCnMapper;

    @Resource
    private StockThkInfoMapper stockThkMapper;

    @Resource
    private ProductsMasterDictMapper productsMasterDictMapper;

    @Resource
    private EntityInfoMapper entityInfoMapper;

    @Resource
    private EntityNameHisMapper nameHisMapper;

    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private EntityBondRelMapper entityBondRelMapper;

    @Resource
    private GovInfoMapper govInfoMapper;

    @Resource
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    @Resource
    private BondInfoMapper bondInfoMapper;

    @Resource
    private ProductsCoverMapper productsCoverMapper;

    @Resource
    private ProductsMapper productsMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private EntityInfoLogsUpdatedService entityInfoLogsUpdatedService;

    @Resource
    private EntityGovRelMapper entityGovRelMapper;

    @Resource
    private EntityMasterMapper entityMasterMapper;

    @Resource
    private ICrmDailyTaskService crmDailyTaskService;

    @Resource
    private EntityCaptureSpeedService entityCaptureSpeedService;

    @Resource
    private EntityInfoLogsService entityInfoLogsService;

    @Resource
    private EntityNameHisMapper entityNameHisMapper;

    /**
     * ??????
     */
    public static final String ENTITY = "ENTITY";

    /**
     * ??????
     */
    public static final String BOND = "BOND";

    /**
     * ???????????????
     */
    private static final String NO_PAGENUM_ERROR = "???????????????";

    /**
     * ????????????size
     */
    private static final Integer DEFAULT_PAGESIZE = 9;

    /**
     * ????????????
     */
    private static final Integer DEFAULT_PAGENUM = 1;

    /**
     * ???????????????
     */
    private static final String REPET_OLD_NAME = "?????????????????????????????????";

    /**
     * ??????????????????  2-??????
     */
    private static final Integer ENTITY_INFO_TYPE = 2;

    /**
     * ??????????????????  2-????????????????????????
     */
    private static final Integer OLD_NAME_FROM_MAN = 2;

    /**
     * ???????????? ABS
     */
    public static final String BOND_TYPE_ABS = "ABS";

    /**
     * ???????????? COLL
     */
    public static final String BOND_TYPE_COLL = "?????????";

    /**
     * ???????????? public
     */
    public static final String BOND_TYPE_PUBLIC = "?????????";

    /**
     * ???????????? private
     */
    public static final String BOND_TYPE_PRIVATE = "?????????";

    /**
     * ???????????? ??????
     */
    public static final String BOND_STATE_LIVE = "(??????)";

    /**
     * ???????????? ?????????
     */
    public static final String BOND_STATE_BACK = "(?????????)";

    /**
     * ???????????? ??????
     */
    public static final String BOND_STATE_DEAD = "(??????)";

    /**
     * ?????????
     */
    public static final String DEAD_STATE = "N";

    /**
     * ????????????????????????
     *
     * @return List<EntityInfoDto>
     * @author penTang
     * @date 2022/9/22 22:40
     */
    @Override
    public EntityInfoDto getEntityInfo() {

        EntityInfoDto entityInfoDto = new EntityInfoDto();
        LambdaQueryWrapper<EntityInfo> eq = new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getStatus, 1);
        List<EntityInfo> list = this.list(eq);
        //issue_bonds ???????????? 0-????????? 1-?????????
        List<EntityInfo> bonds = list.stream()
                .filter(row -> row.getIssueBonds() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //finance ??????????????????
        List<EntityInfo> finance = list.stream()
                .filter(row -> row.getFinance() != null && row.getFinance() == 1)
                .collect(Collectors.toList());

        //list ???????????? 0-????????? 1-?????????
        List<EntityInfo> entityInfoList = list.stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .collect(Collectors.toList());

        //????????????????????????
        List<EntityInfo> listAndBonds = list.stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .filter(row -> row.getIssueBonds() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //!????????????????????????
        List<EntityInfo> notListAndBonds = list.stream()
                .filter(row -> row.getList() != null && row.getList() == 0)
                .filter(row -> row.getIssueBonds() != null && row.getIssueBonds() == 0)
                .collect(Collectors.toList());
        entityInfoDto.setIssueBonds(bonds.size());
        entityInfoDto.setEntitySum(list.size());
        entityInfoDto.setNotBondsAndList(notListAndBonds.size());
        entityInfoDto.setList(entityInfoList.size());
        entityInfoDto.setBondsAndList(listAndBonds.size());
        entityInfoDto.setFinance(finance.size());

        return entityInfoDto;

    }

    /**
     * ?????????????????????????????????
     *
     * @param id ?????????????????????????????????
     * @return ???????????????????????????
     */
    @Override
    public EntityInfo selectEntityInfoById(Long id) {
        return entityInfoMapper.selectEntityInfoById(id);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param entityInfo ???????????????????????????
     * @return ???????????????????????????
     */
    @Override
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo) {
        return entityInfoMapper.selectEntityInfoList(entityInfo);
    }

    @Override
    public void exportEntity(HttpServletResponse response) {
        log.info("  >>>>  ?????????????????????????????? <<<<  ");
        List<EntityInfo> entityInfoList = entityInfoMapper.selectList(new QueryWrapper<EntityInfo>());
        if (CollectionUtils.isEmpty(entityInfoList)) {
            return;
        }
        // ???????????????
        List<List<Object>> sheetDataList = new ArrayList<>();
        List<Object> head = Arrays.asList("????????????", "???????????????????????????", "????????????????????????", "???????????? 0.??? 1.???",
                "?????????????????? 0.??? 1.???", "???????????? 0.??? 1.???", "???????????? 0.??? 1.???", "???????????????????????????????????? 0-?????? 1-??????",
                "??????????????????????????????", "???????????????????????????????????????1????????? 2????????? 3???????????????????????? 4????????????????????? 5?????????", "???????????????????????????");
        sheetDataList.add(head);
        for (EntityInfo entityInfo : entityInfoList) {
            //???????????????
            List<Object> sheetData = new ArrayList<>();
            sheetData.add(entityInfo.getEntityName());
            sheetData.add(entityInfo.getEntityCode());
            sheetData.add(entityInfo.getCreditCode());
            sheetData.add(entityInfo.getList());
            sheetData.add(entityInfo.getFinance());
            sheetData.add(entityInfo.getIssueBonds());
            sheetData.add(entityInfo.getStatus());
            sheetData.add(entityInfo.getCreditError());
            sheetData.add(entityInfo.getCreditErrorRemark());
            sheetData.add(entityInfo.getCreditErrorType());
            sheetData.add(entityInfo.getEntityNameHis());

            //???????????????
            sheetDataList.add(sheetData);
        }
        // ????????????
        ExcelUtils.export(response, "???????????????", sheetDataList);
        log.info("???????????????????????????");
    }

    /**
     * ???????????? entity_info_logs ?????????
     */
    @Transactional(rollbackFor = Exception.class)
    void bindEntityInfoLogs(EntityInfoLogs entityInfoLogs) {
        log.info("  =>> ????????????????????? entity_info_logs <<=  ");
        entityInfoLogsService.getBaseMapper().insert(entityInfoLogs);
    }


    /**
     * ?????????????????????????????????
     *
     * @param entityInfo ???????????????????????????
     * @return ??????
     */
    @Override
    public int updateEntityInfo(EntityInfo entityInfo) {
        return entityInfoMapper.updateEntityInfo(entityInfo);
    }


    /**
     * ?????????????????????????????????
     *
     * @param entityInfo ???????????????????????????
     * @return ??????
     */
    @Override
    public long updateEntityInfoByEntityCodeWithOutId(EntityInfo entityInfo) {
        log.info("  >>>> ??????????????????entityCode????????????,id?????????,entityCode=[{}] <<<<  ", entityInfo.getEntityCode());
        //???????????????????????????????????????
        entityInfo.setId(null);
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        return entityInfoMapper.update(entityInfo, queryWrapper.lambda().eq(EntityInfo::getEntityCode, entityInfo.getEntityCode()));

    }

    /**
     * ???????????????????????????????????????
     *
     * @param ids ????????????????????????????????????????????????
     * @return ??????
     */
    @Override
    public int deleteEntityInfoByIds(Long[] ids) {
        return entityInfoMapper.deleteEntityInfoByIds(ids);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param id ?????????????????????????????????
     * @return ??????
     */
    @Override
    public int deleteEntityInfoById(Long id) {
        return entityInfoMapper.deleteEntityInfoById(id);
    }

    /**
     * ????????????????????????
     *
     * @param entityName
     * @return
     */
    @Override
    public List<EntityInfo> findByName(String entityName) {
        entityName = entityName.trim().replace("???","(").replace("???",")");

        return baseMapper.findByName(entityName);
    }

    /**
     * ????????????????????????(?????? ??????????????????????????????????????????????????? )
     *
     * @param entityName
     * @return
     * @author ?????????ppp
     */
    @Override
    public List<EntityInfo> findByNameOrOldName(String entityName) {
        entityName = entityName.trim().replace("???","(").replace("???",")");

        //??????name??????
        List<EntityInfo> byName = this.findByName(entityName);
        if(CollUtil.isNotEmpty(byName)){
            return byName;
        }

        //???????????????
        List<EntityNameHis> entityNameHis = entityNameHisMapper.findByOldName(entityName);

        List<String> entityCodes = entityNameHis.stream().map(EntityNameHis::getDqCode).collect(Collectors.toList());

        LambdaQueryWrapper<EntityInfo> codeWrapper = Wrappers.<EntityInfo>lambdaQuery()
                .in(EntityInfo::getEntityCode, entityCodes);



        return this.list(codeWrapper);
    }

    @Override
    public List<EntityInfo> findByNames(List<String> entityNames) {
        Wrapper<EntityInfo> wapper = Wrappers.<EntityInfo>lambdaQuery()
                .eq(EntityInfo::getEntityName, entityNames);

        return baseMapper.selectList(wapper);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R addOldName(EntityInfo entity) {
        log.info("  >>>> ??????????????????????????????,entity=[{}] <<<<  ", entity);
        //???????????????????????????
        EntityInfo entityInfo = entityInfoMapper.selectById(entity.getId());
        //???????????????????????????
        String entityCode = entityInfo.getEntityCode();
        QueryWrapper<EntityNameHis> queryWrapper = new QueryWrapper<>();
        String nameHis = entity.getEntityNameHis();
        Long count = nameHisMapper.selectCount(queryWrapper.lambda()
                .eq(EntityNameHis::getDqCode, entityCode)
                .eq(EntityNameHis::getOldName, nameHis));
        if (count > 0) {
            return R.fail(REPET_OLD_NAME);
        }
        //??????????????????
        String remoter = SecurityUtils.getUsername();
        //?????????????????????
        String entityNameHis = entityInfo.getEntityNameHis();
        if (ObjectUtils.isEmpty(entityNameHis)) {
            entityInfo.setEntityNameHis(entity.getEntityNameHis());
        } else {
            entityInfo.setEntityNameHis(entityNameHis + "," + entity.getEntityNameHis());
        }
        String nameHisRemarks = entity.getEntityNameHisRemarks();
        String remarks = "";
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            remarks = TimeFormatUtil.getFormartDate(new Date()) + " " + remoter + " "+Common.CREATE_AUTO;
        } else {
            remarks = TimeFormatUtil.getFormartDate(new Date()) + " " + remoter + " " + nameHisRemarks;
        }

        String oldRemarks = entityInfo.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(oldRemarks)) {
            oldRemarks = remarks;
        } else {
            oldRemarks = oldRemarks + ";" + remarks;
        }
        EntityInfo backInfo = new EntityInfo();
        backInfo.setId(entityInfo.getId()).setEntityNameHisRemarks(oldRemarks).setEntityNameHis(entityInfo.getEntityNameHis());
        entityInfoMapper.updateById(backInfo);

        //????????????????????????
        EntityNameHis newNameHis = new EntityNameHis();
        newNameHis.setDqCode(entityInfo.getEntityCode());
        newNameHis.setOldName(entity.getEntityNameHis());
        newNameHis.setEntityType(1);
        newNameHis.setHappenDate(new Date());
        newNameHis.setRemarks(entity.getEntityNameHisRemarks());
        newNameHis.setSource(1);
        nameHisMapper.insert(newNameHis);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateOldName(String dqCode, String oldName, String newOldName, String status, String remark) {
        log.info("  >>>> ??????,??????????????????????????????,dqCode=[{}],oldName=[{}],newOldName=[{}],status=[{}],remarks=[{}] <<<<  ", dqCode, oldName, newOldName, status, remark);
        if (ObjectUtils.isEmpty(oldName)) {
            return R.fail("????????????????????????");
        }
        if (ObjectUtils.isEmpty(status)) {
            if (ObjectUtils.isEmpty(newOldName)) {
                return R.fail("?????????????????????");
            }
            //?????????????????????????????????????????????
            Long count = nameHisMapper.selectCount(new QueryWrapper<EntityNameHis>().lambda()
                    .eq(EntityNameHis::getDqCode, dqCode)
                    .eq(EntityNameHis::getOldName, newOldName));
            if (count > 0) {
                return R.ok("?????????????????????????????????");
            }
        }
        //??????dqCode???????????????
        QueryWrapper<EntityInfo> infoQuery = new QueryWrapper<>();
        EntityInfo entityInfo = entityInfoMapper.selectOne(infoQuery.lambda().eq(EntityInfo::getEntityCode, dqCode));

        //?????????????????????
        String entityNameHis = entityInfo.getEntityNameHis();
        String entityNameHisRemarks = entityInfo.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(entityNameHis)) {
            return R.fail("???????????????");
        }
        List<String> nameList = Arrays.asList(entityNameHis.split(","));
        List<String> remarkList = Arrays.asList(entityNameHisRemarks.split(";"));

        String newNameResult = "";
        String newRemarkResult = "";
        for (int i = 0; i < nameList.size(); i++) {
            String remarkVo = "";
            String nameVo = "";
            String hisName = nameList.get(i);

            //??????????????????????????????????????????
            if (oldName.equals(hisName)) {
                //?????????????????????????????????????????????
                EntityNameHis one = nameHisMapper.selectOne(new QueryWrapper<EntityNameHis>().lambda()
                        .eq(EntityNameHis::getDqCode, dqCode)
                        .eq(EntityNameHis::getOldName, oldName).last(Common.SQL_LIMIT_1));
                if (ObjectUtils.isEmpty(one)) {
                    continue;
                }
                one.setSource(2);
                // status ?????????????????????????????????
                if (!ObjectUtils.isEmpty(status)) {
                    one.setStatus(0);
                    //???????????????????????????
                    nameHisMapper.updateById(one);
                    continue;
                }
                //???????????????
                if (ObjectUtils.isEmpty(remark)) {
                    remark = Common.CREATE_AUTO;
                }
                one.setRemarks(remark).setOldName(newOldName);
                //?????????????????????
                nameHisMapper.updateById(one);
                nameVo = newOldName;
                remarkVo = TimeFormatUtil.getFormartDate(new Date()) + " " + SecurityUtils.getUsername() + " " + remark;
                if (ObjectUtils.isEmpty(remark)) {
                    remarkVo = remarkVo + Common.CREATE_AUTO;
                }
            } else {
                nameVo = hisName;
                if (remarkList.size() > i) {
                    remarkVo = remarkList.get(i);
                } else {
                    remarkVo = TimeFormatUtil.getFormartDate(new Date()) + " " + SecurityUtils.getUsername() + " "+Common.CREATE_AUTO;
                }
            }
            // ??????????????????????????????
            if (ObjectUtils.isEmpty(newNameResult)) {
                newNameResult = nameVo;
            } else {
                newNameResult = newNameResult + "," + nameVo;
            }
            if (ObjectUtils.isEmpty(newRemarkResult)) {
                newRemarkResult = remarkVo;
            } else {
                newRemarkResult = newRemarkResult + ";" + remarkVo;
            }
        }
        EntityInfo backInfo = new EntityInfo();
        //???????????????????????????
        backInfo.setId(entityInfo.getId()).setEntityNameHis(newNameResult).setEntityNameHisRemarks(newRemarkResult);
        //??????????????????
        entityInfoMapper.updateById(backInfo);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R getInfoDetailByEntityCode(String entityCode) {
        log.info("  >>>> ????????????-????????????-?????? entityCode ????????????????????????,entityCode=[{}] <<<<  ", entityCode);

        EntityInfoDetails entityInfoDetails = new EntityInfoDetails();

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper.lambda().eq(EntityInfo::getEntityCode, entityCode));

        if (CollectionUtils.isEmpty(entityInfos)) {
            return R.fail("???????????????????????????");
        }
        if (entityInfos.size() > 1) {
            return R.fail("????????????????????????????????????????????????");
        }
        // ??????????????????
        EntityInfo entityInfo = entityInfos.get(0);
        entityInfoDetails.setEntityInfo(entityInfo);
        // ??????????????????  --  A?????????????????????  A???????????????????????????
        //?????? A??? ????????????
        entityInfoDetails = getStockCnInfo(entityInfoDetails, entityCode);

        //?????? ?????? ????????????
        entityInfoDetails = getStockThkInfo(entityInfoDetails, entityCode);

        // ??????????????????
        entityInfoDetails = getEntityBondInfo(entityInfoDetails, entityCode);

        // ??????????????????
        entityInfoDetails = getEntityFinancials(entityInfoDetails, entityCode, entityInfo);

        //TODO ??????????????????  --  ??????????????????????????????  ?????????CICS??????????????????  ?????????????????????
        entityInfoDetails = getProductsMaster(entityInfoDetails, entityCode);

        //????????????????????????
        entityInfoDetails = getCoverageDetail(entityInfoDetails, entityCode);

        //TODO ????????????????????????  --  ??????
        EntityBaseBusiInfo baseBusiInfo = entityBaseBusiInfoService.getInfoByEntityCode(entityCode);

        entityInfoDetails.setEntityBaseBusiInfo(baseBusiInfo);
        return R.ok(entityInfoDetails);
    }

    private EntityInfoDetails getCoverageDetail(EntityInfoDetails entityInfoDetails, String entityCode) {

        Map<Integer, List<ProductsCover>> collect = new HashMap<>();

        //????????????????????????
        List<ProductsCover> productsCovers = productsCoverMapper.selectList(new QueryWrapper<ProductsCover>().lambda().eq(ProductsCover::getEntityCode, entityCode));
        if (!CollectionUtils.isEmpty(productsCovers)) {
            collect = productsCovers.stream().collect(Collectors.groupingBy(ProductsCover::getProId));
        }

        //????????????????????????
        List<ProCoverVo> coverVos = new ArrayList<>();

        List<Products> products = productsMapper.selectList(new QueryWrapper<>());

        Map<Integer, List<ProductsCover>> finalCollect = collect;
        products.stream().forEach(o -> {
            String proName = o.getProName();
            List<ProductsCover> covers = new ArrayList<>();
            if (!finalCollect.isEmpty()) {
                covers = finalCollect.get(o.getId());
            }
            ProCoverVo proCoverVo = new ProCoverVo();
            //????????????????????????
            NameValueVo isCover = new NameValueVo();
            //????????????????????????
            NameValueVo coverReason = new NameValueVo();
            //??????????????????
            isCover.setName(proName + "????????????");
            coverReason.setName(proName + "???????????????");
            isCover.setValue("0");
            //?????????
            if (!CollectionUtils.isEmpty(covers)) {
                String cover = covers.get(0).getIsCover();
                if (!ObjectUtils.isEmpty(cover)) {
                    isCover.setValue(cover);
                    if ("0".equals(cover)) {
                        coverReason.setValue(covers.get(0).getCoverDes());
                    }
                }
            }
            proCoverVo.setIsCover(isCover).setCoverReason(coverReason);
            coverVos.add(proCoverVo);
        });
        entityInfoDetails.setCoverageDetail(coverVos);
        return entityInfoDetails;
    }

    private EntityInfoDetails getProductsMaster(EntityInfoDetails entityInfoDetails, String entityCode) {
        //??????????????????
        List<ProductsMasterRel> productsMasterRels = productsMasterRelMapper.selectList(new QueryWrapper<ProductsMasterRel>().lambda().eq(ProductsMasterRel::getEntityCode, entityCode));
        if (CollectionUtils.isEmpty(productsMasterRels)) {
            return entityInfoDetails;
        }
        //????????????id????????????
        Set<Integer> masterIds = productsMasterRels.stream().collect(Collectors.groupingBy(ProductsMasterRel::getProMasDictId)).keySet();
        List<ProductsMasterDict> productsMasterDicts = productsMasterDictMapper.selectList(new QueryWrapper<ProductsMasterDict>().lambda().in(ProductsMasterDict::getId, masterIds));
        List<String> masterName = new ArrayList<>();
        productsMasterDicts.stream().forEach(o -> masterName.add(o.getMasterName()));
        //??????????????????
        entityInfoDetails.setMasterNames(masterName);
        return entityInfoDetails;
    }

    private EntityInfoDetails getEntityFinancials(EntityInfoDetails entityInfoDetails, String entityCode, EntityInfo entityInfo) {

        if (ObjectUtil.isEmpty(entityInfo.getFinance()) || entityInfo.getFinance() == 0) {
            return entityInfoDetails;
        }
        //?????????????????????
        List<EntityFinancial> list = financialMapper.selectList(new QueryWrapper<EntityFinancial>().lambda().eq(EntityFinancial::getEntityCode, entityCode));
        if (!CollectionUtils.isEmpty(list)) {
            entityInfoDetails.setEntityFinancial(list.get(0));
        }
        return entityInfoDetails;
    }

    private EntityInfoDetails getEntityBondInfo(EntityInfoDetails entityInfoDetails, String entityCode) {
        BondInfoDetail bondInfoDetail = new BondInfoDetail();
        //???????????????
        List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda().eq(EntityBondRel::getEntityCode, entityCode));
        //??????????????????????????? -- ??????????????????
        if (CollectionUtils.isEmpty(entityBondRels)) {
            bondInfoDetail.setIsBond(false);
            return entityInfoDetails;
        }
        bondInfoDetail.setIsBond(true);
        //??????????????????
        List<String> bdCodes = new ArrayList<>();
        try {
            entityBondRels.stream().forEach(o -> bdCodes.add(o.getBdCode()));
            List<BondInfo> bondInfos = bondInfoMapper.selectList(new QueryWrapper<BondInfo>().lambda().in(BondInfo::getBondCode, bdCodes).orderByAsc(BondInfo::getValueDate));

            //??????????????????
            List<BondInfo> liveBonds = bondInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getValueDate())).collect(Collectors.toList());
            bondInfoDetail.setFirstBond(liveBonds.get(0).getValueDate());

            //?????????????????????
            // ?????????????????????
            // ?????????????????????
            try {
                List<BondInfo> collList = bondInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getColl()) && o.getColl()).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(collList)) {
                    bondInfoDetail.setIsCollBond(false).setCollBondsNum(0).setCollBondsLiveNum(0);
                } else {
                    //???????????????????????????
                    List<String> shortNameList = new ArrayList<>();
                    collList.stream().forEach(x -> shortNameList.add(x.getBondShortName()));
                    //?????????????????????
                    bondInfoDetail.setIsCollBond(true)
                            .setCollBondsNum(collList.size())
                            .setCollBonds(shortNameList)
                            .setCollBondsLiveNum(collList.stream().filter(o -> o.getBondState() == 0).collect(Collectors.toList()).size());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            //??????ABS??????
            try {
                List<BondInfo> absList = bondInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getAbs()) && o.getAbs()).collect(Collectors.toList());
                // ????????????ABS
                // ??????ABS??????
                if (CollectionUtils.isEmpty(absList)) {
                    bondInfoDetail.setIsAbsBond(false).setAbsBondsNum(0).setAbsBondsLiveNum(0);
                } else {
                    //??????ABS????????????
                    List<String> shortNameList = new ArrayList<>();
                    absList.stream().forEach(x -> shortNameList.add(x.getBondShortName()));
                    //??????ABS??????
                    bondInfoDetail.setIsAbsBond(true)
                            .setAbsBondsNum(absList.size())
                            .setAbsBonds(shortNameList)
                            .setAbsBondsLiveNum(absList.stream().filter(o -> o.getBondState() == 0).collect(Collectors.toList()).size());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            //?????????????????????
            try {
                List<BondInfo> publicList = bondInfos.stream().filter(o -> !ObjectUtils.isEmpty(o.getRaiseType()) && o.getRaiseType() == 0).collect(Collectors.toList());
                // ?????????????????????
                // ?????????????????????
                if (CollectionUtils.isEmpty(publicList)) {
                    bondInfoDetail.setIsPublicBond(false).setPublicBondsNum(0).setPublicBondsLiveNum(0);
                } else {
                    //???????????????????????????
                    List<String> shortNameList = new ArrayList<>();
                    publicList.stream().forEach(x -> shortNameList.add(x.getBondShortName()));
                    //?????????????????????
                    bondInfoDetail.setIsPublicBond(true)
                            .setPublicBondsNum(publicList.size())
                            .setPublicBonds(shortNameList)
                            .setPublicBondsLiveNum(publicList.stream().filter(o -> o.getBondState() == 0).collect(Collectors.toList()).size());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            //?????????????????????
            try {
                List<BondInfo> privateList = bondInfos.stream().filter(o -> !ObjectUtils.isEmpty(o.getRaiseType()) && o.getRaiseType() == 1).collect(Collectors.toList());
                // ?????????????????????
                // ?????????????????????
                if (CollectionUtils.isEmpty(privateList)) {
                    bondInfoDetail.setIsPrivateBond(false).setPrivateBondsNum(0).setPrivateBondsLiveNum(0);
                } else {
                    //???????????????????????????
                    List<String> shortNameList = new ArrayList<>();
                    privateList.stream().forEach(x -> shortNameList.add(x.getBondShortName()));
                    //?????????????????????
                    bondInfoDetail.setIsPrivateBond(true)
                            .setPrivateBondsNum(privateList.size())
                            .setPrivateBonds(shortNameList)
                            .setPrivateBondsLiveNum(privateList.stream().filter(o -> o.getBondState() == 0).collect(Collectors.toList()).size());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            //?????????????????? attrId 686

            bondInfoDetail.setIsCollection("0");
            EntityAttrValue attrValue = entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>().lambda()
                    .eq(EntityAttrValue::getEntityCode, entityCode)
                    .eq(EntityAttrValue::getAttrId, 686).last(Common.SQL_LIMIT_1));
            if (!ObjectUtils.isEmpty(attrValue)) {
                bondInfoDetail.setIsCollection(attrValue.getValue());
            }

            //????????????
            entityInfoDetails.setBondInfoDetail(bondInfoDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entityInfoDetails;
    }

    private EntityInfoDetails getStockThkInfo(EntityInfoDetails entityInfoDetails, String entityCode) {
        //???????????????
        List<EntityStockThkRel> entityStockThkRels = thkRelMapper.selectList(new QueryWrapper<EntityStockThkRel>().lambda().eq(EntityStockThkRel::getEntityCode, entityCode));
        if (CollectionUtils.isEmpty(entityStockThkRels)) {
            return entityInfoDetails;
        }
        //????????????????????????
        List<String> thkRelCodes = new ArrayList<>();
        try {
            entityStockThkRels.stream().forEach(o -> thkRelCodes.add(o.getStockDqCode()));
            List<StockThkInfo> stockThkInfos = stockThkMapper.selectList(new QueryWrapper<StockThkInfo>().lambda().in(StockThkInfo::getStockDqCode, thkRelCodes).orderByDesc(StockThkInfo::getListDate));
            if (!CollectionUtils.isEmpty(stockThkInfos)) {
                //????????????????????????
                if (stockThkInfos.size() > 1) {
                    //????????????????????????
                    List<String> nameHis = new ArrayList<>();
                    for (int i = 1; i < stockThkInfos.size(); i++) {
                        nameHis.add(stockThkInfos.get(i).getStockName());
                    }
                    entityInfoDetails.setShortNameHisG(nameHis);
                }
                //??????????????????????????????
                StockThkInfo stockThkInfo = stockThkInfos.get(0);
                //?????? ??? ???????????????
                String type = entityInfoDetails.getListType();
                if (ObjectUtils.isEmpty(type)) {
                    entityInfoDetails.setListType("??????");
                } else {
                    entityInfoDetails.setListType(entityInfoDetails.getListType() + "/??????");
                }
                //???????????? 3-????????????
                Integer stockStatus = stockThkInfo.getStockStatus();
                if (stockStatus == 3) {
                    entityInfoDetails.setListTypeG(Common.LIVE);
                } else {
                    entityInfoDetails.setListTypeG(Common.DOWN);
                }
                //??????????????????
                entityInfoDetails.setStockThkInfo(stockThkInfo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entityInfoDetails;
    }

    private EntityInfoDetails getStockCnInfo(EntityInfoDetails entityInfoDetails, String entityCode) {
        //???????????????
        List<EntityStockCnRel> entityStockCnRels = cnRelMapper.selectList(new QueryWrapper<EntityStockCnRel>().lambda().eq(EntityStockCnRel::getEntityCode, entityCode));
        if (CollectionUtils.isEmpty(entityStockCnRels)) {
            return entityInfoDetails;
        }
        //??????A???????????????
        List<String> cnRelCodes = new ArrayList<>();
        try {
            entityStockCnRels.stream().forEach(o -> cnRelCodes.add(o.getStockDqCode()));
            List<StockCnInfo> stockCnInfos = stockCnMapper.selectList(new QueryWrapper<StockCnInfo>().lambda().in(StockCnInfo::getStockDqCode, cnRelCodes).orderByDesc(StockCnInfo::getListDate));
            //??????????????????????????????????????????
            if (!CollectionUtils.isEmpty(stockCnInfos)) {
                if (stockCnInfos.size() > 1) {
                    //??????A???????????????
                    List<String> nameHis = new ArrayList<>();
                    for (int i = 1; i < stockCnInfos.size(); i++) {
                        nameHis.add(stockCnInfos.get(i).getStockShortName());
                    }
                    entityInfoDetails.setShortNameHisA(nameHis);
                }
                //??????????????????
                entityInfoDetails.setListType("A???");
                //???????????????????????????
                StockCnInfo stockCnInfo = stockCnInfos.get(0);
                Integer status = stockCnInfo.getStockStatus();
                //A????????? 6-????????????
                if (status == 6) {
                    entityInfoDetails.setListTypeA(Common.LIVE);
                } else {
                    entityInfoDetails.setListTypeA(Common.NOT_ISS);
                }
                //??????A?????????
                entityInfoDetails.setStockCnInfo(stockCnInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entityInfoDetails;
    }

    @Autowired
    private EntityBaseBusiInfoService entityBaseBusiInfoService;

    /**
     * ??????????????????code??????
     *
     * @param entityCodes
     * @return
     */
    @Override
    public List<EntityInfo> findListByEntityCodes(List<String> entityCodes) {
        Wrapper<EntityInfo> wrapper = Wrappers.<EntityInfo>lambdaQuery()
                .in(EntityInfo::getEntityCode, entityCodes);

        return this.list(wrapper);
    }

    @Override
    public R getInfoList(Integer type, String param, Integer pageNum, Integer pageSize) {
        log.info("  >>>> ????????????????????????,type=[{}] <<<<  ", type);
        return R.ok(getInfoListByType(type, param, pageNum, pageSize));
    }

    public Page<EntityInfoList> getInfoListByType(Integer type, String param, Integer pageNum, Integer pageSize) {
//        EntityInfo entityInfo = new EntityInfo();
//        ?????????????????? 1????????? 2????????? 3???????????????????????? 4???????????????
        if (ObjectUtil.isEmpty(pageNum)) {
            pageNum = DEFAULT_PAGENUM;
        }
        if (ObjectUtil.isEmpty(pageSize)) {
            pageSize = DEFAULT_PAGESIZE;
        }
        if (ObjectUtil.isEmpty(param)) {
            param = "";
        }
        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);

        QueryWrapper<EntityInfo> infoQuery = new QueryWrapper<>();
        if (ObjectUtils.isEmpty(type)) {
            type = 1;
        }
        if (type == 1) {
            infoQuery.lambda().eq(EntityInfo::getEntityStockTag, 1);
        } else if (type == 2) {
            infoQuery.lambda().eq(EntityInfo::getEntityBondTag, 1);
        } else if (type == 3) {
            infoQuery.lambda().eq(EntityInfo::getEntityStockTag, 0).eq(EntityInfo::getEntityBondTag, 0);
        } else if (type == 4) {
            infoQuery.lambda().eq(EntityInfo::getFinance, 1);
        }
        if (!ObjectUtils.isEmpty(param)) {
            String finalParam = param;
            infoQuery.and(query -> query.lambda().like(EntityInfo::getEntityCode, finalParam).or().like(EntityInfo::getEntityName, finalParam));
            if (type == 1) {
                infoQuery.lambda().eq(EntityInfo::getEntityStockTag, 1);
            } else if (type == 2) {
                infoQuery.lambda().eq(EntityInfo::getEntityBondTag, 1);
            } else if (type == 3) {
                infoQuery.and(query -> query.lambda().eq(EntityInfo::getEntityStockTag, 0).or().isNull(EntityInfo::getEntityStockTag));
                infoQuery.and(query -> query.lambda().eq(EntityInfo::getEntityBondTag, 0).or().isNull(EntityInfo::getEntityBondTag));
            } else if (type == 4) {
                infoQuery.lambda().eq(EntityInfo::getFinance, 1);
            }
        }
        Page<EntityInfo> page = entityInfoMapper.selectPage(pageInfo, infoQuery);
        List<EntityInfo> entityInfoList = page.getRecords();
        Page<EntityInfoList> resultPage = new Page<>(pageNum, pageSize);
        if (CollectionUtils.isEmpty(entityInfoList)) {
            return resultPage;
        }
        //???????????????
        List<EntityInfoList> records = new ArrayList<>();
        //??????????????????
        resultPage.setTotal(page.getTotal()).setCurrent(page.getCurrent());
        List<EntityInfoList> finalRecords = records;
        //????????????????????????
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        List<EntityNameHis> nameHisList = nameHisMapper.selectList(hisQuery.lambda().eq(EntityNameHis::getEntityType, 1));
        Map<String, List<EntityNameHis>> hisNameListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(nameHisList)) {
            hisNameListMap = nameHisList.stream().collect(Collectors.groupingBy(EntityNameHis::getDqCode));
        }
        Map<String, List<EntityNameHis>> finalHisNameListMap = hisNameListMap;
        Integer finalType = type;
        List<String> codeList = new ArrayList<>();
        entityInfoList.stream().forEach(o -> codeList.add(o.getEntityCode()));
        entityInfoList.stream().forEach(o -> {
            finalRecords.add(getResultMap(o, finalHisNameListMap, finalType));
        });
        records = finalRecords;
        //?????????????????????----?????????????????????????????????????????????????????????  1
        if (type == 1) {
            records = getListSpecial(records, codeList);
        }
        //?????????????????????----??????????????? ???????????????  2
        else if (type == 2) {
            records = getIssSpecial(records, codeList);
        }
        //???????????????????????????----????????????  4  TODO
        else if (type == 4) {
            records = getFinSpecial(records, codeList);
        }
        resultPage.setRecords(records);
        return resultPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateInfoList(List<EntityInfo> list) {
        log.info("  >>>> ???????????????????????????list=[{}] <<<<  ", list);
        list.stream().forEach(o -> {
            EntityInfo entityInfo = entityInfoMapper.selectById(o.getId());
            entityInfoMapper.updateById(o);
            //???????????????????????????????????????????????????
            if (!ObjectUtils.isEmpty(o.getEntityName())) {
                String oldName = entityInfo.getEntityNameHis();
                EntityInfo addOldName = new EntityInfo();
                addOldName.setId(o.getId()).setEntityNameHis(oldName).setEntityNameHisRemarks(o.getEntityNameHisRemarks()).setUpdated(new Date());
                addOldName(addOldName);
            }
            //?????????????????????????????????????????????????????????????????????????????????
            if (!ObjectUtils.isEmpty(o.getEntityCode())) {
                String oldDqCode = entityInfo.getEntityCode();
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                EntityNameHis nameHis = new EntityNameHis();
                nameHis.setDqCode(o.getEntityCode());
                nameHisMapper.update(nameHis, wrapper.lambda().eq(EntityNameHis::getDqCode, oldDqCode));
            }
        });
        return list.size();
    }

    @Override
    public List<EntityInfo> checkEntity(EntityInfo entityInfo) {
        log.info("  >>>> ????????????????????????????????????????????????,entityInfo=[{}] <<<<  ", entityInfo);
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper(entityInfo);
        return entityInfoMapper.selectList(queryWrapper);
    }

    @Override
    public Object getListEntityByPage(EntityAttrByDto entityAttrDto) {

        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        log.info("  >>>>  ????????????-????????????,??????????????????,mapList=[{}] <<<<  ", entityAttrDto.getMapList());
        if (ObjectUtils.isEmpty(pageNum) && ObjectUtils.isEmpty(pageSize)) {
            return getListEntityAll(entityAttrDto);
        } else {
            return getListEntityPage(entityAttrDto);
        }
    }

    /**
     * ?????????????????????????????? ??????????????????
     *
     * @param creditCode
     * @return
     */
    @Override
    public EntityInfo getEntityInfoByCreditCode(String creditCode) {
        return baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode, creditCode));
    }

    /**
     * ????????????
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author ?????????
     * @date 2022/9/25 17:04
     */
    @Override
    public List<EntityInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto) {
        //??????????????????
        List<MoreIndex> mapList = entityAttrDto.getMapList();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> newMapList = mapList.stream().filter(o -> !ObjectUtils.isEmpty(o.getId())).collect(Collectors.toList());
            entityAttrDto.setMapList(newMapList);
        }
        mapList = entityAttrDto.getMapList();
        //0.???????????? 1.????????????
        Integer raiseType = null;

        Integer privateType = entityAttrDto.getPrivateType();
        Integer publicType = entityAttrDto.getPublicType();
        if (!ObjectUtils.isEmpty(publicType) && !ObjectUtils.isEmpty(privateType)) {
            raiseType = null;
        } else if (!ObjectUtils.isEmpty(publicType)) {
            raiseType = 0;
        } else if (!ObjectUtils.isEmpty(privateType)) {
            raiseType = 1;
        }

        //???????????????abs
        Integer abs = entityAttrDto.getAbs();
        //????????????????????????
        Integer coll = entityAttrDto.getColl();

        //?????????????????????
        Integer stockThk = entityAttrDto.getStockThk();
        //???????????????A???
        Integer stockCn = entityAttrDto.getStockCn();
        //?????????????????????
        List<EntityInfoResult> resultRecords = new ArrayList<>();

        List<EntityInfo> entityInfos = entityInfoMapper.getEntityByBondType(raiseType, abs, coll, stockThk, stockCn);
        if (CollectionUtils.isEmpty(entityInfos)){
            return resultRecords;
        }
//        entityInfos.stream().forEach(o -> {
//            EntityInfoResult entityInfoResult = getEntityInfoResult(o, mapList);
//            resultRecords.add(entityInfoResult);
//        });
        resultRecords=getEntityInfoResultNew(entityInfos,mapList,resultRecords);
        return resultRecords;
    }

    /**
     * ????????????????????????
     *
     * @return void
     * @author penTang
     * @date 2022/9/25 21:19
     */
    @Override
    public void ExportEntityInFor(EntityAttrByDto entityAttrDto) {
        List<EntityInfoResult> listEntityAll = this.getListEntityAll(entityAttrDto);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        AtomicInteger serialNumber = new AtomicInteger();
        listEntityAll.forEach(vo -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            EntityInfo info = vo.getEntityInfo();
            map.put("??????", serialNumber.incrementAndGet());
            map.put("??????????????????", info.getEntityCode());
            map.put("????????????", info.getEntityName());
            map.put("????????????", info.getStatus());
            map.put("?????????????????????", info.getCreditCode());
            map.put("????????????", DateUtil.parseDateToStr("yyyy/MM/dd", info.getCreated()));
            map.put("?????????", info.getCreater());
            if (!CollectionUtils.isEmpty(vo.getMore())) {
                vo.getMore().forEach(entryMap -> map.put(entryMap.getKey(), entryMap.getValue()));
            }
            rows.add(map);
        });
        //??????????????????????????????????????????
        writer.write(rows, true);
        // ???????????????
        Sheet sheet = writer.getSheet();
        // ??????????????????
        for (int columnIndex = 0; columnIndex < writer.getColumnCount(); columnIndex++) {
            int width = sheet.getColumnWidth(columnIndex) / 256;
            // ??????????????????
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (Objects.isNull(currentRow)) {
                    currentRow = sheet.createRow(rowIndex);
                }
                Cell currentCell = currentRow.getCell(columnIndex);
                if (Objects.isNull(currentCell)) {
                    continue;
                } else if (currentCell.getCellType() == CellType.STRING) {
                    int length = currentCell.getStringCellValue().getBytes().length;
                    width = width < length ? length : width;
                }
            }
            sheet.setColumnWidth(columnIndex, width * 256);
        }
        // response???HttpServletResponse??????
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("????????????", "UTF-8")) + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();

        }
        // ??????writer???????????????
        writer.close();
        IoUtil.close(out);

    }


    /**
     * ????????????
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author ?????????
     * @date 2022/9/25 17:04
     */
    public Page<EntityInfoResult> getListEntityPage(EntityAttrByDto entityAttrDto) {
        //??????????????????
        List<MoreIndex> mapList = entityAttrDto.getMapList();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> newMapList = mapList.stream().filter(o -> !ObjectUtils.isEmpty(o.getId())).collect(Collectors.toList());
            entityAttrDto.setMapList(newMapList);
        }
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        Integer raiseType = null;

        Integer privateType = entityAttrDto.getPrivateType();
        Integer publicType = entityAttrDto.getPublicType();
        if (!ObjectUtils.isEmpty(publicType) && !ObjectUtils.isEmpty(privateType)) {
            raiseType = null;
        } else if (!ObjectUtils.isEmpty(publicType)) {
            raiseType = 0;
        } else if (!ObjectUtils.isEmpty(privateType)) {
            raiseType = 1;
        }
        //???????????????abs
        Integer abs = entityAttrDto.getAbs();
        //????????????????????????
        Integer coll = entityAttrDto.getColl();

        //?????????????????????
        Integer stockThk = entityAttrDto.getStockThk();
        //???????????????A???
        Integer stockCn = entityAttrDto.getStockCn();

        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 9;
        }
        Page<EntityInfoResult> pageResult = new Page<>(pageNum, pageSize);
        //?????????????????????
        List<EntityInfoResult> resultRecords = new ArrayList<>();
        mapList = entityAttrDto.getMapList();

        Integer count = entityInfoMapper.getEntityCountByBondType(raiseType, abs, coll, stockThk, stockCn);
        pageNum = (pageNum - 1) * pageSize;
        List<EntityInfo> entityInfos = entityInfoMapper.getEntityByBondTypeByPage(raiseType, abs, coll, pageNum, pageSize, stockThk, stockCn);
        pageResult.setTotal(count);
        if (CollectionUtils.isEmpty(entityInfos)){
            return pageResult;
        }

//        records.stream().forEach(o -> {
//            EntityInfoResult entityInfoResult = getEntityInfoResult(o, finalMapList);
//            resultRecords.add(entityInfoResult);
//        });

        resultRecords=getEntityInfoResultNew(entityInfos,mapList,resultRecords);

        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    private  List<EntityInfoResult> getEntityInfoResultNew(List<EntityInfo> entityInfos,List<MoreIndex> mapList, List<EntityInfoResult> resultRecords) {
        List<String>idList=new ArrayList<>();
        List<String>header=new ArrayList<>();
        List<String>codeList=new ArrayList<>();
        entityInfos.forEach(o->codeList.add(o.getEntityCode()));
        QueryWrapper<EntityAttrValue> query = new QueryWrapper<>();
        //??????????????????????????????
        List<EntityAttrValue> attrValueList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(codeList)){
            query.lambda().in(EntityAttrValue::getEntityCode,codeList );
        }
        mapList.forEach(o->{
            header.add(o.getName());
            idList.add(o.getId());
        });
        //??????????????????????????????????????????
        if (!CollectionUtils.isEmpty(idList)){
            query.lambda().in(EntityAttrValue::getAttrId,idList );
            attrValueList = entityAttrValueMapper.selectList(query);
        }
        Map<String, List<EntityAttrValue>> entityCodeMap =new HashMap<>();
        if (!CollectionUtils.isEmpty(attrValueList)){
            entityCodeMap = attrValueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getEntityCode));
        }
        Map<String, List<EntityAttrValue>> finalEntityCodeMap = entityCodeMap;

        entityInfos.forEach(info->{
            List<MoreIndex> more=new ArrayList<>();
            List<String> values=new ArrayList<>();//????????????????????????????????????????????????
            if (!CollectionUtils.isEmpty(mapList)){
                mapList.forEach(o->{
                    MoreIndex moreIndex = new MoreIndex();
                    moreIndex.setName(o.getName()).setId(o.getId()).setKey(o.getName());
                    String value="";
                    //??????????????????????????????????????????
                    if (!ObjectUtils.isEmpty(finalEntityCodeMap)){
                        List<EntityAttrValue> valueList = finalEntityCodeMap.get(info.getEntityCode());
                        if (!ObjectUtils.isEmpty(valueList)){
                            Map<Long, List<EntityAttrValue>> attrValuesById = valueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getAttrId));
                            List<EntityAttrValue> attrValuesByAttrId = attrValuesById.get(Long.valueOf(o.getId()));
                            if (!ObjectUtils.isEmpty(attrValuesByAttrId)){
                                value = attrValuesByAttrId.get(0).getValue();
                            }
                        }
                    }
                    moreIndex.setValue(value);
                    values.add(value);
                    more.add(moreIndex);
                });
            }
            EntityInfoResult entityInfoResult =new EntityInfoResult();
            entityInfoResult.setEntityInfo(info).setHeader(header).setValues(values).setMore(more);
            resultRecords.add(entityInfoResult);
        });
        return resultRecords;
    }

    private EntityInfoResult getEntityInfoResult(EntityInfo o, List<MoreIndex> mapList) {
        EntityInfoResult entityInfoResult = new EntityInfoResult();
        entityInfoResult.setEntityInfo(o);
        List<String> header = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> more = new ArrayList<>();
            for (MoreIndex moreIndex : mapList) {
                moreIndex.setKey(moreIndex.getName());
                header.add(moreIndex.getName());

                QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                List<EntityAttrValue> attrValueList = entityAttrValueMapper.selectList(valueQuer.lambda()
                        .eq(EntityAttrValue::getAttrId, moreIndex.getId())
                        .eq(EntityAttrValue::getEntityCode, o.getEntityCode()));
                String value = "";
                //???????????????
                if (!CollectionUtils.isEmpty(attrValueList)) {
                    for (EntityAttrValue attrValue : attrValueList) {
                        if (ObjectUtils.isEmpty(value)) {
                            value = attrValue.getValue();
                        } else {
                            value = value + "," + attrValue.getValue();
                        }
                    }
                    moreIndex.setValue(value);
                }
                values.add(value);
                more.add(moreIndex);
            }
            entityInfoResult.setMore(more).setHeader(header).setValues(values);
        }
        return entityInfoResult;
    }

    /**
     * ??????????????????????????????
     *
     * @param mapList
     * @return List<Map < String, Object>>
     * @author ?????????
     * @date 2022/9/26 8:37
     */
    public List<Map<String, Object>> getEntityAttrValue(List<Map<String, String>> mapList, EntityInfo o) {
        List<Map<String, Object>> more = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
            EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                    .eq(EntityAttrValue::getAttrId, map.get(EntityUtils.MORE_ENTITY_KPI_ID))
                    .eq(EntityAttrValue::getEntityCode, o.getEntityCode()));

            Map<String, Object> moreMap = new HashMap<>();
            //???????????????
            moreMap.put(EntityUtils.MORE_ENTITY_KPI_KEY, map.get(EntityUtils.MORE_ENTITY_KPI_NAME));
            if (ObjectUtils.isEmpty(attrValue)) {
                moreMap.put(EntityUtils.MORE_ENTITY_KPI_VALUE, null);
            } else {
                moreMap.put(EntityUtils.MORE_ENTITY_KPI_VALUE, attrValue.getValue());
            }
            more.add(moreMap);
        }
        return more;
    }

    /**
     * EntityInfo ????????? map,????????? ???????????????
     *
     * @param entityInfo
     * @return Map<String, Object>
     * @author ?????????
     * @date 2022/9/22 23:45
     */
    public EntityInfoList getResultMap(EntityInfo entityInfo, Map<String, List<EntityNameHis>> map, Integer type) {
        EntityInfoList entityInfoList = new EntityInfoList();
        entityInfoList.setLiveState("N");
        if (null != entityInfo) {
            entityInfoList.setEntityInfo(entityInfo);
            try {
                Integer count = 0;
                if (!map.isEmpty()) {
                    List<EntityNameHis> nameHisList = map.get(entityInfo.getEntityCode());
                    if (!CollectionUtils.isEmpty(nameHisList)) {
                        count = nameHisList.stream().filter(o->Objects.equals(o.getStatus(),1)).collect(Collectors.toList()).size();
                    }
                }
                entityInfoList.setNameUsedNum(count);
            } catch (Exception e) {
                return entityInfoList;
            }
        }
        entityInfoList.newUpdateRecord(entityInfoList);
        return entityInfoList;
    }

    private List<EntityInfoList> getFinSpecial(List<EntityInfoList> record, List<String> codeList) {
        QueryWrapper<EntityAttrValue> valueQueryWrapper = new QueryWrapper<>();

        List<EntityAttrValue> attrValueList = entityAttrValueMapper.selectList(valueQueryWrapper.lambda()
                .eq(EntityAttrValue::getAttrId, 656)
                .in(EntityAttrValue::getEntityCode, codeList)
        );
        if (CollectionUtils.isEmpty(attrValueList)) {
            return record;
        }
        for (int i = 0; i < record.size(); i++) {

            EntityInfoList result = record.get(i);
            String entityCode = result.getEntityCode();
            List<EntityAttrValue> valueList = attrValueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getEntityCode)).get(entityCode);
            if (CollectionUtils.isEmpty(valueList)) {
                record.set(i, result);
                continue;
            }
            //??????????????? TODO
            List<String> liveBondDetail = new ArrayList<>();
            valueList.stream().forEach(o -> liveBondDetail.add(o.getValue()));
            //????????????
            result.setIndustry(liveBondDetail);
            record.set(i, result);
        }
        return record;
    }

    /**
     * ?????????????????????----??????????????? ??????????????? ???????????? 2
     */
    private List<EntityInfoList> getIssSpecial(List<EntityInfoList> record, List<String> codeList) {
        List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda()
                .in(EntityBondRel::getEntityCode, codeList)
        );
        if (CollectionUtils.isEmpty(entityBondRels)) {
            return record;
        }
        Map<String, List<EntityBondRel>> listMap = entityBondRels.stream().collect(Collectors.groupingBy(EntityBondRel::getEntityCode));
        for (int i = 0; i < record.size(); i++) {
            //??????????????? TODO
            List<String> liveBondDetail = new ArrayList<>();
            //???????????????
            final Integer[] liveBond = {0};
            EntityInfoList result = record.get(i);
            String entityCode = result.getEntityCode();
            List<EntityBondRel> bondRels = listMap.get(entityCode);
            if (CollectionUtils.isEmpty(bondRels)) {
                record.set(i, result);
                continue;
            }
            //???????????? code
            List<String> bondCodeList = new ArrayList<>();
            bondRels.stream().forEach(x -> bondCodeList.add(x.getBdCode()));

            List<BondInfo> bondInfos = bondInfoMapper.selectList(new QueryWrapper<BondInfo>().lambda().in(BondInfo::getBondCode, bondCodeList));
            bondInfos.stream().forEach(x -> {
                Integer bondStatus = x.getBondState();
                if (!ObjectUtils.isEmpty(bondStatus) && 0 == bondStatus) {
                    liveBond[0]++;
                    liveBondDetail.add(x.getBondCode());
                }
            });
            if (liveBond[0] > 0) {
                result.setLiveState(Common.LIVE_STATE);
            }
            //??????????????????
            result.setLiveBond(liveBond[0]);
            result.setLiveBondDetail(liveBondDetail);
            record.set(i, result);
        }
        return record;
    }

    /**
     * ?????????????????????----?????????????????????????????????????????????????????????  1
     */
    private List<EntityInfoList> getListSpecial(List<EntityInfoList> record, List<String> codeList) {

        QueryWrapper<EntityStockCnRel> cnRelQueryWrapper = new QueryWrapper<>();
        List<EntityStockCnRel> entityStockCnRels = cnRelMapper.selectList(cnRelQueryWrapper.lambda().in(EntityStockCnRel::getEntityCode, codeList));

        QueryWrapper<EntityStockThkRel> thkRelQueryWrapper = new QueryWrapper<>();
        List<EntityStockThkRel> entityStockthkRels = thkRelMapper.selectList(thkRelQueryWrapper.lambda().in(EntityStockThkRel::getEntityCode, codeList));
        //??????????????????
        for (int i = 0; i < record.size(); i++) {
            EntityInfoList entityInfoList = record.get(i);
            String o = entityInfoList.getEntityCode();
            //????????????????????????
            List<String> stockDqCodeList = new ArrayList<>();
            //????????????
            List<String> stockCodeList = new ArrayList<>();
            //????????????
            List<String> stockDateList = new ArrayList<>();
            //????????????
            List<String> stockdownDateList = new ArrayList<>();

            if (!CollectionUtils.isEmpty(entityStockCnRels)) {
                List<EntityStockCnRel> cnRels = entityStockCnRels.stream().collect(Collectors.groupingBy(EntityStockCnRel::getEntityCode)).get(o);
                if (!CollectionUtils.isEmpty(cnRels)) {
                    cnRels.stream().forEach(x -> {
                        //????????????????????????
                        stockDqCodeList.add(x.getStockDqCode());

                    });
                }
            }
            if (!CollectionUtils.isEmpty(entityStockthkRels)) {
                List<EntityStockThkRel> thkRels = entityStockthkRels.stream().collect(Collectors.groupingBy(EntityStockThkRel::getEntityCode)).get(o);
                if (!CollectionUtils.isEmpty(thkRels)) {
                    thkRels.stream().forEach(x -> {
                        //????????????????????????
                        stockDqCodeList.add(x.getStockDqCode());

                    });
                }
            }
            //??????????????????
            final String[] listState = {Common.DEAD_STATE};
            //A???????????????
            //A???????????????
            if (!CollectionUtils.isEmpty(stockDqCodeList)) {
                List<StockCnInfo> stockCnInfos = stockCnMapper.selectList(new QueryWrapper<StockCnInfo>().lambda().in(StockCnInfo::getStockDqCode, stockDqCodeList));
                List<StockThkInfo> stockThkInfos = stockThkMapper.selectList(new QueryWrapper<StockThkInfo>().lambda().in(StockThkInfo::getStockDqCode, stockDqCodeList));
                //A?????????
                if (!CollectionUtils.isEmpty(stockCnInfos)) {

                    //????????????  6-????????????
                    stockCnInfos.forEach(x -> {
                        String stockCode = x.getStockCode();
                        String substring = stockCode.substring(0, 1);
                        if (!ObjectUtils.nullSafeEquals(Common.STOCK_BEGIN_WITH_A, substring)) {
                            //????????????????????????
                            stockCodeList.add(stockCode);
                        }
                        if (Common.LIVE_STATE.equals(listState[0])) {
                            return;
                        }
                        Integer stockStatus = x.getStockStatus();
                        if (!ObjectUtils.isEmpty(stockStatus) && 6 == stockStatus) {
                            listState[0] = Common.LIVE_STATE;
                        }
                    });
                    //????????????
                    try {
                        stockCnInfos.stream().filter(x -> !ObjectUtils.isEmpty(x.getDelistingDate())).forEach(x -> {
                            String stockCode = x.getStockCode();
                            String substring = stockCode.substring(0, 1);
                            if (!ObjectUtils.nullSafeEquals(Common.STOCK_BEGIN_WITH_A, substring)) {
                                //????????????????????????
                                stockdownDateList.add(x.getDelistingDate());
                            }
                        });
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    //????????????
                    try {
                        stockCnInfos.stream().filter(x -> !ObjectUtils.isEmpty(x.getListDate())).forEach(x -> {
                            String stockCode = x.getStockCode();
                            String substring = stockCode.substring(0, 1);
                            if (!ObjectUtils.nullSafeEquals(Common.STOCK_BEGIN_WITH_A, substring)) {
                                //????????????????????????
                                stockDateList.add(x.getListDate());
                            }
                        });
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                //??????????????????
                //??????????????????
                if (!CollectionUtils.isEmpty(stockThkInfos)) {
                    //????????????  4-????????????
                    stockThkInfos.forEach(x -> {
                        String stockCode = x.getStockCode();
                        String substring = stockCode.substring(0, 1);
                        if (!ObjectUtils.nullSafeEquals(Common.STOCK_BEGIN_WITH_A, substring)) {
                            //????????????????????????
                            stockCodeList.add(stockCode);
                        }
                        if (Common.LIVE_STATE.equals(listState[0])) {
                            return;
                        }
                        Integer stockStatus = x.getStockStatus();
                        if (!ObjectUtils.isEmpty(stockStatus) && 4 == stockStatus) {
                            listState[0] = Common.LIVE_STATE;
                        }
                    });
                    //????????????
                    try {
                        stockThkInfos.stream().filter(x -> !ObjectUtils.isEmpty(x.getDelistingDate())).forEach(x -> {
                            String stockCode = x.getStockCode();
                            String substring = stockCode.substring(0, 1);
                            if (!ObjectUtils.nullSafeEquals(Common.STOCK_BEGIN_WITH_A, substring)) {
                                //????????????????????????
                                stockdownDateList.add(x.getDelistingDate());
                            }
                        });
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    //????????????
                    try {
                        stockThkInfos.stream().filter(x -> !ObjectUtils.isEmpty(x.getListDate())).forEach(x -> {
                            String stockCode = x.getStockCode();
                            String substring = stockCode.substring(0, 1);
                            if (!ObjectUtils.nullSafeEquals(Common.STOCK_BEGIN_WITH_A, substring)) {
                                //????????????????????????
                                stockDateList.add(x.getListDate());
                            }
                        });
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            entityInfoList.setStockCode(stockCodeList);
            entityInfoList.setListDate(stockDateList);
            entityInfoList.setDownDate(stockdownDateList);

            //?????????????????? Y ?????? N ??????
            entityInfoList.setLiveState(listState[0]);
            record.set(i, entityInfoList);
        }
        return record;
    }


    /**
     * ?????? bondInfo ???????????? bondVo
     *
     * @param bondInfo
     * @return bondVo
     */
    public TargetEntityBondsVo matchingBondInfo(BondInfo bondInfo) {
        TargetEntityBondsVo result = new TargetEntityBondsVo();
        BondVo bondVo = new BondVo();
        //??????id => bond_info
        bondVo.setId(bondInfo.getId());
        //???????????? => bond_code
        bondVo.setBondCode(bondInfo.getBondCode());
        //??????????????????
//        bondVo.setTransactionCode(
//                entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
//                        .lambda().eq(EntityAttrValue::getEntityCode, bondInfo.getBondCode())
//                        .eq(EntityAttrValue::getAttrId, Common.TRANSACTION_CODE_ID)).getValue()
//        );
        bondVo.setTransactionCode(bondInfo.getOriCode());
        //????????????
//        bondVo.setFullName(
//                entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
//                        .lambda().eq(EntityAttrValue::getEntityCode, bondInfo.getBondCode())
//                        .eq(EntityAttrValue::getAttrId, Common.BOND_NAME_ID)).getValue()
//        );
        bondVo.setFullName(bondInfo.getBondName());
        //????????????
        bondVo.setShortName(bondInfo.getBondShortName());
        //????????????
        bondVo.setDebtRaisingType(bondInfo.getBondState() == null ? null : bondInfo.getBondState().toString());
        //TODO ????????????
        //???????????????
        bondVo.setRaiseType(bondInfo.getRaiseType());
        //????????????
        bondVo.setBondState(bondInfo.getBondState());
        result.setBondVo(bondVo);
        return result;
    }

    /**
     * ??????entityInfo ???????????? entityVo
     *
     * @param entityInfo
     * @return
     */
    public TargetEntityBondsVo matchingEntityInfo(EntityInfo entityInfo) {
        TargetEntityBondsVo result = new TargetEntityBondsVo();
        if (entityInfo.getEntityCode() != null) {
            //??????????????? bd_code?????????
            List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda().eq(EntityBondRel::getEntityCode, entityInfo.getEntityCode()));
            if (entityBondRels.size() != 0) {
                EntityBondRel entityBondRel = entityBondRels.get(0);
                //EntityAttrValue byAttrCode = entityAttrValueMapper.findTradCode(entityBondRel.getBdCode());
                BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondCode, entityBondRels.get(0).getBdCode()));
                result.setEntityVo((new EntityVo()
                                .setId(entityInfo.getId())
                                .setEntityName(entityInfo.getEntityName())
                                .setEntityCode(entityInfo.getEntityCode())
                                .setCreditCode(entityInfo.getCreditCode())
                                .setBondCode(bondInfo.getOriCode())
                                .setBondSum(entityBondRels.size())
                        )
                );
            } else {
                result.setEntityVo((new EntityVo()
                        .setId(entityInfo.getId())
                        .setEntityName(entityInfo.getEntityName())
                        .setEntityCode(entityInfo.getEntityCode())
                        .setCreditCode(entityInfo.getCreditCode())
                ));
            }
        }
        return result;
    }

    /**
     * ?????????????????? ????????????
     *
     * @param name    entity_name || bond_short_name
     * @param keyword ??????????????? ENTITY || BOND
     * @return R<List < TargetEntityBondsVo>>
     * @author ??????
     * @date 2022/9/25
     */
    @Override
    public R<Page<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword, Integer pageNum, Integer pageSize) {
        log.info("  =>> ???????????? ?????? " + name + " ??? " + keyword + " ?????? <<=  ");
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        //???????????? ????????????||????????????
        switch (keyword) {
            //?????????????????????
            case ENTITY:
                List<TargetEntityBondsVo> res = new ArrayList<>();
                //????????????????????????list
                Page<EntityInfo> entityInfoPage = baseMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<EntityInfo>().lambda().like(EntityInfo::getEntityName, name));
                List<EntityInfo> entityInfos = entityInfoPage.getRecords();
                if (entityInfos.size() == 0) {
                    log.info("  =>>  ????????????????????????  <<=  ");
                    log.info("  >>>>  ?????????????????? - ??????  <<<<  ");
                    return R.ok(null, BadInfo.VALID_EMPTY_TARGET.getInfo());
                }
                //????????????bd_code
                entityInfos.forEach(row -> {
                    res.add(this.matchingEntityInfo(row));
                });
                Page<TargetEntityBondsVo> targetEntityBondsVoPage = new Page<>();
                targetEntityBondsVoPage.setRecords(res)
                        .setTotal(entityInfoPage.getTotal())
                        .setPages(entityInfoPage.getPages())
                        .setCurrent(entityInfoPage.getCurrent())
                        .setSize(entityInfoPage.getSize());
                log.info("  =>>  ???????????????????????? " + entityInfoPage.getSize() + " ??? <<=  ");
                log.info("  >>>>  ?????????????????? - ??????  <<<<  ");
                return R.ok(targetEntityBondsVoPage, SuccessInfo.SUCCESS.getInfo());
            // ?????????????????????
            case BOND:
                List<TargetEntityBondsVo> rest = new ArrayList<>();
                List<EntityAttrValue> entityAttrs;
                //?????????????????? ??????list
                //?????????????????? ??????list
                Page<BondInfo> bondInfoPage = bondInfoMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<BondInfo>().lambda().like(BondInfo::getBondShortName, name));
                List<BondInfo> bondInfos = bondInfoPage.getRecords();
                if (bondInfos.size() == 0) {
                    log.info("  =>>  ????????????????????????  <<=  ");
                    log.info("  >>>>  ?????????????????? - ??????  <<<<  ");
                    return R.ok(null, BadInfo.VALID_EMPTY_TARGET.getInfo());
                }

                //?????????????????? ??????
                bondInfos.forEach(row -> {
                    rest.add(this.matchingBondInfo(row));
                });
                Page<TargetEntityBondsVo> targetEntityBondsVoPageBond = new Page<>();
                targetEntityBondsVoPageBond.setRecords(rest)
                        .setTotal(bondInfoPage.getTotal())
                        .setPages(bondInfoPage.getPages())
                        .setCurrent(bondInfoPage.getCurrent())
                        .setSize(bondInfoPage.getSize());
                log.info("  =>>  ???????????????????????? " + bondInfoPage.getSize() + " ??? <<=  ");
                log.info("  >>>>  ?????????????????? - ??????  <<<<  ");
                return R.ok(targetEntityBondsVoPageBond, SuccessInfo.SUCCESS.getInfo());
            default:
                return R.fail(BadInfo.VALID_PARAM.getInfo());
        }
    }

    /**
     * ???????????????????????????????????????????????????????????? by??????
     *
     * @param id
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     * @author ??????
     * @date 2022/9/25
     */
    @Override
    public R findRelationEntityOrBond(Integer id, String keyword, Integer pageNum, Integer pageSize) {
        List<TargetEntityBondsVo> result = new ArrayList<>();
        switch (keyword) {
            /**
             * {@link com.deloitte.crm.mapper.EntityBondRelMapper#searchEntity} ???sql??????????????? ??????
             * <code>
             *     EXPAND
             *     SELECT
             *             b.id as id,
             *             b.bond_code as bondCode,
             *             b.ori_code as transactionCode,
             *             b.bond_name as fullName,
             *             b.bond_short_name as bondShortName,
             *             b.bond_state as debtRaisingType,
             *             b.raise_type as raiseType,
             *             b.bond_state as bondState
             *         FROM
             *             entity_bond_rel a
             *             LEFT JOIN bond_info b ON a.bd_code = b.bond_code
             *         WHERE
             *             a.entity_code =(
             *             SELECT
             *                 entity_code
             *             FROM
             *                 entity_info
             *         WHERE
             *             id = #{id} AND `status`=1)
             * </code>
             */
            case ENTITY:
                pageNum = pageNum == null ? 1 : pageNum;
                pageSize = pageSize == null ? 10 : pageSize;
                Page<BondVo> page = new Page<>(pageNum, pageSize);
                IPage<BondVo> entityBondRelIPage = this.entityBondRelMapper.searchEntity(page, id);
                return R.ok(entityBondRelIPage);
            case BOND:
                log.info("  =>> ???????????? id ??? " + id + " ??? " + ENTITY + " ?????? <<=  ");
                BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getId, id));
                List<EntityBondRel> entityBondRels1 = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda().eq(EntityBondRel::getBdCode, bondInfo.getBondCode()));
                entityBondRels1.forEach(item -> {
                    EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, item.getEntityCode()));
                    result.add(this.matchingEntityInfo(entityInfo));
                    log.info("  =>>  ???????????????????????? " + result.size() + " ??? <<=  ");
                    log.info("  >>>>  ?????????????????? - ??????  <<<<");
                });
                return R.ok(result);
            default:
                log.info("  =>> ????????????????????????  <<=  ");
                log.info("  >>>>  ?????????????????? - ??????  <<<<  ");
                return R.ok(result);
        }
    }

    @Override
    public Map<String, Object> getOverview() {
        QueryWrapper<EntityInfo> query = new QueryWrapper<>();
//        list	???????????? 0-????????? 1-?????????
//        finance		0	??????????????????
//        issue_bonds		???????????? 0-????????? 1-?????????
        Long count = entityInfoMapper.selectCount(query);
        //????????????
        Long list = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1));
        query.clear();
        //????????????
        Long bonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //????????????????????????
        Long listBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1).eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //????????????????????????????????????
        Long unListBonds = entityInfoMapper.selectCount(query.lambda().ne(EntityInfo::getList, 1).ne(EntityInfo::getIssueBonds, 1));
        query.clear();
        //??????
        Long finance = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getFinance, 1));

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);
        result.put("bonds", bonds);
        result.put("listBonds", listBonds);
        result.put("unListBonds", unListBonds);
        result.put("finance", finance);
        return result;
    }

    @Override
    public Map<String, Object> getOverviewByGroup() {
        log.info("  >>>>  ???????????????????????? <<<<  ");
        QueryWrapper<EntityInfo> query = new QueryWrapper<>();
        //????????????
        Long count = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getStatus, 1));
        //????????????
        Long list = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1).eq(EntityInfo::getStatus, 1));
        query.clear();
        //??????????????????
        query.and(wrapper -> wrapper.lambda().eq(EntityInfo::getList, 1))
                .and(wrapper -> wrapper.lambda().ne(EntityInfo::getIssueBonds, 1).or().isNull(EntityInfo::getIssueBonds))
                .and(wrapper -> wrapper.lambda().eq(EntityInfo::getStatus, 1));
        Long onlyList = entityInfoMapper.selectCount(query);
        query.clear();
        //????????????
        Long bonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getIssueBonds, 1).eq(EntityInfo::getStatus, 1));
        query.clear();
        //??????????????????
        query.and(wrapper -> wrapper.lambda().ne(EntityInfo::getList, 1).or().isNull(EntityInfo::getList))
                .and(wrapper -> wrapper.lambda().eq(EntityInfo::getIssueBonds, 1))
                .and(wrapper -> wrapper.lambda().eq(EntityInfo::getStatus, 1));
        Long onlyBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //??????????????????????????????
        Long listBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1).eq(EntityInfo::getIssueBonds, 1).eq(EntityInfo::getStatus, 1));
        query.clear();
        //????????????????????????????????????
        query.and(wrapper -> wrapper.lambda().ne(EntityInfo::getList, 1).or().isNull(EntityInfo::getList))
                .and(wrapper -> wrapper.lambda().ne(EntityInfo::getIssueBonds, 1).or().isNull(EntityInfo::getIssueBonds))
                .and(wrapper -> wrapper.lambda().eq(EntityInfo::getStatus, 1));
        Long unListBonds = entityInfoMapper.selectCount(query);
        query.clear();
        //??????
        Long finance = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getFinance, 1).eq(EntityInfo::getStatus, 1));

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);
        result.put("bonds", bonds);
        result.put("listBonds", listBonds);
        result.put("unListBonds", unListBonds);
        result.put("finance", finance);
        result.put("onlyList", onlyList);
        result.put("onlyBonds", onlyBonds);
        return result;
    }

    @Override
    public Map<String, Object> getOverviewByAll() {
        log.info("  >>>>  ?????????????????? <<<<  ");
        //??????????????????
        Long entityCount = entityInfoMapper.selectCount(new QueryWrapper<>());
        Long govCount = govInfoMapper.selectCount(new QueryWrapper<>());

        //????????????????????????
        Map<String, Object> govOverview = getGovOverview(govCount);
        //????????????????????????
        Map<String, Object> entityOverview = getOverview();
        //????????????????????????
        Map<String, Object> overviewAll = new HashMap<>();
        overviewAll.put("entityCount", entityCount);
        overviewAll.put("govCount", govCount);

        //???????????????
        Map<String, Object> result = new HashMap<>();

        result.put("overviewAll", overviewAll);
        result.put("govOverview", govOverview);
        result.put("entityOverview", entityOverview);

        return result;
    }

    private Map<String, Object> getGovOverview(Long govCount) {
        QueryWrapper<GovInfo> govQuery = new QueryWrapper<>();
        //????????????????????????
//        @Excel(name = "sys_dict_data  gov_type1???????????????2?????????????????????3?????????")
//        ??????
        Long govLocal = govInfoMapper.selectCount(govQuery.lambda().eq(GovInfo::getGovType, 1));
        govQuery.clear();
//        ??????????????????
        Long govMan = govInfoMapper.selectCount(govQuery.lambda().eq(GovInfo::getGovType, 2));
        govQuery.clear();

//        ??????????????????GV+?????????????????????
        List<GovInfo> govInfosList = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_PRIVINCE_CODE);
//        ??????????????????GV+?????????????????????
        AtomicReference<Integer> province = new AtomicReference<>(0);
        AtomicReference<Integer> city = new AtomicReference<>(0);
        AtomicReference<Integer> area = new AtomicReference<>(0);
        govInfosList.stream().forEach(o -> {
            String govName = o.getGovName();
            if (!ObjectUtils.isEmpty(govName)) {
                if (govName.contains(Common.DOV_INFO_TYPE_PRIVINCE_NAME)) {
                    province.getAndSet(province.get() + 1);
                } else if (govName.contains(Common.DOV_INFO_TYPE_CITY_NAME)) {
                    city.getAndSet(city.get() + 1);
                } else {
                    area.getAndSet(area.get() + 1);
                }
            }
        });
//        ????????????
//        ???????????????GVA???+000001????????????
        Integer JK = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_JK_CODE).size();
//        ???????????????GVB???+000001????????????
        Integer GX = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_GX_CODE).size();
//        ????????????GVC???+000001????????????
        Integer XQ = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_XQ_CODE).size();
        Integer JKGX = JK + GX + XQ;
//        ??????
        Long other = govCount - govLocal - govMan - province.get() - city.get() - area.get() - JKGX;

        //????????????????????????
        Map<String, Object> govOverview = new HashMap<>();
        govOverview.put("govLocal", govLocal);
        govOverview.put("govMan", govMan);
        govOverview.put("province", province);
        govOverview.put("city", city);
        govOverview.put("area", area);
        govOverview.put("JKGX", JKGX);
        govOverview.put("other", other);
        return govOverview;
    }

    /**
     * ????????????
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return R
     * @author ?????????
     * @date 2022/10/8 15:53
     */
    @Override
    public R getQuickOfCoverage(String param, Integer pageNum, Integer pageSize) {
        log.info("  >>>>  ??????????????????????????????????????? <<<<  ");
        if (ObjectUtil.isEmpty(pageNum)) {
            return R.fail(NO_PAGENUM_ERROR);
        }
        if (ObjectUtil.isEmpty(pageSize)) {
            pageSize = DEFAULT_PAGESIZE;
        }
        //??????????????????
        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();

        //?????????????????????
        List<EntityInfoValueResult> resultList = new ArrayList<>();
        Page<EntityInfoValueResult> pageResult = new Page<>(pageNum, pageSize);

        queryWrapper.lambda()
                .like(EntityInfo::getEntityCode, param)
                .or().like(EntityInfo::getEntityName, param)
                .or().like(EntityInfo::getCreditCode, param)
                .orderByAsc(EntityInfo::getId);
        Page<EntityInfo> page = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //????????????????????????
        pageResult.setTotal(page.getTotal());

        List<EntityInfo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            pageResult.setRecords(null);
        } else {
            records.stream().forEach(o -> {
                //?????????????????????
                EntityInfoValueResult result = new EntityInfoValueResult();
                result.setEntityInfo(o);
                //??????????????????
                List<String> listList = getListDetail(o);
                //????????????
                String listDetail = listList.get(0);
                //????????????
                String stockCode = listList.get(1);
                //??????????????????
                String bondDetail = getBondDetail(o);

                //???????????????
                result.setListDetail(listDetail).setBondDetail(bondDetail).setStockCode(stockCode);

                resultList.add(result);
            });
            pageResult.setRecords(resultList).setCurrent(page.getCurrent());
        }
        return R.ok(pageResult);
    }

    private String getBondDetail(EntityInfo o) {
        String bondDetail = "";

        //???????????????????????????
        QueryWrapper<EntityBondRel> relQuery = new QueryWrapper<>();
        List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(relQuery.lambda()
                .eq(EntityBondRel::getEntityCode, o.getEntityCode())
        );
        //??????????????????  ?????? null
        if (CollectionUtils.isEmpty(entityBondRels)) {
            return bondDetail;
        }
        //???????????????    ??????????????? ?????????????????? ??????????????????????????????????????????
        List<String> bondCodes = new ArrayList<>();
        entityBondRels.stream().forEach(x -> bondCodes.add(x.getBdCode()));
        QueryWrapper<BondInfo> bondQuery = new QueryWrapper<>();
        List<BondInfo> bondInfos = bondInfoMapper.selectList(bondQuery.lambda().in(BondInfo::getBondCode, bondCodes));
        //?????????????????????????????????  ???(???)?????? ????????? abs
        AtomicReference<String> privateMsg = new AtomicReference<>("");
        AtomicReference<String> publicMsg = new AtomicReference<>("");
        AtomicReference<String> collMsg = new AtomicReference<>("");
        AtomicReference<String> absMsg = new AtomicReference<>("");
        //????????????????????????
        bondInfos.stream().forEach(x -> {
            String status = "";
            // ???????????? 0_?????? 1_?????? 2_?????????
            Integer bondState = x.getBondState();
            if (!ObjectUtils.isEmpty(bondState) && bondState == 0) {
                status = BOND_STATE_LIVE;
            } else if (!ObjectUtils.isEmpty(bondState) && bondState == 1) {
                status = BOND_STATE_DEAD;
            }
            //??????????????? 0_?????? 1_??????
            Integer raiseType = x.getRaiseType();

            if (!ObjectUtils.isEmpty(raiseType) && raiseType == 0 && !(BOND_TYPE_PUBLIC + BOND_STATE_LIVE).equals(publicMsg.get())) {
                publicMsg.set(BOND_TYPE_PUBLIC + status);
            } else if (!ObjectUtils.isEmpty(raiseType) && raiseType == 1 && !(BOND_TYPE_PRIVATE + BOND_STATE_LIVE).equals(privateMsg.get())) {
                privateMsg.set(BOND_TYPE_PRIVATE + status);
            }
            Boolean abs = x.getAbs();
            if (!ObjectUtils.isEmpty(abs) && abs && !(BOND_TYPE_ABS + BOND_STATE_LIVE).equals(absMsg.get())) {
                absMsg.set(BOND_TYPE_ABS + status);
            }
            Boolean coll = x.getColl();
            if (!ObjectUtils.isEmpty(coll) && coll && !(BOND_TYPE_COLL + BOND_STATE_LIVE).equals(collMsg.get())) {
                collMsg.set(BOND_TYPE_COLL + status);
            }
        });
        if (!ObjectUtil.isEmpty(privateMsg.get())) {
            bondDetail = privateMsg.get();
        }
        if (!ObjectUtil.isEmpty(publicMsg.get())) {
            if (ObjectUtil.isEmpty(bondDetail)) {
                bondDetail = publicMsg.get();
            } else {
                bondDetail = bondDetail + "," + publicMsg.get();
            }
        }
        if (!ObjectUtil.isEmpty(absMsg.get())) {
            if (ObjectUtil.isEmpty(bondDetail)) {
                bondDetail = absMsg.get();
            } else {
                bondDetail = bondDetail + "," + absMsg.get();
            }
        }
        if (!ObjectUtil.isEmpty(collMsg.get())) {
            if (ObjectUtil.isEmpty(bondDetail)) {
                bondDetail = collMsg.get();
            } else {
                bondDetail = bondDetail + "," + collMsg.get();
            }
        }
        return bondDetail;
    }

    //??????????????????
    public List<String> getListDetail(EntityInfo o) {
        //????????????
        String listDetail = "";
        //????????????
        String stockCode = "";
        //?????????????????? A???  ????????????
        QueryWrapper<EntityStockCnRel> cnRelQuery = new QueryWrapper<>();
        List<EntityStockCnRel> cnRels = cnRelMapper.selectList(cnRelQuery.lambda().eq(EntityStockCnRel::getEntityCode, o.getEntityCode()));
        //?????? A??? ????????????
        String ADetail = "";
        //????????????????????????
        //????????????????????????????????????
        //A??? ?????????????????????????????????
        if (!CollectionUtils.isEmpty(cnRels)) {
            //????????????????????? code
            List<String> cnCodes = new ArrayList<>();
            cnRels.stream().forEach(x -> cnCodes.add(x.getStockDqCode()));
            QueryWrapper<StockCnInfo> stockCnInfoQuery = new QueryWrapper<>();

            //?????? entityCode ????????????A?????????
            List<StockCnInfo> stockCnInfos = stockCnMapper.selectList(stockCnInfoQuery.lambda().in(StockCnInfo::getStockDqCode, cnCodes));
            if (!CollectionUtils.isEmpty(stockCnInfos)) {
                for (int i = 0; i < stockCnInfos.size(); i++) {
                    if ("A???(??????)".equals(ADetail)) {
                        break;
                    }
                    StockCnInfo stockCnInfo = stockCnInfos.get(i);
                    //A????????? 6-????????????
                    Integer stockStatus = stockCnInfo.getStockStatus();
                    if (!ObjectUtils.isEmpty(stockStatus) && stockStatus == 6) {
                        ADetail = "A???" + BOND_STATE_LIVE;
                    } else {
                        ADetail = "A???" + BOND_STATE_BACK;
                    }
                }
            }
            List<StockCnInfo> cnInfos = stockCnInfos.stream().filter(x -> !ObjectUtil.isEmpty(x.getListDate())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cnInfos)) {
                cnInfos.sort(Comparator.comparing(StockCnInfo::getListDate));
                stockCode = cnInfos.get(cnInfos.size() - 1).getStockCode();
            }
        }
        //?????????????????? G???  ????????????
        QueryWrapper<EntityStockThkRel> thkRelQuery = new QueryWrapper<>();
        List<EntityStockThkRel> thkRels = thkRelMapper.selectList(thkRelQuery.lambda().eq(EntityStockThkRel::getEntityCode, o.getEntityCode()));
        //?????? G??? ????????????
        String GDetail = "";
        //????????????????????????
        //????????????????????????????????????
        //A??? ?????????????????????????????????
        if (!CollectionUtils.isEmpty(thkRels)) {
            //????????????????????? code
            List<String> thkCodes = new ArrayList<>();
            thkRels.stream().forEach(x -> thkCodes.add(x.getStockDqCode()));
            QueryWrapper<StockThkInfo> stockThkInfoQuery = new QueryWrapper<>();

            //?????? entityCode ????????????G?????????
            List<StockThkInfo> stockThkInfos = stockThkMapper.selectList(stockThkInfoQuery.lambda().in(StockThkInfo::getStockDqCode, thkCodes));
            if (!CollectionUtils.isEmpty(stockThkInfos)) {
                for (int i = 0; i < stockThkInfos.size(); i++) {
                    if (("??????" + BOND_STATE_LIVE).equals(GDetail)) {
                        break;
                    }
                    StockThkInfo stockThkInfo = stockThkInfos.get(i);
                    //???????????? 3-????????????
                    Integer stockStatus = stockThkInfo.getStockStatus();
                    if (!ObjectUtils.isEmpty(stockStatus) && stockStatus == 3) {
                        GDetail = "??????" + BOND_STATE_LIVE;
                    } else {
                        //??????????????????????????????------??????
                        GDetail = "??????" + BOND_STATE_BACK;
                    }
                }
            }
            List<StockThkInfo> thkInfos = stockThkInfos.stream().filter(x -> !ObjectUtil.isEmpty(x.getListDate())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(thkInfos)) {
                thkInfos.sort(Comparator.comparing(StockThkInfo::getListDate));
                if (ObjectUtil.isEmpty(stockCode)) {
                    stockCode = thkInfos.get(thkInfos.size() - 1).getStockCode();
                } else {
                    stockCode = stockCode + "," + thkInfos.get(thkInfos.size() - 1).getStockCode();
                }
            }
        }
        if (ObjectUtil.isEmpty(ADetail)) {
            listDetail = GDetail;
        } else {
            if (ObjectUtil.isEmpty(GDetail)) {
                listDetail = ADetail;
            } else {
                listDetail = ADetail + "," + GDetail;
            }
        }
        List<String> result = new ArrayList<>();
        result.add(listDetail);
        result.add(stockCode);
        return result;
    }

    /**
     * ****************
     * *    ????????????   *
     * ****************
     * <p>
     * ??????????????????
     *
     * @param suffixLength ????????????
     * @param target       ??????
     */
    @Override
    public Integer getSuffixNumber(Integer suffixLength, String target) {
        return Integer.parseInt(target.substring(target.length() - suffixLength));
    }

    /**
     * ****************
     * *    ????????????   *
     * ****************
     * <p>
     * ?????? 0
     *
     * @param prefixLength ????????????
     * @param target       ??????
     * @return
     */
    @Override
    public String appendPrefix(Integer prefixLength, Integer target) {
        return String.format("%0" + prefixLength + "d", target);
    }

    /**
     * ****************
     * *    ????????????   *
     * ****************
     * <p>
     * ?????? 0
     *
     * @param prefixWord   ?????? ???????????????
     * @param prefixLength ????????????
     * @param target       ????????????
     */
    @Override
    public String appendPrefixDiy(String prefixWord, Integer prefixLength, Integer target) {
        return prefixWord + String.format("%0" + prefixLength + "d", target);
    }

    /**
     * ??????????????????entityCode??????????????????--??????
     *
     * @return void
     * @author ?????????
     * @date 2022/10/12 9:51
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addEntityeMsg(EntitySupplyMsgBack entitySupplyMsgBack) {
        log.info("  >>>>  ??????????????????entityCode??????????????????--??????,entityCode=[{}] <<<<  ", entitySupplyMsgBack.getEntityCode());
        EntityInfo entityInfo = entitySupplyMsgBack.newEntityInfo();
        Integer id = entitySupplyMsgBack.getTaskId();
        CrmSupplyTask crmSupplyTask = crmSupplyTaskMapper.selectById(id);

         /*//??????????????????????????????????????????
        if (!ObjectUtils.isEmpty(crmSupplyTask) && !ObjectUtils.isEmpty(crmSupplyTask.getId()) && crmSupplyTask.getId() == 1) {
            return R.fail("???????????????????????????????????????");
        }*/
        updateEntityInfoByEntityCodeWithOutId(entityInfo);

        //?????????????????????????????????
        crmDailyTaskService.checkDailyTask(crmSupplyTask);

        //??????????????????
        entityCaptureSpeedService.sendTFFSpeed(crmSupplyTask, entityInfo);
        return R.ok("????????????");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfoDetail(EntityInfoDetails entityInfoDetails) {
        //????????????????????? ???????????????????????????

        //????????????
        EntityInfo entityInfo = entityInfoDetails.getEntityInfo();
        //A?????????
        StockCnInfo stockCnInfo = entityInfoDetails.getStockCnInfo();
        //????????????
        StockThkInfo stockThkInfo = entityInfoDetails.getStockThkInfo();

        //????????????????????????
        EntityFinancial entityFinancial = entityInfoDetails.getEntityFinancial();

        log.info("  >>>> ????????????-????????????,???????????? entityInfo=[{}],A????????? stockCnInfo=[{}],???????????? stockThkInfo=[{}],?????????????????? entityFinancial=[{}] <<<<  ", entityInfo, stockCnInfo, stockThkInfo, entityFinancial);

        if (!ObjectUtils.isEmpty(entityFinancial)) {
            financialMapper.updateById(entityFinancial.setUpdated(new Date()));
        }
        //????????????????????????????????????
        EntityBaseBusiInfo entityBaseBusiInfo = entityInfoDetails.getEntityBaseBusiInfo();
        if (!ObjectUtils.isEmpty(entityBaseBusiInfo)) {
            entityBaseBusiInfoMapper.updateById(entityBaseBusiInfo.setUpdated(new Date()));
        }

        if (!ObjectUtil.isEmpty(entityInfo) && !ObjectUtil.isEmpty(entityInfo.getEntityCode())) {
            EntityInfo o = entityInfoMapper.selectById(entityInfo.getId());
            String entityName = o.getEntityName();
            //??????????????????????????????????????????????????????
            if (!ObjectUtils.isEmpty(entityInfo.getEntityName()) && !entityInfo.getEntityName().equals(entityName)) {
                String oldName = entityInfo.getEntityName();
                EntityInfo addOldName = new EntityInfo();
                addOldName.setId(entityInfo.getId())
                        .setEntityNameHis(oldName)
                        .setUpdated(new Date())
                        .setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks());
                addOldName(addOldName);
                //?????????????????????????????????????????????
                entityInfo.setEntityNameHis(null).setEntityNameHisRemarks(null);
            }
            EntityInfo old = entityInfoMapper.selectById(entityInfo.getId());
            entityInfoLogsUpdatedService.insert(old.getEntityCode(), old.getEntityName(), old, entityInfo);
            entityInfo.setUpdated(new Date()).setUpdater(SecurityUtils.getUsername());
            entityInfoMapper.updateById(entityInfo);
        }
        if (!ObjectUtil.isEmpty(stockCnInfo) && !ObjectUtil.isEmpty(stockCnInfo.getStockDqCode())) {
            StockCnInfo old = stockCnMapper.selectById(stockCnInfo.getId());
            entityInfoLogsUpdatedService.insert(old.getStockDqCode(), old.getStockShortName(), old, entityInfo);
            stockCnInfo.setUpdated(new Date());
            stockCnMapper.updateById(stockCnInfo);
        }
        if (!ObjectUtil.isEmpty(stockThkInfo) && !ObjectUtil.isEmpty(stockThkInfo.getStockDqCode())) {
            StockThkInfo old = stockThkMapper.selectById(stockThkInfo.getId());
            entityInfoLogsUpdatedService.insert(old.getStockDqCode(), old.getStockName(), old, entityInfo);
            stockThkInfo.setUpdated(new Date());
            stockThkMapper.updateById(stockThkInfo);
        }
    }

    @Override
    public EntityListView getListView(Integer type) {
        log.info("  >>>>  ??????????????????-???????????? <<<<  ");
        EntityListView entityListView = new EntityListView();
        switch (type) {
            //??????????????????
            case 1:
                entityListView = getListViews();
                break;
            //??????????????????
            case 2:
                entityListView = getBondsViews();
                break;
            //???????????????????????????????????????
            case 3:
                entityListView = getUnBondsListViews();
                break;
            //????????????????????????
            case 4:
                entityListView = getFinViews();
        }
        return entityListView;
    }

    private EntityListView getFinViews() {
        EntityListView entityListView = new EntityListView();
        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getFinance, 1));
        entityListView.setTotle(listTotle);
        return entityListView;
    }

    private EntityListView getUnBondsListViews() {
        EntityListView entityListView = new EntityListView();
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(query -> query.lambda().ne(EntityInfo::getEntityStockTag, 1).or().isNull(EntityInfo::getEntityStockTag));
        queryWrapper.and(query -> query.lambda().ne(EntityInfo::getEntityBondTag, 1).or().isNull(EntityInfo::getEntityBondTag));
        Long listTotle = entityInfoMapper.selectCount(queryWrapper);
        entityListView.setTotle(listTotle);
        return entityListView;

    }

    private EntityListView getBondsViews() {
        EntityListView entityListView = new EntityListView();
        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityBondTag, 1));
        entityListView.setTotle(listTotle);
        if (listTotle < 1) {
            return entityListView.setLive(0L).setDead(0L);
        }
        //????????????????????????????????????
        Long bondLive = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getIssueBonds, 1));
        return entityListView.setLive(bondLive).setDead(listTotle - bondLive);
    }

    private EntityListView getListViews() {
        EntityListView entityListView = new EntityListView();
        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityStockTag, 1));
        entityListView.setTotle(listTotle);
        if (listTotle < 1) {
            return entityListView.setLive(0L).setDead(0L);
        }
        Long listLive = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getList, 1));
        return entityListView.setLive(listLive).setDead(listTotle - listLive);

    }

    /**
     * ?????????????????????excel??????
     *
     * @param file
     * @return R
     * @author penTang
     * @date 2022/10/9 16:12
     */
    @Override
    public List<ExportEntityCheckDto> checkBatchEntity(MultipartFile file, ImportDto importDto) {

        try {
            //??????excel
            List<EntityByBatchDto> entityByBatchDtos = this.getEntityAndBondInfoV(file);
            List<ExportEntityCheckDto> entityByBatchList = new ArrayList<ExportEntityCheckDto>();
            for (int i = 0; i < entityByBatchDtos.size(); i++) {
                entityByBatchList = this.ReData(entityByBatchDtos, importDto, entityByBatchList, i);
            }

            return entityByBatchList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * ????????????
     *
     * @param entityByBatchDtos
     * @param importDto
     * @param entityByBatchList
     * @param i
     * @return List<ExportEntityCheckDto>
     * @author penTang
     * @date 2022/10/27 16:54
     */
    public List<ExportEntityCheckDto> ReData(List<EntityByBatchDto> entityByBatchDtos, ImportDto importDto, List<ExportEntityCheckDto> entityByBatchList, Integer i) {

        ExportEntityCheckDto exportEntityCheckDto = new ExportEntityCheckDto();
        //??????????????????
        exportEntityCheckDto.setEntityName(entityByBatchDtos.get(i).getEntityName());
        exportEntityCheckDto.setCreditCode(entityByBatchDtos.get(i).getCreditCode());
        //?????????????????????????????????
        if (Objects.equals(entityByBatchDtos.get(i).getCreditCode(), null) || Objects.equals(entityByBatchDtos.get(i).getCreditCode(), "")) {
            exportEntityCheckDto.setCreditCodeByRecord("????????????");
        } else {
            //???????????????????????????
            String code = entityByBatchDtos.get(i).getCreditCode();
            String regx = "\\w{18}";
            boolean matches = code.matches(regx);
            if (!matches) {
                exportEntityCheckDto.setCreditCodeByRecord("????????????");
            } else {
                //??????????????????????????????????????????
                EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityByBatchDtos.get(i).getCreditCode()));
                if (entityInfo != null) {
                    exportEntityCheckDto.setCreditCodeByRecord("????????????,???????????????");
                    exportEntityCheckDto.setCreditCodeByEntityName(entityInfo.getEntityName());
                    exportEntityCheckDto.setCreditCodeByEntityCode(entityInfo.getEntityCode());
                    exportEntityCheckDto.setCreditCodeByCreditCode(entityInfo.getCreditCode());
                    exportEntityCheckDto.setIsStatus(entityInfo.getStatus() == 1 ? "???" : "???");
                } else {
                    exportEntityCheckDto.setCreditCodeByRecord("????????????,???????????????");
                }
            }
        }
        //????????????????????????
        if (Objects.equals(entityByBatchDtos.get(i).getEntityName(), null) || Objects.equals(entityByBatchDtos.get(i).getEntityName(), "")) {
            exportEntityCheckDto.setEntityNameByRecord("????????????");
        } else {
            //???????????????????????????????????????
            List<EntityInfo> entityInfos = entityInfoMapper.selectList(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityName, entityByBatchDtos.get(i).getEntityName()));

            if (!entityInfos.isEmpty()) {
                exportEntityCheckDto.setEntityNameByRecord("????????????,???????????????");
                exportEntityCheckDto.setEntityNameByEntityName(entityInfos.get(0).getEntityName());
                exportEntityCheckDto.setEntityNameByEntityCode(entityInfos.get(0).getEntityCode());
                exportEntityCheckDto.setEntityNameByCreditCode(entityInfos.get(0).getCreditCode());
                exportEntityCheckDto.setIsStatus(entityInfos.get(0).getStatus() == 1 ? "???" : "???");
            } else {
                exportEntityCheckDto.setEntityNameByRecord("????????????,???????????????");
            }
        }
        //????????????(???????????????)
        if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE1)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("????????????");
            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    more.put(products.getProName(), "?????????");
                }
                exportEntityCheckDto.setMore(more);
            }


        } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE3)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("?????????");

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    more.put(products.getProName(), "?????????");
                }
                exportEntityCheckDto.setMore(more);
            }

        } else if (exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE3)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("?????????");

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    more.put(products.getProName(), "?????????");
                }
                exportEntityCheckDto.setMore(more);
            }

        } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE3) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE3)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("?????????");

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    more.put(products.getProName(), "?????????");
                }
                exportEntityCheckDto.setMore(more);
            }

        } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE2) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE1)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("?????????");
            exportEntityCheckDto.setCreditCodeByResult(exportEntityCheckDto.getCreditCodeByCreditCode());
            exportEntityCheckDto.setEntityCodeByResult(exportEntityCheckDto.getCreditCodeByEntityCode());
            exportEntityCheckDto.setEntityNameByResult(exportEntityCheckDto.getCreditCodeByEntityName());

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    ProductsCover productsCover = productsCoverMapper.selectOne(new LambdaQueryWrapper<ProductsCover>().eq(ProductsCover::getEntityCode, exportEntityCheckDto.getEntityCodeByResult())
                            .eq(ProductsCover::getProId, proId).eq(ProductsCover::getIsGov, 0));
                    if (productsCover != null) {
                        more.put(products.getProName(), productsCover.getCoverDes());
                    } else {
                        more.put(products.getProName(), "?????????");
                    }

                }
                exportEntityCheckDto.setMore(more);
            }

        } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE2)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("?????????");
            exportEntityCheckDto.setCreditCodeByResult(exportEntityCheckDto.getEntityNameByCreditCode());
            exportEntityCheckDto.setEntityCodeByResult(exportEntityCheckDto.getEntityNameByEntityCode());
            exportEntityCheckDto.setEntityNameByResult(exportEntityCheckDto.getEntityNameByEntityName());

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    ProductsCover productsCover = productsCoverMapper.selectOne(new LambdaQueryWrapper<ProductsCover>().eq(ProductsCover::getEntityCode, exportEntityCheckDto.getEntityCodeByResult())
                            .eq(ProductsCover::getProId, proId).eq(ProductsCover::getIsGov, 0));
                    if (productsCover != null) {
                        more.put(products.getProName(), productsCover.getCoverDes());
                    } else {
                        more.put(products.getProName(), "?????????");
                    }

                }
                exportEntityCheckDto.setMore(more);
            }

        }
        //????????????(??????????????????????????????)
        if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE2) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE2)) {
            if (exportEntityCheckDto.getCreditCodeByEntityCode().equals(exportEntityCheckDto.getEntityNameByEntityCode())) {
                exportEntityCheckDto.setCreditCodeIsEntityName("???????????????");
                exportEntityCheckDto.setEndByResult("?????????");
                exportEntityCheckDto.setCreditCodeByResult(exportEntityCheckDto.getEntityNameByCreditCode());
                exportEntityCheckDto.setEntityCodeByResult(exportEntityCheckDto.getEntityNameByEntityCode());
                exportEntityCheckDto.setEntityNameByResult(exportEntityCheckDto.getEntityNameByEntityName());

                //???????????????????????????
                Map<String, String> more = exportEntityCheckDto.getMore();
                List<Integer> proIds = importDto.getProIds();
                //???????????????
                if (CollUtil.isEmpty(proIds)) {
                    proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
                }
                if (!proIds.isEmpty()) {
                    for (Integer proId : proIds) {
                        Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                        ProductsCover productsCover = productsCoverMapper.selectOne(new LambdaQueryWrapper<ProductsCover>().eq(ProductsCover::getEntityCode, exportEntityCheckDto.getEntityCodeByResult())
                                .eq(ProductsCover::getProId, proId).eq(ProductsCover::getIsGov, 0));
                        if (productsCover != null) {
                            more.put(products.getProName(), productsCover.getCoverDes());
                        } else {
                            more.put(products.getProName(), "?????????");
                        }

                    }
                    exportEntityCheckDto.setMore(more);
                }

            } else {
                exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
                exportEntityCheckDto.setEndByResult("????????????,???????????????");

                //???????????????????????????
                Map<String, String> more = exportEntityCheckDto.getMore();
                List<Integer> proIds = importDto.getProIds();
                //???????????????
                if (CollUtil.isEmpty(proIds)) {
                    proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
                }
                if (!proIds.isEmpty()) {
                    for (Integer proId : proIds) {
                        Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                        more.put(products.getProName(), "?????????");
                    }
                    exportEntityCheckDto.setMore(more);
                }

            }
        } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE3) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE2)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("????????????,???????????????");

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    more.put(products.getProName(), "?????????");
                }
                exportEntityCheckDto.setMore(more);
            }

        } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE2) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE3)) {
            exportEntityCheckDto.setCreditCodeIsEntityName("?????????");
            exportEntityCheckDto.setEndByResult("????????????,???????????????");

            //???????????????????????????
            Map<String, String> more = exportEntityCheckDto.getMore();
            List<Integer> proIds = importDto.getProIds();
            //???????????????
            if (CollUtil.isEmpty(proIds)) {
                proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
            }
            if (!proIds.isEmpty()) {
                for (Integer proId : proIds) {
                    Products products = productsMapper.selectOne(new LambdaQueryWrapper<Products>().eq(Products::getId, proId));
                    more.put(products.getProName(), "?????????");
                }
                exportEntityCheckDto.setMore(more);
            }

        }
        //???????????????????????????
        entityByBatchList.add(exportEntityCheckDto);
        //??????????????????redis-??????????????????????????????
        double index = (i + 1) * 1.0;
        double sum = entityByBatchDtos.size() * 1.0;
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        // ???????????????????????????????????????????????????1?????????
        percentInstance.setMinimumFractionDigits(1);
        Double cov = index / sum;
        String s = cov.toString();
        redisService.setCacheObject(importDto.getUuid(), s, 1L, TimeUnit.DAYS);
        return entityByBatchList;
    }


    /**
     * ??????uuid?????????????????????
     *
     * @param uuid
     * @return R
     * @author penTang
     * @date 2022/10/10 20:54
     */
    @Override
    public R getIng(String uuid) {
        return R.ok(redisService.getCacheObject(uuid));
    }

    /**
     * ??????excel????????????
     *
     * @param file
     * @return List<EntityAndBondInfoVo>
     * @author penTang
     * @date 2022/10/9 17:32
     */
    public List<EntityByBatchDto> getEntityAndBondInfoV(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        //???????????????excel
        if (Strings.isNullOrEmpty(fileName)) {
            throw new GlobalException("????????????????????????");
        }

        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new GlobalException("????????????????????????, ?????????????????????.XLSX?????????");
        }

        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("??????????????????????????????", "creditCode");
        reader.addHeaderAlias("????????????", "entityName");


        List<EntityByBatchDto> entityByBatchDtos = reader.readAll(EntityByBatchDto.class);
        return entityByBatchDtos;
    }

    /**
     * ?????????????????????(excel)
     *
     * @return ExcelWriter
     * @author penTang
     * @date 2022/10/10 9:46
     */
    @Override
    public R getExcelWriter(List<ExportEntityCheckDto> entityByBatchList, ImportDto importDto) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //??????????????????

        ExcelWriter merge = writer.merge(0, 0, 0, 1, "????????????", true)
                .merge(0, 0, 2, 5, "??????1-????????????????????????", true)
                .merge(0, 0, 6, 9, "????????????????????????", true)
                .merge(0, 0, 11, 14, "????????????", true);
        List<Integer> proIds = importDto.getProIds();
        //???????????????
        if (CollUtil.isEmpty(proIds)) {
            proIds = productsMapper.selectList(null).stream().map(Products::getId).collect(Collectors.toList());
        }
        int size = proIds.size();
        if (size != 0) {
            merge.merge(0, 0, 15, 15 + size - 1, "??????????????????", true);

        }
        writer.passCurrentRow();// ???????????????
        CellUtil.setCellValue(writer.getOrCreateCell(10, 0), "????????????", writer.getStyleSet(), true);
        CellUtil.setCellValue(writer.getOrCreateCell(15 + size, 0), "????????????", writer.getStyleSet(), true);


        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        entityByBatchList.forEach(o -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("????????????????????????", o.getCreditCode());
            map.put("????????????", o.getEntityName());
            map.put("???????????????????????????????????????", o.getCreditCodeByRecord());
            map.put("????????????????????????????????????????????????", o.getCreditCodeByEntityCode());
            map.put("?????????????????????????????????????????????????????????", o.getCreditCodeByEntityName());
            map.put("???????????????????????????????????????????????????????????????????????????", o.getCreditCodeByCreditCode());
            map.put("??????????????????????????????", o.getEntityNameByRecord());
            map.put("????????????????????????????????????", o.getEntityNameByEntityCode());
            map.put("????????????????????????????????????", o.getEntityNameByEntityName());
            map.put("????????????????????????????????????????????????", o.getEntityNameByCreditCode());
            map.put("??????????????????????????????????????????????????????????????????", o.getCreditCodeIsEntityName());
            map.put("????????????", o.getEndByResult());
            map.put("??????????????????????????????", o.getEntityCodeByResult());
            map.put("??????????????????????????????", o.getEntityNameByResult());
            map.put("????????????????????????????????????", o.getCreditCodeByResult());
            if (o.getMore() != null) {
                for (String s : o.getMore().keySet()) {
                    map.put(s, o.getMore().get(s));
                }
            }
            map.put("????????????", o.getIsStatus());
            rows.add(map);
        });
        //??????????????????????????????????????????
        writer.write(rows, true);
        // ???????????????
        Sheet sheet = writer.getSheet();
        // ??????????????????
        for (int columnIndex = 0; columnIndex < writer.getColumnCount(); columnIndex++) {
            int width = sheet.getColumnWidth(columnIndex) / 256;
            // ??????????????????
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (Objects.isNull(currentRow)) {
                    currentRow = sheet.createRow(rowIndex);
                }
                Cell currentCell = currentRow.getCell(columnIndex);
                if (Objects.isNull(currentCell)) {
                    continue;
                } else if (currentCell.getCellType() == CellType.STRING) {
                    int length = currentCell.getStringCellValue().getBytes().length;
                    width = width < length ? length : width;
                }
            }
            sheet.setColumnWidth(columnIndex, width * 256);
        }
        // response???HttpServletResponse??????
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //test.xlsx??????????????????????????????????????????????????????????????????????????????
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("??????????????????", "UTF-8")) + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();

        }
        // ??????writer???????????????
        writer.close();
        IoUtil.close(out);
        return R.ok("????????????");
    }


    /**
     * ??????????????????????????????
     *
     * @param name
     * @return List<EntityInfo>
     * @author penTang
     * @date 2022/10/18 10:01
     */
    @Override
    public List<EntityInfo> selectEntityInfoListByName(String name) {
        LambdaQueryWrapper<EntityInfo> qw = new LambdaQueryWrapper<EntityInfo>().like(EntityInfo::getEntityName, name);
        List<EntityInfo> list = list(qw);
        return list;
    }

    /**
     * ??????code ??????
     *
     * @param code
     * @return List<EntityInfo>
     * @author penTang
     * @date 2022/10/18 10:01
     */
    @Override
    public EntityInfoCodeDto selectEntityDto(String code) {
        //entity infoQuery
        LambdaQueryWrapper<EntityInfo> qw = new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, code);
        EntityInfo one = getOne(qw);
        EntityInfoCodeDto entityInfoCodeDto = new EntityInfoCodeDto();
        entityInfoCodeDto.setEntityCode(one.getEntityCode());
        entityInfoCodeDto.setCreditCode(one.getCreditCode());
        entityInfoCodeDto.setEntityName(one.getEntityName());
        entityInfoCodeDto.setFinance(one.getFinance());
        entityInfoCodeDto.setIssueBonds(one.getIssueBonds());
        entityInfoCodeDto.setList(one.getList());
        entityInfoCodeDto.setWindMaster(one.getWindMaster());
        entityInfoCodeDto.setShenWanMaster(one.getShenWanMaster());
        //entityFinancial
        LambdaQueryWrapper<EntityFinancial> eq = new LambdaQueryWrapper<EntityFinancial>().eq(EntityFinancial::getEntityCode, code);
        EntityFinancial entityFinancial = financialMapper.selectOne(eq);
        if (entityFinancial != null) {
            entityInfoCodeDto.setMince(entityFinancial.getMince());
        }

        //?????????????????????
        LambdaQueryWrapper<EntityGovRel> eq1 = new LambdaQueryWrapper<EntityGovRel>().eq(EntityGovRel::getEntityCode, code);
        EntityGovRel entityGovRel = entityGovRelMapper.selectOne(eq1);
        if (entityGovRel != null) {
            entityInfoCodeDto.setIsGov("Y");
            LambdaQueryWrapper<GovInfo> eq2 = new LambdaQueryWrapper<GovInfo>().eq(GovInfo::getDqGovCode, entityGovRel.getDqGovCode());
            GovInfo govInfo = govInfoMapper.selectOne(eq2);
            if (govInfo != null) {
                entityInfoCodeDto.setGovName(govInfo.getGovName());
            }

        } else {
            entityInfoCodeDto.setIsGov("N");
        }
        //entitMaster
        LambdaQueryWrapper<EntityMaster> eq2 = new LambdaQueryWrapper<EntityMaster>().eq(EntityMaster::getEntityCode, code);
        EntityMaster entityMaster = entityMasterMapper.selectOne(eq2);
        if (entityMaster != null) {
            entityInfoCodeDto.setYyUrban(entityMaster.getYyUrban());
            entityInfoCodeDto.setZhongxinUrban(entityMaster.getZhongxinUrban());

        }

        return entityInfoCodeDto;
    }

    @Override
    public R<EntitySupplyMsgBack> getEntityBackSupply(Integer id) {
        log.info("  >>>>  ??????????????????-??????????????????3???4???5???????????????????????????,id=[{}] <<<<  ", id);
        if (ObjectUtils.isEmpty(id)) {
            return R.fail("???????????????id");
        }
        CrmSupplyTask crmSupplyTask = crmSupplyTaskService.selectCrmSupplyTaskById(id);
        if (ObjectUtils.isEmpty(crmSupplyTask)) {
            return R.fail();
        }
        String entityCode = crmSupplyTask.getEntityCode();
        EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode).last(" limit 1"));
        if (ObjectUtils.isEmpty(entityInfo)) {
            return R.ok();
        }
        //???????????????????????????
        EntitySupplyMsgBack entitySupplyMsgBack = new EntitySupplyMsgBack();
        entitySupplyMsgBack.setTaskId(id).setState(crmSupplyTask.getState());
        entitySupplyMsgBack.haveEntityInfo(entityInfo);
        //????????????????????? ????????????
        entitySupplyMsgBack.setSource(crmSupplyTask.getFrom());
        Long roleId = crmSupplyTask.getRoleId();
        // roleId=5 -- ??????3
        if (roleId == 5L) {
            EntityFinancial entityFinancial = financialMapper.selectOne(new QueryWrapper<EntityFinancial>().lambda().eq(EntityFinancial::getEntityCode, entityCode).last(" limit 1"));
            entitySupplyMsgBack.haveEntityFinancial(entityFinancial);
        }
        // roleId=6 -- ??????4
        if (roleId == 6L) {
            EntityGovRel entityGovRel = entityGovRelMapper.selectOne(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getEntityCode, entityCode).last(" limit 1"));
            entitySupplyMsgBack.haveEntityGovRel(entityGovRel);
        }
        return R.ok(entitySupplyMsgBack);
    }

    /**
     * ?????? ???????????????????????????????????????
     *
     * @param creditCode
     * @param entityName
     * @return
     * @author ??????
     */
    @Override
    public R<EntityInfoVo> validateCodeAndName(String creditCode, String entityName) {
        if (StrUtil.isBlank(entityName)) {
            return R.fail(BadInfo.PARAM_EMPTY.getInfo());
        }
        entityName = entityName.trim().replace("???","(").replace("???",")");
        log.info("  =>> ???????????????????????? ??????????????????:{}???????????????{} <<=  ", creditCode, entityName);
        if (StrUtil.isBlank(creditCode)) {
            EntityInfo byName = this.checkName(entityName);
            log.info("  =>> ??????????????? {}  <<=  ", byName);
            if (ObjectUtils.isEmpty(byName)) {
                return R.ok(new EntityInfoVo().setStatus(0), SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
            } else {
                return R.ok(new EntityInfoVo().setEntityInfo(byName).setStatus(3), BadInfo.EXITS_ENTITY_NAME.getInfo());
            }
        } else {
            if (!creditCode.matches(Common.REGEX_CREDIT_CODE)) {
                return R.fail(BadInfo.VALID_CREDIT_CODE.getInfo());
            }
            EntityInfo byCredit = baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode, creditCode));
            EntityInfo byName = this.checkName(entityName);
            if (ObjectUtils.isEmpty(byCredit) && ObjectUtils.isEmpty(byName)) {
                return R.ok(new EntityInfoVo().setStatus(0), SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
            }
            if (ObjectUtils.isEmpty(byCredit)) {
                return R.ok(new EntityInfoVo().setEntityInfo(byName).setStatus(1), BadInfo.EXITS_ENTITY_NAME.getInfo());
            }
            if (ObjectUtils.isEmpty(byName)) {
                return R.ok(new EntityInfoVo().setEntityInfo(byCredit).setStatus(2), BadInfo.EXITS_CREDIT_CODE.getInfo());
            }
            return R.ok(new EntityInfoVo().setEntityInfo(byCredit).setStatus(3), BadInfo.EXITS_ENTITY_CODE.getInfo());
        }
    }

    @Override
    public EntityInfo checkName(String entityName) {
        EntityInfo entityInfo = baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityName, entityName));

        if (entityInfo==null){
            //??????????????????
            List<EntityNameHis> oldNames = entityNameHisMapper.findByOldName(entityName);
            if (CollUtil.isNotEmpty(oldNames)){
                return baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, oldNames.get(0).getDqCode()));
            }
        }


        return entityInfo;
    }

    /**
     * ????????????????????????????????????????????? by??????
     *
     * @param entityCode
     * @param creditCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R editeCreditCode(String entityCode, String creditCode) {
        if (!creditCode.matches(Common.REGEX_CREDIT_CODE)) {
            return R.fail(BadInfo.VALID_PARAM.getInfo());
        }
        log.info("  =>> ??????7 ??????????????????????????????????????? <<=  ");
        EntityInfo entityInfo = baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode));
        if (ObjectUtils.isEmpty(entityInfo)) {
            return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());
        }
        log.info("  =>> ??????{} ??????????????? {} ????????? {} <<=  ", entityInfo.getEntityName(), entityInfo.getCreditCode(), creditCode);
        entityInfo.setCreditCode(creditCode);
        baseMapper.updateById(entityInfo);
        log.info("  =>> ??????7 ??????????????????????????????????????? <<=  ");
        return R.ok();
    }

    @Override
    public void exportEntityByType(Integer type, HttpServletResponse response) {
        log.info("  >>>>  ???????????????????????????????????? <<<<  ");
        LambdaQueryWrapper<EntityInfo> infoQuery = new LambdaQueryWrapper<>();
        if (ObjectUtils.isEmpty(type)) {
            type = 1;
        }
        if (type == 1) {
            infoQuery.eq(EntityInfo::getList, 1);
        } else if (type == 2) {
            infoQuery.eq(EntityInfo::getIssueBonds, 1);
        } else if (type == 3) {
            infoQuery.eq(EntityInfo::getList, 0).eq(EntityInfo::getIssueBonds, 0);
        } else if (type == 4) {
            infoQuery.eq(EntityInfo::getFinance, 1);
        }

        List<EntityInfo> entityInfoList = entityInfoMapper.selectList(infoQuery);
        if (CollectionUtils.isEmpty(entityInfoList)) {
            return;
        }
        //???????????????
        List<EntityInfoList> records = new ArrayList<>();
        //??????????????????
        List<EntityInfoList> finalRecords = records;
        //????????????????????????
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        List<EntityNameHis> nameHisList = nameHisMapper.selectList(hisQuery.lambda().eq(EntityNameHis::getEntityType, 1));
        Map<String, List<EntityNameHis>> hisNameListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(nameHisList)) {
            hisNameListMap = nameHisList.stream().collect(Collectors.groupingBy(EntityNameHis::getDqCode));
        }
        Map<String, List<EntityNameHis>> finalHisNameListMap = hisNameListMap;
        Integer finalType = Integer.valueOf(type);
        List<String> codeList = new ArrayList<>();
        entityInfoList.stream().forEach(o -> codeList.add(o.getEntityCode()));
        entityInfoList.stream().forEach(o -> {
            finalRecords.add(getResultMap(o, finalHisNameListMap, finalType));
        });
        records = finalRecords;

        if (type == 1) {
            records = getListSpecial(records, codeList);
        } else if (type == 2) {
            records = getListSpecial(records, codeList);
        } else if (type == 4) {
            records = getListSpecial(records, codeList);
        }
        if (CollectionUtils.isEmpty(entityInfoList)) {
            return;
        }
        // ???????????????
        List<List<Object>> sheetDataList = new ArrayList<>();
        //????????????
        List<Object> head = Arrays.asList("????????????", "??????????????????", "????????????", "????????????",
                "??????????????????", "????????????", "????????????", "????????????");
        sheetDataList.add(head);

        for (EntityInfoList record : records) {
            //???????????????
            List<Object> sheetData = new ArrayList<>();

            String liveType = "Y";

            record.getList();
            if (type == 1) {
                Integer list = record.getList();
                if (ObjectUtils.isEmpty(list) || 1 != list) {
                    liveType = "N";
                }
            } else if (type == 2) {
                Integer iss = record.getIssueBonds();
                if (ObjectUtils.isEmpty(iss) || 1 != iss) {
                    liveType = "N";
                }
            } else if (type == 3) {
                liveType = "N";
            } else if (type == 4) {
                Integer finance = record.getFinance();
                if (ObjectUtils.isEmpty(finance) || 1 != finance) {
                    liveType = "N";
                }
            }
            sheetData.add(liveType);

            sheetData.add(record.getEntityCode());
            sheetData.add(record.getEntityName());

            sheetData.add(record.getStockCode());

            sheetData.add(record.getNameUsedNum());

            sheetData.add(record.getListDate());
            sheetData.add(record.getDownDate());

            //TODO "????????????"
            sheetData.add("");
            //???????????????
            sheetDataList.add(sheetData);
        }
        String name = "??????????????????";
        if (2 == type) {
            name = "??????????????????";
        } else if (3 == type) {
            name = "????????????????????????????????????";
        } else if (4 == type) {
            name = "????????????????????????";
        }
        // ????????????
        ExcelUtils.export(response, name, sheetDataList);
        log.info("??????" + name + "??????");
    }
}