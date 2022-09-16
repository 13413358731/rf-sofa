package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.PurchaseMode;
import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocumentWord;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface BiddingDocumentWordRepository extends JpaRepositoryImplementation<BiddingDocumentWord, Integer> {


    Optional<BiddingDocumentWord> findByPurchaseModeAndTenantId(PurchaseMode purchaseMode, String tenantId);
}
