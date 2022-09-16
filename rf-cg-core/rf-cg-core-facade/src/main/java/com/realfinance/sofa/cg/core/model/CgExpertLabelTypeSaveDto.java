package com.realfinance.sofa.cg.core.model;

import javax.validation.constraints.NotBlank;

public class CgExpertLabelTypeSaveDto {

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
