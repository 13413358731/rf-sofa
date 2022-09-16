package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotBlank;

public class CgSupplierLabelTypeSaveDto {

    private Integer id;

    @NotBlank
    private String name;

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
}
