package com.realfinance.sofa.flow.domain;

import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.BindingParam;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import com.realfinance.sofa.flow.facade.CallbackFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.flow.util.SofaClientFactoryHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.realfinance.sofa.flow.util.ExceptionUtils.businessException;

public enum CallbackType {

    RPC {
        @Override
        public FlowCallbackResponse sendCallbackRequestInternal(String callbackUrl, FlowCallbackRequest request) {
            ClientFactory clientFactory = SofaClientFactoryHolder.get();
            ReferenceClient referenceClient = clientFactory.getClient(ReferenceClient.class);
            ReferenceParam<CallbackFacade> referenceParam = new ReferenceParam<>();
            referenceParam.setInterfaceType(CallbackFacade.class);
            referenceParam.setUniqueId(callbackUrl);
            BindingParam refBindingParam = new BoltBindingParam(); // 暂时默认用bolt不可设置
            referenceParam.setBindingParam(refBindingParam);
            CallbackFacade reference = referenceClient.reference(referenceParam);
            return reference.callback(request);
        }
    },

    HTTP {
        private final RestTemplate restTemplate = new RestTemplate();

        @Override
        public FlowCallbackResponse sendCallbackRequestInternal(String callbackUrl, FlowCallbackRequest request) {
            ResponseEntity<FlowCallbackResponse> responseEntity = restTemplate
                    .postForEntity(callbackUrl, request, FlowCallbackResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }

            return null;
        }
    },
    NONE {
        @Override
        public FlowCallbackResponse sendCallbackRequestInternal(String callbackUrl, FlowCallbackRequest request) {
            FlowCallbackResponse response = new FlowCallbackResponse();
            response.setSuccess(true);
            return response;
        }
    }
    ;

    private static final Logger log = LoggerFactory.getLogger(CallbackType.class);

    /**
     * 发送回调请求
     * @param callbackUrl 回调URL
     * @param request 回调请求对象
     * @return
     */
    public FlowCallbackResponse sendCallbackRequest(String callbackUrl, FlowCallbackRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("发送回调请求，url：{}，request：{}",callbackUrl,request);
        }
        FlowCallbackResponse response = null;
        try {
            response = sendCallbackRequestInternal(callbackUrl,request);
        } catch (Exception e) {
            log.error("发送回调请求时异常，url：{}，request：{}",callbackUrl,request);
            log.error("发送回调请求时异常",e);
        }

        if (response == null) {
            throw businessException("调用回调接口失败");
        }

        if (!response.getSuccess()) {
            throw businessException("业务系统处理回调失败，" + response.getMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug("回调结果：{}",response);
        }

        return response;
    }

    protected abstract FlowCallbackResponse sendCallbackRequestInternal(String callbackUrl, FlowCallbackRequest request);
}
