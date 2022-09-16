package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;

/**
 * 手机验证码
 */
public class AuthCode implements Serializable {

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String code;
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 错误次数
     */
    private int errorCount;

    /**
     * 错误次数+1
     */
    public AuthCode incrErrorCount() {
        this.errorCount += 1;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    @Override
    public String toString() {
        return "AuthCode{" +
                "mobile='" + mobile + '\'' +
                ", code='" + code + '\'' +
                ", timestamp=" + timestamp +
                ", errorCount=" + errorCount +
                '}';
    }
}
