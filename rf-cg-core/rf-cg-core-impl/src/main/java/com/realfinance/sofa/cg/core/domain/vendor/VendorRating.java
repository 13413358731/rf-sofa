package com.realfinance.sofa.cg.core.domain.vendor;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.ContractAttachment;
import com.realfinance.sofa.cg.core.domain.VendorAttachment;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 供应商评价
 */
@Entity
@Table(name = "CG_CORE_VendorRating")
public class VendorRating extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     *合同名称
     */
    @Column(nullable = false)
    private String contractName;

    /**
     *供应商名称
     */
    @Column(nullable = false)
    private String vendorName;

    /**
     *项目名称
     */
    @Column(nullable = false)
    private String projectName;

    /**
     * 是否过期状态 0 否 1 是
     */
    private Integer expireStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vendor_rating_id")
    private List<VendorAttachment> vendorAttachments;

    public List<VendorAttachment> getVendorAttachments() {
        return vendorAttachments;
    }

    public void setVendorAttachments(List<VendorAttachment> vendorAttachments) {
        this.vendorAttachments = vendorAttachments;
    }

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

    public Integer getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(Integer expireStatus) {
        this.expireStatus = expireStatus;
    }
}
