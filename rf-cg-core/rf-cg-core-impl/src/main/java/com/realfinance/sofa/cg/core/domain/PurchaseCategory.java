package com.realfinance.sofa.cg.core.domain;

/**
 * 采购类别
 */
public enum  PurchaseCategory {
    MLCG("目录采购"),
    FMLCG("非目录采购");


    private String zh;

    PurchaseCategory(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
