package com.realfinance.sofa.system.model;

import java.util.ArrayList;
import java.util.List;

public class MenuSmallTreeDto extends MenuSmallDto {

    private List<MenuSmallTreeDto> children = new ArrayList<>();

    public List<MenuSmallTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuSmallTreeDto> children) {
        this.children = children;
    }
}
