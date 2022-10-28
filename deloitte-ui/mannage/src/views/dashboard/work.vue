<template>
  <div class="app-container home">
    <div class="flex1">
      <el-button class="back" type="text" @click="back()">返回首页</el-button>
      <h3 class="title">{{this.$route.query.taskCategory}}-导入任务清单</h3>
    </div>
    <el-row>
      <div v-for="(item, index) in contentData" :key="index">
        <el-col :sm="24" :lg="23" class="form-card">
          <div class="flex1">
            <h3 class="title1">{{ item.taskFileName }}</h3>
            <el-button class="back" type="text">下载导入模板</el-button>
            <fileUpload
              :uploadUrl="'/crm/windTask/doTask/' + item.windTask.id"
              :index="index"
              ref="fileUpload"
              @loading="loading"
              @uploadFail="uploadFail"
              @uploadPass="uploadPass"
            />
          </div>
          <div v-if="item.taskStatus === 0">
            【 <span style="color: red">暂未导入今日更新文件</span> 】
          </div>
          <div v-if="item.taskStatus === 1">
            【 <span style="color: #86BC25">已导入更新文件</span> 】
          </div>
          <div v-if="item.taskStatus === 2">
            【 <span style="color: yellow">导入中</span> 】
          </div>
        </el-col>
        <el-col
          :sm="24"
          :lg="23"
          class="mt20 form-card"
          style="padding-left: 20px; margin-top: -28px"
        >
          <div class="flex1">
            <h3 class="title1">
              新增待确认记录( <span style="color: red">{{ item.data && item.data.length || 0}}</span> )
            </h3>
          </div>
          <el-table
            class="table-content"
            :data="item.data"
            style="width: 98%; margin-top: 15px"
            height="300"
            empty-text="暂无确认记录，请导入今日更新文件"
          >
            <el-table-column
              v-for="(e, index) in item.header"
              :key="index"
              :prop="e"
              :label="e"
            >
              <template slot-scope="scope">
                <span v-if="e === '变化状态'">{{ scope.row.变化状态 === 1 ? "新增" : "修改" }}</span>
                <span v-else>{{ scope.row[e] || '-' }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </div>
    </el-row>
  </div>
</template>

<script>
import { findTaskDetails } from "@/api/task";
import fileUpload from "../../components/FileUpload";
export default {
  name: "work",
  components: {
    fileUpload,
  },
  data() {
    return {
      list: [],
      currentTab: "",
      fileList: [],
      contentData: [],
      taskCateId: this.$route.query.taskCateId,
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      try {
        const params = {
          taskDate: this.$route.query.taskDate,
          taskCateId: this.$route.query.taskCateId,
        };
        findTaskDetails(params).then((res) => {
          const { data } = res;
          this.contentData = data;
        });
      } catch (error) {
        console.log(error);
      }
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    back() {
      this.$router.back();
    },
    changeTab(tab) {
      this.currentTab = tab;
    },
    loading(index) {
      this.contentData[index].taskStatus = 2;
    },
    uploadPass(data, index) {
      this.contentData[index].taskStatus = 1;
      this.init()
    },
    uploadFail(index) {
      this.contentData[index].taskStatus = 0;
    },
  },
};
</script>

<style scoped lang="scss">
::v-deep {
  .el-upload-list {
    display: none;
  }
}
.upload-demo {
  margin-top: 11px;
}

.form-card {
  padding-left: 20px;
  margin-bottom: 30px;
  /* margin: 0 auto; */
  margin-left: 3%;
  margin-top: 3%;
}
.back {
  margin-left: 19px;
}
.title {
  margin-left: 31%;
  font-weight: 600;
}
.title1 {
  font-weight: 600;
}
.g-desc {
  margin-top: 15px;
  margin-left: 25px;
  span {
    color: #86BC25;
  }
  a {
    font-size: 14px;
    color: #9b9b9b;
    text-decoration: revert;
    margin-right: 26px;
    height: 16px;
    margin-top: 5px;
  }
}
.select-x {
  width: 24%;
  position: relative;
  left: 50%;
}
.g-tab {
  margin-left: 1.5%;
  a {
    font-size: 14px;
    color: black;
    text-decoration: underline;
    margin-right: 10px;
  }
  .g-select {
    color: #86BC25;
  }
}
</style>
