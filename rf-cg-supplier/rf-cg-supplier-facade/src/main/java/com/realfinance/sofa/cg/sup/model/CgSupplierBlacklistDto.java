package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierBlacklistDto extends BaseDto {

    private Integer id;

    /**
     * 供应商
     */
    private CgSupplierSmallDto supplier;

    /**
     * 标题
     */
    private String title;

    /**
     * 黑名单原因
     */
    private String reason;

    /**
     * 处理状态
     */
    private String status;

    /**
     * 生效
     */
    private Boolean valid;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierSmallDto getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierSmallDto supplier) {
        this.supplier = supplier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

}
