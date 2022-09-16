package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CgSupplierBadBehaviorSaveDto {

    private Integer id;

    @NotNull
    private Integer supplierId;

    @NotNull
    private LocalDateTime happenTime;

    @NotBlank
    private String behaviorDescription;
    @NotBlank
    private  String supplierName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(LocalDateTime happenTime) {
        this.happenTime = happenTime;
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
