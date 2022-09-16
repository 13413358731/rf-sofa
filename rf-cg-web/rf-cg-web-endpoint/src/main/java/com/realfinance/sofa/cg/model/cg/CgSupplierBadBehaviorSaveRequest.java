package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(description = "保存不良行为对象")
public class CgSupplierBadBehaviorSaveRequest {

    private Integer id;

    private ReferenceObject<Integer> supplierId;

    private LocalDateTime happenTime;

    private String behaviorDescription;

    private  String supplierName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferenceObject<Integer> getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(ReferenceObject<Integer> supplierId) {
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
