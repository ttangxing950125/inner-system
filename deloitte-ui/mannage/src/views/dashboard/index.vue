<template>
  <div class="app-container home">
    <h3 class="g-title">今日运维任务</h3>
    <div class="todo-desc">
      <div>HELLO!</div>
      <div>今天是 {{ currentTime }}, {{ week }}。</div>
      <div>
        今日完成任务合计 {{ "5" }} 条, 待完成{{ "3" }}条，已完成{{
          "2"
        }}条，请尽快完成！
      </div>
      <span type="text">切换日期：</span>
      <el-date-picker v-model="monthDate" type="month" placeholder="选择月" value-format="yyyy-MM" @change="changeMonth">
      </el-date-picker>
    </div>
    <div class="table-box">
      <table border="1" id="table"></table>
    </div>
    <h3 class="g-title">每日运维任务</h3>
    <div class="list-box">
      <el-table
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
            <span :class="scope.row.notComplete > 0 ? 'red' : 'green'">{{ scope.row.notComplete > 0 ? '待完成( '+scope.row.notComplete+'/'+scope.row.taskCount+ ')' : '已完成( '+scope.row.taskCount+'/'+scope.row.taskCount+ ')' }}</span>
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
    </div>
    <el-dialog title="敞口划分" :visible.sync="dialogVisible" width="50%">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="企业名称" prop="region">
          <span>xxxxxxxxxxxx</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="来源" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="wind行业划分" prop="region">
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
        <el-form-item label="申万行业划分" prop="region">
          <span v-if="!edit2">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="是否为金融机构" prop="delivery">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="金融细分领域" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择新增类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="YY-是否为城投机构" prop="delivery">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
            <el-radio label="不详"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="中诚信-是否为城投机构" prop="delivery">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
            <el-radio label="不详"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="IB-是否为城投机构" prop="delivery">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="机构对应域投政府" required>
          <el-col :span="5">
            <el-form-item prop="date1">
              <el-date-picker
                type="date"
                v-model="ruleForm.date1"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="5">
            <el-form-item prop="date2">
              <el-time-picker
                v-model="ruleForm.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="5">
            <el-form-item prop="date2">
              <el-time-picker
                v-model="ruleForm.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
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
          <span>(-)</span>
        </el-form-item>
        <el-form-item label="机构对应域投政府德勤内部代码" prop="delivery">
          <span>(-)</span>
        </el-form-item>
        <el-form-item label="备注" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false"
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
        <el-form-item label="企业名称" prop="region">
          <span>xxxxxxxxxxxx</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="来源" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="wind行业划分" prop="region">
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
        <el-form-item label="申万行业划分" prop="region">
          <span v-if="!edit2">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="所属地区" prop="delivery">
          <el-col :span="5">
            <el-form-item prop="date1">
              <el-date-picker
                type="date"
                v-model="ruleForm.date1"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="5">
            <el-form-item prop="date2">
              <el-time-picker
                v-model="ruleForm.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="5">
            <el-form-item prop="date2">
              <el-time-picker
                v-model="ruleForm.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="所属辖区" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择新增类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="对口监管机构" prop="delivery">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
            <el-radio label="不详"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="remarkDig = false"
          >确认新增</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="新增地方政府"
      :visible.sync="addGovernmentDig"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="170px"
        label-position="left"
      >
        <el-form-item label="新增地方政府级别-大类" prop="region">
          <el-select v-model="ruleForm.region" placeholder="">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新增地方政府级别-小类" prop="region">
          <el-select v-model="ruleForm.region" placeholder="">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新增地方政府名称" prop="region">
          <el-input
            class="t-input"
            v-model="ruleForm.name"
            placeholder="请输入新增政府全称"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >查重</el-button
          >
        </el-form-item>
        <el-form-item label="新增地方政府行政编码" prop="region">
          <el-input
            class="t-input"
            v-model="ruleForm.name"
            placeholder="请输入新增政府官方新增编码"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >查重</el-button
          >
          <el-radio
            style="margin-left: 5px; margin-top: 9px"
            v-model="noUse"
            label="1"
            >不适用</el-radio
          >
        </el-form-item>
        <el-form-item label="上级地方政府名称" prop="delivery">
          <span>（通过下方输入官方行政编码进行查询后自动填入）</span>
        </el-form-item>
        <el-form-item label="上级地方政府行政编码" prop="delivery">
          <el-input class="t-input" v-model="ruleForm.name"></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit1 = !edit1"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addGovernmentDig = false"
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
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="企业名称" prop="region">
          <span>xxxxxxxxxxxx</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="来源" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="wind行业划分" prop="region">
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
        <el-form-item label="申万行业划分" prop="region">
          <span v-if="!edit2">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="政府持股方式" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择新增类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="政府对当前城投支持力度" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
        <el-form-item label="政府对当前城投支持力度判断依据" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
        <el-form-item label="政府部门实际持股比例" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
        <el-form-item label="政府部门实际持股比例年份" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择新增类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="delivery">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="governmentDig = false"
          >确认新增</el-button
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
        <el-form-item label="企业名称" prop="region">
          <span>xxxxxxxxxxxx</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="来源" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="wind行业划分" prop="region">
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
        <el-form-item label="申万行业划分" prop="region">
          <span v-if="!edit2">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="财报列示类型" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关注报告类型" prop="delivery">
          <el-select v-model="ruleForm.region" multiple placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fsDig = false">确认新增</el-button>
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
        <el-form-item label="企业名称" prop="region">
          <span>xxxxxxxxxxxx</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="来源" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="wind行业划分" prop="region">
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
        <el-form-item label="申万行业划分" prop="region">
          <span v-if="!edit2">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="财报列示类型" prop="region">
          <el-select v-model="ruleForm.region" placeholder="选择类型">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关注报告类型" prop="delivery">
          <el-select v-model="ruleForm.region" multiple placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fsDig = false">确认新增</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="补充财报收数等其他相关信息"
      :visible.sync="ibDig"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="135px"
        label-position="left"
      >
        <el-form-item label="企业名称" prop="region">
          <span>xxxxxxxxxxxx</span>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="来源" prop="region">
          <span>xxxxxxx121xxxxx</span>
        </el-form-item>
        <el-form-item label="wind行业划分" prop="region">
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
        <el-form-item label="申万行业划分" prop="region">
          <span v-if="!edit2">xxxxxxx121xxxxx</span>
          <el-input
            class="t-input"
            v-if="edit2"
            v-model="ruleForm.name"
          ></el-input>
          <el-button
            style="margin-left: 5px"
            type="text"
            @click="edit2 = !edit2"
            >{{ edit2 ? "保存" : "修改" }}</el-button
          >
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="IB - SaaS - Old" prop="region">
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
        <el-form-item label="IB - SaaS - 2.0" prop="region">
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
        <el-form-item label="内评-浙商" prop="region">
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
        <el-form-item label="内评-泰隆" prop="region">
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
        <el-form-item label="内评-华金" prop="region">
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
        <el-form-item label="内评-华西" prop="region">
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
  </div>
