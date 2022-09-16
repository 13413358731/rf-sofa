package com.realfinance.sofa.sdebank.response;

import java.util.List;

//2.26 元素征信工商信息(深度)(返回值)
public class ElementBusIInfoResponse {


    //统一信用代码
    private String elementbusiinfo_creditcode;

    //法定代表人/负责人/执行事务合伙人
    private String elementbusiinfo_frname;

    //股东类型
    private List<String> elementbusiinfo_shareholders_0_invtype;

    //股东名称
    private List<String> elementbusiinfo_shareholders_0_shaname;

    //出资比例
    private List<Double> elementbusiinfo_shareholders_0_fundedratio;

    public String getElementbusiinfo_frname() {
        return elementbusiinfo_frname;
    }

    public void setElementbusiinfo_frname(String elementbusiinfo_frname) {
        this.elementbusiinfo_frname = elementbusiinfo_frname;
    }

    public List<String> getElementbusiinfo_shareholders_0_invtype() {
        return elementbusiinfo_shareholders_0_invtype;
    }

    public void setElementbusiinfo_shareholders_0_invtype(List<String> elementbusiinfo_shareholders_0_invtype) {
        this.elementbusiinfo_shareholders_0_invtype = elementbusiinfo_shareholders_0_invtype;
    }

    public List<String> getElementbusiinfo_shareholders_0_shaname() {
        return elementbusiinfo_shareholders_0_shaname;
    }

    public void setElementbusiinfo_shareholders_0_shaname(List<String> elementbusiinfo_shareholders_0_shaname) {
        this.elementbusiinfo_shareholders_0_shaname = elementbusiinfo_shareholders_0_shaname;
    }

    public List<Double> getElementbusiinfo_shareholders_0_fundedratio() {
        return elementbusiinfo_shareholders_0_fundedratio;
    }

    public void setElementbusiinfo_shareholders_0_fundedratio(List<Double> elementbusiinfo_shareholders_0_fundedratio) {
        this.elementbusiinfo_shareholders_0_fundedratio = elementbusiinfo_shareholders_0_fundedratio;
    }

    public String getElementbusiinfo_creditcode() {
        return elementbusiinfo_creditcode;
    }

    public void setElementbusiinfo_creditcode(String elementbusiinfo_creditcode) {
        this.elementbusiinfo_creditcode = elementbusiinfo_creditcode;
    }
}
