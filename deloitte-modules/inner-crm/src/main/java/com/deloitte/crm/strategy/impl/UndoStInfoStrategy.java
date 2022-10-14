package com.deloitte.crm.strategy.impl;

import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/14/18:08
 * @Description:
 */
@Component
public class UndoStInfoStrategy implements WindTaskStrategy {
    @Override
    public boolean support(Integer windDictId) {
        return false;
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        return null;
    }

    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        return null;
    }
}
