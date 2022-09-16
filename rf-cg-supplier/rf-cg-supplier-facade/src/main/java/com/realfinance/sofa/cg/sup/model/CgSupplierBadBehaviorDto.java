package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDateTime;

public class CgSupplierBadBehaviorDto extends BaseDto {

    private Integer id;

    private Integer supplierId;

    /**
     * 发生时期
     */
    private LocalDateTime happenTime;

    /**
     * 不良行为具体描述
     */

    private String behaviorDescription;

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
}
