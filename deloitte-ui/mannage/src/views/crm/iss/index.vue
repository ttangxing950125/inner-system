<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="交易代码" prop="tradeCode">
        <el-input
          v-model="queryParams.tradeCode"
          placeholder="请输入交易代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="债券简称" prop="bondShortName">
        <el-input
          v-model="queryParams.bondShortName"
          placeholder="请输入债券简称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行起始日" prop="issStartDate">
        <el-date-picker clearable
          v-model="queryParams.issStartDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发行起始日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="缴款日" prop="payDate">
        <el-date-picker clearable
          v-model="queryParams.payDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择缴款日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="计划发行规模(亿)" prop="issScalePlan">
        <el-input
          v-model="queryParams.issScalePlan"
          placeholder="请输入计划发行规模(亿)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行金额上限(亿)" prop="issAmountTop">
        <el-input
          v-model="queryParams.issAmountTop"
          placeholder="请输入发行金额上限(亿)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行规模(亿)" prop="issScale">
        <el-input
          v-model="queryParams.issScale"
          placeholder="请输入发行规模(亿)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行期限(年)" prop="issTerm">
        <el-input
          v-model="queryParams.issTerm"
          placeholder="请输入发行期限(年)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="特殊期限" prop="specialTerm">
        <el-input
          v-model="queryParams.specialTerm"
          placeholder="请输入特殊期限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="债券评级" prop="bondGrade">
        <el-input
          v-model="queryParams.bondGrade"
          placeholder="请输入债券评级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主体评级" prop="enetityGrade">
        <el-input
          v-model="queryParams.enetityGrade"
          placeholder="请输入主体评级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="票面利率(%)" prop="couponRate">
        <el-input
          v-model="queryParams.couponRate"
          placeholder="请输入票面利率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="增发债发行收益率(%)" prop="ipoissYield">
        <el-input
          v-model="queryParams.ipoissYield"
          placeholder="请输入增发债发行收益率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指导利率(%)" prop="guideRate">
        <el-input
          v-model="queryParams.guideRate"
          placeholder="请输入指导利率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指导利率利差(%)" prop="guideRateMargin">
        <el-input
          v-model="queryParams.guideRateMargin"
          placeholder="请输入指导利率利差(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="浮动利率(%)" prop="floatRate">
        <el-input
          v-model="queryParams.floatRate"
          placeholder="请输入浮动利率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付息频率" prop="payFre">
        <el-input
          v-model="queryParams.payFre"
          placeholder="请输入付息频率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公告日期 yyyy-MM-dd" prop="annoDate">
        <el-date-picker clearable
          v-model="queryParams.annoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择公告日期 yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发行截止日期 yyyy-MM-dd" prop="issEndDate">
        <el-date-picker clearable
          v-model="queryParams.issEndDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发行截止日期 yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上市日期 yyyy-MM-dd" prop="ipoDate">
        <el-date-picker clearable
          v-model="queryParams.ipoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上市日期 yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上市地点" prop="ipoAddr">
        <el-input
          v-model="queryParams.ipoAddr"
          placeholder="请输入上市地点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="起息日" prop="valueDate">
        <el-date-picker clearable
          v-model="queryParams.valueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择起息日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="到期日" prop="expireDate">
        <el-date-picker clearable
          v-model="queryParams.expireDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择到期日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="债券代码列表" prop="bondCodeList">
        <el-input
          v-model="queryParams.bondCodeList"
          placeholder="请输入债券代码列表"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="募集资金用途" prop="fundUse">
        <el-input
          v-model="queryParams.fundUse"
          placeholder="请输入募集资金用途"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="特殊条款" prop="specialRule">
        <el-input
          v-model="queryParams.specialRule"
          placeholder="请输入特殊条款"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="增信方式" prop="addCreditMethod">
        <el-input
          v-model="queryParams.addCreditMethod"
          placeholder="请输入增信方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评级机构" prop="assessOrgan">
        <el-input
          v-model="queryParams.assessOrgan"
          placeholder="请输入评级机构"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="债券全称" prop="bondName">
        <el-input
          v-model="queryParams.bondName"
          placeholder="请输入债券全称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人简称" prop="issorShortName">
        <el-input
          v-model="queryParams.issorShortName"
          placeholder="请输入发行人简称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人全称" prop="issorName">
        <el-input
          v-model="queryParams.issorName"
          placeholder="请输入发行人全称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人Wind行业(一级)" prop="issorIndustryFirst">
        <el-input
          v-model="queryParams.issorIndustryFirst"
          placeholder="请输入发行人Wind行业(一级)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人Wind行业(二级)" prop="issorIndustrySecond">
        <el-input
          v-model="queryParams.issorIndustrySecond"
          placeholder="请输入发行人Wind行业(二级)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人企业性质" prop="issorEntityNature">
        <el-input
          v-model="queryParams.issorEntityNature"
          placeholder="请输入发行人企业性质"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人省份" prop="issorProvince">
        <el-input
          v-model="queryParams.issorProvince"
          placeholder="请输入发行人省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行人城市" prop="issorCity">
        <el-input
          v-model="queryParams.issorCity"
          placeholder="请输入发行人城市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="担保人" prop="sponsor">
        <el-input
          v-model="queryParams.sponsor"
          placeholder="请输入担保人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行时担保人评级" prop="issSponsorGrade">
        <el-input
          v-model="queryParams.issSponsorGrade"
          placeholder="请输入发行时担保人评级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="担保人企业性质" prop="sponsorEntityNature">
        <el-input
          v-model="queryParams.sponsorEntityNature"
          placeholder="请输入担保人企业性质"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="担保条款" prop="assureRule">
        <el-input
          v-model="queryParams.assureRule"
          placeholder="请输入担保条款"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主承销商" prop="mainUnw">
        <el-input
          v-model="queryParams.mainUnw"
          placeholder="请输入主承销商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承销金额主承分摊" prop="fundShare">
        <el-input
          v-model="queryParams.fundShare"
          placeholder="请输入承销金额主承分摊"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="副主承销商" prop="coUnw">
        <el-input
          v-model="queryParams.coUnw"
          placeholder="请输入副主承销商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="簿记管理人" prop="bookKeeper">
        <el-input
          v-model="queryParams.bookKeeper"
          placeholder="请输入簿记管理人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行价格" prop="issPrice">
        <el-input
          v-model="queryParams.issPrice"
          placeholder="请输入发行价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招标标的" prop="target">
        <el-input
          v-model="queryParams.target"
          placeholder="请输入招标标的"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投标区间" prop="bidRegion">
        <el-input
          v-model="queryParams.bidRegion"
          placeholder="请输入投标区间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招标日" prop="bidDate">
        <el-date-picker clearable
          v-model="queryParams.bidDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择招标日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="招标时间" prop="bidTime">
        <el-input
          v-model="queryParams.bidTime"
          placeholder="请输入招标时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招标场所" prop="bidAddr">
        <el-input
          v-model="queryParams.bidAddr"
          placeholder="请输入招标场所"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中标价位" prop="winBidPrice">
        <el-input
          v-model="queryParams.winBidPrice"
          placeholder="请输入中标价位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中标区间" prop="winBidRegion">
        <el-input
          v-model="queryParams.winBidRegion"
          placeholder="请输入中标区间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="认购倍数" prop="subcMult">
        <el-input
          v-model="queryParams.subcMult"
          placeholder="请输入认购倍数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="加权利率" prop="weightRate">
        <el-input
          v-model="queryParams.weightRate"
          placeholder="请输入加权利率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="全场倍数" prop="fullCourtMult">
        <el-input
          v-model="queryParams.fullCourtMult"
          placeholder="请输入全场倍数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="边际利率" prop="margRate">
        <el-input
          v-model="queryParams.margRate"
          placeholder="请输入边际利率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="边际倍数" prop="margMult">
        <el-input
          v-model="queryParams.margMult"
          placeholder="请输入边际倍数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行费率(%)" prop="issRate">
        <el-input
          v-model="queryParams.issRate"
          placeholder="请输入发行费率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承销团成员" prop="unwMenber">
        <el-input
          v-model="queryParams.unwMenber"
          placeholder="请输入承销团成员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上网发行数量" prop="issOnlineNub">
        <el-input
          v-model="queryParams.issOnlineNub"
          placeholder="请输入上网发行数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上网认购代码" prop="onlineSubcCode">
        <el-input
          v-model="queryParams.onlineSubcCode"
          placeholder="请输入上网认购代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Wind债券类型(二级)" prop="windBondTypeSecond">
        <el-input
          v-model="queryParams.windBondTypeSecond"
          placeholder="请输入Wind债券类型(二级)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="次级债或混合资本债" prop="secondMixedCapitalBond">
        <el-input
          v-model="queryParams.secondMixedCapitalBond"
          placeholder="请输入次级债或混合资本债"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否城投债" prop="isCiBond">
        <el-input
          v-model="queryParams.isCiBond"
          placeholder="请输入是否城投债"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否跨市场" prop="crossMarket">
        <el-input
          v-model="queryParams.crossMarket"
          placeholder="请输入是否跨市场"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="面值" prop="denom">
        <el-input
          v-model="queryParams.denom"
          placeholder="请输入面值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地方政府债新增金额" prop="govNewAmount">
        <el-input
          v-model="queryParams.govNewAmount"
          placeholder="请输入地方政府债新增金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地方政府债置换金额" prop="govReplaceAmount">
        <el-input
          v-model="queryParams.govReplaceAmount"
          placeholder="请输入地方政府债置换金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['crm:iss:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['crm:iss:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['crm:iss:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:iss:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="issList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="交易代码" align="center" prop="tradeCode" />
      <el-table-column label="债券简称" align="center" prop="bondShortName" />
      <el-table-column label="发行起始日" align="center" prop="issStartDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缴款日" align="center" prop="payDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划发行规模(亿)" align="center" prop="issScalePlan" />
      <el-table-column label="发行金额上限(亿)" align="center" prop="issAmountTop" />
      <el-table-column label="发行规模(亿)" align="center" prop="issScale" />
      <el-table-column label="发行期限(年)" align="center" prop="issTerm" />
      <el-table-column label="特殊期限" align="center" prop="specialTerm" />
      <el-table-column label="债券评级" align="center" prop="bondGrade" />
      <el-table-column label="主体评级" align="center" prop="enetityGrade" />
      <el-table-column label="票面利率(%)" align="center" prop="couponRate" />
      <el-table-column label="增发债发行收益率(%)" align="center" prop="ipoissYield" />
      <el-table-column label="指导利率(%)" align="center" prop="guideRate" />
      <el-table-column label="指导利率利差(%)" align="center" prop="guideRateMargin" />
      <el-table-column label="浮动利率(%)" align="center" prop="floatRate" />
      <el-table-column label="利率类型" align="center" prop="rateType" />
      <el-table-column label="付息频率" align="center" prop="payFre" />
      <el-table-column label="公告日期 yyyy-MM-dd" align="center" prop="annoDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.annoDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发行截止日期 yyyy-MM-dd" align="center" prop="issEndDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上市日期 yyyy-MM-dd" align="center" prop="ipoDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ipoDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上市地点" align="center" prop="ipoAddr" />
      <el-table-column label="起息日" align="center" prop="valueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.valueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期日" align="center" prop="expireDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expireDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="债券代码列表" align="center" prop="bondCodeList" />
      <el-table-column label="募集资金用途" align="center" prop="fundUse" />
      <el-table-column label="特殊条款" align="center" prop="specialRule" />
      <el-table-column label="增信方式" align="center" prop="addCreditMethod" />
      <el-table-column label="评级机构" align="center" prop="assessOrgan" />
      <el-table-column label="债券全称" align="center" prop="bondName" />
      <el-table-column label="发行人简称" align="center" prop="issorShortName" />
      <el-table-column label="发行人全称" align="center" prop="issorName" />
      <el-table-column label="发行人Wind行业(一级)" align="center" prop="issorIndustryFirst" />
      <el-table-column label="发行人Wind行业(二级)" align="center" prop="issorIndustrySecond" />
      <el-table-column label="发行人企业性质" align="center" prop="issorEntityNature" />
      <el-table-column label="发行人省份" align="center" prop="issorProvince" />
      <el-table-column label="发行人城市" align="center" prop="issorCity" />
      <el-table-column label="担保人" align="center" prop="sponsor" />
      <el-table-column label="发行时担保人评级" align="center" prop="issSponsorGrade" />
      <el-table-column label="担保人企业性质" align="center" prop="sponsorEntityNature" />
      <el-table-column label="担保条款" align="center" prop="assureRule" />
      <el-table-column label="主承销商" align="center" prop="mainUnw" />
      <el-table-column label="承销金额主承分摊" align="center" prop="fundShare" />
      <el-table-column label="副主承销商" align="center" prop="coUnw" />
      <el-table-column label="簿记管理人" align="center" prop="bookKeeper" />
      <el-table-column label="发行方式" align="center" prop="issType" />
      <el-table-column label="承销方式" align="center" prop="unwType" />
      <el-table-column label="发行价格" align="center" prop="issPrice" />
      <el-table-column label="招标标的" align="center" prop="target" />
      <el-table-column label="招标方式" align="center" prop="bidType" />
      <el-table-column label="投标区间" align="center" prop="bidRegion" />
      <el-table-column label="招标日" align="center" prop="bidDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.bidDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="招标时间" align="center" prop="bidTime" />
      <el-table-column label="招标场所" align="center" prop="bidAddr" />
      <el-table-column label="中标价位" align="center" prop="winBidPrice" />
      <el-table-column label="中标区间" align="center" prop="winBidRegion" />
      <el-table-column label="认购倍数" align="center" prop="subcMult" />
      <el-table-column label="加权利率" align="center" prop="weightRate" />
      <el-table-column label="全场倍数" align="center" prop="fullCourtMult" />
      <el-table-column label="边际利率" align="center" prop="margRate" />
      <el-table-column label="边际倍数" align="center" prop="margMult" />
      <el-table-column label="发行费率(%)" align="center" prop="issRate" />
      <el-table-column label="承销团成员" align="center" prop="unwMenber" />
      <el-table-column label="上网发行数量" align="center" prop="issOnlineNub" />
      <el-table-column label="上网认购代码" align="center" prop="onlineSubcCode" />
      <el-table-column label="Wind债券类型(二级)" align="center" prop="windBondTypeSecond" />
      <el-table-column label="次级债或混合资本债" align="center" prop="secondMixedCapitalBond" />
      <el-table-column label="是否发行失败" align="center" prop="issStatus" />
      <el-table-column label="是否城投债" align="center" prop="isCiBond" />
      <el-table-column label="是否增发" align="center" prop="addIssStatus" />
      <el-table-column label="是否跨市场" align="center" prop="crossMarket" />
      <el-table-column label="面值" align="center" prop="denom" />
      <el-table-column label="地方政府债新增金额" align="center" prop="govNewAmount" />
      <el-table-column label="地方政府债置换金额" align="center" prop="govReplaceAmount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:iss:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:iss:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改新债发行-新发行债券-20220801-20220914对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="交易代码" prop="tradeCode">
          <el-input v-model="form.tradeCode" placeholder="请输入交易代码" />
        </el-form-item>
        <el-form-item label="债券简称" prop="bondShortName">
          <el-input v-model="form.bondShortName" placeholder="请输入债券简称" />
        </el-form-item>
        <el-form-item label="发行起始日" prop="issStartDate">
          <el-date-picker clearable
            v-model="form.issStartDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发行起始日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="缴款日" prop="payDate">
          <el-date-picker clearable
            v-model="form.payDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择缴款日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计划发行规模(亿)" prop="issScalePlan">
          <el-input v-model="form.issScalePlan" placeholder="请输入计划发行规模(亿)" />
        </el-form-item>
        <el-form-item label="发行金额上限(亿)" prop="issAmountTop">
          <el-input v-model="form.issAmountTop" placeholder="请输入发行金额上限(亿)" />
        </el-form-item>
        <el-form-item label="发行规模(亿)" prop="issScale">
          <el-input v-model="form.issScale" placeholder="请输入发行规模(亿)" />
        </el-form-item>
        <el-form-item label="发行期限(年)" prop="issTerm">
          <el-input v-model="form.issTerm" placeholder="请输入发行期限(年)" />
        </el-form-item>
        <el-form-item label="特殊期限" prop="specialTerm">
          <el-input v-model="form.specialTerm" placeholder="请输入特殊期限" />
        </el-form-item>
        <el-form-item label="债券评级" prop="bondGrade">
          <el-input v-model="form.bondGrade" placeholder="请输入债券评级" />
        </el-form-item>
        <el-form-item label="主体评级" prop="enetityGrade">
          <el-input v-model="form.enetityGrade" placeholder="请输入主体评级" />
        </el-form-item>
        <el-form-item label="票面利率(%)" prop="couponRate">
          <el-input v-model="form.couponRate" placeholder="请输入票面利率(%)" />
        </el-form-item>
        <el-form-item label="增发债发行收益率(%)" prop="ipoissYield">
          <el-input v-model="form.ipoissYield" placeholder="请输入增发债发行收益率(%)" />
        </el-form-item>
        <el-form-item label="指导利率(%)" prop="guideRate">
          <el-input v-model="form.guideRate" placeholder="请输入指导利率(%)" />
        </el-form-item>
        <el-form-item label="指导利率利差(%)" prop="guideRateMargin">
          <el-input v-model="form.guideRateMargin" placeholder="请输入指导利率利差(%)" />
        </el-form-item>
        <el-form-item label="浮动利率(%)" prop="floatRate">
          <el-input v-model="form.floatRate" placeholder="请输入浮动利率(%)" />
        </el-form-item>
        <el-form-item label="付息频率" prop="payFre">
          <el-input v-model="form.payFre" placeholder="请输入付息频率" />
        </el-form-item>
        <el-form-item label="公告日期 yyyy-MM-dd" prop="annoDate">
          <el-date-picker clearable
            v-model="form.annoDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择公告日期 yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发行截止日期 yyyy-MM-dd" prop="issEndDate">
          <el-date-picker clearable
            v-model="form.issEndDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发行截止日期 yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上市日期 yyyy-MM-dd" prop="ipoDate">
          <el-date-picker clearable
            v-model="form.ipoDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上市日期 yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上市地点" prop="ipoAddr">
          <el-input v-model="form.ipoAddr" placeholder="请输入上市地点" />
        </el-form-item>
        <el-form-item label="起息日" prop="valueDate">
          <el-date-picker clearable
            v-model="form.valueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择起息日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="到期日" prop="expireDate">
          <el-date-picker clearable
            v-model="form.expireDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择到期日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="债券代码列表" prop="bondCodeList">
          <el-input v-model="form.bondCodeList" placeholder="请输入债券代码列表" />
        </el-form-item>
        <el-form-item label="募集资金用途" prop="fundUse">
          <el-input v-model="form.fundUse" placeholder="请输入募集资金用途" />
        </el-form-item>
        <el-form-item label="特殊条款" prop="specialRule">
          <el-input v-model="form.specialRule" placeholder="请输入特殊条款" />
        </el-form-item>
        <el-form-item label="增信方式" prop="addCreditMethod">
          <el-input v-model="form.addCreditMethod" placeholder="请输入增信方式" />
        </el-form-item>
        <el-form-item label="评级机构" prop="assessOrgan">
          <el-input v-model="form.assessOrgan" placeholder="请输入评级机构" />
        </el-form-item>
        <el-form-item label="债券全称" prop="bondName">
          <el-input v-model="form.bondName" placeholder="请输入债券全称" />
        </el-form-item>
        <el-form-item label="发行人简称" prop="issorShortName">
          <el-input v-model="form.issorShortName" placeholder="请输入发行人简称" />
        </el-form-item>
        <el-form-item label="发行人全称" prop="issorName">
          <el-input v-model="form.issorName" placeholder="请输入发行人全称" />
        </el-form-item>
        <el-form-item label="发行人Wind行业(一级)" prop="issorIndustryFirst">
          <el-input v-model="form.issorIndustryFirst" placeholder="请输入发行人Wind行业(一级)" />
        </el-form-item>
        <el-form-item label="发行人Wind行业(二级)" prop="issorIndustrySecond">
          <el-input v-model="form.issorIndustrySecond" placeholder="请输入发行人Wind行业(二级)" />
        </el-form-item>
        <el-form-item label="发行人企业性质" prop="issorEntityNature">
          <el-input v-model="form.issorEntityNature" placeholder="请输入发行人企业性质" />
        </el-form-item>
        <el-form-item label="发行人省份" prop="issorProvince">
          <el-input v-model="form.issorProvince" placeholder="请输入发行人省份" />
        </el-form-item>
        <el-form-item label="发行人城市" prop="issorCity">
          <el-input v-model="form.issorCity" placeholder="请输入发行人城市" />
        </el-form-item>
        <el-form-item label="担保人" prop="sponsor">
          <el-input v-model="form.sponsor" placeholder="请输入担保人" />
        </el-form-item>
        <el-form-item label="发行时担保人评级" prop="issSponsorGrade">
          <el-input v-model="form.issSponsorGrade" placeholder="请输入发行时担保人评级" />
        </el-form-item>
        <el-form-item label="担保人企业性质" prop="sponsorEntityNature">
          <el-input v-model="form.sponsorEntityNature" placeholder="请输入担保人企业性质" />
        </el-form-item>
        <el-form-item label="担保条款" prop="assureRule">
          <el-input v-model="form.assureRule" placeholder="请输入担保条款" />
        </el-form-item>
        <el-form-item label="主承销商" prop="mainUnw">
          <el-input v-model="form.mainUnw" placeholder="请输入主承销商" />
        </el-form-item>
        <el-form-item label="承销金额主承分摊" prop="fundShare">
          <el-input v-model="form.fundShare" placeholder="请输入承销金额主承分摊" />
        </el-form-item>
        <el-form-item label="副主承销商" prop="coUnw">
          <el-input v-model="form.coUnw" placeholder="请输入副主承销商" />
        </el-form-item>
        <el-form-item label="簿记管理人" prop="bookKeeper">
          <el-input v-model="form.bookKeeper" placeholder="请输入簿记管理人" />
        </el-form-item>
        <el-form-item label="发行价格" prop="issPrice">
          <el-input v-model="form.issPrice" placeholder="请输入发行价格" />
        </el-form-item>
        <el-form-item label="招标标的" prop="target">
          <el-input v-model="form.target" placeholder="请输入招标标的" />
        </el-form-item>
        <el-form-item label="投标区间" prop="bidRegion">
          <el-input v-model="form.bidRegion" placeholder="请输入投标区间" />
        </el-form-item>
        <el-form-item label="招标日" prop="bidDate">
          <el-date-picker clearable
            v-model="form.bidDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择招标日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="招标时间" prop="bidTime">
          <el-input v-model="form.bidTime" placeholder="请输入招标时间" />
        </el-form-item>
        <el-form-item label="招标场所" prop="bidAddr">
          <el-input v-model="form.bidAddr" placeholder="请输入招标场所" />
        </el-form-item>
        <el-form-item label="中标价位" prop="winBidPrice">
          <el-input v-model="form.winBidPrice" placeholder="请输入中标价位" />
        </el-form-item>
        <el-form-item label="中标区间" prop="winBidRegion">
          <el-input v-model="form.winBidRegion" placeholder="请输入中标区间" />
        </el-form-item>
        <el-form-item label="认购倍数" prop="subcMult">
          <el-input v-model="form.subcMult" placeholder="请输入认购倍数" />
        </el-form-item>
        <el-form-item label="加权利率" prop="weightRate">
          <el-input v-model="form.weightRate" placeholder="请输入加权利率" />
        </el-form-item>
        <el-form-item label="全场倍数" prop="fullCourtMult">
          <el-input v-model="form.fullCourtMult" placeholder="请输入全场倍数" />
        </el-form-item>
        <el-form-item label="边际利率" prop="margRate">
          <el-input v-model="form.margRate" placeholder="请输入边际利率" />
        </el-form-item>
        <el-form-item label="边际倍数" prop="margMult">
          <el-input v-model="form.margMult" placeholder="请输入边际倍数" />
        </el-form-item>
        <el-form-item label="发行费率(%)" prop="issRate">
          <el-input v-model="form.issRate" placeholder="请输入发行费率(%)" />
        </el-form-item>
        <el-form-item label="承销团成员" prop="unwMenber">
          <el-input v-model="form.unwMenber" placeholder="请输入承销团成员" />
        </el-form-item>
        <el-form-item label="上网发行数量" prop="issOnlineNub">
          <el-input v-model="form.issOnlineNub" placeholder="请输入上网发行数量" />
        </el-form-item>
        <el-form-item label="上网认购代码" prop="onlineSubcCode">
          <el-input v-model="form.onlineSubcCode" placeholder="请输入上网认购代码" />
        </el-form-item>
        <el-form-item label="Wind债券类型(二级)" prop="windBondTypeSecond">
          <el-input v-model="form.windBondTypeSecond" placeholder="请输入Wind债券类型(二级)" />
        </el-form-item>
        <el-form-item label="次级债或混合资本债" prop="secondMixedCapitalBond">
          <el-input v-model="form.secondMixedCapitalBond" placeholder="请输入次级债或混合资本债" />
        </el-form-item>
        <el-form-item label="是否城投债" prop="isCiBond">
          <el-input v-model="form.isCiBond" placeholder="请输入是否城投债" />
        </el-form-item>
        <el-form-item label="是否跨市场" prop="crossMarket">
          <el-input v-model="form.crossMarket" placeholder="请输入是否跨市场" />
        </el-form-item>
        <el-form-item label="面值" prop="denom">
          <el-input v-model="form.denom" placeholder="请输入面值" />
        </el-form-item>
        <el-form-item label="地方政府债新增金额" prop="govNewAmount">
          <el-input v-model="form.govNewAmount" placeholder="请输入地方政府债新增金额" />
        </el-form-item>
        <el-form-item label="地方政府债置换金额" prop="govReplaceAmount">
          <el-input v-model="form.govReplaceAmount" placeholder="请输入地方政府债置换金额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIss, getIss, delIss, addIss, updateIss } from "@/api/crm/iss";

