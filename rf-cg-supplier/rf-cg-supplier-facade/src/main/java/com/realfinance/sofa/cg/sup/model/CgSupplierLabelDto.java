package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;

public class CgSupplierLabelDto extends BaseDto implements Serializable {

    private Integer id;
    private CgSupplierLabelSmallDto parent;
    private String name;
    private String value;
    private CgSupplierLabelTypeSmallDto type;
    private Integer leafCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierLabelSmallDto getParent() {
        return parent;
    }

    public void setParent(CgSupplierLabelSmallDto parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CgSupplierLabelTypeSmallDto getType() {
        return type;
    }

    public void setType(CgSupplierLabelTypeSmallDto type) {
        this.type = type;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }

}
