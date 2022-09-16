package com.realfinance.sofa.common.datascope;

/**
 * 这个将作为解析数据规则值EL表达式时的ROOT
 */
public class VariableParser {

    public Object department() {
        return DataScopeUtils.loadDepartmentId().map(Object::toString).orElse("null");
    }

    public Object user(){
        return DataScopeUtils.loadPrincipalId().map(Object::toString).orElse("null");
    }

}
