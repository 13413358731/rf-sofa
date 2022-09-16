package com.realfinance.sofa.cg.core.model;

import java.io.Serializable;

public class CgExpertLabelDto extends BaseDto implements Serializable {

    private Integer id;
    private CgExpertLabelSmallDto parent;
    private String name;
    private String value;
    private CgExpertLabelTypeSmallDto type;
    private Integer leafCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgExpertLabelSmallDto getParent() {
        return parent;
    }

    public void setParent(CgExpertLabelSmallDto parent) {
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

    public CgExpertLabelTypeSmallDto getType() {
        return type;
    }

    public void setType(CgExpertLabelTypeSmallDto type) {
        this.type = type;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }
}
