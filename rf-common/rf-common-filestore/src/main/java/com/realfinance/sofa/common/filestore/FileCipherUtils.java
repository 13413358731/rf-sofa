package com.realfinance.sofa.common.filestore;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public final class FileCipherUtils {
    private FileCipherUtils() {
    }

    public static final String ALGORITHM = "DESede";

    public static final byte[] KEY = Base64.getDecoder().decode("V8EE8e8c1TcHq5G14C8QFpFuDav05V1X");

    public static CipherInputStream encrypt(InputStream in) {
        try {
            SecretKey deskey = new SecretKeySpec(FileCipherUtils.KEY, FileCipherUtils.ALGORITHM);
            Cipher cipher = Cipher.getInstance(FileCipherUtils.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            CipherInputStream cipherInputStream = new CipherInputStream(in, cipher);
            return cipherInputStream;
        } catch (Exception e) {
            throw new RuntimeException("加密文件流失败");
        }
    }

    public static CipherOutputStream decrypt(OutputStream out) {
        try {
            SecretKey deskey = new SecretKeySpec(FileCipherUtils.KEY, FileCipherUtils.ALGORITHM);
            Cipher cipher = Cipher.getInstance(FileCipherUtils.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(out, cipher);
            return cipherOutputStream;
        } catch (Exception e) {
            throw new RuntimeException("解密文件流失败");
        }
    }

    public static byte[] encrypt(byte[] bytes) {
        try {
            SecretKey deskey = new SecretKeySpec(FileCipherUtils.KEY, FileCipherUtils.ALGORITHM);
            Cipher cipher = Cipher.getInstance(FileCipherUtils.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }
    }
    public static byte[] decrypt(byte[] bytes) {
        try {
            SecretKey deskey = new SecretKeySpec(FileCipherUtils.KEY, FileCipherUtils.ALGORITHM);
            Cipher cipher = Cipher.getInstance(FileCipherUtils.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException("解密失败");
        }
    }
}
