package com.realfinance.sofa.cg.sup.model;


public class SupplierContactsDto {

    private Integer id;


    private String name;


    private String mobile;


    private String email;


    private String fax;


    private Boolean isPrimary;


    private String businessArea;


    private String note;


    private CgSupplierSmallDto supplier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CgSupplierSmallDto getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierSmallDto supplier) {
        this.supplier = supplier;
    }
}
