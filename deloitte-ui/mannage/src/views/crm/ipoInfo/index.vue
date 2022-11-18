<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="stockName">
        <el-input
          v-model="queryParams.stockName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申购代码" prop="subsCode">
        <el-input
          v-model="queryParams.subsCode"
          placeholder="请输入申购代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="招股日期 yyyy-mm-dd" prop="prospDate">
        <el-date-picker clearable
          v-model="queryParams.prospDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择招股日期 yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="网上发行日期 yyyy-mm-dd" prop="netIssDate">
        <el-date-picker clearable
          v-model="queryParams.netIssDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择网上发行日期 yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上市日期 yyyy-mm-dd" prop="ipoDate">
        <el-date-picker clearable
          v-model="queryParams.ipoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上市日期 yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上市板" prop="ipoBoard">
        <el-input
          v-model="queryParams.ipoBoard"
          placeholder="请输入上市板"
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
      <el-form-item label="发行市盈率" prop="issMult">
        <el-input
          v-model="queryParams.issMult"
          placeholder="请输入发行市盈率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="行业PE(披露值)" prop="industryPe">
        <el-input
          v-model="queryParams.industryPe"
          placeholder="请输入行业PE(披露值)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="行业PE(近1月,静态)" prop="indusPeStat">
        <el-input
          v-model="queryParams.indusPeStat"
          placeholder="请输入行业PE(近1月,静态)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="可比上市公司PE均值" prop="ipoPeAvg">
        <el-input
          v-model="queryParams.ipoPeAvg"
          placeholder="请输入可比上市公司PE均值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="行业PE(近1月,TTM)" prop="indusPeTtm">
        <el-input
          v-model="queryParams.indusPeTtm"
          placeholder="请输入行业PE(近1月,TTM)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总计" prop="total">
        <el-input
          v-model="queryParams.total"
          placeholder="请输入总计"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="新股发行数量" prop="newStockIssnub">
        <el-input
          v-model="queryParams.newStockIssnub"
          placeholder="请输入新股发行数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="老股转让数量" prop="oldStockConvnub">
        <el-input
          v-model="queryParams.oldStockConvnub"
          placeholder="请输入老股转让数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网下配售数量" prop="offlinPlacnub">
        <el-input
          v-model="queryParams.offlinPlacnub"
          placeholder="请输入网下配售数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网上发行数量" prop="netIssnub">
        <el-input
          v-model="queryParams.netIssnub"
          placeholder="请输入网上发行数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回拨前网上发行股数" prop="callIssstockBefor">
        <el-input
          v-model="queryParams.callIssstockBefor"
          placeholder="请输入回拨前网上发行股数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回拨后网上发行股数" prop="callIssstockAfter">
        <el-input
          v-model="queryParams.callIssstockAfter"
          placeholder="请输入回拨后网上发行股数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回拨比例(%)" prop="callRatio">
        <el-input
          v-model="queryParams.callRatio"
          placeholder="请输入回拨比例(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网下申购上限" prop="offlinSubsMax">
        <el-input
          v-model="queryParams.offlinSubsMax"
          placeholder="请输入网下申购上限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网上发行申购上限" prop="netIsssubsMax">
        <el-input
          v-model="queryParams.netIsssubsMax"
          placeholder="请输入网上发行申购上限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预计募资(上市公司)" prop="predicFund">
        <el-input
          v-model="queryParams.predicFund"
          placeholder="请输入预计募资(上市公司)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="募资总额(上市公司)" prop="fundTotal">
        <el-input
          v-model="queryParams.fundTotal"
          placeholder="请输入募资总额(上市公司)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="募资净额(上市公司)" prop="fundNetAmount">
        <el-input
          v-model="queryParams.fundNetAmount"
          placeholder="请输入募资净额(上市公司)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目募投资金(上市公司)" prop="prsFund">
        <el-input
          v-model="queryParams.prsFund"
          placeholder="请输入项目募投资金(上市公司)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上市公司发行费用" prop="ipoIssFees">
        <el-input
          v-model="queryParams.ipoIssFees"
          placeholder="请输入上市公司发行费用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上市公司发行费率(%)" prop="ipoIssMult">
        <el-input
          v-model="queryParams.ipoIssMult"
          placeholder="请输入上市公司发行费率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="老股转让费用" prop="stackConvFees">
        <el-input
          v-model="queryParams.stackConvFees"
          placeholder="请输入老股转让费用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转让费率(%)" prop="convFees">
        <el-input
          v-model="queryParams.convFees"
          placeholder="请输入转让费率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行费用合计" prop="issFeesTotal">
        <el-input
          v-model="queryParams.issFeesTotal"
          placeholder="请输入发行费用合计"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行费率(%)" prop="issRates">
        <el-input
          v-model="queryParams.issRates"
          placeholder="请输入发行费率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承销及保荐费" prop="unwSponsorFees">
        <el-input
          v-model="queryParams.unwSponsorFees"
          placeholder="请输入承销及保荐费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审计及验资费" prop="auditVerFees">
        <el-input
          v-model="queryParams.auditVerFees"
          placeholder="请输入审计及验资费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法律费用" prop="lawFees">
        <el-input
          v-model="queryParams.lawFees"
          placeholder="请输入法律费用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="信息披露费" prop="inforDisFees">
        <el-input
          v-model="queryParams.inforDisFees"
          placeholder="请输入信息披露费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投资者数量(家)" prop="investorsNub">
        <el-input
          v-model="queryParams.investorsNub"
          placeholder="请输入投资者数量(家)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配售对象数量(个)" prop="placObjNub">
        <el-input
          v-model="queryParams.placObjNub"
          placeholder="请输入配售对象数量(个)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="询价申购数量(万股)" prop="inquirePurNub">
        <el-input
          v-model="queryParams.inquirePurNub"
          placeholder="请输入询价申购数量(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="询价认购倍数" prop="inquireSubcMult">
        <el-input
          v-model="queryParams.inquireSubcMult"
          placeholder="请输入询价认购倍数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效报价配售对象家数" prop="validQuotePlacnub">
        <el-input
          v-model="queryParams.validQuotePlacnub"
          placeholder="请输入有效报价配售对象家数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效报价投资者户数" prop="validQuoteInvenub">
        <el-input
          v-model="queryParams.validQuoteInvenub"
          placeholder="请输入有效报价投资者户数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效申购配售对象家数" prop="validPlacsubsNub">
        <el-input
          v-model="queryParams.validPlacsubsNub"
          placeholder="请输入有效申购配售对象家数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效申购数量(万股)" prop="validSubsNub">
        <el-input
          v-model="queryParams.validSubsNub"
          placeholder="请输入有效申购数量(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效申购资金(亿元)" prop="validSubsFund">
        <el-input
          v-model="queryParams.validSubsFund"
          placeholder="请输入有效申购资金(亿元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效申购获配比例(%)" prop="validSubsPlacratio">
        <el-input
          v-model="queryParams.validSubsPlacratio"
          placeholder="请输入有效申购获配比例(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="认购倍数(网下)" prop="offlinSubsMult">
        <el-input
          v-model="queryParams.offlinSubsMult"
          placeholder="请输入认购倍数(网下)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="战略配售获配股份占比" prop="tacticPlacStakeratio">
        <el-input
          v-model="queryParams.tacticPlacStakeratio"
          placeholder="请输入战略配售获配股份占比"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="A类法人投资者数量" prop="legalInvesnubA">
        <el-input
          v-model="queryParams.legalInvesnubA"
          placeholder="请输入A类法人投资者数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="B类法人投资者数量" prop="legalInvesnubB">
        <el-input
          v-model="queryParams.legalInvesnubB"
          placeholder="请输入B类法人投资者数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="C类法人投资者数量" prop="legalInvesnubC">
        <el-input
          v-model="queryParams.legalInvesnubC"
          placeholder="请输入C类法人投资者数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="A类法人投资者获配数量总计(万股)" prop="legalInvesPlacnubA">
        <el-input
          v-model="queryParams.legalInvesPlacnubA"
          placeholder="请输入A类法人投资者获配数量总计(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="B类法人投资者获配数量总计(万股)" prop="legalInvesPlacnubB">
        <el-input
          v-model="queryParams.legalInvesPlacnubB"
          placeholder="请输入B类法人投资者获配数量总计(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="C类法人投资者获配数量总计(万股)" prop="legalInvesPlacnubC">
        <el-input
          v-model="queryParams.legalInvesPlacnubC"
          placeholder="请输入C类法人投资者获配数量总计(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效申购户数" prop="validSubsInvenub">
        <el-input
          v-model="queryParams.validSubsInvenub"
          placeholder="请输入有效申购户数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效申购股数(万股)" prop="validSubsStakernub">
        <el-input
          v-model="queryParams.validSubsStakernub"
          placeholder="请输入有效申购股数(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中签率(%)" prop="checkRatio">
        <el-input
          v-model="queryParams.checkRatio"
          placeholder="请输入中签率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="认购倍数(网上)" prop="netCheckRatio">
        <el-input
          v-model="queryParams.netCheckRatio"
          placeholder="请输入认购倍数(网上)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行方式" prop="issWay">
        <el-input
          v-model="queryParams.issWay"
          placeholder="请输入发行方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承销方式" prop="unwWay">
        <el-input
          v-model="queryParams.unwWay"
          placeholder="请输入承销方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主承销商" prop="unwMaster">
        <el-input
          v-model="queryParams.unwMaster"
          placeholder="请输入主承销商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保荐机构" prop="sponsorInst">
        <el-input
          v-model="queryParams.sponsorInst"
          placeholder="请输入保荐机构"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审计机构" prop="auditInst">
        <el-input
          v-model="queryParams.auditInst"
          placeholder="请输入审计机构"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资产评估机构" prop="assetsEvaIns">
        <el-input
          v-model="queryParams.assetsEvaIns"
          placeholder="请输入资产评估机构"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="律师事务所" prop="lawFirm">
        <el-input
          v-model="queryParams.lawFirm"
          placeholder="请输入律师事务所"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发行公告日期yyyy-mm-dd" prop="issAnnoDate">
        <el-date-picker clearable
          v-model="queryParams.issAnnoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发行公告日期yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="网下发行起始日期yyyy-mm-dd" prop="offlinIssStardate">
        <el-date-picker clearable
          v-model="queryParams.offlinIssStardate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择网下发行起始日期yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="网下发行截止日期yyyy-mm-dd" prop="offlinIssEnddate">
        <el-date-picker clearable
          v-model="queryParams.offlinIssEnddate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择网下发行截止日期yyyy-mm-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="网上发行冻结资金(亿元)" prop="netIssFreeze">
        <el-input
          v-model="queryParams.netIssFreeze"
          placeholder="请输入网上发行冻结资金(亿元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网下配售冻结资金(亿元)" prop="offlinPlacFreeze">
        <el-input
          v-model="queryParams.offlinPlacFreeze"
          placeholder="请输入网下配售冻结资金(亿元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承销商认购余股(万股)" prop="unwSubcBalanstock">
        <el-input
          v-model="queryParams.unwSubcBalanstock"
          placeholder="请输入承销商认购余股(万股)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="包销金额(万元)" prop="underAmount">
        <el-input
          v-model="queryParams.underAmount"
          placeholder="请输入包销金额(万元)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="包销比例" prop="underRatio">
        <el-input
          v-model="queryParams.underRatio"
          placeholder="请输入包销比例"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="面值" prop="faceValue">
        <el-input
          v-model="queryParams.faceValue"
          placeholder="请输入面值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="省份" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证监会行业(2012版)" prop="csrcIndustry">
        <el-input
          v-model="queryParams.csrcIndustry"
          placeholder="请输入证监会行业(2012版)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Wind行业" prop="windIndustry">
        <el-input
          v-model="queryParams.windIndustry"
          placeholder="请输入Wind行业"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易所" prop="bourse">
        <el-input
          v-model="queryParams.bourse"
          placeholder="请输入交易所"
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
          v-hasPermi="['crm:ipoInfo:add']"
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
          v-hasPermi="['crm:ipoInfo:edit']"
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
          v-hasPermi="['crm:ipoInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:ipoInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ipoInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="代码" align="center" prop="code" />
      <el-table-column label="名称" align="center" prop="stockName" />
      <el-table-column label="申购代码" align="center" prop="subsCode" />
      <el-table-column label="招股日期 yyyy-mm-dd" align="center" prop="prospDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.prospDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="网上发行日期 yyyy-mm-dd" align="center" prop="netIssDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.netIssDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上市日期 yyyy-mm-dd" align="center" prop="ipoDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ipoDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上市板" align="center" prop="ipoBoard" />
      <el-table-column label="发行价格" align="center" prop="issPrice" />
      <el-table-column label="发行市盈率" align="center" prop="issMult" />
      <el-table-column label="行业PE(披露值)" align="center" prop="industryPe" />
      <el-table-column label="行业PE(近1月,静态)" align="center" prop="indusPeStat" />
      <el-table-column label="可比上市公司PE均值" align="center" prop="ipoPeAvg" />
      <el-table-column label="行业PE(近1月,TTM)" align="center" prop="indusPeTtm" />
      <el-table-column label="总计" align="center" prop="total" />
      <el-table-column label="新股发行数量" align="center" prop="newStockIssnub" />
      <el-table-column label="老股转让数量" align="center" prop="oldStockConvnub" />
      <el-table-column label="网下配售数量" align="center" prop="offlinPlacnub" />
      <el-table-column label="网上发行数量" align="center" prop="netIssnub" />
      <el-table-column label="回拨前网上发行股数" align="center" prop="callIssstockBefor" />
      <el-table-column label="回拨后网上发行股数" align="center" prop="callIssstockAfter" />
      <el-table-column label="回拨比例(%)" align="center" prop="callRatio" />
      <el-table-column label="网下申购上限" align="center" prop="offlinSubsMax" />
      <el-table-column label="网上发行申购上限" align="center" prop="netIsssubsMax" />
      <el-table-column label="预计募资(上市公司)" align="center" prop="predicFund" />
      <el-table-column label="募资总额(上市公司)" align="center" prop="fundTotal" />
      <el-table-column label="募资净额(上市公司)" align="center" prop="fundNetAmount" />
      <el-table-column label="项目募投资金(上市公司)" align="center" prop="prsFund" />
      <el-table-column label="上市公司发行费用" align="center" prop="ipoIssFees" />
      <el-table-column label="上市公司发行费率(%)" align="center" prop="ipoIssMult" />
      <el-table-column label="老股转让费用" align="center" prop="stackConvFees" />
      <el-table-column label="转让费率(%)" align="center" prop="convFees" />
      <el-table-column label="发行费用合计" align="center" prop="issFeesTotal" />
      <el-table-column label="发行费率(%)" align="center" prop="issRates" />
      <el-table-column label="承销及保荐费" align="center" prop="unwSponsorFees" />
      <el-table-column label="审计及验资费" align="center" prop="auditVerFees" />
      <el-table-column label="法律费用" align="center" prop="lawFees" />
      <el-table-column label="信息披露费" align="center" prop="inforDisFees" />
      <el-table-column label="投资者数量(家)" align="center" prop="investorsNub" />
      <el-table-column label="配售对象数量(个)" align="center" prop="placObjNub" />
      <el-table-column label="询价申购数量(万股)" align="center" prop="inquirePurNub" />
      <el-table-column label="询价认购倍数" align="center" prop="inquireSubcMult" />
      <el-table-column label="有效报价配售对象家数" align="center" prop="validQuotePlacnub" />
      <el-table-column label="有效报价投资者户数" align="center" prop="validQuoteInvenub" />
      <el-table-column label="有效申购配售对象家数" align="center" prop="validPlacsubsNub" />
      <el-table-column label="有效申购数量(万股)" align="center" prop="validSubsNub" />
      <el-table-column label="有效申购资金(亿元)" align="center" prop="validSubsFund" />
      <el-table-column label="有效申购获配比例(%)" align="center" prop="validSubsPlacratio" />
      <el-table-column label="认购倍数(网下)" align="center" prop="offlinSubsMult" />
      <el-table-column label="战略配售获配股份占比" align="center" prop="tacticPlacStakeratio" />
      <el-table-column label="A类法人投资者数量" align="center" prop="legalInvesnubA" />
      <el-table-column label="B类法人投资者数量" align="center" prop="legalInvesnubB" />
      <el-table-column label="C类法人投资者数量" align="center" prop="legalInvesnubC" />
      <el-table-column label="A类法人投资者获配数量总计(万股)" align="center" prop="legalInvesPlacnubA" />
      <el-table-column label="B类法人投资者获配数量总计(万股)" align="center" prop="legalInvesPlacnubB" />
      <el-table-column label="C类法人投资者获配数量总计(万股)" align="center" prop="legalInvesPlacnubC" />
      <el-table-column label="有效申购户数" align="center" prop="validSubsInvenub" />
      <el-table-column label="有效申购股数(万股)" align="center" prop="validSubsStakernub" />
      <el-table-column label="中签率(%)" align="center" prop="checkRatio" />
      <el-table-column label="认购倍数(网上)" align="center" prop="netCheckRatio" />
      <el-table-column label="发行方式" align="center" prop="issWay" />
      <el-table-column label="承销方式" align="center" prop="unwWay" />
      <el-table-column label="主承销商" align="center" prop="unwMaster" />
      <el-table-column label="保荐机构" align="center" prop="sponsorInst" />
      <el-table-column label="审计机构" align="center" prop="auditInst" />
      <el-table-column label="资产评估机构" align="center" prop="assetsEvaIns" />
      <el-table-column label="律师事务所" align="center" prop="lawFirm" />
      <el-table-column label="发行公告日期yyyy-mm-dd" align="center" prop="issAnnoDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issAnnoDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="网下发行起始日期yyyy-mm-dd" align="center" prop="offlinIssStardate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.offlinIssStardate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="网下发行截止日期yyyy-mm-dd" align="center" prop="offlinIssEnddate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.offlinIssEnddate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="网上发行冻结资金(亿元)" align="center" prop="netIssFreeze" />
      <el-table-column label="网下配售冻结资金(亿元)" align="center" prop="offlinPlacFreeze" />
      <el-table-column label="承销商认购余股(万股)" align="center" prop="unwSubcBalanstock" />
      <el-table-column label="包销金额(万元)" align="center" prop="underAmount" />
      <el-table-column label="包销比例" align="center" prop="underRatio" />
      <el-table-column label="面值" align="center" prop="faceValue" />
      <el-table-column label="省份" align="center" prop="province" />
      <el-table-column label="证监会行业(2012版)" align="center" prop="csrcIndustry" />
      <el-table-column label="Wind行业" align="center" prop="windIndustry" />
      <el-table-column label="证券类型" align="center" prop="bondType" />
      <el-table-column label="交易所" align="center" prop="bourse" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:ipoInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:ipoInfo:remove']"
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

    <!-- 添加或修改IPO-新股发行资料-20210914-20221014对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入代码" />
        </el-form-item>
        <el-form-item label="名称" prop="stockName">
          <el-input v-model="form.stockName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="申购代码" prop="subsCode">
          <el-input v-model="form.subsCode" placeholder="请输入申购代码" />
        </el-form-item>
        <el-form-item label="招股日期 yyyy-mm-dd" prop="prospDate">
          <el-date-picker clearable
            v-model="form.prospDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择招股日期 yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="网上发行日期 yyyy-mm-dd" prop="netIssDate">
          <el-date-picker clearable
            v-model="form.netIssDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择网上发行日期 yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上市日期 yyyy-mm-dd" prop="ipoDate">
          <el-date-picker clearable
            v-model="form.ipoDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上市日期 yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上市板" prop="ipoBoard">
          <el-input v-model="form.ipoBoard" placeholder="请输入上市板" />
        </el-form-item>
        <el-form-item label="发行价格" prop="issPrice">
          <el-input v-model="form.issPrice" placeholder="请输入发行价格" />
        </el-form-item>
        <el-form-item label="发行市盈率" prop="issMult">
          <el-input v-model="form.issMult" placeholder="请输入发行市盈率" />
        </el-form-item>
        <el-form-item label="行业PE(披露值)" prop="industryPe">
          <el-input v-model="form.industryPe" placeholder="请输入行业PE(披露值)" />
        </el-form-item>
        <el-form-item label="行业PE(近1月,静态)" prop="indusPeStat">
          <el-input v-model="form.indusPeStat" placeholder="请输入行业PE(近1月,静态)" />
        </el-form-item>
        <el-form-item label="可比上市公司PE均值" prop="ipoPeAvg">
          <el-input v-model="form.ipoPeAvg" placeholder="请输入可比上市公司PE均值" />
        </el-form-item>
        <el-form-item label="行业PE(近1月,TTM)" prop="indusPeTtm">
          <el-input v-model="form.indusPeTtm" placeholder="请输入行业PE(近1月,TTM)" />
        </el-form-item>
        <el-form-item label="总计" prop="total">
          <el-input v-model="form.total" placeholder="请输入总计" />
        </el-form-item>
        <el-form-item label="新股发行数量" prop="newStockIssnub">
          <el-input v-model="form.newStockIssnub" placeholder="请输入新股发行数量" />
        </el-form-item>
        <el-form-item label="老股转让数量" prop="oldStockConvnub">
          <el-input v-model="form.oldStockConvnub" placeholder="请输入老股转让数量" />
        </el-form-item>
        <el-form-item label="网下配售数量" prop="offlinPlacnub">
          <el-input v-model="form.offlinPlacnub" placeholder="请输入网下配售数量" />
        </el-form-item>
        <el-form-item label="网上发行数量" prop="netIssnub">
          <el-input v-model="form.netIssnub" placeholder="请输入网上发行数量" />
        </el-form-item>
        <el-form-item label="回拨前网上发行股数" prop="callIssstockBefor">
          <el-input v-model="form.callIssstockBefor" placeholder="请输入回拨前网上发行股数" />
        </el-form-item>
        <el-form-item label="回拨后网上发行股数" prop="callIssstockAfter">
          <el-input v-model="form.callIssstockAfter" placeholder="请输入回拨后网上发行股数" />
        </el-form-item>
        <el-form-item label="回拨比例(%)" prop="callRatio">
          <el-input v-model="form.callRatio" placeholder="请输入回拨比例(%)" />
        </el-form-item>
        <el-form-item label="网下申购上限" prop="offlinSubsMax">
          <el-input v-model="form.offlinSubsMax" placeholder="请输入网下申购上限" />
        </el-form-item>
        <el-form-item label="网上发行申购上限" prop="netIsssubsMax">
          <el-input v-model="form.netIsssubsMax" placeholder="请输入网上发行申购上限" />
        </el-form-item>
        <el-form-item label="预计募资(上市公司)" prop="predicFund">
          <el-input v-model="form.predicFund" placeholder="请输入预计募资(上市公司)" />
        </el-form-item>
        <el-form-item label="募资总额(上市公司)" prop="fundTotal">
          <el-input v-model="form.fundTotal" placeholder="请输入募资总额(上市公司)" />
        </el-form-item>
        <el-form-item label="募资净额(上市公司)" prop="fundNetAmount">
          <el-input v-model="form.fundNetAmount" placeholder="请输入募资净额(上市公司)" />
        </el-form-item>
        <el-form-item label="项目募投资金(上市公司)" prop="prsFund">
          <el-input v-model="form.prsFund" placeholder="请输入项目募投资金(上市公司)" />
        </el-form-item>
        <el-form-item label="上市公司发行费用" prop="ipoIssFees">
          <el-input v-model="form.ipoIssFees" placeholder="请输入上市公司发行费用" />
        </el-form-item>
        <el-form-item label="上市公司发行费率(%)" prop="ipoIssMult">
          <el-input v-model="form.ipoIssMult" placeholder="请输入上市公司发行费率(%)" />
        </el-form-item>
        <el-form-item label="老股转让费用" prop="stackConvFees">
          <el-input v-model="form.stackConvFees" placeholder="请输入老股转让费用" />
        </el-form-item>
        <el-form-item label="转让费率(%)" prop="convFees">
          <el-input v-model="form.convFees" placeholder="请输入转让费率(%)" />
        </el-form-item>
        <el-form-item label="发行费用合计" prop="issFeesTotal">
          <el-input v-model="form.issFeesTotal" placeholder="请输入发行费用合计" />
        </el-form-item>
        <el-form-item label="发行费率(%)" prop="issRates">
          <el-input v-model="form.issRates" placeholder="请输入发行费率(%)" />
        </el-form-item>
        <el-form-item label="承销及保荐费" prop="unwSponsorFees">
          <el-input v-model="form.unwSponsorFees" placeholder="请输入承销及保荐费" />
        </el-form-item>
        <el-form-item label="审计及验资费" prop="auditVerFees">
          <el-input v-model="form.auditVerFees" placeholder="请输入审计及验资费" />
        </el-form-item>
        <el-form-item label="法律费用" prop="lawFees">
          <el-input v-model="form.lawFees" placeholder="请输入法律费用" />
        </el-form-item>
        <el-form-item label="信息披露费" prop="inforDisFees">
          <el-input v-model="form.inforDisFees" placeholder="请输入信息披露费" />
        </el-form-item>
        <el-form-item label="投资者数量(家)" prop="investorsNub">
          <el-input v-model="form.investorsNub" placeholder="请输入投资者数量(家)" />
        </el-form-item>
        <el-form-item label="配售对象数量(个)" prop="placObjNub">
          <el-input v-model="form.placObjNub" placeholder="请输入配售对象数量(个)" />
        </el-form-item>
        <el-form-item label="询价申购数量(万股)" prop="inquirePurNub">
          <el-input v-model="form.inquirePurNub" placeholder="请输入询价申购数量(万股)" />
        </el-form-item>
        <el-form-item label="询价认购倍数" prop="inquireSubcMult">
          <el-input v-model="form.inquireSubcMult" placeholder="请输入询价认购倍数" />
        </el-form-item>
        <el-form-item label="有效报价配售对象家数" prop="validQuotePlacnub">
          <el-input v-model="form.validQuotePlacnub" placeholder="请输入有效报价配售对象家数" />
        </el-form-item>
        <el-form-item label="有效报价投资者户数" prop="validQuoteInvenub">
          <el-input v-model="form.validQuoteInvenub" placeholder="请输入有效报价投资者户数" />
        </el-form-item>
        <el-form-item label="有效申购配售对象家数" prop="validPlacsubsNub">
          <el-input v-model="form.validPlacsubsNub" placeholder="请输入有效申购配售对象家数" />
        </el-form-item>
        <el-form-item label="有效申购数量(万股)" prop="validSubsNub">
          <el-input v-model="form.validSubsNub" placeholder="请输入有效申购数量(万股)" />
        </el-form-item>
        <el-form-item label="有效申购资金(亿元)" prop="validSubsFund">
          <el-input v-model="form.validSubsFund" placeholder="请输入有效申购资金(亿元)" />
        </el-form-item>
        <el-form-item label="有效申购获配比例(%)" prop="validSubsPlacratio">
          <el-input v-model="form.validSubsPlacratio" placeholder="请输入有效申购获配比例(%)" />
        </el-form-item>
        <el-form-item label="认购倍数(网下)" prop="offlinSubsMult">
          <el-input v-model="form.offlinSubsMult" placeholder="请输入认购倍数(网下)" />
        </el-form-item>
        <el-form-item label="战略配售获配股份占比" prop="tacticPlacStakeratio">
          <el-input v-model="form.tacticPlacStakeratio" placeholder="请输入战略配售获配股份占比" />
        </el-form-item>
        <el-form-item label="A类法人投资者数量" prop="legalInvesnubA">
          <el-input v-model="form.legalInvesnubA" placeholder="请输入A类法人投资者数量" />
        </el-form-item>
        <el-form-item label="B类法人投资者数量" prop="legalInvesnubB">
          <el-input v-model="form.legalInvesnubB" placeholder="请输入B类法人投资者数量" />
        </el-form-item>
        <el-form-item label="C类法人投资者数量" prop="legalInvesnubC">
          <el-input v-model="form.legalInvesnubC" placeholder="请输入C类法人投资者数量" />
        </el-form-item>
        <el-form-item label="A类法人投资者获配数量总计(万股)" prop="legalInvesPlacnubA">
          <el-input v-model="form.legalInvesPlacnubA" placeholder="请输入A类法人投资者获配数量总计(万股)" />
        </el-form-item>
        <el-form-item label="B类法人投资者获配数量总计(万股)" prop="legalInvesPlacnubB">
          <el-input v-model="form.legalInvesPlacnubB" placeholder="请输入B类法人投资者获配数量总计(万股)" />
        </el-form-item>
        <el-form-item label="C类法人投资者获配数量总计(万股)" prop="legalInvesPlacnubC">
          <el-input v-model="form.legalInvesPlacnubC" placeholder="请输入C类法人投资者获配数量总计(万股)" />
        </el-form-item>
        <el-form-item label="有效申购户数" prop="validSubsInvenub">
          <el-input v-model="form.validSubsInvenub" placeholder="请输入有效申购户数" />
        </el-form-item>
        <el-form-item label="有效申购股数(万股)" prop="validSubsStakernub">
          <el-input v-model="form.validSubsStakernub" placeholder="请输入有效申购股数(万股)" />
        </el-form-item>
        <el-form-item label="中签率(%)" prop="checkRatio">
          <el-input v-model="form.checkRatio" placeholder="请输入中签率(%)" />
        </el-form-item>
        <el-form-item label="认购倍数(网上)" prop="netCheckRatio">
          <el-input v-model="form.netCheckRatio" placeholder="请输入认购倍数(网上)" />
        </el-form-item>
        <el-form-item label="发行方式" prop="issWay">
          <el-input v-model="form.issWay" placeholder="请输入发行方式" />
        </el-form-item>
        <el-form-item label="承销方式" prop="unwWay">
          <el-input v-model="form.unwWay" placeholder="请输入承销方式" />
        </el-form-item>
        <el-form-item label="主承销商" prop="unwMaster">
          <el-input v-model="form.unwMaster" placeholder="请输入主承销商" />
        </el-form-item>
        <el-form-item label="保荐机构" prop="sponsorInst">
          <el-input v-model="form.sponsorInst" placeholder="请输入保荐机构" />
        </el-form-item>
        <el-form-item label="审计机构" prop="auditInst">
          <el-input v-model="form.auditInst" placeholder="请输入审计机构" />
        </el-form-item>
        <el-form-item label="资产评估机构" prop="assetsEvaIns">
          <el-input v-model="form.assetsEvaIns" placeholder="请输入资产评估机构" />
        </el-form-item>
        <el-form-item label="律师事务所" prop="lawFirm">
          <el-input v-model="form.lawFirm" placeholder="请输入律师事务所" />
        </el-form-item>
        <el-form-item label="发行公告日期yyyy-mm-dd" prop="issAnnoDate">
          <el-date-picker clearable
            v-model="form.issAnnoDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发行公告日期yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="网下发行起始日期yyyy-mm-dd" prop="offlinIssStardate">
          <el-date-picker clearable
            v-model="form.offlinIssStardate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择网下发行起始日期yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="网下发行截止日期yyyy-mm-dd" prop="offlinIssEnddate">
          <el-date-picker clearable
            v-model="form.offlinIssEnddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择网下发行截止日期yyyy-mm-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="网上发行冻结资金(亿元)" prop="netIssFreeze">
          <el-input v-model="form.netIssFreeze" placeholder="请输入网上发行冻结资金(亿元)" />
        </el-form-item>
        <el-form-item label="网下配售冻结资金(亿元)" prop="offlinPlacFreeze">
          <el-input v-model="form.offlinPlacFreeze" placeholder="请输入网下配售冻结资金(亿元)" />
        </el-form-item>
        <el-form-item label="承销商认购余股(万股)" prop="unwSubcBalanstock">
          <el-input v-model="form.unwSubcBalanstock" placeholder="请输入承销商认购余股(万股)" />
        </el-form-item>
        <el-form-item label="包销金额(万元)" prop="underAmount">
          <el-input v-model="form.underAmount" placeholder="请输入包销金额(万元)" />
        </el-form-item>
        <el-form-item label="包销比例" prop="underRatio">
          <el-input v-model="form.underRatio" placeholder="请输入包销比例" />
        </el-form-item>
        <el-form-item label="面值" prop="faceValue">
          <el-input v-model="form.faceValue" placeholder="请输入面值" />
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="证监会行业(2012版)" prop="csrcIndustry">
          <el-input v-model="form.csrcIndustry" placeholder="请输入证监会行业(2012版)" />
        </el-form-item>
        <el-form-item label="Wind行业" prop="windIndustry">
          <el-input v-model="form.windIndustry" placeholder="请输入Wind行业" />
        </el-form-item>
        <el-form-item label="交易所" prop="bourse">
          <el-input v-model="form.bourse" placeholder="请输入交易所" />
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
import { listIpoInfo, getIpoInfo, delIpoInfo, addIpoInfo, updateIpoInfo } from "@/api/crm/ipoInfo";

