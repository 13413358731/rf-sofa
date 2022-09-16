package com.realfinance.sofa.cg.core.domain.req;

import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.serialno.TheSingleReason;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购需求
 */
@MappedSuperclass
public abstract class BaseRequirement extends BaseEntity implements IEntity<Integer> {

    /**
     * 受理状态
     */
    public enum AcceptStatus {
        /**
         * 初始
         */
        CS,
        /**
         * 待处理
         */
        DCL,
        /**
         * 退回
         */
        TH,
        /**
         * 通过
         */
        TG,
        /**
         * 方案退回
         */
        FATH,
        /**
         * 方案执行退回
         */
        FAZXTH,
    }

    /**
     * 计划状态
     */
    public enum PlanStatus {
        /**
         * 计划内
         */
        JHN,
        /**
         * 计划有改变
         */
        JHYGB,
        /**
         * 计划外
         */
        JHW;
    }

    /**
     * 项目分类三
     */
    public enum ProjCateThree {
        /**
         * 货物
         */
        GOODS,
        /**
         * 服务
         */
        SERVICE,
        /**
         * 工程
         */
        PROJECT;
    }

    @Version
    protected Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    protected String tenantId;

    /**
     * 部门ID
     */
    @Column(nullable = false)
    protected Integer departmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 采购申请名称
     */
    @Column(nullable = false)
    protected String name;

    /**
     * 申请采购金额
     */
    @Column(nullable = false)
    protected BigDecimal reqTotalAmount;

    /**
     * 市场参考价/市场控制总价（总）
     */
    @Column
    protected BigDecimal marketPrice;

    /**
     * 申请人联系电话
     */
    @Column(nullable = false, length = 50)
    protected String reqUserPhone;

    /**
     * 使用部门
     */
    protected String useDepartmentIds;

    /**
     * 采购部门
     */
    @Column(nullable = false)
    protected Integer purDepartmentId;

    /**
     * 受理状态
     */
    @Column(nullable = false)
    @Enumerated
    protected AcceptStatus acceptStatus;

    /**
     * 处理意见
     */
    @Column(length = 2000)
    protected String reason;

    /**
     * 经办人用户ID
     */
    @Column
    protected Integer operatorUserId;

    /**
     * 描述（分配经办人时留记录）
     */
    @Column(length = 2048)
    protected String comment;

    /**
     * 供应商资格要求
     */
    @Column(nullable = false)
    protected String supRequirements;

    /**
     * 供应商特殊资格要求
     */
    @Column
    protected String supSpecialRequirements;

    /**
     * 备注
     */
    protected String note;

    /**
     * 采购种类项目分类二
     */
    @Column()
    @Enumerated
    protected PurchaseType purType;

    /**
     * 拟用采购方式
     */
    @Column(nullable = false)
    @Enumerated
    protected PurchaseMode purMode;

    /**
     * 项目特性
     */
    @Column()
    @Enumerated
    protected ProjectFeatures projFeatures;

    /**
     * 推荐评分方法
     */
    @Column(nullable = false)
    @Enumerated
    protected EvalMethod sugEvalMethod;

    /**
     * 合同类别
     */
    @Column(nullable = false)
    @Enumerated
    protected ContractCategory contractCategory;

    /**
     * 合同制单人
     */
    @Column(nullable = false)
    protected Integer contractCreatedUserId;

    /**
     * 合约有效期
     */
    @Column()
    protected Integer contractValidity;

    /**
     * 采购类别
     */
    @Column()
    @Enumerated
    protected PurchaseCategory purCategory;

    /**
     * 是否计划内
     */
    @Column(nullable = false)
    @Enumerated
    protected PlanStatus planStatus;

   /* @OneToOne
    @JoinColumn(name = "plan_id")
    protected PurchasePlan plan;*/

    /**
     * 数量
     */
    @Column
    protected Integer number;

    /**
     * 中标供应商数
     */
    @Column(nullable = false)
    protected Integer numberOfWinSup;

    /**
     * 采购内容
     */
    @Column
    protected String purContent;

    /**
     * 供货（服务）地点
     */
    @Column
    protected String supplyLocation;

    /**
     * 交货（完成）时间
     */
    @Column
    protected String deliveryTime;

