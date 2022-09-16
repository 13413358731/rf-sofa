package com.realfinance.sofa.common.notice.email;

import java.io.Serializable;
import java.util.Collection;

public class Email implements Serializable {

    private String tenantId;

    private String id;
    /**
     * 收件人
     */
    private Collection<String> destAddress;
    /**
     * 抄送人
     */
    private Collection<String> ccAddress;

    /**
     * 暗送人
     */
    private Collection<String> bccAddress;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String body;

    /**
     * 附件
     */
    private Collection<EmailAttachment> attachments;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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

    public Collection<String> getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(Collection<String> ccAddress) {
        this.ccAddress = ccAddress;
    }

    public Collection<String> getBccAddress() {
        return bccAddress;
    }

    public void setBccAddress(Collection<String> bccAddress) {
        this.bccAddress = bccAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Collection<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<EmailAttachment> attachments) {
        this.attachments = attachments;
    }
}
