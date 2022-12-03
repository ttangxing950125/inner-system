package com.deloitte.additional.recording.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.service.*;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.system.api.domain.SysDictData;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 通用Controller
 * 涉及 项目 敞口、年份、获取用户、用户组 角色等
 * 文件上传下载等
 */
@Slf4j
@RestController
@RequestMapping("common")
public class CommonController {

    @Value("${ocr.request.path}")
    public String url;
    @Value("${ocr.request.apiKey}")
    public String apiKey;

    @Resource
    private PrsModelMasterService prsModelMasterService;
    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;
    @Resource
    private SysGroupService sysGroupService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PrsModelQualService qualService;
    @Resource
    private BasEvdInfoService basEvdInfoService;
    @Resource
    private EntityInfoService entityInfoService;

    @Resource
    private SysDictDataService sysDictDataService;
    @Resource(name = "localFileServiceImpl")
    private FileService fileService;

    @GetMapping("/comboBox")
    public R comboBox() {

        Map<String, Object> map = new HashMap<>();
        //查询所有敞口
        map.put("modelMasterList", prsModelMasterService.finAllPrsModelMaster());
        //查询所有版本
        map.put("projectVersionsList", prsProjectVersionsService.findAllPrsProjectVersions());
        //查询所有用户组
        map.put("sysGroupList", sysGroupService.getSysGroupList());
        //查询补录人员
        map.put("collectionList", sysUserService.getUserNameList("补录人员"));
        //查询审核人员
        map.put("approverList", sysUserService.getUserNameList("审核人员"));
        //查询所有指标名称和指标code
        map.put("allQualNameCod", qualService.getAllQualNameCod());
        //查询evidence的名称和code
        map.put("evdNameCodeVos", basEvdInfoService.getAllEvdNameCode());
        //查询主体名称编码
        map.put("entityNameCodeVoList", entityInfoService.getAllEntityNameCode());
        //查询年份
        List<Map<String, Object>> maps = sysDictDataService.finAllsysDictData();
//        List<String> timeValueList = CollectionUtils.isEmpty(maps) ? maps.get(0).keySet().stream().map(String::valueOf).collect(Collectors.toList()) : null;
        map.put("year", maps);
        final List<SysDictData> collStat = sysDictDataService.findByType(Common.DICTTYPE_COLLSTAT);
        map.put("status",collStat);
        return R.ok(map);
    }

    /**
     * 根据类型查询敞口
     * @param type
     * @return
     */
    @GetMapping("/getTypeModelMaster")
    public R getTypeModelMaster(Integer type) {
        return prsModelMasterService.getTypeModelMaster(type);
    }

    /**
     * 文件上传
     * @param taskId 任务Id
     * @param entityCode 主体编码
     * @param taskType 任务类型 对应 data_type
     * @param file 文件
     * @return
     */
    @PostMapping("/upload")
    public R fileUpload(@RequestParam("taskId") Integer taskId,
                        @RequestParam("entityCode") String entityCode,
                        @RequestParam("taskType") Integer taskType,
                        @RequestPart(required = true) MultipartFile file) {
        try {
            Optional.ofNullable(taskId).orElseThrow(() -> new ServiceException("任务Id不可以为空"));
            Optional.ofNullable(entityCode).orElseThrow(() -> new ServiceException("主体编码不可以为空"));
            Optional.ofNullable(taskType).orElseThrow(() -> new ServiceException("任务taskType不可以为空"));
            Optional.ofNullable(file).orElseThrow(() -> new ServiceException("参数不可以为空"));
            if (ObjectUtil.isEmpty(file.getBytes())) {
                throw new ServiceException("文件为空");
            }
            return R.ok(fileService.localFileUpload(file, taskId, taskType, entityCode));
        } catch (Exception e) {
            log.error("文件上传出现异常:{}", e);
            throw new ServiceException("上传文件异常，请联系管理员");
        }
    }

    /**
     * OCR 文件上传
     * @param FileOcrUploadDto
     * @param request
     * @param response
     * @return
     */
    @SneakyThrows
    @RequestMapping(value = "/ocrRecognition", method = RequestMethod.POST)
    public R ocrRecognition(HttpServletRequest request, HttpServletResponse response) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");
            final byte[] bytes = multipartFile.getBytes();
            if (multipartFile == null || bytes.length == 0) {
                throw new ServiceException("文件为空");
            }
            final String encode = Base64.encode(bytes);
            HashMap<String, Object> requestMap = new HashMap<>();
            requestMap.put("api_key", apiKey);
            requestMap.put("image", encode);
            log.info("请求OCR识别URL: {} >>> JsonL{}", url, requestMap);
            String postResult = HttpUtil.post(url, requestMap);
            log.info("请求OCR文件识别返回结果集>>>>:{}", postResult);
            final Map map = JSONObject.parseObject(postResult, Map.class);
            final Object word_list = map.get("word_list");
            return R.ok(word_list);
        } catch (Exception e) {
            log.error("文件OCR识别异常：{}", e);
            throw new ServiceException("文件识别异常请重新上传");
        }
    }
}
