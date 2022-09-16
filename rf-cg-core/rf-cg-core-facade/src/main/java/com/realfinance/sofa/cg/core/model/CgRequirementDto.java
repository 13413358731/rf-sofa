package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CgRequirementDto extends BaseDto {
    /**
     * 部门ID
     */
    private Integer departmentId;

    public CgRequirementDto(){
    }
    public CgRequirementDto(Integer id){
        this.id=id;
    }
    private Integer id;

    private String tenantId;

    /**
     * 采购申请名称
     */
    private String name;

    /**
     * 申请采购金额
     */
    private BigDecimal reqTotalAmount;

    /**
     * 市场参考价/市场控制总价（总）
     */
    private BigDecimal marketPrice;

    /**
     * 申请人联系电话
     */
    private String reqUserPhone;

    /**
     * 使用部门
     */
    private String useDepartmentIds;

    /**
     * 采购部门
     */
    private Integer purDepartmentId;

    /**
     * 受理状态
     */
    private String acceptStatus;

    /**
     * 处理意见
     */
    private String reason;

    /**
     * 经办人用户ID
     */
    private Integer operatorUserId;

    /**
     * 供应商资格要求
     */
    private String supRequirements;

    /**
     * 供应商特殊资格要求
     */
    private String supSpecialRequirements;

    /**
     * 备注
     */
    private String note;

    /**
     * 采购种类
     */
    private String purType;

    /**
     * 拟用采购方式
     */
    private String purMode;

    /**
     * 项目特性
     */
    private String projFeatures;

    /**
     * 推荐评分方法
     */
    private String sugEvalMethod;

    /**
     * 合同类别
     */
    private String contractCategory;

    /**
     * 合同制单人
     */
    private Integer contractCreatedUserId;

    /**
     * 合约有效期
     */
    private Integer contractValidity;

    /**
     * 采购类别
     */
    private String purCategory;

    /**
     * 是否计划内
     */
    private String planStatus;

   /* @OneToOne
    @JoinColumn(name = "plan_id")
    private PurchasePlan plan;*/

    /**
     * 数量
     */
    private Integer number;

    /**
     * 中标供应商数
     */
    private Integer numberOfWinSup;

    /**
     * 采购内容
     */
    private String purContent;

    /**
     * 供货（服务）地点
     */
    private String supplyLocation;

    /**
     * 交货（完成）时间
     */
    private String deliveryTime;

    /**
     * 合同期限
     */
    private String contractTerm;

    /**
     * 质量要求
     */
    private String qualityRequirements;

    /**
     * 服务（含实施团队和人员）要求
     */
    private String serviceRequirements;

    /**
     * 包装和运输要求
     */
    private String transportRequirements;
    /**
     * 交货（或提交成果）要求
     */
    private String deliveryRequirements;

    /**
     * 售后服务
     */
    private String afterSalesService;

    /**
     * 付款方式
     */
    private String paymentMethod;

    /**
     * 违约条款
     */
    private String breachClause;

    /**
     * 是否含有保证金
     */
    private Boolean hasBond;

    /**
     * 投标保证金
     */
    private BigDecimal bidBond;

    /**
     * 履约保证金
     */
    private BigDecimal performanceBond;

    /**
     * 履约保证金年限
     */
    private Integer performanceYears;

    /**
     * 质保金
     */
    private BigDecimal warrantyBond;

    /**
     * 质保金年限
     */
    private Integer warrantyYears;

    /**
     * 其他补充
     */
    private String supplement;

    /**
     * 单据状态
     */
    private String status;

    /**
     * 通过时间
     */
    private LocalDateTime passTime;

    /**
     * 采购方案
     */
    private Integer purchasePlan;

    /**
     * 立项金额
     */
    protected BigDecimal approvalAmount;

    /**
     * 单一方式原因
     */
    protected String singleReason;

    /**
     * 项目分类三
     */
    protected String projCategoryThree;

    /**
     * 是否需提供样板
     */
    protected Boolean needSample;

    /**
     * 是否来自需求受理
     */
    protected Boolean accept;

    /**
     * 项目编号
     */
    protected String projectNo;

    /**
     * 采购方式原因
     */
    protected String purModeReason;

    /**
     * 评价办法说明
     */
    protected String evalMethodReason;

    /**
     * 项目类别
     */
    protected String projectCategory;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
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

    public String getPurType() {
        return purType;
    }

    public void setPurType(String purType) {
        this.purType = purType;
    }

    public String getPurMode() {
        return purMode;
    }

    public void setPurMode(String purMode) {
        this.purMode = purMode;
    }

    public String getProjFeatures() {
        return projFeatures;
    }

    public void setProjFeatures(String projFeatures) {
        this.projFeatures = projFeatures;
    }

    public String getSugEvalMethod() {
        return sugEvalMethod;
    }

    public void setSugEvalMethod(String sugEvalMethod) {
        this.sugEvalMethod = sugEvalMethod;
    }

    public String getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(String contractCategory) {
        this.contractCategory = contractCategory;
    }

    public Integer getContractCreatedUserId() {
        return contractCreatedUserId;
    }

    public void setContractCreatedUserId(Integer contractCreatedUserId) {
        this.contractCreatedUserId = contractCreatedUserId;
    }

    public Integer getContractValidity() {
        return contractValidity;
    }

    public void setContractValidity(Integer contractValidity) {
        this.contractValidity = contractValidity;
    }

    public String getPurCategory() {
        return purCategory;
    }

    public void setPurCategory(String purCategory) {
        this.purCategory = purCategory;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public BigDecimal getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(BigDecimal approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public String getSingleReason() {
        return singleReason;
    }

    public void setSingleReason(String singleReason) {
        this.singleReason = singleReason;
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

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjCategoryThree() {
        return projCategoryThree;
    }

    public void setProjCategoryThree(String projCategoryThree) {
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
