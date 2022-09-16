package com.realfinance.sofa.system.model;

import java.util.ArrayList;
import java.util.List;

public class DepartmentSmallTreeDto extends DepartmentSmallDto {

    private List<DepartmentSmallTreeDto> children = new ArrayList<>();

    public List<DepartmentSmallTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentSmallTreeDto> children) {
        this.children = children;
    }
}
