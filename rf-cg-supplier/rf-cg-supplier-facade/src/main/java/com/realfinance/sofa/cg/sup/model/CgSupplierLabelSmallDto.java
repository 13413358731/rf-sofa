package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class CgSupplierLabelSmallDto implements ReferenceDto<Integer>, Serializable {

    @NotNull
    private Integer id;
    private String name;
    private String value;
    private CgSupplierLabelTypeSmallDto type;

    public CgSupplierLabelSmallDto() {
    }

    public CgSupplierLabelSmallDto(Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CgSupplierLabelSmallDto that = (CgSupplierLabelSmallDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CgSupplierLabelSmallDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
