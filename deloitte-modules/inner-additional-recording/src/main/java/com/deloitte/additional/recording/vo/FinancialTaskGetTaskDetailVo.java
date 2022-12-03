package com.deloitte.additional.recording.vo;

import com.deloitte.additional.recording.domain.FinancialTask;
import com.deloitte.additional.recording.domain.PrsModelQual;
import com.deloitte.system.api.domain.SysDictData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/28/15:59
 * @Description:
 */
@Setter
@Getter
public class FinancialTaskGetTaskDetailVo implements Serializable {
    //任务详情
   private FinancialTask task;
   //获取报告数据
   private List<GetTaskDataDetailByParamVo> taskDataDetailByPara;
   //年报数据 & 合并报告
   private   Map<String, List<SysDictData>> groupByDictType;

   //规则 TODO 数据采集规则 全部 待确定
  private  List<PrsModelQual> collRuleList;
}
