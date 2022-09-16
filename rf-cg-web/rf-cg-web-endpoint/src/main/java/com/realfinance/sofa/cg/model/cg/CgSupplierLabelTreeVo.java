package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class CgSupplierLabelTreeVo extends CgSupplierLabelVo {

    @JsonManagedReference
    @Schema(description = "下级标签")
    protected List<CgSupplierLabelTreeVo> children = new ArrayList<>();


    @Override
    @JsonBackReference
    public CgSupplierLabelVo getParent() {
        return super.getParent();
    }

    public List<CgSupplierLabelTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CgSupplierLabelTreeVo> children) {
        this.children = children;
    }
}
