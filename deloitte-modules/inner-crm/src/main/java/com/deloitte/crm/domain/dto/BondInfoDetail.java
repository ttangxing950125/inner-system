package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.BondInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/13 22:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BondInfoDetail {
    /**
     * 是否发债（含历史）
     */
    private Boolean isBond;
    /**
     * 是否可以收数
     */
    private Boolean isColl;
    /**
     * 首次发债时间
     */
    private String firstBond;


    /**
     * 是否发行集合债
     */
    private Boolean isCollBond;
    /**
     * 发行集合债明细
     */
    private List<BondInfo> collBonds;
    /**
     * 发行集合债数量
     */
    private Integer collBondsNum;
    /**
     * 存续集合债数量
     */
    private Integer collBondsLiveNum;



    /**
     * 是否发行ABS
     */
    private Boolean isAbsBond;
    /**
     * 发行ABS明细
     */
    private List<BondInfo> absBonds;
    /**
     * 发行ABS数量
     */
    private Integer absBondsNum;
    /**
     * 存续ABS数量
     */
    private Integer absBondsLiveNum;

    /**
     * 是否发行公募债
     */
    private Boolean isPublicBond;
    /**
     * 发行公募债明细
     */
    private List<BondInfo> publicBonds;
    /**
     * 发行公募债数量
     */
    private Integer publicBondsNum;
    /**
     * 存续公募债数量
     */
    private Integer publicBondsLiveNum;

    /**
     * 是否发行私募债
     */
    private Boolean isPrivateBond;
    /**
     * 发行私募债明细
     */
    private List<BondInfo> privateBonds;
    /**
     * 发行私募债数量
     */
    private Integer privateBondsNum;
    /**
     * 存续私募债数量
     */
    private Integer privateBondsLiveNum;
}

