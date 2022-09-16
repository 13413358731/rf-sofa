package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CgSupplierExaminationDetailsSaveDto {

    private Integer id;

    /**
     * 公司名称
     */
    @NotBlank
    private String name;
    /**
     * 业务类型（新注册，门户信息修改，系统内信息修改）
     */
    @NotBlank
    private String category;

    /**
     * 统一社会信用代码
     */
    @NotBlank
    private String unifiedSocialCreditCode;
    /**
     * 类型（如有限责任公司等）
     */
    @NotBlank
    private String companyType;
    /**
     * 公司住所
     */
    @NotBlank
    private String companyDomicile;
    /**
     * 法定代表人
     */
    @NotBlank
    private String statutoryRepresentative;
    /**
     * 注册资本
     */
    @NotNull
    private BigDecimal registeredCapital;
    /**
     * 注册资本大写
     */
    @NotBlank
    private String registeredCapitalUppercase;
    /**
     * 注册资本币种
     */
    @NotBlank
    private String registeredCapitalCurrency;
    /**
     * 成立日期
     */
    @NotNull
    private LocalDate setupDate;
    /**
     * 营业期限（开始）
     */
    @NotNull
    private LocalDate businessTermStart;
    /**
     * 营业期限（结束），长期则9999-12-31
     */
    @NotNull
    private LocalDate businessTermEnd;
    /**
     * 经营范围
     */
    @NotBlank
    private String businessScope;
    /**
     * 员工数量
     */
    @NotNull
    private Integer numberOfEmployees;
    /**
     * 纳税人属性
     */
    @NotBlank
    private String taxpayerAttribute;
    /**
     * 营业场地面积
     */
    @NotBlank
    private String businessArea;
    /**
     * 公司网址
     */
    private String companyWebsite;
    /**
     * 公司经营地址
     */
    @NotBlank
    private String companyAddress;
    /**
     * 公司组织结构
     */
    @NotBlank
    private String companyStructure;
    /**
     * 供应商基本情况介绍
     */
    @NotBlank
    private String companyProfile;
    /**
     * 公司性质
     */
    @NotBlank
    private String companyNature;
    /**
     * 市场主体
     */
    @NotBlank
    private String marketSubject;
    /**
     * 专业认证资质
     */
    @NotBlank
    private String qualityCertification;
    /**
     * 近三年财务状况
     */
    @NotBlank
    private String financialStanding;
    /**
     * 银行授信额度（如有）
     */
    @NotBlank
    private String bankCreditLine;
    /**
     * 主要客户群及占销售比例情况
     */
    @NotBlank
    private String targetCustomerProfile;
    /**
     * 市场主要竞争对手及份额排名情况
     */
    @NotBlank
    private String competitorProfile;
    /**
     * 所属行业水平及外部评价情况
     */
    @NotBlank
    private String industryLevelProfile;
    /**
     * 风险管理能力（含突发时间应对能力）
     */
    @NotBlank
    private String riskManagementProfile;
    /**
     * 上级母公司名称（如有）
     */
    private String parentCompany;
    /**
     * 法人身份证姓名
     */
    @NotBlank
    private String idCardName;
    /**
     * 法人身份证号
     */
    @NotBlank
    private String idCardNumber;
    /**
     * 法人身份证地址
     */
    @NotBlank
    private String idCardAddress;

    /**
     * 停用开始时间
     */
    private LocalDateTime disableTermStart;

    /**
     * 停用终止时间
     */
    private LocalDateTime disableTermEnd;

    @NotBlank
    private String idCardMobile;

    @NotBlank
    private String idCardEmail;

    @NotBlank
    private String authIdCardName;

    @NotBlank
    private String authIdCardNumber;

    @NotBlank
    private String authIdCardAddress;

    @NotBlank
    private String authIdCardMobile;

    @NotBlank
    private String authIdCardEmail;

    @NotBlank
    private String telephone;

    private Boolean taxIsNormalThree;

    private Boolean badDebtThree;

    private Boolean adminPenaltyThree;

    private Boolean blackListThree;

    /**
     * 账号ID
     */
    @NotNull
    private Integer account;

    /**
     * 联系人
     */
    @NotEmpty
    private List<CgSupplierExaminationContactsDto> contacts;

    /**
     * 联系人
     */
    private List<CgSupplierExaminationQuallityAuthDto> qualityAuths;

    /**
     * 附件
     */
    @NotEmpty
    private List<CgAttachmentDto> attachments;

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

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public List<CgSupplierExaminationQuallityAuthDto> getQualityAuths() {
        return qualityAuths;
    }

    public void setQualityAuths(List<CgSupplierExaminationQuallityAuthDto> qualityAuths) {
        this.qualityAuths = qualityAuths;
    }

    public List<CgSupplierExaminationContactsDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<CgSupplierExaminationContactsDto> contacts) {
        this.contacts = contacts;
    }

    public List<CgAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttachmentDto> attachments) {
        this.attachments = attachments;
    }
}
