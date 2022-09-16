package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class CgPurchaseCatalogTreeVo extends CgPurchaseCatalogVo {

    @JsonManagedReference
    @Schema(description = "下级")
    protected List<CgPurchaseCatalogTreeVo> children = new ArrayList<>();

    // 前端保存时用
    public Integer getParentId() {
        CgPurchaseCatalogVo parent = getParent();
        if (parent == null) {
            return null;
        }
        return parent.getId();
    }

    @Override
    @JsonBackReference
    public CgPurchaseCatalogVo getParent() {
        return super.getParent();
    }

    public List<CgPurchaseCatalogTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CgPurchaseCatalogTreeVo> children) {
        this.children = children;
    }
}
