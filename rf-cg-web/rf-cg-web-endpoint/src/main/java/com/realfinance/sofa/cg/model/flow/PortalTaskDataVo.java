package com.realfinance.sofa.cg.model.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PortalTaskDataVo {
    //平台
    @JsonProperty(value = "APP_CODE")
    private String appCode;
    //待办标题
    @JsonProperty(value = "TITLE")
    private String title;
    //待办URL
    @JsonProperty(value = "URL")
    private String url;
    //紧急程度
    @JsonProperty(value = "URGENT_LEV")
    private String urgentLev;
    //待办日期
    @JsonProperty(value = "DATE")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date date;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrgentLev() {
        return urgentLev;
    }

    public void setUrgentLev(String urgentLev) {
        this.urgentLev = urgentLev;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
