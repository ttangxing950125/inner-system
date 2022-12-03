<template>
    <div class="padding20">
        <div class="none-div">
            <svg-icon
                slot="prefix"
                icon-class="data404"
                class="center-icon"
                > 
            </svg-icon>
            <div class="none-font mt10">请先选择或搜索指标层字典进行配置</div>
        </div>
    </div>
  </template>
    
  <script>
  export default {
  data() {
      return {
      }
  },
  methods: {
      
  },
  };
  </script>
  
  <style scoped lang='scss'>
  .padding20 {
      height: 100%;
      .none-div {
          position: absolute;
          left: 50%;
          top: 50%;
          text-align: center;
      }
      .center-icon {
          height: 81px;
          width: 120px;
      }
      .none-font {
          color: #6D798F;
          font-size: 12px;
      }
  }
  </style>