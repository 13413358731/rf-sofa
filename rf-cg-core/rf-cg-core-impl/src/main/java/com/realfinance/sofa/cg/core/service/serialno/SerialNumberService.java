package com.realfinance.sofa.cg.core.service.serialno;

import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecordId;

import java.util.Map;

public interface SerialNumberService {
    String next(SerialNumberRecordId id);
    String next(SerialNumberRecordId id, Map<String, Object> variables);
}
