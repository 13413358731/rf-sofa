package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotNull;

public class CgSupplierAccountSmallDto implements ReferenceDto<Integer> {

    @NotNull
    private Integer id;

    private String username;

    public CgSupplierAccountSmallDto() {
    }

    public CgSupplierAccountSmallDto(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
