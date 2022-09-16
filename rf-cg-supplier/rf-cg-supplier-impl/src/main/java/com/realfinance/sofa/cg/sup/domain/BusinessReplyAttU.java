package com.realfinance.sofa.cg.sup.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 应答-上传采购文件
 */
@Entity
@Table(name = "CG_SUP_BIZ_REPLY_ATT_U")
public class BusinessReplyAttU extends Attachment {

    @ManyToOne
    @JoinColumn(name = "biz_reply_id", updatable = false)
    private BusinessReply bizReply;

    public BusinessReply getBizReply() {
        return bizReply;
    }

    public void setBizReply(BusinessReply bizReply) {
        this.bizReply = bizReply;
    }
}
