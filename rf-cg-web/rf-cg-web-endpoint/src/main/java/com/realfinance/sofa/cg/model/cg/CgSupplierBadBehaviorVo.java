package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;

import java.time.LocalDateTime;

public class CgSupplierBadBehaviorVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    private CgSupplierVo supplierId;

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

    public CgSupplierVo getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(CgSupplierVo supplierId) {
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
