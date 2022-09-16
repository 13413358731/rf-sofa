package com.realfinance.sofa.cg.core.domain;

/**
 * 采购种类
 */
public enum PurchaseType {
    KJ("科技"),
    JJ("基建"),
    ZH("综合");

    /**
     * 中文名
     */
    private String zh;

    PurchaseType(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
