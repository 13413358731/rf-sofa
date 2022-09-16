package com.realfinance.sofa.common.filetoken;

import java.io.Serializable;

/**
 * 文件Token
 */
public class FileToken implements Serializable {
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 过期时间戳
     */
    private long expireTimestamp;

    public boolean isExpired() {
        return System.currentTimeMillis() > expireTimestamp;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(long expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    @Override
    public String toString() {
        return "FileToken{" +
                "fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", username='" + username + '\'' +
                ", expireTimestamp=" + expireTimestamp +
                '}';
    }
}


