package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CgSupplierExaminationDto extends BaseDto implements Serializable {

    private Integer id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 业务类型（新注册，门户信息修改，系统内信息修改）
     */
    private String category;
    /**
     * 处理状态
     */
    private String status;
    /**
     * 理由（驳回时的理由）
     */
    private String reason;
    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;
    /**
     * 类型（如有限责任公司等）
     */
    private String companyType;
    /**
     * 公司住所
     */
    private String companyDomicile;
    /**
     * 法定代表人
     */
    private String statutoryRepresentative;
    /**
     * 注册资本
     */
    private BigDecimal registeredCapital;
    /**
     * 注册资本大写
     */
    private String registeredCapitalUppercase;
    /**
     * 注册资本币种
     */
    private String registeredCapitalCurrency;
    /**
     * 成立日期
     */
    private LocalDate setupDate;
    /**
     * 营业期限（开始）
     */
    private LocalDate businessTermStart;
    /**
     * 营业期限（结束），长期则9999-12-31
     */
    private LocalDate businessTermEnd;
    /**
     * 经营范围
     */
    private String businessScope;
    /**
     * 员工数量
     */
    private Integer numberOfEmployees;
    /**
     * 纳税人属性
     */
    private String taxpayerAttribute;
    /**
     * 营业场地面积
     */
    private String businessArea;
    /**
     * 公司网址
     */
    private String companyWebsite;
    /**
     * 公司经营地址
     */
    private String companyAddress;
    /**
     * 公司组织结构
     */
    private String companyStructure;
    /**
     * 供应商基本情况介绍
     */
    private String companyProfile;
    /**
     * 公司性质
     */
    private String companyNature;
    /**
     * 市场主体
     */
    private String marketSubject;
    /**
     * 专业认证资质
     */
    private String qualityCertification;
    /**
     * 近三年财务状况
     */
    private String financialStanding;
    /**
     * 银行授信额度（如有）
     */
    private String bankCreditLine;
    /**
     * 主要客户群及占销售比例情况
     */
    private String targetCustomerProfile;
    /**
     * 市场主要竞争对手及份额排名情况
     */
    private String competitorProfile;
    /**
     * 所属行业水平及外部评价情况
     */
    private String industryLevelProfile;
    /**
     * 风险管理能力（含突发时间应对能力）
     */
    private String riskManagementProfile;
    /**
     * 上级母公司名称（如有）
     */
    private String parentCompany;
    /**
     * 法人身份证姓名
     */
    private String idCardName;
    /**
     * 法人身份证号
     */
    private String idCardNumber;
    /**
     * 法人身份证地址
     */
    private String idCardAddress;

    /**
     * 停用开始时间
     */
    protected LocalDateTime disableTermStart;

    /**
     * 停用终止时间
     */
    protected LocalDateTime disableTermEnd;

    private CgSupplierAccountDto account;

    /**
     * 法人手机号
     */
    protected String idCardMobile;

    /**
     * 法人邮箱
     */
    protected String idCardEmail;

    /**
     * 法人授权人身份证姓名
     */
    protected String authIdCardName;

    /**
     * 法人授权人身份证号
     */
    protected String authIdCardNumber;

    /**
     * 法人授权人身份证地址
     */
    protected String authIdCardAddress;

    /**
     * 法人授权人手机号
     */
    protected String authIdCardMobile;

    /**
     * 法人授权人邮箱
     */
    protected String authIdCardEmail;

    /**
     * 电话
     */
    protected String telephone;

    /**
     * 近3年纳税是否正常
     */
    protected Boolean taxIsNormalThree;

    /**
     *近3年是否存在不良债务
     */
    protected Boolean badDebtThree;

    /**
     *近3年是否存在行政处罚
     */
    protected Boolean adminPenaltyThree;

    /**
     *近3年是否被工商列入黑名单
     */
    protected Boolean blackListThree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyDomicile() {
        return companyDomicile;
    }

    public void setCompanyDomicile(String companyDomicile) {
        this.companyDomicile = companyDomicile;
    }

    public String getStatutoryRepresentative() {
        return statutoryRepresentative;
    }

    public void setStatutoryRepresentative(String statutoryRepresentative) {
        this.statutoryRepresentative = statutoryRepresentative;
    }

    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getRegisteredCapitalUppercase() {
        return registeredCapitalUppercase;
    }

    public void setRegisteredCapitalUppercase(String registeredCapitalUppercase) {
        this.registeredCapitalUppercase = registeredCapitalUppercase;
    }

    public String getRegisteredCapitalCurrency() {
        return registeredCapitalCurrency;
    }

    public void setRegisteredCapitalCurrency(String registeredCapitalCurrency) {
        this.registeredCapitalCurrency = registeredCapitalCurrency;
    }

    public LocalDate getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(LocalDate setupDate) {
        this.setupDate = setupDate;
    }

    public LocalDate getBusinessTermStart() {
        return businessTermStart;
    }

    public void setBusinessTermStart(LocalDate businessTermStart) {
        this.businessTermStart = businessTermStart;
    }

    public LocalDate getBusinessTermEnd() {
        return businessTermEnd;
    }

    public void setBusinessTermEnd(LocalDate businessTermEnd) {
        this.businessTermEnd = businessTermEnd;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getTaxpayerAttribute() {
        return taxpayerAttribute;
    }

    public void setTaxpayerAttribute(String taxpayerAttribute) {
        this.taxpayerAttribute = taxpayerAttribute;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyStructure() {
        return companyStructure;
    }

    public void setCompanyStructure(String companyStructure) {
        this.companyStructure = companyStructure;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature;
    }

    public String getMarketSubject() {
        return marketSubject;
    }

    public void setMarketSubject(String marketSubject) {
        this.marketSubject = marketSubject;
    }

    public String getQualityCertification() {
        return qualityCertification;
    }

    public void setQualityCertification(String qualityCertification) {
        this.qualityCertification = qualityCertification;
    }

    public String getFinancialStanding() {
        return financialStanding;
    }

    public void setFinancialStanding(String financialStanding) {
        this.financialStanding = financialStanding;
    }

    public String getBankCreditLine() {
        return bankCreditLine;
    }

    public void setBankCreditLine(String bankCreditLine) {
        this.bankCreditLine = bankCreditLine;
    }

    public String getTargetCustomerProfile() {
        return targetCustomerProfile;
    }

    public void setTargetCustomerProfile(String targetCustomerProfile) {
        this.targetCustomerProfile = targetCustomerProfile;
    }

    public String getCompetitorProfile() {
        return competitorProfile;
    }

    public void setCompetitorProfile(String competitorProfile) {
        this.competitorProfile = competitorProfile;
    }

    public String getIndustryLevelProfile() {
        return industryLevelProfile;
    }

    public void setIndustryLevelProfile(String industryLevelProfile) {
        this.industryLevelProfile = industryLevelProfile;
    }

    public String getRiskManagementProfile() {
        return riskManagementProfile;
    }

    public void setRiskManagementProfile(String riskManagementProfile) {
        this.riskManagementProfile = riskManagementProfile;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardAddress() {
        return idCardAddress;
    }

    public void setIdCardAddress(String idCardAddress) {
        this.idCardAddress = idCardAddress;
    }

    public LocalDateTime getDisableTermStart() {
        return disableTermStart;
    }

    public void setDisableTermStart(LocalDateTime disableTermStart) {
        this.disableTermStart = disableTermStart;
    }

    public LocalDateTime getDisableTermEnd() {
        return disableTermEnd;
    }

    public void setDisableTermEnd(LocalDateTime disableTermEnd) {
        this.disableTermEnd = disableTermEnd;
    }

    public String getIdCardMobile() {
        return idCardMobile;
    }

    public void setIdCardMobile(String idCardMobile) {
        this.idCardMobile = idCardMobile;
    }

    public String getIdCardEmail() {
        return idCardEmail;
    }

    public void setIdCardEmail(String idCardEmail) {
        this.idCardEmail = idCardEmail;
    }

    public String getAuthIdCardName() {
        return authIdCardName;
    }

    public void setAuthIdCardName(String authIdCardName) {
        this.authIdCardName = authIdCardName;
    }

    public String getAuthIdCardNumber() {
        return authIdCardNumber;
    }

    public void setAuthIdCardNumber(String authIdCardNumber) {
        this.authIdCardNumber = authIdCardNumber;
    }

    public String getAuthIdCardAddress() {
        return authIdCardAddress;
    }

    public void setAuthIdCardAddress(String authIdCardAddress) {
        this.authIdCardAddress = authIdCardAddress;
    }

    public String getAuthIdCardMobile() {
        return authIdCardMobile;
    }

    public void setAuthIdCardMobile(String authIdCardMobile) {
        this.authIdCardMobile = authIdCardMobile;
    }

    public String getAuthIdCardEmail() {
        return authIdCardEmail;
    }

    public void setAuthIdCardEmail(String authIdCardEmail) {
        this.authIdCardEmail = authIdCardEmail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getTaxIsNormalThree() {
        return taxIsNormalThree;
    }

    public void setTaxIsNormalThree(Boolean taxIsNormalThree) {
        this.taxIsNormalThree = taxIsNormalThree;
    }

    public Boolean getBadDebtThree() {
        return badDebtThree;
    }

    public void setBadDebtThree(Boolean badDebtThree) {
        this.badDebtThree = badDebtThree;
    }

    public Boolean getAdminPenaltyThree() {
        return adminPenaltyThree;
    }

    public void setAdminPenaltyThree(Boolean adminPenaltyThree) {
        this.adminPenaltyThree = adminPenaltyThree;
    }

    public Boolean getBlackListThree() {
        return blackListThree;
    }

    public void setBlackListThree(Boolean blackListThree) {
        this.blackListThree = blackListThree;
    }

    public CgSupplierAccountDto getAccount() {
        return account;
    }

    public void setAccount(CgSupplierAccountDto account) {
        this.account = account;
    }
}
