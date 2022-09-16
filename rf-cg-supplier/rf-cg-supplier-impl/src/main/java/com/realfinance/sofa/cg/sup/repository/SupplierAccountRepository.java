package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierAccount;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface SupplierAccountRepository extends JpaRepositoryImplementation<SupplierAccount, Integer> {

    boolean existsByTenantIdAndUsername(String tenantId, String username);

    Optional<SupplierAccount> findByTenantIdAndUsername(String tenantId, String username);
    @Lock(LockModeType.PESSIMISTIC_WRITE) // for update
    @Query("SELECT a FROM SupplierAccount a WHERE a.id = :id")
    Optional<SupplierAccount> lockAccount(Integer id);

}
