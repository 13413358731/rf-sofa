package com.realfinance.sofa.common.notice.email.impl;

import com.realfinance.sofa.common.notice.email.Email;
import com.realfinance.sofa.common.notice.email.EmailAttachment;
import com.realfinance.sofa.common.notice.email.EmailException;
import com.realfinance.sofa.common.notice.email.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.UUID;

/**
 * 使用需要引入spring-boot-starter-mail
 */
public class SpringEmailSender implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(SpringEmailSender.class);

    private final JavaMailSenderImpl mailSender;
    private final String from;

    public SpringEmailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
        this.from = Objects.requireNonNull(mailSender.getUsername());
    }

    @Override
    public String send(Email email) throws EmailException {
        validate(email);
        doSend(email);
        return email.getId() == null ? UUID.randomUUID().toString() : email.getId();
    }

    protected void doSend(Email email) throws EmailException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(from);
            helper.setTo(email.getDestAddress().toArray(String[]::new));
            if (email.getBccAddress() != null && !email.getBccAddress().isEmpty()) {
                helper.setBcc(email.getBccAddress().toArray(String[]::new));
            }
            if (email.getCcAddress() != null && !email.getCcAddress().isEmpty()) {
                helper.setCc(email.getCcAddress().toArray(String[]::new));
            }
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);
            if (email.getAttachments() != null && !email.getAttachments().isEmpty()) {
                for (EmailAttachment attachment : email.getAttachments()) {
                    helper.addAttachment(attachment.getName(), new ByteArrayResource(attachment.getContent()));
                }
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送失败",e);
            log.error("邮件发送失败，ID：{}，目标地址：{}，主题：{}，内容：{}",
                    email.getId(),
                    String.join("、",email.getDestAddress()),
                    email.getSubject(),
                    email.getBody());
            throw new EmailException("邮件发送失败");
        }
    }
}
