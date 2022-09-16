package com.realfinance.sofa.flow.model;

import java.io.Serializable;

public class BizDto extends BaseDto implements Serializable {

    private Integer id;

    /**
     * 业务编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String note;

    /**
     * 跳转url
     */
    private String url;

    /**
     * 回调类型 RPC 或 HTTP
     */
    private String callbackType;

    /**
     * 业务回调URL
     * 使用Http回调时使用
     * 通知业务系统流程状态
     */
    private String callbackUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    public String toString() {
        return "BizDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", url='" + url + '\'' +
                ", callbackType='" + callbackType + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", createdUserId=" + createdUserId +
                ", createdTime=" + createdTime +
                ", modifiedUserId=" + modifiedUserId +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
