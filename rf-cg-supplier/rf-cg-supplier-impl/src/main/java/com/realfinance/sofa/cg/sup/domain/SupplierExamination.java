package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商审核
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EXAMINATION", indexes = {
        @Index(columnList = "unifiedSocialCreditCode,tenantId")
})
@NamedEntityGraph(name = "SupplierExamination{account,contacts}",
        attributeNodes = {
                @NamedAttributeNode("account"),
                @NamedAttributeNode("contacts")
        })
public class SupplierExamination extends BaseEntity implements IEntity<Integer> {

    public enum Category {
        /**
         * 创建
         */
        INITIAL,
        /**
         * 在门户修改
         */
        MODIFY_FROM_PORTAL,
        /**
         * 在采购系统修改
         */
        MODIFY_FROM_INTERNAL;
    }

    public SupplierExamination() {
        this.contacts = new ArrayList<>();
        this.attachments = new ArrayList<>();
        this.qualityAuths = new ArrayList<>();
    }

    @Version
    private Long v;

    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 公司名称
     */
    @Column(nullable = false, length = 100)
    private String name;
    /**
     * 业务类型（新注册，门户信息修改，系统内信息修改）
     */
    @Enumerated
    @Column(nullable = false)
    private Category category;
    /**
     * 处理状态
     */
    @Enumerated
    @Column(nullable = false)
    private FlowStatus status;
    /**
     * 审批通过时间
     */
    @Column
    private LocalDateTime passTime;
    /**
     * 理由（驳回时的理由）
     */
    @Column()
    private String reason;
    /**
     * 统一社会信用代码
     */
    @Column(nullable = false, length = 18)
    private String unifiedSocialCreditCode;
    /**
     * 类型（如有限责任公司等）
     */
    @Column(nullable = false, length = 50)
    private String companyType;
    /**
     * 公司住所
     */
    @Column(nullable = false)
    private String companyDomicile;
    /**
     * 法定代表人
     */
    @Column(nullable = false, length = 50)
    private String statutoryRepresentative;
    /**
     * 注册资本
     */
    @Column(nullable = false, length = 20, scale = 2)
    private BigDecimal registeredCapital;
    /**
     * 注册资本大写
     */
    @Column(length = 50)
    private String registeredCapitalUppercase;
    /**
     * 注册资本币种
     */
    @Column(nullable = false, length = 5)
    private Supplier.CurrencyUnit registeredCapitalCurrency;
    /**
     * 成立日期
     */
    @Column(nullable = false)
    private LocalDate setupDate;
    /**
     * 营业期限（开始）
     */
    @Column(nullable = false)
    private LocalDate businessTermStart;
    /**
     * 营业期限（结束），长期则9999-12-31
     */
    @Column(nullable = false)
    private LocalDate businessTermEnd;
    /**
     * 经营范围
     */
    @Column(nullable = false)
    private String businessScope;
    /**
     * 员工数量
     */
    @Column(nullable = false)
    private Integer numberOfEmployees;
    /**
     * 纳税人属性
     */
    @Enumerated
    @Column()
    private TaxpayerAttribute taxpayerAttribute;
    /**
     * 营业场地面积
     */
    @Column(length = 50)
    private String businessArea;
    /**
     * 公司网址
     */
    @Column
    private String companyWebsite;
    /**
     * 公司经营地址
     */
    @Column(nullable = false)
    private String companyAddress;
    /**
     * 公司组织结构
     */
    @Column(length = 1000)
    private String companyStructure;
    /**
     * 供应商基本情况介绍
     */
    @Column(length = 1000)
    private String companyProfile;
    /**
     * 公司性质
     */
    @Enumerated
    @Column()
    private CompanyNature companyNature;
    /**
     * 市场主体
     */
    @Enumerated
    @Column()
    private MarketSubject marketSubject;
    /**
     * 专业认证资质
     */
    @Column(length = 1000)
    private String qualityCertification;
    /**
     * 近三年财务状况
     */
    @Column(nullable = false, length = 1000)
    private String financialStanding;
    /**
     * 银行授信额度（如有）
     */
    @Column(length = 1000)
    private String bankCreditLine;
    /**
     * 主要客户群及占销售比例情况
     */
    @Column(length = 1000)
    private String targetCustomerProfile;
    /**
     * 市场主要竞争对手及份额排名情况
     */
    @Column(length = 1000)
    private String competitorProfile;
    /**
     * 所属行业水平及外部评价情况
     */
    @Column(length = 1000)
    private String industryLevelProfile;
    /**
     * 风险管理能力（含突发时间应对能力）
     */
    @Column(length = 1000)
    private String riskManagementProfile;
    /**
     * 上级母公司名称（如有）
     */
    @Column(length = 100)
    private String parentCompany;
    /**
     * 法人身份证姓名
     */
    @Column(nullable = false)
    private String idCardName;
    /**
     * 法人身份证号
     */
    @Column(nullable = false)
    private String idCardNumber;
    /**
     * 法人身份证地址
     */
    @Column(nullable = false)
    private String idCardAddress;

