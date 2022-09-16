package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class CgExpertLabelTreeVo extends CgExpertLabelVo {

    @JsonManagedReference
    @Schema(description = "下级标签")
    protected List<CgExpertLabelTreeVo> children = new ArrayList<>();


    @Override
    @JsonBackReference
    public CgExpertLabelVo getParent() {
        return super.getParent();
    }

    public List<CgExpertLabelTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CgExpertLabelTreeVo> children) {
        this.children = children;
    }
}
