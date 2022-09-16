package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.time.LocalDateTime;
import java.util.List;

public interface TenantRepository extends JpaRepositoryImplementation<Tenant, String> {

    List<Tenant> findByStartTimeBeforeAndEndTimeAfterAndEnabled(LocalDateTime startTime,LocalDateTime endTime,Boolean enabled);
}
