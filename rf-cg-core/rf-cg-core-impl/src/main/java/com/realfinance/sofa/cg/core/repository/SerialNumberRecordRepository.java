package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecord;
import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecordId;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SerialNumberRecordRepository extends JpaRepositoryImplementation<SerialNumberRecord, SerialNumberRecordId> {

}
