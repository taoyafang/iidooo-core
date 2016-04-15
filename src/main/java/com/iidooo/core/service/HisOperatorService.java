package com.iidooo.core.service;

import javax.servlet.http.HttpServletRequest;

public interface HisOperatorService {
    void createHisOperator(String tableName, Integer tableKey, HttpServletRequest request);
    
    int getPVCount(String tableName, Integer tableKey, String operation);
    
    int getUVCount(String tableName, Integer tableKey, String operation);
}
