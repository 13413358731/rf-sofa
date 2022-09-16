package com.realfinance.sofa.cg.core.domain;

/**
 * 评分方法
 */
public enum EvalMethod {
    ZDPBJF("最低评标价法"),
    ZJJBJPJZJGF("最接近报价平均值价格法"),
    HLDJF("合理低价法"),
    ZHPFF("综合评分法"),
    XJBF("性价比法");

    private String zh;

    EvalMethod(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
