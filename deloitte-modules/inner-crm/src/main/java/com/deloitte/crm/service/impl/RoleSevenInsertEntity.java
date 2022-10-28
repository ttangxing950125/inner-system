package com.deloitte.crm.service.impl;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityInfoInsertDTO;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.RoleSevenTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author 正杰
 * @description: RoleSevenInsertEntity
 * @date 2022/10/28
 */
@Component
@Slf4j
public class RoleSevenInsertEntity implements RoleSevenTask {

    private EntityInfoMapper entityInfoMapper;

    private EntityInfoManager entityInfoManager;

    private ICrmEntityTaskService iCrmEntityTaskService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R finishTask(EntityInfoInsertDTO entityInfoInsertDTO) {
        String creditCode = entityInfoInsertDTO.getCreditCode();
        String entityName = entityInfoInsertDTO.getEntityName().replace(" ", "");
        Integer taskId = entityInfoInsertDTO.getTaskId();
        String username = StrUtil.isBlank(SecurityUtils.getUsername()) ? "角色7测试" : SecurityUtils.getUsername();
        log.info("  =>> 角色7 新增主体 {} ：开始 <<=  ", entityName);

        // 插入后获取id;
        EntityInfo entityInfo = new EntityInfo().setEntityName(entityName).setCreater(username);
        entityInfoMapper.insert(entityInfo);
        Integer id = entityInfo.getId();

        // 生成entity_code 那么将该值的 用 IB+0..+id  例 IB000001
        String entityCode = "IB" + this.appendPrefix(6, id);
        entityInfo.setEntityCode(entityCode);

        // 判断社会信用代码是否适用 => 适用为 空 并为其赋值 5 否则有数字
        Integer creditErrorType = entityInfoInsertDTO.getCreditErrorType();
        if (creditErrorType == null) {
            creditErrorType = 5;
            entityInfo.setCreditError(1);
            entityInfo.setCreditErrorType(creditErrorType);
        }
        switch (creditErrorType) {
            case 1:
                //注销企业:ZX+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("ZX", entityCode));
                break;
            case 2:
                //吊销企业:DX+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("DX", entityCode));
                break;
            case 3:
                //非注册机构:OC+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("OC", entityCode));
                break;
            case 4:
                //其他未知原因:CU+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("CU", entityCode));
                break;
            case 5:
                Assert.isTrue(!StrUtil.isBlank(creditCode) || creditCode.matches(Common.REGEX_CREDIT_CODE), BadInfo.VALID_CREDIT_CODE.getInfo());
                entityInfo.setCreditCode(creditCode);
                break;
            default:
                throw new RuntimeException(BadInfo.COULD_NOT_FIND_SOURCE.getInfo());
        }
        log.info("  =>> 主体 {} 统一社会代码为 {} <<=  ", entityName, entityInfo.getCreditCode());
        //添加当前用户
        entityInfo.setUpdater(username);
        //默认生效 代码为 1
        entityInfo.setStatus(1);
        //再次修改当条信息
        entityInfoMapper.updateById(entityInfo);
        //当新增后的 关联数据也进行存储 1-债券 bond_info、2-港股 stock_thk_info、3-股票  stock_cn_info
        String dataCode = entityInfoInsertDTO.getDataCode();

        //添加相关数据
        entityInfoManager.bindData(entityInfoInsertDTO,entityCode,SecurityUtils.getUsername());

        //完成任务
        return iCrmEntityTaskService.finishTask(entityInfoInsertDTO.getTaskId(),2,entityCode);
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 拼接 0
     *
     * @param prefixLength 前缀长度
     * @param target       字符
     * @return
     */
    public String appendPrefix(Integer prefixLength, Integer target) {
        return String.format("%0" + prefixLength + "d", target);
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 拼接 0
     *
     * @param prefixWord   前缀 拼接的字符
     * @param prefixLength 前缀长度
     * @param target       目标字符
     */
    public String appendPrefixDiy(String prefixWord, Integer prefixLength, Integer target) {
        return prefixWord + String.format("%0" + prefixLength + "d", target);
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 拿到后缀数字
     *
     * @param suffixLength 后缀长度
     * @param target       字符
     */
    public Integer getSuffixNumber(Integer suffixLength, String target) {
        return Integer.parseInt(target.substring(target.length() - suffixLength));
    }

    /**
     * 统一社会信用代码不适用是 进行字符拼接
     * 例如 注销企业:ZX+企业德勤代码+R+自0000001开始排序。
     *
     * @param prefix
     * @param entityCode
     * @return
     */
    public String appendCreditCode(String prefix, String entityCode) {
        EntityInfo lastOneByPrefixCredit = entityInfoMapper.findLastOneByPrefixCredit(prefix);
        String suffixString = null;
        if (lastOneByPrefixCredit != null) {
            Integer suffixNumber = this.getSuffixNumber(7, lastOneByPrefixCredit.getCreditCode());
            suffixString = this.appendPrefix(7, suffixNumber + 1);
        } else {
            suffixString = "0000001";
        }
        return prefix + entityCode + "R" + suffixString;
    }

}