export default {
  name: "Iss",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 新债发行-新发行债券-20220801-20220914表格数据
      issList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tradeCode: null,
        bondShortName: null,
        issStartDate: null,
        payDate: null,
        issScalePlan: null,
        issAmountTop: null,
        issScale: null,
        issTerm: null,
        specialTerm: null,
        bondGrade: null,
        enetityGrade: null,
        couponRate: null,
        ipoissYield: null,
        guideRate: null,
        guideRateMargin: null,
        floatRate: null,
        rateType: null,
        payFre: null,
        annoDate: null,
        issEndDate: null,
        ipoDate: null,
        ipoAddr: null,
        valueDate: null,
        expireDate: null,
        bondCodeList: null,
        fundUse: null,
        specialRule: null,
        addCreditMethod: null,
        assessOrgan: null,
        bondName: null,
        issorShortName: null,
        issorName: null,
        issorIndustryFirst: null,
        issorIndustrySecond: null,
        issorEntityNature: null,
        issorProvince: null,
        issorCity: null,
        sponsor: null,
        issSponsorGrade: null,
        sponsorEntityNature: null,
        assureRule: null,
        mainUnw: null,
        fundShare: null,
        coUnw: null,
        bookKeeper: null,
        issType: null,
        unwType: null,
        issPrice: null,
        target: null,
        bidType: null,
        bidRegion: null,
        bidDate: null,
        bidTime: null,
        bidAddr: null,
        winBidPrice: null,
        winBidRegion: null,
        subcMult: null,
        weightRate: null,
        fullCourtMult: null,
        margRate: null,
        margMult: null,
        issRate: null,
        unwMenber: null,
        issOnlineNub: null,
        onlineSubcCode: null,
        windBondTypeSecond: null,
        secondMixedCapitalBond: null,
        issStatus: null,
        isCiBond: null,
        addIssStatus: null,
        crossMarket: null,
        denom: null,
        govNewAmount: null,
        govReplaceAmount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询新债发行-新发行债券-20220801-20220914列表 */
    getList() {
      this.loading = true;
      listIss(this.queryParams).then(response => {
        this.issList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        tradeCode: null,
        bondShortName: null,
        issStartDate: null,
        payDate: null,
        issScalePlan: null,
        issAmountTop: null,
        issScale: null,
        issTerm: null,
        specialTerm: null,
        bondGrade: null,
        enetityGrade: null,
        couponRate: null,
        ipoissYield: null,
        guideRate: null,
        guideRateMargin: null,
        floatRate: null,
        rateType: null,
        payFre: null,
        annoDate: null,
        issEndDate: null,
        ipoDate: null,
        ipoAddr: null,
        valueDate: null,
        expireDate: null,
        bondCodeList: null,
        fundUse: null,
        specialRule: null,
        addCreditMethod: null,
        assessOrgan: null,
        bondName: null,
        issorShortName: null,
        issorName: null,
        issorIndustryFirst: null,
        issorIndustrySecond: null,
        issorEntityNature: null,
        issorProvince: null,
        issorCity: null,
        sponsor: null,
        issSponsorGrade: null,
        sponsorEntityNature: null,
        assureRule: null,
        mainUnw: null,
        fundShare: null,
        coUnw: null,
        bookKeeper: null,
        issType: null,
        unwType: null,
        issPrice: null,
        target: null,
        bidType: null,
        bidRegion: null,
        bidDate: null,
        bidTime: null,
        bidAddr: null,
        winBidPrice: null,
        winBidRegion: null,
        subcMult: null,
        weightRate: null,
        fullCourtMult: null,
        margRate: null,
        margMult: null,
        issRate: null,
        unwMenber: null,
        issOnlineNub: null,
        onlineSubcCode: null,
        windBondTypeSecond: null,
        secondMixedCapitalBond: null,
        issStatus: 0,
        isCiBond: null,
        addIssStatus: 0,
        crossMarket: null,
        denom: null,
        govNewAmount: null,
        govReplaceAmount: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加新债发行-新发行债券-20220801-20220914";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIss(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改新债发行-新发行债券-20220801-20220914";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIss(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIss(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除新债发行-新发行债券-20220801-20220914编号为"' + ids + '"的数据项？').then(function() {
        return delIss(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/iss/export', {
        ...this.queryParams
      }, `iss_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
