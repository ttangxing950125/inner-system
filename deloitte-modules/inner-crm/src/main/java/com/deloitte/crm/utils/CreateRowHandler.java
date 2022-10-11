package com.deloitte.crm.utils;

import cn.hutool.poi.excel.sax.handler.RowHandler;

import java.util.List;

public abstract class CreateRowHandler {

   protected abstract RowHandler getRowHandler(List<List<Object>> lineList);
}