    /**
     * 停用开始时间
     */
    @Column
    private LocalDateTime disableTermStart;

    /**
     * 停用终止时间
     */
    @Column
    private LocalDateTime disableTermEnd;

    /**
     * 法人手机号
     */
    @Column(length = 15)
    private String idCardMobile;

    /**
     * 法人邮箱
     */
    @Column()
    @Email
    private String idCardEmail;

    /**
     * 法人授权人身份证姓名
     */
    @Column()
    private String authIdCardName;
    /**
     * 法人授权人身份证号
     */
    @Column()
    private String authIdCardNumber;
    /**
     * 法人授权人身份证地址
     */
    @Column()
    private String authIdCardAddress;

    /**
     * 法人授权人手机号
     */
    @Column(length = 15)
    private String authIdCardMobile;

    /**
     * 法人授权人邮箱
     */
    @Column()
    @Email
    private String authIdCardEmail;

    /**
     * 电话
     */
    @Column(length = 15)
    private String telephone;

    /**
     * 近3年纳税是否正常
     */
    @Column()
    private Boolean taxIsNormalThree;

    /**
     *近3年是否存在不良债务
     */
    @Column()
    private Boolean badDebtThree;

    /**
     *近3年是否存在行政处罚
     */
    @Column()
    private Boolean adminPenaltyThree;

    /**
     *近3年是否被工商列入黑名单
     */
    @Column()
    private Boolean blackListThree;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", updatable = false)
    private SupplierAccount account;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplier_examination_id")
    private List<SupplierExaminationContacts> contacts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplier_examination_id")
    private List<SupplierExaminationAttachment> attachments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplier_examination_id")
    private List<SupplierExaminationCredit> credits;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplier_examination_id")
    private List<SupplierExaminationQualityAuth> qualityAuths;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
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

    public Supplier.CurrencyUnit getRegisteredCapitalCurrency() {
        return registeredCapitalCurrency;
    }

    public void setRegisteredCapitalCurrency(Supplier.CurrencyUnit registeredCapitalCurrency) {
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

    public TaxpayerAttribute getTaxpayerAttribute() {
        return taxpayerAttribute;
    }

    public void setTaxpayerAttribute(TaxpayerAttribute taxpayerAttribute) {
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

    public CompanyNature getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(CompanyNature companyNature) {
        this.companyNature = companyNature;
    }

    public MarketSubject getMarketSubject() {
        return marketSubject;
    }

    public void setMarketSubject(MarketSubject marketSubject) {
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

    public SupplierAccount getAccount() {
        return account;
    }

    public void setAccount(SupplierAccount account) {
        this.account = account;
    }

    public List<SupplierExaminationContacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<SupplierExaminationContacts> contacts) {
        this.contacts = contacts;
    }

    public List<SupplierExaminationAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SupplierExaminationAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<SupplierExaminationQualityAuth> getQualityAuths() {
        return qualityAuths;
    }

    public void setQualityAuths(List<SupplierExaminationQualityAuth> qualityAuths) {
        this.qualityAuths = qualityAuths;
    }

    public List<SupplierExaminationCredit> getCredits() {
        return credits;
    }

    public void setCredits(List<SupplierExaminationCredit> credits) {
        this.credits = credits;
    }
}
