package com.realfinance.sofa.sdebank;

import com.realfinance.sofa.common.notice.email.impl.SpringEmailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SdebankEmailSender extends SpringEmailSender {

    public SdebankEmailSender() {
        super(createJavaMailSenderImpl());
    }

    public SdebankEmailSender(JavaMailSenderImpl mailSender) {
        super(mailSender);
    }

    private static JavaMailSenderImpl createJavaMailSenderImpl() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("172.16.84.121");
        sender.setPort(25);
        sender.setUsername("srcps@sdebank.com");
        sender.setPassword("abcd1234");
        sender.setProtocol("smtp");
        return sender;
    }
}
