package com.realfinance.sofa.common.notice.sms.impl;

import com.realfinance.sofa.common.notice.sms.Sms;
import com.realfinance.sofa.common.notice.sms.SmsException;
import com.realfinance.sofa.common.notice.sms.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * 打印到控制台实现
 */
public class ConsoleSmsSender implements SmsSender {

    private static final Logger log = LoggerFactory.getLogger(ConsoleSmsSender.class);

    @Override
    public String send(Sms sms) throws SmsException {
        validate(sms);
        String msg = "发送内容【" +
                sms.getContent() +
                "】到" +
                String.join("、", sms.getDestAddress()) +
                "的手机上";
        if (log.isInfoEnabled()) {
            log.info(msg);
        } else {
            System.out.println(msg);
        }
        return sms.getId() == null ? UUID.randomUUID().toString() : sms.getId();
    }
}
