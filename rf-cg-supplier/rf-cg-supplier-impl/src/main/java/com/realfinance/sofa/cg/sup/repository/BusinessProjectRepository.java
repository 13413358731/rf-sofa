package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.BusinessProject;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import javax.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BusinessProjectRepository extends JpaRepositoryImplementation<BusinessProject, Integer> {

    Optional<BusinessProject> findByProjectIdAndTenantId(String projectId, String tenantId);

    List<BusinessProject> findByProjectIdInAndTenantId(Collection<String> projectIds, String tenantId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT bp FROM BusinessProject bp WHERE bp.projectId = :projectId AND bp.tenantId = :tenantId")
    Optional<BusinessProject> lockBusinessProject(String projectId, String tenantId);
}
