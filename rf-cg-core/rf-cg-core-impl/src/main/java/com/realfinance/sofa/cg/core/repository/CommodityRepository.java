package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface CommodityRepository extends JpaRepositoryImplementation<Commodity, Integer> {
    boolean existsById(String id);

//    List<Commodity> findByExpireStatus(Integer expireStatus);
}
