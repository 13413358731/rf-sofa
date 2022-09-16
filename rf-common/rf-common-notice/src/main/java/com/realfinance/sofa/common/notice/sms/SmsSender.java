package com.realfinance.sofa.common.notice.sms;

import java.util.Objects;

/**
 * 短信发送接口
 */
public interface SmsSender {

    /**
     * 发送短信
     * @param sms
     * @return
     * @throws SmsException
     */
    String send(Sms sms) throws SmsException;

    /**
     * 校验参数
     * @param sms
     */
    default void validate(Sms sms) throws SmsException {
        Objects.requireNonNull(sms);
        if (sms.getTenantId() == null || sms.getTenantId().isBlank()) {
            throw new SmsException("缺少法人ID");
        }
        if (sms.getDestAddress() == null || sms.getDestAddress().isEmpty()) {
            throw new SmsException("缺少接收地址");
        }
        if (sms.getContent() == null || sms.getContent().isBlank()) {
            throw new SmsException("短信内容不能为空白");
        }
    }
}
