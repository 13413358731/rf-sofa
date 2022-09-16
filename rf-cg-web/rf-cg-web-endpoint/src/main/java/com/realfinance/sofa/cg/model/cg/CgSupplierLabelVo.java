package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "供应商标签对象")
public class CgSupplierLabelVo extends BaseVo implements IdentityObject<Integer> {

    public interface Save { }

    @Schema(description = "ID")
    protected Integer id;
    @Schema(description = "上级标签")
    protected CgSupplierLabelVo parent;
    @NotBlank(groups = {Save.class})
    @Schema(description = "标签名称")
    protected String name;
    @NotNull(groups = {Save.class})
    @Schema(description = "标签值")
    protected String value;
    @NotNull(groups = {Save.class})
    @Schema(description = "标签类型")
    protected CgSupplierLabelTypeVo type;
    @Schema(description = "子叶数")
    protected Integer leafCount;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierLabelVo getParent() {
        return parent;
    }

    public void setParent(CgSupplierLabelVo parent) {
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

    public CgSupplierLabelTypeVo getType() {
        return type;
    }

    public void setType(CgSupplierLabelTypeVo type) {
        this.type = type;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }
}
