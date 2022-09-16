package com.realfinance.sofa.common.jpa.auditor;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.NumberUtils;

import java.util.Optional;

public class SofaRpcAuditor implements AuditorAware<Integer> {

    private static final Logger log = LoggerFactory.getLogger(SofaRpcAuditor.class);

    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.of(RpcInvokeContext.getContext())
                .map(context -> context.getRequestBaggage(RpcContextConstants.PRINCIPAL_ID_REQUEST_BAGGAGE_KEY))
                .map(this::parseNumber);
    }

    private Integer parseNumber(String currentUserId) {
        try {
            return NumberUtils.parseNumber(currentUserId, Integer.class);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("用户ID转换异常,ID: {}",currentUserId);
            }
            return null;
        }
    }
}
