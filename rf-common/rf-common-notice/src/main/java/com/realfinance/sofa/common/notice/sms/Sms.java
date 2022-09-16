package com.realfinance.sofa.common.notice.sms;

import java.io.Serializable;
import java.util.Collection;

/**
 * 短信对象
 */
public class Sms implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 目标地址
     */
    private Collection<String> destAddress;

    /**
     * 内容
     */
    private String content;

    private String tenantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<String> getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(Collection<String> destAddress) {
        this.destAddress = destAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


}
