package com.realfinance.sofa.common.notice.email.impl;

import com.realfinance.sofa.common.notice.email.Email;
import com.realfinance.sofa.common.notice.email.EmailException;
import com.realfinance.sofa.common.notice.email.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * 打印到控制台实现
 */
public class ConsoleEmailSender implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(ConsoleEmailSender.class);

    @Override
    public String send(Email email) throws EmailException {
        validate(email);
        String msg = String.format("邮件发送失败，ID：%s，目标地址：%s，主题：%s，内容：%s",
                email.getId(),
                String.join("、",email.getDestAddress()),
                email.getSubject(),
                email.getBody());
        if (log.isInfoEnabled()) {
            log.info(msg);
        } else {
            System.out.println(msg);
        }
        return email.getId() == null ? UUID.randomUUID().toString() : email.getId();
    }
}
