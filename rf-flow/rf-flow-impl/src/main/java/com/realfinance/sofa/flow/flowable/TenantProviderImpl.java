package com.realfinance.sofa.flow.flowable;

import com.realfinance.sofa.common.datascope.DataScopeUtils;
import org.flowable.ui.common.tenant.TenantProvider;

public class TenantProviderImpl implements TenantProvider {

    @Override
    public String getTenantId() {
        return DataScopeUtils.loadTenantId();
    }
}
