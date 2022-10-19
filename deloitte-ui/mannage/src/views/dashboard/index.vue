<template>
  <div class="app-container home">
    <h3 class="g-title">今日运维任务</h3>
    <div class="todo-desc">
      <div>HELLO!</div>
      <div>今日日期 {{ currentTime }}, {{ week }}。</div>
      <div>
        当前日期完成任务合计 {{ taskCount && taskCount.taskCount }} 条, 待完成 {{ taskCount && taskCount.taskNoCount }} 条，已完成 {{ taskCount && taskCount.taskCop }} 条，请尽快完成！
      </div>
      <span type="text">切换日期：</span>
      <el-date-picker v-model="monthDate" type="month" placeholder="选择月" value-format="yyyy-MM" @change="changeMonth">
      </el-date-picker>
    </div>
    <div class="table-box">
      <table border="1" id="table" @click="getDay"></table>
    </div>
    <h3 class="g-title">每日运维任务</h3>
    <div class="list-box">
      <el-table
        v-if="roleId === 'role1'"
        v-loading="loading"
        class="table-content"
        :data="list"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" sortable label="序号"> </el-table-column>
        <el-table-column prop="taskDesc" label="任务说明"> </el-table-column>
        <el-table-column prop="name" label="任务状态"> 
          <template slot-scope="scope">
            <span :class="scope.row.complete === scope.row.taskCount ? 'green' : 'red'">{{ scope.row.complete !== scope.row.taskCount ? '待完成( '+scope.row.complete+'/'+scope.row.taskCount+ ' )' : '已完成( '+scope.row.taskCount+'/'+scope.row.taskCount+ ' )' }}</span>
          </template>
        </el-table-column>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="work(scope.row)" type="text" size="small"
              >开始工作</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-table
        v-if="roleId === 'role6'"
        v-loading="loading"
        class="table-content"
        :data="list7"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" label="序号"> </el-table-column>
        <el-table-column prop="taskCategory" label="捕获渠道"> </el-table-column>
        <el-table-column prop="dataShow" label="任务说明"> 
        </el-table-column>
        <el-table-column prop="state" label="任务状态"> 
          <template slot-scope="scope">
            <span :class="scope.row.state === 0 ? 'red' : 'green'">{{ stateArr[scope.row.state] }}</span>
          </template>
        </el-table-column>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="addBody(scope.row)" type="text" size="small"
              >添加</el-button
            >
            <el-button @click="changeAddState(scope.row.id, 1)" type="text" size="small"
              >忽略</el-button
            >
            <el-button @click="detaile(scope.row)" type="text" size="small"
              >查看详情</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-table
      v-if="roleId === 'role2'"
        v-loading="loading"
        class="table-content"
        :data="list2"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" label="序号">
        </el-table-column>
        <el-table-column prop="sourceName" label="来源"> </el-table-column>
        <el-table-column prop="taskCategory" label="企业名称">
          <template slot-scope="scope">
            <span>{{ scope.row.entityName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="统一社会信用代码">
          <template slot-scope="scope">
            <span>{{ scope.row.creditCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataShow" label="任务状态"> 
          <template slot-scope="scope">
            <span :class="scope.row.status ? 'green' : 'red'">{{ scope.row.status === 1 ? '已完成' : '未完成' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="workRole2(scope.row)" type="text" size="small"
              >开始工作</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-table
      v-if="roleId === 'role3'"
        v-loading="loading"
        class="table-content"
        :data="list3"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" label="序号">
        </el-table-column>
        <el-table-column type="index" label="来源"> </el-table-column>
        <el-table-column prop="taskCategory" label="企业名称">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.entityName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="统一社会信用代码">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.creditCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="是否为金融机构">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.finance === 1 ? 'Y' : 'N' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataShow" label="城投机构对应地方政府名称">
          <template slot-scope="scope">
            <span>{{ scope.row.values[0] && scope.row.values[0].value }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataShow" label="任务状态"> 
          <template slot-scope="scope">
            <span :class="cope.row.crmSupplyTask &&  cope.row.crmSupplyTask.status ? 'green' : 'red'">{{ cope.row.crmSupplyTask &&  cope.row.crmSupplyTask.status === 1 ? '已完成' : '未完成' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="workRole3(scope.row)" type="text" size="small"
              >开始工作</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-table
      v-if="roleId === 'role4'"
        v-loading="loading"
        class="table-content"
        :data="list3"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" label="序号">
        </el-table-column>
        <el-table-column type="index" label="来源"> </el-table-column>
        <el-table-column prop="taskCategory" label="企业名称">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.entityName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="统一社会信用代码">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.creditCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="是否为金融机构">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.finance === 1 ? 'Y' : 'N' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataShow" label="城投机构对应地方政府名称">
          <template slot-scope="scope">
            <span>{{ scope.row.values[0] && scope.row.values[0].value }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataShow" label="任务状态"> 
          <template slot-scope="scope">
            <span :class="cope.row.crmSupplyTask &&  cope.row.crmSupplyTask.status ? 'green' : 'red'">{{ cope.row.crmSupplyTask &&  cope.row.crmSupplyTask.status === 1 ? '已完成' : '未完成' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="workRole4(scope.row)" type="text" size="small"
              >开始工作</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-table
      v-if="roleId === 'role5'"
        v-loading="loading"
        class="table-content"
        :data="list3"
        align="center"
        style="width: 98%; margin-top: 15px"
      >
        <el-table-column type="index" label="序号">
        </el-table-column>
        <el-table-column type="index" label="来源"> </el-table-column>
        <el-table-column prop="taskCategory" label="企业名称">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.entityName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="统一社会信用代码">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.creditCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="是否为城投机构">
          <template slot-scope="scope">
            <span>{{ scope.row.isUi }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskCategory" label="是否为金融机构">
          <template slot-scope="scope">
            <span>{{ scope.row.entityInfo && scope.row.entityInfo.finance === 1 ? 'Y' : 'N' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataShow" label="任务状态"> 
          <template slot-scope="scope">
            <span :class="cope.row.crmSupplyTask &&  cope.row.crmSupplyTask.status ? 'green' : 'red'">{{ cope.row.crmSupplyTask &&  cope.row.crmSupplyTask.status === 1 ? '已完成' : '未完成' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="province" label="任务操作">
          <template slot-scope="scope">
            <el-button @click="workRole5(scope.row)" type="text" size="small"
              >开始工作</el-button
            >
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
    </div>
    <el-dialog title="敞口划分" :visible.sync="dialogVisible" width="50%">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="185px"
        label-position="left"
      >
      <el-form-item label="企业名称" >
          <span>{{ ruleForm.entityName }}</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" >
          <span>{{ ruleForm.creditCode }}</span>
        </el-form-item>
        <el-form-item label="来源" >
          <span>{{ ruleForm.sourceName }}</span>
        </el-form-item>
        <el-form-item label="wind行业划分" >
          <span v-if="!edit1">{{ ruleForm.wind }}</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.wind"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="申万行业划分" >
          <span v-if="!edit2">{{ ruleForm.shenWan }}</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.shenWan"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="是否为金融机构" prop="delivery">
          <el-radio-group v-model="ruleForm.isFinance">
            <el-radio label="Y">是</el-radio>
            <el-radio label="N">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="金融细分领域" >
          <el-select v-model="ruleForm.financeSegmentation" placeholder="">
            <el-option  v-for="(item, index) in options1" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="YY-是否为城投机构" prop="delivery">
          <el-radio-group v-model="ruleForm.city">
            <el-radio label="Y">是</el-radio>
            <el-radio label="N">否</el-radio>
            <el-radio label="">不详</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="中诚信-是否为城投机构" prop="delivery">
          <el-radio-group v-model="ruleForm.cityZhong">
            <el-radio label="Y">是</el-radio>
            <el-radio label="N">否</el-radio>
            <el-radio label="">不详</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="IB-是否为城投机构" prop="delivery">
          <el-radio-group v-model="ruleForm.cityIb">
            <el-radio label="Y">是</el-radio>
            <el-radio label="N">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="机构对应域投政府" required>
          <el-col :span="6">
            <el-select v-model="ruleForm.region" placeholder="选择省份" @change="getCity">
              <el-option v-for="(item, index) in region" :key="index" :label="item.govName" :value="item"></el-option>
            </el-select>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="6">
            <el-select v-model="ruleForm.city" placeholder="选择地区" @change="getCounty">
              <el-option v-for="(item, index) in city" :key="index" :label="item.govName" :value="item"></el-option>
            </el-select>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="6">
            <el-select v-model="ruleForm.county" placeholder="选择县" @change="getGov">
              <el-option v-for="(item, index) in county" :key="index" :label="item.govName" :value="item"></el-option>
            </el-select>
          </el-col>
          <el-col class="line" :span="1">
            <el-button
            style="margin-left: 5px"
            type="text"
            @click="addGovernment"
              >新增地方政府</el-button
              ></el-col
          >
        </el-form-item>

        <el-form-item label="机构对应域投政府行政代码" prop="delivery">
          <span>{{ ruleForm.govCode || '(-)' }}</span>
        </el-form-item>
        <el-form-item label="机构对应域投政府德勤内部代码" prop="delivery">
          <span>{{ ruleForm.dqGovCode || '(-)' }}</span>
        </el-form-item>
        <el-form-item label="敞口划分" >
          <el-select v-model="ruleForm.masterCode" placeholder="">
            <el-option  v-for="(item, index) in options2" :key="index" :label="item.masterName" :value="item.masterCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.remarks"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="subRule2"
          >确认新增</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="补充金融机构相关信息"
      :visible.sync="remarkDig"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="企业名称" >
          <span>{{ ruleForm.entityName }}</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" >
          <span>{{ ruleForm.creditCode }}</span>
        </el-form-item>
        <el-form-item label="来源" >
          <span>{{ ruleForm.source }}</span>
        </el-form-item>
        <el-form-item label="wind行业划分" >
          <span v-if="!edit1">{{ ruleForm.windMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.windMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="申万行业划分" >
          <span v-if="!edit2">{{ ruleForm.shenWanMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.shenWanMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="所属地区">
          <el-col :span="6">
            <el-select v-model="ruleForm.region" placeholder="选择省份" @change="getCity">
              <el-option v-for="(item, index) in region" :key="index" :label="item.govName" :value="item"></el-option>
            </el-select>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="6">
            <el-select v-model="ruleForm.city" placeholder="选择地区" @change="getCounty">
              <el-option v-for="(item, index) in city" :key="index" :label="item.govName" :value="item"></el-option>
            </el-select>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="6">
            <el-select v-model="ruleForm.county" placeholder="选择县">
              <el-option v-for="(item, index) in county" :key="index" :label="item.govName" :value="item.govName"></el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="所属辖区" >
          <el-select class="width146" v-model="ruleForm.belJurisdiction" :multiple="xmultiple" placeholder="选择辖区">
            <el-option v-for="(item, index) in jurisdiction" :key="index" :label="item.value" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="对口监管机构">
          <el-select class="width146" v-model="ruleForm.regulators" :multiple="jmultiple"  placeholder="选择机构">
            <el-option v-for="(item, index) in supervise" :key="index" :label="item.value" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.remarks"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="subRule345(3)"
          >保存并提交</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="新增地方政府"
      :visible.sync="addGovernmentDig"
      width="50%"
    >
      <el-form
        :model="addGovForm"
        :rules="rules"
        ref="addGovForm"
        label-width="170px"
        label-position="left"
      >
        <el-form-item label="新增地方政府级别-大类" >
          <el-select class="width320" v-model="addGovForm.govLevelBig" @change="getSmall">
            <el-option v-for="(item, index) in govOption1" :key="index" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新增地方政府级别-小类" >
          <el-select class="width320" v-model="addGovForm.govLevelSmall" placeholder="">
            <el-option v-for="(item, index) in govOption2" :key="index" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新增地方政府名称" >
          <el-input
            class="t-input width320"
            v-model="addGovForm.govName"
            placeholder="请输入新增政府全称"
            @change="changeCode(1)"
          ></el-input>
          <el-button
            v-if="repalce1 === 0"
            style="margin-left: 5px"
            type="text"
            @click="checkReplace(addGovForm.name, 'GOV_NAME', 1)"
            >查重</el-button
          >
          <span class="red" v-if="repalce1 === 2">存在重复无法添加</span>
          <span class="green" v-if="repalce1 === 1">无重复，可新增</span>
        </el-form-item>
        <el-form-item label="新增地方政府行政编码" >
          <el-input
            class="t-input width320"
            v-model="addGovForm.govCode"
            placeholder="请输入新增政府官方新增编码"
            :disabled="noUse"
            @change="changeCode(2)"
          ></el-input>
          <el-button
            v-if="repalce2 === 0"
            style="margin-left: 5px"
            type="text"
            @click="checkReplace(addGovForm.code, 'GOV_CODE', 2)"
            >查重</el-button
          >
          <span class="red" v-if="repalce2 === 2">存在重复无法添加</span>
          <span class="green" v-if="repalce2 === 1">无重复，可新增</span>
        </el-form-item>
        <el-form-item label="" prop="delivery">
          <el-radio
            style="margin-left: 5px; margin-top: 9px"
            v-model="noUse"
            :label="true"
            >不适用</el-radio
          >
        </el-form-item>
        <el-form-item label="上级地方政府名称" prop="delivery">
          <span v-if="!addGovForm.preGovName">（通过下方输入官方行政编码进行查询后自动填入）</span>
          <span v-if="addGovForm.preGovName">{{ addGovForm.preGovName }}</span>
        </el-form-item>
        <el-form-item label="上级地方政府行政编码" prop="delivery">
          <el-input class="t-input width320" v-model="addGovForm.preGovCode"></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="selectCode(addGovForm.preGovCode)"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="subGov"
          >确认新增</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="补充城投及政府相关信息"
      :visible.sync="governmentDig"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="170px"
        label-position="left"
      >
      <el-form-item label="企业名称" >
          <span>{{ ruleForm.entityName }}</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" >
          <span>{{ ruleForm.creditCode }}</span>
        </el-form-item>
        <el-form-item label="来源" >
          <span>{{ ruleForm.source }}</span>
        </el-form-item>
        <el-form-item label="wind行业划分" >
          <span v-if="!edit1">{{ ruleForm.windMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.windMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="申万行业划分" >
          <span v-if="!edit2">{{ ruleForm.shenWanMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.shenWanMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="政府持股方式" >
          <el-select class="width320" v-model="ruleForm.shareMethod" placeholder="选择省份">
            <el-option v-for="(item, index) in region" :key="index" :label="item.govName" :value="item.govName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="政府对当前城投支持力度" prop="delivery">
          <el-input
          class="t-input width320"
            type="text"
            v-model="ruleForm.support"
          ></el-input>
        </el-form-item>
        <el-form-item label="政府对当前城投支持力度判断依据" prop="delivery">
          <el-input
          class="t-input width320"
            type="text"
            v-model="ruleForm.judgment"
          ></el-input>
        </el-form-item>
        <el-form-item label="政府部门实际持股比例" prop="delivery">
          <el-input
          class="t-input width320"
            type="text"
            v-model="ruleForm.shareRatio"
          ></el-input>
        </el-form-item>
        <el-form-item label="政府部门实际持股比例年份" >
          <el-select class="width320" v-model="ruleForm.shareRatioYear" placeholder="选择年份">
            <el-option v-for="(item,index) in years" :key="index" :label="item.value" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.remarks"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="subRule345(4)"
          >保存并提交</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="补充财报收数等其他相关信息"
      :visible.sync="fsDig"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="135px"
        label-position="left"
      >
      <el-form-item label="企业名称" >
          <span>{{ ruleForm.entityName }}</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" >
          <span>{{ ruleForm.creditCode }}</span>
        </el-form-item>
        <el-form-item label="来源" >
          <span>{{ ruleForm.source }}</span>
        </el-form-item>
        <el-form-item label="wind行业划分" >
          <span v-if="!edit1">{{ ruleForm.windMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.windMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="申万行业划分" >
          <span v-if="!edit2">{{ ruleForm.shenWanMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.shenWanMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="财报列示类型" >
          <el-select class="width320" v-model="ruleForm.listType" placeholder="选择类型">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关注报告类型" prop="delivery">
          <el-select class="width320" v-model="ruleForm.reportType" multiple placeholder="请选择">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="subRule345(5)">保存并提交</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="IB&内评"
      :visible.sync="ibDig"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="200px"
        label-position="left"
      >
      <el-form-item label="企业名称" >
          <span>{{ ruleForm.entityName }}</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" >
          <span>{{ ruleForm.creditCode }}</span>
        </el-form-item>
        <el-form-item label="来源" >
          <span>{{ ruleForm.source }}</span>
        </el-form-item>
        <el-form-item label="wind行业划分" >
          <span v-if="!edit1">{{ ruleForm.windMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.windMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="申万行业划分" >
          <span v-if="!edit2">{{ ruleForm.shenWanMaster }}</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.shenWanMaster"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="IB - SaaS - Old" >
          <span v-if="!edit1">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="IB - SaaS - 2.0" >
          <span v-if="!edit1">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="内评-浙商" >
          <span v-if="!edit1">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="内评-泰隆" >
          <span v-if="!edit1">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="内评-华金" >
          <span v-if="!edit1">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-form-item label="内评-华西" >
          <span v-if="!edit1">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit1"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >{{ edit1 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="ibDig = false">确认新增</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="新增主体"
      :visible.sync="bodyDig"
      width="50%"
      :before-close="handleClose"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="200px"
        label-position="left"
      >
        <el-form-item label="导入日期">
          <span>{{ ruleForm.created }}</span>
        </el-form-item>
        <el-form-item label="债券简称">
          <span>{{ ruleForm.bondShortName }}</span>
        </el-form-item>
        <el-form-item label="债券全称">
          <span>{{ ruleForm.bondFullName }}</span>
        </el-form-item>
        <el-form-item label="其他信息">
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="detaile()"
            >查看详情</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="新增主体统一社会信用代码" prop="creditCode">
          <el-input
            class="t-input"
            v-model="ruleForm.creditCode"
            @change="changeInput"
            :disabled="ruleForm.notUse"
            ></el-input>
            <el-button
            v-if="!creditCodePass"
            style="margin-left: 5px"
            type="text"
            @click="check(ruleForm.creditCode)"
            >{{ "查重" }}</el-button>
            <span v-if="creditCodePass === 2" style="color:greenyellow">无重复，可新增</span>
            <span v-if="creditCodePass === 1" style="color:red">存在重复 无法新增</span>
        </el-form-item>
        <div class="notUse">
          <el-checkbox class="mr60" v-model="ruleForm.notUse">不适用</el-checkbox>
          <span class="mr10" >不适用原因</span>
          <el-select :disabled="!ruleForm.notUse" v-model="ruleForm.creditErrorType" placeholder="请选择">
            <el-option
              v-for="item in notUseoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
        <el-form-item label="新增主体名称" prop="entityName">
          <el-input
            class="t-input"
            v-model="ruleForm.entityName"
            @change="entityNamePass = false"
          ></el-input>
          <el-button
            v-if="!entityNamePass"
            style="margin-left: 5px"
            type="text"
            @click="check(ruleForm.entityName)"
            >{{ "查重" }}</el-button>
            <span v-if="entityNamePass === 2" style="color:greenyellow; margin-left: 5px">无重复，可新增</span>
            <span v-if="entityNamePass === 1" style="color:red; margin-left: 5px">存在重复 无法新增</span>
        </el-form-item>
      </el-form>
      <el-button class="ml40" type="success" plain :disabled="disabled" @click="submitAdd">确认新增</el-button>
    </el-dialog>
    <el-dialog
      title="库内重复情况"
      :visible.sync="replaceDig"
      width="40%">
      <div class="dig-width">
        <el-col :sm="24" :lg="12" class="form-card">
          <div class="flex1 mt10">
            <div class="first">统一社会信用代码</div>
            <div class="content">{{ replaceData.creditCode }}</div>
          </div>
          <div class="flex1 mt10">
            <div class="first">企业名称</div>
            <div class="content">{{ replaceData.entityName }}</div>
          </div>
          <div class="flex1 mt10">
            <div class="first">企业曾用名</div>
            <div class="content">{{ replaceData.entityNameHis }}</div>
          </div>
          <div class="flex1 mt10">
            <div class="first">德勤内部主体代码</div>
            <div class="content">{{ replaceData.entityCode }}</div>
          </div>
        </el-col>
      </div>
    </el-dialog>
    <el-dialog
      title="详情"
      :visible.sync="detaileDig"
      width="35%">
      <div class="dig-width7">
        <el-col :sm="24" :lg="12" class="form-card">
          <div v-for="(item, index) in selectRole7" :key="index" class="flex1 mt10">
            <div class="first">{{ index }}</div>
            <div class="content">{{ item || '-' }}</div>
          </div>
        </el-col>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { 
          getTaskByDate,
          queryList,
          getDayTaskInfo,
          // getMouthTaskInfo,
          checkCreditCode,
          getRoleSupplyTask,
          getTaskByEntityCode,
          addEntityAttrValuesNew,
          getTaskInfo,
          getTable,
          getFinances,
          getPreGovName,
          getModelMaster,
          insertMas,
          getGovLevelBig,
          getGovLevelSmall,
          insertGov,
          addSeven,
          ignoreTask,
          getTaskCount,
          addFinEntitySubtableMsg,
          addGovEntitySubtableMsg,
          addEntityeMsg
        } from "@/api/task";
import { getGovLevel, getAttrByOrganName, getTypeByAttrId, checkData } from '@/api/common'
import pagination from "../../components/Pagination";
export default {
  components: {
    pagination
  },
  name: "Index",
  data() {
    return {
      bodyDig: false,
      replaceDig: false,
      role2NowTime: '',
      list: [],
      fsDig: false,
      ibDig: false,
      loading: false,
      currentTime: "",
      week: "",
      month: "",
      monthDate: "",
      currentDay: "",
      dialogVisible: false,
      noUse: false,
      ruleForm: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        resource: "",
        desc: "",
      },
      addGovForm: {},
      rules: {
        name: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        region: [
          { required: true, message: "请选择活动区域", trigger: "change" },
        ],
        date1: [
          {
            type: "date",
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
        date2: [
          {
            type: "date",
            required: true,
            message: "请选择时间",
            trigger: "change",
          },
        ],
        type: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个活动性质",
            trigger: "change",
          },
        ],
        resource: [
          { required: true, message: "请选择活动资源", trigger: "change" },
        ],
        creditCode: [
          { required: true, message: "请输入统一社会信用代码", trigger: "blur" },
        ],
        entityName: [
          { required: true, message: "请输入新建主体名称", trigger: "blur" },
        ],
        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }],
      },
      edit2: false,
      edit1: false,
      addGovernmentDig: false,
      remarkDig: false,
      governmentDig: false,
      stateArr: {
        0: '未处理',
        1: '已有主体',
        2: '新增主体',
      },
      options: [],
      month: '',
      monthMm: '',
      notUseoptions: [
        {
          value: 1,
          label: '吊销'
        },
        {
          value: 2,
          label: '注销'
        },
        {
          value: 3,
          label: '非大陆注册机构'
        },
        {
          value: 4,
          label: '其他未知原因'
        }
      ],
      replaceData: {},
      entityNewNameCheck: false,
      entityNamePass: false,
      creditCodePass: false,
      addBodyId: '',
      list7: [],
      list3: [],
      region: [],
      city: [],
      county: [],
      xmultiple: false,
      jmultiple: false,
      jurisdiction: [],
      supervise: [],
      years: [],
      options1: [],
      options2: [],
      list2: [],
      repalce1: 0,
      repalce2: 0,
      govOption2: [],
      govOption1: [],
      detaileDig: false,
      roleId: localStorage.getItem('roleId'),
      selectRole7: [],
      taskCount: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      clickDay: '',
      colorArr: {
          1: 'shadow',
          2: 'shadow-yellow',
          3: 'shadow-green',
      }
    };
  },
  mounted() {
    this.getCurrentTime();
    this.getDaysInfo();
    this.init();
  },
  computed: {
    disabled () {
      return this.creditCodePass !== 2 && this.entityNamePass !== 2
    }
  },
  methods: {
    init(page) {
      try {
        this.$modal.loading('Loading...')
        this.loading = true;
        const role2date = {
          date:this.nowTime
        }
        const role2MonthDate = {
          date:this.monthDate
        }
        this.nowTime = this.clickDay || this.nowTime
        // 角色7相关接口
        // getMouthTaskInfo(role2MonthDate).then((res) => {
        //   const { data } = res
        //   this.showMsg(data)
        // });
        // (role2date).then(res => {
        //   const { data } = res
        //   this.list7 = data
        // })
        // 角色1相关接口
        const params = {
          taskDate: this.nowTime,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        };
        const paramsMonth = {
          taskDate: this.monthDate,
        };
        getTaskByDate(params).then((res) => {
          const { data } = res
          this.list = data
        });
        queryList(paramsMonth).then((res)=> {
          const { data } = res
          if (!page) {
              this.showMsg(data)
          }
        })
        // 角色 345 相关接口
        getRoleSupplyTask({taskDate: this.nowTime }).then(res => {
          const { data } = res
          this.list3 = data
        })
        // 角色 2 相关接口
        getTaskInfo({date: this.nowTime, pageNum: this.queryParams.pageNum, pageSize: this.queryParams.pageSize }).then(res => {
          const { data } = res
          this.list2 = data.records
          this.total = data.total
          this.queryParams.pages = data.pages
        })

        getTaskCount({TaskDate: this.nowTime}).then(res => {
          const { data } = res
          this.taskCount = data
        })

        // 角色7
        getDayTaskInfo({ date: this.nowTime, pageNum: this.queryParams.pageNum, pageSize: this.queryParams.pageSize }).then((res) => {
          const { data } = res
          this.list7 = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
          // this.sureDate(this, false, this.year, this.monthMm, parseInt(row.path[0].innerText))
        });
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
        this.$modal.closeLoading()
      }
    },
    getList() {
      this.init(true)
    },
    changeMonth(row) {
      const parmas = {
        taskDate: this.monthDate
      }
      this.year = this.monthDate.substr(0, 4)
      this.month = this.monthDate.substr(5, 2)
      this.month = this.month.indexOf('0') === 0 ? this.month.substr(1, 1) : this.month
      this.sureDate(this, false, this.year, this.month-1)
      // sureDate(this, false, )
      try {
        queryList(parmas).then((res)=> {
            const { data } = res
          this.showMsg(data)
        })
      } catch (error) {
        console.log(error)
      }
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    getCurrentTime() {
      //获取当前时间并打印
      let yy = new Date().getFullYear();
      let mm =
        new Date().getMonth() + 1 > 9
          ? new Date().getMonth() + 1
          : "0" + (new Date().getMonth() + 1);
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf =
        new Date().getMinutes() < 10
          ? "0" + new Date().getMinutes()
          : new Date().getMinutes();
      let ss =
        new Date().getSeconds() < 10
          ? "0" + new Date().getSeconds()
          : new Date().getSeconds();
      // eslint-disable-next-line no-unused-vars
      this.currentTime = yy + "年" + mm + "月" + dd + "日";
      this.monthDate = yy + "-" + mm;
      this.monthMm = mm
      this.year = yy
      this.currentDay = dd;
      this.nowTime = yy + "-" + mm + "-" + dd;
      this.role2NowTime = yy + "/" + mm + "/" + dd;
      let myDate = new Date();
      let wk = myDate.getDay();
      let weeks = [
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六",
      ];

      this.week = weeks[wk];
      this.month = yy + "年" + mm + "月";
    },
    getDaysInfo() {
      var _this = this;

      this.sureDate(_this);
    },
    is_leap(year) {
      //判断是不是闰年
      return year % 100 == 0
        ? year % 400 == 0
          ? 1
          : 0
        : year % 4 == 0
        ? 1
        : 0;
    },
    //下面的是画表格的方法，这个方法会根据数据画出我们需要的表格
    drawTable(jsonHtml) {
      var _this = this;
      var str = `
  <tr class="xiqi">
   <td class="isRed">日</td>
   <td>一</td>
   <td>二</td>
   <td>三</td>
   <td>四</td>
   <td>五</td>
   <td class="isRed">六</td>
  </tr>`;
      let idx = "",
        date_str = "",
        isRed = "",
        hasMsg = "";
        for (var i = 0; i < _this.tr_str; i++) {
          str += "<tr>";
        for (var k = 0; k < 7; k++) {
          idx = i * 7 + k;
          isRed = k === 0 || k === 6 ? "isRed" : "";
          date_str = idx - _this.firstnow + 1;
          date_str <= 0 || date_str > this.m_days[this.mnow]
            ? (date_str = " ")
            : (date_str = idx - _this.firstnow + 1);
          date_str == _this.dnow
            ? (hasMsg =
                '<td class="thisDay" date="' +
                date_str +
                '"><div class="' +
                isRed +
                'content-day">' +
                "<div class='shadow'>" +
                date_str +
                "</div>" +
                "</div></td>")
            : (hasMsg =
                '<td date="' +
                date_str +
                '"><div class="' +
                isRed +
                'content-day">' +
                '<div class="'+ (date_str !== ' ' ? 'shadow' : '') +'">'+
                date_str +
                "</div>" +
                "</div></td>");

          for (var l = 0, len = jsonHtml.length; l < len; l++) {
            if (date_str == jsonHtml[l].date) {
              date_str == _this.dnow
                ? (hasMsg =
                    '<td class="thisDay" date="' +
                    date_str +
                    '"><div class="' +
                    isRed +
                    'content-day">' +
                     "<div class='"+(this.colorArr[jsonHtml[l].bad])+"'>" +
                    date_str +
                    "</div>" +
                    "</div>" +
                    "</td>")
                : (hasMsg =
                    '<td date="' +
                    date_str +
                    '"><div class="' +
                    isRed +
                    'content-day">' +
                    "<div class='"+(this.colorArr[jsonHtml[l].bad])+"'>" +
                    date_str +
                    "</div>" +
                    "</div>" +
                    "</td>");
            }
          }
          str += hasMsg;
        }
        str += "</tr>";
      }
      var table = document.getElementById("table");
      table.innerHTML = str;
    },
    //两个参数代表的含义分别是this对象以及判断当前的操作是不是在进行月份的修改
    sureDate(_this, other, year, month, now) {
      this.newDate = new Date();
      this.ynow = year || this.newDate.getFullYear();
      if (!other) {
        this.mnow = month || this.newDate.getMonth(); //月份
      }
      this.dnow = now || this.newDate.getDate(); //今日日期
      this.firstDay = new Date(this.ynow, this.mnow, 1);
      this.firstnow = this.firstDay.getDay();
      this.m_days = [
        31,
        28 + this.is_leap(this.ynow),
        31,
        30,
        31,
        30,
        31,
        31,
        30,
        31,
        30,
        31,
      ];
      this.tr_str = Math.ceil((_this.m_days[this.mnow] + this.firstnow) / 7);
      this.showMsg();
    },
    //通过接口返回的是我们当前的月份对应在日历中需要处理的事项
    showMsg(flagData = []) {
        if (flagData) {
            flagData.forEach(e => {
                let dateStr = e.taskDate.substr(-2, 2)
                if(dateStr.substr(0, 1) == 0) {
                    e.date = dateStr.substr(-1)
                }else {
                    e.date = dateStr
                }
                e.bad = e.taskStatus
            });
        }        
      this.drawTable(flagData);
    },
    work(row) {
      this.$router.push({ path: 'work', query: { taskCateId: row.taskCateId, taskDate: this.nowTime } });
    },
    getDay(row) {
      if (!row.path[0].innerText) {
        return
      }
      const clickDay = row.path[0].innerText >= 10 ? row.path[0].innerText : '0'+row.path[0].innerText
      const tbodyObj = row.path[3].localName === 'tbody' ? row.path[3].children : row.path[4].children
      const tbodyArr = Array.from(tbodyObj)
      tbodyArr.forEach(e => {
        const tr = Array.from(e.children)
        tr.forEach(i => {
          i.className = ''
        })
      });
      row.path[1].localName === 'td' ? row.path[1].className = 'thisDay' : row.path[2].className = 'thisDay'
      try {
        this.$modal.loading("loading...");
        const parmas = {
          date: this.monthDate+ '-' +clickDay,
          pageNum: 1,
          pageSize: this.queryParams.pageSize
        };
        this.clickDay = this.monthDate+ '-' +clickDay
        getDayTaskInfo(parmas).then((res) => {
          const { data } = res
          this.list7 = data.records
          this.total = data.total
          this.queryParams.pageNum = data.current
          // this.sureDate(this, false, this.year, this.monthMm, parseInt(row.path[0].innerText))
        });
        const params = {
          taskDate: this.monthDate+ '-' +clickDay,
          pageNum: 1,
          pageSize: 20
        };
        getTaskByDate(params).then((res) => {
          const { data } = res
          this.list = data
          // this.sureDate(this, false, this.year, this.monthMm, parseInt(row.path[0].innerText))
        });
        getRoleSupplyTask(params).then((res) => {
          const { data } = res
          this.list3 = data
          // this.sureDate(this, false, this.year, this.monthMm, parseInt(row.path[0].innerText))
        });
        getTaskInfo({date: this.monthDate+ '-' +clickDay, pageNum: this.queryParams.pageNum, pageSize: this.queryParams.pageSize }).then(res => {
          const { data } = res
          this.list2 = data.records
          this.total = data.total
          this.queryParams.pages = data.pages
          // this.sureDate(this, false, this.year, this.monthMm, parseInt(row.path[0].innerText))
        });
        getTaskCount({TaskDate: this.monthDate+ '-' +clickDay}).then(res => {
          const { data } = res
          this.taskCount = data
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading();
      }
    },
    // 角色7 流程开始 
    addBody(row) {
      this.bodyDig = true
      this.addBodyId = row.id
      this.ruleForm.bondFullName = row.bondFullName
      this.ruleForm.created = row.created
      this.ruleForm.bondShortName = row.bondShortName
      this.ruleForm.id = row.id
      this.selectRole7 = JSON.parse(row.details)
      this.$set(this.ruleForm, 'wind',  '')
        this.$set(this.ruleForm, 'shenWan',  '')
    },
    check(row) {
      this.$modal.loading("loading...");
      try {
        checkCreditCode({creditCode: row}).then(res => {
          const { data } = res
          if (data.bo === false) {
            this.replaceDig = true
            this.replaceData = data.entityInfo
            this.creditCodePass = 1 // 信用代码查重没过
            this.entityNamePass = 1 // 新增主体名称查重没过
          } else {
            this.creditCodePass = 2 // 信用代码查重通过
            this.entityNamePass = 2 // 新增主体名称查重通过
          }
        })
      } catch (error) {
        console.log(error)
      } finally{
        this.$modal.closeLoading();
      }
    },
    changeInput() {
      this.creditCodePass = false
      this.entityNamePass = false
    },
    submitAdd() {
      this.$modal.loading("loading...");
      this.ruleForm.created = ''
      try {
        addSeven(this.ruleForm).then(res => {
          const { data } = res
          this.bodyDig = false
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.init()
        })
      } catch (error) {
        console.log(error)
      } finally{
        this.$modal.closeLoading();
      }
    },
    // 角色7修改状态 1忽略 2新增
    changeAddState(id, state) {
      try {
        this.$modal.loading("loading...");
        const params = {
          id: id || this.addBodyId
        }
        ignoreTask(params).then(res => {
          const { data } = res
          this.$message({
            message: data,
            type: 'success'
          });
          this.init()
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading();
      }
    },
    detaile(row) {
      this.detaileDig = true
      this.selectRole7 = row ? JSON.parse(row.details) : this.selectRole7
    },
    // 角色7流程结束

    // 角色2流程开始
    workRole2(row) {
      try {
        this.dialogVisible = true
        this.ruleForm.entityName = row.entityName
        this.ruleForm.creditCode = row.creditCode
        this.ruleForm.sourceName = row.sourceName
        this.$set(this.ruleForm, 'entityCode',  row.entityCode)
        this.$set(this.ruleForm, 'id',  row.id)
        this.$set(this.ruleForm, 'wind',  '')
        this.$set(this.ruleForm, 'shenWan',  '')
        this.$modal.loading("loading...");
        getTable({id: row.id}).then(res => {
          const { data } = res
          this.ruleForm.wind = data.attrs.wind行业划分明细.value
          this.ruleForm.sw = data.attrs['申万(2021)行业划分明细'].value
        })
        getFinances({}).then(res => {
          const { data } = res
          this.options1 = data
        })
        getGovLevel({preGovCode: ''}).then(res => {
          const { data } = res
          this.region = data
        })
        getModelMaster({}).then(res => {
          const { data } = res
          this.options2 = data
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading();
      }
    },
    checkReplace(row, type, num) {
      try {
        this.$modal.loading('Loading...')
        const parmas = {
        keyword: type,
        target: row,
      }
      checkData(parmas).then(res => {
        const { data } = res
        const ret = data.data === null ? 1 : 2
        if (num === 1) {
          this.repalce1 = ret
        }
        if (num === 2) {
          this.repalce2 = ret
        }
      })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    changeCode(num) {
      if (num === 1) {
        this.repalce1 = 0
      }
      if (num === 2) {
        this.repalce2 = 0
      }
    },
    selectCode(row) {
      try {
        this.$modal.loading('Loading...')
        getPreGovName({govCode: row}).then(res => {
          this.$set(this.addGovForm, 'preGovName', res.data)
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading()
      }
    },
    subRule2() {
      try {
        this.$modal.loading('Loading...')
        insertMas(this.ruleForm).then(res => {
          const { data } = res
          if (data === null) {
            this.governmentDig = false
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success'
            });
          }
        })
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: 'error'
        });
      } finally {
        this.$modal.closeLoading()
      }
      
    },
    getGov(row) {
      this.ruleForm.county = row.govName
      this.county.forEach(e => {
        if (e.govName === row.govName) {
          this.ruleForm.govCode = e.govCode
          this.ruleForm.dqGovCode = e.dqGovCode
        }
      })
    },
    addGovernment() {
      this.addGovernmentDig = true;
      getGovLevelBig({}).then(res => {
        const { data } = res
        this.govOption1 = data
      })
    },
    getSmall(row) {
      getGovLevelSmall({id: row}).then(res => {
        const { data } = res
        this.govOption2 = data
      })
    },
    subGov() {
      try {
        this.$modal.loading("loading...");
        insertGov(this.addGovForm).then(res => {
          const { data } = res
          if (data === null) {
            this.addGovernmentDig = false
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success'
            });
          }
        })
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: 'error'
        });
      } finally{
        this.$modal.closeLoading()
      }
    },
    // 角色345流程开始
    workRole3(row) {
      try {
        this.remarkDig = true
        this.ruleForm.entityName = row.entityInfo.entityName
        this.ruleForm.creditCode = row.entityInfo.creditCode
        this.$set(this.ruleForm, 'entityCode',  row.entityInfo.entityCode)
        this.$set(this.ruleForm, 'id',  row.crmSupplyTask.id)
        this.$set(this.ruleForm, 'wind',  '')
        this.$set(this.ruleForm, 'shenWan',  '')
        this.$modal.loading("loading...");
        const params = {
          entityCode: row.entityInfo.entityCode,
          roleId: 5,
        }
        getTaskByEntityCode(params).then(res => {
          const { data } = res
          let sw = ''
          let wind = ''
          data.attrValueList.forEach(e => {
            if ( e.attrId === 97 ) {
              sw += e.value // 申万
            }
            if ( e.attrId === 99 ) {
              wind += e.value // wind行业划分明细值
            }
          })
          this.$set(this.ruleForm, 'shenWanMaster', sw)
          this.$set(this.ruleForm, 'windMaster', wind)
        })

        getGovLevel({preGovCode: ''}).then(res => {
          const { data } = res
          this.region = data
        })
        
        // todo 根据角色穿不同的参数
        const ret = {
          organName: '金融机构表'
        }
        getAttrByOrganName(ret).then(res => {
          const { data } = res
          const xid = data.所属辖区[0].id
          this.xmultiple = data.所属辖区[0].multiple 
          const jid = data.对口监管机构[0].id
          this.jmultiple = data.对口监管机构[0].multiple
          getTypeByAttrId({attrId: xid}).then(res => {
            const { data } = res
            this.jurisdiction = data
          })
          getTypeByAttrId({attrId: jid}).then(res => {
            const { data } = res
            this.supervise = data
          })
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading();
      }
    },
    getCity(row) {
      this.ruleForm.region = row.govName
      getGovLevel({preGovCode: row.dqGovCode}).then(res => {
        const { data } = res
        this.city = data
      })
    },
    getCounty(row) {
      this.ruleForm.city = row.govName
      getGovLevel({preGovCode: row.dqGovCode}).then(res => {
        const { data } = res
        this.county = data
      })
    },
    subRule345(row) {
      try {
        this.$modal.loading("loading...");
        const region = this.ruleForm.region || this.ruleForm.city || this.ruleForm.county
        this.$set(this.ruleForm, 'belPlace', region)
        this.$set(this.ruleForm, '政府部门实际持股比例-年份', this.ruleForm.政府部门实际持股比例年份)
        if (row === 3) {
          addFinEntitySubtableMsg(this.ruleForm).then(res => {
            if (res.code === 200) {
              this.governmentDig = false
              this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success'
              });
            }
          })
        }
        if (row === 4) {
          addGovEntitySubtableMsg(this.ruleForm).then(res => {
            if (res.code === 200) {
              this.governmentDig = false
              this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success'
              });
            }
          })
        }
        if (row === 5) {
          addEntityeMsg(this.ruleForm).then(res => {
            if (res.code === 200) {
              this.governmentDig = false
              this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success'
              });
            }
          })
        }
        // addEntityAttrValuesNew(this.ruleForm).then(res => {
        //   const { data } = res
        //   if (data.code === 200) {
        //     this.governmentDig = false
        //     this.$message({
        //       showClose: true,
        //       message: '操作成功',
        //       type: 'success'
        //     });
        //   }
        // })
      } catch (error) {
        this.$message({
          showClose: true,
          message: error,
          type: 'error'
        });
      } finally {
        this.$modal.closeLoading();
      }
    },
    workRole4(row) {
      try {
        this.$modal.loading("loading...");
        this.governmentDig = true
        this.ruleForm.entityName = row.entityInfo.entityName
        this.ruleForm.creditCode = row.entityInfo.creditCode
        this.$set(this.ruleForm, 'entityCode',  row.entityInfo.entityCode)
        this.$set(this.ruleForm, 'id',  row.crmSupplyTask.id)
        this.$set(this.ruleForm, 'wind',  '')
        this.$set(this.ruleForm, 'shenWan',  '')
        const params = {
          entityCode: row.entityInfo.entityCode,
          roleId: 5,
        }
        getTaskByEntityCode(params).then(res => {
          const { data } = res
          let sw = ''
          let wind = ''
          data.attrValueList.forEach(e => {
            if ( e.attrId === 97 ) {
              sw += e.value // 申万
            }
            if ( e.attrId === 99 ) {
              wind += e.value // wind行业划分明细值
            }
          })
          this.$set(this.ruleForm, 'shenWanMaster', sw)
          this.$set(this.ruleForm, 'windMaster', wind)
        })
        getGovLevel({preGovCode: ''}).then(res => {
          const { data } = res
          this.region = data
        })
        const ret = {
          organName: '城投机构表'
        }
        getAttrByOrganName(ret).then(res => {
          const { data } = res
          const id = data.政府部门实际持股比例年份[0].id
          getTypeByAttrId({attrId: id}).then(res => {
            const { data } = res
            this.years = data
          })
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading();
      }
    },
    workRole5(row) {
      try {
        this.$modal.loading("loading...");
        this.fsDig = true
        this.ruleForm.entityName = row.entityInfo.entityName
        this.ruleForm.creditCode = row.entityInfo.creditCode
        this.$set(this.ruleForm, 'entityCode',  row.entityInfo.entityCode)
        this.$set(this.ruleForm, 'id',  row.crmSupplyTask.id)
        this.$set(this.ruleForm, 'wind',  '')
        this.$set(this.ruleForm, 'shenWan',  '')
        const params = {
          entityCode: row.entityInfo.entityCode,
          roleId: 5,
        }
        getTaskByEntityCode(params).then(res => {
          const { data } = res
          let sw = ''
          let wind = ''
          data.attrValueList.forEach(e => {
            if ( e.attrId === 97 ) {
              sw += e.value // 申万
            }
            if ( e.attrId === 99 ) {
              wind += e.value // wind行业划分明细值
            }
          })
          this.$set(this.ruleForm, 'shenWanMaster', sw)
          this.$set(this.ruleForm, 'windMaster', wind)
        })
        const ret = {
          organName: '财报收数'
        }
        getAttrByOrganName(ret).then(res => {
          const { data } = res
          const id1 = data.关注报告类型[0].id
          const id2 = data.财报列示类型[0].id
          getTypeByAttrId({attrId: id1}).then(res => {
            const { data } = res
            this.options1 = data // 关注报告类型options
          })
          getTypeByAttrId({attrId: id2}).then(res => {
            const { data } = res
            this.options2 = data // 财报列示类型options
          })
        })
      } catch (error) {
        console.log(error)
      } finally {
        this.$modal.closeLoading();
      }
    },
    // 角色345流程结束
    handleClose() {
        this.ruleForm = {}
        this.creditCodePass = false
        this.entityNamePass = false
        this.bodyDig = false
    }
  },
  watch: {
     'ruleForm.notUse':{
      handler(newName,oldName){
          if (newName) {
              this.ruleForm.creditCode = ''
          } else {
              this.ruleForm.creditErrorType = ''
          }
      },
      deep:true //为true，表示深度监听，这时候就能监测到a值变化
    }
    }
};
</script>

<style scoped lang="scss">
#table {
  border-collapse: collapse;
  border: solid 1px #cccc;
  margin: 0 auto;
}

.t-input {
  width: 310px;
}
.g-title {
  padding-left: 20px;
  font-weight: 600;
}
.todo-desc {
  padding-left: 20px;
  div {
    margin-top: 10px;
  }
}
.table-box {
  margin-top: 20px;
}
.list-box {
  padding-left: 15px;
}
::v-deep {
  td {
    div {
      cursor: pointer;
    }
  }
  .content-day {
    width: 85px;
    height: 50px;
    text-align: center;
    line-height: 52px;
  }
  .isRedcontent-day {
    width: 85px;
    height: 50px;
    text-align: center;
    line-height: 52px;
  }
  .shadow {
    display: inline-block;
    min-width: 10px;
    width:20px;
    padding: 3px 3px;
    font-size: 12px;
    font-weight: 100;
    line-height: 1;
    color: #666666;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #D8D8D8;
    border-radius: 15px;
  }
  .shadow-green {
    display: inline-block;
    min-width: 10px;
    width:20px;
    padding: 3px 3px;
    font-size: 12px;
    font-weight: 100;
    line-height: 1;
    color: #666666;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #86BC25;
    border-radius: 15px;
  }
  .shadow-yellow {
    display: inline-block;
    min-width: 10px;
    width:20px;
    padding: 3px 3px;
    font-size: 12px;
    font-weight: 100;
    line-height: 1;
    color: #666666;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #FFCD01;
    border-radius: 15px;
  }
  .xiqi {
    border-bottom: 1px solid #86BC25;
    text-align: center;
    height: 40px;
  }
  .thisDay {
    background: #86BC25;
    .shadow {
      background-color: #fff;
    }
  }
}
.green {
  margin-left: 5px;
   color: #86BC25
}
.red {
  margin-left: 5px;
   color: red
}
.notUse {
  margin-left:82px;
  margin-bottom: 20px;
  .mr60 {
    margin-right: 60px;
  }
}
.dig-width {
  height: 160px;
}
.dig-width7 {
  height:300px;
  overflow-y:auto
}
.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-top: 1%;
  width: 100%;
}
.edit-btn {
  margin-top: -3px;
  margin-left: 5px;
}
.flex1 {
  .first {
    width: 200px;
  }
  .content {
    color: #a7a7a7;
    margin-top: 3px;
    width: 240px;
  }
}
.replace-title {
  ::v-deep .el-dialog__title{
    color: red
  }
}
.ml40 {
  margin-left: 42%
}
.width146 {
  width: 146px;
}
.width320 {
  width: 320px;
}
</style>
