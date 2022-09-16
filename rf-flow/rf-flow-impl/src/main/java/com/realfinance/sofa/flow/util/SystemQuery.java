package com.realfinance.sofa.flow.util;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import org.springframework.stereotype.Component;

@Component
public class SystemQuery {

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    public static SystemQueryFacade get() {
        return SpringContextHolder.getBean(SystemQuery.class).systemQueryFacade;
    }
}
