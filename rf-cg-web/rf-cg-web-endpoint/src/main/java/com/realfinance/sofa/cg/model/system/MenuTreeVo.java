package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeVo extends MenuVo {

    @JsonManagedReference
    @Schema(description = "下级菜单")
    protected List<MenuTreeVo> children = new ArrayList<>();

    @Override
    @JsonBackReference
    public MenuVo getParent() {
        return super.getParent();
    }

    public List<MenuTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeVo> children) {
        this.children = children;
    }
}
