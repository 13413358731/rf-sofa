package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CgProjectDto extends BaseDto {

    /**
     * 部门ID
     */
    private Integer departmentId;

    private Integer id;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 申请采购总金额
     */
    private BigDecimal reqTotalAmount;

    /**
     * 市场参考价/市场控制总价（总）
     */
    private BigDecimal marketPrice;

    /**
     * 拟成交供应商数
     */
    private Integer numberOfWinSup;

    /**
     * 采购实施工时
     */
    private String purDuration;

    /**
     * 选取的采购方式
     */
    private String purMode;

    /**
     * 项目特性
     */
    private String projFeatures;

    /**
     * 选取的评标方法
     */
    private String evalMethod;

    /**
     * 项目申请部门
     */
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
    private Integer supLabelId;

    /**
     * 采购种类
     */
    private String purType;

    /**
     * 合同类别
     */
    private String contractCategory;

    /**
     * 采购类别
     */
    private String purCategory;

    /**
     * 采购需求
     */
    private Integer purchasePlan;

    /**
     * 全新/以往采购
     */
    private Boolean newPurchase;

    /**
     * 项目类别
     */
    private String projectCategory;

    /**
     * 采购用途
     */
    private String purpose;

    /**
     * 适用范围
     */
    private String applicableScope;

    /**
     * 采购需求
     */
    private String purReq;

    /**
     * 我行历史采购情况
     */
    private String purHistory;

    /**
     * 供应商资质要求
     */
    private String supQualReq;

    /**
     * 采购小组组成
     */
    private String purGroup;

    /**
     * 采购方式原因
     */
    private String purModeReason;

    /**
     * 评价办法说明
     */
    private String evalMethodReason;

    /**
     * 市场及供应商情况
     */
    private String marketSupProfile;

    /**
     * 有关情况说明
     */
    private String note;

    /**
     * 是否退回到申请
     */
    private Boolean returnReq;

    private String status;

    private LocalDateTime passTime;

    private CgRequirementDto requirement;

    protected LocalDateTime reqCreatedTime;

    /**
     * 立项金额
     */
    protected BigDecimal approvalAmount;

    /**
     * 合约有效期
     */
    protected Integer contractValidity;

    /**
     * 单一方式原因
     */
    protected String singleReason;

    /**
     * 供应商资格要求
     */
    protected String supRequirements;


    /**
     * 合同制单人
     */
    protected Integer contractCreatedUserId;

    /**
     * 使用部门
     */
    protected String useDepartmentIds;

    public CgProjectDto() {
    }

    public CgProjectDto(Integer id) {
        this.id = id;
    }

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

    public String getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(String evalMethod) {
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

    public String getPurType() {
        return purType;
    }

    public void setPurType(String purType) {
        this.purType = purType;
    }

    public String getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(String contractCategory) {
        this.contractCategory = contractCategory;
    }

    public String getPurCategory() {
        return purCategory;
    }

    public void setPurCategory(String purCategory) {
        this.purCategory = purCategory;
    }

    public Boolean getNewPurchase() {
        return newPurchase;
    }

    public void setNewPurchase(Boolean newPurchase) {
        this.newPurchase = newPurchase;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
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

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
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

    public CgRequirementDto getRequirement() {
        return requirement;
    }

    public void setRequirement(CgRequirementDto requirement) {
        this.requirement = requirement;
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

    public String getSingleReason() {
        return singleReason;
    }

    public void setSingleReason(String singleReason) {
        this.singleReason = singleReason;
    }

    public String getSupRequirements() {
        return supRequirements;
    }

    public void setSupRequirements(String supRequirements) {
        this.supRequirements = supRequirements;
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
}
