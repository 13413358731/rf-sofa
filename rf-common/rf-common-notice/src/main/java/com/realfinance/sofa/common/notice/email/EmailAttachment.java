package com.realfinance.sofa.common.notice.email;

import java.io.Serializable;

public class EmailAttachment implements Serializable {
    /**
     * 附件名称
     */
    private String name;

    /**
     * 内容
     */
    private byte[] content;

    /**
     * 是否内联
     */
    private boolean inner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public boolean isInner() {
        return inner;
    }

    public void setInner(boolean inner) {
        this.inner = inner;
    }
}
