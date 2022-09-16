package com.realfinance.sofa.cg.core.domain;

/**
 * 合同类别
 */
public enum ContractCategory {

    DJHT("单价合同"),

    ZJHT("总价合同"),

    KJHT("框架合同");

    private String zh;

    ContractCategory(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
