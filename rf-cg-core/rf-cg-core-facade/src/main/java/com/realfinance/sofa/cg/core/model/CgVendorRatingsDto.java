package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CgVendorRatingsDto extends BaseDto {

    private Integer id;

    /**
     * 法人ID
     */
    private String tenantId;

    /**
     *合同名称
     */
    private String contractName;

    /**
     *供应商名称
     */
    private String vendorName;

    /**
     *项目名称
     */
    private String projectName;

    /**
     *供应商服务满意度
     */
    private String vendorSatisfactory;

    /**
     *项目满意度
     */
    private String projectSatisfactory;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVendorSatisfactory() {
        return vendorSatisfactory;
    }

    public void setVendorSatisfactory(String vendorSatisfactory) {
        this.vendorSatisfactory = vendorSatisfactory;
    }

    public String getProjectSatisfactory() {
        return projectSatisfactory;
    }

    public void setProjectSatisfactory(String projectSatisfactory) {
        this.projectSatisfactory = projectSatisfactory;
    }
}
