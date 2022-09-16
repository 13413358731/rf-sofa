package com.realfinance.sofa.common.notice.email;

import org.springframework.util.StringUtils;

import java.util.Objects;

public interface EmailSender {

    /**
     * 发送邮件，返回唯一ID
     * @param email
     * @return
     * @throws EmailException
     */
    String send(Email email) throws EmailException;

    default void validate(Email email) throws EmailException {
        Objects.requireNonNull(email);
        if (!StringUtils.hasText(email.getTenantId())) {
            throw new EmailException("缺少TenantId");
        }
        if (email.getDestAddress() == null || email.getDestAddress().isEmpty()) {
            throw new EmailException("缺少接收地址");
        }
        if (!StringUtils.hasText(email.getSubject())) {
            throw new EmailException("主题不能为空白");
        }
        if (!StringUtils.hasText(email.getBody())) {
            throw new EmailException("内容不能为空白");
        }
    }
}
