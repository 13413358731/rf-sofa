package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface VendorRatingsRepository extends JpaRepositoryImplementation<VendorRating, Integer> {
    boolean existsById(String id);

//    List<VendorRating> findByExpireStatus(Integer expireStatus);
}