export default {
  name: "IpoInfo",
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
      // IPO-新股发行资料-20210914-20221014表格数据
      ipoInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        stockName: null,
        subsCode: null,
        prospDate: null,
        netIssDate: null,
        ipoDate: null,
        ipoBoard: null,
        issPrice: null,
        issMult: null,
        industryPe: null,
        indusPeStat: null,
        ipoPeAvg: null,
        indusPeTtm: null,
        total: null,
        newStockIssnub: null,
        oldStockConvnub: null,
        offlinPlacnub: null,
        netIssnub: null,
        callIssstockBefor: null,
        callIssstockAfter: null,
        callRatio: null,
        offlinSubsMax: null,
        netIsssubsMax: null,
        predicFund: null,
        fundTotal: null,
        fundNetAmount: null,
        prsFund: null,
        ipoIssFees: null,
        ipoIssMult: null,
        stackConvFees: null,
        convFees: null,
        issFeesTotal: null,
        issRates: null,
        unwSponsorFees: null,
        auditVerFees: null,
        lawFees: null,
        inforDisFees: null,
        investorsNub: null,
        placObjNub: null,
        inquirePurNub: null,
        inquireSubcMult: null,
        validQuotePlacnub: null,
        validQuoteInvenub: null,
        validPlacsubsNub: null,
        validSubsNub: null,
        validSubsFund: null,
        validSubsPlacratio: null,
        offlinSubsMult: null,
        tacticPlacStakeratio: null,
        legalInvesnubA: null,
        legalInvesnubB: null,
        legalInvesnubC: null,
        legalInvesPlacnubA: null,
        legalInvesPlacnubB: null,
        legalInvesPlacnubC: null,
        validSubsInvenub: null,
        validSubsStakernub: null,
        checkRatio: null,
        netCheckRatio: null,
        issWay: null,
        unwWay: null,
        unwMaster: null,
        sponsorInst: null,
        auditInst: null,
        assetsEvaIns: null,
        lawFirm: null,
        issAnnoDate: null,
        offlinIssStardate: null,
        offlinIssEnddate: null,
        netIssFreeze: null,
        offlinPlacFreeze: null,
        unwSubcBalanstock: null,
        underAmount: null,
        underRatio: null,
        faceValue: null,
        province: null,
        csrcIndustry: null,
        windIndustry: null,
        bondType: null,
        bourse: null
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
    /** 查询IPO-新股发行资料-20210914-20221014列表 */
    getList() {
      this.loading = true;
      listIpoInfo(this.queryParams).then(response => {
        this.ipoInfoList = response.rows;
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
        code: null,
        stockName: null,
        subsCode: null,
        prospDate: null,
        netIssDate: null,
        ipoDate: null,
        ipoBoard: null,
        issPrice: null,
        issMult: null,
        industryPe: null,
        indusPeStat: null,
        ipoPeAvg: null,
        indusPeTtm: null,
        total: null,
        newStockIssnub: null,
        oldStockConvnub: null,
        offlinPlacnub: null,
        netIssnub: null,
        callIssstockBefor: null,
        callIssstockAfter: null,
        callRatio: null,
        offlinSubsMax: null,
        netIsssubsMax: null,
        predicFund: null,
        fundTotal: null,
        fundNetAmount: null,
        prsFund: null,
        ipoIssFees: null,
        ipoIssMult: null,
        stackConvFees: null,
        convFees: null,
        issFeesTotal: null,
        issRates: null,
        unwSponsorFees: null,
        auditVerFees: null,
        lawFees: null,
        inforDisFees: null,
        investorsNub: null,
        placObjNub: null,
        inquirePurNub: null,
        inquireSubcMult: null,
        validQuotePlacnub: null,
        validQuoteInvenub: null,
        validPlacsubsNub: null,
        validSubsNub: null,
        validSubsFund: null,
        validSubsPlacratio: null,
        offlinSubsMult: null,
        tacticPlacStakeratio: null,
        legalInvesnubA: null,
        legalInvesnubB: null,
        legalInvesnubC: null,
        legalInvesPlacnubA: null,
        legalInvesPlacnubB: null,
        legalInvesPlacnubC: null,
        validSubsInvenub: null,
        validSubsStakernub: null,
        checkRatio: null,
        netCheckRatio: null,
        issWay: null,
        unwWay: null,
        unwMaster: null,
        sponsorInst: null,
        auditInst: null,
        assetsEvaIns: null,
        lawFirm: null,
        issAnnoDate: null,
        offlinIssStardate: null,
        offlinIssEnddate: null,
        netIssFreeze: null,
        offlinPlacFreeze: null,
        unwSubcBalanstock: null,
        underAmount: null,
        underRatio: null,
        faceValue: null,
        province: null,
        csrcIndustry: null,
        windIndustry: null,
        bondType: null,
        bourse: null
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
      this.title = "添加IPO-新股发行资料-20210914-20221014";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIpoInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改IPO-新股发行资料-20210914-20221014";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIpoInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIpoInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除IPO-新股发行资料-20210914-20221014编号为"' + ids + '"的数据项？').then(function() {
        return delIpoInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/ipoInfo/export', {
        ...this.queryParams
      }, `ipoInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
