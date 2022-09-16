package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface ContractManageRepository extends JpaRepositoryImplementation<ContractManage, Integer> {
    boolean existsById(String id);

    List<ContractManage> findByExpireStatus(Integer expireStatus);
}
