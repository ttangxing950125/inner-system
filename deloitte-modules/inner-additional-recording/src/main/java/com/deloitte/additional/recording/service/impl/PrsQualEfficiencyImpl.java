package com.deloitte.additional.recording.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.domain.PrsQualData;
import com.deloitte.additional.recording.domain.PrsQualEfficiency;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import com.deloitte.additional.recording.listener.DataListener;
import com.deloitte.additional.recording.mapper.PrsQualEfficiencyMapper;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.vo.qual.PrsQualEfficiencyVO;
import com.deloitte.common.core.constant.Constants;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.JwtUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述 PrsQualEfficiency 业务逻辑处理层
 */
@Service
public class PrsQualEfficiencyImpl extends ServiceImpl<PrsQualEfficiencyMapper, PrsQualEfficiency> implements PrsQualEfficiencyService {


    @Autowired
    private PrsQualDataService prsQualDataService;

    @Autowired
    private PrsModelQualService prsModelQualService;

    @Autowired
    private EntityInfoService entityInfoService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysDictDataService dictDataService;

    @Override
    public Page<PrsQualEfficiencyVO> page(String token, Integer versionId, String modelCode, String qualCodes, String searchKey, Integer riskLevel, String userYear, Integer page, Integer pageSize) {
        Page<PrsQualEfficiencyVO> pages = new Page<>(page, pageSize);
        //1 判断是否为固定的审核人员
        String userId = JwtUtil.getuserid(token);
        //查询字典库
        SysDictData dictData = dictDataService.findByValueAndType(userId, Constants.EXAMINE_USER);
        //声明一个boolean值 判断是否有查询所有列表的权限
        boolean boo = false;
        if (dictData != null) {
            boo = true;
        }
        //判断是否选择了指标
        if (StrUtil.isNotBlank(qualCodes)) {
            String[] codes = qualCodes.split(",");
            //有查询所有列表权限
            List<PrsQualEfficiencyVO> vos = this.baseMapper.pageByCodes(codes, searchKey, riskLevel, boo, userId, page - 1, pageSize);
            //总量
            long total = this.baseMapper.countByCodes(codes, searchKey, riskLevel, boo, userId);
            pages.setTotal(total);
            pages.setRecords(vos);
            return pages;
            //如果为空 则需要查询当前用户分配下的列表

        }
        //未选择指标下拉框
        List<PrsQualEfficiencyVO> vos = this.baseMapper.page(versionId, modelCode, searchKey, riskLevel, userYear, boo, userId, page - 1, pageSize);
        long total = this.baseMapper.pageCount(versionId, modelCode, searchKey, riskLevel, userYear, boo, userId);
        pages.setRecords(vos);
        pages.setTotal(total);
        return pages;
    }

    @Override
    public Map<String, List<PrsQualDataExcelDto>> findByCodes(String qualCodes, String token) {
        //1 判断是否为固定的审核人员
        String userId = JwtUtil.getuserid(token);
        //查询字典库
        SysDictData dictData = dictDataService.findByValueAndType(userId, Constants.EXAMINE_USER);
        if (dictData != null) {
            throw new ServiceException("暂无操作权限，请联系管理员");
        }
        String[] codes = qualCodes.split(",");
        Map<String, List<PrsQualDataExcelDto>> map = new HashMap<>();
        for (String code : codes) {
            List<PrsQualDataExcelDto> list = prsQualDataService.findExcelListByCode(code);
            String quanlName = prsModelQualService.getByCode(code);
            //拼接sheetName
            String sheetName = quanlName + "-" + code;
            map.put(sheetName, list);
        }

        return map;
    }

    @Override
    public void importExcel(MultipartFile serviceFile, String token) {
        //1 判断是否为固定的审核人员
        String userId = JwtUtil.getuserid(token);
        //查询字典库
        SysDictData dictData = dictDataService.findByValueAndType(userId, Constants.EXAMINE_USER);
        if (dictData != null) {
            throw new ServiceException("暂无操作权限，请联系管理员");
        }
        InputStream in = null;
        try {
            in = serviceFile.getInputStream();
            DataListener<PrsQualDataExcelDto> dataListener = new DataListener<PrsQualDataExcelDto>();
            EasyExcel.read(in, PrsQualDataExcelDto.class, dataListener).build().readAll();
            List<PrsQualDataExcelDto> list = dataListener.getCachedDataList();
            //得到sheet
            List<ReadSheet> sheets = dataListener.getSheets();
            //声明一个集合收集qualData的数据
            List<PrsQualData> qualDataList = new ArrayList<>();
            for (ReadSheet sheet : sheets) {
                //截取sheetName 得到指标code
                String sheetName = sheet.getSheetName();
                String[] split = sheetName.split("-");
                //读取到上传的数据 验证数据是否存在 （存在-》执行更新操作 ，同时移除集合中）
                Iterator<PrsQualDataExcelDto> iterator = list.iterator();
                while (iterator.hasNext()) {
                    PrsQualDataExcelDto dto = iterator.next();
                    //查询实体
                    EntityInfo creditCode = entityInfoService.getByCreditCode(dto.getCreditCode());
                    if (creditCode == null) {
                        throw new ServiceException(dto.getEntityName() + "实体信息有误,请确认后重试");
                    }
                    //查询原来数据库是否存在，存在->更新数据  同时移除集合中数据
                    PrsQualData prsQualData = prsQualDataService.getByEntityAndQcodeAndTime(creditCode.getEntityCode(), split[1], "2021");
                    //查询用户
                    SysUser user = userService.getByName(dto.getUserName());
                    if (user == null) {
                        throw new ServiceException(dto.getUserName() + "用户不存在");
                    }
                    //判断是否存在 存在更新，然后移除集合
                    if (prsQualData != null) {
                        //更新数据库数据
                        prsQualData.setProperties(prsQualData, dto.getQualValue(), dto.getTaskStatus(), user.getId());
                        prsQualDataService.updateById(prsQualData);
                        iterator.remove();
                    } else {
                        PrsQualData qualData = new PrsQualData().createBy(split[1], creditCode.getEntityCode(), dto.getQualValue(), "2021");
                        qualDataList.add(qualData);
                    }
                }
            }
            //批量新增
            prsQualDataService.saveBatch(qualDataList);
        } catch (IOException ex) {
            throw new ServiceException("文件解析失败");
        } finally {
            try {
                // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