    /**
     * 合同期限
     */
    @Column
    protected String contractTerm;

    /**
     * 质量要求
     */
    @Column
    protected String qualityRequirements;

    /**
     * 服务（含实施团队和人员）要求
     */
    @Column
    protected String serviceRequirements;

    /**
     * 包装和运输要求
     */
    @Column
    protected String transportRequirements;

    /**
     * 交货（或提交成果）要求
     */
    protected String deliveryRequirements;

    /**
     * 售后服务
     */
    @Column
    protected String afterSalesService;

    /**
     * 付款方式
     */
    @Column
    protected String paymentMethod;

    /**
     * 违约条款
     */
    @Column
    protected String breachClause;

    /**
     * 是否含保证金
     */
    @Column(nullable = false)
    protected Boolean hasBond;

    /**
     * 投标保证金
     */
    @Column
    protected BigDecimal bidBond;

    /**
     * 履约保证金
     */
    @Column
    protected BigDecimal performanceBond;

    /**
     * 履约保证金年限
     */
    @Column
    protected Integer performanceYears;

    /**
     * 质保金
     */
    @Column
    protected BigDecimal warrantyBond;

    /**
     * 质保金年限
     */
    @Column
    protected Integer warrantyYears;

    /**
     * 其他补充
     */
    @Column
    protected String supplement;

    /**
     * 单据状态
     */
    @Column(nullable = false)
    protected FlowStatus status;

    /**
     * 通过时间
     */
    @Column
    protected LocalDateTime passTime;

    /**
     * 采购计划
     */
    @Column
    protected Integer purchasePlan;

    /**
     * 采购编号
     */
//    @Column()
//    private String purchaseNo;
    /**
     * 项目编号
     */
    @Column()
    private String projectNo;

    /**
     * 立项金额
     */
    @Column()
    protected BigDecimal approvalAmount;

    /**
     * 拟用采购方式原因与说明
     */
//    @Column()
//    protected String purDescription;
    /**
     * 采购方式原因
     */
    @Column(length = 1000)
    private String purModeReason;

    /**
     * 单一方式原因
     */
    @Column()
    @Enumerated
    protected TheSingleReason singleReason;

    /**
     * 拟用评审方式原因与说明
     */
//    @Column()
//    protected String auditDescription;
    /**
     * 评价办法说明
     */
    @Column(length = 1000)
    private String evalMethodReason;

    /**
     * 项目分类一
     */
//    @Column(nullable = false)
//    @Enumerated
//    protected ProjCateOne projCategoryOne;
    /**
     * 项目类别
     */
    @Column
    private ProjectCategory projectCategory;

    /**
     * 项目分类三
     */
    @Column()
    @Enumerated
    protected ProjCateThree projCategoryThree;

    /**
     * 是否需提供样板
     */
    @Column(nullable = false)
    protected Boolean needSample;

    /**
     * 是否来自需求受理
     */
    @Column(nullable = false)
    protected Boolean accept;

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

    public String getReqUserPhone() {
        return reqUserPhone;
    }

    public void setReqUserPhone(String reqUserPhone) {
        this.reqUserPhone = reqUserPhone;
    }

    public String getUseDepartmentIds() {
        return useDepartmentIds;
    }

    public void setUseDepartmentIds(String useDepartmentIds) {
        this.useDepartmentIds = useDepartmentIds;
    }

    public Integer getPurDepartmentId() {
        return purDepartmentId;
    }

    public void setPurDepartmentId(Integer purDepartmentId) {
        this.purDepartmentId = purDepartmentId;
    }

