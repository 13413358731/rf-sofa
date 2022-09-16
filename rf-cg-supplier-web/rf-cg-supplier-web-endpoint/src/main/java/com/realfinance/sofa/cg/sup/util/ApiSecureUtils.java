package com.realfinance.sofa.cg.sup.util;

import com.realfinance.sofa.common.util.SpringContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 接口安全工具类
 */
@Component
@Lazy(false)
public class ApiSecureUtils {

    @Value("#{T(com.realfinance.sofa.cg.sup.util.ApiSecureUtils).loadPrivateKeyByStr('${rf.api.privateKey:}')}")
    private RSAPrivateKey privateKey;

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {
        if (privateKeyStr == null || privateKeyStr.isEmpty()) {
            return null;
        }
        try {
            byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public byte[] decryptBase64(byte[] bytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] body = Base64.getDecoder().decode(bytes);
        return rsa.doFinal(body);
    }

    public byte[] decryptBase64(String base64) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] body = Base64.getDecoder().decode(base64);
        return rsa.doFinal(body);
    }


    public static byte[] decrypt(byte[] bytes) throws Exception {
        return SpringContextHolder.getBean(ApiSecureUtils.class).decryptBase64(bytes);
    }

    public static byte[] decrypt(String data) throws Exception {
        return SpringContextHolder.getBean(ApiSecureUtils.class).decryptBase64(data);
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
