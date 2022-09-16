package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.ProductLibrary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ProductLibraryRepository extends JpaRepositoryImplementation<ProductLibrary,Integer> {


/*    Page<ProductLibrary> findAllByPurchaseCatalogId(Integer purchaseCatalogId);*/


}
