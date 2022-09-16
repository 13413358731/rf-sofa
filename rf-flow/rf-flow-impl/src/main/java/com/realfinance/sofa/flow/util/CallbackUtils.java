package com.realfinance.sofa.flow.util;

import com.realfinance.sofa.flow.domain.CallbackType;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;

public class CallbackUtils {

    public static CallbackType getCallbackType(String callbackType) {
        try {
            return CallbackType.valueOf(callbackType);
        } catch (Exception e) {
            throw new FlowableIllegalArgumentException("找不到回调类型");
        }
    }
}
