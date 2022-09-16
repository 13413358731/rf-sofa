package com.realfinance.sofa.cg.sup.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 供应商信息修改VO
 */
public class CgSupplierEditVo {
    /**
     * 公司名称
     */
    protected String name;

    /**
     * 统一社会信用代码
     */
    protected String unifiedSocialCreditCode;
    /**
     * 类型（如有限责任公司等）
     */
    protected String companyType;
    /**
     * 公司住所
     */
    protected String companyDomicile;
    /**
     * 法定代表人
     */
    protected String statutoryRepresentative;
    /**
     * 注册资本
     */
    protected BigDecimal registeredCapital;
    /**
     * 注册资本大写
     */
    protected String registeredCapitalUppercase;
    /**
     * 注册资本币种
     */
    protected String registeredCapitalCurrency;
    /**
     * 成立日期
     */
    protected LocalDate setupDate;
    /**
     * 营业期限（开始）
     */
    protected LocalDate businessTermStart;
    /**
     * 营业期限（结束），长期则9999-12-31
     */
    protected LocalDate businessTermEnd;
    /**
     * 经营范围
     */
    protected String businessScope;
    /**
     * 员工数量
     */
    protected Integer numberOfEmployees;
    /**
     * 纳税人属性
     */
    protected String taxpayerAttribute;
    /**
     * 营业场地面积
     */
    protected String businessArea;
    /**
     * 公司网址
     */
    protected String companyWebsite;
    /**
     * 公司经营地址
     */
    protected String companyAddress;
    /**
     * 公司组织结构
     */
    protected String companyStructure;
    /**
     * 供应商基本情况介绍
     */
    protected String companyProfile;
    /**
     * 公司性质
     */
    protected String companyNature;
    /**
     * 市场主体
     */
    protected String marketSubject;
    /**
     * 专业认证资质
     */
    protected String qualityCertification;
    /**
     * 近三年财务状况
     */
    protected String financialStanding;
    /**
     * 银行授信额度（如有）
     */
    protected String bankCreditLine;
    /**
     * 主要客户群及占销售比例情况
     */
    protected String targetCustomerProfile;
    /**
     * 市场主要竞争对手及份额排名情况
     */
    protected String competitorProfile;
    /**
     * 所属行业水平及外部评价情况
     */
    protected String industryLevelProfile;
    /**
     * 风险管理能力（含突发时间应对能力）
     */
    protected String riskManagementProfile;
    /**
     * 上级母公司名称（如有）
     */
    protected String parentCompany;
    /**
     * 法人身份证姓名
     */
    protected String idCardName;
    /**
     * 法人身份证号
     */
    protected String idCardNumber;
    /**
     * 法人身份证地址
     */
    protected String idCardAddress;

    @Schema(description = "法人手机号")
    protected String idCardMobile;

    @Schema(description = "法人邮箱")
    protected String idCardEmail;

    @Schema(description = "法人授权人身份证姓名")
    protected String authIdCardName;

    @Schema(description = "法人授权人身份证号")
    protected String authIdCardNumber;

    @Schema(description = "法人授权人身份证地址")
    protected String authIdCardAddress;

    @Schema(description = "法人授权人手机号")
    protected String authIdCardMobile;

    @Schema(description = "法人授权人邮箱")
    protected String authIdCardEmail;

    @Schema(description = "电话")
    protected String telephone;

    @Schema(description = "近3年纳税是否正常")
    protected Boolean taxIsNormalThree;

    @Schema(description = "近3年是否存在不良债务")
    protected Boolean badDebtThree;

    @Schema(description = "近3年是否存在行政处罚")
    protected Boolean adminPenaltyThree;

    @Schema(description = "近3年是否被工商列入黑名单")
    protected Boolean blackListThree;

    /**
     * 联系人
     */
    protected List<CgSupplierContactsDto> contacts;

    /**
     * 资质授权
     */
    protected List<CgSupplierExaminationQuallityAuthDto> qualityAuths;

    /**
     * 附件
     */
    protected List<AttachmentVo> attachments;

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

    public List<CgSupplierContactsDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<CgSupplierContactsDto> contacts) {
        this.contacts = contacts;
    }

    public List<AttachmentVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentVo> attachments) {
        this.attachments = attachments;
    }

    public List<CgSupplierExaminationQuallityAuthDto> getQualityAuths() {
        return qualityAuths;
    }

    public void setQualityAuths(List<CgSupplierExaminationQuallityAuthDto> qualityAuths) {
        this.qualityAuths = qualityAuths;
    }
}
