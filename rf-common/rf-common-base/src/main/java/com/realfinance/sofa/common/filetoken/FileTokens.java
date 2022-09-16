package com.realfinance.sofa.common.filetoken;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 文件TOKEN工具类
 * 需要引入hutool-crypto
 */
public final class FileTokens {

    /**
     * 防止token被篡改
     */
    private static final HMac H_MAC;

    static {
        H_MAC = SecureUtil.hmacMd5("MD5Hash算法的特点：1：输入任意长度的信息，经过摘要处理，输出为128位的信息（数字指纹）。2：不同输入产生不同的结果（唯一性）。3：根据128位的输出结果不可能反推出输入的信息(不可逆)");
    }

    /**
     * 默认有效时间1天
     */
    private static final long DEFAULT_EXPIRE = TimeUnit.DAYS.toMillis(1);

    private FileTokens() {

    }

    public static FileToken create(String fileId, String fileName) {
        return create(fileId,fileName,null, DEFAULT_EXPIRE);
    }

    public static FileToken create(String fileId, String fileName, String username) {
        return create(fileId,fileName,username, DEFAULT_EXPIRE);
    }

    public static FileToken create(String fileId, String fileName, String username, long expire) {
        Objects.requireNonNull(fileId);
        Objects.requireNonNull(fileName);
        if (expire < 60000) { // 有效时间小于1分钟的改为默认时间
            expire = DEFAULT_EXPIRE;
        }
        FileToken fileToken = new FileToken();
        fileToken.setFileId(fileId);
        fileToken.setFileName(fileName);
        fileToken.setUsername(username);
        fileToken.setExpireTimestamp(System.currentTimeMillis() + expire);
        return fileToken;
    }

    public static String encode(String fileId, String fileName) {
        FileToken fileToken = create(fileId, fileName);
        return encode(fileToken);
    }

    public static String encode(String fileId, String fileName, String username) {
        FileToken fileToken = create(fileId, fileName, username);
        return encode(fileToken);
    }

    public static String encode(String fileId, String fileName, String username, long expire) {
        FileToken fileToken = create(fileId, fileName, username, expire);
        return encode(fileToken);
    }

    /**
     * 格式：文件名.用户名|过期时间戳|文件ID.签名
     * @param fileToken
     * @return
     */
    public static String encode(FileToken fileToken) {
        Objects.requireNonNull(fileToken);
        String fileId = Objects.requireNonNull(fileToken.getFileId());
        String fileName = Objects.requireNonNull(fileToken.getFileName());
        String username = fileToken.getUsername() == null ? "" : fileToken.getUsername();
        String expireTimestamp = String.valueOf(fileToken.getExpireTimestamp());
        String data = username + "|" + expireTimestamp + "|" + fileId;
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] sign = H_MAC.digest(bytes);
        Base64.Encoder urlEncoder = Base64.getUrlEncoder();
        return urlEncoder.encodeToString(fileName.getBytes(StandardCharsets.UTF_8)) +
                "." +
                urlEncoder.encodeToString(bytes) +
                "." +
                urlEncoder.encodeToString(sign);
    }

    public static FileToken decode(String token) {
        Objects.requireNonNull(token);
        String[] split = token.split("\\.");
        if (split.length != 3) {
            throw new IllegalArgumentException("Token格式错误");
        }
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] bytes = urlDecoder.decode(split[1]);
        byte[] sign = urlDecoder.decode(split[2]);
        if (!Arrays.equals(H_MAC.digest(bytes), sign)) {
            throw new IllegalArgumentException("Token签名校验失败");
        }
        String fileName = new String(urlDecoder.decode(split[0]), StandardCharsets.UTF_8);
        String data = new String(bytes, StandardCharsets.UTF_8);
        int i1 = data.indexOf("|");
        String username = data.substring(0, i1);
        int i2 = data.indexOf("|",i1 + 1);
        long expireTimestamp = Long.parseLong(data.substring(i1 + 1, i2));
        String fileId = data.substring(i2 + 1);
        FileToken fileToken = new FileToken();
        fileToken.setFileId(fileId);
        fileToken.setFileName(fileName);
        fileToken.setUsername(username);
        fileToken.setExpireTimestamp(expireTimestamp);
        return fileToken;
    }
}
