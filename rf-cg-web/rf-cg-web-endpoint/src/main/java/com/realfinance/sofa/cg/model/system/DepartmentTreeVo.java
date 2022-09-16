package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "部门对象")
public class DepartmentTreeVo extends DepartmentVo {

    @JsonManagedReference
    @Schema(description = "下级部门")
    protected List<DepartmentVo> children = new ArrayList<>();

    @Override
    @JsonBackReference
    public DepartmentVo getParent() {
        return super.getParent();
    }

    public List<DepartmentVo> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentVo> children) {
        this.children = children;
    }
}
