package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "供应商对象")
public class CgVendorRatingsVo extends BaseVo implements FlowableVo,IdentityObject<Integer> {

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

    private String status;

    protected FlowInfoVo flowInfo;

    /**
     *供应商服务满意度
     */
    private String vendorSatisfactory;

    /**
     *项目满意度
     */
    private String projectSatisfactory;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
