package com.realfinance.sofa.cg.core.domain;

/**
 * 采购方式
 */
public enum PurchaseMode {
    /**
     * 公开招标
     */
    GKZB("公开招标"),
    /**
     * 邀请招标
     */
    YQZB("邀请招标"),
    /**
     * 竞争性谈判
     */
    JZXTP("竞争性谈判"),
    /**
     * 竞争性磋商
     */
    JZXCS("竞争性磋商"),
    /**
     * 询价
     */
    XJ("询价"),
    /**
     * 单一来源
     */
    DYLY("单一来源");

    /**
     * 中文名
     */
    private String zh;

    PurchaseMode(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
