package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.domain.serialno.TheSingleReason;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseProject extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 部门ID
     */
    @Column(nullable = false)
    private Integer departmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目编号(采购编号)
     */
    @Column(nullable = false)
    private String projectNo;

    /**
     * 项目名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 申请采购总金额
     */
    @Column(nullable = false)
    private BigDecimal reqTotalAmount;

    /**
     * 市场参考价/市场控制总价（总）
     */
    private BigDecimal marketPrice;

    /**
     * 拟成交供应商数（成交/中标单位数）
     */
    @Column(nullable = false)
    private Integer numberOfWinSup;

    /**
     * 采购实施工时
     */
    @Column
    private String purDuration;

    /**
     * 选取的采购方式
     */
    @Column(nullable = false)
    @Enumerated
    private PurchaseMode purMode;

    /**
     * 项目特性
     */
    @Column()
    @Enumerated
    private ProjectFeatures projFeatures;

    /**
     * 选取的评标方法（拟用评审方式）
     */
    @Column(nullable = false)
    @Enumerated
    private EvalMethod evalMethod;

    /**
     * 项目申请部门
     */
    @Column(nullable = false)
    private Integer reqDepartmentId;

    /**
     * 申请人
     */
    private Integer reqUserId;

    /**
     * 申请人联系电话
     */
    private String reqUserPhone;

    /**
     * 受理日期
     */
    private LocalDateTime acceptTime;

    /**
     * 全体年度资格商
     */
    @Column
    private Integer supLabelId;

    /**
     * 采购种类(项目分类二)
     */
    @Column()
    @Enumerated
    private PurchaseType purType;

    /**
     * 合同类别
     */
    @Column(nullable = false)
    @Enumerated
    private ContractCategory contractCategory;

    /**
     * 采购类别
     */
    @Column()
    @Enumerated
    private PurchaseCategory purCategory;

    /**
     * 专家组成描述
     */
    @Column
    private String expertDescription;

    /**
     * 采购计划
     */
    @Column
    private Integer purchasePlan;
    /**
     * 全新/以往采购
     */
    @Column
    private Boolean newPurchase;

    /**
     * 项目类别
     */
    @Column
    private ProjectCategory projectCategory;

    /**
     * 采购用途
     */
    @Column(length = 1000)
    private String purpose;

    /**
     * 适用范围
     */
    @Column(length = 1000)
    private String applicableScope;

    /**
     * 采购需求
     */
    @Column(length = 1000)
    private String purReq;

    /**
     * 我行历史采购情况
     */
    @Column(length = 1000)
    private String purHistory;

    /**
     * 供应商资质要求
     */
    @Column(length = 1000)
    private String supQualReq;

    /**
     * 采购小组组成
     */
    @Column(length = 1000)
    private String purGroup;

    /**
     * 采购方式原因
     */
    @Column(length = 1000)
    private String purModeReason;

    /**
     * 评价办法说明
     */
    @Column(length = 1000)
    private String evalMethodReason;

    /**
     * 市场及供应商情况
     */
    @Column(length = 1000)
    private String marketSupProfile;

    /**
     * 有关情况说明
     */
    @Column(length = 1000)
    private String note;

    @Column(nullable = false)
    private FlowStatus status;

    @Column
    private LocalDateTime passTime;

    @Column()
    protected LocalDateTime reqCreatedTime;

    /**
     * 立项金额
     */
    @Column()
    protected BigDecimal approvalAmount;

    /**
     * 合约有效期
     */
    @Column()
    protected Integer contractValidity;

    /**
     * 单一方式原因
     */
    @Column()
    @Enumerated
    protected TheSingleReason singleReason;

    /**
     * 供应商资格要求
     */
    @Column()
    protected String supRequirements;

    /**
     * 合同制单人
     */
    @Column(nullable = false)
    protected Integer contractCreatedUserId;

    /**
     * 使用部门
     */
    @Column()
    protected String useDepartmentIds;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "req_id", updatable = false)
    private Requirement requirement;

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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getReqTotalAmount() {
        return reqTotalAmount;
    }

    public void setReqTotalAmount(BigDecimal reqTotalAmount) {
        this.reqTotalAmount = reqTotalAmount;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getNumberOfWinSup() {
        return numberOfWinSup;
    }

    public void setNumberOfWinSup(Integer numberOfWinSup) {
        this.numberOfWinSup = numberOfWinSup;
    }

    public String getPurDuration() {
        return purDuration;
    }

    public void setPurDuration(String purDuration) {
        this.purDuration = purDuration;
    }

    public PurchaseMode getPurMode() {
        return purMode;
    }

    public void setPurMode(PurchaseMode purMode) {
        this.purMode = purMode;
    }

    public ProjectFeatures getProjFeatures() {
        return projFeatures;
    }

    public void setProjFeatures(ProjectFeatures projFeatures) {
        this.projFeatures = projFeatures;
    }

    public EvalMethod getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(EvalMethod evalMethod) {
        this.evalMethod = evalMethod;
    }

    public Integer getReqDepartmentId() {
        return reqDepartmentId;
    }

    public void setReqDepartmentId(Integer reqDepartmentId) {
        this.reqDepartmentId = reqDepartmentId;
    }

    public Integer getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Integer reqUserId) {
        this.reqUserId = reqUserId;
    }

    public String getReqUserPhone() {
        return reqUserPhone;
    }

    public void setReqUserPhone(String reqUserPhone) {
        this.reqUserPhone = reqUserPhone;
    }

    public LocalDateTime getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(LocalDateTime acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Integer getSupLabelId() {
        return supLabelId;
    }

    public void setSupLabelId(Integer supLabelId) {
        this.supLabelId = supLabelId;
    }

    public PurchaseType getPurType() {
        return purType;
    }

    public void setPurType(PurchaseType purType) {
        this.purType = purType;
    }

    public ContractCategory getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(ContractCategory contractCategory) {
        this.contractCategory = contractCategory;
    }

    public PurchaseCategory getPurCategory() {
        return purCategory;
    }

    public void setPurCategory(PurchaseCategory purCategory) {
        this.purCategory = purCategory;
    }

    public String getExpertDescription() {
        return expertDescription;
    }

    public void setExpertDescription(String expertDescription) {
        this.expertDescription = expertDescription;
    }

    public Boolean getNewPurchase() {
        return newPurchase;
    }

    public void setNewPurchase(Boolean newPurchase) {
        this.newPurchase = newPurchase;
    }

    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getApplicableScope() {
        return applicableScope;
    }

    public void setApplicableScope(String applicableScope) {
        this.applicableScope = applicableScope;
    }

    public String getPurReq() {
        return purReq;
    }

    public void setPurReq(String purReq) {
        this.purReq = purReq;
    }

    public String getPurHistory() {
        return purHistory;
    }

    public void setPurHistory(String purHistory) {
        this.purHistory = purHistory;
    }

    public String getSupQualReq() {
        return supQualReq;
    }

    public void setSupQualReq(String supQualReq) {
        this.supQualReq = supQualReq;
    }

    public String getPurGroup() {
        return purGroup;
    }

    public void setPurGroup(String purGroup) {
        this.purGroup = purGroup;
    }

    public String getPurModeReason() {
        return purModeReason;
    }

    public void setPurModeReason(String purModeReason) {
        this.purModeReason = purModeReason;
    }

    public String getEvalMethodReason() {
        return evalMethodReason;
    }

    public void setEvalMethodReason(String evalMethodReason) {
        this.evalMethodReason = evalMethodReason;
    }

    public String getMarketSupProfile() {
        return marketSupProfile;
    }

    public void setMarketSupProfile(String marketSupProfile) {
        this.marketSupProfile = marketSupProfile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Integer getPurchasePlan() {
        return purchasePlan;
    }

    public void setPurchasePlan(Integer purchasePlan) {
        this.purchasePlan = purchasePlan;
    }

    public LocalDateTime getReqCreatedTime() {
        return reqCreatedTime;
    }

    public void setReqCreatedTime(LocalDateTime reqCreatedTime) {
        this.reqCreatedTime = reqCreatedTime;
    }

    public BigDecimal getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(BigDecimal approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public Integer getContractValidity() {
        return contractValidity;
    }

    public void setContractValidity(Integer contractValidity) {
        this.contractValidity = contractValidity;
    }

    public TheSingleReason getSingleReason() {
        return singleReason;
    }

    public void setSingleReason(TheSingleReason singleReason) {
        this.singleReason = singleReason;
    }

    public Integer getContractCreatedUserId() {
        return contractCreatedUserId;
    }

    public void setContractCreatedUserId(Integer contractCreatedUserId) {
        this.contractCreatedUserId = contractCreatedUserId;
    }

    public String getUseDepartmentIds() {
        return useDepartmentIds;
    }

    public void setUseDepartmentIds(String useDepartmentIds) {
        this.useDepartmentIds = useDepartmentIds;
    }

    public String getSupRequirements() {
        return supRequirements;
    }

    public void setSupRequirements(String supRequirements) {
        this.supRequirements = supRequirements;
    }

}
