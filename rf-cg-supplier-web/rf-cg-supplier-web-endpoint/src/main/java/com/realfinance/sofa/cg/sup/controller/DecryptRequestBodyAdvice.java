package com.realfinance.sofa.cg.sup.controller;

import com.realfinance.sofa.cg.sup.annotation.DecryptRequestBody;
import com.realfinance.sofa.cg.sup.util.ApiSecureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 请求体解密
 * 需要配置文件中配置私钥
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethodAnnotation(DecryptRequestBody.class) != null;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return new DecryptedHttpInputMessage(inputMessage);
    }

    private static class DecryptedHttpInputMessage implements HttpInputMessage {

        private static final Logger log = LoggerFactory.getLogger(DecryptedHttpInputMessage.class);

        HttpInputMessage him;

        public DecryptedHttpInputMessage(HttpInputMessage him) {
            this.him = him;
        }

        @Override
        public InputStream getBody() throws IOException {
            try {
                long startTime = System.currentTimeMillis();
                byte[] decrypted = ApiSecureUtils.decrypt(him.getBody().readAllBytes());
                if (log.isDebugEnabled()) {
                    long t = System.currentTimeMillis() - startTime;
                    log.debug("解密耗时：{}",t);
                }
                return new ByteArrayInputStream(decrypted);
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                return InputStream.nullInputStream();
            }
        }

        @Override
        public HttpHeaders getHeaders() {
            return him.getHeaders();
        }
    }
}
