package com.realfinance.sofa.cg.model.flow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PortalTaskVo {
    //平台
    @JsonProperty(value = "PLAT_CODE")
    private String platCode;
    //总的待办数量
    @JsonProperty(value = "TOTAL")
    private String total;
    //打开方式
    @JsonProperty(value = "OPENTYPE")
    private String openType;
    //待办列表
    @JsonProperty(value = "MORE_URL")
    private String moreUrl;
    //待办详细信息
    @JsonProperty(value = "DATA")
    private List<PortalTaskDataVo> data;

    public String getPlatCode() {
        return platCode;
    }

    public void setPlatCode(String platCode) {
        this.platCode = platCode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public List<PortalTaskDataVo> getData() {
        return data;
    }

    public void setData(List<PortalTaskDataVo> data) {
        this.data = data;
    }
}
