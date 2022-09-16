package com.realfinance.sofa.sdebank.model;

import java.util.List;

//2.26 元素征信工商信息(深度) 比较后接口返回值
public class SupplierRelationshipDto {

    private List<ElementBusIInfoDto> list;

    //关联类型(1-5)
    private Integer type;

    private Double proportion;

    public List<ElementBusIInfoDto> getList() {
        return list;
    }

    public void setList(List<ElementBusIInfoDto> list) {
        this.list = list;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }
}