    public AcceptStatus getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(AcceptStatus acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSupRequirements() {
        return supRequirements;
    }

    public void setSupRequirements(String supRequirements) {
        this.supRequirements = supRequirements;
    }

    public String getSupSpecialRequirements() {
        return supSpecialRequirements;
    }

    public void setSupSpecialRequirements(String supSpecialRequirements) {
        this.supSpecialRequirements = supSpecialRequirements;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PurchaseType getPurType() {
        return purType;
    }

    public void setPurType(PurchaseType purType) {
        this.purType = purType;
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

    public EvalMethod getSugEvalMethod() {
        return sugEvalMethod;
    }

    public void setSugEvalMethod(EvalMethod sugEvalMethod) {
        this.sugEvalMethod = sugEvalMethod;
    }

    public ContractCategory getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(ContractCategory contractCategory) {
        this.contractCategory = contractCategory;
    }

    public Integer getContractCreatedUserId() {
        return contractCreatedUserId;
    }

    public void setContractCreatedUserId(Integer contractCreatedUserId) {
        this.contractCreatedUserId = contractCreatedUserId;
    }

    public PurchaseCategory getPurCategory() {
        return purCategory;
    }

    public void setPurCategory(PurchaseCategory purCategory) {
        this.purCategory = purCategory;
    }

    public PlanStatus getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(PlanStatus planStatus) {
        this.planStatus = planStatus;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberOfWinSup() {
        return numberOfWinSup;
    }

    public void setNumberOfWinSup(Integer numberOfWinSup) {
        this.numberOfWinSup = numberOfWinSup;
    }

    public String getPurContent() {
        return purContent;
    }

    public void setPurContent(String purContent) {
        this.purContent = purContent;
    }

    public String getSupplyLocation() {
        return supplyLocation;
    }

    public void setSupplyLocation(String supplyLocation) {
        this.supplyLocation = supplyLocation;
    }

    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(String contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getQualityRequirements() {
        return qualityRequirements;
    }

    public void setQualityRequirements(String qualityRequirements) {
        this.qualityRequirements = qualityRequirements;
    }

    public String getServiceRequirements() {
        return serviceRequirements;
    }

    public void setServiceRequirements(String serviceRequirements) {
        this.serviceRequirements = serviceRequirements;
    }

    public String getTransportRequirements() {
        return transportRequirements;
    }

    public void setTransportRequirements(String transportRequirements) {
        this.transportRequirements = transportRequirements;
    }

    public String getDeliveryRequirements() {
        return deliveryRequirements;
    }

    public void setDeliveryRequirements(String deliveryRequirements) {
        this.deliveryRequirements = deliveryRequirements;
    }

    public String getAfterSalesService() {
        return afterSalesService;
    }

    public void setAfterSalesService(String afterSalesService) {
        this.afterSalesService = afterSalesService;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBreachClause() {
        return breachClause;
    }

    public void setBreachClause(String breachClause) {
        this.breachClause = breachClause;
    }

    public Boolean getHasBond() {
        return hasBond;
    }

    public void setHasBond(Boolean hasBond) {
        this.hasBond = hasBond;
    }

    public BigDecimal getBidBond() {
        return bidBond;
    }

    public void setBidBond(BigDecimal bidBond) {
        this.bidBond = bidBond;
    }

    public BigDecimal getPerformanceBond() {
        return performanceBond;
    }

    public void setPerformanceBond(BigDecimal performanceBond) {
        this.performanceBond = performanceBond;
    }

    public Integer getPerformanceYears() {
        return performanceYears;
    }

    public void setPerformanceYears(Integer performanceYears) {
        this.performanceYears = performanceYears;
    }

    public BigDecimal getWarrantyBond() {
        return warrantyBond;
    }

    public void setWarrantyBond(BigDecimal warrantyBond) {
        this.warrantyBond = warrantyBond;
    }

    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    public void setWarrantyYears(Integer warrantyYears) {
        this.warrantyYears = warrantyYears;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
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

    public Integer getPurchasePlan() {
        return purchasePlan;
    }

    public void setPurchasePlan(Integer purchasePlan) {
        this.purchasePlan = purchasePlan;
    }

    public Integer getContractValidity() {
        return contractValidity;
    }

    public void setContractValidity(Integer contractValidity) {
        this.contractValidity = contractValidity;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
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

    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }

    public BigDecimal getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(BigDecimal approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public TheSingleReason getSingleReason() {
        return singleReason;
    }

    public void setSingleReason(TheSingleReason singleReason) {
        this.singleReason = singleReason;
    }

    public ProjCateThree getProjCategoryThree() {
        return projCategoryThree;
    }

    public void setProjCategoryThree(ProjCateThree projCategoryThree) {
        this.projCategoryThree = projCategoryThree;
    }

    public Boolean getNeedSample() {
        return needSample;
    }

    public void setNeedSample(Boolean needSample) {
        this.needSample = needSample;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}
