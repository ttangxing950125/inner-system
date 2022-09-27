package com.deloitte.crm.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.bean.BeanUtils;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityBondRel;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoByDto;
import com.deloitte.crm.domain.dto.EntityInfoResult;
import com.deloitte.crm.dto.BondsDetailDto;
import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityBondRelMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.IEntityNameHisService;
import com.deloitte.crm.utils.HttpUtils;
import com.deloitte.crm.vo.EntityInfoVo;
import com.deloitte.crm.vo.TargetEntityBondsVo;
import lombok.AllArgsConstructor;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper, EntityInfo> implements IEntityInfoService {

    private IEntityNameHisService iEntityNameHisService;

    private EntityInfoMapper entityInfoMapper;

    private EntityNameHisMapper nameHisMapper;

    private EntityAttrValueMapper entityAttrValueMapper;

    private EntityBondRelMapper entityBondRelMapper;

    @Autowired
    private HttpUtils httpUtils;

    /**
     * 字段对应的名称
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_NAME = "name";
    /**
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_ID = "id";
    /**
     * 添加的指标封装的字段
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_MORE = "more";
    /**
     * 新增指标的字段名称
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_KEY = "key";
    /**
     * 新增指标的字段值
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_VALUE = "value";

    /**
     * 统计企业主体信息
     *
     * @return List<EntityInfoDto>
     * @author penTang
     * @date 2022/9/22 22:40
     */
    @Override
    public EntityInfoDto getEntityInfo() {

        EntityInfoDto entityInfoDto = new EntityInfoDto();
        List<EntityInfo> list = this.list();

        //TODO issue_bonds 是否发债 0-未发债 1-已发债
        List<EntityInfo> bonds = list().stream()
                .filter(row -> row.getIssueBonds() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //TODO finance 是否金融机构
        List<EntityInfo> finance = list().stream()
                .filter(row -> row.getFinance() != null && row.getFinance() == 1)
                .collect(Collectors.toList());

        //TODO list 是否上市 0-未上市 1-已上市
        List<EntityInfo> entityInfoList = list().stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .collect(Collectors.toList());

        //TODO 即是上市又是发债
        List<EntityInfo> listAndBonds = list().stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .filter(row -> row.getFinance() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //TODO !即是上市又是发债
        List<EntityInfo> notListAndBonds = list().stream()
                .filter(row -> row.getList() != null && row.getList() == 0)
                .filter(row -> row.getFinance() != null && row.getIssueBonds() == 0)
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
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityInfo selectEntityInfoById(Long id) {
        return entityInfoMapper.selectEntityInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo) {
        return entityInfoMapper.selectEntityInfoList(entityInfo);
    }

    private final String IB = "IB";

    private final String ZERO = "0";

    private final Integer CODE_NUMBER = 6;

    /**
     * 新增【请填写功能名称】
     *
     * @param entityDto 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityInfo(EntityDto entityDto) {
        EntityInfo entityInfo = new EntityInfo();
        BeanUtils.copyBeanProp(entityInfo, entityDto);

        // 生成entity_code 那么将该值的 用 IB+0..+id  例 IB000001
        baseMapper.insert(entityInfo);
        Integer id = entityInfo.getId();
        StringBuilder sb = new StringBuilder(IB);
        for (int j = 0; j < CODE_NUMBER - String.valueOf(id).length(); j++) {
            sb.append(ZERO);
        }
        entityInfo.setEntityCode(sb.toString() + id);

        UpdateWrapper<EntityInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .eq(EntityInfo::getId, id)
                .set(EntityInfo::getEntityCode, entityInfo.getEntityCode());

        // 新增曾用名 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        //1-企业主体
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfo.getEntityCode());
        entityNameHis.setOldName(entityDto.getOldName());
        //1-曾用名为自动生曾
        entityNameHis.setSource(1);
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHis.setCreated(new Date());
        iEntityNameHisService.insertEntityNameHis(entityNameHis);

        return entityInfoMapper.update(entityInfo, wrapper);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityInfo(EntityInfo entityInfo) {
        return entityInfoMapper.updateEntityInfo(entityInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoByIds(Long[] ids) {
        return entityInfoMapper.deleteEntityInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoById(Long id) {
        return entityInfoMapper.deleteEntityInfoById(id);
    }

    /**
     * 传入社会信用代码于企业名称
     * => 存在该社会信用代码 返回 比较信息为 false
     * ==> 前端跳转调用人工对比信息，并确认
     * <p>
     * => 不存在社会信用代码 但存在相同企业名称 返回 比较信息 false
     * ==> 前端跳转调用人工对比信息，并确认
     * <p>
     * => 不存在社会信用代码 也不存在相同企业名称 返回 比较信息 true
     * ==> 确认新增主体 生成企业主体德勤代码、统一社会信用代码相关字段
     *
     * @param creditCode 传入 企业统一社会信用代码
     * @param entityName 传入 企业名称
     * @return 比较信息结果
     * @author 正杰
     * @date 2022/9/22
     */
    @Override
    public R<EntityInfoVo> validEntity(String creditCode, String entityName) {
        // 校验数据库是否存在该主体
        EntityInfo entityByCode = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                .lambda().eq(EntityInfo::getCreditCode, creditCode));
        EntityInfo entityByName = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                .lambda().eq(EntityInfo::getEntityName, entityName));
        //库内无该社会信用代码
        if (entityByCode == null) {
            //库内无该主体 是新增
            if (entityByName == null) {
                return R.ok(new EntityInfoVo().setMsg(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo()));
                //库内存在该主体 但是不存在该社会信用代码
            } else {
                return R.ok(new EntityInfoVo()
                        .setEntityInfo(entityByName)
                        .setBo(BadInfo.GET)
                        .setMsg(BadInfo.EXITS_ENTITY_NAME.getInfo()));
            }
            //库内存在该主体 但是主体名称不同
        } else if (entityByName == null) {
            return R.ok(new EntityInfoVo()
                    .setEntityInfo(entityByCode)
                    .setBo(BadInfo.GET)
                    .setMsg(BadInfo.EXITS_ENTITY_DIFFERENT_NAME.getInfo()));
        }
        //库内已存在该主体
        return R.ok(new EntityInfoVo()
                .setEntityInfo(entityByCode)
                .setBo(BadInfo.GET)
                .setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo()));
    }

    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     *
     * @param creditCode    统一社会信用代码
     * @param entityNewName 主体新名称
     * @param remarks       备注
     * @return 修改返回信息
     * @author 正杰
     * @date 2022/9/22
     */
    @Override
    public R editEntityNameHis(String creditCode, String entityNewName, String remarks) {
        //获取当前登录用户
        String username = SecurityUtils.getUsername();
        if (username == null) {
            return R.fail(BadInfo.VALID_EMPTY_USERNAME.getInfo());
        }

        //修改主体名称
        EntityInfo entity = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                .lambda().eq(EntityInfo::getCreditCode, creditCode));
        if (entity == null) {
            return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());
        }

        String oldName = entity.getEntityName();
        //修改主体曾用名 entity_name_his 时 需要用 ， 拼接
        entity.setEntityNameHis(entity.getEntityNameHis() + "，" + oldName);
        //修改主体曾用名 entity_name_his_remarks 时 需要用 日期+更新人+备注;
        entity.setEntityNameHisRemarks(entity.getEntityNameHisRemarks()
                + "\r\n"
                + "；"
                + new Date()
                + " "
                + SecurityUtils.getUsername()
                + " "
                + remarks
        );

        //修改主体曾用名列表以及目前名称
        UpdateWrapper<EntityInfo> entityInfoUpdateWrapper = new UpdateWrapper<>();
        entityInfoUpdateWrapper.lambda().eq(EntityInfo::getCreditCode, creditCode)
                .set(EntityInfo::getEntityName, entityNewName)
                .set(EntityInfo::getEntityNameHis, entity.getEntityNameHis())
                .set(EntityInfo::getEntityNameHisRemarks, entity.getEntityNameHisRemarks())
                .set(EntityInfo::getUpdated, entity.getUpdated())
                .set(EntityInfo::getUpdater, username);
        baseMapper.update(entity, entityInfoUpdateWrapper);

        //查询新名称是否与曾用名重复
        List<String> collect = Arrays.asList(entity.getEntityNameHis().split("，"))
                .stream().filter(row -> row.equals(entityNewName))
                .collect(Collectors.toList());
        if (collect.size() == 0) {
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
            iEntityNameHisService.insertEntityNameHis(entityNameHis);
        }
        return R.ok();
    }

    /**
     * 根据名称查询主体
     *
     * @param entityName
     * @return
     */
    @Override
    public List<EntityInfo> findByName(String entityName) {
        return baseMapper.findByName(entityName);
    }

    @Transactional
    @Override
    public R addOldName(EntityInfo entity) {
        EntityInfo entityInfo = entityInfoMapper.selectById(entity.getId());
        //校验曾用名是否存在
        String entityCode = entityInfo.getEntityCode();
        QueryWrapper<EntityNameHis> queryWrapper = new QueryWrapper<>();
        String nameHis = entity.getEntityNameHis();
        Long aLong = nameHisMapper.selectCount(queryWrapper.lambda()
                .eq(EntityNameHis::getDqCode, entityCode)
                .eq(EntityNameHis::getOldName, nameHis));
        if (aLong > 0) {
            return R.fail("曾用名重复，请重新输入");
        }
        //获取操作用户
        String remoter = httpUtils.getRemoter();
        //修改曾用名记录
        String entityNameHis = entityInfo.getEntityNameHis();
        if (ObjectUtils.isEmpty(entityNameHis)) {
            entityInfo.setEntityNameHis(entity.getEntityNameHis());
        } else {
            entityInfo.setEntityNameHis(entityNameHis + EntityUtils.NAME_USED_SIGN + entity.getEntityNameHis());
        }
        String nameHisRemarks = entity.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            entityInfo.setEntityNameHisRemarks(entity.getUpdated() + remoter + entity.getEntityNameHisRemarks());
        } else {
            entityInfo.setEntityNameHisRemarks(entityNameHis + EntityUtils.NAME_USED_REMARK_SIGN + entity.getUpdated() + remoter + entity.getEntityNameHisRemarks());
        }
        entityInfoMapper.updateById(entityInfo);

        //插入曾用名记录表
        EntityNameHis newNameHis = new EntityNameHis();
        newNameHis.setDqCode(entityInfo.getEntityCode());
        newNameHis.setOldName(entity.getEntityNameHis());
        newNameHis.setEntityType(2);
        newNameHis.setHappenDate(entity.getUpdated());
        newNameHis.setRemarks(entity.getEntityNameHisRemarks());
        newNameHis.setSource(2);
        nameHisMapper.insert(newNameHis);
        return R.ok();
    }

    @Override
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        //根据dqCode查询主体表
        QueryWrapper<EntityInfo> infoQuery = new QueryWrapper<>();
        EntityInfo entityInfo = entityInfoMapper.selectOne(infoQuery.lambda().eq(EntityInfo::getEntityCode, dqCode));
        //根据dqCode查询曾用名列表
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        EntityNameHis nameHis = nameHisMapper.selectOne(hisQuery.lambda()
                .eq(EntityNameHis::getDqCode, dqCode)
                .eq(EntityNameHis::getOldName, oldName));

        if (ObjectUtils.isEmpty(status)) {
            //校验修改后的曾用名是否已经存在
            Long aLong = nameHisMapper.selectCount(hisQuery.lambda()
                    .eq(EntityNameHis::getDqCode, dqCode)
                    .eq(EntityNameHis::getOldName, newOldName));
            if (aLong > 0) {
                return R.ok("曾用名已经存在，请重新输入");
            }
            //修改主体表中的数据
            entityInfo.setEntityNameHis(entityInfo.getEntityNameHis().replaceAll(oldName, newOldName));
            entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
            entityInfoMapper.updateById(entityInfo);
            //修改曾用名表中的数据
            nameHis.setOldName(newOldName);
            nameHisMapper.updateById(nameHis);
            return R.ok();
        }

        //修改主体表中的数据
        entityInfo.setEntityNameHis(entityInfo.getEntityNameHis().replaceAll(oldName, ""));
        entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
        String remarks = entityInfo.getEntityNameHisRemarks();
        String[] split = remarks.split(EntityUtils.NAME_USED_REMARK_SIGN);
        String newRemarks = "";
        //拆分曾用名
        for (String value : split) {
            if (value.contains(oldName)) {
                continue;
            }
            if (ObjectUtils.isEmpty(newRemarks)) {
                newRemarks = value;
            } else {
                newRemarks = newRemarks + EntityUtils.NAME_USED_REMARK_SIGN + value;
            }
        }
        entityInfo.setEntityNameHisRemarks(newRemarks);
        entityInfoMapper.updateById(entityInfo);

        //修改曾用名表中的数据
        nameHis.setStatus(EntityUtils.INVALID);
        nameHisMapper.updateById(nameHis);
        return R.ok();
    }

    @Override
    public R getInfoDetail(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>(entityInfo);
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(entityInfos)) {
            return R.fail("异常查询，数据为空");
        }
        if (entityInfos.size() > 1) {
            return R.fail("异常查询，唯一识别码查出多条数据");
        }
        //TODO  添加主体其余详细信息

        return R.ok(entityInfos.get(0));
    }

    @Override
    public R getInfoList(EntityInfoByDto entityInfoDto) {
        entityInfoDto.setEntityInfo();
        EntityInfo entityInfo = entityInfoDto.getEntityInfo();
        Integer pageNum = entityInfoDto.getPageNum();
        Integer pageSize = entityInfoDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum)) {
            return R.fail("请输入页码");
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = EntityUtils.DEFAULT_PAGE_SIZE;
        }
        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>(entityInfo);

        Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //创建结果集
        Page<Map<String, Object>> pageResult = new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());
        //封装结果集
        List<Map<String, Object>> records = new ArrayList<>();
        entityInfoPage.getRecords().stream().forEach(o ->
                records.add(getResultMap(o))
        );
        pageResult.setRecords(records);
        return R.ok(pageResult);
    }

    @Transactional
    @Override
    public Integer updateInfoList(List<EntityInfo> list) {
        list.stream().forEach(o -> {
            EntityInfo entityInfo = entityInfoMapper.selectById(o.getId());
            entityInfoMapper.updateById(o);
            //修改政府主体名称时，需要添加曾用名
            if (!ObjectUtils.isEmpty(o.getEntityName())) {
                String oldName = entityInfo.getEntityNameHis();
                EntityInfo addOldName = new EntityInfo();
                addOldName.setId(o.getId());
                addOldName.setEntityNameHis(oldName);
                addOldName.setEntityNameHisRemarks(o.getEntityNameHisRemarks());
                addOldName(addOldName);
            }
            //修改政府主体代码时，需要修改主体历史表中的政府主体代码
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
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper(entityInfo);
        return entityInfoMapper.selectList(queryWrapper);
    }

    @Override
    public Object getListEntityByPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum) && ObjectUtils.isEmpty(pageSize)) {
            return getListEntityAll(entityAttrDto);
        } else {
            return getListEntityPage(entityAttrDto);
        }
    }

    /**
     * 全量导出
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:04
     */
    @Override
    public List<EntityInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto) {

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper);

        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();

        entityInfos.stream().forEach(o -> {

            EntityInfoResult entityInfoResult = new EntityInfoResult();
            entityInfoResult.setEntityInfo(o);

            if (!CollectionUtils.isEmpty(mapList)) {
                List<Map<String, Object>> more = getEntityAttrValue(mapList, o);
                entityInfoResult.setMore(more);
            }
            resultRecords.add(entityInfoResult);
        });
        return resultRecords;
    }

    /**
     * 导出政府主体数据
     *
     * @return void
     * @author penTang
     * @date 2022/9/25 21:19
     */
    @Override
    public R ExportEntityInFor(EntityAttrByDto entityAttrDto) {
        List<EntityInfoResult> listEntityAll = this.getListEntityAll(entityAttrDto);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        AtomicInteger serialNumber = new AtomicInteger();
        listEntityAll.forEach(vo -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            EntityInfo info = vo.getEntityInfo();
            map.put("序号", serialNumber.incrementAndGet());
            map.put("德勤主体代码", info.getEntityCode());
            map.put("主体名称", info.getEntityName());
            map.put("续存状态", info.getStatus());
            map.put("统一社会性代码", info.getCreditCode());
            map.put("创建日期", DateUtil.parseDateToStr("yyyy/MM/dd", info.getCreated()));
            map.put("创建人", info.getCreater());
            if (!CollectionUtils.isEmpty(vo.getMore())) {
                vo.getMore().forEach(entryMap -> map.put(entryMap.get("key").toString(), map.get("value")));
            }
            rows.add(map);
        });
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        // 设置自适应
        Sheet sheet = writer.getSheet();
        // 循环设置列宽
        for (int columnIndex = 0; columnIndex < writer.getColumnCount(); columnIndex++) {
            int width = sheet.getColumnWidth(columnIndex) / 256;
            // 获取最大行宽
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
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("企业主体", "UTF-8")) + ".xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return R.fail("导出异常");
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("导出异常");
        }
        // 关闭writer，释放内存
        writer.close();
        return R.ok("导出结束");
    }


    /**
     * 分页查询
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:04
     */
    public Page<EntityInfoResult> getListEntityPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //查询分页数据集
        Page<EntityInfoResult> pageResult = new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());

        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();
        //添加指标栏位
        List<EntityInfo> records = entityInfoPage.getRecords();
        records.stream().forEach(o -> {
            EntityInfoResult entityInfoResult = new EntityInfoResult();
            entityInfoResult.setEntityInfo(o);

            //获取额外的列数据信息
            if (!CollectionUtils.isEmpty(mapList)) {
                List<Map<String, Object>> more = getEntityAttrValue(mapList, o);
                entityInfoResult.setMore(more);
            }
            resultRecords.add(entityInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    /**
     * 获取额外的数据列信息
     *
     * @param mapList
     * @return List<Map < String, Object>>
     * @author 冉浩岑
     * @date 2022/9/26 8:37
     */
    public List<Map<String, Object>> getEntityAttrValue(List<Map<String, String>> mapList, EntityInfo o) {
        List<Map<String, Object>> more = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
            EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                    .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                    .eq(EntityAttrValue::getEntityCode, o.getEntityCode()));

            Map<String, Object> moreMap = new HashMap<>();
            //新增指标栏
            moreMap.put(MORE_ENTITY_KPI_KEY, map.get(MORE_ENTITY_KPI_NAME));
            if (ObjectUtils.isEmpty(attrValue)) {
                moreMap.put(MORE_ENTITY_KPI_VALUE, null);
            } else {
                moreMap.put(MORE_ENTITY_KPI_VALUE, attrValue.getValue());
            }
            more.add(moreMap);
        }
        return more;
    }

    /**
     * EntityInfo 对象转 map,并查询 曾用名条数
     *
     * @param entityInfo
     * @return Map<String, Object>
     * @author 冉浩岑
     * @date 2022/9/22 23:45
     */
    public Map<String, Object> getResultMap(EntityInfo entityInfo) {
        Map<String, Object> resultMap = new HashMap();
        if (null != entityInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(entityInfo), new TypeReference<Map<String, String>>() {
            });
            try {
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                Long count = nameHisMapper.selectCount(wrapper.lambda().eq(EntityNameHis::getDqCode, entityInfo.getEntityCode()));
                resultMap.put(EntityUtils.NAME_USED_NUM, count);
            } catch (Exception e) {
                return resultMap;
            }
        }
        return resultMap;
    }


    public static final String ENTITY = "ENTITY";

    public static final String BOND = "BOND";

    /**
     * 查询债卷信息 模糊匹配
     *
     * @param name    entity_name || bond_short_name
     * @param keyword 请传入常量 ENTITY || BOND
     * @return R<List < TargetEntityBondsVo>>
     * @author 正杰
     * @date 2022/9/25
     */
    @Override
    public R<List<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword) {

        // 正杰任务 模糊匹配 查询主体||债券信息
        switch (keyword) {
            // 模糊匹配主体名
            case ENTITY:
                List<TargetEntityBondsVo> res = null;
                List<EntityInfo> entityInfos = null;
                //主体list
                entityInfos = baseMapper.selectList(new QueryWrapper<EntityInfo>()
                        .lambda().like(EntityInfo::getEntityName, name));
                if (entityInfos == null) {
                    return R.ok(res, BadInfo.VALID_EMPTY_TARGET.getInfo());
                }
                //找到对应bd_code
                entityInfos.forEach(row -> {
                    EntityBondRel entityBondRel = null;
                    EntityAttrValue entityAttrValue = null;
                    if (row.getEntityCode() != null) {
                        entityBondRel = entityBondRelMapper.selectOne(new QueryWrapper<EntityBondRel>()
                                .lambda().eq(EntityBondRel::getEntityCode, row.getEntityCode()));
                    } else if (entityBondRel != null) {
                        entityAttrValue = entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
                                .lambda().eq(EntityAttrValue::getEntityCode, entityBondRel.getBdCode())
                                .eq(EntityAttrValue::getAttrId, Common.TRANSACTION_CODE_ID));
                    }
                    res.add(new TargetEntityBondsVo()
                            .setEntityInfo(row)
                            .setSingleInfo(
                                    new BondsDetailDto()
                                            .setName(Common.TRANSACTION_CODE_NAME)
                                            .setId(entityAttrValue.getId() == null ? 0 : entityAttrValue.getId())
                                            .setValue(entityAttrValue.getValue() == null ? "" : entityAttrValue.getValue())));
                });
                return R.ok(res);
            //TODO 模糊匹配债券名
            case BOND:
                List<TargetEntityBondsVo> rest = null;
                List<EntityAttrValue> entityAttrs = null;
                //债券list
                entityAttrs = entityAttrValueMapper.selectList(new QueryWrapper<EntityAttrValue>()
                        .lambda().eq(EntityAttrValue::getAttrId, Common.BOND_SHORT_NAME_ID)
                        .like(EntityAttrValue::getValue, name));
                if (entityAttrs == null) {
                    return R.ok(rest, BadInfo.VALID_EMPTY_TARGET.getInfo());
                }
                //找到对应的主体
                entityAttrs.forEach(item -> {
                    EntityBondRel entityBondRel = null;
                    EntityInfo entityInfo = null;
                    List<BondsDetailDto> bondsDetailDtos = null;
                    if (item.getEntityCode() != null) {
                        entityBondRel = entityBondRelMapper.selectOne(new QueryWrapper<EntityBondRel>()
                                .lambda().eq(EntityBondRel::getBdCode, item.getEntityCode()));
                    } else if (entityBondRel != null) {
                        entityInfo = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                                .lambda().eq(EntityInfo::getEntityCode, entityBondRel.getEntityCode()));
                    }

//                    bondsDetailDtos.add(new BondsDetailDto().setName())

                });
                return R.ok(rest);
            default:
                return R.fail(BadInfo.VALID_PARAM.getInfo());
        }
    }

}