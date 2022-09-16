package com.realfinance.sofa.common.ocr.model;

import java.io.Serializable;

/**
 * 营业执照
 */
public class BusinessLicense implements Serializable {

    /**
     * 营业执照
     */
    private String type;

    /**
     * 名称
     */
    private String bizLicenseCompanyName;

    /**
     * 类型/公司类型/主体类型
     */
    private String bizLicenseCompanyType;

    /**
     * 住所/经营场所/主要经营场所/营业场所
     */
    private String bizLicenseAddress;

    /**
     * 注册号
     */
    private String bizLicenseRegistrationCode;

    /**
     * 证照编号
     */
    private String bizLicenseSerialNumber;

    /**
     * 法定代表人/负责人/经营者/经营者姓名
     */
    private String bizLicenseOwnerName;

    /**
     * 注册资本
     */
    private String bizLicenseRegCapital;

    /**
     * 实收资本
     */
    private String bizLicensePaidinCapital;

    /**
     * 经营范围
     */
    private String bizLicenseScope;

    /**
     * 成立日期/注册日期
     */
    private String bizLicenseStartTime;

    /**
     * 组成形式
     */
    private String bizLicenseComposingForm;

    /**
     * 营业期限
     */
    private String bizLicenseOperatingPeriod;

    /**
     * 统一社会信用代码
     */
    private String bizLicenseCreditCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizLicenseCompanyName() {
        return bizLicenseCompanyName;
    }

    public void setBizLicenseCompanyName(String bizLicenseCompanyName) {
        this.bizLicenseCompanyName = bizLicenseCompanyName;
    }

    public String getBizLicenseCompanyType() {
        return bizLicenseCompanyType;
    }

    public void setBizLicenseCompanyType(String bizLicenseCompanyType) {
        this.bizLicenseCompanyType = bizLicenseCompanyType;
    }

    public String getBizLicenseAddress() {
        return bizLicenseAddress;
    }

    public void setBizLicenseAddress(String bizLicenseAddress) {
        this.bizLicenseAddress = bizLicenseAddress;
    }

    public String getBizLicenseRegistrationCode() {
        return bizLicenseRegistrationCode;
    }

    public void setBizLicenseRegistrationCode(String bizLicenseRegistrationCode) {
        this.bizLicenseRegistrationCode = bizLicenseRegistrationCode;
    }

    public String getBizLicenseSerialNumber() {
        return bizLicenseSerialNumber;
    }

    public void setBizLicenseSerialNumber(String bizLicenseSerialNumber) {
        this.bizLicenseSerialNumber = bizLicenseSerialNumber;
    }

    public String getBizLicenseOwnerName() {
        return bizLicenseOwnerName;
    }

    public void setBizLicenseOwnerName(String bizLicenseOwnerName) {
        this.bizLicenseOwnerName = bizLicenseOwnerName;
    }

    public String getBizLicenseRegCapital() {
        return bizLicenseRegCapital;
    }

    public void setBizLicenseRegCapital(String bizLicenseRegCapital) {
        this.bizLicenseRegCapital = bizLicenseRegCapital;
    }

    public String getBizLicensePaidinCapital() {
        return bizLicensePaidinCapital;
    }

    public void setBizLicensePaidinCapital(String bizLicensePaidinCapital) {
        this.bizLicensePaidinCapital = bizLicensePaidinCapital;
    }

    public String getBizLicenseScope() {
        return bizLicenseScope;
    }

    public void setBizLicenseScope(String bizLicenseScope) {
        this.bizLicenseScope = bizLicenseScope;
    }

    public String getBizLicenseStartTime() {
        return bizLicenseStartTime;
    }

    public void setBizLicenseStartTime(String bizLicenseStartTime) {
        this.bizLicenseStartTime = bizLicenseStartTime;
    }

    public String getBizLicenseComposingForm() {
        return bizLicenseComposingForm;
    }

    public void setBizLicenseComposingForm(String bizLicenseComposingForm) {
        this.bizLicenseComposingForm = bizLicenseComposingForm;
    }

    public String getBizLicenseOperatingPeriod() {
        return bizLicenseOperatingPeriod;
    }

    public void setBizLicenseOperatingPeriod(String bizLicenseOperatingPeriod) {
        this.bizLicenseOperatingPeriod = bizLicenseOperatingPeriod;
    }

    public String getBizLicenseCreditCode() {
        return bizLicenseCreditCode;
    }

    public void setBizLicenseCreditCode(String bizLicenseCreditCode) {
        this.bizLicenseCreditCode = bizLicenseCreditCode;
    }
}
