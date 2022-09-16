package com.realfinance.sofa.cg.core.model;

public class CgPurchaseResultNoticeQueryCriteria {

    /**
     * 外部结果通知标题
     */
    private String outsideTitleLike;


    /**
     * 内部结果通知标题
     */
    private String insideTitleLike;

    public String getOutsideTitleLike() {
        return outsideTitleLike;
    }

    public void setOutsideTitleLike(String outsideTitleLike) {
        this.outsideTitleLike = outsideTitleLike;
    }

    public String getInsideTitleLike() {
        return insideTitleLike;
    }

    public void setInsideTitleLike(String insideTitleLike) {
        this.insideTitleLike = insideTitleLike;
    }
}
