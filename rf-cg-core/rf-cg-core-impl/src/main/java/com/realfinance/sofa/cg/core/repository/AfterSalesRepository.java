package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.AfterSales.AfterSales;
import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface AfterSalesRepository extends JpaRepositoryImplementation<AfterSales, Integer> {
    boolean existsById(String id);

//    List<Commodity> findByExpireStatus(Integer expireStatus);
}