</template>

<script>
import { getTaskByDate, queryList } from "@/api/task";
export default {
  name: "Index",
  data() {
    return {
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
      noUse: "",
      ruleForm: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
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
        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }],
      },
      edit2: false,
      edit1: false,
      addGovernmentDig: false,
      remarkDig: false,
      governmentDig: false,
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      month: ''
    };
  },
  mounted() {
    this.getCurrentTime();
    this.getDaysInfo();
    this.init();
  },
  methods: {
    init() {
      try {
        this.loading = true;
        const params = {
          taskDate: this.nowTime,
        };
        const paramsMonth = {
          taskDate: this.monthDate,
        };
        getTaskByDate(params).then((res) => {
          const { data } = res
          this.list = data
        });
        queryList(paramsMonth).then((res)=> {
          console.log(res)
        })
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    changeMonth(row) {
      const parmas = {
        taskDate: this.monthDate
      }
      this.year = this.monthDate.substr(0, 4)
      this.month = this.monthDate.substr(5, 2)
      this.month = this.month.indexOf('0') === 0 ? this.month.substr(1, 1) : this.month
      this.sureDate(this, false, this.year, this.month)
      // sureDate(this, false, )
      try {
        queryList(parmas).then((res)=> {
          console.log(res)
        })
      } catch (error) {
        console.log(error)
      } finally {
      
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
      this.month = mm
      this.year = yy
      this.currentDay = dd;
      this.nowTime = yy + "-" + mm + "-" + dd;
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
                "<div class=''>" +
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
                    "<div class='shadow-green'>" +
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
                    "<div class='shadow-green'>" +
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
    sureDate(_this, other, year, month) {
      this.newDate = new Date();
      this.ynow = year || this.newDate.getFullYear();
      if (!other) {
        this.mnow = month || this.newDate.getMonth(); //月份
      }
      this.dnow = this.newDate.getDate(); //今日日期
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
    showMsg() {
      var jsonHtml = [
        {
          date: 2,
          msg: "吃饭",
        },
        {
          date: 3,
          msg: "睡觉",
        },
        {
          date: 4,
          msg: "打豆豆",
        },
        {
          date: 6,
          msg: "豆豆不在家",
        },
        {
          date: 12,
          msg: "~~~~~",
        },
        {
          date: 15,
          msg: "怎么办~~~~",
        },
        {
          date: 20,
          msg: "找豆豆",
        },
      ];
      this.drawTable(jsonHtml);
    },
    addGovernment() {
      this.addGovernmentDig = true;
    },
    work(row) {
      this.$router.push({ path: 'work', query: { taskCateId: 2, taskDate: this.nowTime } });
    }
  },
};
</script>

<style scoped lang="scss">
#table {
  border-collapse: collapse;
  border: solid 1px #cccc;
  margin: 0 auto;
}

.t-input {
  width: 220px;
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
  .content-day {
    width: 80px;
    height: 50px;
    text-align: center;
    line-height: 52px;
  }
  .isRedcontent-day {
    width: 80px;
    height: 50px;
    text-align: center;
    line-height: 52px;
  }
  .shadow {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    color: #ccc;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #777;
    border-radius: 15px;
  }
  .shadow-green {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: rgb(107, 244, 112);
    border-radius: 15px;
  }
  .xiqi {
    border-bottom: 1px solid #52ff00bd;
    text-align: center;
    height: 40px;
  }
  .thisDay {
    background: #52ff00bd;
    .shadow {
      background-color: #fff;
    }
  }
}
.green {
   color: #52ff00bd
}
.red {
   color: red
}
</style>
