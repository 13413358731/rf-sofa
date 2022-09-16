package com.realfinance.sofa.cg.model.cg;


import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;


import java.math.BigDecimal;
import java.time.LocalDate;

public class CgPurchasePlanVo extends BaseVo implements IdentityObject<Integer> {


   private  AnnualPlanVo annualPlan;

    private Integer id;

    /**
     * 编号
     */
    private String  number;

    /**
     * 项目名称
     */
    private  String projectName;

    /**
     *采购内容说明
     */
    private  String contentDescription;

    /**
     *采购估算金额
     */
    private BigDecimal estimatedAmount;

    /**
     *项目分类
     */
    private  String  projectClassification;

    /**
     *本年计划列支额
     */
    private  BigDecimal  plannedExpenditure;


    /**
     *计划采购年限
     */
    private String  plannedProcurementPeriod;


    /**
     *采购类别
     */
    private  String  purchaseCategory;


    /**
     *拟签合同模式
     */
    private  String contractMode;

    /**
     *拟选供应商数
     */
    private  Integer  supplierNumber;

    /**
     *是否属长期延续项目
     */
    private  Boolean isContinue;


    /**
     *计划提交采购申请日
     */
    private LocalDate purchaseApplicationDate;

    /**
     *项目计划实施日期
     */
    private  LocalDate  ImplementationDate;


    /**
     *统筹部门
     */
    private  String  coOrdinationDepartment;


    /**
     *统筹部门联系人
     */
    private   String   coOrdinationDepartmentContacts;

    /**
     *需求部门
     */
    private  String  demandDepartment;

    /**
     *需求部门联系人
     */
    private   String demandDepartmentContacts;

    /**
     *采购方式
     */
    private  String procurementMethod;

    /**
     *备注
     */
    private  String remarks;

    /**
     *上次合同到期日
     */
    private LocalDate dueDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getProjectClassification() {
        return projectClassification;
    }

    public void setProjectClassification(String projectClassification) {
        this.projectClassification = projectClassification;
    }

    public BigDecimal getPlannedExpenditure() {
        return plannedExpenditure;
    }

    public void setPlannedExpenditure(BigDecimal plannedExpenditure) {
        this.plannedExpenditure = plannedExpenditure;
    }

    public String getPlannedProcurementPeriod() {
        return plannedProcurementPeriod;
    }

    public void setPlannedProcurementPeriod(String plannedProcurementPeriod) {
        this.plannedProcurementPeriod = plannedProcurementPeriod;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public String getContractMode() {
        return contractMode;
    }

    public void setContractMode(String contractMode) {
        this.contractMode = contractMode;
    }

    public Integer getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(Integer supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public Boolean getContinue() {
        return isContinue;
    }

    public void setContinue(Boolean aContinue) {
        isContinue = aContinue;
    }

    public LocalDate getPurchaseApplicationDate() {
        return purchaseApplicationDate;
    }

    public void setPurchaseApplicationDate(LocalDate purchaseApplicationDate) {
        this.purchaseApplicationDate = purchaseApplicationDate;
    }

    public LocalDate getImplementationDate() {
        return ImplementationDate;
    }

    public void setImplementationDate(LocalDate implementationDate) {
        ImplementationDate = implementationDate;
    }

    public String getCoOrdinationDepartment() {
        return coOrdinationDepartment;
    }

    public void setCoOrdinationDepartment(String coOrdinationDepartment) {
        this.coOrdinationDepartment = coOrdinationDepartment;
    }

    public String getCoOrdinationDepartmentContacts() {
        return coOrdinationDepartmentContacts;
    }

    public void setCoOrdinationDepartmentContacts(String coOrdinationDepartmentContacts) {
        this.coOrdinationDepartmentContacts = coOrdinationDepartmentContacts;
    }

    public String getDemandDepartment() {
        return demandDepartment;
    }

    public void setDemandDepartment(String demandDepartment) {
        this.demandDepartment = demandDepartment;
    }

    public String getDemandDepartmentContacts() {
        return demandDepartmentContacts;
    }

    public void setDemandDepartmentContacts(String demandDepartmentContacts) {
        this.demandDepartmentContacts = demandDepartmentContacts;
    }

    public String getProcurementMethod() {
        return procurementMethod;
    }

    public void setProcurementMethod(String procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public AnnualPlanVo getAnnualPlan() {
        return annualPlan;
    }

    public void setAnnualPlan(AnnualPlanVo annualPlan) {
        this.annualPlan = annualPlan;
    }
}
