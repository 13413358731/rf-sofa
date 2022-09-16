package com.realfinance.sofa.sdebank.model;

import java.util.List;

/**
 * 2.26 元素征信工商信息(深度)
 */
public class ElementBusIInfoDto {
    /**
     * 公司名称(企业名称)
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;


    //第二点
    /**
     * 法定代表人
     */
    private String statutoryRepresentative;

    //第一点and第三点

    /**
     *股东名称
     */
    private List<String> shareholderNames;

    /**
     *股东类型
     */
    private List<String> shareholderTypes;

    /**
     * 出资比例(股权占比)
     */
    private List<Double> equityRatio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getStatutoryRepresentative() {
        return statutoryRepresentative;
    }

    public void setStatutoryRepresentative(String statutoryRepresentative) {
        this.statutoryRepresentative = statutoryRepresentative;
    }

    public List<String> getShareholderNames() {
        return shareholderNames;
    }

    public void setShareholderNames(List<String> shareholderNames) {
        this.shareholderNames = shareholderNames;
    }

    public List<String> getShareholderTypes() {
        return shareholderTypes;
    }

    public void setShareholderTypes(List<String> shareholderTypes) {
        this.shareholderTypes = shareholderTypes;
    }

    public List<Double> getEquityRatio() {
        return equityRatio;
    }

    public void setEquityRatio(List<Double> equityRatio) {
        this.equityRatio = equityRatio;
    }
}
