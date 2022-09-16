package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;

public interface CallbackFacade {
    /**
     * 流程回调
     * @param request
     * @return
     */
    FlowCallbackResponse callback(FlowCallbackRequest request);
}
